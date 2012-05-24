package mffs;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemForceField extends ItemBlock {

	public ItemForceField(int i) {
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	public int getMetadata(int i) {
		return getPlacedBlockMetadata(i);
	}

	public int getPlacedBlockMetadata(int i) {
		return i;
	}

	public String getItemNameIS(ItemStack itemstack) {

		return null;
	}
}
