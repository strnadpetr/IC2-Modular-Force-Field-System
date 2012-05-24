package mffs;

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

public class TileEntityReaktorMonitorClient extends TileEntityMaschines implements IInventory {

	private boolean channel[];
	private ItemStack ItemStacks[];
	private int linkMonitor_ID;
	private boolean linkMonitor;
	private int usechannel;
	private static int maxchannel = 5;
	private String Montorname = null;
	private boolean Signal = false;
	private int conectet_ID;

	public TileEntityReaktorMonitorClient() {

		usechannel = 0;
		linkMonitor_ID = 0;
		linkMonitor = false;
		ItemStacks = new ItemStack[1];
		channel = new boolean[] { false, false, false, false, false, false };

	}

	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new ContainerReaktorMonitorClient(inventoryplayer, this);
	}

	public void addfreqcard() {

		if (getStackInSlot(0) != null) {

			if (getStackInSlot(0).getItem() == mod_ModularForceFieldSystem.MFFSitemsclc) {

				if (linkMonitor_ID != Functions.getTAGfromItemstack(getStackInSlot(0)).getInteger("RMonitorID")) {
					linkMonitor_ID = Functions.getTAGfromItemstack(getStackInSlot(0)).getInteger("RMonitorID");
				}

			}
		} else {
			linkMonitor_ID = 0;
		}

	}

	public void updatecheck() {

		if (Functions.isSimulation()) {

			if (this.isLinkMonitor()) {

				TileEntity Monitor = Linkgrid.getWorldMap(worldObj).getRMonitor().get(this.getLinkMonitor_ID());
				if (Monitor == null) {
					setConectet_ID(0);
					setActive(false);
					updateEntity();
				}

			}

		}

	}

	public void updateEntity() {

		if (Functions.isSimulation()) {

			addfreqcard();

			if (this.getLinkMonitor_ID() != 0) {
				this.setLinkMonitor(true);
				try {

					channel = Linkgrid.getWorldMap(worldObj).getRMonitor().get(this.getLinkMonitor_ID()).getChannel();
					Montorname = Linkgrid.getWorldMap(worldObj).getRMonitor().get(this.getLinkMonitor_ID()).getMontorname();
					setConectet_ID(getLinkMonitor_ID());

				} catch (java.lang.NullPointerException ex) {
					this.setLinkMonitor(false);
					this.setLinkMonitor_ID(0);
					this.setMontorname("null");
					this.setConectet_ID(0);
					// System.out.println("!!ERROR!!!");
				}
			} else {
				this.setLinkMonitor(false);
				this.setConectet_ID(0);
				this.setMontorname("null");
			}

			if (this.isLinkMonitor()) {

				if (channel[usechannel]) {
					if (this.isSignal() != true) {
						this.setSignal(true);
						worldObj.markBlockAsNeedsUpdate(xCoord, yCoord, zCoord);
						this.notifyNeighbors(worldObj, xCoord, yCoord, zCoord);
					}
				} else {
					if (this.isSignal() != false) {
						this.setSignal(false);
						worldObj.markBlockAsNeedsUpdate(xCoord, yCoord, zCoord);
						this.notifyNeighbors(worldObj, xCoord, yCoord, zCoord);
					}
				}
			}
		}
	}

	public static void notifyNeighbors(World world, int i, int j, int k) {
		
		for(int l=0;l<6;l++)
		{
		world.notifyBlocksOfNeighborChange(i, j, k, l);
		world.notifyBlocksOfNeighborChange(i - 1, j, k, l);
		world.notifyBlocksOfNeighborChange(i + 1, j, k, l);
		world.notifyBlocksOfNeighborChange(i, j - 1, k, l);
		world.notifyBlocksOfNeighborChange(i, j + 1, k, l);
		world.notifyBlocksOfNeighborChange(i, j, k - 1, l);
		world.notifyBlocksOfNeighborChange(i, j, k + 1, l);
		}
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);

		usechannel = nbttagcompound.getInteger("usechannel");

		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		ItemStacks = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < ItemStacks.length) {
				ItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {

		super.writeToNBT(nbttagcompound);

		nbttagcompound.setInteger("usechannel", usechannel);

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < ItemStacks.length; i++) {
			if (ItemStacks[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				ItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}

	public ItemStack decrStackSize(int i, int j) {
		if (ItemStacks[i] != null) {
			if (ItemStacks[i].stackSize <= j) {
				ItemStack itemstack = ItemStacks[i];
				ItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = ItemStacks[i].splitStack(j);
			if (ItemStacks[i].stackSize == 0) {
				ItemStacks[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		ItemStacks[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return entityplayer.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
		}
	}

	public ItemStack getStackInSlot(int i) {
		return ItemStacks[i];
	}

	public String getInvName() {

		return "Camoflageupgrade";
	}

	public int getInventoryStackLimit() {
		return 1;
	}

	public int getSizeInventory() {
		return ItemStacks.length;
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {

			return entityplayer.getDistance((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;

		}
	}

	@Override
	public void openChest() {

	}

	@Override
	public void closeChest() {

	}

	public int getLinkMonitor_ID() {
		return linkMonitor_ID;
	}

	public void setLinkMonitor_ID(int linkMonitor_ID) {
		this.linkMonitor_ID = linkMonitor_ID;
	}

	public boolean isLinkMonitor() {
		return linkMonitor;
	}

	public void setLinkMonitor(boolean linkMonitor) {
		this.linkMonitor = linkMonitor;
	}

	public int getUsechannel() {
		return usechannel;
	}

	public void setUsechannel(int usechannel) {
		this.usechannel = usechannel;
	}

	public String getMontorname() {
		return Montorname;
	}

	public void setMontorname(String montorname) {
		Montorname = montorname;
	}

	public boolean getChannel() {
		return channel[this.getUsechannel()];
	}

	public boolean isSignal() {
		return Signal;
	}

	public void setSignal(boolean signal) {
		Signal = signal;
	}

	public int getConectet_ID() {
		return conectet_ID;
	}

	public void setConectet_ID(int conectet_ID) {
		this.conectet_ID = conectet_ID;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

}
