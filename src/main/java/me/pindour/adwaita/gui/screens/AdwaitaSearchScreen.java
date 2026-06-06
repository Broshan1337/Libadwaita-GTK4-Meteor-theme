package me.pindour.adwaita.gui.screens;

import me.pindour.adwaita.gui.themes.adwaita.AdwaitaGuiTheme;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.WidgetScreen;
import meteordevelopment.meteorclient.utils.Utils;

public class AdwaitaSearchScreen extends WidgetScreen {
    private final AdwaitaGuiTheme theme;

    public AdwaitaSearchScreen(GuiTheme theme) {
        super(theme, "Search");
        this.theme = (AdwaitaGuiTheme) theme;
    }

    @Override
    public void initWidgets() {
        double margin = Utils.getWindowHeight() / 8.0;
        add(theme.search()).marginTop(margin).top().centerX();
    }
}
