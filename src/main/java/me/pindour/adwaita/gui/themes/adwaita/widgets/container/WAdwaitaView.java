package me.pindour.adwaita.gui.themes.adwaita.widgets.container;

import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.widgets.containers.WView;
import meteordevelopment.meteorclient.utils.Utils;

//? if <=1.21.10
//import me.pindour.adwaita.gui.widgets.IWidgetBackport;

public class WAdwaitaView extends WView implements AdwaitaWidget {

    @Override
    public void init() {
        maxHeight = Utils.getWindowHeight() - theme.scale(200);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (canScroll && hasScrollBar) {
            roundedRect().pos(handleX(), handleY())
                         .size(handleWidth(), handleHeight())
                         .radius(smallRadius())
                         .color(theme().scrollbarColor.get(
                                 //? if >=1.21.11 {
                                 focused,
                                 //? } else
                                 //((IWidgetBackport)this).adwaita$isSelfFocused(),
                                 handleMouseOver
                         ))
                         .render();
        }
    }
}
