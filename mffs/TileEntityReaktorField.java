package mffs;

import ic2.api.Direction;
import ic2.api.EnergyNet;
import ic2.api.IEnergyConductor;
import ic2.common.TileEntityNuclearReactor;
import ic2.common.TileEntityReactorChamber;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import net.minecraft.src.Container;
import net.minecraft.src.EntityItem;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_ModularForceFieldSystem;

public class TileEntityReaktorField extends TileEntityProjektor {

	private boolean watercool;
	private boolean isreaktor;
	private int reactorx = 0;
	private int reactory = 0;
	private int reactorz = 0;
	private int reactordist = 0;
	private int reactorsize = 0;
	private int reactorheat;
	private int maxheat;

	public TileEntityReaktorField() {

		isreaktor = false;
		reactorheat = 0;
		watercool = false;
		setDropper(false);
		setHardner(false);

	}

	public int getReactorx() {
		return reactorx;
	}

	public int getReactory() {
		return reactory;
	}

	public int getReactorz() {
		return reactorz;
	}

	public int getReactordist() {
		return reactordist;
	}

	public int getReactorsize() {
		return reactorsize;
	}

	public int getReactorheat() {
		return reactorheat;
	}

	public boolean isWatercool() {
		return watercool;
	}

	public void setWatercoolinit(boolean watercool) {

		this.watercool = watercool;
	}

	public void setWatercool(boolean watercool) {

		this.watercool = watercool;

	}

	public int getReaktorheat() {
		return reactorheat;
	}

	public void setReaktorheat(int reaktorheat) {
		this.reactorheat = reaktorheat;
	}

	public boolean isIsreaktor() {
		return isreaktor;
	}

