package ic2.api;

import net.minecraft.src.EntityPlayer;

public interface INetworkItemEventListener
{
    void onNetworkEvent(int metaData, EntityPlayer player, int event);
}
