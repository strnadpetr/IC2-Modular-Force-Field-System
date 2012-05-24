package ic2.api;

import net.minecraft.src.TileEntity;

public interface IEnergyEmitter extends IEnergyTile
{
    boolean emitsEnergyTo(TileEntity receiver, Direction direction);
}