	public void setIsreaktor(boolean isreaktor) {
		this.isreaktor = isreaktor;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);
		watercool = nbttagcompound.getBoolean("watercool");

	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {

		super.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("watercool", watercool);

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

	private boolean scanforNuclearReaktor() {

		for (int x = xCoord - 2; x <= xCoord + 2; x++) {
			for (int y = yCoord - 2; y <= yCoord + 2; y++) {
				for (int z = zCoord - 2; z <= zCoord + 2; z++) {

					TileEntity tileEntity = worldObj.getBlockTileEntity(x, y, z);

					if (tileEntity instanceof TileEntityNuclearReactor) {
						reactorx = x;
						reactory = y;
						reactorz = z;

						if (Math.abs(xCoord - reactorx) <= 1 && Math.abs(yCoord - reactory) <= 1 && Math.abs(zCoord - reactorz) <= 1) {
							reactordist = 1;
						} else {
							reactordist = 2;
						}

						return true;
					}
				}
			}
		}
		return false;
	}

	private int scanforReaktorCamber() {

		int chamber = 0;

		if (worldObj.getBlockTileEntity(reactorx + 1, reactory, reactorz) instanceof TileEntityReactorChamber) {
			chamber++;
		}
		if (worldObj.getBlockTileEntity(reactorx - 1, reactory, reactorz) instanceof TileEntityReactorChamber) {
			chamber++;
		}
		if (worldObj.getBlockTileEntity(reactorx, reactory + 1, reactorz) instanceof TileEntityReactorChamber) {
			chamber++;
		}
		if (worldObj.getBlockTileEntity(reactorx, reactory - 1, reactorz) instanceof TileEntityReactorChamber) {
			chamber++;
		}
		if (worldObj.getBlockTileEntity(reactorx, reactory, reactorz + 1) instanceof TileEntityReactorChamber) {
			chamber++;
		}
		if (worldObj.getBlockTileEntity(reactorx, reactory, reactorz - 1) instanceof TileEntityReactorChamber) {
			chamber++;
		}
		return chamber;

	}

	public void updateEntity() {

		if (Functions.isSimulation()) {

			if (!isIsreaktor()) {
				setIsreaktor(scanforNuclearReaktor());

			}

			if (this.getLinkGenerator_ID() != 0) {
				this.setLinkGenerator(true);
				try {
					this.setLinkPower(Linkgrid.getWorldMap(worldObj).getGenerator().get(this.getLinkGenerator_ID()).getForcepower());
					this.setMaxlinkPower(Linkgrid.getWorldMap(worldObj).getGenerator().get(this.getLinkGenerator_ID()).getMaxforcepower());
				} catch (java.lang.NullPointerException ex) {
					this.setLinkGenerator(false);
					this.setLinkPower(0);
					this.setMaxlinkPower(1000000);
					destroyField();
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

			boolean inpowerdirekt = worldObj.isBlockGettingPowered(xCoord, yCoord, zCoord);
			boolean powerdirekt = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);

			if (inpowerdirekt == true || powerdirekt == true) {
				powerdirekt = true;
			}

			switch (reactorsize) {
			case 0:
				maxheat = 7500;
				break;
			case 1:
				maxheat = 8350;
				break;
			case 2:
				maxheat = 9200;
				break;
			case 3:
				maxheat = 10050;
				break;
			case 4:
				maxheat = 10900;
				break;
			case 5:
				maxheat = 11750;
				break;
			case 6:
				maxheat = 12600;
				break;
			}

			if (maxheat < reactorheat) {
				setHardner(true);
				powerdirekt = true;

			}

			if ((reactorsize == 0 && reactordist == 1) || (reactorsize >= 1 && reactordist == 2)) {

				if ((powerdirekt) && this.isLinkGenerator() && this.getLinkPower() > Forcepowerneed(25 * 6, true) && isIsreaktor()) {

					if (getActive() != true) {
						setActive(true);
						checkupdates();
						fieldcalculation(false);
						FieldGenerate(true);
						worldObj.markBlockNeedsUpdate(xCoord, yCoord, zCoord);
					}
				}
			}
			if ((!powerdirekt) || !this.isLinkGenerator() || this.getLinkPower() < Forcepowerneed(25 * 6, false) || !isIsreaktor()) {

				if (getActive() != false) {
					setActive(false);
					destroyField();
					setHardner(false);
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

				if (isreaktor) {
					reactorsize = scanforReaktorCamber();
					checkupdates();
					TileEntity tileEntity = worldObj.getBlockTileEntity(reactorx, reactory, reactorz);

					if (tileEntity instanceof TileEntityNuclearReactor) {
						setReaktorheat(((TileEntityNuclearReactor) tileEntity).heat);
						if (getActive()) {
							refreshwater();
						}

					} else {

						setIsreaktor(false);

					}
				} else {
					checkupdates();
				}

				addfreqcard();
				this.setTicker((short) 0);
			}
			this.setTicker((short) (this.getTicker() + 1));
		}

	}

	public void refreshwater() {
		for (int y1 = 0 - reactordist; y1 <= reactordist; y1++) {
			for (int x1 = 0 - reactordist; x1 <= reactordist; x1++) {
				for (int z1 = 0 - reactordist; z1 <= reactordist; z1++) {

					if (watercool) {
						if (worldObj.getBlockId(reactorx + x1, reactory + y1, reactorz + z1) == 0) {
							worldObj.setBlockWithNotify(reactorx + x1, reactory + y1, reactorz + z1, 8);
							TileEntity tileEntity = Linkgrid.getWorldMap(worldObj).getGenerator().get(this.getLinkGenerator_ID());
							if (tileEntity instanceof TileEntityGeneratorCore) {
								((TileEntityGeneratorCore) tileEntity).Energylost(1000);
							}

						}

					}
				}
			}
		}
	}

	public void fieldcalculation(boolean create) {

		if (!field_queue.isEmpty()) {
			field_queue.clear();
		}

		for (int y1 = 0 - reactordist; y1 <= reactordist; y1++) {
			for (int x1 = 0 - reactordist; x1 <= reactordist; x1++) {
				for (int z1 = 0 - reactordist; z1 <= reactordist; z1++) {

					hasher.setLength(0);
					hasher.append(reactorx + x1).append("/").append(reactory + y1).append("/").append(reactorz + z1);

					ForceFieldWorldMap ffworldmap = WorldMap.getForceFieldforWorld(worldObj).addandgetffmp(reactorx + x1, reactory + y1, reactorz + z1);

					if (create) {
						ffworldmap.ffworld_remove(getProjektor_ID());
					}

					if (x1 == 0 - reactordist || x1 == reactordist || y1 == 0 - reactordist || y1 == reactordist || z1 == 0 - reactordist || z1 == reactordist) {

						boolean freespace = !worldObj.getBlockMaterial(reactorx + x1, reactory + y1, reactorz + z1).isSolid();
						boolean ffblockinplace = worldObj.getBlockId(reactorx + x1, reactory + y1, reactorz + z1) == mod_ModularForceFieldSystem.MFFSFieldblock.blockID;

						if (ffblockinplace) {
							freespace = true;
						}

						if (ffworldmap.listsize() == 0) {

							ffworldmap.ffworld_addFirst(new ForceFieldBlock(this.getLinkGenerator_ID(), this.getProjektor_ID(), freespace, true, (short) 3));
							field_queue.add(hasher.toString());

						} else {

							ffworldmap.ffworld_addLast(new ForceFieldBlock(this.getLinkGenerator_ID(), this.getProjektor_ID(), freespace, false, (short) 3));
							field_queue.add(hasher.toString());

						}

					} else {
						if (watercool) {
							refreshwater();

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
						ffworldmap.ffworld_setfirstfreeospace(false);
						ffworldmap.ffworld_setfistactive(true);

					}
				} else {
					ffworldmap.ffworld_remove(getProjektor_ID());
				}

			}
		}
		if (watercool) {
			for (int y1 = 0 - reactordist; y1 <= reactordist; y1++) {
				for (int x1 = 0 - reactordist; x1 <= reactordist; x1++) {
					for (int z1 = 0 - reactordist; z1 <= reactordist; z1++) {

						if (worldObj.getBlockId(reactorx + x1, reactory + y1, reactorz + z1) == 8) {
							worldObj.setBlockWithNotify(reactorx + x1, reactory + y1, reactorz + z1, 0);

						}

					}
				}
			}
		}
		field_queue.clear();

	}

	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new ContainerProjektor(inventoryplayer, this);
	}

	public void checkupdates() {

		for (int x = 0 - 2; x <= 2; x++) {
			for (int y = 0 - 2; y <= 2; y++) {
				for (int z = 0 - 2; z <= 2; z++) {

					if ((x == 0 && y == 0 && (z == -2 || z == -1 || z == +1 || z == +2)) || (x == 0 && z == 0 && (y == -2 || y == -1 || y == +1 || y == +2)) || (z == 0 && y == 0 && (x == -2 || x == -1 || x == +1 || x == +2))) {

						if (worldObj.getBlockId(reactorx + x, reactory + y, reactorz + z) == mod_ModularForceFieldSystem.MFFSUpgrades.blockID) {

							int meta = worldObj.getBlockMetadata(reactorx + x, reactory + y, reactorz + z);

							TileEntity upgrades = worldObj.getBlockTileEntity(reactorx + x, reactory + y, reactorz + z);
							if (upgrades != null) {
								if (((TileEntityPassivUpgrade) upgrades).getconectet_ID() == 0 && meta == 8) {
									((TileEntityPassivUpgrade) upgrades).setconectet_ID(getProjektor_ID());
									((TileEntityPassivUpgrade) upgrades).setConnectet_typID((short) 2);
									worldObj.markBlockAsNeedsUpdate(upgrades.xCoord, upgrades.yCoord, upgrades.zCoord);
								}

								if (((TileEntityPassivUpgrade) upgrades).getconectet_ID() == getProjektor_ID() && ((TileEntityPassivUpgrade) upgrades).getConnectet_typID() == 2) {

									if (((TileEntityPassivUpgrade) upgrades).getActive() != this.getActive()) {
										((TileEntityPassivUpgrade) upgrades).setActive(this.getActive());

									}

								}
							}
						}

					}
					if (worldObj.getBlockId(reactorx + x, reactory + y, reactorz + z) == mod_ModularForceFieldSystem.MFFSMaschines.blockID && worldObj.getBlockMetadata(reactorx + x, reactory + y, reactorz + z) == 8) {

						TileEntity reactorcooler = worldObj.getBlockTileEntity(reactorx + x, reactory + y, reactorz + z);
						if (reactorcooler != null) {
							if (((TileEntityPassivUpgrade) reactorcooler).getconectet_ID() == 0) {
								((TileEntityPassivUpgrade) reactorcooler).setconectet_ID(getProjektor_ID());
								((TileEntityPassivUpgrade) reactorcooler).setConnectet_typID((short) 2);
								worldObj.markBlockAsNeedsUpdate(reactorcooler.xCoord, reactorcooler.yCoord, reactorcooler.zCoord);
							}

							if (((TileEntityPassivUpgrade) reactorcooler).getconectet_ID() == getProjektor_ID() && ((TileEntityPassivUpgrade) reactorcooler).getConnectet_typID() == 2) {

								if (((TileEntityPassivUpgrade) reactorcooler).getActive() != this.getActive()) {
									((TileEntityPassivUpgrade) reactorcooler).setActive(this.getActive());
									worldObj.markBlockAsNeedsUpdate(reactorcooler.xCoord, reactorcooler.yCoord, reactorcooler.zCoord);
								}
								if (((TileEntityReaktorCooler) reactorcooler).isIsreaktor() != this.isIsreaktor()) {
									((TileEntityReaktorCooler) reactorcooler).setIsreaktor(this.isIsreaktor());
								}
								if (((TileEntityReaktorCooler) reactorcooler).getMaxheat() != this.maxheat) {
									((TileEntityReaktorCooler) reactorcooler).setMaxheat(this.maxheat);
								}
								if (((TileEntityReaktorCooler) reactorcooler).getReaktorx() != this.reactorx) {
									((TileEntityReaktorCooler) reactorcooler).setReaktorx(this.reactorx);
								}
								if (((TileEntityReaktorCooler) reactorcooler).getReaktory() != this.reactory) {
									((TileEntityReaktorCooler) reactorcooler).setReaktory(this.reactory);
								}
								if (((TileEntityReaktorCooler) reactorcooler).getReaktorz() != this.reactorz) {
									((TileEntityReaktorCooler) reactorcooler).setReaktorz(this.reactorz);
								}

							}
						}

					}

					if (worldObj.getBlockId(reactorx + x, reactory + y, reactorz + z) == mod_ModularForceFieldSystem.MFFSMaschines.blockID && worldObj.getBlockMetadata(reactorx + x, reactory + y, reactorz + z) == 9) {

						TileEntity reactormonitor = worldObj.getBlockTileEntity(reactorx + x, reactory + y, reactorz + z);
						if (reactormonitor != null) {
							if (((TileEntityPassivUpgrade) reactormonitor).getconectet_ID() == 0) {
								((TileEntityPassivUpgrade) reactormonitor).setconectet_ID(getProjektor_ID());
								((TileEntityPassivUpgrade) reactormonitor).setConnectet_typID((short) 2);
								worldObj.markBlockAsNeedsUpdate(reactormonitor.xCoord, reactormonitor.yCoord, reactormonitor.zCoord);
							}

							if (((TileEntityPassivUpgrade) reactormonitor).getconectet_ID() == getProjektor_ID() && ((TileEntityPassivUpgrade) reactormonitor).getConnectet_typID() == 2) {

								if (((TileEntityPassivUpgrade) reactormonitor).getActive() != this.getActive()) {
									((TileEntityPassivUpgrade) reactormonitor).setActive(this.getActive());
									worldObj.markBlockAsNeedsUpdate(reactormonitor.xCoord, reactormonitor.yCoord, reactormonitor.zCoord);
								}
								if (((TileEntityReaktorMonitor) reactormonitor).isIsreaktor() != this.isIsreaktor()) {
									((TileEntityReaktorMonitor) reactormonitor).setIsreaktor(this.isIsreaktor());
								}
								if (((TileEntityReaktorMonitor) reactormonitor).getReaktorx() != this.reactorx) {
									((TileEntityReaktorMonitor) reactormonitor).setReaktorx(this.reactorx);
								}
								if (((TileEntityReaktorMonitor) reactormonitor).getReaktory() != this.reactory) {
									((TileEntityReaktorMonitor) reactormonitor).setReaktory(this.reactory);
								}
								if (((TileEntityReaktorMonitor) reactormonitor).getReaktorz() != this.reactorz) {
									((TileEntityReaktorMonitor) reactormonitor).setReaktorz(this.reactorz);
								}
								if (((TileEntityReaktorMonitor) reactormonitor).getMaxheat() != this.maxheat) {
									((TileEntityReaktorMonitor) reactormonitor).setMaxheat(this.maxheat);
								}

							}
						}

					}

				}
			}
		}

	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}
}