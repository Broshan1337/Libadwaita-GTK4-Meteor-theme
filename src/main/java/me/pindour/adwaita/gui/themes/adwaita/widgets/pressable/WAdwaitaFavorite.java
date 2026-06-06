package me.pindour.adwaita.gui.themes.adwaita.widgets.pressable;

import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import me.pindour.adwaita.gui.themes.adwaita.icons.AdwaitaBuiltinIcons;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.widgets.pressable.WFavorite;
import meteordevelopment.meteorclient.utils.render.color.Color;

public class WAdwaitaFavorite extends WFavorite implements AdwaitaWidget {
    public WAdwaitaFavorite(boolean checked) {
        super(checked);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        double pad = pad();
        double s = theme.textHeight();

        renderer.quad(
                x + pad,
                y + pad,
                s,
                s,
                checked ? AdwaitaBuiltinIcons.BOOKMARK_YES.texture() : AdwaitaBuiltinIcons.BOOKMARK_NO.texture(),
                getColor()
        );
    }

    @Override
    protected Color getColor() {
        return theme().textColor();
    }
}
