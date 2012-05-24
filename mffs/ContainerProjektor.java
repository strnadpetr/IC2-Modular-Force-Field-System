package mffs;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class ContainerProjektor extends Container {

	private TileEntityProjektor entity;

	public ContainerProjektor(InventoryPlayer inventory, TileEntityProjektor tileentity) {
		entity = tileentity;
		addSlot(new Slot(tileentity, 0, 97, 120));

		for (int j = 0; j < 9; j++) {
			addSlot(new Slot(inventory, j, 8 + j * 18, 142));
		}
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return entity.isUseableByPlayer(entityplayer);
	}

	public ItemStack transferStackInSlot(int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize != itemstack.stackSize) {
				slot.onPickupFromSlot(itemstack1);
			} else {
				return null;
			}
		}
		return itemstack;
	}

}