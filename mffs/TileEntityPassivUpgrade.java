package mffs;

/*
 *  Typ 1: Generator
 *  Typ 2: Projektor
 * 
 */

import net.minecraft.src.Container;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_ModularForceFieldSystem;

public class TileEntityPassivUpgrade extends TileEntityMaschines {

	private short connectet_typID;
	private int conectet_ID;
	private int counter;

	public TileEntityPassivUpgrade() {

		conectet_ID = 0;
		counter = 0;
		connectet_typID = 0;
	}

	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new Containerdummy(inventoryplayer, this);
	}

	public int getconectet_ID() {
		return conectet_ID;
	}

	public void setconectet_ID(int conectet_ID) {
		this.conectet_ID = conectet_ID;
	}

	public short getConnectet_typID() {
		return connectet_typID;
	}

	public void setConnectet_typID(short i) {
		this.connectet_typID = i;
	}

	public void updateEntity() {

		if (Functions.isSimulation()) {

			if (getActive() && getWrenchDropRate() != -1.0F) {
				setWrenchRate(-1.0F);
			}
			if (!getActive() && getWrenchDropRate() != 1.0F) {
				setWrenchRate(1.0F);
			}
		}
	}

	public void updatecheck() {

		if (Functions.isSimulation()) {

			if (conectet_ID != 0) {
				switch (connectet_typID) {
				case 1:
					TileEntity Generator = Linkgrid.getWorldMap(worldObj).getGenerator().get(conectet_ID);
					if (Generator == null) {
						setconectet_ID(0);
						setActive(false);
						updateEntity();
					}
					break;
				case 2:
					TileEntity Projektor = Linkgrid.getWorldMap(worldObj).getProjektor().get(conectet_ID);
					if (Projektor == null) {
						setconectet_ID(0);
						setActive(false);
						updateEntity();
					}
					break;
				}

			}

		}
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);

	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {

		super.writeToNBT(nbttagcompound);

	}

}
