package me.pindour.adwaita.gui.themes.adwaita.widgets.pressable;

import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import me.pindour.adwaita.gui.themes.adwaita.icons.AdwaitaBuiltinIcons;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.widgets.pressable.WTriangle;

public class WAdwaitaTriangle extends WTriangle implements AdwaitaWidget {
    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        double s = theme.textHeight() * 0.75;
        double pad = pad();

        renderer.rotatedQuad(
                x + width - pad - s,
                y + height / 2 - s / 2,
                s,
                s,
                rotation,
                AdwaitaBuiltinIcons.ARROW.texture(),
                theme.textColor()
        );
    }
}
