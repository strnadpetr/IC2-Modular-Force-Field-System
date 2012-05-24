package mffs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_ModularForceFieldSystem;

public abstract class TileEntityProjektor extends TileEntityMaschines implements IInventory {

	private ItemStack ProjektorItemStacks[];

	private boolean camouflage;
	private boolean zapper;
	private boolean dropper;
	private boolean Subwater;
	private boolean Dome;
	private boolean hardner;
	private int Projektor_ID;
	private int linkGenerator_ID;
	private boolean linkGenerator;
	private int linkPower;
	private int maxlinkPower;
	private short ticker;
	private int fpcost;
	private boolean create;
	private int energy_ticker = 0;
	private short ffmeta;
	private int textur;

	protected StringBuffer hasher = new StringBuffer();
	protected Queue<String> field_queue = new LinkedList<String>();

	public TileEntityProjektor() {
		Random random = new Random();

		ProjektorItemStacks = new ItemStack[1];
		linkGenerator_ID = 0;
		Projektor_ID = random.nextInt();
		linkGenerator = false;
		linkPower = 0;
		maxlinkPower = 1000000;
		ticker = 0;
		create = true;
		Subwater = false;
		Dome = false;
		hardner = false;
		dropper = true;
		zapper = false;
		camouflage = false;
		textur = -1;
		ffmeta = 0;
	}

	public boolean isCreate() {
		return create;
	}

	public void setCreate(boolean create) {
		this.create = create;
	}

	public short getTicker() {
		return ticker;
	}

	public void setTicker(short ticker) {
		this.ticker = ticker;
	}

	public int getTextur() {
		return textur;
	}

	public void setTextur(int textur) {
		this.textur = textur;
	}

	public short getffmeta() {
		return ffmeta;
	}

	public void setffmeta(short ffmeta) {
		this.ffmeta = ffmeta;
	}

	public int getMaxlinkPower() {
		return maxlinkPower;
	}

	public void setMaxlinkPower(int maxlinkPower) {
		this.maxlinkPower = maxlinkPower;
	}

	public boolean isLinkGenerator() {
		return linkGenerator;
	}

	public void setLinkGenerator(boolean linkGenerator) {
		this.linkGenerator = linkGenerator;
	}

	public int getLinkPower() {
		return linkPower;
	}

	public void setLinkPower(int linkPower) {
		this.linkPower = linkPower;
	}

	public int getLinkGenerator_ID() {
		return linkGenerator_ID;
	}

	public void setLinkGenerator_ID(int linkGenerator_ID) {
		this.linkGenerator_ID = linkGenerator_ID;
	}

	public boolean isCamouflage() {
		return camouflage;
	}

	public void setCamouflage(boolean camouflage) {
		this.camouflage = camouflage;
	}

	public boolean isZapper() {
		return zapper;
	}

	public void setZapper(boolean zapper) {
		this.zapper = zapper;
	}

	public boolean isSubwater() {
		return Subwater;
	}

	public void setSubwater(boolean subwater) {
		Subwater = subwater;
	}

	public boolean isDome() {
		return Dome;
	}

	public void setDome(boolean dome) {
		Dome = dome;
	}

	public boolean isDropper() {
		return dropper;
	}

	public void setDropper(boolean dropper) {
		this.dropper = dropper;
	}

	public boolean isHardner() {
		return hardner;
	}

