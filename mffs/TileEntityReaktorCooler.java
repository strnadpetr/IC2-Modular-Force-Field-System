package mffs;


import ic2.api.Items;
import ic2.common.TileEntityNuclearReactor;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_ModularForceFieldSystem;

public class TileEntityReaktorCooler extends TileEntityPassivUpgrade implements IInventory {

	private boolean isreaktor;
	private ItemStack inventory[];
	private int reaktorx;
	private int reaktory;
	private int reaktorz;
	private int maxheat;
	private int heat;
	private int mode;
	private int targetheat;

	public TileEntityReaktorCooler() {

		inventory = new ItemStack[9];
		mode = 0;
		targetheat = 0;

	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public void updateEntity() {

		super.updateEntity();

		if (Functions.isSimulation()) {

			updatecheck();

			if (isIsreaktor()) {

				TileEntity tileEntity = worldObj.getBlockTileEntity(reaktorx, reaktory, reaktorz);

				if (tileEntity == null) {
					setIsreaktor(false);
				}

				if (tileEntity instanceof TileEntityNuclearReactor) {

					heat = ((TileEntityNuclearReactor) tileEntity).heat;

					if (this.getMode() != 0 && this.getActive()) {

						switch (getMode()) {
						case 1: // cooler
							if ((getTargetheat() + 50) < heat) {
								reactorcooler((TileEntityNuclearReactor) tileEntity);
							}
							break;
						case 2: // heater
							if ((getTargetheat() - 50) > heat) {
								reactorheater((TileEntityNuclearReactor) tileEntity);
							}
							break;

						case 3: // balance
							if ((getTargetheat() + 100) < heat) {
								reactorcooler((TileEntityNuclearReactor) tileEntity);
								break;
							}

							if ((getTargetheat() - 100) > heat) {
								reactorheater((TileEntityNuclearReactor) tileEntity);
								break;
							}

						}

					}
				}
			}
		}
	}

	private void reactorheater(TileEntityNuclearReactor tileEntity) {
		for (int i = 0; i < inventory.length; i++) {
			if (getStackInSlot(i) != null) {
				if ((getStackInSlot(i).getItem() == Items.getItem("lavaCell").getItem())) {

					if (getStackInSlot(i).stackSize == 1) {
						inventory[i] = null;
					} else {
						this.getStackInSlot(i).stackSize--;
					}

					((TileEntityNuclearReactor) tileEntity).heat = ((TileEntityNuclearReactor) tileEntity).heat + 100;
					break;
				}
			}
		}
	}

	private void reactorcooler(TileEntityNuclearReactor tileEntity) {
		for (int i = 0; i < inventory.length; i++) {
			if (getStackInSlot(i) != null) {

				if ((getStackInSlot(i).getItem() == Items.getItem("waterCell").getItem())) {
					if (getStackInSlot(i).stackSize == 1) {
						inventory[i] = null;
					} else {
						this.getStackInSlot(i).stackSize--;
					}
					((TileEntityNuclearReactor) tileEntity).heat = ((TileEntityNuclearReactor) tileEntity).heat - 50;
					if (((TileEntityNuclearReactor) tileEntity).heat < 0) {
						((TileEntityNuclearReactor) tileEntity).heat = 0;
					}
					break;
				}

				if (getStackInSlot(i).itemID == Block.ice.blockID) {
					if (getStackInSlot(i).stackSize == 1) {
						inventory[i] = null;
					} else {
						this.getStackInSlot(i).stackSize--;
					}
					((TileEntityNuclearReactor) tileEntity).heat = ((TileEntityNuclearReactor) tileEntity).heat - 100;
					if (((TileEntityNuclearReactor) tileEntity).heat < 0) {
						((TileEntityNuclearReactor) tileEntity).heat = 0;
					}
					break;
				}

			}
		}

	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);
		targetheat = nbttagcompound.getInteger("targetheat");
		mode = nbttagcompound.getInteger("mode");

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

		nbttagcompound.setInteger("targetheat", targetheat);
		nbttagcompound.setInteger("mode", mode);

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

	public int getTargetheat() {
		return targetheat;
	}

	public void setTargetheat(int targetheat) {
		this.targetheat = targetheat;
	}

	public void setMaxheat(int maxheat) {
		this.maxheat = maxheat;
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

	public int getMaxheat() {
		return maxheat;
	}

	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new ContainerReactorCooler(inventoryplayer, this);
	}

	public int getSizeInventory() {
		return inventory.length;
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

	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	public String getInvName() {

		return "Reactor Cooler";
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return entityplayer.getDistance((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
		}
	}

	public void openChest() {

	}

	public void closeChest() {

	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

}
