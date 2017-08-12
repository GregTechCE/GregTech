package gregtech.api.recipes;

import gregtech.GT_Mod;
import gregtech.api.GregTech_API;
import gregtech.api.ConfigCategories;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.ore.OrePrefixes;
import gregtech.api.unification.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.type.AbstractSolidMaterial;
import gregtech.api.unification.material.type.DustMaterial;
import gregtech.api.unification.material.type.GemMaterial;
import gregtech.api.unification.material.type.MetalMaterial;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static gregtech.api.GT_Values.L;
import static gregtech.api.GT_Values.M;

/**
 * Class for Automatic Recipe registering.
 */
//TODO Still needs a rewrite
public class RecipeRegistrator {
    /**
     * List of Materials, which are used in the Creation of Sticks. All Rod Materials are automatically added to this List.
     */
    public static final List<Material> ROD_MATERIAL_LIST = new ArrayList<>();
    
    private static final ItemStack MT_1 = new ItemStack(Blocks.DIRT, 1, 0), MT_2 = new ItemStack(Blocks.DIRT, 1, 0);
    
    private static final ItemStack[][] SHAPES_1 = new ItemStack[][] {
            {MT_1, null, MT_1, MT_1, MT_1, MT_1, null, MT_1, null},
            {MT_1, null, MT_1, MT_1, null, MT_1, MT_1, MT_1, MT_1},
            {null, MT_1, null, MT_1, MT_1, MT_1, MT_1, null, MT_1},
            {MT_1, MT_1, MT_1, MT_1, null, MT_1, null, null, null},
            {MT_1, null, MT_1, MT_1, MT_1, MT_1, MT_1, MT_1, MT_1},
            {MT_1, MT_1, MT_1, MT_1, null, MT_1, MT_1, null, MT_1},
            {null, null, null, MT_1, null, MT_1, MT_1, null, MT_1},
            {null, MT_1, null, null, MT_1, null, null, MT_2, null},
            {MT_1, MT_1, MT_1, null, MT_2, null, null, MT_2, null},
            {null, MT_1, null, null, MT_2, null, null, MT_2, null},
            {MT_1, MT_1, null, MT_1, MT_2, null, null, MT_2, null},
            {null, MT_1, MT_1, null, MT_2, MT_1, null, MT_2, null},
            {MT_1, MT_1, null, null, MT_2, null, null, MT_2, null},
            {null, MT_1, MT_1, null, MT_2, null, null, MT_2, null},
            {null, MT_1, null, MT_1, null, null, null, MT_1, MT_2},
            {null, MT_1, null, null, null, MT_1, MT_2, MT_1, null},
            {null, MT_1, null, MT_1, null, MT_1, null, null, MT_2},
            {null, MT_1, null, MT_1, null, MT_1, MT_2, null, null},
            {null, MT_2, null, null, MT_1, null, null, MT_1, null},
            {null, MT_2, null, null, MT_2, null, MT_1, MT_1, MT_1},
            {null, MT_2, null, null, MT_2, null, null, MT_1, null},
            {null, MT_2, null, MT_1, MT_2, null, MT_1, MT_1, null},
            {null, MT_2, null, null, MT_2, MT_1, null, MT_1, MT_1},
            {null, MT_2, null, null, MT_2, null, MT_1, MT_1, null},
            {MT_1, null, null, null, MT_2, null, null, null, MT_2},
            {null, null, MT_1, null, MT_2, null, MT_2, null, null},
            {MT_1, null, null, null, MT_2, null, null, null, null},
            {null, null, MT_1, null, MT_2, null, null, null, null},
            {MT_1, MT_2, null, null, null, null, null, null, null},
            {MT_2, MT_1, null, null, null, null, null, null, null},
            {MT_1, null, null, MT_2, null, null, null, null, null},
            {MT_2, null, null, MT_1, null, null, null, null, null},
            {MT_1, MT_1, MT_1, MT_1, MT_1, MT_1, null, MT_2, null},
            {MT_1, MT_1, null, MT_1, MT_1, MT_2, MT_1, MT_1, null},
            {null, MT_1, MT_1, MT_2, MT_1, MT_1, null, MT_1, MT_1},
            {null, MT_2, null, MT_1, MT_1, MT_1, MT_1, MT_1, MT_1},
            {MT_1, MT_1, MT_1, MT_1, MT_2, MT_1, null, MT_2, null},
            {MT_1, MT_1, null, MT_1, MT_2, MT_2, MT_1, MT_1, null},
            {null, MT_1, MT_1, MT_2, MT_2, MT_1, null, MT_1, MT_1},
            {null, MT_2, null, MT_1, MT_2, MT_1, MT_1, MT_1, MT_1},
            {MT_1, null, null, null, MT_1, null, null, null, null},
            {null, MT_1, null, MT_1, null, null, null, null, null},
            {MT_1, MT_1, null, MT_2, null, MT_1, MT_2, null, null},
            {null, MT_1, MT_1, MT_1, null, MT_2, null, null, MT_2}
    };

