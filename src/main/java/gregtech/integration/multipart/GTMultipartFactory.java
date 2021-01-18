package gregtech.integration.multipart;

import codechicken.lib.data.MCDataInput;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.api.IDynamicPartFactory;
import gregtech.api.GTValues;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class GTMultipartFactory implements IDynamicPartFactory {

    public static final ResourceLocation CABLE_PART_KEY = new ResourceLocation(GTValues.MODID, "cable");
    public static final ResourceLocation CABLE_PART_TICKABLE_KEY = new ResourceLocation(GTValues.MODID, "cable_tickable");
    public static final ResourceLocation FLUID_PIPE_PART_KEY = new ResourceLocation(GTValues.MODID, "fluid_pipe");
    public static final ResourceLocation FLUID_PIPE_ACTIVE_PART_KEY = new ResourceLocation(GTValues.MODID, "fluid_pipe_active");
    public static final ResourceLocation INVENTORY_PIPE_PART_KEY = new ResourceLocation(GTValues.MODID, "inv_pipe");
    public static final ResourceLocation INVENTORY_PIPE_TICKABLE_PART_KEY = new ResourceLocation(GTValues.MODID, "inv_pipe_tickable");

    public static final GTMultipartFactory INSTANCE = new GTMultipartFactory();
    private final Map<ResourceLocation, Supplier<TMultiPart>> partRegistry = new HashMap<>();

    public void registerFactory() {
        registerPart(CABLE_PART_KEY, CableMultiPart::new);
        registerPart(CABLE_PART_TICKABLE_KEY, CableMultiPartTickable::new);
        registerPart(FLUID_PIPE_PART_KEY, FluidPipeMultiPart::new);
        registerPart(FLUID_PIPE_ACTIVE_PART_KEY, FluidPipeActiveMultiPart::new);
        registerPart(INVENTORY_PIPE_PART_KEY, InventoryPipeMultiPart::new);
        registerPart(INVENTORY_PIPE_TICKABLE_PART_KEY, InventoryPipeMultiPartTickable::new);
        MultiPartRegistry.registerParts(this, partRegistry.keySet());
    }

    private void registerPart(ResourceLocation identifier, Supplier<TMultiPart> supplier) {
        partRegistry.put(identifier, supplier);
    }

    @Override
    public TMultiPart createPartServer(ResourceLocation identifier, NBTTagCompound compound) {
        return createPart(identifier);
    }

    @Override
    public TMultiPart createPartClient(ResourceLocation identifier, MCDataInput packet) {
        return createPart(identifier);
    }

    public TMultiPart createPart(ResourceLocation identifier) {
        if (partRegistry.containsKey(identifier)) {
            Supplier<TMultiPart> supplier = partRegistry.get(identifier);
            return supplier.get();

        }
        return null;
    }
}
