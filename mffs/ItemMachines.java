package mffs;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;

public class ItemMachines extends ItemBlock {

	public ItemMachines(int i) {
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
			return "Generator_Core";
		case 1:
			return "Area_Projektor";
		case 2:
			return "Directional_Projektor";
		case 3:
			return "Deflector_Projektor";
		case 4:
			return "Tube_Projektor";
		case 5:
			return "Directional_Extender";
		case 6:
			return "Generator_EU_Injektor";
		case 7:
			return "Reaktor_Field";
		case 8:
			return "Reaktor_Cooler";
		case 9:
			return "Reaktor_Monitor";
		}
		return null;
	}
}
