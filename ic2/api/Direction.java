package ic2.api;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

public enum Direction
{
    XN(0),

    XP(1),

    YN(2),

    YP(3),

    ZN(4),

    ZP(5);

    Direction(int dir)
    {
        this.dir = dir;
    }

    public TileEntity applyToTileEntity(TileEntity tileEntity)
    {
        int coords[] = { tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord };
        coords[dir / 2] += getSign();

        if (tileEntity.worldObj != null && tileEntity.worldObj.blockExists(coords[0], coords[1], coords[2]))
        {
            return tileEntity.worldObj.getBlockTileEntity(coords[0], coords[1], coords[2]);
        }
        else
        {
            return null;
        }
    }

    public Direction getInverse()
    {
        int inverseDir = dir - getSign();

        for (Direction direction: Direction.values())
        {
            if (direction.dir == inverseDir)
            {
                return direction;
            }
        }

        return this;
    }

    public int toSideValue()
    {
        return (dir + 4) % 6;
    }

    private int getSign()
    {
        return (dir % 2) * 2 - 1;
    }

    private int dir;
}
