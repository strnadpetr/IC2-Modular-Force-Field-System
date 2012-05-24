package mffs;

import ic2.api.Direction;
import ic2.api.EnergyNet;
import ic2.api.ExplosionWhitelist;
import ic2.api.IEnergySink;
import net.minecraft.src.ModLoader;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_ModularForceFieldSystem;

public class TileEntityGeneratorEUInjector extends TileEntityGeneratorInjector implements IEnergySink {

	private boolean addedToEnergyNet;
	private TileEntityGeneratorCore Core;
	private byte delayupdate = 0;

	public TileEntityGeneratorEUInjector() {

		addedToEnergyNet = false;

	}

	public void updateEntity() {

		if (Functions.isSimulation()) {

			if (!this.isContocore() && this.getRemGenerator_ID() != 0) {
				Core = Linkgrid.getWorldMap(worldObj).getGenerator().get(this.getRemGenerator_ID());
				if (Core != null) {
					this.setContocore(true);
				}
			}

			if (this.isContocore()) {
				if (delayupdate == 20)

				{

					TileEntity Generator = Linkgrid.getWorldMap(worldObj).getGenerator().get(this.getRemGenerator_ID());
					if (Generator == null) {
						this.setContocore(false);
						this.setRemGenerator_ID(0);
					} else {
						if (this.getActive() != ((TileEntityMaschines) Generator).getActive()) {
							this.setActive(((TileEntityMaschines) Generator).getActive());
							worldObj.markBlockAsNeedsUpdate(xCoord, yCoord, zCoord);
						}
					}
					delayupdate = 0;
				}

				delayupdate++;
			}

			if (!addedToEnergyNet) {
				EnergyNet.getForWorld(worldObj).addTileEntity(this);
				addedToEnergyNet = true;
			}

			if (getActive() && getWrenchDropRate() != -1.0F) {
				setWrenchRate(-1.0F);
				ExplosionWhitelist.addWhitelistedBlock(mod_ModularForceFieldSystem.MFFSMaschines);
			}
			if (!getActive() && getWrenchDropRate() != 1.0F) {
				setWrenchRate(1.0F);
				ExplosionWhitelist.removeWhitelistedBlock(mod_ModularForceFieldSystem.MFFSMaschines);
			}

		}

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
	public boolean isAddedToEnergyNet() {
		return addedToEnergyNet;
	}

	@Override
	public boolean demandsEnergy() {
		if (this.isContocore() && getActive()) {
			if (Core.getForcepower() < Core.getMaxforcepower()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public int injectEnergy(Direction directionFrom, int amount) {
		if (this.isContocore()) {
			Core.setForcepower(Core.getForcepower() + (amount * 10));
			int j = 0;
			if (Core.getForcepower() > Core.getMaxforcepower()) {
				j = Core.getForcepower() - Core.getMaxforcepower();
				Core.setForcepower(Core.getMaxforcepower());
			}
			return (int) (j / 10);
		} else {
			return amount;
		}
	}

}
