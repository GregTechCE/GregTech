package gregtech.api.interfaces.tileentity;

import gregtech.api.interfaces.IDescribable;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple compound Interface for all my TileEntities.
 * <p/>
 * Also delivers most of the Informations about my TileEntities.
 * <p/>
 * It can cause Problems to include this Interface!
 */
public interface IGregTechTileEntity extends ITexturedTileEntity, IGearEnergyTileEntity, ICoverable, IFluidHandler, ITurnable, IGregTechDeviceInformation, IUpgradableMachine, IDigitalChest, IDescribable, IMachineBlockUpdateable {
    /**
     * gets the Error displayed on the GUI
     */
    public int getErrorDisplayID();

    /**
     * sets the Error displayed on the GUI
     */
    public void setErrorDisplayID(int aErrorID);

    /**
     * @return the MetaID of the Block or the MetaTileEntity ID.
     */
    public int getMetaTileID();

    /**
     * Internal Usage only!
     */
    public int setMetaTileID(short aID);

    /**
     * @return the MetaTileEntity which is belonging to this, or null if it doesnt has one.
     */
    public IMetaTileEntity getMetaTileEntity();

    /**
     * Sets the MetaTileEntity.
     * Even though this uses the Universal Interface, certain BaseMetaTileEntities only accept one kind of MetaTileEntity
     * so only use this if you are sure its the correct one or you will get a Class cast Error.
     *
     * @param aMetaTileEntity
     */
    public void setMetaTileEntity(IMetaTileEntity aMetaTileEntity);

    /**
     * Causes a general Texture update.
     * <p/>
     * Only used Client Side to mark Blocks dirty.
     */
    public void issueTextureUpdate();

    /**
     * Causes the Machine to send its initial Data, like Covers and its ID.
     */
    public void issueClientUpdate();

    /**
     * causes Explosion. Strength in Overload-EU
     */
    public void doExplosion(long aExplosionEU);

    /**
     * Sets the Block on Fire in all 6 Directions
     */
    public void setOnFire();

    /**
     * Sets the Block to Fire
     */
    public void setToFire();

    /**
     * Sets the Owner of the Machine. Returns the set Name.
     */
    public String setOwnerName(String aName);

    /**
     * gets the Name of the Machines Owner or "Player" if not set.
     */
    public String getOwnerName();

    /**
     * Sets initial Values from NBT
     *
     * @param tNBT is the NBTTag of readFromNBT
     * @param aID  is the MetaTileEntityID
     */
    public void setInitialValuesAsNBT(NBTTagCompound aNBT, short aID);

    /**
     * Called when leftclicking the TileEntity
     */
    public void onLeftclick(EntityPlayer aPlayer);

    /**
     * Called when rightclicking the TileEntity
     */
    public boolean onRightclick(EntityPlayer aPlayer, byte aSide, float par1, float par2, float par3);

    public float getBlastResistance(byte aSide);

    public ArrayList<ItemStack> getDrops();

    /**
     * 255 = 100%
     */
    public int getLightOpacity();

    public void addCollisionBoxesToList(World aWorld, int aX, int aY, int aZ, AxisAlignedBB inputAABB, List<AxisAlignedBB> outputAABB, Entity collider);

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World aWorld, int aX, int aY, int aZ);

    public void onEntityCollidedWithBlock(World aWorld, int aX, int aY, int aZ, Entity collider);
}