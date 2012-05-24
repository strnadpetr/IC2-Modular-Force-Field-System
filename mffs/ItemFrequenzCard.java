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

public class ItemFrequenzCard extends Item implements ITextureProvider {

	private StringBuffer info = new StringBuffer();

	public ItemFrequenzCard(int i) {
		super(i);
		setIconIndex(17);
		setMaxStackSize(1);

	}

	public String getTextureFile() {
		return "/mffs_grafik/items.png";
	}

	public boolean isRepairable() {
		return false;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {

		if (Functions.getTAGfromItemstack(itemstack).hasKey("Generator_ID")) {
			info.setLength(0);
			info.append("[MFFS Frequency Card] Frequency encoded: ").append(String.valueOf(Functions.getTAGfromItemstack(itemstack).getInteger("Generator_ID")));
		} else {
			info.setLength(0);
			info.append("[MFFS Frequency Card] is empty ");
		}
		Functions.ChattoPlayer(entityplayer, info.toString());
		return itemstack;

	}

}
