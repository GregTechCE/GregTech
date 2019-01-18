package gregtech.api.cover;

import gregtech.api.util.GTControlledRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.BiFunction;

public final class CoverDefinition extends IForgeRegistryEntry.Impl {

    public static GTControlledRegistry<ResourceLocation, CoverDefinition> registry = new GTControlledRegistry<>(Integer.MAX_VALUE);

    public static CoverDefinition getCoverById(ResourceLocation id) {
        return registry.getObject(id);
    }

    public static CoverDefinition getCoverByNetworkId(int networkId) {
        return registry.getObjectById(networkId);
    }

    public static int getNetworkIdForCover(CoverDefinition definition) {
        return registry.getIDForObject(definition);
    }

    public static void registerCover(int id, CoverDefinition coverDefinition) {
        registry.register(id, coverDefinition.coverId, coverDefinition);
    }

    private final ResourceLocation coverId;
    private final BiFunction<ICoverable, EnumFacing, CoverBehavior> behaviorCreator;
    private final ItemStack dropItemStack;

    public CoverDefinition(ResourceLocation coverId, BiFunction<ICoverable, EnumFacing, CoverBehavior> behaviorCreator, ItemStack dropItemStack) {
        this.coverId = coverId;
        this.behaviorCreator = behaviorCreator;
        this.dropItemStack = dropItemStack.copy();
    }

    public ResourceLocation getCoverId() {
        return coverId;
    }

    public ItemStack getDropItemStack() {
        return dropItemStack.copy();
    }

    public CoverBehavior createCoverBehavior(ICoverable metaTileEntity, EnumFacing side) {
        CoverBehavior coverBehavior = behaviorCreator.apply(metaTileEntity, side);
        coverBehavior.setCoverDefinition(this);
        return coverBehavior;
    }

}
