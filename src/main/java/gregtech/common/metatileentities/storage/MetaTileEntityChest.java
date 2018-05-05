package gregtech.common.metatileentities.storage;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.ModularUI.Builder;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.render.Textures;
import gregtech.api.util.GTUtility;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;

public class MetaTileEntityChest extends MetaTileEntity {

    private final int colorRGB;
    private final int inventorySize;

    public MetaTileEntityChest(String metaTileEntityId, int colorRGB, int inventorySize) {
        super(metaTileEntityId);
        this.colorRGB = colorRGB;
        this.inventorySize = inventorySize;
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityChest(metaTileEntityId, colorRGB, inventorySize);
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.itemInventory = new ItemStackHandler(inventorySize);
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return colorRGB == 0xFFFFFF ? Textures.WOODEN_CHEST.getParticleTexture() :
            Textures.METAL_CHEST.getParticleTexture();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        if(colorRGB == 0xFFFFFF) {
            Textures.WOODEN_CHEST.render(renderState, translation, pipeline, getFrontFacing());
        } else {
            Textures.METAL_CHEST.render(renderState, translation, ArrayUtils.add(pipeline,
                new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA(colorRGB))), getFrontFacing());
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176,
            18 + inventorySize * 2 + 94)
            .label(5, 5, getMetaName());
        for(int i = 0; i < inventorySize; i++) {
            builder.slot((IItemHandlerModifiable) itemInventory, i,
                8 + (i % 9) * 18, 18 + (i / 9) * 18, GuiTextures.SLOT);
        }
        builder.bindPlayerInventory(entityPlayer.inventory, 18 + inventorySize * 2 + 12);
        return builder.build(getHolder(), entityPlayer);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("Inventory", ((ItemStackHandler) itemInventory).serializeNBT());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        ((ItemStackHandler) this.itemInventory).deserializeNBT(data.getCompoundTag("Inventory"));
    }

    @Override
    protected boolean shouldSerializeInventories() {
        return false; //handled manually
    }
}
