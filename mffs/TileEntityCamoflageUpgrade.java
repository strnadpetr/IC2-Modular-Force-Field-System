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

public class TileEntityCamoflageUpgrade extends TileEntityPassivUpgrade implements IInventory {

	private ItemStack CamoflageItemStacks[];
	private int ItemID;

	public TileEntityCamoflageUpgrade() {

		CamoflageItemStacks = new ItemStack[1];
		ItemID = -1;

	}

	public void setItem_ID(int itemID) {
		ItemID = itemID;
	}

	public int getItem_ID() {
		return ItemID;
	}

	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new ContainerCamoflage(inventoryplayer, this);
	}

	public void updateEntity() {

		if (Functions.isSimulation() && this.getconectet_ID() != 0) {
			if (getStackInSlot(0) != null) {
				if (getStackInSlot(0).itemID <= 122) {
					if (getStackInSlot(0).getHasSubtypes()) {
						this.setItem_ID(getStackInSlot(0).itemID);
						this.setItem_ID(ItemID + (getStackInSlot(0).getItemDamage() * 1000));
					} else {
						this.setItem_ID(getStackInSlot(0).itemID);
					}

				} else {
					this.setItem_ID(-1);
				}
			} else {
				this.setItem_ID(-1);
			}
		}
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);
		ItemID = nbttagcompound.getInteger("ItemID");

		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		CamoflageItemStacks = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < CamoflageItemStacks.length) {
				CamoflageItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {

		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("ItemID", ItemID);

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < CamoflageItemStacks.length; i++) {
			if (CamoflageItemStacks[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				CamoflageItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}

	public ItemStack decrStackSize(int i, int j) {
		if (CamoflageItemStacks[i] != null) {
			if (CamoflageItemStacks[i].stackSize <= j) {
				ItemStack itemstack = CamoflageItemStacks[i];
				CamoflageItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = CamoflageItemStacks[i].splitStack(j);
			if (CamoflageItemStacks[i].stackSize == 0) {
				CamoflageItemStacks[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		CamoflageItemStacks[i] = itemstack;
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
		return CamoflageItemStacks[i];
	}

	public String getInvName() {

		return "Camoflageupgrade";
	}

	public int getInventoryStackLimit() {
		return 1;
	}

	public int getSizeInventory() {
		return CamoflageItemStacks.length;
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

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

}
