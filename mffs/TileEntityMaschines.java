package mffs;

import ic2.api.IWrenchable;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;

public abstract class TileEntityMaschines extends TileEntity implements IWrenchable {

	private boolean init;
	private boolean active;
	private short facing;
	private float wrenchRate;

	public TileEntityMaschines()

	{
		init = true;
		active = false;
		facing = -1;
		wrenchRate = 1.0F;

	}

	public abstract Container getContainer(InventoryPlayer inventoryplayer);

	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);
		facing = nbttagcompound.getShort("facing");
		active = nbttagcompound.getBoolean("active");
		wrenchRate = nbttagcompound.getFloat("wrenchRate");
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("facing", facing);
		nbttagcompound.setBoolean("active", active);
		nbttagcompound.setFloat("wrenchRate", wrenchRate);
	}

	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
		return false;
	}

	public void setFacing(short i) {

		facing = i;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean flag) {
		active = flag;
	}

	public short getFacing() {
		return facing;
	}

	public void setWrenchRate(float flag) {

		wrenchRate = flag;

	}

	public boolean wrenchCanRemove(EntityPlayer entityPlayer) {

		if (getWrenchDropRate() <= 0.0F) {
			return false;
		}
		return true;
	}

	public float getWrenchDropRate() {
		return wrenchRate;
	}

	public boolean isInit() {
		return init;
	}

	public void setInit(boolean init) {
		this.init = init;
	}

}
