package gregtech.common.pipelike;

import com.google.common.collect.*;
import crafttweaker.annotations.ZenRegister;
import gregtech.api.pipelike.IBaseProperty;
import gregtech.api.pipelike.IPipeLikeTileProperty;
import gregtech.api.pipelike.PipeFactory;
import gregtech.api.unification.material.type.Material;
import gregtech.common.pipelike.cables.CableFactory;
import gregtech.common.pipelike.fluidpipes.FluidPipeFactory;
import gregtech.common.pipelike.itempipes.ItemPipeFactory;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@ZenClass("mod.gregtech.CableAndPipeRegistry")
@ZenRegister
public class CTCableAndPipeRegistry {

    private static Table<String, Material, long[]> cachedRegistryEntries = TreeBasedTable.create();
    private static Multimap<String, Material> cachedSpecialIgnorance = HashMultimap.create();
    private static Table<String, Material, IBaseProperty> cachedIgnorance = HashBasedTable.create();
    private static Table<String, Material, IBaseProperty> cachedGeneration = HashBasedTable.create();

    @ZenMethod
    public static void registerCable(Material material, long voltage, int baseAmperage, int lossPerBlock, @Optional(valueLong = -1L) int color) {
        Objects.requireNonNull(material);
        cachedRegistryEntries.put(CableFactory.INSTANCE.name, material, new long[]{voltage, baseAmperage, lossPerBlock, color});
    }

    @ZenMethod
    public static void setNoInsulatedCables(Material material) {
        Objects.requireNonNull(material);
        cachedSpecialIgnorance.put(CableFactory.INSTANCE.name, material);
    }

    @ZenMethod
    public static void disableCable(String type, Material material) {
        cacheIgnoranceCase(CableFactory.INSTANCE, type, material, true);
    }

    @ZenMethod
    public static void reenableCable(String type, Material material) {
        cacheIgnoranceCase(CableFactory.INSTANCE, type, material, false);
    }

    @ZenMethod
    public static void registerFluidPipe(Material material, int fluidCapacity, int heatLimit, @Optional(valueBoolean = true) boolean isGasProof, @Optional(valueLong = -1L) int color) {
        Objects.requireNonNull(material);
        cachedRegistryEntries.put(FluidPipeFactory.INSTANCE.name, material, new long[]{fluidCapacity, heatLimit, isGasProof ? 1L : 0L, color});
    }

    @ZenMethod
    public static void setOnlyMediumSizedFluidPipes(Material material) {
        Objects.requireNonNull(material);
        cachedSpecialIgnorance.put(FluidPipeFactory.INSTANCE.name, material);
    }

    @ZenMethod
    public static void disableFluidPipe(String type, Material material) {
        cacheIgnoranceCase(FluidPipeFactory.INSTANCE, type, material, true);
    }

    @ZenMethod
    public static void reenableFluidPipe(String type, Material material) {
        cacheIgnoranceCase(FluidPipeFactory.INSTANCE, type, material, false);
    }

    @ZenMethod
    public static void registerItemPipe(Material material, int transferCapacity, @Optional int tickRate, @Optional int routingValueMultiplier, @Optional(valueLong = -1L) int color) {
        Objects.requireNonNull(material);
        cachedRegistryEntries.put(ItemPipeFactory.INSTANCE.name, material, new long[]{transferCapacity, tickRate, routingValueMultiplier, color});
    }

    @ZenMethod
    public static void disableItemPipe(String type, Material material) {
        cacheIgnoranceCase(ItemPipeFactory.INSTANCE, type, material, true);
    }

    @ZenMethod
    public static void reenableItemPipe(String type, Material material) {
        cacheIgnoranceCase(ItemPipeFactory.INSTANCE, type, material, false);
    }

    private static <Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty>
    void cacheIgnoranceCase(PipeFactory<Q, P, ?> factory, String type, Material material, boolean ignore) {
        Q property = null;
        for (Q p : factory.classBaseProperty.getEnumConstants()) if (p.name().equals(type)) {
            property = p;
            break;
        }
        Objects.requireNonNull(property);
        Objects.requireNonNull(material);
        (ignore ? cachedIgnorance : cachedGeneration).put(factory.name, material, property);
    }

    @SubscribeEvent
    public static void registerCables(CableFactory.CableRegistryEvent event) {
        String name = event.factory.name;
        registerCachedEntries(event,
            (material, data) -> event.registerCable(material, Math.max(data[0], 1L), Math.max((int) data[1], 1), Math.max((int) data[2], 0)),
            (material, data) -> {if (data[3] != -1L) event.specifyMaterialColor(material, (int) data[3]);});
        registerSpecialIgnorance(event, event::setNotInsulable);
        registerIgnoranceCase(event);
    }

    @SubscribeEvent
    public static void registerFluidPipes(FluidPipeFactory.FluidPipeRegistryEvent event) {
        registerCachedEntries(event,
            (material, data) -> event.registerFluidPipe(material, Math.max((int) data[0], 1), Math.max((int) data[1], 1), data[2] != 0L),
            (material, data) -> {if (data[3] != -1L) event.specifyMaterialColor(material, (int) data[3]);});
        registerSpecialIgnorance(event, event::setOnlyMediumSized);
        registerIgnoranceCase(event);
    }

    @SubscribeEvent
    public static void registerItemPipes(ItemPipeFactory.ItemPipeRegistryEvent event) {
        registerCachedEntries(event,
            (material, data) -> event.registerItemPipe(material, Math.max((int) data[0], 1), Math.max((int) data[1], 1), Math.max((int) data[2], 1)),
            (material, data) -> {if (data[3] != -1L) event.specifyMaterialColor(material, (int) data[3]);});
        registerIgnoranceCase(event);
    }

    private static <Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty>
    void registerCachedEntries(PipeFactory.PipeRegistryEvent<Q, P> event, BiConsumer<Material, long[]> register, BiConsumer<Material, long[]> colorSpecifier) {
        if (cachedRegistryEntries.containsRow(event.factory.name)) {
            cachedRegistryEntries.rowMap().get(event.factory.name).forEach((material, data) -> {
                register.accept(material, data);
                colorSpecifier.accept(material, data);
            });
        }
    }

    private static <Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty>
    void registerSpecialIgnorance(PipeFactory.PipeRegistryEvent<Q, P> event, Consumer<Material> handler) {
        if (cachedSpecialIgnorance.containsKey(event.factory.name)) {
            cachedSpecialIgnorance.get(event.factory.name).forEach(handler::accept);
        }
    }

    @SuppressWarnings("unchecked")
    private static <Q extends Enum<Q> & IBaseProperty & IStringSerializable, P extends IPipeLikeTileProperty>
    void registerIgnoranceCase(PipeFactory.PipeRegistryEvent<Q, P> event) {
        if (cachedIgnorance.containsRow(event.factory.name)) {
            cachedIgnorance.rowMap().get(event.factory.name).forEach(((material, property) -> event.setIgnored((Q) property, material)));
        }
        if (cachedGeneration.containsRow(event.factory.name)) {
            cachedGeneration.rowMap().get(event.factory.name).forEach(((material, property) -> event.setGenerated((Q) property, material)));
        }
    }
}
