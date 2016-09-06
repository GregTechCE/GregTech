package gregtech.common.blocks;

import com.google.common.collect.Lists;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.Textures;
import gregtech.api.items.GT_Generic_Block;
import gregtech.api.objects.ItemData;
import gregtech.api.objects.MaterialStack;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import gregtech.common.render.newblocks.IBlockIconProvider;
import ic2.core.block.EntityIC2Explosive;
import ic2.core.block.EntityItnt;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class GT_Block_Reinforced extends GT_Generic_Block implements IBlockIconProvider {

    public GT_Block_Reinforced(String aName) {
        super(GT_Item_Storage.class, aName, new GT_Material_Reinforced());
        setSoundType(SoundType.STONE);
        setCreativeTab(GregTech_API.TAB_GREGTECH);
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".0.name", "Bronzeplate Reinforced Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".1.name", "Iridium-Tungstensteel Reinforced Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".2.name", "Plascrete Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".3.name", "Tungstensteel Reinforced Block");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".4.name", "Brittle Charcoal");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".5.name", "Powderbarrel");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".6.name", "Solid Super Fuel");
        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + ".7.name", "Magic Solid Super Fuel");
        ItemList.Block_BronzePlate.set(new ItemStack(this.setHardness(60.0f).setResistance(150.0f), 1, 0));
        ItemList.Block_IridiumTungstensteel.set(new ItemStack(this.setHardness(200.0f).setResistance(600.0f), 1, 1));
        ItemList.Block_Plascrete.set(new ItemStack(this.setHardness(80.0f).setResistance(350.0f), 1, 2));
        ItemList.Block_TungstenSteelReinforced.set(new ItemStack(this.setHardness(100.0f).setResistance(400.0f), 1, 3));
        ItemList.Block_BrittleCharcoal.set(new ItemStack(this.setHardness(0.5f).setResistance(8.0f), 1, 4));
        ItemList.Block_Powderbarrel.set(new ItemStack(this.setHardness(2.5f).setResistance(2.0f), 1, 5));
        ItemList.Block_SSFUEL.set(new ItemStack(this.setHardness(2.5f).setResistance(2.0f), 1, 6));
        ItemList.Block_MSSFUEL.set(new ItemStack(this.setHardness(2.5f).setResistance(2.0f), 1, 7));
        GT_ModHandler.addCraftingRecipe(ItemList.Block_BronzePlate.get(1L),GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"hP ", "PBP", " P ", 'P', OrePrefixes.plate.get(Materials.Bronze), 'B', OrePrefixes.stone.get(Materials.GraniteBlack)});
        GT_ModHandler.addCraftingRecipe(ItemList.Block_BronzePlate.get(1L),GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"hP ", "PBP", " P ", 'P', OrePrefixes.plate.get(Materials.Bronze), 'B', OrePrefixes.stone.get(Materials.GraniteRed)});
        GT_ModHandler.addCraftingRecipe(ItemList.Block_IridiumTungstensteel.get(1L),GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"hBP", 'P', OrePrefixes.plate.get(Materials.Iridium), 'B', ItemList.Block_TungstenSteelReinforced.get(1L)});
        GT_OreDictUnificator.setItemData(ItemList.Block_IridiumTungstensteel.get(1), new ItemData(new MaterialStack(Materials.Iridium, OrePrefixes.plate.mMaterialAmount), new MaterialStack(Materials.TungstenSteel, 2*OrePrefixes.plate.mMaterialAmount),new MaterialStack(Materials.Concrete, OrePrefixes.dust.mMaterialAmount)));
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Items.COAL, 1, 1), new Object[]{ItemList.Block_BrittleCharcoal.get(1)});
        GT_ModHandler.addCraftingRecipe(ItemList.Block_Powderbarrel.get(1L),GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"WSW","GGG","WGW", 'W', OrePrefixes.plank.get(Materials.Wood), 'G', new ItemStack(Items.GUNPOWDER,1),'S',new ItemStack(Items.STRING ,1)});
        
    }

    @Override
    public String getHarvestTool(IBlockState state) {
        int aMeta = state.getValue(METADATA);
        if (aMeta == 5 || aMeta == 4 || aMeta == 6 || aMeta == 7) return "axe";
        return "pickaxe";
    }

    @Override
    public int getHarvestLevel(IBlockState blockState) {
        int aMeta = blockState.getValue(METADATA);
        if (aMeta == 4||aMeta == 5 || aMeta == 6 || aMeta == 7) return 1;
        return 4;
    }

    @Override
    public TextureAtlasSprite getIcon(EnumFacing aSide, int aMeta) {
        if ((aMeta >= 0) && (aMeta < 16)) {
            switch (aMeta) {
                case 0:
                    return Textures.BlockIcons.BLOCK_BRONZEPREIN.getIcon();
                case 1:
                    return Textures.BlockIcons.BLOCK_IRREIN.getIcon();
                case 2:
                    return Textures.BlockIcons.BLOCK_PLASCRETE.getIcon();
                case 3:
                    return Textures.BlockIcons.BLOCK_TSREIN.getIcon();
                case 4:
                    return GT_Utility.getTexture("minecraft:blocks/coal_block");
                case 5:
                	return Textures.BlockIcons.COVER_WOOD_PLATE.getIcon();
                case 6:
                	return GT_Utility.getTexture("minecraft:blocks/coal_block");
                case 7:
                	return GT_Utility.getTexture("minecraft:blocks/coal_block");
            }
        }
        return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        int tMeta = blockState.getValue(METADATA);
        if (tMeta == 0) {
            return 60.0F;
        }
        if (tMeta == 1) {
            return 200.0F;
        }
        if (tMeta == 2) {
            return 80.0F;
        }
        if (tMeta == 3) {
            return 100.0F;
        }
        if (tMeta == 4||tMeta == 5 || tMeta == 6 || tMeta == 7) {
            return 0.5F;
        }
        return Blocks.IRON_BLOCK.getBlockHardness(blockState, worldIn, pos);
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
        int tMeta = world.getBlockState(pos).getValue(METADATA);
        if (tMeta == 0) {
            return 150.0F;
        }
        if (tMeta == 1) {
            return 600.0F;
        }
        if (tMeta == 2) {
            return 350.0F;
        }
        if (tMeta == 3) {
            return 400.0F;
        }
        if (tMeta == 4 || tMeta == 6 || tMeta == 7) {
            return 8.0F;
        }
        if (tMeta == 5) {
            return 1.0F;
        }
        return Blocks.IRON_BLOCK.getExplosionResistance(world, pos, exploder, explosion);
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
    public boolean isBlockNormalCube(IBlockState state) {
        return true;
    }

    @Override
    public boolean isVisuallyOpaque() {
        return true;
    }

    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
        return super.canCreatureSpawn(state, world, pos, type);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return Lists.newArrayList(new ItemStack(this, 1, state.getValue(METADATA)));
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if(state.getValue(METADATA) == 4) {
            spawnAsEntity(worldIn, pos, new ItemStack(Items.COAL, worldIn.rand.nextInt(2) + 1, 1));
        } else  {
            super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if(state.getValue(METADATA) == 5) {
            EntityIC2Explosive entitytntprimed = getExplosionEntity(world, pos.getX(), pos.getY(), pos.getZ(), player == null ? null : player);
            world.spawnEntityInWorld(entitytntprimed);
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        }
        return true;
    }

    public EntityIC2Explosive getExplosionEntity(World world, int x, int y, int z, EntityLivingBase igniter) {
      EntityIC2Explosive ret = new EntityItnt(world, x + 0.5D, y + 0.5D, z + 0.5D);
      ret.setIgniter(igniter);
      return ret;
    }

    public void checkAndExplode(World world, BlockPos pos) {
        if(world.isBlockIndirectlyGettingPowered(pos) > 0) {
            explode(world, pos);
        }
    }

    public void explode(World world, BlockPos pos) {
        EntityIC2Explosive entitytntprimed = getExplosionEntity(world, pos.getX(), pos.getY(), pos.getZ(), null);
        world.spawnEntityInWorld(entitytntprimed);
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        world.setBlockToAir(pos);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        if(state.getValue(METADATA) == 5) {
            checkAndExplode(worldIn, pos);
        }
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(world, pos, neighbor);
        if(world.getBlockState(pos).getValue(METADATA) == 5) {
            checkAndExplode((World) world, pos);
        }
    }

    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
        super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
        if(worldIn.getBlockState(pos).getValue(METADATA) == 5) {
            checkAndExplode(worldIn, pos);
        }
    }


    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(state.getValue(METADATA) == 5) {
            ItemStack held = playerIn.getHeldItem(hand);
            if (held != null && held.getItem() == Items.FLINT_AND_STEEL) {
                held.damageItem(1, playerIn);
                explode(worldIn, pos);
                return true;
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item aItem, CreativeTabs par2CreativeTabs, List<ItemStack> aList) {
        for (int i = 0; i < 16; i++) {
            aList.add(new ItemStack(aItem, 1, i));
        }
    }

}
