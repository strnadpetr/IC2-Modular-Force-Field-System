package mffs;

import java.util.List;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.Slot;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_ModularForceFieldSystem;
import net.minecraft.src.forge.ITextureProvider;

public class ItemMFD_IDwriter extends ItemMFD {

	public ItemMFD_IDwriter(int i) {
		super(i, 2);
		setIconIndex(2);

	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		if (Functions.isSimulation()) {

		}
		return false;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (Functions.isSimulation()) {
			List<Slot> slots = entityplayer.inventorySlots.inventorySlots;
			for (Slot slot : slots) {
				if (slot.getStack() != null) {
					if (slot.getStack().getItem() == mod_ModularForceFieldSystem.MFFSitemcardempty) {
						slot.putStack(new ItemStack(mod_ModularForceFieldSystem.MFFSitemidc, 1));
						slot.onSlotChanged();
						Functions.ChattoPlayer(entityplayer, "[MFD]ID-Card coded");

						Functions.getTAGfromItemstack(slot.getStack()).setString("name", entityplayer.username);
						return itemstack;
					}

				}

			}
			Functions.ChattoPlayer(entityplayer, "[MFD]Error need <MFFS Card blank> in  Inventory");

		}
		return itemstack;
	}

}
