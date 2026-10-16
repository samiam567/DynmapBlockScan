package org.dynmapblockscan.neoforge_1_21_1;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

@Mod("dynmapblockscan")
public class DynmapBlockScanMod
{
    public static DynmapBlockScanPlugin.OurLog logger = new DynmapBlockScanPlugin.OurLog();
    public static DynmapBlockScanMod instance;

    public static Proxy proxy = FMLEnvironment.dist.isClient() ? new ClientProxy() : new Proxy();

    public static DynmapBlockScanPlugin plugin;
    public static File jarfile;

    public DynmapBlockScanMod(IEventBus modEventBus, ModContainer modContainer) {
        instance = this;
        modEventBus.addListener(this::setup);

        NeoForge.EVENT_BUS.register(this);

        FMLPaths.getOrCreateGameRelativePath(Path.of("config", "dynmapblockscan"));
        modContainer.registerConfig(ModConfig.Type.COMMON, SettingsConfig.SPEC, "dynmapblockscan/settings.toml");
    }

    public static class SettingsConfig
    {
        public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
        public static final ModConfigSpec SPEC;

        public static final ModConfigSpec.ConfigValue<List<? extends String>> excludeModules;
        public static final ModConfigSpec.ConfigValue<List<? extends String>> excludeBlockNames;

        static
        {
            BUILDER.comment("DynmapBlockScan settings");
            BUILDER.push("settings");
            excludeModules = BUILDER.comment("Which modules to exclude").defineList("exclude_modules", Arrays.asList("minecraft"), entry -> true);
            excludeBlockNames = BUILDER.comment("Which block names to exclude").defineList("exclude_blocknames", Arrays.asList(), entry -> true);
            BUILDER.pop();

            SPEC = BUILDER.build();
        }
    }

    public void setup(final FMLCommonSetupEvent event) {
        logger.info("setup");
        jarfile = ModList.get().getModFileById("dynmapblockscan").getFile().getFilePath().toFile();
    }

    private MinecraftServer server;

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        server = event.getServer();
        if(plugin == null)
            plugin = proxy.startServer(server);
        plugin.setDisabledModules((List<String>) SettingsConfig.excludeModules.get());
        plugin.setDisabledBlockNames((List<String>) SettingsConfig.excludeBlockNames.get());
        plugin.serverStarting();
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        plugin.serverStarted();
    }

    @SubscribeEvent
    public void serverStopping(ServerStoppingEvent event) {
        proxy.stopServer(plugin);
        plugin = null;
    }
}
