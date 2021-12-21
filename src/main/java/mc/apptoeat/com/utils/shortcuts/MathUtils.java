package mc.apptoeat.com.utils.shortcuts;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class MathUtils {

    public static int getItemDamageValue(ItemStack is,ItemStack helmet1, ItemStack chestplate1,ItemStack leggings1,ItemStack boots1) {

        double damageValue = 0;
        if(is != null) {
            if (is.getType() == Material.AIR) {
                damageValue = 1;
            } else if (is.getType() == Material.WOOD_SWORD) {
                damageValue = 5;
            } else if (is.getType() == Material.STONE_SWORD) {
                damageValue = 6;
            } else if (is.getType() == Material.IRON_SWORD) {
                damageValue = 7;
            } else if (is.getType() == Material.DIAMOND_SWORD) {
                damageValue = 8;
            } else {
                damageValue = 1; // other blocks & items
            }
        }

        if (helmet1 != null) {
            Material helmet = helmet1.getType();
            if (helmet == Material.LEATHER_HELMET)
                damageValue -= (0.5 / 1.5);
                // value shown at bar above the health bar / 1.5
            else if (helmet == Material.CHAINMAIL_HELMET
                    || helmet == Material.IRON_HELMET
                    || helmet == Material.DIAMOND_HELMET
                    || helmet == Material.GOLD_HELMET)
                damageValue -= (1 / 1.5);
        }

        if (chestplate1 != null) {
            Material chestplate = chestplate1.getType();
            if (chestplate == Material.LEATHER_CHESTPLATE)
                damageValue -= (1.5 / 1.5);
            else if (chestplate == Material.CHAINMAIL_CHESTPLATE
                    || chestplate == Material.GOLD_CHESTPLATE)
                damageValue -= (2.5 / 1.5);
            else if (chestplate == Material.IRON_CHESTPLATE)
                damageValue -= (3 / 1.5);
            else if (chestplate == Material.DIAMOND_CHESTPLATE)
                damageValue -= (4 / 1.5);
        }

        if (leggings1 != null) {
            Material leggings = leggings1.getType();
            if (leggings == Material.LEATHER_LEGGINGS)
                damageValue -= (1 / 1.5);
            else if (leggings == Material.GOLD_LEGGINGS)
                damageValue -= (1.5 / 1.5);
            else if (leggings == Material.GOLD_LEGGINGS)
                damageValue -= (1.5 / 1.5);
            else if (leggings == Material.GOLD_LEGGINGS)
                damageValue -= (1.5 / 1.5);
            else if (leggings == Material.CHAINMAIL_LEGGINGS)
                damageValue -= (2 / 1.5);
            else if (leggings == Material.IRON_LEGGINGS)
                damageValue -= (2.5 / 1.5);
            else if (leggings == Material.DIAMOND_LEGGINGS)
                damageValue -= (3 / 1.5);
        }
        if (boots1 != null) {
            Material boots = boots1.getType();
            if (boots == Material.LEATHER_BOOTS
                    || boots == Material.GOLD_BOOTS
                    || boots == Material.CHAINMAIL_BOOTS)
                damageValue -= (0.5 / 1.5);
            else if (boots == Material.IRON_BOOTS)
                damageValue -= (1 / 1.5);
            else if (boots == Material.DIAMOND_BOOTS)
                damageValue -= (1.5 / 1.5);
        }


        return (int) Math.round(damageValue);
    }

    public static boolean equalVectors(Vector vector, Vector vector2,double epsilon) {
        return Math.abs(vector.getX() - vector2.getX()) < epsilon && Math.abs(vector.getY() - vector2.getY()) < epsilon && Math.abs(vector.getZ() - vector2.getZ()) < epsilon;
    }

    public static double floor(double number, double by, boolean down) {
        double var1 = number / by;
        if (down) {
            var1 = Math.floor(var1);
        } else var1  = Math.ceil(var1);
        var1 *= by;

        return var1;
    }

    public static double getRandom(double min,double max) {
        min = min * 100;
        max = max * 100;

        for(int i = (int) min; i <=max; i++) {
            double getRandomValue = ((int) (Math.random() * (max - min)) + min) / 100;
            return getRandomValue;
        }
        return 0;
    }
}
