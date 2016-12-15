package gregtech.common.blocks;

import com.google.common.collect.Lists;
import gregtech.api.GregTech_API;
import gregtech.api.enums.*;
import gregtech.api.interfaces.IOreRecipeRegistrator;
import gregtech.api.items.GT_Generic_Block;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public abstract class GT_Block_Stones_Abstract extends GT_Generic_Block implements IOreRecipeRegistrator {

    public GT_Block_Stones_Abstract(Class<? extends ItemBlock> aItemClass, String aName) {
        super(aItemClass, aName, Material.ROCK);
        OrePrefixes.crafting.add(this);
        setSoundType(SoundType.STONE);
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
        GT_Values.RA.addAssemblerRecipe(new ItemStack(this, 1, 0), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), new ItemStack(this, 1, 3), 50, 4);
        GT_Values.RA.addAssemblerRecipe(new ItemStack(this, 1, 8), ItemList.Circuit_Integrated.getWithDamage(0L, 4L), new ItemStack(this, 1, 11), 50, 4);
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
            GT_Values.RA.addLaserEngraverRecipe(new ItemStack(this, 1, 7), GT_Utility.copyAmount(0L, aStack), new ItemStack(this, 1, 6), 50, 16);
            GT_Values.RA.addLaserEngraverRecipe(new ItemStack(this, 1, 15), GT_Utility.copyAmount(0L, aStack), new ItemStack(this, 1, 14), 50, 16);
        }
    }

    @Override
    public String getHarvestTool(IBlockState state) {
        return "pickaxe";
    }

    @Override
    public int getHarvestLevel(IBlockState state) {
        return 1;
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return Blocks.STONE.getBlockHardness(blockState, worldIn, pos);
    }

    @Override
    public String getUnlocalizedName() {
        return this.mUnlocalizedName;
    }

    @Override
    public String getLocalizedName() {
        return GT_LanguageManager.getTranslation(this.mUnlocalizedName + ".name");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getIcon(EnumFacing aSide, int aMeta) {
        if ((aMeta >= 0) && (aMeta < 16)) {
            return gregtech.api.enums.Textures.BlockIcons.GRANITES[aMeta].getIcon();
        }
        return null;
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
        return state.getValue(METADATA) % 8 < 3;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return Lists.newArrayList(new ItemStack(this, 1, state.getValue(METADATA)));
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item aItem, CreativeTabs par2CreativeTabs, List<ItemStack> aList) {
        for (int i = 0; i < 16; i++) {
            aList.add(new ItemStack(aItem, 1, i));
        }
    }
}
