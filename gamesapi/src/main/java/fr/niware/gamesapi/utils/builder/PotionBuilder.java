package fr.niware.gamesapi.utils.builder;

import org.bukkit.Material;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class PotionBuilder extends ItemBuilder {

    public PotionBuilder(Material material, int amount, PotionType potionType, boolean extended, boolean upgraded) {
        super(material, amount);
        this.getMeta().setBasePotionData(new PotionData(potionType, extended, upgraded));
    }

    @Override
    public PotionMeta getMeta() {
        return (PotionMeta) super.getMeta();
    }

    public PotionBuilder effect(PotionEffectType potionEffectType, int duration, int values) {
        this.getMeta().addCustomEffect(new PotionEffect(potionEffectType, duration, values), true);
        return this;
    }
}