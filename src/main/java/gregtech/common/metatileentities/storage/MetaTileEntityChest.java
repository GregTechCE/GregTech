package gregtech.common.metatileentities.storage;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.ModularUI.Builder;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntityHolder;
import gregtech.api.render.Textures;
import gregtech.api.unification.material.type.SolidMaterial;
import gregtech.api.util.GTUtility;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;
import java.util.List;

public class MetaTileEntityChest extends MetaTileEntity {

    private static final Cuboid6[] CHEST_COLLISION = new Cuboid6[] {new Cuboid6(1 / 16.0, 0 / 16.0, 1 / 16.0, 15 / 16.0, 14 / 16.0, 15 / 16.0)};

    private final SolidMaterial material;
    private final int inventorySize;
    private ItemStackHandler inventory;

    public MetaTileEntityChest(String metaTileEntityId, SolidMaterial material, int inventorySize) {
        super(metaTileEntityId);
        this.material = material;
        this.inventorySize = inventorySize;
        initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(MetaTileEntityHolder holder) {
        return new MetaTileEntityChest(metaTileEntityId, material, inventorySize);
    }

    @Override
    public String getHarvestTool() {
        return material.toString().contains("wood") ? "axe" : "pickaxe";
    }

    @Override
    public Cuboid6[] getCollisionBox() {
        return CHEST_COLLISION;
    }

    @Override
    protected void initializeInventory() {
        super.initializeInventory();
        this.inventory = new ItemStackHandler(inventorySize);
        this.itemInventory = inventory;
    }

    @Override
    public void clearMachineInventory(NonNullList<ItemStack> itemBuffer) {
        clearInventory(itemBuffer, inventory);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getParticleTexture() {
        return material.toString().contains("wood") ? Textures.WOODEN_CHEST.getParticleTexture() :
            Textures.METAL_CHEST.getParticleTexture();
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        if(material.toString().contains("wood")) {
            ColourMultiplier multiplier = new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering()));
            Textures.WOODEN_CHEST.render(renderState, translation, ArrayUtils.add(pipeline, multiplier), getFrontFacing());
        } else {
            ColourMultiplier multiplier = new ColourMultiplier(ColourRGBA.multiply(
                GTUtility.convertRGBtoOpaqueRGBA_CL(material.materialRGB),
                GTUtility.convertRGBtoOpaqueRGBA_CL(getPaintingColorForRendering())));
            Textures.METAL_CHEST.render(renderState, translation, ArrayUtils.add(pipeline, multiplier), getFrontFacing());
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176,
            18 + inventorySize * 2 + 94)
            .label(5, 5, getMetaFullName());
        for(int i = 0; i < inventorySize; i++) {
            builder.slot(inventory, i, 8 + (i % 9) * 18, 18 + (i / 9) * 18, GuiTextures.SLOT);
        }
        builder.bindPlayerInventory(entityPlayer.inventory, 18 + inventorySize * 2 + 12);
        return builder.build(getHolder(), entityPlayer);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setTag("Inventory", inventory.serializeNBT());
        return data;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.inventory.deserializeNBT(data.getCompoundTag("Inventory"));
    }

    @Override
    protected boolean shouldSerializeInventories() {
        return false; //handled manually
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        tooltip.add(I18n.format("gregtech.universal.tooltip.item_storage_capacity", inventorySize));
    }
}
