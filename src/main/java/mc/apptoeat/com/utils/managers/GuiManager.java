package mc.apptoeat.com.utils.managers;

import lombok.Getter;
import mc.apptoeat.com.utils.events.Event;
import mc.apptoeat.com.utils.gui.Gui;

import java.util.ArrayList;

public class GuiManager {
    @Getter
    private ArrayList<Gui> guis = new ArrayList<>();

    public GuiManager() {
        //TODO add listeners in the currentPlugin
        /* in order to register a event do main.getInstance().getEventManager().events.add() */
    }
}
