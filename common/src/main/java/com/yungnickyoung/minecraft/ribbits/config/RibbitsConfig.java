package com.yungnickyoung.minecraft.ribbits.config;

/**
 * The mod's settings, as stored in {@code config/ribbits.json}.
 */
public class RibbitsConfig {
    public General general = new General();
    public Network network = new Network();

    public static class General {
        public boolean prideFlagAllYear = false;
        public boolean disablePrideFlagCN = true;
    }

    public static class Network {
        public String proxyHost = "";
        public int proxyPort = 8080;
        public String proxyUsername = "";
        public String proxyPassword = "";
    }
}
