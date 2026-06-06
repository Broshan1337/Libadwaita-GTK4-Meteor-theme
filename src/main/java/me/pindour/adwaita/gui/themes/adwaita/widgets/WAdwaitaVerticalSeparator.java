package me.pindour.adwaita.gui.themes.adwaita.widgets;

import me.pindour.adwaita.gui.themes.adwaita.AdwaitaGuiTheme;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.widgets.WVerticalSeparator;

public class WAdwaitaVerticalSeparator extends WVerticalSeparator implements AdwaitaWidget {

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        AdwaitaGuiTheme theme = theme();
        double s = theme.scale(1);

        renderer.quad(
                x,
                y,
                s,
                height,
                theme.surface0Color()
        );
    }
}