    private static final String H = "h", F = "f", I = "I", P = "P", R = "R";
    private static final String[][] SHAPES_A = new String[][]{
            null,
            null,
            null,
            {"Helmet", P + P + P, P + H + P},
            {"ChestPlate", P + H + P, P + P + P, P + P + P},
            {"Pants", P + P + P, P + H + P, P + " " + P},
            {"Boots", P + " " + P, P + H + P},
            {"Sword", " " + P + " ", F + P + H, " " + R + " "},
            {"Pickaxe", P + I + I, F + R + H, " " + R + " "},
            {"Shovel", F + P + H, " " + R + " ", " " + R + " "},
            {"Axe", P + I + H, P + R + " ", F + R + " "},
            {"Axe", P + I + H, P + R + " ", F + R + " "},
            {"Hoe", P + I + H, F + R + " ", " " + R + " "},
            {"Hoe", P + I + H, F + R + " ", " " + R + " "},
            {"Sickle", " " + P + " ", P + F + " ", H + P + R},
            {"Sickle", " " + P + " ", P + F + " ", H + P + R},
            {"Sickle", " " + P + " ", P + F + " ", H + P + R},
            {"Sickle", " " + P + " ", P + F + " ", H + P + R},
            {"Sword", " " + R + " ", F + P + H, " " + P + " "},
            {"Pickaxe", " " + R + " ", F + R + H, P + I + I},
            {"Shovel", " " + R + " ", " " + R + " ", F + P + H},
            {"Axe", F + R + " ", P + R + " ", P + I + H},
            {"Axe", F + R + " ", P + R + " ", P + I + H},
            {"Hoe", " " + R + " ", F + R + " ", P + I + H},
            {"Hoe", " " + R + " ", F + R + " ", P + I + H},
            {"Spear", P + H + " ", F + R + " ", " " + " " + R},
            {"Spear", P + H + " ", F + R + " ", " " + " " + R},
            {"Knive", H + P, R + F},
            {"Knive", F + H, P + R},
            {"Knive", F + H, P + R},
            {"Knive", P + F, R + H},
            {"Knive", P + F, R + H},
            null,
            null,
            null,
            null,
            {"WarAxe", P + P + P, P + R + P, F + R + H},
            null,
            null,
            null,
            {"Shears", H + P, P + F},
            {"Shears", H + P, P + F},
            {"Scythe", I + P + H, R + F + P, R + " " + " "},
            {"Scythe", H + P + I, P + F + R, " " + " " + R}
    };

    public static void registerMaterialRecycling(ItemStack stack, Material material, long materialAmount, MaterialStack byproduct) {
        if (!GT_Utility.isStackValid(stack)) return;

        if (byproduct != null) {
            byproduct = byproduct.clone();
            byproduct.mAmount /= stack.stackSize;
        }

        OreDictionaryUnifier.addItemData(GT_Utility.copyAmount(1, stack), new ItemMaterialInfo(material, materialAmount / stack.stackSize, byproduct));
    }

    public static void registerMaterialRecycling(ItemStack stack, ItemMaterialInfo data) {
        if (!GT_Utility.isStackValid(stack)
                || GT_Utility.areStacksEqual(new ItemStack(Items.BLAZE_ROD), stack)
                || data == null
                || !data.hasValidMaterialData()
                || data.mMaterial.mAmount <= 0
                || GT_Utility.getFluidForFilledItem(stack, false) != null)
            return;

//        registerReverseMacerating(GT_Utility.copyAmount(1, stack), data, data.mPrefix == null);
        registerReverseSmelting(GT_Utility.copyAmount(1, stack), data.mMaterial.mMaterial, data.mMaterial.mAmount, true);
        registerReverseFluidSmelting(GT_Utility.copyAmount(1, stack), data.mMaterial.mMaterial, data.mMaterial.mAmount, data.getByProduct(0));
        registerReverseArcSmelting(GT_Utility.copyAmount(1, stack), data);
    }

