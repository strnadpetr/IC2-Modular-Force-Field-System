package ic2.api;

import java.util.*;

import net.minecraft.src.Block;

public final class ExplosionWhitelist
{
    public static void addWhitelistedBlock(Block block)
    {
        whitelist.add(block);
    }

    public static void removeWhitelistedBlock(Block block)
    {
        whitelist.remove(block);
    }

    public static boolean isBlockWhitelisted(Block block)
    {
        return whitelist.contains(block);
    }

    private static Set<Block> whitelist = new HashSet<Block>();
}
