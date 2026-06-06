package me.pindour.adwaita.gui.themes.adwaita.widgets.pressable;

import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import me.pindour.adwaita.gui.widgets.pressable.WColorPicker;
import me.pindour.adwaita.utils.ColorUtils;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.renderer.packer.GuiTexture;
import meteordevelopment.meteorclient.utils.render.color.Color;

public class WAdwaitaColorPicker extends WColorPicker implements AdwaitaWidget {

    public WAdwaitaColorPicker(Color color, GuiTexture overlayTexture) {
        super(color, overlayTexture);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        background(mouseOver ? ColorUtils.darker(color) : color, theme().surface2Color()).render();

        if (mouseOver) {
            double s = theme.textHeight();

            renderer.quad(
                    x + width / 2 - s / 2,
                    y + height / 2 - s / 2,
                    s,
                    s,
                    overlayTexture,
                    theme().textColor()
            );
        }
    }
}
