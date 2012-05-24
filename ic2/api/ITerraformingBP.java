package ic2.api;

import net.minecraft.src.World;

public interface ITerraformingBP
{
    public abstract int getConsume();

    public abstract int getRange();

    public abstract boolean terraform(World world, int x, int z, int yCoord);
}
