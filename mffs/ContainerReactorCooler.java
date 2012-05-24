package mffs;

import java.util.List;

import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICrafting;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class ContainerReactorCooler extends Container {

	private TileEntityReaktorCooler colerentity;

	public ContainerReactorCooler(InventoryPlayer inventory, TileEntityReaktorCooler tileentity) {

		colerentity = tileentity;

		addSlot(new Slot(colerentity, 0, 8, 113));
		addSlot(new Slot(colerentity, 1, 26, 113));
		addSlot(new Slot(colerentity, 2, 44, 113));
		addSlot(new Slot(colerentity, 3, 62, 113));
		addSlot(new Slot(colerentity, 4, 80, 113));
		addSlot(new Slot(colerentity, 5, 98, 113));
		addSlot(new Slot(colerentity, 6, 116, 113));
		addSlot(new Slot(colerentity, 7, 134, 113));
		addSlot(new Slot(colerentity, 8, 152, 113));

		for (int k = 0; k < 9; k++) {
			addSlot(new Slot(inventory, k, 8 + k * 18, 143));
		}

	}

	public void onCraftGuiClosed(EntityPlayer entityplayer) {
		super.onCraftGuiClosed(entityplayer);
		entityplayer.inventorySlots.updateCraftingResults();

	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return colerentity.isUseableByPlayer(entityplayer);
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