    /**
     * @param stack          the stack to be recycled.
     * @param material       the Material.
     * @param materialAmount the amount of it in Material Units.
     */
    public static void registerReverseFluidSmelting(ItemStack stack, DustMaterial material, long materialAmount, MaterialStack byproduct) {
        if (stack == null
                || material == null
                || material.smeltInto.hasFluid()
                || !material.contains(AbstractSolidMaterial.MatFlags.SMELTING_TO_FLUID)
                || (L * materialAmount) / (M * stack.stackSize) <= 0)
            return;

        ItemMaterialInfo data = OreDictionaryUnifier.getItemData(stack);
        boolean hide = stack.getUnlocalizedName().startsWith("gt.blockmachines") && (GT_Mod.gregtechproxy.mHideRecyclingRecipes);
        if (GT_Mod.gregtechproxy.mHideRecyclingRecipes && data != null && data.hasValidPrefixData() && !(data.mPrefix == OrePrefixes.dust || data.mPrefix == OrePrefixes.ingot || data.mPrefix == OrePrefixes.block | data.mPrefix == OrePrefixes.plate)) {
            hide = true;
        }

        RecipeBuilder.NotConsumableInputRecipeBuilder recipeBuilder =
                RecipeMap.FLUID_EXTRACTION_RECIPES.recipeBuilder()
                    .inputs(GT_Utility.copyAmount(1, stack))
                    .fluidOutputs(material.smeltInto.getFluid((int) ((L * materialAmount) / (M * stack.stackSize))))
                    .duration((int) Math.max(1, (24 * materialAmount) / M))
                    .EUt(Math.max(8, (int) Math.sqrt(2 * material.smeltInto.getFluid(1).getFluid().getTemperature())));

        if (byproduct != null) {
            if (byproduct.mMaterial.smeltInto.hasFluid() || !(byproduct.mMaterial instanceof MetalMaterial)) {
                if (byproduct.mMaterial.contains(Material.MatFlags.FLAMMABLE)) {
                    recipeBuilder.outputs(OreDictionaryUnifier.getDust(Materials.Ash, byproduct.mAmount / 2));
                } else if (byproduct.mMaterial.contains(Material.MatFlags.UNBURNABLE)) {
                    recipeBuilder.outputs(OreDictionaryUnifier.getDustOrIngot(byproduct.mMaterial.smeltInto, byproduct.mAmount));
                }
            } else {
                recipeBuilder.outputs(OreDictionaryUnifier.getIngotOrDust(byproduct.mMaterial.smeltInto, byproduct.mAmount));
            }
        }

        if (hide) {
            recipeBuilder.hidden();
        }

        recipeBuilder.buildAndRegister();
    }

    /**
     * @param stack             the stack to be recycled.
     * @param material          the Material.
     * @param materialAmount    the amount of it in Material Units.
     * @param allowAlloySmelter if it is allowed to be recycled inside the Alloy Smelter.
     */
    public static void registerReverseSmelting(ItemStack stack, DustMaterial material, long materialAmount, boolean allowAlloySmelter) {
        if (stack == null || material == null || materialAmount <= 0 || material.smeltInto == null || (materialAmount > M && material instanceof MetalMaterial)))
            return;

        materialAmount /= stack.stackSize;
        if (material == Materials.Naquadah || material == Materials.NaquadahEnriched) return;

