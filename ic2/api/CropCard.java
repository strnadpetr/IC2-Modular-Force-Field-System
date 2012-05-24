package ic2.api;

import java.util.HashMap;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;

public abstract class CropCard
{
    public abstract String name();

    public String discoveredBy()
    {
        return "Alblaka";
    }

    public String desc(int i)
    {
        String[] att = attributes();

        if (att == null || att.length == 0)
        {
            return "";
        }

        if (i == 0)
        {
            String s = att[0];

            if (att.length >= 2)
            {
                s += ", " + att[1];

                if (att.length >= 3)
                {
                    s += ",";
                }
            }

            return s;
        }
        else
        {
            if (att.length < 3)
            {
                return "";
            }

            String s = att[2];

            if (att.length >= 4)
            {
                s += ", " + att[3];
            }

            return s;
        }
    }

    public abstract int tier();

    public abstract int stat(int n);

    public abstract String[] attributes();

    public abstract int getSpriteIndex(TECrop crop);

    public String getTextureFile()
    {
        return "/ic2/sprites/crops_0.png";
    }

    public int growthDuration(TECrop crop)
    {
        return tier() * 200;
    }

    public abstract boolean canGrow(TECrop crop);

    public int weightInfluences(TECrop crop, float humidity, float nutrients, float air)
    {
        return (int)(humidity + nutrients + air);
    }

    public boolean canCross(TECrop crop)
    {
        return crop.size >= 3;
    }

    public boolean rightclick(TECrop crop, EntityPlayer player)
    {
        return crop.harvest(true);
    }

    public abstract boolean canBeHarvested(TECrop crop);

    public float dropGainChance()
    {
        float base = 1F;

        for (int i = 0; i < tier(); i++)
        {
            base *= 0.95;
        }

        return base;
    }

    public abstract ItemStack getGain(TECrop crop);

    public byte getSizeAfterHarvest(TECrop crop)
    {
        return 1;
    }

    public boolean leftclick(TECrop crop, EntityPlayer player)
    {
        return crop.pick(true);
    }

    public float dropSeedChance(TECrop crop)
    {
        if (crop.size == 1)
        {
            return 0;
        }

        float base = 0.5F;

        if (crop.size == 2)
        {
            base /= 2F;
        }

        for (int i = 0; i < tier(); i++)
        {
            base *= 0.8;
        }

        return base;
    }

    public ItemStack getSeeds(TECrop crop)
    {
        return crop.generateSeeds(crop.id, crop.statGrowth, crop.statGain, crop.statResistance, crop.scanLevel);
    }

    public void onNeighbourChange(TECrop crop) {}

    public boolean emitRedstone(TECrop crop)
    {
        return false;
    }

    public void onBlockDestroyed(TECrop crop) {}

    public int getEmittedLight(TECrop crop)
    {
        return 0;
    }

    public boolean onEntityCollision(TECrop crop, Entity entity)
    {
        if (entity instanceof EntityLiving)
        {
            return ((EntityLiving)entity).motionY < 0 || ((EntityLiving)entity).isSprinting();
        }

        return false;
    }

    public void tick(TECrop crop) {}

    public boolean isWeed(TECrop crop)
    {
        return crop.size >= 2 && (crop.id == 0 || crop.statGrowth >= 24);
    }

    public final int getId()
    {
        for (int i = 0; i < cropCardList.length; i++)
        {
            if (this == cropCardList[i])
            {
                return i;
            }
        }

        return -1;
    }

    private static final CropCard[] cropCardList = new CropCard[256];

    public static int cropCardListLength()
    {
        return cropCardList.length;
    }

    public static final CropCard getCrop(int id)
    {
        if (id < 0 || id >= cropCardList.length)
        {
            return cropCardList[0];
        }

        if (cropCardList[id] == null)
        {
            System.out.println("[IndustrialCraft] Something tried to access non-existant cropID #" + id + "!!!");
            return cropCardList[0];
        }

        return cropCardList[id];
    }

    public static final boolean idExists(int id)
    {
        return !(id < 0 || id >= cropCardList.length || cropCardList[id] == null);
    }

    public static final short registerCrop(CropCard crop)
    {
        for (short x = 0; x < cropCardList.length; x++)
        {
            if (cropCardList[x] == null)
            {
                cropCardList[x] = crop;
                nameReference.addLocal("item.cropSeed" + x + ".name", crop.name() + " Seeds");
                return x;
            }
        }

        return -1;
    }

    public static final boolean registerCrop(CropCard crop, int i)
    {
        if (i < 0 || i >= cropCardList.length)
        {
            return false;
        }

        if (cropCardList[i] == null)
        {
            cropCardList[i] = crop;
            nameReference.addLocal("item.cropSeed" + i + ".name", crop.name() + " Seeds");
            return true;
        }

        System.out.println("[IndustrialCraft] Cannot add crop:" + crop.name() + " on ID #" + i + ", slot already occupied by crop:" + cropCardList[i].name());
        return false;
    }

    public static TECrop nameReference;

    private static HashMap<ItemStack, BaseSeed> baseseeds = new HashMap<ItemStack, BaseSeed>();

    public static boolean registerBaseSeed(ItemStack stack, int id, int size, int growth, int gain, int resistance)
    {
        for (ItemStack key : baseseeds.keySet())
            if (key.itemID == stack.itemID && key.getItemDamage() == stack.getItemDamage())
            {
                return false;
            }

        baseseeds.put(stack, new BaseSeed(id, size, growth, gain, resistance, stack.stackSize));
        return true;
    }

    public static BaseSeed getBaseSeed(ItemStack stack)
    {
        if (stack == null)
        {
            return null;
        }

        for (ItemStack key : baseseeds.keySet())
        {
            if (key.itemID == stack.itemID &&
                    (key.getItemDamage() == -1 || key.getItemDamage() == stack.getItemDamage()))
            {
                return baseseeds.get(key);
            }
        }

        return null;
    }
}
