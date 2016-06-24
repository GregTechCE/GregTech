package gregtech.common.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ic2.core.IC2;
import ic2.core.block.EntityIC2Explosive;
import ic2.core.block.EntityItnt;

import java.util.List;
import java.util.Random;

public class GT_Block_Reinforced extends GT_Generic_Block {

    public GT_Block_Reinforced(String aName) {
        super(GT_Item_Storage.class, aName, new GT_Material_Reinforced());
        setStepSound(soundTypeStone);
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
        GT_ModHandler.addCraftingRecipe(ItemList.Block_BronzePlate.get(1L, new Object[0]),GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"hP ", "PBP", " P ", 'P', OrePrefixes.plate.get(Materials.Bronze), 'B', OrePrefixes.stone.get(Materials.GraniteBlack)});
        GT_ModHandler.addCraftingRecipe(ItemList.Block_BronzePlate.get(1L, new Object[0]),GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"hP ", "PBP", " P ", 'P', OrePrefixes.plate.get(Materials.Bronze), 'B', OrePrefixes.stone.get(Materials.GraniteRed)});
        GT_ModHandler.addCraftingRecipe(ItemList.Block_IridiumTungstensteel.get(1L, new Object[0]),GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"hBP", 'P', OrePrefixes.plate.get(Materials.Iridium), 'B', ItemList.Block_TungstenSteelReinforced.get(1L, new Object[0])});
        GT_OreDictUnificator.setItemData(ItemList.Block_IridiumTungstensteel.get(1, new Object[0]), new ItemData(new MaterialStack(Materials.Iridium, OrePrefixes.plate.mMaterialAmount), new MaterialStack(Materials.TungstenSteel, 2*OrePrefixes.plate.mMaterialAmount),new MaterialStack(Materials.Concrete, OrePrefixes.dust.mMaterialAmount)));
        GT_ModHandler.addShapelessCraftingRecipe(new ItemStack(Items.coal, 1, 1), new Object[]{ItemList.Block_BrittleCharcoal.get(1, new Object[0])});
        GT_ModHandler.addCraftingRecipe(ItemList.Block_Powderbarrel.get(1L, new Object[0]),GT_ModHandler.RecipeBits.REVERSIBLE, new Object[]{"WSW","GGG","WGW", 'W', OrePrefixes.plank.get(Materials.Wood), 'G', new ItemStack(Items.gunpowder,1),'S',new ItemStack(Items.string,1)});
        
    }

    public String getHarvestTool(int aMeta) {
        if (aMeta == 5 || aMeta == 4 || aMeta == 6 || aMeta == 7) return "axe";
        return "pickaxe";
    }

    public int getHarvestLevel(int aMeta) {
        if (aMeta == 4||aMeta == 5 || aMeta == 6 || aMeta == 7) return 1;
        return 4;
    }

    public IIcon getIcon(int aSide, int aMeta) {
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
                    return Blocks.coal_block.getIcon(0, 0);
                case 5:
                	return Textures.BlockIcons.COVER_WOOD_PLATE.getIcon();
                case 6:
                	return Blocks.coal_block.getIcon(0, 0);
                case 7:
                	return Blocks.coal_block.getIcon(0, 0);
            }
        }
        return Textures.BlockIcons.MACHINE_CASING_SOLID_STEEL.getIcon();
    }

    public float getBlockHardness(World aWorld, int aX, int aY, int aZ) {
        if (aWorld == null) {
            return 0.0F;
        }
        if (aWorld.isAirBlock(aX, aY, aZ)) {
            return 0.0F;
        }
        int tMeta = aWorld.getBlockMetadata(aX, aY, aZ);
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
        return Blocks.iron_block.getBlockHardness(aWorld, aX, aY, aZ);
    }

    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        if (world == null) {
            return 0.0F;
        }
        int tMeta = world.getBlockMetadata(x, y, z);
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
        return super.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
    }

    public String getUnlocalizedName() {
        return this.mUnlocalizedName;
    }

    public String getLocalizedName() {
        return StatCollector.translateToLocal(this.mUnlocalizedName + ".name");
    }

    public boolean canBeReplacedByLeaves(IBlockAccess aWorld, int aX, int aY, int aZ) {
        return false;
    }

    public boolean isNormalCube(IBlockAccess aWorld, int aX, int aY, int aZ) {
        return true;
    }

    public boolean renderAsNormalBlock() {
        return true;
    }

    public boolean isOpaqueCube() {
        return true;
    }

    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public int damageDropped(int par1) {
        return par1;
    }

    public int getDamageValue(World par1World, int par2, int par3, int par4) {
        return par1World.getBlockMetadata(par2, par3, par4);
    }

    public int quantityDropped(Random par1Random) {
        return 1;
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return Item.getItemFromBlock(this);
    }

    public void dropBlockAsItemWithChance(World aWorld, int aX, int aY, int aZ, int par5, float chance, int par7) {
        if (par5 == 4) {
            Random ran = new Random();

            this.dropBlockAsItem(aWorld, aX, aY, aZ, new ItemStack(Items.coal, ran.nextInt(2) + 1, 1));
        } else {
            super.dropBlockAsItemWithChance(aWorld, aX, aY, aZ, par5, chance, par7);
        }
    }
    
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
    {
      if(world.getBlockMetadata(x, y, z)==5){
        EntityIC2Explosive entitytntprimed = getExplosionEntity(world, x, y, z, player == null ? null : player);
        if (entitytntprimed == null) {
          return false;
        }
        
        world.spawnEntityInWorld(entitytntprimed);
        world.playSoundAtEntity(entitytntprimed, "random.fuse", 1.0F, 1.0F);
        
      world.setBlockToAir(x, y, z);
      return false;
      }
      return super.removedByPlayer(world, player, x, y, z);
    }
    
    public EntityIC2Explosive getExplosionEntity(World world, int x, int y, int z, EntityLivingBase igniter)
    {
      EntityIC2Explosive ret;
      ret = new EntityItnt(world, x + 0.5D, y + 0.5D, z + 0.5D);
      ret.setIgniter(igniter);
      
      return ret;
    }
    
    public void onBlockAdded(World world, int x, int y, int z)
    {
      super.onBlockAdded(world, x, y, z);
      if (world.isBlockIndirectlyGettingPowered(x, y, z)&&world.getBlockMetadata(x, y, z)==5) {
        removedByPlayer(world, null, x, y, z);
      }
    }
    
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor)
    {
      if (world.isBlockIndirectlyGettingPowered(x, y, z)&&world.getBlockMetadata(x, y, z)==5) {
        removedByPlayer(world, null, x, y, z);
      }
    }
    
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion)
    {
      EntityIC2Explosive entitytntprimed = getExplosionEntity(world, x, y, z, explosion == null ? null : explosion.getExplosivePlacedBy());
      if (entitytntprimed == null) {
        return;
      }
      entitytntprimed.fuse = (world.rand.nextInt(entitytntprimed.fuse / 4) + entitytntprimed.fuse / 8);
      world.spawnEntityInWorld(entitytntprimed);
    }
    
    public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset)
    {
      if ((player.getCurrentEquippedItem() != null) && (player.getCurrentEquippedItem().getItem() == Items.flint_and_steel)&&par1World.getBlockMetadata(x, y, z)==5)
      {
//        par1World.setBlockMetadataWithNotify(x, y, z, 6, 7);
        removedByPlayer(par1World, player, x, y, z);
        
        return true;
      }
      return super.onBlockActivated(par1World, x, y, z, player, side, xOffset, yOffset, zOffset);
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
