package gregtech.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.interfaces.IOreRecipeRegistrator;
import gregtech.api.items.GT_Generic_Block;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class GT_Block_Stones_Abstract
        extends GT_Generic_Block
        implements IOreRecipeRegistrator {
    public GT_Block_Stones_Abstract(Class<? extends ItemBlock> aItemClass, String aName) {
        super(aItemClass, aName, Material.rock);
        OrePrefixes.crafting.add(this);
        setStepSound(soundTypeStone);
        setCreativeTab(GregTech_API.TAB_GREGTECH_MATERIALS);
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 0), new ItemStack(this, 1, 7));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 1), new ItemStack(this, 1, 0));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 2), new ItemStack(this, 1, 0));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 3), new ItemStack(this, 1, 0));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 4), new ItemStack(this, 1, 0));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 5), new ItemStack(this, 1, 0));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 6), new ItemStack(this, 1, 0));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 7), new ItemStack(this, 1, 0));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 8), new ItemStack(this, 1, 15));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 9), new ItemStack(this, 1, 8));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 10), new ItemStack(this, 1, 8));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 11), new ItemStack(this, 1, 8));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 12), new ItemStack(this, 1, 8));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 13), new ItemStack(this, 1, 8));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 14), new ItemStack(this, 1, 8));
        GT_ModHandler.addSmeltingRecipe(new ItemStack(this, 1, 15), new ItemStack(this, 1, 8));
        GT_Values.RA.addAssemblerRecipe(new ItemStack(this, 1, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 4L, new Object[0]), new ItemStack(this, 1, 3), 50, 4);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(this, 1, 8), ItemList.Circuit_Integrated.getWithDamage(0L, 4L, new Object[0]), new ItemStack(this, 1, 11), 50, 4);
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 6), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"f", "X", 'X', new ItemStack(this, 1, 7)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 14), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"f", "X", 'X', new ItemStack(this, 1, 15)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 4), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(this, 1, 3)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 12), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(this, 1, 11)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 1), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(this, 1, 0)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 1, 9), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"h", "X", 'X', new ItemStack(this, 1, 8)});
        GT_Values.RA.addForgeHammerRecipe(new ItemStack(this, 1, 3), new ItemStack(this, 1, 4), 16, 10);
        GT_Values.RA.addForgeHammerRecipe(new ItemStack(this, 1, 11), new ItemStack(this, 1, 12), 16, 10);
        GT_Values.RA.addForgeHammerRecipe(new ItemStack(this, 1, 0), new ItemStack(this, 1, 1), 16, 10);
        GT_Values.RA.addForgeHammerRecipe(new ItemStack(this, 1, 8), new ItemStack(this, 1, 9), 16, 10);
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 4, 3), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"XX", "XX", 'X', new ItemStack(this, 4, 0)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 4, 11), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"XX", "XX", 'X', new ItemStack(this, 4, 8)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 4, 3), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"XX", "XX", 'X', new ItemStack(this, 4, 7)});
        GT_ModHandler.addCraftingRecipe(new ItemStack(this, 4, 11), GT_ModHandler.RecipeBits.NOT_REMOVABLE, new Object[]{"XX", "XX", 'X', new ItemStack(this, 4, 15)});
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        if (aOreDictName.equals(OreDictNames.craftingLensWhite.toString())) {
            GT_Values.RA.addLaserEngraverRecipe(new ItemStack(this, 1, 7), GT_Utility.copyAmount(0L, new Object[]{aStack}), new ItemStack(this, 1, 6), 50, 16);
            GT_Values.RA.addLaserEngraverRecipe(new ItemStack(this, 1, 15), GT_Utility.copyAmount(0L, new Object[]{aStack}), new ItemStack(this, 1, 14), 50, 16);
        }
    }
    @Override
    public String getHarvestTool(int aMeta) {
        return "pickaxe";
    }
    @Override
    public int getHarvestLevel(int aMeta) {
        return 1;
    }
    @Override
    public float getBlockHardness(World aWorld, int aX, int aY, int aZ) {
        return this.blockHardness = Blocks.stone.getBlockHardness(aWorld, aX, aY, aZ) * 3.0F;
    }
    @Override
    public String getUnlocalizedName() {
        return this.mUnlocalizedName;
    }
    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal(this.mUnlocalizedName + ".name");
    }
    @Override
    public boolean canBeReplacedByLeaves(IBlockAccess aWorld, int aX, int aY, int aZ) {
        return false;
    }
    @Override
    public boolean isNormalCube(IBlockAccess aWorld, int aX, int aY, int aZ) {
        return true;
    }
    @Override
    public boolean renderAsNormalBlock() {
        return true;
    }
    @Override
    public boolean isOpaqueCube() {
        return true;
    }
    @Override
    public IIcon getIcon(int aSide, int aMeta) {
        if ((aMeta >= 0) && (aMeta < 16)) {
            return gregtech.api.enums.Textures.BlockIcons.GRANITES[aMeta].getIcon();
        }
        return null;
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z) % 8 < 3;
    }
    @Override
    public int damageDropped(int par1) {
        return par1 % 8 == 0 ? par1 + 1 : par1;
    }
    @Override
    public int getDamageValue(World par1World, int par2, int par3, int par4) {
        return par1World.getBlockMetadata(par2, par3, par4);
    }
    @Override
    public int quantityDropped(Random par1Random) {
        return 1;
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return Item.getItemFromBlock(this);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister aIconRegister) {
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item aItem, CreativeTabs par2CreativeTabs, List aList) {
        for (int i = 0; i < 16; i++) {
            aList.add(new ItemStack(aItem, 1, i));
        }
    }
}
