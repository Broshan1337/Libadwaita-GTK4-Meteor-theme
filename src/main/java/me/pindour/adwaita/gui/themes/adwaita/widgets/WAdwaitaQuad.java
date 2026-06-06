package me.pindour.adwaita.gui.themes.adwaita.widgets;

import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.widgets.WQuad;
import meteordevelopment.meteorclient.utils.render.color.Color;

public class WAdwaitaQuad extends WQuad implements AdwaitaWidget {

    public WAdwaitaQuad(Color color) {
        super(color);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.quad(x, y, width, height, color);
    }
}
