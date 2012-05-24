package mffs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import net.minecraft.client.Minecraft;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet3Chat;
import net.minecraft.src.TileEntity;
import net.minecraft.src.mod_ModularForceFieldSystem;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

public class Functions {

	private final static Logger log = Logger.getLogger("Client");

	public static void DisplayInfo(String s) {
		log.info(s);
	}

	public static EntityPlayer getPlayerInstance() {
		return ModLoader.getMinecraftInstance().thePlayer;
	}

	public static boolean isSimulation() {
		return !ModLoader.getMinecraftInstance().isMultiplayerWorld();
	}

	public static File getMinecraftDir() {
		return Minecraft.getMinecraftDir();
	}

	public static int getBlockIdFor(String s, int i) {
		if (mod_ModularForceFieldSystem.config == null) {
			return i;
		}
		try {
			return (new Integer(mod_ModularForceFieldSystem.config.getOrCreateIntProperty(s, "1", i).value)).intValue();
		} catch (Exception exception) {
			System.out.println((new StringBuilder()).append("[ModularForceFieldSystem] Error while trying to access ID-List, config wasn't loaded properly!").toString());
		}
		return i;
	}

	public static int getGuiIdFor(String s, int i) {
		if (mod_ModularForceFieldSystem.config == null) {
			return i;
		}
		try {
			return (new Integer(mod_ModularForceFieldSystem.config.getOrCreateIntProperty(s, "0", i).value)).intValue();
		} catch (Exception exception) {
			System.out.println((new StringBuilder()).append("[ModularForceFieldSystem] Error while trying to access ID-List, config wasn't loaded properly!").toString());
		}
		return i;
	}

	public static int getItemIdFor(String s, int i) {
		if (mod_ModularForceFieldSystem.config == null) {
			return i;
		}
		try {
			return (new Integer(mod_ModularForceFieldSystem.config.getOrCreateIntProperty(s, "2", i).value)).intValue();
		} catch (Exception exception) {
			System.out.println((new StringBuilder()).append("[ModularForceFieldSystem] Error while trying to access ID-List, config wasn't loaded properly!").toString());
		}
		return i;
	}

	public static void ChattoPlayer(EntityPlayer player, String Message) {
		ModLoader.getMinecraftInstance().ingameGUI.addChatMessage(Message);
	}

	public static NBTTagCompound getTAGfromItemstack(ItemStack itemStack) {
		NBTTagCompound tag = itemStack.getTagCompound();
		if (tag == null) {
			tag = new NBTTagCompound();
			itemStack.setTagCompound(tag);
		}
		return tag;
	}

}
