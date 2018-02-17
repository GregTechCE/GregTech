package gregtech.api.unification;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.Material;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.unification.stack.SimpleItemStack;
import gregtech.api.unification.stack.UnificationEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import static gregtech.api.GTValues.M;

public class OreDictUnifier {

    private OreDictUnifier() {}

    private static final HashMap<SimpleItemStack, ItemMaterialInfo> materialUnificationInfo = new HashMap<>();
    private static final HashMap<SimpleItemStack, UnificationEntry> stackUnificationInfo = new HashMap<>();
    private static final HashMap<UnificationEntry, ArrayList<SimpleItemStack>> stackUnificationItems = new HashMap<>();

    public static void registerOre(ItemStack itemStack, MaterialStack component, MaterialStack... byproducts) {
        if (itemStack.isEmpty()) return;
        materialUnificationInfo.put(new SimpleItemStack(itemStack), new ItemMaterialInfo(component, byproducts));
    }

    public static void registerOre(ItemStack itemStack, ItemMaterialInfo materialInfo) {
        if (itemStack.isEmpty()) return;
        materialUnificationInfo.put(new SimpleItemStack(itemStack), materialInfo);
    }

    public static void registerOre(ItemStack itemStack, OrePrefix orePrefix, @Nullable Material material) {
        if (itemStack.isEmpty()) return;
        OreDictionary.registerOre(orePrefix.name() + (material == null ? "" : material.toCamelCaseString()), itemStack);
    }

    public static void registerOre(ItemStack itemStack, String oreDict) {
        if (itemStack.isEmpty()) return;
        OreDictionary.registerOre(oreDict, itemStack);
    }

    public static void init() {
        MinecraftForge.EVENT_BUS.register(OreDictUnifier.class);
    }

