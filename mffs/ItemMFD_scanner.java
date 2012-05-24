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

public class ItemMFD_scanner extends ItemMFD {

	public ItemMFD_scanner(int i) {
		super(i, 1);
		setIconIndex(1);

	}

	public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {

		if (Functions.isSimulation()) {

		}
	}

}
