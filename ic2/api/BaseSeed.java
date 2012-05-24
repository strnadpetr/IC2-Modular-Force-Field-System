package ic2.api;

public class BaseSeed
{
    public int id;
    public int size;
    public int statGrowth;
    public int statGain;
    public int statResistance;
    public int stackSize;

    public BaseSeed(int id, int size, int statGrowth, int statGain, int statResistance, int stackSize)
    {
        super();
        this.id = id;
        this.size = size;
        this.statGrowth = statGrowth;
        this.statGain = statGain;
        this.statResistance = statResistance;
        this.stackSize = stackSize;
    }
}
