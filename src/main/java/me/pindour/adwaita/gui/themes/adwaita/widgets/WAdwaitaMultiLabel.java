package me.pindour.adwaita.gui.themes.adwaita.widgets;

import me.pindour.adwaita.api.text.RichText;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.widgets.WMultiLabel;
import meteordevelopment.meteorclient.utils.render.color.Color;

public class WAdwaitaMultiLabel extends WMultiLabel implements AdwaitaWidget {

    public WAdwaitaMultiLabel(RichText text, double maxWidth) {
        super(text.getPlainText(), false, maxWidth);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        double h = theme.textHeight(title);
        Color defaultColor = theme().textColor();

        for (int i = 0; i < lines.size(); i++) {
            renderer().text(
                    RichText.of(lines.get(i)).boldIf(title),
                    x,
                    y + h * i,
                    color != null ? color : defaultColor
            );
        }
    }
}
