package com.yungnickyoung.minecraft.ribbits.client.screen;

import com.yungnickyoung.minecraft.ribbits.config.RibbitsConfig;
import com.yungnickyoung.minecraft.ribbits.module.ConfigModule;
import com.yungnickyoung.minecraft.ribbits.util.GeoIP;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

/**
 * The mod's settings screen. Built from vanilla widgets so that it is available on every loader.
 */
public class RibbitsConfigScreen extends Screen {
    private static final int ROW_WIDTH = 220;
    private static final int ROW_HEIGHT = 20;

    private final @Nullable Screen lastScreen;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);

    private Checkbox prideFlagAllYear;
    private @Nullable Checkbox disablePrideFlagCN;
    private EditBox proxyHost;
    private EditBox proxyPort;
    private EditBox proxyUsername;
    private EditBox proxyPassword;

    public RibbitsConfigScreen(@Nullable Screen lastScreen) {
        super(Component.translatable("config.ribbits.title"));
        this.lastScreen = lastScreen;
    }

    public static Screen create(@Nullable Screen parent) {
        return new RibbitsConfigScreen(parent);
    }

    @Override
    protected void init() {
        RibbitsConfig config = ConfigModule.getConfig();

        this.layout.addTitleHeader(this.title, this.font);

        LinearLayout contents = this.layout.addToContents(LinearLayout.vertical().spacing(6));

        this.prideFlagAllYear = contents.addChild(Checkbox.builder(
                        Component.translatable("config.ribbits.general.prideFlagAllYear"), this.font)
                .selected(config.general.prideFlagAllYear)
                .tooltip(Tooltip.create(Component.translatable("config.ribbits.general.prideFlagAllYear.tooltip")))
                .maxWidth(ROW_WIDTH)
                .build());

        // Only shown where it is relevant, matching the behaviour of the previous config screen.
        if (GeoIP.isInChina()) {
            this.disablePrideFlagCN = contents.addChild(Checkbox.builder(
                            Component.translatable("config.ribbits.general.disablePrideFlagCN"), this.font)
                    .selected(config.general.disablePrideFlagCN)
                    .tooltip(Tooltip.create(Component.translatable("config.ribbits.general.disablePrideFlagCN.tooltip")))
                    .maxWidth(ROW_WIDTH)
                    .build());
        }

        this.proxyHost = this.addLabelledField(contents, "config.ribbits.network.proxyHost", config.network.proxyHost);
        this.proxyPort = this.addLabelledField(contents, "config.ribbits.network.proxyPort", Integer.toString(config.network.proxyPort));
        this.proxyUsername = this.addLabelledField(contents, "config.ribbits.network.proxyUsername", config.network.proxyUsername);
        this.proxyPassword = this.addLabelledField(contents, "config.ribbits.network.proxyPassword", config.network.proxyPassword);

        LinearLayout footer = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));
        footer.addChild(Button.builder(CommonComponents.GUI_DONE, button -> {
            this.save();
            this.onClose();
        }).width(100).build());
        footer.addChild(Button.builder(CommonComponents.GUI_CANCEL, button -> this.onClose()).width(100).build());

        this.layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    private EditBox addLabelledField(LinearLayout contents, String translationKey, String value) {
        contents.addChild(new StringWidget(ROW_WIDTH, 9, Component.translatable(translationKey), this.font));
        EditBox box = contents.addChild(new EditBox(this.font, ROW_WIDTH, ROW_HEIGHT, Component.translatable(translationKey)));
        box.setMaxLength(256);
        box.setValue(value);
        box.setTooltip(Tooltip.create(Component.translatable(translationKey + ".tooltip")));
        return box;
    }

    private void save() {
        RibbitsConfig config = ConfigModule.getConfig();

        config.general.prideFlagAllYear = this.prideFlagAllYear.selected();
        if (this.disablePrideFlagCN != null) {
            config.general.disablePrideFlagCN = this.disablePrideFlagCN.selected();
        }

        config.network.proxyHost = this.proxyHost.getValue().trim();
        config.network.proxyPort = parsePort(this.proxyPort.getValue(), config.network.proxyPort);
        config.network.proxyUsername = this.proxyUsername.getValue().trim();
        config.network.proxyPassword = this.proxyPassword.getValue();

        ConfigModule.save();
    }

    /**
     * Keeps the previous value when the field does not hold a usable port number.
     */
    private static int parsePort(String value, int fallback) {
        try {
            int port = Integer.parseInt(value.trim());
            return port >= 0 && port <= 65535 ? port : fallback;
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(this.lastScreen);
    }
}
