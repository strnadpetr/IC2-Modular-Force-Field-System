package mffs;

import ic2.common.TileEntityNuclearReactor;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_ModularForceFieldSystem;

public class TileEntityReaktorMonitor extends TileEntityPassivUpgrade implements IInventory {

	private ItemStack inventory[];
	private boolean channel[];
	private int targetheat[];
	private int RMonitor_ID;
	private boolean create;
	private int reaktorx;
	private int reaktory;
	private int reaktorz;
	private int heat;
	private boolean isreaktor;
	private boolean reaktoraktive;
	private int maxheat;
	private String Montorname = null;
	private int Montorname_ID = 0;

	public TileEntityReaktorMonitor() {

		inventory = new ItemStack[1];
		channel = new boolean[] { false, false, false, false, false, false };
		targetheat = new int[] { 0, 0, 0, 0, 0 };
		RMonitor_ID = 0;
		create = true;
		reaktoraktive = false;

	}

	public int getRMonitor_ID() {
		return RMonitor_ID;
	}

	public void setRMonitor_ID(int rMonitor_ID) {
		RMonitor_ID = rMonitor_ID;
	}

	public void freqencoding() {

		if (Functions.isSimulation()) {

			if (getStackInSlot(0) != null) {
				if (getStackInSlot(0).getItem() == mod_ModularForceFieldSystem.MFFSitemcardempty) {

					this.setInventorySlotContents(0, new ItemStack(mod_ModularForceFieldSystem.MFFSitemsclc));

					Functions.getTAGfromItemstack(getStackInSlot(0)).setInteger("RMonitorID", RMonitor_ID);
					this.onInventoryChanged();

				}
			}
		}
	}

	public void removefromgrid() {

		if (Functions.isSimulation()) {
			Linkgrid.getWorldMap(worldObj).getRMonitor().remove(getRMonitor_ID());
		}
	}

	public void updateEntity() {

		super.updateEntity();

		if (Functions.isSimulation()) {

			updatecheck();
			freqencoding();

			if (isIsreaktor()) {

				TileEntity tileEntity = worldObj.getBlockTileEntity(reaktorx, reaktory, reaktorz);

				if (tileEntity == null) {
					setIsreaktor(false);
				}

				if (tileEntity instanceof TileEntityNuclearReactor) {

					heat = ((TileEntityNuclearReactor) tileEntity).heat;
					reaktoraktive = ((TileEntityNuclearReactor) tileEntity).getActive();

					if (reaktoraktive != channel[0]) {
						channel[0] = reaktoraktive;
					}

					if (targetheat[0] < heat && channel[1] != true) {
						channel[1] = true;
					}
					if (targetheat[1] < heat && channel[2] != true) {
						channel[2] = true;
					}
					if (targetheat[2] < heat && channel[3] != true) {
						channel[3] = true;
					}
					if (targetheat[3] < heat && channel[4] != true) {
						channel[4] = true;
					}
					if (targetheat[4] < heat && channel[5] != true) {
						channel[5] = true;
					}

					if (targetheat[0] > heat && channel[1] != false) {
						channel[1] = false;
					}
					if (targetheat[1] > heat && channel[2] != false) {
						channel[2] = false;
					}
					if (targetheat[2] > heat && channel[3] != false) {
						channel[3] = false;
					}
					if (targetheat[3] > heat && channel[4] != false) {
						channel[4] = false;
					}
					if (targetheat[4] > heat && channel[5] != false) {
						channel[5] = false;
					}

				}

			} else {

				channel = new boolean[] { false, false, false, false, false, false };
			}

			if (create) {

				if (RMonitor_ID == 0) {

					RMonitor_ID = Linkgrid.getWorldMap(worldObj).newRMonitor_ID(this);

					Montorname_ID = Linkgrid.getWorldMap(worldObj).newRMonitor_Name();
					Montorname = new StringBuilder().append(ModLoader.getMinecraftInstance().thePlayer.username).append("@").append(Montorname_ID).toString();

					Linkgrid.getWorldMap(worldObj).getRMonitor().put(getRMonitor_ID(), this);
				} else {
					Linkgrid.getWorldMap(worldObj).getRMonitor().put(getRMonitor_ID(), this);
				}
				create = false;
			}

		}
	}

	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new ContainerReaktorMonitor(inventoryplayer, this);
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);

		targetheat[0] = nbttagcompound.getInteger("targetheat0");
		targetheat[1] = nbttagcompound.getInteger("targetheat1");
		targetheat[2] = nbttagcompound.getInteger("targetheat2");
		targetheat[3] = nbttagcompound.getInteger("targetheat3");
		targetheat[4] = nbttagcompound.getInteger("targetheat4");

		Montorname_ID = nbttagcompound.getInteger("MontornameID");
		Montorname = nbttagcompound.getString("Montorname");
		RMonitor_ID = nbttagcompound.getInteger("RMonitorID");

		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < inventory.length) {
				inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {

		super.writeToNBT(nbttagcompound);

		nbttagcompound.setInteger("targetheat0", targetheat[0]);
		nbttagcompound.setInteger("targetheat1", targetheat[1]);
		nbttagcompound.setInteger("targetheat2", targetheat[2]);
		nbttagcompound.setInteger("targetheat3", targetheat[3]);
		nbttagcompound.setInteger("targetheat4", targetheat[4]);

		nbttagcompound.setInteger("MontornameID", Montorname_ID);
		nbttagcompound.setString("Montorname", Montorname);
		nbttagcompound.setInteger("RMonitorID", RMonitor_ID);

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}

	public int getSizeInventory() {
		return inventory.length;
	}

	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	public int getInventoryStackLimit() {
		return 1;
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return entityplayer.getDistance((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
		}
	}

	public ItemStack decrStackSize(int i, int j) {
		if (inventory[i] != null) {
			if (inventory[i].stackSize <= j) {
				ItemStack itemstack = inventory[i];
				inventory[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = inventory[i].splitStack(j);
			if (inventory[i].stackSize == 0) {
				inventory[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public void openChest() {

	}

	public String getInvName() {

		return "ReaktorMonitor";
	}

	public void closeChest() {

	}

	public int getHeat() {
		return heat;
	}

	public void setHeat(int heat) {
		this.heat = heat;
	}

	public boolean isIsreaktor() {
		return isreaktor;
	}

	public void setIsreaktor(boolean isreaktor) {
		this.isreaktor = isreaktor;
	}

	public int getReaktorx() {
		return reaktorx;
	}

	public void setReaktorx(int reaktorx) {
		this.reaktorx = reaktorx;
	}

	public int getReaktory() {
		return reaktory;
	}

	public void setReaktory(int reaktory) {
		this.reaktory = reaktory;
	}

	public int getReaktorz() {
		return reaktorz;
	}

	public void setReaktorz(int reaktorz) {
		this.reaktorz = reaktorz;
	}

	public boolean isReaktoraktive() {
		return reaktoraktive;
	}

	public boolean getChannel(int stat) {
		return channel[stat];
	}

	public boolean[] getChannel() {
		return channel;
	}

	public int getTargetheat(int channel) {
		return targetheat[channel];
	}

	public void setTargetheat(int channel, int heat) {
		this.targetheat[channel] = heat;
	}

	public int getMaxheat() {
		return maxheat;
	}

	public void setMaxheat(int maxheat) {
		this.maxheat = maxheat;
	}

	public String getMontorname() {
		return Montorname;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

}
