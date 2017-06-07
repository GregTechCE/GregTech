package gregtech.api.damagesources;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class DamageSources {

    private static DamageSource EXPLOSION = new DamageSource("explosion");
    private static DamageSource HEAT = new DamageSource("heat");
    private static DamageSource FROST = new DamageSource("frost");

    public static DamageSource getElectricDamage() {
        return ic2.api.info.Info.DMG_ELECTRIC;
    }

    public static DamageSource getRadioactiveDamage() {
        return ic2.api.info.Info.DMG_RADIATION;
    }

    public static DamageSource getExplodingDamage() {
        return EXPLOSION;
    }

    public static DamageSource getHeatDamage() {
        return HEAT;
    }

    public static DamageSource getFrostDamage() {
        return FROST;
    }

    public static DamageSource causeCombatDamage(String aType, EntityLivingBase aDamager) {
        return new EntityDamageSource(aType, aDamager);
    }

}