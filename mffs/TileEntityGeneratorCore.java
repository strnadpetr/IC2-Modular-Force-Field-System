package mffs;

import java.util.List;
import java.util.Random;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_ModularForceFieldSystem;

public class TileEntityGeneratorCore extends TileEntityMaschines implements IInventory {

	private ItemStack inventory[];
	private int forcepower;
	private int maxforcepower;
	private short transmitrange;
	private int Generator_ID;
	private boolean create;
	private short linketprojektor;
	private byte delayupdate = 0;

	public TileEntityGeneratorCore() {

		inventory = new ItemStack[1];
		Random random = new Random();
		transmitrange = 8;
		forcepower = 0;
		maxforcepower = 10000000;
		Generator_ID = 0;
		linketprojektor = 0;
		create = true;

	}

	public void freqencoding() {

		if (Functions.isSimulation()) {

			if (getStackInSlot(0) != null) {
				if (getStackInSlot(0).getItem() == mod_ModularForceFieldSystem.MFFSitemcardempty) {

					this.setInventorySlotContents(0, new ItemStack(mod_ModularForceFieldSystem.MFFSitemfc));

					Functions.getTAGfromItemstack(getStackInSlot(0)).setInteger("Generator_ID", Generator_ID);
					this.onInventoryChanged();

				}
			}
		}
	}

	public Container getContainer(InventoryPlayer inventoryplayer) {
		return new ContainerGenerator(inventoryplayer, this);
	}

	public void setMaxforcepower(int maxforcepower) {
		this.maxforcepower = maxforcepower;
	}

	public int getMaxforcepower() {
		return maxforcepower;
	}

	public Short getLinketprojektor() {
		return linketprojektor;
	}

	public void setLinketprojektor(Short linketprojektor) {
		this.linketprojektor = linketprojektor;
	}

	public void addtogrid() {
		if (Functions.isSimulation()) {
			// Linkgrid.getWorldMap(worldObj).getGenerator().put(getGenerator_ID(),
			// this);
		}
	}

	public void removefromgrid() {
		if (Functions.isSimulation()) {
			Linkgrid.getWorldMap(worldObj).getGenerator().remove(getGenerator_ID());
		}
	}

