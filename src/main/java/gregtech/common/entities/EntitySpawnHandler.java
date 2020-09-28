package gregtech.common.entities;

import gregtech.api.GTValues;
import gregtech.common.ConfigHolder;
import gregtech.common.items.MetaItems;
import gregtech.common.items.behaviors.ToggleEnergyConsumerBehavior;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = GTValues.MODID)
public class EntitySpawnHandler {

    @SubscribeEvent
    public static void onEntitySpawn(LivingSpawnEvent.SpecialSpawn event) {
        EntityLivingBase entity = event.getEntityLiving();
        EnumDifficulty difficulty = entity.world.getDifficulty();
        if (difficulty == EnumDifficulty.HARD && entity.getRNG().nextFloat() <= 0.03f) {
            if (entity instanceof EntityZombie && ConfigHolder.nanoSaberConfiguration.zombieSpawnWithSabers) {
                ItemStack itemStack = MetaItems.NANO_SABER.getInfiniteChargedStack();
                ToggleEnergyConsumerBehavior.setItemActive(itemStack, true);
                entity.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, itemStack);
                ((EntityZombie) entity).setDropChance(EntityEquipmentSlot.MAINHAND, 0.0f);
            }
        }
    }
}
