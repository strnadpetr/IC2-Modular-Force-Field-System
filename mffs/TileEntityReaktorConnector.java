package mffs;

import ic2.api.Direction;
import ic2.api.EnergyNet;
import ic2.api.IEnergyConductor;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public class TileEntityReaktorConnector extends TileEntityPassivUpgrade implements IEnergyConductor {

	private boolean addedToEnergyNet;
	private boolean redpowert;
	private short ticker;

	public TileEntityReaktorConnector() {

		addedToEnergyNet = false;
		redpowert = false;
		ticker = 0;
	}

	public void updateEntity() {

		if (Functions.isSimulation()) {

			super.updateEntity();

			updatecheck();

			if (!addedToEnergyNet) {
				EnergyNet.getForWorld(worldObj).addTileEntity(this);
				addedToEnergyNet = true;
			}

			if (this.getTicker() == 10) {

				if (worldObj.isBlockGettingPowered(xCoord, yCoord, zCoord)) {
					if (redpowert == false) {
						redpowert = true;
						worldObj.markBlockAsNeedsUpdate(xCoord, yCoord, yCoord);
						this.notifyNeighbors(worldObj, xCoord, yCoord, zCoord);
					}
				} else {
					if (redpowert == true) {
						redpowert = false;
						worldObj.markBlockAsNeedsUpdate(xCoord, yCoord, yCoord);
						this.notifyNeighbors(worldObj, xCoord, yCoord, zCoord);
					}
				}

				this.setTicker((short) 0);
			}
			this.setTicker((short) (this.getTicker() + 1));

		}
	}

	public static void notifyNeighbors(World world, int i, int j, int k) {
		world.notifyBlocksOfNeighborChange(i, j, k, 0);
		world.notifyBlocksOfNeighborChange(i - 1, j, k, 0);
		world.notifyBlocksOfNeighborChange(i + 1, j, k, 0);
		world.notifyBlocksOfNeighborChange(i, j - 1, k, 0);
		world.notifyBlocksOfNeighborChange(i, j + 1, k, 0);
		world.notifyBlocksOfNeighborChange(i, j, k - 1, 0);
		world.notifyBlocksOfNeighborChange(i, j, k + 1, 0);
	}

	public boolean isredpowert() {
		return redpowert;
	}

	public short getTicker() {
		return ticker;
	}

	public void setTicker(short ticker) {
		this.ticker = ticker;
	}

	@Override
	public boolean isAddedToEnergyNet() {
		return addedToEnergyNet;
	}

	public void invalidate() {
		if (Functions.isSimulation() && addedToEnergyNet) {
			EnergyNet.getForWorld(worldObj).removeTileEntity(this);
			addedToEnergyNet = false;
		}
		super.invalidate();
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity tileentity, Direction direction) {

		return true;
	}

	@Override
	public boolean emitsEnergyTo(TileEntity tileentity, Direction direction) {

		return true;
	}

	@Override
	public double getConductionLoss() {

		return 0;
	}

	@Override
	public int getInsulationEnergyAbsorption() {

		return 9001;
	}

	@Override
	public int getInsulationBreakdownEnergy() {
		return 9001;
	}

	@Override
	public int getConductorBreakdownEnergy() {
		return 0;
	}

	@Override
	public void removeInsulation() {

	}

	@Override
	public void removeConductor() {

	}

}
