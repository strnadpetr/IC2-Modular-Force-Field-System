package ic2.api;

import net.minecraft.src.EntityPlayer;

public interface IWrenchable
{
    boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side);

    short getFacing();

    void setFacing(short facing);

    boolean wrenchCanRemove(EntityPlayer entityPlayer);

    float getWrenchDropRate();
}
