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

public class ItemMFD_Reaktorlink extends ItemMFD {

	public ItemMFD_Reaktorlink(int i) {
		super(i, 1);
		setIconIndex(1);

	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		ModLoader.openGUI(entityplayer, new GuiReaktorLinkRemote(itemstack, world, entityplayer));
		return itemstack;
	}

	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l) {
		ModLoader.openGUI(entityplayer, new GuiReaktorLinkRemote(itemstack, world, entityplayer));
		return true;
	}

	public boolean isFull3D() {
		return true;
	}

}