	public void setHardner(boolean hardner) {
		this.hardner = hardner;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {

		super.readFromNBT(nbttagcompound);
		Projektor_ID = nbttagcompound.getInteger("Projektor_ID");
		ffmeta = nbttagcompound.getShort("ffmeta");
		textur = nbttagcompound.getInteger("textur");

		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		ProjektorItemStacks = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < ProjektorItemStacks.length) {
				ProjektorItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {

		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Projektor_ID", Projektor_ID);
		nbttagcompound.setShort("ffmeta", ffmeta);
		nbttagcompound.setInteger("textur", textur);

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < ProjektorItemStacks.length; i++) {
			if (ProjektorItemStacks[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				ProjektorItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);
	}

	public int getProjektor_ID() {
		return Projektor_ID;
	}

	public void Hardnerdrop(int x, int y, int z) {

		int blockid = worldObj.getBlockId(x, y, z);
		int blockmeta = worldObj.getBlockMetadata(x, y, z);

		if (blockid >= 122) {
			blockid = 0;
		}

		switch (blockid) {
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 51:
		case 54:
		case 69:
		case 90:
			blockid = 0;
			break;

		case 26:
			blockid = 355;
			blockmeta = 0;
			break;

		case 63:
		case 68:
			blockid = 323;
			blockmeta = 0;
			break;

		case 55:
			blockid = 331;
			blockmeta = 0;
			break;

		case 83:
			blockid = 338;
			blockmeta = 0;
			break;

		case 93:
		case 94:
			blockid = 356;
			blockmeta = 0;
			break;

		case 60:
		case 2:
			blockid = 3;
			blockmeta = 0;
			break;

		case 1:
			blockid = 4;
			blockmeta = 0;
			break;
		}

		if (blockid != 0) {
			if (this.dropper) {
				ItemStack itemstack = new ItemStack(blockid, 1, blockmeta);
				EntityItem entityitem = new EntityItem(worldObj, x, y, z, itemstack);
				worldObj.spawnEntityInWorld(entityitem);
			}
			worldObj.setBlockWithNotify(x, y, z, 0);
		}

	}

	public void FieldGenerate(boolean init) {

		int blockcounter = 0;
		int maxnbockcounter = 1000;

		if (init || energy_ticker == 20) {
			for (String hasher : field_queue) {

				ForceFieldWorldMap ffb = WorldMap.getForceFieldforWorld(worldObj).getFFWM(hasher.toString());

				if (ffb.ffworld_getfirstfreespace()) {

					TileEntity tileEntity = Linkgrid.getWorldMap(worldObj).getGenerator().get(ffb.ffworld_getfirstGenerator_ID());
					if (tileEntity instanceof TileEntityGeneratorCore && tileEntity != null) {

						int cost = 0;

						if (init) {
							cost = mod_ModularForceFieldSystem.forcefieldblockcost * mod_ModularForceFieldSystem.forcefieldblockcreatemodifier;
						} else {
							cost = mod_ModularForceFieldSystem.forcefieldblockcost;
						}

						if (ffb.ffworld_getfistmode() == 1) {
							cost *= mod_ModularForceFieldSystem.forcefieldblockzappermodifier;
						}

						((TileEntityGeneratorCore) tileEntity).Energylost(cost);

					}

				}

			}

			energy_ticker = 0;
		}

		for (String hasher : field_queue) {
			if (blockcounter == maxnbockcounter) {
				break;
			}
			ForceFieldWorldMap ffb = WorldMap.getForceFieldforWorld(worldObj).getFFWM(hasher);
			if (ffb.ffworld_getfirstProjektor_ID() == getProjektor_ID() && ffb.ffworld_getfistactive()) {
				if (worldObj.getChunkFromBlockCoords(ffb.getX(), ffb.getY()).isChunkLoaded) {
					if (this.hardner) {
						this.Hardnerdrop(ffb.getX(), ffb.getY(), ffb.getZ());
					}
					if (!ffb.ffworld_getfirstfreespace()) {
						if (!worldObj.getBlockMaterial(ffb.getX(), ffb.getY(), ffb.getZ()).isSolid() && worldObj.getBlockId(ffb.getX(), ffb.getY(), ffb.getZ()) <= 124) {
							ffb.ffworld_setfirstfreeospace(true);
						}
					}
					if (ffb.ffworld_getfirstfreespace()) {

						if (worldObj.getBlockId(ffb.getX(), ffb.getY(), ffb.getZ()) != mod_ModularForceFieldSystem.MFFSFieldblock.blockID || ffb.getsync() == false) {
							ffb.setsync(true);
							worldObj.setBlockAndMetadataWithNotify(ffb.getX(), ffb.getY(), ffb.getZ(), mod_ModularForceFieldSystem.MFFSFieldblock.blockID, ffb.ffworld_getfistmode());
							blockcounter++; // Count create blocks...
						}
					}
				}
			}
		}

		energy_ticker++;

	}

	public int Forcepowerneed(int blocks, boolean init) {
		int forcepower;
		forcepower = blocks * mod_ModularForceFieldSystem.forcefieldblockcost;
		if (init) {
			forcepower = (forcepower * mod_ModularForceFieldSystem.forcefieldblockcreatemodifier) + (forcepower * 5);
		}
		return forcepower;
	}

	// card function

	public void addfreqcard() {

		if (getStackInSlot(0) != null) {
			if (getStackInSlot(0).getItem() == mod_ModularForceFieldSystem.MFFSitemfc) {

				if (linkGenerator_ID != Functions.getTAGfromItemstack(getStackInSlot(0)).getInteger("Generator_ID")) {
					linkGenerator_ID = Functions.getTAGfromItemstack(getStackInSlot(0)).getInteger("Generator_ID");
				}

			}
		} else {
			linkGenerator_ID = 0;
		}

	}

	public ItemStack decrStackSize(int i, int j) {
		if (ProjektorItemStacks[i] != null) {
			if (ProjektorItemStacks[i].stackSize <= j) {
				ItemStack itemstack = ProjektorItemStacks[i];
				ProjektorItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = ProjektorItemStacks[i].splitStack(j);
			if (ProjektorItemStacks[i].stackSize == 0) {
				ProjektorItemStacks[i] = null;
			}
			return itemstack1;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		ProjektorItemStacks[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit()) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return entityplayer.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
		}
	}

	public ItemStack getStackInSlot(int i) {
		return ProjektorItemStacks[i];
	}

	public String getInvName() {

		return "Projektor";
	}

	public int getInventoryStackLimit() {
		return 1;
	}

	public int getSizeInventory() {
		return ProjektorItemStacks.length;
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return entityplayer.getDistance((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64D;
		}
	}

	@Override
	public void openChest() {

	}

	@Override
	public void closeChest() {

	}

}
