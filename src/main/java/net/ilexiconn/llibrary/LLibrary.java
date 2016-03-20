package net.ilexiconn.llibrary;

import net.ilexiconn.llibrary.server.ServerProxy;
import net.ilexiconn.llibrary.server.capability.EntityDataCapabilityImplementation;
import net.ilexiconn.llibrary.server.capability.EntityDataCapabilityStorage;
import net.ilexiconn.llibrary.server.capability.IEntityDataCapability;
import net.ilexiconn.llibrary.server.update.UpdateHandler;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "llibrary", name = "LLibrary", version = LLibrary.VERSION)
public class LLibrary {
    public static final String VERSION = "1.0.0-develop";

    @SidedProxy(serverSide = "net.ilexiconn.llibrary.server.ServerProxy", clientSide = "net.ilexiconn.llibrary.client.ClientProxy")
    public static ServerProxy PROXY;
    @Mod.Instance("llibrary")
    public static LLibrary INSTANCE;
    @CapabilityInject(IEntityDataCapability.class)
    public static Capability<IEntityDataCapability> ENTITY_DATA_CAPABILITY;

    public static Logger LOGGER;
    public static SimpleNetworkWrapper NETWORK_WRAPPER;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        LLibrary.LOGGER = LogManager.getLogger("LLibrary");
        LLibrary.NETWORK_WRAPPER = NetworkRegistry.INSTANCE.newSimpleChannel("llibrary");
        CapabilityManager.INSTANCE.register(IEntityDataCapability.class, new EntityDataCapabilityStorage(), EntityDataCapabilityImplementation.class);
        LLibrary.PROXY.onPreInit();
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        LLibrary.PROXY.onInit();
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        UpdateHandler.INSTANCE.searchForUpdates();
        LLibrary.PROXY.onPostInit();
    }
}