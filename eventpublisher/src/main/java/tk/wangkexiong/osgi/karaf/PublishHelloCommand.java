package tk.wangkexiong.osgi.karaf;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.BundleContextAware;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import java.util.Dictionary;
import java.util.Hashtable;


@Command(scope = "event", name = "publish", description = "Publish Hello Event.")
public class PublishHelloCommand extends OsgiCommandSupport
    implements BundleContextAware {

    private static final String DESC = "Say hello to somebody";

    @Argument(index = 0, name = "topic", description = DESC, required = true, multiValued = false)
    protected String topic;
    @Argument(index = 1, name = "name", description = DESC, required = true, multiValued = false)
    protected String name;

    protected BundleContext bundleContext;

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    @Override
    protected Object doExecute() throws Exception {
        ServiceReference<?> ref = bundleContext.getServiceReference(EventAdmin.class.getName());

        if (ref != null) {
            EventAdmin eventAdmin = (EventAdmin) bundleContext.getService(ref);

            Dictionary<String, String> properties = new Hashtable<String, String>();
            properties.put("name", this.name);

            Event event = new Event(this.topic, properties);
            eventAdmin.sendEvent(event);
        } else {
            System.out.println("eventadmin feature is not installed...");
        }

        return null;
    }
}

