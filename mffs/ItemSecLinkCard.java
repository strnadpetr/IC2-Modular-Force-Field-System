package mffs;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import net.minecraft.src.forge.ITextureProvider;

public class ItemSecLinkCard extends Item implements ITextureProvider {

	private StringBuffer info = new StringBuffer();

	public ItemSecLinkCard(int i) {
		super(i);
		setIconIndex(19);
		setMaxStackSize(1);

	}

	public String getTextureFile() {
		return "/mffs_grafik/items.png";
	}

	public boolean isRepairable() {
		return false;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {

		if (Functions.getTAGfromItemstack(itemstack).hasKey("RMonitorID")) {
			info.setLength(0);
			info.append("Link Card ID: ").append(String.valueOf(Functions.getTAGfromItemstack(itemstack).getInteger("RMonitorID")));
		} else {
			info.setLength(0);
			info.append("Link Card ID: is empty ");
		}
		Functions.ChattoPlayer(entityplayer, info.toString());
		return itemstack;

	}
}