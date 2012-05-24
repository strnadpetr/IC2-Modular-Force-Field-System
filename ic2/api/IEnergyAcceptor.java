package ic2.api;

import net.minecraft.src.TileEntity;

public interface IEnergyAcceptor extends IEnergyTile
{
    boolean acceptsEnergyFrom(TileEntity emitter, Direction direction);
}
