package me.pindour.adwaita.gui.themes.adwaita.widgets.input;

import me.pindour.adwaita.api.render.Corners;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaGuiTheme;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import me.pindour.adwaita.gui.widgets.input.WMultiSelect;
import me.pindour.adwaita.utils.ColorUtils;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.utils.render.color.Color;

import java.util.List;

public class WAdwaitaMultiSelect<T> extends WMultiSelect<T> implements AdwaitaWidget {
    public WAdwaitaMultiSelect(String title, List<T> items) {
        super(title, items);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (expanded || animation.isRunning())
            roundedRect().pos(x, y + header.height)
                         .size(width, height - header.height)
                         .radius(radius(), Corners.BOTTOM)
                         .color(ColorUtils.withAlpha(theme().baseColor(), theme().backgroundOpacity()))
                         .render();
    }

    @Override
    protected WHeader createHeader() {
        return new WAdwaitaHeader(title);
    }

    @Override
    protected WItem createItem(T item) {
        return new WAdwaitaItem(item);
    }

    protected class WAdwaitaHeader extends WHeader {

        public WAdwaitaHeader(String title) {
            super(title);
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            AdwaitaGuiTheme theme = theme();
            Color bgColor = ColorUtils.withAlpha(
                    mouseOver ? theme.surface1Color() : theme.surface0Color(),
                    theme.backgroundOpacity()
            );

            // Background
            roundedRect().bounds(this)
                         .radius(radius(), expanded || animation.isRunning() ? Corners.TOP : Corners.ALL)
                         .color(bgColor)
                         .render();
        }
    }

    protected class WAdwaitaItem extends WItem {

        public WAdwaitaItem(T item) {
            super(item);
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            if (!mouseOver || checkbox.mouseOver) return;

            roundedRect().bounds(this)
                         .radius(smallRadius())
                         .color(theme().surface0Color())
                         .render();
        }
    }
}
