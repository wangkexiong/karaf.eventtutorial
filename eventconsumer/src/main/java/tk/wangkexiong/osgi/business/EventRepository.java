package tk.wangkexiong.osgi.business;

import org.apache.karaf.shell.console.BundleContextAware;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;


public class EventRepository implements BundleContextAware {
    private Map<String, Registry> eventHandlers = new HashMap<String, Registry>();
    protected BundleContext bundleContext;

    public synchronized void addEvent(String filter) throws InvalidSyntaxException {
        if (!eventHandlers.containsKey(filter)) {
            EventHandler handler = new EventDisplayer();
            Dictionary<String, String> properties = new Hashtable<String, String>();
            properties.put(EventConstants.EVENT_TOPIC, filter);

            ServiceRegistration<?> registration = getBundleContext()
                                                      .registerService(EventHandler.class.getName(),
                    handler, properties);

            Registry registry = new Registry(handler, registration);
            eventHandlers.put(filter, registry);
        }
    }

    public synchronized void removeEvent(String filter) {
        if (eventHandlers.containsKey(filter)) {
            Registry registry = eventHandlers.get(filter);
            registry.getRegistration().unregister();

            releaseResource(registry, registry.getHandler());
            eventHandlers.remove(filter);
        }
    }

    private void releaseResource(Registry registry, EventHandler eventHandler) {
        eventHandler = null;
        registry = null;
    }

    public void cleanup() {
        for (String key : eventHandlers.keySet()) {
            Registry registry = eventHandlers.get(key);
            registry.getRegistration().unregister();

            releaseResource(registry, registry.getHandler());
        }

        eventHandlers = null;
    }

    public BundleContext getBundleContext() {
        Bundle framework = bundleContext.getBundle(0);

        return (framework == null) ? bundleContext : framework.getBundleContext();
    }

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    public Set<String> getFilters() {
        return eventHandlers.keySet();
    }

    private class Registry {
        private EventHandler handler;
        private ServiceRegistration<?> registration;

        public Registry(EventHandler handler,
            ServiceRegistration<?> registration) {
            this.handler = handler;
            this.registration = registration;
        }

        public EventHandler getHandler() {
            return handler;
        }

        public ServiceRegistration<?> getRegistration() {
            return registration;
        }
    }
}
