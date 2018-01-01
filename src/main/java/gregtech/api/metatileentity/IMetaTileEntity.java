package gregtech.api.metatileentity;

import gregtech.api.capability.impl.FluidTankHandler;
import gregtech.api.capability.internal.IGregTechTileEntity;
import gregtech.api.capability.internal.IRedstoneEmitter;
import gregtech.api.capability.internal.IRedstoneReceiver;
import gregtech.api.capability.internal.ITurnable;
import gregtech.api.gui.IUIHolder;
import gregtech.api.gui.ModularUI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface IMetaTileEntity extends ITurnable, IRedstoneReceiver, IRedstoneEmitter, IUIHolder {

    IMetaTileEntityFactory getFactory();

    String getMetaName();

    IGregTechTileEntity getHolder();

    /**
     * when placing a Machine in World, to initialize default Modes. data can be null!
     */
    void initFromItemStackData(NBTTagCompound data);

    /**
     * Adds the NBT-Information to the ItemStack, when being dismanteled properly
     * Used to store Machine specific Upgrade Data.
     */
    void writeItemStackData(NBTTagCompound data);

    /**
     * writeToNBT
     */
    void saveNBTData(NBTTagCompound data);

    /**
     * readFromNBT
     */
    void loadNBTData(NBTTagCompound data);

    void receiveCustomData(int dataId, PacketBuffer buf);

    void writeInitialData(PacketBuffer buf);
    void receiveInitialData(PacketBuffer buf);

    <T> boolean hasCapability(Capability<T> capability, EnumFacing side);
    <T> T getCapability(Capability<T> capability, EnumFacing side);

    /**
     * Called when a player right clicks the facing with a screwdriver.
     */
    boolean onScrewdriverRightClick(EnumFacing side, EntityPlayer player, EnumHand hand, float clickX, float clickY, float clickZ);

    /**
     * Called when a player right clicks the facing with a wrench.
     */
    boolean onWrenchRightClick(EnumFacing side, EnumFacing wrenchingSide, EntityPlayer player, EnumHand hand, float clickX, float clickY, float clickZ);

    /**
     * The First processed Tick which was passed to this MetaTileEntity
     */
    void onFirstTick();

    /**
     * The Tick before all the generic handling happens, what gives a slightly faster reaction speed.
     * Don't use this if you really don't need to. @onPostTick is better suited for ticks.
     * This happens still after the Cover handling.
     */
    void onPreTick(long tickTimer);

    /**
     * The Tick after all the generic handling happened.
     * Recommended to use this like updateEntity.
     */
    void onPostTick(long tickTimer);

    /**
     * Called when this MetaTileEntity gets (intentionally) disconnected from the BaseMetaTileEntity.
     * Doesn't get called when this thing is moved by Frames or similar hacks.
     */
    void inValidate();

    /**
     * Called when the BaseMetaTileEntity gets invalidated, what happens right before the @inValidate above gets called
     */
    void onRemoval();

    /**
     * Creates instance of ModularUI to be opened for specified player
     * Note that set of widgets in GUI returned in this method should be SAME ON BOTH SIDES
     * @param player player who opens GUI
     * @return instance of ModularUI for this MetaTileEntity
     */
    ModularUI<? extends IMetaTileEntity> createUI(EntityPlayer player);

    IItemHandlerModifiable createImportItemHandler();
    IItemHandlerModifiable createExportItemHandler();

    IItemHandler createItemHandler();

    FluidTankHandler createImportFluidHandler();
    FluidTankHandler createExportFluidHandler();

    IFluidHandler createFluidHandler();

    /**
     * @return true if the Machine can be accessed
     */
    boolean isAccessAllowed(EntityPlayer player);

    /**
     * Called when a player rightclicks the machine
     * Sneaky rightclicks are not getting passed to this!
     */
    boolean onRightClick(EnumFacing side, EntityPlayer player, EnumHand hand, float clickX, float clickY, float clickZ);

    /**
     * Called when a player leftclicks the machine
     * Sneaky leftclicks are getting passed to this unlike with the rightclicks.
     */
    void onLeftClick(EntityPlayer player);

    /**
     * Called when the Machine explodes, override Explosion Code here.
     */
    void doExplosion(long explosionPower);

    void onEntityCollidedWithBlock(Entity collider);

}