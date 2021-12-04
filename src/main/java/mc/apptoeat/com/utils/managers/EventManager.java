package mc.apptoeat.com.utils.managers;

import lombok.Getter;
import lombok.Setter;
import mc.apptoeat.com.utils.ai.PathFinding.NPCDamage;
import mc.apptoeat.com.utils.events.Event;

import java.util.ArrayList;

@Getter
@Setter
public class EventManager {
    @Getter
    private ArrayList<Event> events = new ArrayList<>();

    public EventManager() {
        //TODO add listeners in the currentPlugin
        /* in order to register a event do main.getInstance().getEventManager().events.add() */
        events.add(new NPCDamage());
    }
}
