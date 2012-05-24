package mffs;

import net.minecraft.src.Container;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.TileEntity;

public class TileEntityGeneratorInjector extends TileEntityMaschines {

	private boolean contocore;
	private int remGenerator_ID;

	public TileEntityGeneratorInjector() {

		contocore = false;
		remGenerator_ID = 0;
	}

	public boolean isContocore() {
		return contocore;
	}

	public void setContocore(boolean contocore) {
		this.contocore = contocore;
	}

	public int getRemGenerator_ID() {
		return remGenerator_ID;
	}

	public void setRemGenerator_ID(int remGenerator_ID) {
		this.remGenerator_ID = remGenerator_ID;
	}

	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new Containerdummy(inventoryplayer, this);
	}

}