	public int gaugeFuelScaled(int i) {
		return (i * this.getForcepower()) / maxforcepower;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);
		forcepower = nbttagcompound.getInteger("forcepower");
		maxforcepower = nbttagcompound.getInteger("maxforcepower");
		transmitrange = nbttagcompound.getShort("transmitrange");
		Generator_ID = nbttagcompound.getInteger("Generator_ID");

		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		inventory = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < inventory.length) {
				inventory[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);

		nbttagcompound.setInteger("forcepower", forcepower);
		nbttagcompound.setInteger("maxforcepower", maxforcepower);
		nbttagcompound.setShort("transmitrange", transmitrange);
		nbttagcompound.setInteger("Generator_ID", Generator_ID);

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}

	public void CheckconInjektor() {
		TileEntity tileEntity = worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord);
		if (tileEntity instanceof TileEntityGeneratorInjector) {
			if (((TileEntityGeneratorInjector) tileEntity).getRemGenerator_ID() == 0) {
				((TileEntityGeneratorInjector) tileEntity).setRemGenerator_ID(Generator_ID);
			}

		}
	}

	public void Energylost(int fpcost) {

		if (this.getForcepower() >= 0) {
			this.setForcepower(this.getForcepower() - fpcost);
		}
		if (this.getForcepower() < 0) {
			this.setForcepower(0);
		}

	}

	public void CheckconUprades() {
		short temp_transmitrange = 8;
		int temp_maxforcepower = 10000000;

		for (int xoffset = -1; xoffset <= +1; xoffset++) {
			for (int zoffset = -1; zoffset <= +1; zoffset++) {

				if (!(xoffset == 0 && zoffset == 0)) {

					TileEntity tileEntity = worldObj.getBlockTileEntity(xCoord + xoffset, yCoord, zCoord + zoffset);
					if (tileEntity instanceof TileEntityPassivUpgrade && tileEntity != null) {

						int meta = worldObj.getBlockMetadata(xCoord + xoffset, yCoord, zCoord + zoffset);

						if (((TileEntityPassivUpgrade) tileEntity).getconectet_ID() == 0 && (meta == 4 || meta == 5)) {
							((TileEntityPassivUpgrade) tileEntity).setconectet_ID(Generator_ID);
							((TileEntityPassivUpgrade) tileEntity).setConnectet_typID((short) 1);

							worldObj.markBlockAsNeedsUpdate(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
						}

						if (((TileEntityPassivUpgrade) tileEntity).getconectet_ID() == this.Generator_ID) {

							if (((TileEntityPassivUpgrade) tileEntity).getActive() != this.getActive()) {
								((TileEntityPassivUpgrade) tileEntity).setActive(this.getActive());
							}

							switch (meta) {
							case 4:
								temp_maxforcepower += 2000000;
								break;
							case 5:
								temp_transmitrange = (short) (temp_transmitrange * 2);
								break;
							}

						}
					}
				}
			}
		}

		if (this.getTransmitrange() != temp_transmitrange) {
			this.setTransmitrange(temp_transmitrange);

		}
		if (this.getMaxforcepower() != temp_maxforcepower) {
			this.setMaxforcepower(temp_maxforcepower);
		}
		if (this.getForcepower() > this.maxforcepower) {
			this.setForcepower(maxforcepower);
		}

	}

	public void updateEntity() {

		if (Functions.isSimulation()) {

			if (create) {

				if (Generator_ID == 0) {
					Generator_ID = Linkgrid.getWorldMap(worldObj).newGenerator_ID(this);
					Linkgrid.getWorldMap(worldObj).getGenerator().put(getGenerator_ID(), this);
				} else {
					Linkgrid.getWorldMap(worldObj).getGenerator().put(getGenerator_ID(), this);
				}
				create = false;
			}

			if (delayupdate == 10) {
				CheckconInjektor();
				CheckconUprades();
				delayupdate = 0;
			} else {
				delayupdate++;
			}

			setLinketprojektor((short) Linkgrid.getWorldMap(worldObj).conProjektors(getGenerator_ID(), xCoord, yCoord, zCoord, getTransmitrange()));

			boolean powerdirekt = worldObj.isBlockGettingPowered(xCoord, yCoord, zCoord);
			boolean powerindrekt = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);

			if (powerdirekt || powerindrekt) {

				if (getActive() != true) {
					setActive(true);
				}
			} else {
				if (getActive() != false) {
					setActive(false);
				}
			}

			freqencoding();
		}
		if (getActive() && getWrenchDropRate() != -1.0F) {
			setWrenchRate(-1.0F);
		}
		if (!getActive() && getWrenchDropRate() != 1.0F) {
			setWrenchRate(1.0F);
		}
	}

	public int getForcepower() {
		return forcepower;
	}

	public void setForcepower(int f) {

		forcepower = f;

	}

	public void setTransmitrange(short transmitrange) {

		this.transmitrange = transmitrange;
	}

	public short getTransmitrange() {
		return transmitrange;
	}

	public int getGenerator_ID() {
		return Generator_ID;
	}

	public int getSizeInventory() {
		return inventory.length;
	}

	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	public int getInventoryStackLimit() {
		return 1;
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return entityplayer.getDistance((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
		}
	}

	public ItemStack decrStackSize(int i, int j) {
		if (inventory[i] != null) {
			if (inventory[i].stackSize <= j) {
				ItemStack itemstack = inventory[i];
				inventory[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = inventory[i].splitStack(j);
			if (inventory[i].stackSize == 0) {
				inventory[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public void openChest() {

	}

	public String getInvName() {

		return "GeneratorCore";
	}

	public void closeChest() {

	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

}
