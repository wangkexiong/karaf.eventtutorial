package tk.wangkexiong.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class Activator implements BundleActivator {
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println(getBundleName(context) + " Started ...");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println(getBundleName(context) + " Stoped ...");
    }

    private String getBundleName(BundleContext context) {
        return context.getBundle().getHeaders().get("Bundle-Name");
    }
}
