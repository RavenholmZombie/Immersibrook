package com.mesabrook.ib;

import java.io.File;
import java.util.Random;

import org.apache.logging.log4j.Logger;

import com.mesabrook.ib.proxy.CommonProxy;
import com.mesabrook.ib.tab.TabImmersibrook;
import com.mesabrook.ib.telecom.WirelessEmergencyAlertManager;
import com.mesabrook.ib.util.Reference;
import com.mesabrook.ib.util.config.ModConfig;
import com.mesabrook.ib.util.handlers.RegistryHandler;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, dependencies = "required-after:harvestcraft;required-after:immersiveengineering;required-after:jabcm", updateJSON = Reference.UPDATE_URL)
public class Main 
{
	
	@Instance
	public static Main instance;
    
    @SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.SERVER)
    public static CommonProxy proxy;
    
    public static Logger logger;
    public static boolean THERCMOD = false;
    public static boolean DYNMAP = false;
    public static final Random rand = new Random();
    
    // Config
 	public static File config;
    
    // Creative Tab
    public static final CreativeTabs IMMERSIBROOK_MAIN = new TabImmersibrook("tab_immersibrook");
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        RegistryHandler.preInitRegistries(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        RegistryHandler.initRegistries();
        proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	RegistryHandler.postInitRegistries(event);
    }
    
	@EventHandler
	public static void serverInit(FMLServerStartingEvent event)
	{
		RegistryHandler.serverRegistries(event);
	}
	
	@EventHandler
	public static void serverStarted(FMLServerStartedEvent event)
	{
		if (ModConfig.autoStartWEA)
		{
			WirelessEmergencyAlertManager.instance().start();
		}
	}
	
	@EventHandler
	public static void serverStopping(FMLServerStoppingEvent event)
	{
		if (ModConfig.autoStartWEA)
		{
			WirelessEmergencyAlertManager.instance().stop();
		}
	}
}
