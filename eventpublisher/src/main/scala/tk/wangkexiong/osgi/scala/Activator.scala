package tk.wangkexiong.osgi.scala

import org.osgi.framework._

class Activator extends BundleActivator {

    def start( context: BundleContext ) {
         var bundleNames = context.getBundle().getHeaders
          
         println(bundleNames.get("Bundle-Name") + " Started ...")
     }

     def stop( context: BundleContext )  {
         var bundleNames = context.getBundle().getHeaders
          
         println(bundleNames.get("Bundle-Name") + " Stoped ...")
     }
}
