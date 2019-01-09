package gregtech.common.command.util;

import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IElectricItem;
import gregtech.api.items.materialitem.MaterialMetaItem;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.MetaItem.MetaValueItem;
import gregtech.api.items.toolitem.IToolStats;
import gregtech.api.items.toolitem.ToolMetaItem;
import gregtech.api.items.toolitem.ToolMetaItem.MetaToolValueItem;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class CommandHand extends CommandBase {
    @Override
    public String getName() {
        return "hand";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "gregtech.command.util.hand.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(sender instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) sender;
            ItemStack stackInHand = player.inventory.getCurrentItem();
            if(stackInHand.isEmpty()) {
                throw new CommandException("gregtech.command.util.hand.no_item");
            }
            player.sendMessage(new TextComponentTranslation("gregtech.command.util.hand.item_id", stackInHand.getItem().getRegistryName(), stackInHand.getItemDamage()));
            IElectricItem electricItem = stackInHand.getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
            IFluidHandlerItem fluidHandlerItem = stackInHand.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            if(electricItem != null) {
                player.sendMessage(new TextComponentTranslation("gregtech.command.util.hand.electric",
                    electricItem.getCharge(),
                    electricItem.getMaxCharge(),
                    electricItem.getTier(),
                    Boolean.toString(electricItem.canProvideChargeExternally())));
            }
            if(fluidHandlerItem != null) {
                for(IFluidTankProperties properties : fluidHandlerItem.getTankProperties()) {
                    FluidStack contents = properties.getContents();
                    player.sendMessage(new TextComponentTranslation("gregtech.command.util.hand.fluid",
                        contents == null ? "empty" : contents.getFluid().getName(),
                        contents == null ? 0 : contents.amount,
                        properties.getCapacity(),
                        Boolean.toString(properties.canFill()), Boolean.toString(properties.canDrain())));
                }
            }
            if(stackInHand.getItem() instanceof MetaItem) {
                MetaItem<?> metaItem = (MetaItem<?>) stackInHand.getItem();
                MetaValueItem metaValueItem = metaItem.getItem(stackInHand);
                if(metaValueItem == null) {
                    if(metaItem instanceof MaterialMetaItem) {
                        Material material = ((MaterialMetaItem) metaItem).getMaterial(stackInHand);
                        OrePrefix orePrefix = ((MaterialMetaItem) metaItem).getOrePrefix(stackInHand);
                        player.sendMessage(new TextComponentTranslation("gregtech.command.util.hand.material_meta_item", orePrefix, material));
                    }
                } else {
                    if(metaValueItem instanceof ToolMetaItem.MetaToolValueItem) {
                        IToolStats toolStats = ((MetaToolValueItem) metaValueItem).getToolStats();
                        player.sendMessage(new TextComponentTranslation("gregtech.command.util.hand.tool_stats", toolStats.getClass().getName()));
                    }
                    player.sendMessage(new TextComponentTranslation("gregtech.command.util.hand.meta_item", metaValueItem.unlocalizedName, metaValueItem));
                }
            }
        } else {
            throw new CommandException("gregtech.command.util.hand.not_a_player");
        }
    }
}
