package gregtech.api.cover;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.function.BiFunction;

public final class CoverDefinition extends IForgeRegistryEntry.Impl {

    public static BiMap<ResourceLocation, CoverDefinition> registry = HashBiMap.create();

    public static CoverDefinition getCoverById(ResourceLocation id) {
        return registry.get(id);
    }

    public static void registerCover(CoverDefinition coverDefinition) {
        if(registry.containsKey(coverDefinition.coverId) ||
            registry.containsValue(coverDefinition)) {
            throw new IllegalArgumentException("Id is already occupied: " + coverDefinition.coverId);
        }
        registry.put(coverDefinition.coverId, coverDefinition);
    }

    private final ResourceLocation coverId;
    private final BiFunction<MetaTileEntity, EnumFacing, CoverBehavior> behaviorCreator;
    private final ItemStack dropItemStack;

    public CoverDefinition(ResourceLocation coverId, BiFunction<MetaTileEntity, EnumFacing, CoverBehavior> behaviorCreator, ItemStack dropItemStack) {
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

    public CoverBehavior createCoverBehavior(MetaTileEntity metaTileEntity, EnumFacing side) {
        CoverBehavior coverBehavior = behaviorCreator.apply(metaTileEntity, side);
        coverBehavior.setCoverDefinition(this);
        return coverBehavior;
    }

}
