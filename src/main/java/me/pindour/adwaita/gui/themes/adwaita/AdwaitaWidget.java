package me.pindour.adwaita.gui.themes.adwaita;

import me.pindour.adwaita.api.render.RoundedRect;
import me.pindour.adwaita.renderer.AdwaitaRenderer;
import me.pindour.adwaita.api.render.Corners;
import me.pindour.adwaita.utils.ColorUtils;
import meteordevelopment.meteorclient.gui.utils.BaseWidget;
import meteordevelopment.meteorclient.gui.widgets.WWidget;
import meteordevelopment.meteorclient.utils.render.color.Color;

public interface AdwaitaWidget extends BaseWidget {

    // Getters

    default AdwaitaGuiTheme theme() {
        return (AdwaitaGuiTheme) getTheme();
    }

    default AdwaitaRenderer renderer() {
        return AdwaitaRenderer.get();
    }

    default RoundedRect roundedRect() {
        return RoundedRect.get();
    }

    // Styling

    default float radius() {
        return (float) (theme().scale(theme().windowRadius.get()));
    }

    default float smallRadius() {
        return (float) (theme().scale(theme().cardRadius.get()));
    }

    default float buttonRadius() {
        return (float) (theme().scale(theme().buttonRadius.get()));
    }

    default Corners corners() {
        return Corners.ALL;
    }

    default float outlineWidth() {
        // libadwaita uses subtle 1px borders; honour the "show borders" toggle.
        return theme().showBorders.get() ? (float) theme().scale(1) : 0f;
    }

    // Rendering

    default RoundedRect background(Color backgroundColor, Color outlineColor) {
        return roundedRect().bounds((WWidget) this)
                            .radius(smallRadius(), corners())
                            .color(backgroundColor)
                            .outline(outlineColor, outlineWidth());
    }

    default RoundedRect background(boolean pressed, boolean mouseOver) {
        return background(getBackgroundColor(pressed, mouseOver), getOutlineColor(pressed, mouseOver));
    }

    // Colors

    default Color getBackgroundColor(boolean pressed, boolean mouseOver) {
        AdwaitaGuiTheme theme = theme();

        return ColorUtils.withAlpha(
                theme.backgroundColor.get(pressed, mouseOver),
                theme.backgroundOpacity()
        );
    }

    default Color getOutlineColor(boolean pressed, boolean mouseOver) {
        AdwaitaGuiTheme theme = theme();

        return ColorUtils.withAlpha(
                theme.outlineColor.get(pressed, mouseOver),
                theme.backgroundOpacity() * 0.4
        );
    }
}