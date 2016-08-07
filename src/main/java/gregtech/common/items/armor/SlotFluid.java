package gregtech.common.items.armor;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.reflect.internal.Trees.This;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;

public class SlotFluid extends Slot{

	public SlotFluid(IInventory inventory, int slot_index, int x, int y) {
		super(inventory, slot_index, x, y);
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer p_82869_1_)
    {
        return false;
    }
	
	@Override
    public boolean isItemValid(ItemStack p_75214_1_)
    {
        return false;
    }
	
}
