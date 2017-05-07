package gregtech.api.items;

import codechicken.lib.render.particle.CustomParticleHandler;
import codechicken.lib.vec.Cuboid6;
import com.google.common.collect.ObjectArrays;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Log;
import gregtech.common.render.RenderBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.LoaderException;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNullableByDefault;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static gregtech.api.enums.GT_Values.W;

public abstract class GT_Generic_Block extends Block {

    protected GT_Generic_Block(String aName, @Nullable Class<? extends ItemBlock> aItemClass, Material aMaterial) {
        super(aMaterial);

        setUnlocalizedName("gt." + aName);
        setRegistryName(aName);
        GameRegistry.register(this);

        if (aItemClass != null) {
            ItemBlock itemBlock = null;
            try {
                itemBlock = aItemClass.getConstructor(Block.class).newInstance(this);
            } catch(ReflectiveOperationException e){
                e.printStackTrace(GT_Log.err);
                throw new LoaderException(e);
            }
            GameRegistry.register(itemBlock, getRegistryName());
        }

        GT_LanguageManager.addStringLocalization(getUnlocalizedName() + "." + W + ".name", "Unnamed");
    }

    @Override
    public String getLocalizedName() {
        return GT_LanguageManager.getTranslation(this.getUnlocalizedName() + ".name");
    }

    @Override
    public boolean addLandingEffects(IBlockState state, WorldServer worldObj, BlockPos blockPosition, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addHitEffects(IBlockState state, World worldObj, RayTraceResult target, ParticleManager manager) {
        CustomParticleHandler.addBlockHitEffects(worldObj, getBlockBounds(target.getBlockPos()), target.sideHit, getParticleSprite(worldObj, target.getBlockPos(), target.sideHit), manager);
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
        TextureAtlasSprite[] textures = new TextureAtlasSprite[6];
        for(EnumFacing facing : EnumFacing.VALUES) {
            textures[facing.getIndex()] = getParticleSprite(world, pos, facing);
        }
        CustomParticleHandler.addBlockDestroyEffects(world, getBlockBounds(pos), textures, manager);
        return true;
    }


    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getParticleSprite(IBlockAccess worldObj, BlockPos aPos, EnumFacing side) {
        return getWorldIcon(worldObj, aPos, worldObj.getBlockState(aPos), side);
    }
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(TextureMap textureMap) {}

    @SideOnly(Side.CLIENT)
    public int getColorMultiplier(IBlockAccess aWorld, BlockPos aPos, IBlockState aState) {
        return 0xFFFFFFFF;
    }

    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getWorldIcon(IBlockAccess aWorld, BlockPos aPos, IBlockState aState, EnumFacing aSide) {
        return getIcon(aSide, aState.getValue(METADATA));
    }

    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getItemIcon(ItemStack itemStack, EnumFacing aSide) {
        return getIcon(aSide, itemStack.getItemDamage());
    }

    public TextureAtlasSprite getIcon(EnumFacing aSide, int metadata) {
        return null;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return RenderBlocks.INSTANCE.renderType;
    }
}