        boolean hide = (material != Materials.Iron) && (GT_Mod.gregtechproxy.mHideRecyclingRecipes);
        if (allowAlloySmelter)
            ModHandler.addSmeltingAndAlloySmeltingRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.getIngot(material.smeltInto, materialAmount), hide);
        else
            ModHandler.addSmeltingRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.getIngot(material.smeltInto, materialAmount));
    }

    public static void registerReverseArcSmelting(ItemStack stack, Material material, long materialAmount, MaterialStack byProduct01, MaterialStack byProduct02, MaterialStack byProduct03) {
        registerReverseArcSmelting(stack, new ItemMaterialInfo(material == null ? null : new MaterialStack(material, materialAmount), byProduct01, byProduct02, byProduct03));
    }

    public static void registerReverseArcSmelting(ItemStack stack, ItemMaterialInfo data) {
        if (stack == null || data == null) return;
        data = new ItemMaterialInfo(data);

        if (!data.hasValidMaterialData()) return;
        boolean iron = false;


        for (MaterialStack material : data.getAllMaterialStacks()) {
            if (material.mMaterial == Materials.Iron||material.mMaterial == Materials.Copper ||
                    material.mMaterial == Materials.WroughtIron||material.mMaterial == Materials.AnnealedCopper) iron = true;

            if (material.mMaterial.contains(Material.MatFlags.UNBURNABLE)) {
                material.mMaterial = material.mMaterial.smeltInto.mArcSmeltInto;
                continue;
            }
            if (material.mMaterial.contains(Material.MatFlags.EXPLOSIVE)) {
                material.mMaterial = Materials.Ash;
                material.mAmount /= 4;
                continue;
            }
            if (material.mMaterial.contains(Material.MatFlags.FLAMMABLE)) {
                material.mMaterial = Materials.Ash;
                material.mAmount /= 2;
                continue;
            }
            if (material.mMaterial.contains(Material.MatFlags.NO_SMELTING)) {
                material.mAmount = 0;
                continue;
            }
            if (material.mMaterial instanceof MetalMaterial) {
            	if(GT_Mod.gregtechproxy.mArcSmeltIntoAnnealed){
                material.mMaterial = material.mMaterial.smeltInto.mArcSmeltInto;
            	}else{
            		material.mMaterial = material.mMaterial.smeltInto.mSmeltInto;
            	}
                continue;
            }
            material.mAmount = 0;
        }

        data = new ItemMaterialInfo(data);
        if (data.mByProducts.length > 3) for (MaterialStack material : data.getAllMaterialStacks()){
            if (material.mMaterial == Materials.Ash) material.mAmount = 0;
        }

        data = new ItemMaterialInfo(data);

        if (!data.hasValidMaterialData()) return;

        long amount = 0;
        for (MaterialStack material : data.getAllMaterialStacks())
            amount += material.mAmount * material.mMaterial.getMass();

        boolean hide = !iron && GT_Mod.gregtechproxy.mHideRecyclingRecipes;
        RA.addArcFurnaceRecipe(stack, new ItemStack[]{OreDictionaryUnifier.getIngotOrDust(data.mMaterial), OreDictionaryUnifier.getIngotOrDust(data.getByProduct(0)), OreDictionaryUnifier.getIngotOrDust(data.getByProduct(1)), OreDictionaryUnifier.getIngotOrDust(data.getByProduct(2))}, null, (int) Math.max(16, amount / M), 96, hide);
    }

    public static void registerReverseMacerating(ItemStack stack, Material material, long materialAmount, MaterialStack byProduct01, MaterialStack byProduct02, MaterialStack byProduct03, boolean allowHammer) {
        registerReverseMacerating(stack, new ItemMaterialInfo(material == null ? null : new MaterialStack(material, materialAmount), byProduct01, byProduct02, byProduct03), allowHammer);
    }

    public static void registerReverseMacerating(ItemStack stack, ItemMaterialInfo data, boolean allowHammer) {
        if (stack == null || data == null) return;
        data = new ItemMaterialInfo(data);

        if (!data.hasValidMaterialData()) return;

        for (MaterialStack material : data.getAllMaterialStacks())
            material.mMaterial = material.mMaterial.macerateInto;

        data = new ItemMaterialInfo(data);

        if (!data.hasValidMaterialData()) return;

        long amount = 0;
        for (MaterialStack material : data.getAllMaterialStacks()) {
            amount += material.mAmount * material.mMaterial.getMass();
        }
        boolean hide = (data.mMaterial.mMaterial != Materials.Iron) && (GT_Mod.gregtechproxy.mHideRecyclingRecipes);
        RA.addPulveriserRecipe(stack, new ItemStack[]{OreDictionaryUnifier.getDust(data.mMaterial), OreDictionaryUnifier.getDust(data.getByProduct(0)), OreDictionaryUnifier.getDust(data.getByProduct(1)), OreDictionaryUnifier.getDust(data.getByProduct(2))}, null, data.mMaterial.mMaterial == Materials.Marble ? 1 : (int) Math.max(16, amount / M), 4, hide);

        if (allowHammer) {
            for (MaterialStack material : data.getAllMaterialStacks()) {
                if (material.mMaterial instanceof GemMaterial) {
                    RA.addForgeHammerRecipe(GT_Utility.copyAmount(1, stack), OreDictionaryUnifier.getDust(data.mMaterial), 200, 32);
                    break;
                }
            }
        }
        ItemStack dust = OreDictionaryUnifier.getDust(data.mMaterial);
        if (dust != null) {
            ModHandler.addPulverisationRecipe(GT_Utility.copyAmount(1, stack), dust, OreDictionaryUnifier.getDust(data.getByProduct(0)), 100, OreDictionaryUnifier.getDust(data.getByProduct(1)), 100, true);
        }
    }

    /**
     * You give this Function a Material and it will scan almost everything for adding recycling Recipes
     *
     * @param stack             a Material, for example an Ingot or a Gem.
     * @param recipeReplacing allows to replace the Recipe with a Plate variant
     */
    public static synchronized void registerUsagesForMaterials(ItemStack stack, String plate, boolean recipeReplacing) {
        if (stack == null) return;
        stack = GT_Utility.copy(stack);
        ItemStack itemStack;
        ItemMaterialInfo data = OreDictionaryUnifier.getItemData(stack);
        if (data == null || data.mPrefix != OrePrefixes.ingot) plate = null;
        if (plate != null && OreDictionaryUnifier.getFirstOre(plate, 1) == null) plate = null;

        MT_1.setItem(stack.getItem());
        MT_1.stackSize = 1;
        Items.FEATHER.setDamage(MT_1, Items.FEATHER.getDamage(stack));

        MT_2.setItem(new ItemStack(Blocks.DIRT).getItem());
        MT_2.stackSize = 1;
        Items.FEATHER.setDamage(MT_2, 0);

        for (ItemStack[] recipe : SHAPES_1) {
            int amount = 0;
            for (ItemStack mat : recipe) {
                if (mat == MT_1) amount++;
            }
            if (data != null && data.hasValidPrefixMaterialData())
                for (ItemStack crafted : ModHandler.getRecipeOutputs(recipe)) {
                    OreDictionaryUnifier.addItemData(crafted, new ItemMaterialInfo(data.mMaterial.mMaterial, data.mMaterial.mAmount * amount));
                }
        }

        for (Material material : ROD_MATERIAL_LIST) {
            ItemStack Mt2 = OreDictionaryUnifier.get(OrePrefixes.stick, material, 1);
            if (Mt2 != null) {
                MT_2.setItem(Mt2.getItem());
                MT_2.stackSize = 1;
                Items.FEATHER.setDamage(MT_2, Items.FEATHER.getDamage(Mt2));

                for (int i = 0; i < SHAPES_1.length; i++) {
                    ItemStack[] recipe = SHAPES_1[i];

                    int amount1 = 0, amount2 = 0;
                    for (ItemStack mat : recipe) {
                        if (mat == MT_1) amount1++;
                        if (mat == MT_2) amount2++;
                    }
                    for (ItemStack crafted : ModHandler.getVanillyToolRecipeOutputs(recipe)) {
                        if (data != null && data.hasValidPrefixMaterialData())
                            OreDictionaryUnifier.addItemData(crafted, new ItemMaterialInfo(data.mMaterial.mMaterial, data.mMaterial.mAmount * amount1, new MaterialStack(material, OrePrefixes.stick.mMaterialAmount * amount2)));

                        if (recipeReplacing && plate != null && SHAPES_A[i] != null && SHAPES_A[i].length > 1) {
                            assert data != null;
                            if (GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.recipereplacements, data.mMaterial.mMaterial + "." + SHAPES_A[i][0], true)) {
                                if (null != (itemStack = ModHandler.removeRecipe(recipe))) {
                                    switch (SHAPES_A[i].length) {
                                        case 2:
                                            ModHandler.addCraftingRecipe(itemStack, ModHandler.RecipeBits.BUFFERED, new Object[]{SHAPES_A[i][1], P.charAt(0), plate, R.charAt(0), OrePrefixes.stick.get(material), I.charAt(0), data});
                                            break;
                                        case 3:
                                            ModHandler.addCraftingRecipe(itemStack, ModHandler.RecipeBits.BUFFERED, new Object[]{SHAPES_A[i][1], SHAPES_A[i][2], P.charAt(0), plate, R.charAt(0), OrePrefixes.stick.get(material), I.charAt(0), data});
                                            break;
                                        default:
                                            ModHandler.addCraftingRecipe(itemStack, ModHandler.RecipeBits.BUFFERED, new Object[]{SHAPES_A[i][1], SHAPES_A[i][2], SHAPES_A[i][3], P.charAt(0), plate, R.charAt(0), OrePrefixes.stick.get(material), I.charAt(0), data});
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}