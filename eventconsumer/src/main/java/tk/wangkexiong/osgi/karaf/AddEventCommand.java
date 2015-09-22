package tk.wangkexiong.osgi.karaf;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import tk.wangkexiong.osgi.business.EventRepository;


@Command(scope = "eventhandler", name = "add", description = "Add an event listener.")
public class AddEventCommand extends OsgiCommandSupport {
    private static final String DESC =
        "The event topic to listen to (*, org/apache/karaf, org/apache/karaf/*," +
        "org/apache/karaf/log, org.apache/karaf/log2),\n" +
        "only one handler per topic will be created." +
        "The filter is space separated.";
    @Argument(index = 0, name = "filter", description = DESC, required = true, multiValued = false)
    String filter;
    EventRepository repository;

    @Override
    protected Object doExecute() throws Exception {
        repository.addEvent(filter);

        return null;
    }

    public void setRepository(EventRepository respository) {
        this.repository = respository;
    }
}
