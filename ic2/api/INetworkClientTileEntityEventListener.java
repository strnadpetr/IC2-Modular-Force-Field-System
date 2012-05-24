package ic2.api;

import net.minecraft.src.EntityPlayer;

public interface INetworkClientTileEntityEventListener
{
    void onNetworkEvent(EntityPlayer player, int event);
}
