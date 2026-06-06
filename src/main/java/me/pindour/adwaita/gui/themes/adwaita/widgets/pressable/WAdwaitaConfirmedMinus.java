package me.pindour.adwaita.gui.themes.adwaita.widgets.pressable;
//? if >=1.21.10 {
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaGuiTheme;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.widgets.pressable.WConfirmedMinus;
import meteordevelopment.meteorclient.utils.render.color.Color;

public class WAdwaitaConfirmedMinus extends WConfirmedMinus implements AdwaitaWidget {

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        AdwaitaGuiTheme theme = theme();
        double pad = pad();
        double s = theme.scale(3);

        Color outline = theme.outlineColor.get(pressed, mouseOver);
        Color fg = pressedOnce ? theme.backgroundColor.get(pressed, mouseOver) : theme.redColor();
        Color bg = pressedOnce ? theme.redColor() : theme.backgroundColor.get(pressed, mouseOver);

        background(bg, outline).render();
        renderer.quad(x + pad, y + height / 2 - s / 2, width - pad * 2, s, fg);
    }
}
//?}