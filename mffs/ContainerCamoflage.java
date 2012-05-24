package mffs;

import java.util.List;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class ContainerCamoflage extends Container {

	private TileEntityCamoflageUpgrade tileEntity_Camoflage_Upgrade;

	public ContainerCamoflage(InventoryPlayer inventoryplayer, TileEntityCamoflageUpgrade tileEntity_Camoflage_Upgrade) {

		addSlot(new Slot(tileEntity_Camoflage_Upgrade, 0, 116, 30));

		for (int j = 0; j < 9; j++) {
			addSlot(new Slot(inventoryplayer, j, 8 + j * 18, 66));
		}
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {

		return true;
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
