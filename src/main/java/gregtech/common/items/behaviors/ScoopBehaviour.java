package gregtech.common.items.behaviors;

import forestry.api.lepidopterology.EnumFlutterType;
import forestry.api.lepidopterology.IButterfly;
import forestry.api.lepidopterology.IEntityButterfly;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.util.GTUtility;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ScoopBehaviour implements IItemBehaviour {

    private final int cost;

    public ScoopBehaviour(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemStack, EntityPlayer player, Entity entity) {
        if (entity instanceof IEntityButterfly) {
            if (player.world.isRemote) {
                return true;
            }
            if (player.capabilities.isCreativeMode || GTUtility.doDamageItem(itemStack, this.cost, false)) {
                Object tButterfly = ((IEntityButterfly) entity).getButterfly();
                ((IButterfly) tButterfly).getGenome().getPrimary().getRoot().getBreedingTracker(entity.world, player.getGameProfile()).registerCatch((IButterfly) tButterfly);
                player.world.spawnEntity(new EntityItem(player.world, entity.posX, entity.posY, entity.posZ, ((IButterfly) tButterfly).getGenome().getPrimary().getRoot().getMemberStack(((IButterfly) tButterfly).copy(), EnumFlutterType.BUTTERFLY)));
                entity.setDead();
            }
            return true;
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack itemStack, List<String> lines) {
        lines.add(I18n.format("behaviour.scoop"));
    }
}
