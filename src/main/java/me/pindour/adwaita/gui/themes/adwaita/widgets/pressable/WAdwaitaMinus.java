package me.pindour.adwaita.gui.themes.adwaita.widgets.pressable;

import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.widgets.pressable.WMinus;

public class WAdwaitaMinus extends WMinus implements AdwaitaWidget {

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        double pad = pad();
        double s = theme.scale(3);

        background(pressed, mouseOver).render();
        renderer.quad(x + pad, y + height / 2 - s / 2, width - pad * 2, s, theme().redColor());
    }
}
