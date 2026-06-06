package me.pindour.adwaita.gui.widgets;

import meteordevelopment.meteorclient.gui.widgets.WWidget;
import meteordevelopment.meteorclient.gui.widgets.containers.WView;

@SuppressWarnings("unused")
public interface IWidgetBackport {
    boolean adwaita$isFocused();
    boolean adwaita$isSelfFocused();

    void adwaita$setFocused(boolean focused);

    WView adwaita$getView();
    boolean adwaita$isWidgetInView(WWidget widget);
}
