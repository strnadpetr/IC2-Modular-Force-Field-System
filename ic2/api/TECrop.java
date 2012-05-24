package ic2.api;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.TileEntity;

public abstract class TECrop extends TileEntity
{
    public short id = -1;

    public byte size = 0;

    public byte statGrowth = 0;

    public byte statGain = 0;

    public byte statResistance = 0;

    public byte scanLevel = 0;

    public short[] custumData = new short[16];

    public int nutrientStorage = 0;

    public int waterStorage = 0;

    public int exStorage = 0;

    public abstract byte getHumidity();

    public abstract byte getNutrients();

    public abstract byte getAirQuality();

    public int getLightLevel()
    {
        return worldObj.getBlockLightValue(xCoord, yCoord, zCoord);
    }

    public abstract boolean pick(boolean manual);

    public abstract boolean harvest(boolean manual);

    public abstract void reset();

    public abstract void updateState();

    public abstract boolean isBlockBelow(Block block);

    public abstract ItemStack generateSeeds(short plant, byte growth, byte gain, byte resis, byte scan);

    public abstract void addLocal(String s1, String s2);
}
