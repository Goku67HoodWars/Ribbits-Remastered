package com.yungnickyoung.minecraft.ribbits;

import com.yungnickyoung.minecraft.ribbits.module.BlockModule;
import com.yungnickyoung.minecraft.ribbits.module.ConfigModule;
import com.yungnickyoung.minecraft.ribbits.module.ItemModule;
import com.yungnickyoung.minecraft.ribbits.module.NetworkModule;
import com.yungnickyoung.minecraft.ribbits.registry.RibbitsRegistries;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RibbitsCommon {
    public static final String MOD_ID = "ribbits";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static final String MC_VERSION_STRING = "26_2";

    /**
     * Runs the post-registration setup. Each loader bootstrap drains the registries FIRST
     * (Fabric writes directly; Forge/NeoForge replay during their RegisterEvent), then calls this.
     */
    public static void init() {
        RibbitsRegistries.initDataModules();
        ConfigModule.init();
        NetworkModule.init();
        BlockModule.registerFlammability();
        ItemModule.registerCompostablesAndDispenserBehavior();
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
