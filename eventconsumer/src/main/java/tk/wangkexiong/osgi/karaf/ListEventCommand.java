package tk.wangkexiong.osgi.karaf;

import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import tk.wangkexiong.osgi.business.EventRepository;

import java.util.Set;


@Command(scope = "eventhandler", name = "list", description = "List all event handlers.")
public class ListEventCommand extends OsgiCommandSupport {
    EventRepository repository;

    @Override
    protected Object doExecute() throws Exception {
        Set<String> filters = repository.getFilters();
        StringBuilder builder = new StringBuilder();
        builder.append("Registered EventHandler Topics\n");

        for (String filt : filters) {
            builder.append(filt);
            builder.append("\n");
        }

        System.out.println(builder.toString());

        return null;
    }

    public void setRepository(EventRepository respository) {
        this.repository = respository;
    }
}
