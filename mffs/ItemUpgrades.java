package mffs;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemUpgrades extends ItemBlock {

	public ItemUpgrades(int i) {
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
		int i = itemstack.getItemDamage();
		switch (i) {

		case 0:
			return "Reaktor_Monitor_Client";
		case 1:
			return "Projektor_Subwater";
		case 2:
			return "Projektor_Dome";
		case 3:
			return "Projektor_Hardner";
		case 4:
			return "Generator_Storage";
		case 5:
			return "Generator_Linkex";
		case 6:
			return "Projektor_Zapper";
		case 7:
			return "Projektor_camouflage";
		case 8:
			return "Reactor_Connector";
		}
		return null;
	}
}