    @SubscribeEvent
    public static void onItemRegistration(OreDictionary.OreRegisterEvent event) {
        String oreName = event.getName();
        OrePrefix orePrefix = OrePrefix.getPrefix(oreName);
        Material material = null;
        if(orePrefix == null) {
            //split ore dict name to parts
            //oreBasalticMineralSand -> ore, Basaltic, Mineral, Sand
            ArrayList<String> splits = new ArrayList<>();
            StringBuilder builder = new StringBuilder();
            for(char character : oreName.toCharArray()) {
                if(Character.isUpperCase(character)) {
                    if(builder.length() > 0) {
                        splits.add(builder.toString());
                        builder = new StringBuilder().append(character);
                    } else splits.add(Character.toString(character));
                } else builder.append(character);
            }
            if(builder.length() > 0) {
                splits.add(builder.toString());
            }
            //try to combine in different manners
            //oreBasaltic MineralSand , ore BasalticMineralSand
            StringBuilder buffer = new StringBuilder();
            for(int i = 0; i < splits.size(); i++) {
                buffer.append(splits.get(i));
                OrePrefix maybePrefix = OrePrefix.getPrefix(buffer.toString()); //ore -> OrePrefix.ore
                String possibleMaterialName = Joiner.on("").join(splits.subList(i + 1, splits.size())); //BasalticMineralSand
                String underscoreName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, possibleMaterialName); //basaltic_mineral_sand
                Material possibleMaterial = Material.MATERIAL_REGISTRY.getObject(underscoreName); //Materials.BasalticSand
                if(maybePrefix != null && possibleMaterial != null) {
                    orePrefix = maybePrefix;
                    material = possibleMaterial;
                    break;
                }
            }
        }
        //finally register item
        if(orePrefix != null && (material != null || orePrefix.isSelfReferencing)) {
            UnificationEntry unificationEntry = new UnificationEntry(orePrefix, material);
            SimpleItemStack simpleItemStack = new SimpleItemStack(event.getOre());
            stackUnificationInfo.put(simpleItemStack, unificationEntry);
            stackUnificationItems.computeIfAbsent(unificationEntry, p -> new ArrayList<>()).add(simpleItemStack);
            orePrefix.processOreRegistration(material);
        }
    }

    @Nullable
    public static MaterialStack getMaterial(ItemStack itemStack) {
        if(itemStack.isEmpty()) return null;
        SimpleItemStack simpleItemStack = new SimpleItemStack(itemStack);
        UnificationEntry entry = stackUnificationInfo.get(simpleItemStack);
        if(entry != null && entry.material != null) return new MaterialStack(entry.material, entry.orePrefix.materialAmount);
        ItemMaterialInfo info = materialUnificationInfo.get(simpleItemStack);
        return info == null ? null : info.material.copy();
    }

    @Nullable
    public static ImmutableList<MaterialStack> getByProducts(ItemStack itemStack) {
        if(itemStack.isEmpty()) return null;
        SimpleItemStack simpleItemStack = new SimpleItemStack(itemStack);
        UnificationEntry entry = stackUnificationInfo.get(simpleItemStack);
        if(entry != null && entry.material != null) return ImmutableList.of(new MaterialStack(entry.material, entry.orePrefix.materialAmount), entry.orePrefix.secondaryMaterial);
        ItemMaterialInfo info = materialUnificationInfo.get(simpleItemStack);
        return info == null ? null : info.byProducts;
    }

    @Nullable
    public static OrePrefix getPrefix(ItemStack itemStack) {
        if(itemStack.isEmpty()) return null;
        SimpleItemStack simpleItemStack = new SimpleItemStack(itemStack);
        UnificationEntry entry = stackUnificationInfo.get(simpleItemStack);
        if(entry != null) return entry.orePrefix;
        return null;
    }

    @Nullable
    public static UnificationEntry getUnificationEntry(ItemStack itemStack) {
        if(itemStack.isEmpty()) return null;
        return stackUnificationInfo.get(new SimpleItemStack(itemStack));
    }

    public static ItemStack getUnificated(ItemStack itemStack) {
        if(itemStack.isEmpty()) return ItemStack.EMPTY;
        UnificationEntry unificationEntry = getUnificationEntry(itemStack);
        if(unificationEntry == null || !stackUnificationItems.containsKey(unificationEntry) || !unificationEntry.orePrefix.isUnificationEnabled)
            return itemStack;
        ArrayList<SimpleItemStack> keys = stackUnificationItems.get(unificationEntry);
        keys.sort(Comparator.comparing(a -> a.item.delegate.name().getResourceDomain()));
        return keys.size() > 0 ? keys.get(0).asItemStack(itemStack.getCount()) : itemStack;
    }

    public static ItemStack get(OrePrefix orePrefix, Material material) {
        return get(orePrefix, material, 1);
    }

    public static ItemStack get(OrePrefix orePrefix, Material material, int stackSize) {
        UnificationEntry unificationEntry = new UnificationEntry(orePrefix, material);
        if(!stackUnificationItems.containsKey(unificationEntry) || !unificationEntry.orePrefix.isUnificationEnabled)
            return ItemStack.EMPTY;
        ArrayList<SimpleItemStack> keys = stackUnificationItems.get(unificationEntry);
        keys.sort(Comparator.comparing(a -> a.item.delegate.name().getResourceDomain()));
        return keys.size() > 0 ? keys.get(0).asItemStack(stackSize) : ItemStack.EMPTY;
    }

    public static ItemStack getGem(GemMaterial material, long materialAmount) {
        ItemStack stack = ItemStack.EMPTY;
        if (materialAmount >= M)
            stack = get(OrePrefix.gem, material, (int) (materialAmount / M));
        if (stack.isEmpty()) {
            if ((materialAmount * 2) % M == 0 || materialAmount >= M * 16)
                stack = get(OrePrefix.gemFlawed, material, (int) ((materialAmount * 2) / M));
            if (materialAmount * 4 >= M)
                stack = get(OrePrefix.gemChipped, material, (int) ((materialAmount * 4) / M));
        }
        return stack;
    }

    public static ItemStack getDust(DustMaterial material, long materialAmount) {
        if (materialAmount <= 0) return ItemStack.EMPTY;
        ItemStack stack = ItemStack.EMPTY;
        if (materialAmount % M == 0 || materialAmount >= M * 16)
            stack = get(OrePrefix.dust, material, (int) (materialAmount / M));
        if (stack.isEmpty() && (materialAmount * 4) % M == 0 || materialAmount >= M * 8)
            stack = get(OrePrefix.dustSmall, material, (int) ((materialAmount * 4) / M));
        if (stack.isEmpty() && (materialAmount * 9) >= M)
            stack = get(OrePrefix.dustTiny, material, (int) ((materialAmount * 9) / M));
        return stack;
    }

    public static ItemStack getIngot(MetalMaterial material, long materialAmount) {
        if (materialAmount <= 0) return ItemStack.EMPTY;
        ItemStack stack = ItemStack.EMPTY;
        if (((materialAmount % (M * 9) == 0 && materialAmount / (M * 9) > 1) || materialAmount >= M * 72))
            stack = get(OrePrefix.block, material, (int) (materialAmount / (M * 9)));
        if (stack.isEmpty() && ((materialAmount % M == 0) || materialAmount >= M * 8))
            stack = get(OrePrefix.ingot, material, (int) (materialAmount / M));
        if (stack.isEmpty() && (materialAmount * 9) >= M)
            stack = get(OrePrefix.nugget, material, (int) ((materialAmount * 9) / M));
        return stack;
    }

    public static ItemStack getDustOrIngot(DustMaterial material, long materialAmount) {
        if (materialAmount <= 0) return ItemStack.EMPTY;
        ItemStack stack = getDust(material, materialAmount);
        if (stack.isEmpty()) stack = getIngot((MetalMaterial) material, materialAmount);
        return stack;
    }
}
