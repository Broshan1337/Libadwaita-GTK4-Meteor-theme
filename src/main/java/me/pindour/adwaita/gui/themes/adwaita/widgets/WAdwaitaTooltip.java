package me.pindour.adwaita.gui.themes.adwaita.widgets;

import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.widgets.WTooltip;

public class WAdwaitaTooltip extends WTooltip implements AdwaitaWidget {

    public WAdwaitaTooltip(String text) {
        super(text);
    }

    @Override
    public void init() {
        add(theme.label(text)).padVertical(4).padHorizontal(6);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        background(theme().baseColor(), theme().surface0Color()).render();
    }
}
