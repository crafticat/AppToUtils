package mc.apptoeat.com.utils.managers;

import lombok.Getter;
import mc.apptoeat.com.utils.ability.ItemAbility;

import java.util.ArrayList;

public class ItemAbilityManager {
    @Getter
    private ArrayList<ItemAbility> items = new ArrayList<>();

    public ItemAbilityManager() {
        //TODO add listeners in the currentPlugin
        /* in order to register a event do main.getInstance().getEventManager().events.add() */

    }
}
