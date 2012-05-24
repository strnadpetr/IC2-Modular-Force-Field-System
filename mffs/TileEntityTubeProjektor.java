package mffs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.src.Container;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_ModularForceFieldSystem;

public class TileEntityTubeProjektor extends TileEntityProjektor {

	private int length;
	private int maxlength;
	private int radius;
	private int maxradius;
	private short mode_designe;
	private boolean inhiitor;

	public TileEntityTubeProjektor() {

		inhiitor = true;
		length = 1;
		radius = 2;
		maxlength = 32;
		maxradius = 5;
		mode_designe = 1;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);
		length = nbttagcompound.getInteger("length");
		radius = nbttagcompound.getInteger("radius");
		mode_designe = nbttagcompound.getShort("mode_designe");

	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {

		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("length", length);
		nbttagcompound.setInteger("radius", radius);
		nbttagcompound.setShort("mode_designe", mode_designe);

	}

	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new ContainerProjektor(inventoryplayer, this);
	}

	public int getMaxlength() {
		return maxlength;
	}

	public int getlength() {
		return length;
	}

	public int getRadius() {
		return radius;
	}

	public int getMaxradius() {
		return maxradius;
	}

	public void setRadius(int radius) {

		this.radius = radius;

	}

	public void setRadiusinit(int radius) {

		this.radius = radius;

	}

	public void setlength(int length) {

		this.length = length;

	}

	public void setlengthinit(int length) {

		this.length = length;

	}

	public short getmode_designe() {
		return mode_designe;
	}

	public void setmode_designe(short mode_designe) {

		this.mode_designe = mode_designe;

	}

	public void setmode_designeinit(short mode_designe) {

		this.mode_designe = mode_designe;

	}

	public void addtogrid() {

		if (Functions.isSimulation()) {
			Linkgrid.getWorldMap(worldObj).getProjektor().put(getProjektor_ID(), this);
		}
	}

	public void removefromgrid() {

		if (Functions.isSimulation()) {
			Linkgrid.getWorldMap(worldObj).getProjektor().remove(getProjektor_ID());

		}
	}

	public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {

		if (this.getFacing() != side && (!this.getActive())) {
			this.setFacing((short) side);

			worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
			return true;
		}
		return false;
	}

	public void updateEntity() {

		if (Functions.isSimulation()) {

			if (this.getLinkGenerator_ID() != 0) {
				this.setLinkGenerator(true);
				try {
					this.setLinkPower(Linkgrid.getWorldMap(worldObj).getGenerator().get(this.getLinkGenerator_ID()).getForcepower());
					this.setMaxlinkPower(Linkgrid.getWorldMap(worldObj).getGenerator().get(this.getLinkGenerator_ID()).getMaxforcepower());
				} catch (java.lang.NullPointerException ex) {
					this.setLinkGenerator(false);
					this.setLinkPower(0);
					this.setMaxlinkPower(1000000);
				}
			} else {
				this.setLinkGenerator(false);
				this.setLinkPower(0);
				this.setMaxlinkPower(1000000);
			}

			if (this.isCreate() && this.getLinkGenerator_ID() != 0) {
				addtogrid();
				addfreqcard();
				this.setCreate(false);
				if (this.getActive()) {
					checkupdates();
					fieldcalculation(true);
				}
			}

			boolean powerdirekt = worldObj.isBlockGettingPowered(xCoord, yCoord, zCoord);
			boolean powerindrekt = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);

			if ((powerdirekt || powerindrekt) && this.isLinkGenerator() && this.getLinkPower() > Forcepowerneed(radius * radius * 6, true)) {

				if (getActive() != true) {
					setActive(true);
					fieldcalculation(false);
					FieldGenerate(true);
					worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
				}
			}
			if ((!powerdirekt && !powerindrekt) || !this.isLinkGenerator() || this.getLinkPower() < Forcepowerneed(length * radius * 4, false)) {

				if (getActive() != false) {
					setActive(false);
					destroyField();
					worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
				}
			}

			if (getActive() && getWrenchDropRate() != -1.0F) {
				setWrenchRate(-1.0F);
			}
			if (!getActive() && getWrenchDropRate() != 1.0F) {
				setWrenchRate(1.0F);
			}

			if (getActive()) {
				FieldGenerate(false);
			}

			if (this.getTicker() == 10) {
				checkupdates();
				addfreqcard();
				this.setTicker((short) 0);
			}
			this.setTicker((short) (this.getTicker() + 1));
		}

	}

	public void checkupdates() {

		this.setSubwater(false);
		this.setHardner(false);
		this.setCamouflage(false);

		for (int x = xCoord - 1; x <= xCoord + 1; x++) {
			for (int y = yCoord - 1; y <= yCoord + 1; y++) {
				for (int z = zCoord - 1; z <= zCoord + 1; z++) {

					if (worldObj.getBlockId(x, y, z) == mod_ModularForceFieldSystem.MFFSUpgrades.blockID) {

						int meta = worldObj.getBlockMetadata(x, y, z);

						TileEntity upgrades = worldObj.getBlockTileEntity(x, y, z);
						if (upgrades != null) {
							if (((TileEntityPassivUpgrade) upgrades).getconectet_ID() == 0 && (meta == 1 || meta == 3 || meta == 7)) {
								((TileEntityPassivUpgrade) upgrades).setconectet_ID(getProjektor_ID());
								((TileEntityPassivUpgrade) upgrades).setConnectet_typID((short) 2);
								worldObj.markBlockAsNeedsUpdate(upgrades.xCoord, upgrades.yCoord, upgrades.zCoord);
							}

							if (meta == 7) {
								if (((TileEntityCamoflageUpgrade) upgrades).getItem_ID() != getTextur()) {
									this.setTextur(((TileEntityCamoflageUpgrade) upgrades).getItem_ID());
								}

							}

							if (((TileEntityPassivUpgrade) upgrades).getconectet_ID() == getProjektor_ID() && ((TileEntityPassivUpgrade) upgrades).getConnectet_typID() == 2) {

								if (((TileEntityPassivUpgrade) upgrades).getActive() != this.getActive()) {
									((TileEntityPassivUpgrade) upgrades).setActive(this.getActive());
								}

								switch (meta) {

								case 1:
									this.setSubwater(true);
									break;
								case 3:
									this.setHardner(true);
									break;
								case 7:
									this.setCamouflage(true);
									break;
								}
							}
						}
					}
				}
			}
		}

		if (this.isCamouflage()) {
			this.setffmeta((short) 2);
		} else {
			this.setffmeta((short) 0);
		}

	}

	public void fieldcalculation(boolean create) {

		if (!field_queue.isEmpty()) {
			field_queue.clear();
		}

		int tempx = 0;
		int tempy = 0;
		int tempz = 0;

		int x_loop = 0;
		int y_loop = 0;
		int z_loop = 0;

		int x_offset_s = 0;
		int y_offset_s = 0;
		int z_offset_s = 0;

		int x_offset_e = 0;
		int y_offset_e = 0;
		int z_offset_e = 0;

		if (this.getFacing() == 0 || this.getFacing() == 1) {

			y_loop = length;
			x_loop = radius;
			z_loop = radius;
			if (mode_designe == 2 && this.getFacing() == 0) {
				y_offset_s = length;
			}
			if (mode_designe == 2 && this.getFacing() == 1) {
				y_offset_s = length - length;
			}
			if (mode_designe == 2 && this.getFacing() == 0) {
				y_offset_s = length - length;
			}
			if (mode_designe == 2 && this.getFacing() == 1) {
				y_offset_s = length;
			}
		}

		if (this.getFacing() == 2 || this.getFacing() == 3) {

			y_loop = radius;
			z_loop = length;
			x_loop = radius;
			if (mode_designe == 2 && this.getFacing() == 3) {
				z_offset_s = length;
			}
			if (mode_designe == 2 && this.getFacing() == 2) {
				z_offset_s = length - length;
			}
			if (mode_designe == 2 && this.getFacing() == 3) {
				z_offset_e = length - length;
			}
			if (mode_designe == 2 && this.getFacing() == 2) {
				z_offset_e = length;
			}

		}
		if (this.getFacing() == 4 || this.getFacing() == 5) {

			y_loop = radius;
			z_loop = radius;
			x_loop = length;
			if (mode_designe == 2 && this.getFacing() == 5) {
				x_offset_s = length;
			}
			if (mode_designe == 2 && this.getFacing() == 4) {
				x_offset_s = length - length;
			}
			if (mode_designe == 2 && this.getFacing() == 5) {
				x_offset_e = length - length;
			}
			if (mode_designe == 2 && this.getFacing() == 4) {
				x_offset_e = length;
			}

		}

		for (int z1 = 0 - z_loop + z_offset_s; z1 <= z_loop - z_offset_e; z1++) {
			for (int x1 = 0 - x_loop + x_offset_s; x1 <= x_loop - x_offset_e; x1++) {
				for (int y1 = 0 - y_loop + y_offset_s; y1 <= y_loop - y_offset_e; y1++) {

					hasher.setLength(0);
					hasher.append(xCoord + x1).append("/").append(yCoord + y1).append("/").append(zCoord + z1);

					ForceFieldWorldMap ffworldmap = WorldMap.getForceFieldforWorld(worldObj).addandgetffmp(xCoord + x1, yCoord + y1, zCoord + z1);

					int x_loop_temp = x_loop;
					int y_loop_temp = y_loop;
					int z_loop_temp = z_loop;

					if (x_loop == length && (this.getFacing() == 4 || this.getFacing() == 5)) {
						x_loop_temp += 1;
					}
					if (y_loop == length && (this.getFacing() == 0 || this.getFacing() == 1)) {
						y_loop_temp += 1;
					}
					if (z_loop == length && (this.getFacing() == 2 || this.getFacing() == 3)) {
						z_loop_temp += 1;
					}

					if (x1 == 0 - x_loop_temp || x1 == x_loop_temp || y1 == 0 - y_loop_temp || y1 == y_loop_temp || z1 == 0 - z_loop_temp || z1 == z_loop_temp) {

						boolean freespace = !worldObj.getBlockMaterial(xCoord + x1, yCoord + y1, zCoord + z1).isSolid();
						boolean ffblockinplace = worldObj.getBlockId(xCoord + x1, yCoord + y1, zCoord + z1) == mod_ModularForceFieldSystem.MFFSFieldblock.blockID;

						if (ffblockinplace) {
							freespace = true;
						}

						if (ffworldmap.listsize() == 0) {
							if (this.getffmeta() == 2) {
								TexturworldMap.getTexturMap(worldObj.worldProvider.worldType).add(xCoord + x1, yCoord + y1, zCoord + z1, this.getTextur());
							}
							ffworldmap.ffworld_addFirst(new ForceFieldBlock(this.getLinkGenerator_ID(), this.getProjektor_ID(), freespace, true, this.getffmeta()));
							field_queue.add(hasher.toString());

						} else {

							ffworldmap.ffworld_addLast(new ForceFieldBlock(this.getLinkGenerator_ID(), this.getProjektor_ID(), freespace, false, this.getffmeta()));
							field_queue.add(hasher.toString());

						}

					} else {

						if (this.isSubwater()) {
							if (worldObj.getBlockMaterial(xCoord + x1, yCoord + y1, zCoord + z1).isLiquid()) {
								worldObj.setBlockWithNotify(xCoord + x1, yCoord + y1, zCoord + z1, 0);
							}
						}

						if (ffworldmap.listsize() > 0) {
							if (ffworldmap.ffworld_getfistactive() && ffworldmap.ffworld_getfirstfreespace() && ffworldmap.ffworld_getfirstGenerator_ID() == this.getLinkGenerator_ID()) {
								ffworldmap.ffworld_setfistactive(false);
								ffworldmap.ffworld_setfirstfreeospace(true);
								worldObj.setBlockWithNotify(xCoord + x1, yCoord + y1, zCoord + z1, 0);
							}
						}
					}
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
						ffworldmap.ffworld_setfirstfreeospace(false);

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
