package mffs;

import java.util.LinkedList;
import java.util.Queue;
import net.minecraft.src.Container;
import net.minecraft.src.EntityItem;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_ModularForceFieldSystem;

public class TileEntityDirectionalExtender extends TileEntityProjektor {

	private boolean con_to_projektor;
	private int remProjektor_ID;
	private int remGenerator_ID;
	private int wide;
	private int maxwide;
	private int length;
	private int distance;
	private boolean preactive;
	private short ausrichtungx;
	private short ausrichtungy;
	private short ausrichtungz;

	public TileEntityDirectionalExtender() {

		preactive = false;
		con_to_projektor = false;
		remProjektor_ID = 0;
		remGenerator_ID = 0;
		wide = 1;
		maxwide = 32;

	}

	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new Containerdummy(inventoryplayer, this);
	}

	public int getMaxwide() {
		return maxwide;
	}

	public int getWide() {
		return wide;
	}

	public void setWide(int wide) {

		this.wide = wide;

	}

	public void setWideinit(int wide) {

		this.wide = wide;
	}

	public int getRemGenerator_ID() {
		return remGenerator_ID;
	}

	public void setRemGenerator_ID(int remGenerator_ID) {
		this.remGenerator_ID = remGenerator_ID;
		this.setLinkGenerator_ID(remGenerator_ID);
	}

	public boolean isPreactive() {
		return preactive;
	}

	public void setPreactive(boolean preactive) {
		this.preactive = preactive;
	}

	public int getLength() {
		return length;
	}

	public void setDistance(int distance) {

		this.distance = distance;
	}

	public void setlength(int length) {

		this.length = length;
	}

	public int getDistance() {
		return distance;
	}

	public boolean isCon_to_projektor() {
		return con_to_projektor;
	}

	public void setCon_to_projektor(boolean con_to_projektor) {
		this.con_to_projektor = con_to_projektor;
	}

	public int getRemProjektor_ID() {
		return remProjektor_ID;
	}

	public void setRemProjektor_ID(int remProjektor_ID) {
		this.remProjektor_ID = remProjektor_ID;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);

		wide = nbttagcompound.getInteger("wide");
		length = nbttagcompound.getInteger("length");
		distance = nbttagcompound.getInteger("distance");

	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {

		super.writeToNBT(nbttagcompound);

		nbttagcompound.setInteger("wide", wide);
		nbttagcompound.setInteger("length", length);
		nbttagcompound.setInteger("distance", distance);

	}

	public void updateEntity() {

		if (Functions.isSimulation()) {

			if (this.isCreate() && getRemGenerator_ID() != 0) {
				if (this.isPreactive()) {
					fieldcalculation(true);
				}
				this.setCreate(false);
			}

			if (this.isPreactive() == true && !this.isCreate()) {

				if (getActive() != true) {
					setActive(true);
					fieldcalculation(false);
					FieldGenerate(true);
					worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
				}
			}

			if (this.isPreactive() == false && !this.isCreate()) {

				if (getActive() != false) {
					setActive(false);
					destroyField();
					worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
				}
			}

			if (getActive()) {
				FieldGenerate(false);
			}

			if (getActive() && getWrenchDropRate() != -1.0F) {
				setWrenchRate(-1.0F);
			}
			if (!getActive() && getWrenchDropRate() != 1.0F) {
				setWrenchRate(1.0F);
			}

		}
	}

	public void fieldcalculation(boolean create) {

		if (!field_queue.isEmpty()) {
			field_queue.clear();
		}

		int tempx = 0;
		int tempy = 0;
		int tempz = 0;

		for (int x = xCoord - 1; x <= xCoord + 1; x++) {
			for (int y = yCoord - 1; y <= yCoord + 1; y++) {
				for (int z = zCoord - 1; z <= zCoord + 1; z++) {

					if (worldObj.getBlockId(x, y, z) == mod_ModularForceFieldSystem.MFFSMaschines.blockID && worldObj.getBlockMetadata(x, y, z) == 2) {
						ausrichtungx = (short) (xCoord - x);
						ausrichtungy = (short) (yCoord - y);
						ausrichtungz = (short) (zCoord - z);

					}

				}
			}
		}

		for (int x1 = 0; x1 <= getWide(); x1++) {
			for (int y1 = 1; y1 < getLength() + 1; y1++) {

				if (this.getFacing() == 0) {
					tempy = y1 - y1 - y1 - getDistance();
					if (ausrichtungx > 0) {
						tempx = x1;
					}
					if (ausrichtungx < 0) {
						tempx = x1 - x1 - x1;
					}
					if (ausrichtungz > 0) {
						tempz = x1;
					}
					if (ausrichtungz < 0) {
						tempz = x1 - x1 - x1;
					}

				}

				if (this.getFacing() == 1) {
					tempy = y1 + getDistance();
					if (ausrichtungx > 0) {
						tempx = x1;
					}
					if (ausrichtungx < 0) {
						tempx = x1 - x1 - x1;
					}
					if (ausrichtungz > 0) {
						tempz = x1;
					}
					if (ausrichtungz < 0) {
						tempz = x1 - x1 - x1;
					}

				}

				if (this.getFacing() == 2) {
					tempz = y1 - y1 - y1 - getDistance();
					if (ausrichtungx > 0) {
						tempx = x1;
					}
					if (ausrichtungx < 0) {
						tempx = x1 - x1 - x1;
					}
					if (ausrichtungy > 0) {
						tempy = x1;
					}
					if (ausrichtungy < 0) {
						tempy = x1 - x1 - x1;
					}
				}

				if (this.getFacing() == 3) {
					tempz = y1 + getDistance();
					if (ausrichtungx > 0) {
						tempx = x1;
					}
					if (ausrichtungx < 0) {
						tempx = x1 - x1 - x1;
					}
					if (ausrichtungy > 0) {
						tempy = x1;
					}
					if (ausrichtungy < 0) {
						tempy = x1 - x1 - x1;
					}
				}

				if (this.getFacing() == 4) {
					tempx = y1 - y1 - y1 - getDistance();
					if (ausrichtungz > 0) {
						tempz = x1;
					}
					if (ausrichtungz < 0) {
						tempz = x1 - x1 - x1;
					}
					if (ausrichtungy > 0) {
						tempy = x1;
					}
					if (ausrichtungy < 0) {
						tempy = x1 - x1 - x1;
					}

				}
				if (this.getFacing() == 5) {
					tempx = y1 + getDistance();
					if (ausrichtungz > 0) {
						tempz = x1;
					}
					if (ausrichtungz < 0) {
						tempz = x1 - x1 - x1;
					}
					if (ausrichtungy > 0) {
						tempy = x1;
					}
					if (ausrichtungy < 0) {
						tempy = x1 - x1 - x1;
					}

				}

				hasher.setLength(0);
				hasher.append(xCoord + tempx).append("/").append(yCoord + tempy).append("/").append(zCoord + tempz);

				ForceFieldWorldMap ffworldmap = WorldMap.getForceFieldforWorld(worldObj).addandgetffmp(xCoord + tempx, yCoord + tempy, zCoord + tempz);

				boolean freespace = !worldObj.getBlockMaterial(xCoord + tempx, yCoord + tempy, zCoord + tempz).isSolid();
				boolean ffblockinplace = worldObj.getBlockId(xCoord + tempx, yCoord + tempy, zCoord + tempz) == mod_ModularForceFieldSystem.MFFSFieldblock.blockID;

				if (ffblockinplace) {
					freespace = true;
				}

				if (ffworldmap.listsize() == 0) {
					if (this.getffmeta() == 2) {
						TexturworldMap.getTexturMap(worldObj.worldProvider.worldType).add(xCoord + tempx, yCoord + tempy, zCoord + tempz, this.getTextur());
					}
					ffworldmap.ffworld_addFirst(new ForceFieldBlock(remGenerator_ID, this.getProjektor_ID(), freespace, true, this.getffmeta()));
					field_queue.add(hasher.toString());

				} else {
					ffworldmap.ffworld_addLast(new ForceFieldBlock(remGenerator_ID, this.getProjektor_ID(), freespace, false, this.getffmeta()));
					field_queue.add(hasher.toString());
				}
			}
		}
	}

	public void destroyField() {
		for (String hasher : field_queue) {
			ForceFieldWorldMap ffworldmap = WorldMap.getForceFieldforWorld(worldObj).getFFWM(hasher.toString());
			if (ffworldmap.listsize() >= 1) {

				if (ffworldmap.ffworld_getfirstfreespace() == true && ffworldmap.ffworld_getfistactive() && ffworldmap.ffworld_getfirstProjektor_ID() == getProjektor_ID()) {
					ffworldmap.ffworld_remove(getProjektor_ID());
					worldObj.setBlockWithNotify(ffworldmap.getX(), ffworldmap.getY(), ffworldmap.getZ(), 0);
					if (ffworldmap.listsize() >= 1) {

						ffworldmap.ffworld_setfistactive(true);

					}
				} else {
					ffworldmap.ffworld_remove(getProjektor_ID());
				}

			}
		}
		field_queue.clear();
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

}
