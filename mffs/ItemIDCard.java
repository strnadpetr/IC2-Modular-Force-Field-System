package mffs;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_ModularForceFieldSystem;
import net.minecraft.src.forge.ITextureProvider;

public class ItemIDCard extends Item implements ITextureProvider {

	private StringBuffer info = new StringBuffer();

	public ItemIDCard(int i) {
		super(i);
		setIconIndex(18);
		setMaxStackSize(1);

	}

	public String getTextureFile() {
		return "/mffs_grafik/items.png";
	}

	public boolean isRepairable() {
		return false;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {

		if (Functions.isSimulation()) {

			Functions.ChattoPlayer(entityplayer, ":-) Wait for Beta 6");
			Functions.ChattoPlayer(entityplayer, Functions.getTAGfromItemstack(itemstack).getString("name"));
		}

		return itemstack;

	}

}
