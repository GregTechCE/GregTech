package gregtech.common.items;

import gregtech.api.ConfigCategories;
import gregtech.api.GT_Values;
import gregtech.api.GregTech_API;
import gregtech.api.items.OreDictNames;
import gregtech.api.unification.Dyes;
import gregtech.api.unification.OreDictionaryUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.items.GT_MetaGenerated_Item_X32;
import gregtech.api.items.ItemList;
import gregtech.api.objects.GT_MultiTexture;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.util.*;
import gregtech.common.covers.*;
import gregtech.common.items.behaviors.*;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.ItemName;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Loader;

import java.util.Collection;
import java.util.List;

public class GT_MetaGenerated_Item_01 extends GT_MetaGenerated_Item_X32 {
        public static GT_MetaGenerated_Item_01 INSTANCE;
        private final static String aTextArrow = "  A"; private final static String aTextStick = " S "; private final static String aTextFeather = "F  ";
        private final static String aTextEmptyRow = "   "; private final static String aTextShape = " P ";
        private final String mToolTipPurify = GT_LanguageManager.addStringLocalization("metaitem.01.tooltip.purify", "Throw into Cauldron to get clean Dust");

        public GT_MetaGenerated_Item_01() {
                super("metaitem.01", OrePrefix.dustTiny, OrePrefix.dustSmall, OrePrefix.dust, OrePrefix.dustImpure, OrePrefix.dustPure, OrePrefix.crushed, OrePrefix.crushedPurified, OrePrefix.crushedCentrifuged, OrePrefix.gem, OrePrefix.nugget, null, OrePrefix.ingot, OrePrefix.ingotHot, OrePrefix.ingotDouble, OrePrefix.ingotTriple, OrePrefix.ingotQuadruple, OrePrefix.ingotQuintuple, OrePrefix.plate, OrePrefix.plateDouble, OrePrefix.plateTriple, OrePrefix.plateQuadruple, OrePrefix.plateQuintuple, OrePrefix.plateDense, OrePrefix.stick, OrePrefix.lens, OrePrefix.round, OrePrefix.bolt, OrePrefix.screw, OrePrefix.ring, OrePrefix.foil, OrePrefix.cell, OrePrefix.cellPlasma);
                INSTANCE = this;

                //it's here because OrePrefix.cell is handled by gt.metaitem.01
                ItemList.Cell_Empty.set(OreDictionaryUnifier.get(OrePrefix.cell, Materials.Empty, 1));
                ItemList.Cell_Air.set(OreDictionaryUnifier.get(OrePrefix.cell, Materials.Air, 1));
                ItemList.Cell_Water.set(OreDictionaryUnifier.get(OrePrefix.cell, Materials.Water, 1));
                ItemList.Cell_Lava.set(OreDictionaryUnifier.get(OrePrefix.cell, Materials.Lava, 1));

                OrePrefix.cellPlasma.mContainerItem = ItemList.Cell_Empty.get(1);
                OrePrefix.cell.mContainerItem = ItemList.Cell_Empty.get(1);

                int tLastID = 0;

                setBurnValue(17000 + Materials.Wood.mMetaItemSubID, 1600);
                OreDictionaryUnifier.addToBlacklist(new ItemStack(this, 1, 17000 + Materials.Wood.mMetaItemSubID));
                GT_ModHandler.addCompressionRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 8L), new ItemStack(this, 1, 17000 + Materials.Wood.mMetaItemSubID));
                GregTech_API.registerCover(new ItemStack(this, 1, 17000 + Materials.Wood.mMetaItemSubID), new GT_RenderedTexture(Textures.BlockIcons.COVER_WOOD_PLATE), null);

                ItemStack tStack = new ItemStack(this, 1, 17000 + Materials.Wood.mMetaItemSubID);
                tStack.setStackDisplayName("The holy Planks of Sengir");
                GT_Utility.ItemNBT.addEnchantment(tStack, Enchantments.SMITE, 10);
                GT_ModHandler.addCraftingRecipe(tStack, GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"XXX", "XDX", "XXX", 'X', OrePrefix.gem.get(Materials.NetherStar), 'D', new ItemStack(Blocks.DRAGON_EGG, 1, 32767)});

                ItemList.Credit_Greg_Copper.set(addItem(tLastID = 0, "Copper GT Credit", "0.125 Credits"));
                ItemList.Credit_Greg_Cupronickel.set(addItem(tLastID = 1, "Cupronickel GT Credit", "1 Credit", new Object[]{new ItemMaterialInfo(Materials.Cupronickel, 907200L)}));
                ItemList.Credit_Greg_Silver.set(addItem(tLastID = 2, "Silver GT Credit", "8 Credits", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 1L)}));
                ItemList.Credit_Greg_Gold.set(addItem(tLastID = 3, "Gold GT Credit", "64 Credits"));
                ItemList.Credit_Greg_Platinum.set(addItem(tLastID = 4, "Platinum GT Credit", "512 Credits"));
                ItemList.Credit_Greg_Osmium.set(addItem(tLastID = 5, "Osmium GT Credit", "4096 Credits"));
                ItemList.Credit_Greg_Naquadah.set(addItem(tLastID = 6, "Naquadah GT Credit", "32768 Credits"));
                ItemList.Credit_Greg_Neutronium.set(addItem(tLastID = 7, "Neutronium GT Credit", "262144 Credits"));
                ItemList.Coin_Gold_Ancient.set(addItem(tLastID = 8, "Ancient Gold Coin", "Found in ancient Ruins", new Object[]{new ItemMaterialInfo(Materials.Gold, 907200L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 8L)}));
                ItemList.Coin_Doge.set(addItem(tLastID = 9, "Doge Coin", "wow much coin how money so crypto plz mine v rich very currency wow", new Object[]{new ItemMaterialInfo(Materials.Brass, 907200L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 1L)}));
                ItemList.Coin_Chocolate.set(addItem(tLastID = 10, "Chocolate Coin", "Wrapped in Gold", new Object[]{new ItemMaterialInfo(Materials.Gold, OrePrefix.foil.mMaterialAmount), new GT_FoodStat(1, 0.1F, EnumAction.EAT, OreDictionaryUnifier.get(OrePrefix.foil, Materials.Gold, 1L), true, false, false, new int[]{Potion.getIdFromPotion(MobEffects.SPEED), 200, 1, 100})}));
                ItemList.Credit_Copper.set(addItem(tLastID = 11, "Industrial Copper Credit", "0.125 Credits"));

                ItemList.Credit_Silver.set(addItem(tLastID = 13, "Industrial Silver Credit", "8 Credits", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.LUCRUM, 1L)}));
                ItemList.Credit_Gold.set(addItem(tLastID = 14, "Industrial Gold Credit", "64 Credits"));
                ItemList.Credit_Platinum.set(addItem(tLastID = 15, "Industrial Platinum Credit", "512 Credits"));
                ItemList.Credit_Osmium.set(addItem(tLastID = 16, "Industrial Osmium Credit", "4096 Credits"));

                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Coin_Chocolate.get(1L), new Object[]{OrePrefix.dust.get(Materials.Cocoa), OrePrefix.dust.get(Materials.Milk), OrePrefix.dust.get(Materials.Sugar), OrePrefix.foil.get(Materials.Gold)});

                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Copper.get(8L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Iron});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Iron.get(8L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Silver});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Silver.get(8L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Gold});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Gold.get(8L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Platinum});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Platinum.get(8L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Osmium});

                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Iron.get(1L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Copper, ItemList.Credit_Copper, ItemList.Credit_Copper, ItemList.Credit_Copper, ItemList.Credit_Copper, ItemList.Credit_Copper, ItemList.Credit_Copper, ItemList.Credit_Copper});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Silver.get(1L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Iron, ItemList.Credit_Iron, ItemList.Credit_Iron, ItemList.Credit_Iron, ItemList.Credit_Iron, ItemList.Credit_Iron, ItemList.Credit_Iron, ItemList.Credit_Iron});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Gold.get(1L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Silver, ItemList.Credit_Silver, ItemList.Credit_Silver, ItemList.Credit_Silver, ItemList.Credit_Silver, ItemList.Credit_Silver, ItemList.Credit_Silver, ItemList.Credit_Silver});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Platinum.get(1L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Gold, ItemList.Credit_Gold, ItemList.Credit_Gold, ItemList.Credit_Gold, ItemList.Credit_Gold, ItemList.Credit_Gold, ItemList.Credit_Gold, ItemList.Credit_Gold});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Osmium.get(1L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Platinum, ItemList.Credit_Platinum, ItemList.Credit_Platinum, ItemList.Credit_Platinum, ItemList.Credit_Platinum, ItemList.Credit_Platinum, ItemList.Credit_Platinum, ItemList.Credit_Platinum});

                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Copper.get(8L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Greg_Cupronickel});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Cupronickel.get(8L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Greg_Silver});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Silver.get(8L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Greg_Gold});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Gold.get(8L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Greg_Platinum});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Platinum.get(8L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Greg_Osmium});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Osmium.get(8L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Greg_Naquadah});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Naquadah.get(8L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Greg_Neutronium});

                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Cupronickel.get(1L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Greg_Copper, ItemList.Credit_Greg_Copper, ItemList.Credit_Greg_Copper, ItemList.Credit_Greg_Copper, ItemList.Credit_Greg_Copper, ItemList.Credit_Greg_Copper, ItemList.Credit_Greg_Copper, ItemList.Credit_Greg_Copper});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Silver.get(1L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Greg_Cupronickel, ItemList.Credit_Greg_Cupronickel, ItemList.Credit_Greg_Cupronickel, ItemList.Credit_Greg_Cupronickel, ItemList.Credit_Greg_Cupronickel, ItemList.Credit_Greg_Cupronickel, ItemList.Credit_Greg_Cupronickel, ItemList.Credit_Greg_Cupronickel});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Gold.get(1L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Greg_Silver, ItemList.Credit_Greg_Silver, ItemList.Credit_Greg_Silver, ItemList.Credit_Greg_Silver, ItemList.Credit_Greg_Silver, ItemList.Credit_Greg_Silver, ItemList.Credit_Greg_Silver, ItemList.Credit_Greg_Silver});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Platinum.get(1L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Greg_Gold, ItemList.Credit_Greg_Gold, ItemList.Credit_Greg_Gold, ItemList.Credit_Greg_Gold, ItemList.Credit_Greg_Gold, ItemList.Credit_Greg_Gold, ItemList.Credit_Greg_Gold, ItemList.Credit_Greg_Gold});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Osmium.get(1L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Greg_Platinum, ItemList.Credit_Greg_Platinum, ItemList.Credit_Greg_Platinum, ItemList.Credit_Greg_Platinum, ItemList.Credit_Greg_Platinum, ItemList.Credit_Greg_Platinum, ItemList.Credit_Greg_Platinum, ItemList.Credit_Greg_Platinum});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Naquadah.get(1L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Greg_Osmium, ItemList.Credit_Greg_Osmium, ItemList.Credit_Greg_Osmium, ItemList.Credit_Greg_Osmium, ItemList.Credit_Greg_Osmium, ItemList.Credit_Greg_Osmium, ItemList.Credit_Greg_Osmium, ItemList.Credit_Greg_Osmium});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Credit_Greg_Neutronium.get(1L), GT_ModHandler.RecipeBits.KEEPNBT | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Credit_Greg_Naquadah, ItemList.Credit_Greg_Naquadah, ItemList.Credit_Greg_Naquadah, ItemList.Credit_Greg_Naquadah, ItemList.Credit_Greg_Naquadah, ItemList.Credit_Greg_Naquadah, ItemList.Credit_Greg_Naquadah, ItemList.Credit_Greg_Naquadah});

                ItemList.Component_Minecart_Wheels_Iron.set(addItem(tLastID = 100, "Iron Minecart Wheels", "To get things rolling", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L)}));
                ItemList.Component_Minecart_Wheels_Steel.set(addItem(tLastID = 101, "Steel Minecart Wheels", "To get things rolling", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L)}));

                GT_ModHandler.addCraftingRecipe(ItemList.Component_Minecart_Wheels_Iron.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{" h ", "RSR", " w ", 'R', OrePrefix.ring.get(Materials.AnyIron), 'S', OrePrefix.stick.get(Materials.AnyIron)});
                GT_ModHandler.addCraftingRecipe(ItemList.Component_Minecart_Wheels_Steel.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{" h ", "RSR", " w ", 'R', OrePrefix.ring.get(Materials.Steel), 'S', OrePrefix.stick.get(Materials.Steel)});

                ItemList.Arrow_Head_Glass_Emtpy.set(addItem(tLastID = 200, "Empty Glass Arrow Head", "Fill with Potions before use", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L)}));
                ItemList.Arrow_Head_Glass_Poison.set(addItem(tLastID = 201, "Poison Glass Arrow Head", "Glass Arrow filled with Poison", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Head_Glass_Poison_Long.set(addItem(tLastID = 202, "Poison Glass Arrow Head", "Glass Arrow filled with stretched Poison", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Head_Glass_Poison_Strong.set(addItem(tLastID = 203, "Poison Glass Arrow Head", "Glass Arrow filled with strong Poison", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Head_Glass_Slowness.set(addItem(tLastID = 204, "Slowness Glass Arrow Head", "Glass Arrow filled with Laming Brew", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Head_Glass_Slowness_Long.set(addItem(tLastID = 205, "Slowness Glass Arrow Head", "Glass Arrow filled with stretched Laming Brew", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Head_Glass_Weakness.set(addItem(tLastID = 206, "Weakness Glass Arrow Head", "Glass Arrow filled with Weakening Brew", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Head_Glass_Weakness_Long.set(addItem(tLastID = 207, "Weakness Glass Arrow Head", "Glass Arrow filled with stretched Weakening Brew", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Head_Glass_Holy_Water.set(addItem(tLastID = 208, "Holy Water Glass Arrow Head", "Glass Arrow filled with Holy Water", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AURAM, 1L)}));

                ItemList.Arrow_Wooden_Glass_Emtpy.set(addItem(tLastID = 225, "Regular Glass Vial Arrow", "Empty Glass Arrow", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L)}));
                ItemList.Arrow_Wooden_Glass_Poison.set(addItem(tLastID = 226, "Regular Poison Arrow", "Glass Arrow filled with Poison", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Wooden_Glass_Poison_Long.set(addItem(tLastID = 227, "Regular Poison Arrow", "Glass Arrow filled with stretched Poison", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Wooden_Glass_Poison_Strong.set(addItem(tLastID = 228, "Regular Poison Arrow", "Glass Arrow filled with strong Poison", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Wooden_Glass_Slowness.set(addItem(tLastID = 229, "Regular Slowness Arrow", "Glass Arrow filled with Laming Brew", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Wooden_Glass_Slowness_Long.set(addItem(tLastID = 230, "Regular Slowness Arrow", "Glass Arrow filled with stretched Laming Brew", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Wooden_Glass_Weakness.set(addItem(tLastID = 231, "Regular Weakness Arrow", "Glass Arrow filled with Weakening Brew", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Wooden_Glass_Weakness_Long.set(addItem(tLastID = 232, "Regular Weakness Arrow", "Glass Arrow filled with stretched Weakening Brew", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Wooden_Glass_Holy_Water.set(addItem(tLastID = 233, "Regular Holy Water Arrow", "Glass Arrow filled with Holy Water", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AURAM, 1L)}));

                ItemList.Arrow_Plastic_Glass_Emtpy.set(addItem(tLastID = 250, "Light Glass Vial Arrow", "Empty Glass Arrow", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L)}));
                ItemList.Arrow_Plastic_Glass_Poison.set(addItem(tLastID = 251, "Light Poison Arrow", "Glass Arrow filled with Poison", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Plastic_Glass_Poison_Long.set(addItem(tLastID = 252, "Light Poison Arrow", "Glass Arrow filled with stretched Poison", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Plastic_Glass_Poison_Strong.set(addItem(tLastID = 253, "Light Poison Arrow", "Glass Arrow filled with strong Poison", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Plastic_Glass_Slowness.set(addItem(tLastID = 254, "Light Slowness Arrow", "Glass Arrow filled with Laming Brew", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Plastic_Glass_Slowness_Long.set(addItem(tLastID = 255, "Light Slowness Arrow", "Glass Arrow filled with stretched Laming Brew", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Plastic_Glass_Weakness.set(addItem(tLastID = 256, "Light Weakness Arrow", "Glass Arrow filled with Weakening Brew", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Plastic_Glass_Weakness_Long.set(addItem(tLastID = 257, "Light Weakness Arrow", "Glass Arrow filled with stretched Weakening Brew", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VENENUM, 1L)}));
                ItemList.Arrow_Plastic_Glass_Holy_Water.set(addItem(tLastID = 258, "Light Holy Water Arrow", "Glass Arrow filled with Holy Water", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TELUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AURAM, 1L)}));

                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Emtpy.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Emtpy, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Wood)});
                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Poison.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Poison, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Wood)});
                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Poison_Long.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Poison_Long, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Wood)});
                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Poison_Strong.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Poison_Strong, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Wood)});
                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Slowness.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Slowness, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Wood)});
                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Slowness_Long.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Slowness_Long, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Wood)});
                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Weakness.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Weakness, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Wood)});
                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Weakness_Long.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Weakness_Long, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Wood)});
                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Wooden_Glass_Holy_Water.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Holy_Water, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Wood)});

                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Emtpy.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Emtpy, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Plastic)});
                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Poison.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Poison, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Plastic)});
                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Poison_Long.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Poison_Long, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Plastic)});
                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Poison_Strong.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Poison_Strong, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Plastic)});
                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Slowness.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Slowness, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Plastic)});
                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Slowness_Long.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Slowness_Long, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Plastic)});
                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Weakness.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Weakness, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Plastic)});
                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Weakness_Long.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Weakness_Long, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Plastic)});
                GT_ModHandler.addCraftingRecipe(ItemList.Arrow_Plastic_Glass_Holy_Water.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextArrow, aTextStick, aTextFeather, 'A', ItemList.Arrow_Head_Glass_Holy_Water, 'F', OreDictNames.craftingFeather, 'S', OrePrefix.stick.get(Materials.Plastic)});

                ItemList.Shape_Empty.set(addItem(tLastID = 300, "Empty Shape Plate", "Raw Plate to make Molds and Extruder Shapes", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L)}));

                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Empty.get(1L), GT_ModHandler.RecipeBits.MIRRORED | GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"hf", "PP", "PP", 'P', OrePrefix.plate.get(Materials.Steel)});

                ItemList.Shape_Mold_Plate.set(addItem(tLastID = 301, "Mold (Plate)", "Mold for making Plates"));
                ItemList.Shape_Mold_Casing.set(addItem(tLastID = 302, "Mold (Casing)", "Mold for making Item Casings"));
                ItemList.Shape_Mold_Gear.set(addItem(tLastID = 303, "Mold (Gear)", "Mold for making Gears"));
                ItemList.Shape_Mold_Credit.set(addItem(tLastID = 304, "Mold (Coinage)", "Secure Mold for making Coins (Don't lose it!)"));
                ItemList.Shape_Mold_Bottle.set(addItem(tLastID = 305, "Mold (Bottle)", "Mold for making Bottles"));
                ItemList.Shape_Mold_Ingot.set(addItem(tLastID = 306, "Mold (Ingot)", "Mold for making Ingots"));
                ItemList.Shape_Mold_Ball.set(addItem(tLastID = 307, "Mold (Ball)", "Mold for making Balls"));
                ItemList.Shape_Mold_Block.set(addItem(tLastID = 308, "Mold (Block)", "Mold for making Blocks"));
                ItemList.Shape_Mold_Nugget.set(addItem(tLastID = 309, "Mold (Nuggets)", "Mold for making Nuggets"));
                ItemList.Shape_Mold_Bun.set(addItem(tLastID = 310, "Mold (Buns)", "Mold for shaping Buns"));
                ItemList.Shape_Mold_Bread.set(addItem(tLastID = 311, "Mold (Bread)", "Mold for shaping Breads"));
                ItemList.Shape_Mold_Baguette.set(addItem(tLastID = 312, "Mold (Baguette)", "Mold for shaping Baguettes"));
                ItemList.Shape_Mold_Cylinder.set(addItem(tLastID = 313, "Mold (Cylinder)", "Mold for shaping Cylinders"));
                ItemList.Shape_Mold_Anvil.set(addItem(tLastID = 314, "Mold (Anvil)", "Mold for shaping Anvils"));
                ItemList.Shape_Mold_Name.set(addItem(tLastID = 315, "Mold (Name)", "Mold for naming Items (rename Mold with Anvil)"));
                ItemList.Shape_Mold_Arrow.set(addItem(tLastID = 316, "Mold (Arrow Head)", "Mold for making Arrow Heads"));
                ItemList.Shape_Mold_Gear_Small.set(addItem(tLastID = 317, "Mold (Small Gear)", "Mold for making small Gears"));

                GT_ModHandler.removeRecipe(new ItemStack(Blocks.GLASS), null, new ItemStack(Blocks.GLASS), null, new ItemStack(Blocks.GLASS));

                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Credit.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"h  ", aTextShape, aTextEmptyRow, 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Plate.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{" h ", aTextShape, aTextEmptyRow, 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Casing.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"  h", aTextShape, aTextEmptyRow, 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Gear.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextEmptyRow, " Ph", aTextEmptyRow, 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Bottle.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextEmptyRow, aTextShape, "  h", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Ingot.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextEmptyRow, aTextShape, " h ", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Ball.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextEmptyRow, aTextShape, "h  ", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Block.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextEmptyRow, "hP ", aTextEmptyRow, 'P', ItemList.Shape_Empty});

                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Nugget.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P h", aTextEmptyRow, aTextEmptyRow, 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Bun.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P  ", "  h", aTextEmptyRow, 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Bread.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P  ", aTextEmptyRow, "  h", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Baguette.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P  ", aTextEmptyRow, " h ", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Cylinder.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"  P", aTextEmptyRow, "  h", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Anvil.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"  P", aTextEmptyRow, " h ", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Name.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"  P", aTextEmptyRow, "h  ", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Arrow.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"  P", "h  ", aTextEmptyRow, 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Mold_Gear_Small.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextEmptyRow, aTextEmptyRow, "h P", 'P', ItemList.Shape_Empty});

                ItemList.Shape_Extruder_Plate.set(addItem(tLastID = 350, "Extruder Shape (Plate)", "Extruder Shape for making Plates"));
                ItemList.Shape_Extruder_Rod.set(addItem(tLastID = 351, "Extruder Shape (Rod)", "Extruder Shape for making Rods"));
                ItemList.Shape_Extruder_Bolt.set(addItem(tLastID = 352, "Extruder Shape (Bolt)", "Extruder Shape for making Bolts"));
                ItemList.Shape_Extruder_Ring.set(addItem(tLastID = 353, "Extruder Shape (Ring)", "Extruder Shape for making Rings"));
                ItemList.Shape_Extruder_Cell.set(addItem(tLastID = 354, "Extruder Shape (Cell)", "Extruder Shape for making Cells"));
                ItemList.Shape_Extruder_Ingot.set(addItem(tLastID = 355, "Extruder Shape (Ingot)", "Extruder Shape for, wait, can't we just use a Furnace?"));
                ItemList.Shape_Extruder_Wire.set(addItem(tLastID = 356, "Extruder Shape (Wire)", "Extruder Shape for making Wires"));
                ItemList.Shape_Extruder_Casing.set(addItem(tLastID = 357, "Extruder Shape (Casing)", "Extruder Shape for making Item Casings"));
                ItemList.Shape_Extruder_Pipe_Tiny.set(addItem(tLastID = 358, "Extruder Shape (Tiny Pipe)", "Extruder Shape for making tiny Pipes"));
                ItemList.Shape_Extruder_Pipe_Small.set(addItem(tLastID = 359, "Extruder Shape (Small Pipe)", "Extruder Shape for making small Pipes"));
                ItemList.Shape_Extruder_Pipe_Medium.set(addItem(tLastID = 360, "Extruder Shape (Normal Pipe)", "Extruder Shape for making Pipes"));
                ItemList.Shape_Extruder_Pipe_Large.set(addItem(tLastID = 361, "Extruder Shape (Large Pipe)", "Extruder Shape for making large Pipes"));
                ItemList.Shape_Extruder_Pipe_Huge.set(addItem(tLastID = 362, "Extruder Shape (Huge Pipe)", "Extruder Shape for making full Block Pipes"));
                ItemList.Shape_Extruder_Block.set(addItem(tLastID = 363, "Extruder Shape (Block)", "Extruder Shape for making Blocks"));
                ItemList.Shape_Extruder_Sword.set(addItem(tLastID = 364, "Extruder Shape (Sword Blade)", "Extruder Shape for making Swords"));
                ItemList.Shape_Extruder_Pickaxe.set(addItem(tLastID = 365, "Extruder Shape (Pickaxe Head)", "Extruder Shape for making Pickaxes"));
                ItemList.Shape_Extruder_Shovel.set(addItem(tLastID = 366, "Extruder Shape (Shovel Head)", "Extruder Shape for making Shovels"));
                ItemList.Shape_Extruder_Axe.set(addItem(tLastID = 367, "Extruder Shape (Axe Head)", "Extruder Shape for making Axes"));
                ItemList.Shape_Extruder_Hoe.set(addItem(tLastID = 368, "Extruder Shape (Hoe Head)", "Extruder Shape for making Hoes"));
                ItemList.Shape_Extruder_Hammer.set(addItem(tLastID = 369, "Extruder Shape (Hammer Head)", "Extruder Shape for making Hammers"));
                ItemList.Shape_Extruder_File.set(addItem(tLastID = 370, "Extruder Shape (File Head)", "Extruder Shape for making Files"));
                ItemList.Shape_Extruder_Saw.set(addItem(tLastID = 371, "Extruder Shape (Saw Blade)", "Extruder Shape for making Saws"));
                ItemList.Shape_Extruder_Gear.set(addItem(tLastID = 372, "Extruder Shape (Gear)", "Extruder Shape for making Gears"));
                ItemList.Shape_Extruder_Bottle.set(addItem(tLastID = 373, "Extruder Shape (Bottle)", "Extruder Shape for making Bottles"));

                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Bolt.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"x  ", aTextShape, aTextEmptyRow, 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Cell.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{" x ", aTextShape, aTextEmptyRow, 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Ingot.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"  x", aTextShape, aTextEmptyRow, 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Ring.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextEmptyRow, " Px", aTextEmptyRow, 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Rod.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextEmptyRow, aTextShape, "  x", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Wire.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextEmptyRow, aTextShape, " x ", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Casing.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextEmptyRow, aTextShape, "x  ", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Plate.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextEmptyRow, "xP ", aTextEmptyRow, 'P', ItemList.Shape_Empty});

                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Block.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P x", aTextEmptyRow, aTextEmptyRow, 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Small.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P  ", "  x", aTextEmptyRow, 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Large.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P  ", aTextEmptyRow, "  x", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Medium.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P  ", aTextEmptyRow, " x ", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Sword.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"  P", aTextEmptyRow, "  x", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pickaxe.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"  P", aTextEmptyRow, " x ", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Shovel.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"  P", aTextEmptyRow, "x  ", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Axe.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"  P", "x  ", aTextEmptyRow, 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Hoe.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextEmptyRow, aTextEmptyRow, "x P", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Hammer.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextEmptyRow, "x  ", "  P", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_File.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"x  ", aTextEmptyRow, "  P", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Saw.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{" x ", aTextEmptyRow, "  P", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Gear.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"x  ", aTextEmptyRow, "P  ", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Tiny.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{" x ", aTextEmptyRow, "P  ", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Pipe_Huge.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"  x", aTextEmptyRow, "P  ", 'P', ItemList.Shape_Empty});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Extruder_Bottle.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{aTextEmptyRow, "  x", "P  ", 'P', ItemList.Shape_Empty});

                ItemList.Shape_Slicer_Flat.set(addItem(tLastID = 398, "Slicer Blade (Flat)", "Slicer Blade for cutting Flat"));
                ItemList.Shape_Slicer_Stripes.set(addItem(tLastID = 399, "Slicer Blade (Stripes)", "Slicer Blade for cutting Stripes"));

                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Slicer_Flat.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"hXS", aTextShape, "fXd", 'P', ItemList.Shape_Extruder_Block, 'X', OrePrefix.plate.get(Materials.StainlessSteel), 'S', OrePrefix.screw.get(Materials.StainlessSteel)});
                GT_ModHandler.addCraftingRecipe(ItemList.Shape_Slicer_Stripes.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"hXS", "XPX", "fXd", 'P', ItemList.Shape_Extruder_Block, 'X', OrePrefix.plate.get(Materials.StainlessSteel), 'S', OrePrefix.screw.get(Materials.StainlessSteel)});

                ItemList.Fuel_Can_Plastic_Empty.set(addItem(tLastID = 400, "Empty Plastic Fuel Can", "Used to store Fuels", new Object[]{new ItemMaterialInfo(Materials.Plastic, OrePrefix.plate.mMaterialAmount * 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 1L)}));
                ItemList.Fuel_Can_Plastic_Filled.set(addItem(tLastID = 401, "Plastic Fuel Can", "Burns well in Diesel Generators", new Object[]{new ItemMaterialInfo(Materials.Plastic, OrePrefix.plate.mMaterialAmount * 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 1L)}));

                GT_ModHandler.addCraftingRecipe(ItemList.Fuel_Can_Plastic_Empty.get(7L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" PP", "P P", "PPP", 'P', OrePrefix.plate.get(Materials.Plastic)});

                ItemList.Spray_Empty.set(addItem(tLastID = 402, "Empty Spray Can", "Used for making Sprays", new Object[]{new ItemMaterialInfo(Materials.Tin, OrePrefix.plate.mMaterialAmount * 2L, Materials.Redstone, OrePrefix.dust.mMaterialAmount), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 1L)}));

                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Redstone, 1L), OreDictionaryUnifier.get(OrePrefix.cell, Materials.Empty, 1L), ItemList.Spray_Empty.get(1L), 800, 1);

                ItemList.Crate_Empty.set(addItem(tLastID = 403, "Empty Crate", "To Package lots of Material", new Object[]{new ItemMaterialInfo(Materials.Wood, 3628800L, Materials.Iron, OrePrefix.screw.mMaterialAmount), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 2L)}));

                GT_ModHandler.addCraftingRecipe(ItemList.Crate_Empty.get(4L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"SWS", "WdW", "SWS", 'W', OrePrefix.plank.get(Materials.Wood), 'S', OrePrefix.screw.get(Materials.AnyIron)});
                GT_ModHandler.addCraftingRecipe(ItemList.Crate_Empty.get(4L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"SWS", "WdW", "SWS", 'W', OrePrefix.plank.get(Materials.Wood), 'S', OrePrefix.screw.get(Materials.Steel)});

                ItemList.ThermosCan_Empty.set(addItem(tLastID = 404, "Empty Thermos Can", "Keeping hot things hot and cold things cold", new Object[]{new ItemMaterialInfo(Materials.Aluminium, OrePrefix.plate.mMaterialAmount * 1L + 2L * OrePrefix.ring.mMaterialAmount), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.GELUM, 1L)}));

                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plateDouble, Materials.Aluminium, 1L), OreDictionaryUnifier.get(OrePrefix.ring, Materials.Aluminium, 2L), ItemList.ThermosCan_Empty.get(1L), 800, 1);

                ItemList.Large_Fluid_Cell_Steel.set(addItem(tLastID = 405, "Large Steel Fluid Cell", "", new Object[]{new ItemMaterialInfo(Materials.Steel, OrePrefix.plate.mMaterialAmount * 2L + 2L * OrePrefix.ring.mMaterialAmount), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 2L)}));
                setFluidContainerStats(32000 + tLastID, 16000L, 16L);

                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plateDouble, Materials.Steel, 1L), OreDictionaryUnifier.get(OrePrefix.ring, Materials.Steel, 2L), ItemList.Large_Fluid_Cell_Steel.get(1L), 100, 64);

                ItemList.Large_Fluid_Cell_TungstenSteel.set(addItem(tLastID = 406, "Large Tungstensteel Fluid Cell", "", new Object[]{new ItemMaterialInfo(Materials.TungstenSteel, OrePrefix.plate.mMaterialAmount * 2L + 2L * OrePrefix.ring.mMaterialAmount), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 6L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 3L)}));
                setFluidContainerStats(32000 + tLastID, 64000L, 16L);

                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plateDouble, Materials.TungstenSteel, 1L), OreDictionaryUnifier.get(OrePrefix.ring, Materials.TungstenSteel, 2L), ItemList.Large_Fluid_Cell_TungstenSteel.get(1L), 200, 256);
                for (byte i = 0; i < 16; i = (byte) (i + 1)) {
                        ItemList.SPRAY_CAN_DYES[i].set(addItem(tLastID = 430 + 2 * i, "Spray Can (" + Dyes.get(i).mName + ")", "Full", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 4L)}));
                        ItemList.SPRAY_CAN_DYES_USED[i].set(addItem(tLastID + 1, "Spray Can (" + Dyes.get(i).mName + ")", "Used", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 3L), SubTag.INVISIBLE}));
                        IItemBehaviour<GT_MetaBase_Item> tBehaviour = new Behaviour_Spray_Color(ItemList.Spray_Empty.get(1L), ItemList.SPRAY_CAN_DYES_USED[i].get(1L), ItemList.SPRAY_CAN_DYES[i].get(1L), 512L, i);
                        addItemBehavior(32000 + tLastID, tBehaviour);
                        addItemBehavior(32001 + tLastID, tBehaviour);
                }
                ItemList.Tool_Matches.set(addItem(tLastID = 471, "Match", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L)}));
                ItemList.Tool_MatchBox_Used.set(addItem(tLastID = 472, "Match Box", "This is not a Car", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L), SubTag.INVISIBLE}));
                ItemList.Tool_MatchBox_Full.set(addItem(tLastID = 473, "Match Box (Full)", "This is not a Car", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L)}));

                IItemBehaviour<GT_MetaBase_Item> tBehaviour = new Behaviour_Lighter(null, ItemList.Tool_Matches.get(1L), ItemList.Tool_Matches.get(1L), 1L);
                addItemBehavior(32471, tBehaviour);
                tBehaviour = new Behaviour_Lighter(null, ItemList.Tool_MatchBox_Used.get(1L), ItemList.Tool_MatchBox_Full.get(1L), 16L);
                addItemBehavior(32472, tBehaviour);
                addItemBehavior(32473, tBehaviour);

                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.bolt, Materials.Wood, 1L), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Phosphor, 1L), ItemList.Tool_Matches.get(1L), 16, 16);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.bolt, Materials.Wood, 1L), OreDictionaryUnifier.get(OrePrefix.dustSmall, Materials.Phosphorus, 1L), ItemList.Tool_Matches.get(1L), 16, 16);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.bolt, Materials.Wood, 4L), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Phosphor, 1L), ItemList.Tool_Matches.get(4L), 64, 16);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.bolt, Materials.Wood, 4L), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Phosphorus, 1L), ItemList.Tool_Matches.get(4L), 64, 16);
                GT_Values.RA.addBoxingRecipe(ItemList.Tool_Matches.get(16L), OreDictionaryUnifier.get(OrePrefix.plateDouble, Materials.Paper, 1L), ItemList.Tool_MatchBox_Full.get(1L), 64, 16);
                GT_Values.RA.addUnboxingRecipe(ItemList.Tool_MatchBox_Full.get(1L), ItemList.Tool_Matches.get(16L), null, 32, 16);

                ItemList.Tool_Lighter_Invar_Empty.set(addItem(tLastID = 474, "Lighter (Empty)", "", new Object[]{new ItemMaterialInfo(Materials.Invar, OrePrefix.plate.mMaterialAmount * 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L)}));
                ItemList.Tool_Lighter_Invar_Used.set(addItem(tLastID = 475, "Lighter", "", new Object[]{new ItemMaterialInfo(Materials.Invar, OrePrefix.plate.mMaterialAmount * 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L), SubTag.INVISIBLE}));
                ItemList.Tool_Lighter_Invar_Full.set(addItem(tLastID = 476, "Lighter (Full)", "", new Object[]{new ItemMaterialInfo(Materials.Invar, OrePrefix.plate.mMaterialAmount * 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L)}));

                tBehaviour = new Behaviour_Lighter(ItemList.Tool_Lighter_Invar_Empty.get(1L), ItemList.Tool_Lighter_Invar_Used.get(1L), ItemList.Tool_Lighter_Invar_Full.get(1L), 100L);
                addItemBehavior(32475, tBehaviour);
                addItemBehavior(32476, tBehaviour);

                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Invar, 2L), new ItemStack(Items.FLINT, 1), ItemList.Tool_Lighter_Invar_Empty.get(1L), 256, 16);

                ItemList.Tool_Lighter_Platinum_Empty.set(addItem(tLastID = 477, "Platinum Lighter (Empty)", "A known Prank Master is engraved on it", new Object[]{new ItemMaterialInfo(Materials.Platinum, OrePrefix.plate.mMaterialAmount * 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.NEBRISUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L)}));
                ItemList.Tool_Lighter_Platinum_Used.set(addItem(tLastID = 478, "Platinum Lighter", "A known Prank Master is engraved on it", new Object[]{new ItemMaterialInfo(Materials.Platinum, OrePrefix.plate.mMaterialAmount * 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.NEBRISUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L), SubTag.INVISIBLE}));
                ItemList.Tool_Lighter_Platinum_Full.set(addItem(tLastID = 479, "Platinum Lighter (Full)", "A known Prank Master is engraved on it", new Object[]{new ItemMaterialInfo(Materials.Platinum, OrePrefix.plate.mMaterialAmount * 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.IGNIS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.NEBRISUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L)}));

                tBehaviour = new Behaviour_Lighter(ItemList.Tool_Lighter_Platinum_Empty.get(1L), ItemList.Tool_Lighter_Platinum_Used.get(1L), ItemList.Tool_Lighter_Platinum_Full.get(1L), 1000L);
                addItemBehavior(32478, tBehaviour);
                addItemBehavior(32479, tBehaviour);

                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Platinum, 2L), new ItemStack(Items.FLINT, 1), ItemList.Tool_Lighter_Platinum_Empty.get(1L), 256, 256);

                if (Loader.isModLoaded("GalacticraftMars")) {
                        ItemList.Ingot_Heavy1.set(addItem(tLastID = 462, "Heavy Duty Alloy Ingot", "Used to make Heavy Duty Plates"));
                        ItemList.Ingot_Heavy2.set(addItem(tLastID = 463, "Heavy Duty Alloy Ingot T2", "Used to make Heavy Duty Plates T2"));
                        ItemList.Ingot_Heavy3.set(addItem(tLastID = 464, "Heavy Duty Alloy Ingot T3", "Used to make Heavy Duty Plates T3"));
                }
                ItemList.Ingot_IridiumAlloy.set(addItem(tLastID = 480, "Iridium Alloy Ingot", "Used to make Iridium Plates", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L)}));

                GT_ModHandler.addRollingMachineRecipe(ItemList.Ingot_IridiumAlloy.get(1L), new Object[]{"IAI", "ADA", "IAI", 'D', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "iridiumplate", true) ? OreDictNames.craftingIndustrialDiamond : OrePrefix.dust.get(Materials.Diamond), 'A', OrePrefix.plateAlloy.get("Advanced"), 'I', OrePrefix.plate.get(Materials.Iridium)});
                GT_ModHandler.addCraftingRecipe(ItemList.Ingot_IridiumAlloy.get(1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"IAI", "ADA", "IAI", 'D', GregTech_API.sRecipeFile.get(ConfigCategories.Recipes.harderrecipes, "iridiumplate", true) ? OreDictNames.craftingIndustrialDiamond : OrePrefix.dust.get(Materials.Diamond), 'A', OrePrefix.plateAlloy.get("Advanced"), 'I', OrePrefix.plate.get(Materials.Iridium)});

                ItemList.Paper_Printed_Pages.set(addItem(tLastID = 481, "Printed Pages", "Used to make written Books", new Object[]{new ItemMaterialInfo(Materials.Paper, 10886400L), new Behaviour_PrintedPages(), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 2L)}));
                ItemList.Paper_Magic_Empty.set(addItem(tLastID = 482, "Magic Paper", "", new Object[]{SubTag.INVISIBLE, new ItemMaterialInfo(Materials.Paper, 3628800L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.PRAECANTATIO, 1L)}));
                ItemList.Paper_Magic_Page.set(addItem(tLastID = 483, "Enchanted Page", "", new Object[]{SubTag.INVISIBLE, new ItemMaterialInfo(Materials.Paper, 3628800L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.PRAECANTATIO, 2L)}));
                ItemList.Paper_Magic_Pages.set(addItem(tLastID = 484, "Enchanted Pages", "", new Object[]{SubTag.INVISIBLE, new ItemMaterialInfo(Materials.Paper, 10886400L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PRAECANTATIO, 4L)}));
                ItemList.Paper_Punch_Card_Empty.set(addItem(tLastID = 485, "Punch Card", "", new Object[]{SubTag.INVISIBLE, new ItemMaterialInfo(Materials.Paper, 7257600L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 1L)}));
                ItemList.Paper_Punch_Card_Encoded.set(addItem(tLastID = 486, "Punched Card", "", new Object[]{SubTag.INVISIBLE, new ItemMaterialInfo(Materials.Paper, 7257600L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 2L)}));
                ItemList.Book_Written_01.set(addItem(tLastID = 487, "Book", "", new Object[]{new ItemMaterialInfo(Materials.Paper, 10886400L), "bookWritten", OreDictNames.craftingBook, new Behaviour_WrittenBook(), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 2L)}));
                ItemList.Book_Written_02.set(addItem(tLastID = 488, "Book", "", new Object[]{new ItemMaterialInfo(Materials.Paper, 10886400L), "bookWritten", OreDictNames.craftingBook, new Behaviour_WrittenBook(), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 2L)}));
                ItemList.Book_Written_03.set(addItem(tLastID = 489, "Book", "", new Object[]{new ItemMaterialInfo(Materials.Paper, 10886400L), "bookWritten", OreDictNames.craftingBook, new Behaviour_WrittenBook(), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 2L)}));

                ItemList.Schematic.set(addItem(tLastID = 490, "Schematic", "EMPTY", new Object[]{new ItemMaterialInfo(Materials.StainlessSteel, 7257600L), new TC_Aspects.TC_AspectStack(TC_Aspects.STRONTIO, 1L)}));
                ItemList.Schematic_Crafting.set(addItem(tLastID = 491, "Schematic (Crafting)", "Crafts the Programmed Recipe", new Object[]{new ItemMaterialInfo(Materials.StainlessSteel, 7257600L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 1L)}));
                ItemList.Schematic_1by1.set(addItem(tLastID = 495, "Schematic (1x1)", "Crafts 1 Items as 1x1 (use in Packager)", new Object[]{new ItemMaterialInfo(Materials.StainlessSteel, 7257600L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 1L)}));
                ItemList.Schematic_2by2.set(addItem(tLastID = 496, "Schematic (2x2)", "Crafts 4 Items as 2x2 (use in Packager)", new Object[]{new ItemMaterialInfo(Materials.StainlessSteel, 7257600L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 1L)}));
                ItemList.Schematic_3by3.set(addItem(tLastID = 497, "Schematic (3x3)", "Crafts 9 Items as 3x3 (use in Packager)", new Object[]{new ItemMaterialInfo(Materials.StainlessSteel, 7257600L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 1L)}));
                ItemList.Schematic_Dust.set(addItem(tLastID = 498, "Schematic (Dusts)", "Combines Dusts (use in Packager)", new Object[]{new ItemMaterialInfo(Materials.StainlessSteel, 7257600L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 1L)}));

                GT_ModHandler.addCraftingRecipe(ItemList.Schematic_1by1.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"d  ", aTextShape, aTextEmptyRow, 'P', ItemList.Schematic});
                GT_ModHandler.addCraftingRecipe(ItemList.Schematic_2by2.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{" d ", aTextShape, aTextEmptyRow, 'P', ItemList.Schematic});
                GT_ModHandler.addCraftingRecipe(ItemList.Schematic_3by3.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"  d", aTextShape, aTextEmptyRow, 'P', ItemList.Schematic});
                GT_ModHandler.addCraftingRecipe(ItemList.Schematic_Dust.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{aTextEmptyRow, aTextShape, "  d", 'P', ItemList.Schematic});

                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Schematic_Crafting});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Schematic_1by1});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Schematic_2by2});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Schematic_3by3});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Schematic.get(1L), GT_ModHandler.RecipeBits.BUFFERED | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Schematic_Dust});

                ItemList.Battery_Hull_LV.set(addItem(tLastID = 500, "Small Battery Hull", "An empty LV Battery Hull", new Object[]{new ItemMaterialInfo(Materials.BatteryAlloy, OrePrefix.plate.mMaterialAmount * 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L)}));
                ItemList.Battery_Hull_MV.set(addItem(tLastID = 501, "Medium Battery Hull", "An empty MV Battery Hull", new Object[]{new ItemMaterialInfo(Materials.BatteryAlloy, OrePrefix.plate.mMaterialAmount * 3L), new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L)}));
                ItemList.Battery_Hull_HV.set(addItem(tLastID = 502, "Large Battery Hull", "An empty HV Battery Hull", new Object[]{new ItemMaterialInfo(Materials.BatteryAlloy, OrePrefix.plate.mMaterialAmount * 9L), new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 1L)}));

                GT_ModHandler.addCraftingRecipe(ItemList.Battery_Hull_LV.get(1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"C", "P", "P", 'P', OrePrefix.plate.get(Materials.BatteryAlloy), 'C', OreDictNames.craftingWireTin});
                GT_ModHandler.addCraftingRecipe(ItemList.Battery_Hull_MV.get(1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"C C", "PPP", "PPP", 'P', OrePrefix.plate.get(Materials.BatteryAlloy), 'C', OreDictNames.craftingWireCopper});

                ItemList.Battery_RE_ULV_Tantalum.set(addItem(tLastID = 499, "Tantalum Capacitor", "Reusable", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L)}));
                setElectricStats(32000 + tLastID, 1000L, GT_Values.V[0], 0L, -3L, false);

                ItemList.Battery_SU_LV_SulfuricAcid.set(addItem(tLastID = 510, "Small Acid Battery", "Single Use", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L)}));
                setElectricStats(32000 + tLastID, 18000L, GT_Values.V[1], 1L, -2L, true);
                ItemList.Battery_SU_LV_Mercury.set(addItem(tLastID = 511, "Small Mercury Battery", "Single Use", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L)}));
                setElectricStats(32000 + tLastID, 32000L, GT_Values.V[1], 1L, -2L, true);

                ItemList.Battery_RE_LV_Cadmium.set(addItem(tLastID = 517, "Small Cadmium Battery", "Reusable", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L), "calclavia:ADVANCED_BATTERY"}));
                setElectricStats(32000 + tLastID, 75000L, GT_Values.V[1], 1L, -3L, true);
                ItemList.Battery_RE_LV_Lithium.set(addItem(tLastID = 518, "Small Lithium Battery", "Reusable", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L), "calclavia:ADVANCED_BATTERY"}));
                setElectricStats(32000 + tLastID, 100000L, GT_Values.V[1], 1L, -3L, true);
                ItemList.Battery_RE_LV_Sodium.set(addItem(tLastID = 519, "Small Sodium Battery", "Reusable", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L), "calclavia:ADVANCED_BATTERY"}));
                setElectricStats(32000 + tLastID, 50000L, GT_Values.V[1], 1L, -3L, true);

                ItemList.Battery_SU_MV_SulfuricAcid.set(addItem(tLastID = 520, "Medium Acid Battery", "Single Use", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 4L)}));
                setElectricStats(32000 + tLastID, 72000L, GT_Values.V[2], 2L, -2L, true);
                ItemList.Battery_SU_MV_Mercury.set(addItem(tLastID = 521, "Medium Mercury Battery", "Single Use", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 4L)}));
                setElectricStats(32000 + tLastID, 128000L, GT_Values.V[2], 2L, -2L, true);

                ItemList.Battery_RE_MV_Cadmium.set(addItem(tLastID = 527, "Medium Cadmium Battery", "Reusable", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L)}));
                setElectricStats(32000 + tLastID, 300000L, GT_Values.V[2], 2L, -3L, true);
                ItemList.Battery_RE_MV_Lithium.set(addItem(tLastID = 528, "Medium Lithium Battery", "Reusable", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L)}));
                setElectricStats(32000 + tLastID, 400000L, GT_Values.V[2], 2L, -3L, true);
                ItemList.Battery_RE_MV_Sodium.set(addItem(tLastID = 529, "Medium Sodium Battery", "Reusable", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L)}));
                setElectricStats(32000 + tLastID, 200000L, GT_Values.V[2], 2L, -3L, true);

                ItemList.Battery_SU_HV_SulfuricAcid.set(addItem(tLastID = 530, "Large Acid Battery", "Single Use", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 8L)}));
                setElectricStats(32000 + tLastID, 288000L, GT_Values.V[3], 3L, -2L, true);
                ItemList.Battery_SU_HV_Mercury.set(addItem(tLastID = 531, "Large Mercury Battery", "Single Use", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 8L)}));
                setElectricStats(32000 + tLastID, 512000L, GT_Values.V[3], 3L, -2L, true);

                ItemList.Battery_RE_HV_Cadmium.set(addItem(tLastID = 537, "Large Cadmium Battery", "Reusable", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 4L)}));
                setElectricStats(32000 + tLastID, 1200000L, GT_Values.V[3], 3L, -3L, true);
                ItemList.Battery_RE_HV_Lithium.set(addItem(tLastID = 538, "Large Lithium Battery", "Reusable", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 4L)}));
                setElectricStats(32000 + tLastID, 1600000L, GT_Values.V[3], 3L, -3L, true);
                ItemList.Battery_RE_HV_Sodium.set(addItem(tLastID = 539, "Large Sodium Battery", "Reusable", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 4L)}));
                setElectricStats(32000 + tLastID, 800000L, GT_Values.V[3], 3L, -3L, true);

                GT_ModHandler.addExtractionRecipe(ItemList.Battery_SU_LV_SulfuricAcid.get(1L), ItemList.Battery_Hull_LV.get(1L));
                GT_ModHandler.addExtractionRecipe(ItemList.Battery_SU_LV_Mercury.get(1L), ItemList.Battery_Hull_LV.get(1L));
                GT_ModHandler.addExtractionRecipe(ItemList.Battery_SU_MV_SulfuricAcid.get(1L), ItemList.Battery_Hull_MV.get(1L));
                GT_ModHandler.addExtractionRecipe(ItemList.Battery_SU_MV_Mercury.get(1L), ItemList.Battery_Hull_MV.get(1L));
                GT_ModHandler.addExtractionRecipe(ItemList.Battery_SU_HV_SulfuricAcid.get(1L), ItemList.Battery_Hull_HV.get(1L));
                GT_ModHandler.addExtractionRecipe(ItemList.Battery_SU_HV_Mercury.get(1L), ItemList.Battery_Hull_HV.get(1L));
                GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_LV_Cadmium.get(1L), ItemList.Battery_Hull_LV.get(1L));
                GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_LV_Lithium.get(1L), ItemList.Battery_Hull_LV.get(1L));
                GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_LV_Sodium.get(1L), ItemList.Battery_Hull_LV.get(1L));
                GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_MV_Cadmium.get(1L), ItemList.Battery_Hull_MV.get(1L));
                GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_MV_Lithium.get(1L), ItemList.Battery_Hull_MV.get(1L));
                GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_MV_Sodium.get(1L), ItemList.Battery_Hull_MV.get(1L));
                GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_HV_Cadmium.get(1L), ItemList.Battery_Hull_HV.get(1L));
                GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_HV_Lithium.get(1L), ItemList.Battery_Hull_HV.get(1L));
                GT_ModHandler.addExtractionRecipe(ItemList.Battery_RE_HV_Sodium.get(1L), ItemList.Battery_Hull_HV.get(1L));

                GT_Values.RA.addCannerRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Cadmium, 2L), ItemList.Battery_Hull_LV.get(1L), ItemList.Battery_RE_LV_Cadmium.get(1L), null, 100, 2);
                GT_Values.RA.addCannerRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Lithium, 2L), ItemList.Battery_Hull_LV.get(1L), ItemList.Battery_RE_LV_Lithium.get(1L), null, 100, 2);
                GT_Values.RA.addCannerRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Sodium, 2L), ItemList.Battery_Hull_LV.get(1L), ItemList.Battery_RE_LV_Sodium.get(1L), null, 100, 2);
                GT_Values.RA.addCannerRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Cadmium, 8L), ItemList.Battery_Hull_MV.get(1L), ItemList.Battery_RE_MV_Cadmium.get(1L), null, 400, 2);
                GT_Values.RA.addCannerRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Lithium, 8L), ItemList.Battery_Hull_MV.get(1L), ItemList.Battery_RE_MV_Lithium.get(1L), null, 400, 2);
                GT_Values.RA.addCannerRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Sodium, 8L), ItemList.Battery_Hull_MV.get(1L), ItemList.Battery_RE_MV_Sodium.get(1L), null, 400, 2);
                GT_Values.RA.addCannerRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Cadmium, 32L), ItemList.Battery_Hull_HV.get(1L), ItemList.Battery_RE_HV_Cadmium.get(1L), null, 1600, 2);
                GT_Values.RA.addCannerRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Lithium, 32L), ItemList.Battery_Hull_HV.get(1L), ItemList.Battery_RE_HV_Lithium.get(1L), null, 1600, 2);
                GT_Values.RA.addCannerRecipe(OreDictionaryUnifier.get(OrePrefix.dust, Materials.Sodium, 32L), ItemList.Battery_Hull_HV.get(1L), ItemList.Battery_RE_HV_Sodium.get(1L), null, 1600, 2);

                ItemList.Energy_LapotronicOrb.set(addItem(tLastID = 597, "Lapotronic Energy Orb", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 16L), OrePrefix.battery.get(Materials.Ultimate)}));
                setElectricStats(32000 + tLastID, 100000000L, GT_Values.V[5], 5L, -3L, true);

                ItemList.ZPM.set(addItem(tLastID = 598, "Zero Point Module", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 64L)}));
                setElectricStats(32000 + tLastID, 2000000000000L, GT_Values.V[7], 7L, -2L, true);

                ItemList.Energy_LapotronicOrb2.set(addItem(tLastID = 599, "Lapotronic Energy Orb Cluster", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 16L), OrePrefix.battery.get(Materials.Ultimate)}));
                setElectricStats(32000 + tLastID, 1000000000L, GT_Values.V[6], 6L, -3L, true);

                ItemList.ZPM2.set(addItem(tLastID = 605, "Ultimate Battery", "Fill this to win minecraft", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 64L)}));
                setElectricStats(32000 + tLastID, Long.MAX_VALUE, GT_Values.V[8], 8L, -3L, true);

                ItemList.Electric_Motor_LV.set(addItem(tLastID = 600, "Electric Motor (LV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 1L)}));
                ItemList.Electric_Motor_MV.set(addItem(tLastID = 601, "Electric Motor (MV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 2L)}));
                ItemList.Electric_Motor_HV.set(addItem(tLastID = 602, "Electric Motor (HV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 4L)}));
                ItemList.Electric_Motor_EV.set(addItem(tLastID = 603, "Electric Motor (EV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 8L)}));
                ItemList.Electric_Motor_IV.set(addItem(tLastID = 604, "Electric Motor (IV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 16L)}));
                ItemList.Electric_Motor_LuV.set(addItem(tLastID = 606, "Electric Motor (LuV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 24L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 24L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 24L)}));
                ItemList.Electric_Motor_ZPM.set(addItem(tLastID = 607, "Electric Motor (ZPM)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 32L)}));
                ItemList.Electric_Motor_UV.set(addItem(tLastID = 608, "Electric Motor (UV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 48L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 48L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 48L)}));

                GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CWR", "WIW", "RWC", 'I', OrePrefix.stick.get(Materials.IronMagnetic), 'R', OrePrefix.stick.get(Materials.AnyIron), 'W', OrePrefix.wireGt01.get(Materials.AnyCopper), 'C', OrePrefix.cableGt01.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"CWR", "WIW", "RWC", 'I', OrePrefix.stick.get(Materials.SteelMagnetic), 'R', OrePrefix.stick.get(Materials.Steel), 'W', OrePrefix.wireGt01.get(Materials.AnyCopper), 'C', OrePrefix.cableGt01.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CWR", "WIW", "RWC", 'I', OrePrefix.stick.get(Materials.SteelMagnetic), 'R', OrePrefix.stick.get(Materials.Aluminium), 'W', OrePrefix.wireGt02.get(Materials.AnyCopper), 'C', OrePrefix.cableGt01.get(Materials.AnyCopper)});
                GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CWR", "WIW", "RWC", 'I', OrePrefix.stick.get(Materials.SteelMagnetic), 'R', OrePrefix.stick.get(Materials.StainlessSteel), 'W', OrePrefix.wireGt04.get(Materials.AnyCopper), 'C', OrePrefix.cableGt01.get(Materials.Gold)});
                GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CWR", "WIW", "RWC", 'I', OrePrefix.stick.get(Materials.NeodymiumMagnetic), 'R', OrePrefix.stick.get(Materials.Titanium), 'W', OrePrefix.wireGt08.get(Materials.AnnealedCopper), 'C', OrePrefix.cableGt01.get(Materials.Aluminium)});
                GT_ModHandler.addCraftingRecipe(ItemList.Electric_Motor_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CWR", "WIW", "RWC", 'I', OrePrefix.stick.get(Materials.NeodymiumMagnetic), 'R', OrePrefix.stick.get(Materials.TungstenSteel), 'W', OrePrefix.wireGt16.get(Materials.AnnealedCopper), 'C', OrePrefix.cableGt01.get(Materials.Tungsten)});

                ItemList.Electric_Pump_LV.set(addItem(tLastID = 610, "Electric Pump (LV)", "640 L/sec (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L)}));
                ItemList.Electric_Pump_MV.set(addItem(tLastID = 611, "Electric Pump (MV)", "2560 L/sec (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 2L)}));
                ItemList.Electric_Pump_HV.set(addItem(tLastID = 612, "Electric Pump (HV)", "10240 L/sec (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 4L)}));
                ItemList.Electric_Pump_EV.set(addItem(tLastID = 613, "Electric Pump (EV)", "40.960 L/sec (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 8L)}));
                ItemList.Electric_Pump_IV.set(addItem(tLastID = 614, "Electric Pump (IV)", "163.840 L/sec (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 16L)}));
                ItemList.Electric_Pump_LuV.set(addItem(tLastID = 620, "Electric Pump (LuV)", "655.360 L/sec (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 16L)}));
                ItemList.Electric_Pump_ZPM.set(addItem(tLastID = 621, "Electric Pump (ZPM)", "2.621.440 L/sec (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 16L)}));
                ItemList.Electric_Pump_UV.set(addItem(tLastID = 622, "Electric Pump (UV)", "10.485.760 L/sec (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 16L)}));

                GregTech_API.registerCover(ItemList.Electric_Pump_LV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[1][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP)), new GT_Cover_Pump(32));
                GregTech_API.registerCover(ItemList.Electric_Pump_MV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP)), new GT_Cover_Pump(128));
                GregTech_API.registerCover(ItemList.Electric_Pump_HV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[3][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP)), new GT_Cover_Pump(512));
                GregTech_API.registerCover(ItemList.Electric_Pump_EV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[4][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP)), new GT_Cover_Pump(2048));
                GregTech_API.registerCover(ItemList.Electric_Pump_IV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[5][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP)), new GT_Cover_Pump(8192));
                GregTech_API.registerCover(ItemList.Electric_Pump_LuV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[6][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP)), new GT_Cover_Pump(32768));
                GregTech_API.registerCover(ItemList.Electric_Pump_ZPM.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[7][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP)), new GT_Cover_Pump(131072));
                GregTech_API.registerCover(ItemList.Electric_Pump_UV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[8][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP)), new GT_Cover_Pump(524288));

                ItemList.FluidRegulator_LV.set(addItem(tLastID = 615, "Fluid Regulator (LV)", "Configuable up to 640 L/sec (as Cover)", new Object[]{}));
                ItemList.FluidRegulator_MV.set(addItem(tLastID = 616, "Fluid Regulator (MV)", "Configuable up to 2560 L/sec (as Cover)", new Object[]{}));
                ItemList.FluidRegulator_HV.set(addItem(tLastID = 617, "Fluid Regulator (HV)", "Configuable up to 10240 L/sec (as Cover)", new Object[]{}));
                ItemList.FluidRegulator_EV.set(addItem(tLastID = 618, "Fluid Regulator (EV)", "Configuable up to 40960 L/sec (as Cover)", new Object[]{}));
                ItemList.FluidRegulator_IV.set(addItem(tLastID = 619, "Fluid Regulator (IV)", "Configuable up to 163840 L/sec (as Cover)", new Object[]{}));

                GregTech_API.registerCover(ItemList.FluidRegulator_LV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[1][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP)), new GT_Cover_FluidRegulator(32));
                GregTech_API.registerCover(ItemList.FluidRegulator_MV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP)), new GT_Cover_FluidRegulator(128));
                GregTech_API.registerCover(ItemList.FluidRegulator_HV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[3][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP)), new GT_Cover_FluidRegulator(512));
                GregTech_API.registerCover(ItemList.FluidRegulator_EV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[4][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP)), new GT_Cover_FluidRegulator(2048));
                GregTech_API.registerCover(ItemList.FluidRegulator_IV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[5][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_PUMP)), new GT_Cover_FluidRegulator(8192));

                ItemList.FluidFilter.set(addItem(tLastID = 635, "Fluid Filter", "Set with Fluid Container to only accept one Fluid Type", new Object[]{}));
                GregTech_API.registerCover(ItemList.FluidFilter.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[1][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SHUTTER)), new GT_Cover_Fluidfilter());

                /**ItemList.Rotor_LV.set(addItem(tLastID = 620, "Tin Rotor", "", new Object[] { OrePrefix.rotor.get(Materials.Tin), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 1L) }));
                 ItemList.Rotor_MV.set(addItem(tLastID = 621, "Bronze Rotor", "", new Object[] { OrePrefix.rotor.get(Materials.Bronze), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 2L) }));
                 ItemList.Rotor_HV.set(addItem(tLastID = 622, "Steel Rotor", "", new Object[] { OrePrefix.rotor.get(Materials.Steel), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 4L) }));
                 ItemList.Rotor_EV.set(addItem(tLastID = 623, "Stainless Steel Rotor", "", new Object[] { OrePrefix.rotor.get(Materials.StainlessSteel), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 8L) }));
                 ItemList.Rotor_IV.set(addItem(tLastID = 624, "Tungstensteel Rotor", "", new Object[] { OrePrefix.rotor.get(Materials.TungstenSteel), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 16L) }));
                 ItemList.Rotor_LuV.set(ItemList.Rotor_IV.get(1L));
                 ItemList.Rotor_ZPM.set(ItemList.Rotor_LuV.get(1L));
                 ItemList.Rotor_UV.set(ItemList.Rotor_ZPM.get(1L));**/

                GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_LV, 'O', OrePrefix.ring.get(Materials.Rubber), 'X', OrePrefix.rotor.get(Materials.Tin), 'S', OrePrefix.screw.get(Materials.Tin), 'W', OrePrefix.cableGt01.get(Materials.Tin), 'P', OrePrefix.pipeMedium.get(Materials.Bronze)});
                GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_MV, 'O', OrePrefix.ring.get(Materials.Rubber), 'X', OrePrefix.rotor.get(Materials.Bronze), 'S', OrePrefix.screw.get(Materials.Bronze), 'W', OrePrefix.cableGt01.get(Materials.AnyCopper), 'P', OrePrefix.pipeMedium.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_HV, 'O', OrePrefix.ring.get(Materials.Rubber), 'X', OrePrefix.rotor.get(Materials.Steel), 'S', OrePrefix.screw.get(Materials.Steel), 'W', OrePrefix.cableGt01.get(Materials.Gold), 'P', OrePrefix.pipeMedium.get(Materials.StainlessSteel)});
                GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_EV, 'O', OrePrefix.ring.get(Materials.Rubber), 'X', OrePrefix.rotor.get(Materials.StainlessSteel), 'S', OrePrefix.screw.get(Materials.StainlessSteel), 'W', OrePrefix.cableGt01.get(Materials.Aluminium), 'P', OrePrefix.pipeMedium.get(Materials.Titanium)});
                GT_ModHandler.addCraftingRecipe(ItemList.Electric_Pump_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SXO", "dPw", "OMW", 'M', ItemList.Electric_Motor_IV, 'O', OrePrefix.ring.get(Materials.Rubber), 'X', OrePrefix.rotor.get(Materials.TungstenSteel), 'S', OrePrefix.screw.get(Materials.TungstenSteel), 'W', OrePrefix.cableGt01.get(Materials.Tungsten), 'P', OrePrefix.pipeMedium.get(Materials.TungstenSteel)});

                ItemList.Conveyor_Module_LV.set(addItem(tLastID = 630, "Conveyor Module (LV)", "1 Stack every 20 secs (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 1L)}));
                ItemList.Conveyor_Module_MV.set(addItem(tLastID = 631, "Conveyor Module (MV)", "1 Stack every 5 secs (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 2L)}));
                ItemList.Conveyor_Module_HV.set(addItem(tLastID = 632, "Conveyor Module (HV)", "1 Stack every 1 sec (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 4L)}));
                ItemList.Conveyor_Module_EV.set(addItem(tLastID = 633, "Conveyor Module (EV)", "1 Stack every 1/5 sec (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 8L)}));
                ItemList.Conveyor_Module_IV.set(addItem(tLastID = 634, "Conveyor Module (IV)", "1 Stack every 1/20 sec (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 16L)}));
                ItemList.Conveyor_Module_LuV.set(addItem(tLastID = 636, "Conveyor Module (LuV)", "1 Stack every 1/20 sec (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 16L)}));
                ItemList.Conveyor_Module_ZPM.set(addItem(tLastID = 637, "Conveyor Module (ZPM)", "1 Stack every 1/20 sec (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 16L)}));
                ItemList.Conveyor_Module_UV.set(addItem(tLastID = 638, "Conveyor Module (UV)", "1 Stack every 1/20 sec (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 16L)}));

                GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_LV, 'C', OrePrefix.cableGt01.get(Materials.Tin), 'R', OrePrefix.plate.get(Materials.Rubber)});
                GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_MV, 'C', OrePrefix.cableGt01.get(Materials.AnyCopper), 'R', OrePrefix.plate.get(Materials.Rubber)});
                GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_HV, 'C', OrePrefix.cableGt01.get(Materials.Gold), 'R', OrePrefix.plate.get(Materials.Rubber)});
                GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_EV, 'C', OrePrefix.cableGt01.get(Materials.Aluminium), 'R', OrePrefix.plate.get(Materials.Rubber)});
                GT_ModHandler.addCraftingRecipe(ItemList.Conveyor_Module_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"RRR", "MCM", "RRR", 'M', ItemList.Electric_Motor_IV, 'C', OrePrefix.cableGt01.get(Materials.Tungsten), 'R', OrePrefix.plate.get(Materials.Rubber)});

                GregTech_API.registerCover(ItemList.Conveyor_Module_LV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[1][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_CONVEYOR)), new GT_Cover_Conveyor(400));
                GregTech_API.registerCover(ItemList.Conveyor_Module_MV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_CONVEYOR)), new GT_Cover_Conveyor(100));
                GregTech_API.registerCover(ItemList.Conveyor_Module_HV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[3][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_CONVEYOR)), new GT_Cover_Conveyor(20));
                GregTech_API.registerCover(ItemList.Conveyor_Module_EV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[4][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_CONVEYOR)), new GT_Cover_Conveyor(4));
                GregTech_API.registerCover(ItemList.Conveyor_Module_IV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[5][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_CONVEYOR)), new GT_Cover_Conveyor(1));
                GregTech_API.registerCover(ItemList.Conveyor_Module_LuV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[6][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_CONVEYOR)), new GT_Cover_Conveyor(1));
                GregTech_API.registerCover(ItemList.Conveyor_Module_ZPM.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[7][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_CONVEYOR)), new GT_Cover_Conveyor(1));
                GregTech_API.registerCover(ItemList.Conveyor_Module_UV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[8][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_CONVEYOR)), new GT_Cover_Conveyor(1));

                ItemList.Electric_Piston_LV.set(addItem(tLastID = 640, "Electric Piston (LV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 1L)}));
                ItemList.Electric_Piston_MV.set(addItem(tLastID = 641, "Electric Piston (MV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 2L)}));
                ItemList.Electric_Piston_HV.set(addItem(tLastID = 642, "Electric Piston (HV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 4L)}));
                ItemList.Electric_Piston_EV.set(addItem(tLastID = 643, "Electric Piston (EV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 8L)}));
                ItemList.Electric_Piston_IV.set(addItem(tLastID = 644, "Electric Piston (IV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 16L)}));
                ItemList.Electric_Piston_LuV.set(addItem(tLastID = 645, "Electric Piston (LuV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 16L)}));
                ItemList.Electric_Piston_ZPM.set(addItem(tLastID = 646, "Electric Piston (ZPM)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 16L)}));
                ItemList.Electric_Piston_UV.set(addItem(tLastID = 647, "Electric Piston (UV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 16L)}));

                GT_ModHandler.addCraftingRecipe(ItemList.Electric_Piston_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"PPP", "CSS", "CMG", 'P', OrePrefix.plate.get(Materials.Steel), 'S', OrePrefix.stick.get(Materials.Steel), 'G', OrePrefix.gearGtSmall.get(Materials.Steel), 'M', ItemList.Electric_Motor_LV, 'C', OrePrefix.cableGt01.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.Electric_Piston_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"PPP", "CSS", "CMG", 'P', OrePrefix.plate.get(Materials.Aluminium), 'S', OrePrefix.stick.get(Materials.Aluminium), 'G', OrePrefix.gearGtSmall.get(Materials.Aluminium), 'M', ItemList.Electric_Motor_MV, 'C', OrePrefix.cableGt01.get(Materials.AnyCopper)});
                GT_ModHandler.addCraftingRecipe(ItemList.Electric_Piston_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"PPP", "CSS", "CMG", 'P', OrePrefix.plate.get(Materials.StainlessSteel), 'S', OrePrefix.stick.get(Materials.StainlessSteel), 'G', OrePrefix.gearGtSmall.get(Materials.StainlessSteel), 'M', ItemList.Electric_Motor_HV, 'C', OrePrefix.cableGt01.get(Materials.Gold)});
                GT_ModHandler.addCraftingRecipe(ItemList.Electric_Piston_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"PPP", "CSS", "CMG", 'P', OrePrefix.plate.get(Materials.Titanium), 'S', OrePrefix.stick.get(Materials.Titanium), 'G', OrePrefix.gearGtSmall.get(Materials.Titanium), 'M', ItemList.Electric_Motor_EV, 'C', OrePrefix.cableGt01.get(Materials.Aluminium)});
                GT_ModHandler.addCraftingRecipe(ItemList.Electric_Piston_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"PPP", "CSS", "CMG", 'P', OrePrefix.plate.get(Materials.TungstenSteel), 'S', OrePrefix.stick.get(Materials.TungstenSteel), 'G', OrePrefix.gearGtSmall.get(Materials.TungstenSteel), 'M', ItemList.Electric_Motor_IV, 'C', OrePrefix.cableGt01.get(Materials.Tungsten)});

                ItemList.Robot_Arm_LV.set(addItem(tLastID = 650, "Robot Arm (LV)", "Inserts into specific Slots (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 1L)}));
                ItemList.Robot_Arm_MV.set(addItem(tLastID = 651, "Robot Arm (MV)", "Inserts into specific Slots (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 2L)}));
                ItemList.Robot_Arm_HV.set(addItem(tLastID = 652, "Robot Arm (HV)", "Inserts into specific Slots (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 4L)}));
                ItemList.Robot_Arm_EV.set(addItem(tLastID = 653, "Robot Arm (EV)", "Inserts into specific Slots (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 8L)}));
                ItemList.Robot_Arm_IV.set(addItem(tLastID = 654, "Robot Arm (IV)", "Inserts into specific Slots (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 16L)}));
                ItemList.Robot_Arm_LuV.set(addItem(tLastID = 655, "Robot Arm (LuV)", "Inserts into specific Slots (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 16L)}));
                ItemList.Robot_Arm_ZPM.set(addItem(tLastID = 656, "Robot Arm (ZPM)", "Inserts into specific Slots (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 16L)}));
                ItemList.Robot_Arm_UV.set(addItem(tLastID = 657, "Robot Arm (UV)", "Inserts into specific Slots (as Cover)", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.MOTUS, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 16L)}));

                GT_ModHandler.addCraftingRecipe(ItemList.Robot_Arm_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CCC", "MSM", "PES", 'S', OrePrefix.stick.get(Materials.Steel), 'M', ItemList.Electric_Motor_LV, 'P', ItemList.Electric_Piston_LV, 'E', OrePrefix.circuit.get(Materials.Basic), 'C', OrePrefix.cableGt01.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.Robot_Arm_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CCC", "MSM", "PES", 'S', OrePrefix.stick.get(Materials.Aluminium), 'M', ItemList.Electric_Motor_MV, 'P', ItemList.Electric_Piston_MV, 'E', OrePrefix.circuit.get(Materials.Good), 'C', OrePrefix.cableGt01.get(Materials.AnyCopper)});
                GT_ModHandler.addCraftingRecipe(ItemList.Robot_Arm_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CCC", "MSM", "PES", 'S', OrePrefix.stick.get(Materials.StainlessSteel), 'M', ItemList.Electric_Motor_HV, 'P', ItemList.Electric_Piston_HV, 'E', OrePrefix.circuit.get(Materials.Advanced), 'C', OrePrefix.cableGt01.get(Materials.Gold)});
                GT_ModHandler.addCraftingRecipe(ItemList.Robot_Arm_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CCC", "MSM", "PES", 'S', OrePrefix.stick.get(Materials.Titanium), 'M', ItemList.Electric_Motor_EV, 'P', ItemList.Electric_Piston_EV, 'E', OrePrefix.circuit.get(Materials.Elite), 'C', OrePrefix.cableGt01.get(Materials.Aluminium)});
                GT_ModHandler.addCraftingRecipe(ItemList.Robot_Arm_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"CCC", "MSM", "PES", 'S', OrePrefix.stick.get(Materials.TungstenSteel), 'M', ItemList.Electric_Motor_IV, 'P', ItemList.Electric_Piston_IV, 'E', OrePrefix.circuit.get(Materials.Master), 'C', OrePrefix.cableGt01.get(Materials.Tungsten)});

                GregTech_API.registerCover(ItemList.Robot_Arm_LV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[1][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ARM)), new GT_Cover_Arm(400));
                GregTech_API.registerCover(ItemList.Robot_Arm_MV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ARM)), new GT_Cover_Arm(100));
                GregTech_API.registerCover(ItemList.Robot_Arm_HV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[3][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ARM)), new GT_Cover_Arm(20));
                GregTech_API.registerCover(ItemList.Robot_Arm_EV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[4][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ARM)), new GT_Cover_Arm(4));
                GregTech_API.registerCover(ItemList.Robot_Arm_IV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[5][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ARM)), new GT_Cover_Arm(1));
                GregTech_API.registerCover(ItemList.Robot_Arm_LuV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[6][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ARM)), new GT_Cover_Arm(1));
                GregTech_API.registerCover(ItemList.Robot_Arm_ZPM.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[7][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ARM)), new GT_Cover_Arm(1));
                GregTech_API.registerCover(ItemList.Robot_Arm_UV.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[8][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ARM)), new GT_Cover_Arm(1));

                ItemList.Field_Generator_LV.set(addItem(tLastID = 670, "Field Generator (LV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 1L)}));
                ItemList.Field_Generator_MV.set(addItem(tLastID = 671, "Field Generator (MV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 2L)}));
                ItemList.Field_Generator_HV.set(addItem(tLastID = 672, "Field Generator (HV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 4L)}));
                ItemList.Field_Generator_EV.set(addItem(tLastID = 673, "Field Generator (EV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 8L)}));
                ItemList.Field_Generator_IV.set(addItem(tLastID = 674, "Field Generator (IV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 16L)}));
                ItemList.Field_Generator_LuV.set(addItem(tLastID = 675, "Field Generator (LuV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 16L)}));
                ItemList.Field_Generator_ZPM.set(addItem(tLastID = 676, "Field Generator (ZPM)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 16L)}));
                ItemList.Field_Generator_UV.set(addItem(tLastID = 677, "Field Generator (UV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 16L)}));

                GT_ModHandler.addCraftingRecipe(ItemList.Field_Generator_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"WCW", "CGC", "WCW", 'G', OrePrefix.gem.get(Materials.EnderPearl), 'C', OrePrefix.circuit.get(Materials.Basic), 'W', OrePrefix.wireGt01.get(Materials.Osmium)});
                GT_ModHandler.addCraftingRecipe(ItemList.Field_Generator_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"WCW", "CGC", "WCW", 'G', OrePrefix.gem.get(Materials.EnderEye), 'C', OrePrefix.circuit.get(Materials.Good), 'W', OrePrefix.wireGt02.get(Materials.Osmium)});

                ItemList.Emitter_LV.set(addItem(tLastID = 680, "Emitter (LV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUX, 1L)}));
                ItemList.Emitter_MV.set(addItem(tLastID = 681, "Emitter (MV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUX, 2L)}));
                ItemList.Emitter_HV.set(addItem(tLastID = 682, "Emitter (HV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUX, 4L)}));
                ItemList.Emitter_EV.set(addItem(tLastID = 683, "Emitter (EV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUX, 8L)}));
                ItemList.Emitter_IV.set(addItem(tLastID = 684, "Emitter (IV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUX, 16L)}));
                ItemList.Emitter_LuV.set(addItem(tLastID = 685, "Emitter (LuV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUX, 16L)}));
                ItemList.Emitter_ZPM.set(addItem(tLastID = 686, "Emitter (ZPM)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUX, 16L)}));
                ItemList.Emitter_UV.set(addItem(tLastID = 687, "Emitter (UV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUX, 16L)}));

                GT_ModHandler.addCraftingRecipe(ItemList.Emitter_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SSC", "WQS", "CWS", 'Q', OrePrefix.gem.get(Materials.Quartzite), 'S', OrePrefix.stick.get(Materials.Brass), 'C', OrePrefix.circuit.get(Materials.Basic), 'W', OrePrefix.cableGt01.get(Materials.Tin)});
                GT_ModHandler.addCraftingRecipe(ItemList.Emitter_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SSC", "WQS", "CWS", 'Q', OrePrefix.gem.get(Materials.NetherQuartz), 'S', OrePrefix.stick.get(Materials.Electrum), 'C', OrePrefix.circuit.get(Materials.Good), 'W', OrePrefix.cableGt01.get(Materials.AnyCopper)});
                GT_ModHandler.addCraftingRecipe(ItemList.Emitter_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SSC", "WQS", "CWS", 'Q', OrePrefix.gem.get(Materials.Emerald), 'S', OrePrefix.stick.get(Materials.Chrome), 'C', OrePrefix.circuit.get(Materials.Advanced), 'W', OrePrefix.cableGt01.get(Materials.Gold)});
                GT_ModHandler.addCraftingRecipe(ItemList.Emitter_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SSC", "WQS", "CWS", 'Q', OrePrefix.gem.get(Materials.EnderPearl), 'S', OrePrefix.stick.get(Materials.Platinum), 'C', OrePrefix.circuit.get(Materials.Elite), 'W', OrePrefix.cableGt01.get(Materials.Aluminium)});
                GT_ModHandler.addCraftingRecipe(ItemList.Emitter_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"SSC", "WQS", "CWS", 'Q', OrePrefix.gem.get(Materials.EnderEye), 'S', OrePrefix.stick.get(Materials.Osmium), 'C', OrePrefix.circuit.get(Materials.Master), 'W', OrePrefix.cableGt01.get(Materials.Tungsten)});

                ItemList.Sensor_LV.set(addItem(tLastID = 690, "Sensor (LV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 1L)}));
                ItemList.Sensor_MV.set(addItem(tLastID = 691, "Sensor (MV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 2L)}));
                ItemList.Sensor_HV.set(addItem(tLastID = 692, "Sensor (HV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 4L)}));
                ItemList.Sensor_EV.set(addItem(tLastID = 693, "Sensor (EV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 8L)}));
                ItemList.Sensor_IV.set(addItem(tLastID = 694, "Sensor (IV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 16L)}));
                ItemList.Sensor_LuV.set(addItem(tLastID = 695, "Sensor (LuV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 16L)}));
                ItemList.Sensor_ZPM.set(addItem(tLastID = 696, "Sensor (ZPM)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 16L)}));
                ItemList.Sensor_UV.set(addItem(tLastID = 697, "Sensor (UV)", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 16L)}));

                GT_ModHandler.addCraftingRecipe(ItemList.Sensor_LV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P Q", "PS ", "CPP", 'Q', OrePrefix.gem.get(Materials.Quartzite), 'S', OrePrefix.stick.get(Materials.Brass), 'P', OrePrefix.plate.get(Materials.Steel), 'C', OrePrefix.circuit.get(Materials.Basic)});
                GT_ModHandler.addCraftingRecipe(ItemList.Sensor_MV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P Q", "PS ", "CPP", 'Q', OrePrefix.gem.get(Materials.NetherQuartz), 'S', OrePrefix.stick.get(Materials.Electrum), 'P', OrePrefix.plate.get(Materials.Aluminium), 'C', OrePrefix.circuit.get(Materials.Good)});
                GT_ModHandler.addCraftingRecipe(ItemList.Sensor_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P Q", "PS ", "CPP", 'Q', OrePrefix.gem.get(Materials.Emerald), 'S', OrePrefix.stick.get(Materials.Chrome), 'P', OrePrefix.plate.get(Materials.StainlessSteel), 'C', OrePrefix.circuit.get(Materials.Advanced)});
                GT_ModHandler.addCraftingRecipe(ItemList.Sensor_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P Q", "PS ", "CPP", 'Q', OrePrefix.gem.get(Materials.EnderPearl), 'S', OrePrefix.stick.get(Materials.Platinum), 'P', OrePrefix.plate.get(Materials.Titanium), 'C', OrePrefix.circuit.get(Materials.Elite)});
                GT_ModHandler.addCraftingRecipe(ItemList.Sensor_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"P Q", "PS ", "CPP", 'Q', OrePrefix.gem.get(Materials.EnderEye), 'S', OrePrefix.stick.get(Materials.Osmium), 'P', OrePrefix.plate.get(Materials.TungstenSteel), 'C', OrePrefix.circuit.get(Materials.Master)});

                ItemList.Circuit_Primitive.set(addItem(tLastID = 700, "NAND Chip", "A very simple Circuit", new Object[]{OrePrefix.circuit.get(Materials.Primitive)}));
                ItemList.Circuit_Basic.set(addItem(tLastID = 701, "Basic Electronic Circuit", "A basic Circuit", new Object[]{OrePrefix.circuit.get(Materials.Basic)}));
                ItemList.Circuit_Good.set(addItem(tLastID = 702, "Good Electronic Circuit", "A good Circuit", new Object[]{OrePrefix.circuit.get(Materials.Good)}));
                ItemList.Circuit_Advanced.set(addItem(tLastID = 703, "Advanced Circuit", "An advanced Circuit", new Object[]{OrePrefix.circuit.get(Materials.Advanced)}));
                ItemList.Circuit_Data.set(addItem(tLastID = 704, "Data Storage Circuit", "A Data Storage Chip", new Object[]{OrePrefix.circuit.get(Materials.Data)}));
                ItemList.Circuit_Elite.set(addItem(tLastID = 705, "Data Control Circuit", "A Processor", new Object[]{OrePrefix.circuit.get(Materials.Elite)}));
                ItemList.Circuit_Master.set(addItem(tLastID = 706, "Energy Flow Circuit", "A High Voltage Processor", new Object[]{OrePrefix.circuit.get(Materials.Master)}));
                ItemList.Tool_DataOrb.set(addItem(tLastID = 707, "Data Orb", "A High Capacity Data Storage", new Object[]{OrePrefix.circuit.get(Materials.Ultimate), SubTag.NO_UNIFICATION, new Behaviour_DataOrb()}));
                ItemList.Circuit_Ultimate.set(ItemList.Tool_DataOrb.get(1L));
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Tool_DataOrb.get(1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Tool_DataOrb.get(1L)});
                ItemList.Tool_DataStick.set(addItem(tLastID = 708, "Data Stick", "A Low Capacity Data Storage", new Object[]{OrePrefix.circuit.get(Materials.Data), SubTag.NO_UNIFICATION, new Behaviour_DataStick()}));
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Tool_DataStick.get(1L), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{ItemList.Tool_DataStick.get(1L)});


                ItemList.Circuit_Board_Basic.set(addItem(tLastID = 710, "Basic Circuit Board", "A basic Board"));
                ItemList.Circuit_Board_Advanced.set(addItem(tLastID = 711, "Advanced Circuit Board", "An advanced Board"));
                ItemList.Circuit_Board_Elite.set(addItem(tLastID = 712, "Processor Board", "A Processor Board"));
                ItemList.Circuit_Parts_Crystal_Chip_Elite.set(addItem(tLastID = 713, "Engraved Crystal Chip", "Needed for Circuits"));
                ItemList.Circuit_Parts_Crystal_Chip_Master.set(addItem(tLastID = 714, "Engraved Lapotron Chip", "Needed for Circuits"));
                ItemList.Circuit_Parts_Advanced.set(addItem(tLastID = 715, "Advanced Circuit Parts", "Advanced Circuit Parts"));
                ItemList.Circuit_Parts_Wiring_Basic.set(addItem(tLastID = 716, "Etched Medium Voltage Wiring", "Part of Circuit Boards"));
                ItemList.Circuit_Parts_Wiring_Advanced.set(addItem(tLastID = 717, "Etched High Voltage Wiring", "Part of Circuit Boards"));
                ItemList.Circuit_Parts_Wiring_Elite.set(addItem(tLastID = 718, "Etched Extreme Voltage Wiring", "Part of Circuit Boards"));
                ItemList.Empty_Board_Basic.set(addItem(tLastID = 719, "Empty Circuit Board", "A Board Part"));
                ItemList.Empty_Board_Elite.set(addItem(tLastID = 720, "Empty Processor Board", "A Processor Board Part"));


                ItemList.Component_Sawblade_Diamond.set(addItem(tLastID = 721, "Diamond Sawblade", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 4L), OreDictNames.craftingDiamondBlade}));
                ItemList.Component_Grinder_Diamond.set(addItem(tLastID = 722, "Diamond Grinding Head", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 6L), OreDictNames.craftingGrinder}));
                ItemList.Component_Grinder_Tungsten.set(addItem(tLastID = 723, "Tungsten Grinding Head", "", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERDITIO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.METALLUM, 6L), OreDictNames.craftingGrinder}));

                ItemList.QuantumEye.set(addItem(tLastID = 724, "Quantum Eye", "Improved Ender Eye"));
                ItemList.QuantumStar.set(addItem(tLastID = 725, "Quantum Star", "Improved Nether Star"));
                ItemList.Gravistar.set(addItem(tLastID = 726, "Gravi Star", "Ultimate Nether Star"));

                GT_ModHandler.addCraftingRecipe(ItemList.Field_Generator_HV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"WCW", "CGC", "WCW", 'G', ItemList.QuantumEye.get(1L), 'C', OrePrefix.circuit.get(Materials.Advanced), 'W', OrePrefix.wireGt04.get(Materials.Osmium)});
                GT_ModHandler.addCraftingRecipe(ItemList.Field_Generator_EV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"WCW", "CGC", "WCW", 'G', OrePrefix.gem.get(Materials.NetherStar), 'C', OrePrefix.circuit.get(Materials.Elite), 'W', OrePrefix.wireGt08.get(Materials.Osmium)});
                GT_ModHandler.addCraftingRecipe(ItemList.Field_Generator_IV.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"WCW", "CGC", "WCW", 'G', ItemList.QuantumStar.get(1L), 'C', OrePrefix.circuit.get(Materials.Master), 'W', OrePrefix.wireGt16.get(Materials.Osmium)});

                GT_ModHandler.addCraftingRecipe(ItemList.Component_Sawblade_Diamond.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{" D ", "DGD", " D ", 'D', OrePrefix.dustSmall.get(Materials.Diamond), 'G', OrePrefix.gearGt.get(Materials.CobaltBrass)});
                GT_ModHandler.addCraftingRecipe(ItemList.Component_Grinder_Diamond.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"DSD", "SIS", "DSD", 'I', OreDictNames.craftingIndustrialDiamond, 'D', OrePrefix.dust.get(Materials.Diamond), 'S', OrePrefix.plate.get(Materials.Steel)});
                GT_ModHandler.addCraftingRecipe(ItemList.Component_Grinder_Tungsten.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"TST", "SIS", "TST", 'I', OreDictNames.craftingIndustrialDiamond, 'T', OrePrefix.plate.get(Materials.Tungsten), 'S', OrePrefix.plate.get(Materials.Steel)});

                ItemList.Upgrade_Muffler.set(addItem(tLastID = 727, "Muffler Upgrade", "Makes Machines silent", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 2L)}));
                ItemList.Upgrade_Lock.set(addItem(tLastID = 728, "Lock Upgrade", "Protects your Machines", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.TUTAMEN, 4L)}));

                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Aluminium, 1L), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Plastic, 2L), ItemList.Upgrade_Muffler.get(1L), 1600, 2);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Aluminium, 1L), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 2L), ItemList.Upgrade_Muffler.get(1L), 1600, 2);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Iron, 1L), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Plastic, 2L), ItemList.Upgrade_Muffler.get(1L), 1600, 2);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Iron, 1L), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 2L), ItemList.Upgrade_Muffler.get(1L), 1600, 2);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.WroughtIron, 1L), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Plastic, 2L), ItemList.Upgrade_Muffler.get(1L), 1600, 2);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.WroughtIron, 1L), OreDictionaryUnifier.get(OrePrefix.dust, Materials.Wood, 2L), ItemList.Upgrade_Muffler.get(1L), 1600, 2);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Aluminium, 1L), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Iridium, 1L), ItemList.Upgrade_Lock.get(1L), 6400, 16);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Iron, 1L), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Iridium, 1L), ItemList.Upgrade_Lock.get(1L), 6400, 16);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.WroughtIron, 1L), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Iridium, 1L), ItemList.Upgrade_Lock.get(1L), 6400, 16);

                ItemList.Component_Filter.set(addItem(tLastID = 729, "Item Filter", "", new Object[]{new ItemMaterialInfo(Materials.Zinc, OrePrefix.foil.mMaterialAmount * 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 1L), OreDictNames.craftingFilter}));

                GT_Values.RA.addAssemblerRecipe(GT_ModHandler.getIC2Item(ItemName.crafting, CraftingItemType.carbon_mesh, 4), OreDictionaryUnifier.get(OrePrefix.foil, Materials.Zinc, 16L), Materials.Plastic.getMolten(144), ItemList.Component_Filter.get(1L), 1600, 32);

                ItemList.Cover_Controller.set(addItem(tLastID = 730, "Machine Controller", "Turns Machines ON/OFF", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L)}));
                ItemList.Cover_ActivityDetector.set(addItem(tLastID = 731, "Activity Detector", "Gives out Activity as Redstone", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L)}));
                ItemList.Cover_FluidDetector.set(addItem(tLastID = 732, "Fluid Detector", "Gives out Fluid Amount as Redstone", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 1L)}));
                ItemList.Cover_ItemDetector.set(addItem(tLastID = 733, "Item Detector", "Gives out Item Amount as Redstone", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TERRA, 1L)}));
                ItemList.Cover_EnergyDetector.set(addItem(tLastID = 734, "Energy Detector", "Gives out Energy Amount as Redstone", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L)}));
                ItemList.Cover_PlayerDetector.set(addItem(tLastID = 735, "Player Detector", "Gives out close Players as Redstone", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L)}));
                GT_Values.RA.addAssemblerRecipe(ItemList.Sensor_EV.get(1L), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Titanium, 1L), ItemList.Cover_PlayerDetector.get(1L), 3200, 128);

                GregTech_API.registerCover(ItemList.Cover_Controller.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_CONTROLLER)), new GT_Cover_ControlsWork());
                GregTech_API.registerCover(ItemList.Cover_ActivityDetector.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ACTIVITYDETECTOR)), new GT_Cover_DoesWork());
                GregTech_API.registerCover(ItemList.Cover_FluidDetector.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FLUIDDETECTOR)), new GT_Cover_LiquidMeter());
                GregTech_API.registerCover(ItemList.Cover_ItemDetector.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ITEMDETECTOR)), new GT_Cover_ItemMeter());
                GregTech_API.registerCover(ItemList.Cover_EnergyDetector.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ENERGYDETECTOR)), new GT_Cover_EUMeter());
                GregTech_API.registerCover(ItemList.Cover_PlayerDetector.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ACTIVITYDETECTOR)), new GT_Cover_PlayerDetector());

                ItemList.Cover_Screen.set(addItem(tLastID = 740, "Computer Monitor", "Displays Data", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.COGNITIO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.LUX, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITREUS, 1L)}));
                ItemList.Cover_Crafting.set(addItem(tLastID = 744, "Crafting Table Cover", "Better than a wooden Workbench", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.FABRICO, 4L)}));
                ItemList.Cover_Drain.set(addItem(tLastID = 745, "Drain", "Absorbs Fluids and collects Rain", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.VACUOS, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.AQUA, 2L)}));

                ItemList.Cover_Shutter.set(addItem(tLastID = 749, "Shutter Module", "Blocks Inventory/Tank Side. Usage together with Machine Controller.", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.ITER, 1L)}));

                GT_ModHandler.addCraftingRecipe(ItemList.Cover_Screen.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"AGA", "RPB", "ALA", 'A', OrePrefix.plate.get(Materials.Aluminium), 'L', OrePrefix.dust.get(Materials.Glowstone), 'R', Dyes.dyeRed, 'G', Dyes.dyeLime, 'B', Dyes.dyeBlue, 'P', OrePrefix.plate.get(Materials.Glass)});

                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Aluminium, 2L), new ItemStack(Blocks.IRON_BARS, 2), ItemList.Cover_Drain.get(1L), 800, 16);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Iron, 2L), new ItemStack(Blocks.IRON_BARS, 2), ItemList.Cover_Drain.get(1L), 800, 16);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.WroughtIron, 2L), new ItemStack(Blocks.IRON_BARS, 2), ItemList.Cover_Drain.get(1L), 800, 16);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Aluminium, 1L), new ItemStack(Blocks.CRAFTING_TABLE, 1), ItemList.Cover_Crafting.get(1L), 800, 16);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.Iron, 1L), new ItemStack(Blocks.CRAFTING_TABLE, 1), ItemList.Cover_Crafting.get(1L), 800, 16);
                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.plate, Materials.WroughtIron, 1L), new ItemStack(Blocks.CRAFTING_TABLE, 1), ItemList.Cover_Crafting.get(1L), 800, 16);
                GT_Values.RA.addAssemblerRecipe(ItemList.Cover_Shutter.get(1L), OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Advanced, 2), ItemList.FluidFilter.get(1L), 800, 4);

                GregTech_API.registerCover(ItemList.Cover_Screen.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SCREEN)), new GT_Cover_Screen());
                GregTech_API.registerCover(ItemList.Cover_Crafting.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[1][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_CRAFTING)), new GT_Cover_Crafting());
                GregTech_API.registerCover(ItemList.Cover_Drain.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[0][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_DRAIN)), new GT_Cover_Drain());
                GregTech_API.registerCover(ItemList.Cover_Shutter.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[1][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_SHUTTER)), new GT_Cover_Shutter());

                ItemList.Cover_SolarPanel.set(addItem(tLastID = 750, "Solar Panel", "May the Sun be with you", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 1L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 1L)}));
                ItemList.Cover_SolarPanel_8V.set(addItem(tLastID = 751, "Solar Panel (8V)", "8 Volt Solar Panel", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 2L)}));
                ItemList.Cover_SolarPanel_LV.set(addItem(tLastID = 752, "Solar Panel (LV)", "Low Voltage Solar Panel", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 4L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 4L)}));
                ItemList.Cover_SolarPanel_MV.set(addItem(tLastID = 753, "Solar Panel (MV)", "Medium Voltage Solar Panel", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 8L)}));
                ItemList.Cover_SolarPanel_HV.set(addItem(tLastID = 754, "Solar Panel (HV)", "High Voltage Solar Panel", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 16L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 16L)}));
                ItemList.Cover_SolarPanel_EV.set(addItem(tLastID = 755, "Solar Panel (EV)", "Extreme Solar Panel", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 32L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 32L)}));
                ItemList.Cover_SolarPanel_IV.set(addItem(tLastID = 756, "Solar Panel (IV)", "Insane Solar Panel", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 64L)}));
                ItemList.Cover_SolarPanel_LuV.set(addItem(tLastID = 757, "Solar Panel (LuV)", "Ludicrous Solar Panel", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 64L)}));
                ItemList.Cover_SolarPanel_ZPM.set(addItem(tLastID = 758, "Solar Panel (ZPM)", "ZPM Voltage Solar Panel", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 64L)}));
                ItemList.Cover_SolarPanel_UV.set(addItem(tLastID = 759, "Solar Panel (UV)", "Ultimate Solar Panel", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.POTENTIA, 64L), new TC_Aspects.TC_AspectStack(TC_Aspects.TENEBRAE, 64L)}));

                GregTech_API.registerCover(ItemList.Cover_SolarPanel.get(1L), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL), new GT_Cover_SolarPanel(1));
                GregTech_API.registerCover(ItemList.Cover_SolarPanel_8V.get(1L), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_8V), new GT_Cover_SolarPanel(8));
                GregTech_API.registerCover(ItemList.Cover_SolarPanel_LV.get(1L), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_LV), new GT_Cover_SolarPanel(32));
                GregTech_API.registerCover(ItemList.Cover_SolarPanel_MV.get(1L), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_MV), new GT_Cover_SolarPanel(128));
                GregTech_API.registerCover(ItemList.Cover_SolarPanel_HV.get(1L), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_HV), new GT_Cover_SolarPanel(512));
                GregTech_API.registerCover(ItemList.Cover_SolarPanel_EV.get(1L), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_EV), new GT_Cover_SolarPanel(2048));
                GregTech_API.registerCover(ItemList.Cover_SolarPanel_IV.get(1L), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_IV), new GT_Cover_SolarPanel(8192));
                GregTech_API.registerCover(ItemList.Cover_SolarPanel_LuV.get(1L), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_LuV), new GT_Cover_SolarPanel(32768));
                GregTech_API.registerCover(ItemList.Cover_SolarPanel_ZPM.get(1L), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_ZPM), new GT_Cover_SolarPanel(131072));
                GregTech_API.registerCover(ItemList.Cover_SolarPanel_UV.get(1L), new GT_RenderedTexture(Textures.BlockIcons.SOLARPANEL_UV), new GT_Cover_SolarPanel(524288));

                ItemList.Tool_Sonictron.set(addItem(tLastID = 760, "Sonictron", "Bring your Music with you", new Object[]{Behaviour_Sonictron.INSTANCE, new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 4L)}));
                ItemList.Tool_Cheat.set(addItem(tLastID = 761, "Debug Scanner", "Also an Infinite Energy Source", new Object[]{Behaviour_Scanner.INSTANCE, new TC_Aspects.TC_AspectStack(TC_Aspects.NEBRISUM, 64L)}));
                setElectricStats(32000 + tLastID, -2000000000L, 1000000000L, -1L, -3L, false);
                ItemList.Tool_Scanner.set(addItem(tLastID = 762, "Portable Scanner", "Tricorder", new Object[]{Behaviour_Scanner.INSTANCE, new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 6L), new TC_Aspects.TC_AspectStack(TC_Aspects.SENSUS, 6L)}));
                setElectricStats(32000 + tLastID, 400000L, GT_Values.V[2], 2L, -1L, false);
                GT_ModHandler.addCraftingRecipe(ItemList.Tool_Scanner.get(1L), GT_ModHandler.RecipeBits.DISMANTLEABLE | GT_ModHandler.RecipeBits.NOT_REMOVABLE | GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"EPR", "CSC", "PBP", 'C', OrePrefix.circuit.get(Materials.Advanced), 'P', OrePrefix.plate.get(Materials.Aluminium), 'E', ItemList.Emitter_MV, 'R', ItemList.Sensor_MV, 'S', ItemList.Cover_Screen, 'B', ItemList.Battery_RE_MV_Lithium});
                ItemList.NC_SensorKit.set(addItem(tLastID = 763, "GregTech Sensor Kit", "", new Object[]{new Behaviour_SensorKit()}));
                ItemList.Duct_Tape.set(addItem(tLastID = 764, "BrainTech Aerospace Advanced Reinforced Duct Tape FAL-84", "If you can't fix it with this, use more of it!", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.INSTRUMENTUM, 2L), OreDictNames.craftingDuctTape}));
                ItemList.McGuffium_239.set(addItem(tLastID = 765, "Mc Guffium 239", "42% better than Phlebotnium", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ALIENIS, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.PERMUTATIO, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.SPIRITUS, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.AURAM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.VITIUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.RADIO, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.MAGNETO, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.ELECTRUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.NEBRISUM, 8L), new TC_Aspects.TC_AspectStack(TC_Aspects.STRONTIO, 8L)}));

                GT_Values.RA.addAssemblerRecipe(OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Good, 4L), OreDictionaryUnifier.get(OrePrefix.plate, Materials.StainlessSteel, 2L), ItemList.Schematic.get(1L), 3200, 4);
                GT_Values.RA.addAssemblerRecipe(ItemList.Sensor_LV.get(1L), ItemList.Emitter_LV.get(1L), ItemList.NC_SensorKit.get(1L), 1600, 2);

                ItemList.Cover_RedstoneTransmitterExternal.set(addItem(tLastID = 741, "Redstone Transmitter (Out)", "Transfers Redstonesignals wireless", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L)}));
                ItemList.Cover_RedstoneTransmitterInternal.set(addItem(tLastID = 742, "Redstone Transmitter (In)", "Transfers Redstonesignals wireless", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L)}));
                ItemList.Cover_RedstoneReceiverExternal.set(addItem(tLastID = 746, "Redstone Receiver (Out)", "Transfers Redstonesignals wireless", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L)}));
                ItemList.Cover_RedstoneReceiverInternal.set(addItem(tLastID = 747, "Redstone Receiver (In)", "Transfers Redstonesignals wireless", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L)}));

                GregTech_API.registerCover(ItemList.Cover_RedstoneTransmitterExternal.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ACTIVITYDETECTOR)), new GT_Cover_RedstoneTransmitterExternal());
                GregTech_API.registerCover(ItemList.Cover_RedstoneTransmitterInternal.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ACTIVITYDETECTOR)), new GT_Cover_RedstoneTransmitterInternal());
                GregTech_API.registerCover(ItemList.Cover_RedstoneReceiverExternal.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FLUIDDETECTOR)), new GT_Cover_RedstoneReceiverExternal());
                GregTech_API.registerCover(ItemList.Cover_RedstoneReceiverInternal.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_FLUIDDETECTOR)), new GT_Cover_RedstoneReceiverInternal());

                GT_Values.RA.addAssemblerRecipe(ItemList.Emitter_EV.get(1L), OreDictionaryUnifier.get(OrePrefix.plate, Materials.StainlessSteel, 1L), ItemList.Cover_RedstoneTransmitterExternal.get(1L), 3200, 128);
                GT_Values.RA.addAssemblerRecipe(ItemList.Sensor_EV.get(1L), OreDictionaryUnifier.get(OrePrefix.plate, Materials.StainlessSteel, 1L), ItemList.Cover_RedstoneReceiverExternal.get(1L), 3200, 128);
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Cover_RedstoneTransmitterInternal.get(1L), new Object[]{ItemList.Cover_RedstoneTransmitterExternal.get(1L)});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Cover_RedstoneReceiverInternal.get(1L), new Object[]{ItemList.Cover_RedstoneReceiverExternal.get(1L)});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Cover_RedstoneTransmitterExternal.get(1L), new Object[]{ItemList.Cover_RedstoneTransmitterInternal.get(1L)});
                GT_ModHandler.addShapelessCraftingRecipe(ItemList.Cover_RedstoneReceiverExternal.get(1L), new Object[]{ItemList.Cover_RedstoneReceiverInternal.get(1L)});

                ItemList.Cover_NeedsMaintainance.set(addItem(tLastID = 748, "Needs Maintainance Cover", "Attach to Multiblock Controller. Emits Redstone Signal if needs Maintainance", new Object[]{new TC_Aspects.TC_AspectStack(TC_Aspects.ORDO, 2L), new TC_Aspects.TC_AspectStack(TC_Aspects.MACHINA, 1L)}));
                GregTech_API.registerCover(ItemList.Cover_NeedsMaintainance.get(1L), new GT_MultiTexture(Textures.BlockIcons.MACHINE_CASINGS[2][0], new GT_RenderedTexture(Textures.BlockIcons.OVERLAY_ACTIVITYDETECTOR)), new GT_Cover_NeedMaintainance());
                GT_Values.RA.addAssemblerRecipe(ItemList.Emitter_MV.get(1L), OreDictionaryUnifier.get(OrePrefix.plate, Materials.Aluminium, 1L), ItemList.Cover_NeedsMaintainance.get(1L), 600, 24);

                GT_Values.RA.addAssemblerRecipe(ItemList.Electric_Pump_LV.get(1L), OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Basic, 2), 	ItemList.FluidRegulator_LV.get(1L), 800, 4);
                GT_Values.RA.addAssemblerRecipe(ItemList.Electric_Pump_MV.get(1L), OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Good, 2), 	ItemList.FluidRegulator_MV.get(1L), 800, 8);
                GT_Values.RA.addAssemblerRecipe(ItemList.Electric_Pump_HV.get(1L), OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Advanced, 2), ItemList.FluidRegulator_HV.get(1L), 800, 16);
                GT_Values.RA.addAssemblerRecipe(ItemList.Electric_Pump_EV.get(1L), OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Elite, 2), 	ItemList.FluidRegulator_EV.get(1L), 800, 32);
                GT_Values.RA.addAssemblerRecipe(ItemList.Electric_Pump_IV.get(1L), OreDictionaryUnifier.get(OrePrefix.circuit, Materials.Master, 2), 	ItemList.FluidRegulator_IV.get(1L), 800, 64);

        }

        public boolean onEntityItemUpdate(EntityItem aItemEntity) {
                int aDamage = aItemEntity.getEntityItem().getItemDamage();
                if ((aDamage < 32000) && (aDamage >= 0) && (!aItemEntity.worldObj.isRemote)) {
                        Materials aMaterial = GregTech_API.sGeneratedMaterials[(aDamage % 1000)];
                        if ((aMaterial != null) && (aMaterial != Materials.Empty) && (aMaterial != Materials._NULL)) {
                                int tX = MathHelper.floor_double(aItemEntity.posX);
                                int tY = MathHelper.floor_double(aItemEntity.posY);
                                int tZ = MathHelper.floor_double(aItemEntity.posZ);
                                OrePrefix aPrefix = this.mGeneratedPrefixList[(aDamage / 1000)];
                                if (aPrefix == OrePrefix.dustImpure || aPrefix == OrePrefix.dustPure || aPrefix == OrePrefix.crushed || aPrefix == OrePrefix.dust) {
                                        IBlockState tBlock = aItemEntity.worldObj.getBlockState(new BlockPos(tX, tY, tZ));
                                        if(tBlock.getBlock() == Blocks.CAULDRON) {
                                                int waterLevel = tBlock.getValue(BlockCauldron.LEVEL);
                                                if(waterLevel > 0) {
                                                        if(aPrefix == OrePrefix.crushed) {
                                                                aItemEntity.setEntityItemStack(OreDictionaryUnifier.get(OrePrefix.crushedPurified, aMaterial, aItemEntity.getEntityItem().stackSize));
                                                        } else if (aPrefix == OrePrefix.dust && aMaterial == Materials.Wheat) {
                                                                aItemEntity.setEntityItemStack(ItemList.Food_Dough.get(aItemEntity.getEntityItem().stackSize, new Object[]{}));
                                                        } else {
                                                                aItemEntity.setEntityItemStack(OreDictionaryUnifier.get(OrePrefix.dust, aMaterial, aItemEntity.getEntityItem().stackSize));
                                                        }
                                                        aItemEntity.worldObj.setBlockState(new BlockPos(tX, tY, tZ), tBlock.withProperty(BlockCauldron.LEVEL, waterLevel - 1));
                                                        return true;
                                                }
                                        }
                                }
                        }
                }
                return false;
        }

        protected void addAdditionalToolTips(List aList, ItemStack aStack, EntityPlayer aPlayer) {
                super.addAdditionalToolTips(aList, aStack, aPlayer);
                int aDamage = aStack.getItemDamage();
                if ((aDamage < 32000) && (aDamage >= 0)) {
                        Materials aMaterial = GregTech_API.sGeneratedMaterials[(aDamage % 1000)];
                        if ((aMaterial != null) && (aMaterial != Materials.Empty) && (aMaterial != Materials._NULL)) {
                                OrePrefix aPrefix = this.mGeneratedPrefixList[(aDamage / 1000)];
                                if ((aPrefix == OrePrefix.dustImpure) || (aPrefix == OrePrefix.dustPure)) {
                                        aList.add(this.mToolTipPurify);
                                }
                        }
                }
        }

        public boolean isPlasmaCellUsed(OrePrefix aPrefix, Materials aMaterial) {
                Collection<GT_Recipe> fusionRecipes = GT_Recipe.GT_Recipe_Map.sFusionRecipes.mRecipeList;
                if(aPrefix == OrePrefix.cellPlasma && aMaterial.getPlasma(1L) != null) { //Materials has a plasma fluid
                        for(GT_Recipe recipe : fusionRecipes) { //Loop through fusion recipes
                                if(recipe.getFluidOutput(0) != null) { //Make sure fluid output can't be null (not sure if possible)
                                        if (recipe.getFluidOutput(0).isFluidEqual(aMaterial.getPlasma(1L)))
                                                return true; //Fusion recipe output matches current plasma cell fluid
                                }
                        }
                }
                return false;
        }

        public boolean doesShowInCreative(OrePrefix aPrefix, Materials aMaterial, boolean aDoShowAllItems) {
                return (aDoShowAllItems) || (((aPrefix != OrePrefix.gem) || (!aMaterial.mName.startsWith("Infused"))) && (aPrefix != OrePrefix.dustTiny) && (aPrefix != OrePrefix.dustSmall) && (aPrefix != OrePrefix.dustImpure) && (aPrefix != OrePrefix.dustPure) && (aPrefix != OrePrefix.crushed) && (aPrefix != OrePrefix.crushedPurified) && (aPrefix != OrePrefix.crushedCentrifuged) && (aPrefix != OrePrefix.ingotHot) && !(aPrefix == OrePrefix.cellPlasma && !isPlasmaCellUsed(aPrefix, aMaterial)));
        }

        public ItemStack getContainerItem(ItemStack aStack) {
                int aDamage = aStack.getItemDamage();
                if ((aDamage >= 32430) && (aDamage <= 32461)) {
                        return ItemList.Spray_Empty.get(1L);
                }
                if ((aDamage == 32479) || (aDamage == 32476)) {
                        return new ItemStack(this, 1, aDamage - 2);
                }
                if (aDamage == 32401) {
                        return new ItemStack(this, 1, aDamage - 1);
                }
                return super.getContainerItem(aStack);
        }

        public boolean doesMaterialAllowGeneration(OrePrefix aPrefix, Materials aMaterial) {
                return (super.doesMaterialAllowGeneration(aPrefix, aMaterial));
        }
}