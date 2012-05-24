package ic2.api;

import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public final class Ic2Recipes
{
    public static void addCraftingRecipe(ItemStack result, Object... args)
    {
        try
        {
            Class.forName(getPackage() + ".common.AdvRecipe").getMethod("addAndRegister", ItemStack.class, Array.newInstance(Object.class, 0).getClass()).invoke(null, result, args);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void addShapelessCraftingRecipe(ItemStack result, Object... args)
    {
        try
        {
            Class.forName(getPackage() + ".common.AdvShapelessRecipe").getMethod("addAndRegister", ItemStack.class, Array.newInstance(Object.class, 0).getClass()).invoke(null, result, args);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static List<Map.Entry<ItemStack, ItemStack> > getCompressorRecipes()
    {
        try
        {
            return (List<Map.Entry<ItemStack, ItemStack> >) Class.forName(getPackage() + ".common.TileEntityCompressor").getField("recipes").get(null);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void addCompressorRecipe(ItemStack input, ItemStack output)
    {
        getCompressorRecipes().add(new AbstractMap.SimpleEntry<ItemStack, ItemStack>(input, output));
    }

    public static ItemStack getCompressorOutputFor(ItemStack input, boolean adjustInput)
    {
        return getOutputFor(input, adjustInput, getCompressorRecipes());
    }

    public static List<Map.Entry<ItemStack, ItemStack> > getExtractorRecipes()
    {
        try
        {
            return (List<Map.Entry<ItemStack, ItemStack> >) Class.forName(getPackage() + ".common.TileEntityExtractor").getField("recipes").get(null);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void addExtractorRecipe(ItemStack input, ItemStack output)
    {
        getExtractorRecipes().add(new AbstractMap.SimpleEntry<ItemStack, ItemStack>(input, output));
    }

    public static ItemStack getExtractorOutputFor(ItemStack input, boolean adjustInput)
    {
        return getOutputFor(input, adjustInput, getExtractorRecipes());
    }

    public static List<Map.Entry<ItemStack, ItemStack> > getMaceratorRecipes()
    {
        try
        {
            return (List<Map.Entry<ItemStack, ItemStack> >) Class.forName(getPackage() + ".common.TileEntityMacerator").getField("recipes").get(null);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void addMaceratorRecipe(ItemStack input, ItemStack output)
    {
        getMaceratorRecipes().add(new AbstractMap.SimpleEntry<ItemStack, ItemStack>(input, output));
    }

    public static ItemStack getMaceratorOutputFor(ItemStack input, boolean adjustInput)
    {
        return getOutputFor(input, adjustInput, getMaceratorRecipes());
    }

    private static ItemStack getOutputFor(ItemStack input, boolean adjustInput, List<Map.Entry<ItemStack, ItemStack> > recipeList)
    {
        assert input != null;

        for (Map.Entry<ItemStack, ItemStack> entry: recipeList)
        {
            if (entry.getKey().isItemEqual(input) && input.stackSize >= entry.getKey().stackSize)
            {
                if (adjustInput)
                {
                    input.stackSize -= entry.getKey().stackSize;
                }

                return entry.getValue().copy();
            }
        }

        return null;
    }

    public static List<ItemStack> getRecyclerBlacklist()
    {
        try
        {
            return (List<ItemStack>) Class.forName(getPackage() + ".common.TileEntityRecycler").getField("blacklist").get(null);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void addRecyclerBlacklistItem(ItemStack newBlacklistedItem)
    {
        getRecyclerBlacklist().add(newBlacklistedItem);
    }

    public static void addRecyclerBlacklistItem(Item newBlacklistedItem)
    {
        addRecyclerBlacklistItem(new ItemStack(newBlacklistedItem));
    }

    public static void addRecyclerBlacklistItem(Block newBlacklistedBlock)
    {
        addRecyclerBlacklistItem(new ItemStack(newBlacklistedBlock));
    }

    public static boolean isRecyclerInputBlacklisted(ItemStack itemStack)
    {
        for (ItemStack blackItem: getRecyclerBlacklist())
        {
            if (itemStack.isItemEqual(blackItem))
            {
                return true;
            }
        }

        return false;
    }

    public static List<Map.Entry<ItemStack, Float>> getScrapboxDrops()
    {
        try
        {
            return (List<Map.Entry<ItemStack, Float>>) Class.forName(getPackage() + ".common.ItemScrapbox").getMethod("getDropList").invoke(null);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void addScrapboxDrop(ItemStack dropItem, float chance)
    {
        try
        {
            Class.forName(getPackage() + ".common.ItemScrapbox").getMethod("addDrop", ItemStack.class, float.class).invoke(null, dropItem, chance);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void addScrapboxDrop(Item dropItem, float chance)
    {
        addScrapboxDrop(new ItemStack(dropItem), chance);
    }

    public static void addScrapboxDrop(Block dropItem, float chance)
    {
        addScrapboxDrop(new ItemStack(dropItem), chance);
    }

    public static List<Map.Entry<ItemStack, Integer>> getMatterAmplifiers()
    {
        try
        {
            return (List<Map.Entry<ItemStack, Integer>>) Class.forName(getPackage() + ".common.TileEntityMatter").getField("amplifiers").get(null);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void addMatterAmplifier(ItemStack amplifierItem, int value)
    {
        getMatterAmplifiers().add(new AbstractMap.SimpleEntry<ItemStack, Integer>(amplifierItem, value));
    }

    public static void addMatterAmplifier(Item amplifierItem, int value)
    {
        addMatterAmplifier(new ItemStack(amplifierItem), value);
    }

    public static void addMatterAmplifier(Block amplifierItem, int value)
    {
        addMatterAmplifier(new ItemStack(amplifierItem), value);
    }

    private static String getPackage()
    {
        Package pkg = Ic2Recipes.class.getPackage();

        if (pkg != null)
        {
            return pkg.getName().substring(0, pkg.getName().lastIndexOf('.'));
        }
        else
        {
            return "ic2";
        }
    }
}
