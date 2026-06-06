package me.pindour.adwaita.gui.themes.adwaita.widgets.input;

import me.pindour.adwaita.api.animation.Animation;
import me.pindour.adwaita.api.animation.Direction;
import me.pindour.adwaita.api.text.RichText;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaGuiTheme;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import me.pindour.adwaita.gui.themes.adwaita.icons.AdwaitaBuiltinIcons;
import me.pindour.adwaita.utils.ColorUtils;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.utils.Cell;
import meteordevelopment.meteorclient.gui.widgets.input.WDropdown;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.render.color.Color;

import java.util.Locale;

public class WAdwaitaDropdown<T> extends WDropdown<T> implements AdwaitaWidget {
    private final RichText titleText;
    private RichText valueText;

    private Animation animation;

    public WAdwaitaDropdown(String title, T[] values, T value) {
        super(values, value);
        titleText = RichText.of(title);
        valueText = RichText.of(getNameFor(value));
    }

    @Override
    public void init() {
        double pad = theme.pad();

        root = createRootWidget();
        root.theme = theme;
        root.spacing = pad;
        maxValueWidth = 0;

        for (int i = 0; i < values.length; i++) {
            WValue widget = new WValue(values[i]);
            widget.theme = theme;

            double valueWidth = theme().textWidth(widget.valueName);
            maxValueWidth = Math.max(maxValueWidth, valueWidth);

            Cell<?> cell = root.add(widget).padHorizontal(pad).expandWidgetX();
            if (i == 0) cell.padTop(pad);
            if (i == values.length - 1) cell.padBottom(pad);
        }

        animation = new Animation(
                theme().guiAnimationEasing(),
                theme().guiAnimationDuration(),
                Direction.BACKWARDS
        );
    }

    @Override
    protected WDropdownRoot createRootWidget() {
        return new WRoot();
    }

    @Override
    protected WDropdownValue createValueWidget() {
        return null;
    }

    @Override
    protected void onCalculateSize() {
        double pad = pad();

        root.calculateSize();

        double titleWidth = pad + theme().textWidth(titleText) + pad;
        double valueWidth = pad + maxValueWidth + pad;
        double arrowWidth = pad + theme.textHeight() + pad;

        width = titleWidth + valueWidth + arrowWidth;
        height = pad + theme.textHeight() + pad;

        root.width = width;
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        AdwaitaGuiTheme theme = theme();
        double pad = pad();
        double s = theme.textHeight() * 0.75;

        background(pressed, mouseOver).render();

        // Title text
        renderer().text(
                titleText,
                x + pad,
                y + pad,
                theme.textColor()
        );

        // Dot separator
        double dotSize = theme.textHeight() / 3;
        double dotX = x + pad + theme.textWidth(titleText) + pad;
        double dotY = y + pad + theme.textHeight() / 2 - dotSize / 2;

        renderer.quad(
                dotX,
                dotY,
                dotSize,
                dotSize,
                GuiRenderer.CIRCLE,
                theme.accentColor()
        );

        // Value text
        renderer().text(
                valueText,
                dotX + dotSize + pad,
                y + pad,
                theme.accentColor()
        );

        // Open indicator
        renderer.rotatedQuad(
                x + width - pad - s,
                y + height / 2 - s / 2,
                s,
                s,
                180 * (1 - animation.getProgress()),
                AdwaitaBuiltinIcons.ARROW.texture(),
                theme.textColor()
        );
    }

    @Override
    public boolean render(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        animProgress = -8008135; // Small hack to cancel out WDropdown scissors, so we can use our animation

        boolean render = super.render(renderer, mouseX, mouseY, delta);
        double progress = animation.getProgress();

        if (!render && progress > 0) {
            renderer.absolutePost(() -> {
                renderer.scissorStart(root.x, root.y, root.width, root.height * progress);
                root.render(renderer, mouseX, mouseY, delta);
                renderer.scissorEnd();
            });
        }

        return render;
    }

    @Override
    protected void onPressed(int button) {
        super.onPressed(button);
        handlePressed();
    }

    @Override
    public void set(T value) {
        super.set(value);

        if (animation == null) return;

        animation.reset();
        handlePressed();
    }

    private void handlePressed() {
        valueText = RichText.of(getNameFor(value));
        animation.reverse();
    }

    private String getNameFor(T value) {
        String name = value.toString();

        // ENUM_NAME -> Enum Name
        if (name.contains("_")) return Utils.nameToTitle(name.toLowerCase(Locale.ROOT).replace("_", "-"));

        // EnumName -> Enum Name
        return Utils.nameToTitle(name.replaceAll("(?<=[a-z])([A-Z])", "-$1").toLowerCase(Locale.ROOT));
    }

    private static class WRoot extends WDropdownRoot implements AdwaitaWidget {
        private static final int DROPDOWN_Y_OFFSET = 6;

        @Override
        protected void onCalculateWidgetPositions() {
            this.y += DROPDOWN_Y_OFFSET;
            super.onCalculateWidgetPositions();
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            AdwaitaGuiTheme theme = theme();

            Color outlineColor = ColorUtils.withAlpha(
                    theme.accentColor(),
                    0.8 + (0.2 * theme.backgroundOpacity())
            );

            Color backgroundColor = ColorUtils.withAlpha(
                    theme.backgroundColor.get(false, false),
                    0.8 + (0.2 * theme.backgroundOpacity())
            );

            background(backgroundColor, outlineColor).render();
        }
    }

    private class WValue extends WDropdownValue implements AdwaitaWidget {
        private final RichText valueName;

        public WValue(T value) {
            this.value = value;
            this.valueName = RichText.of(getNameFor(value));
        }

        @Override
        protected void onCalculateSize() {
            double pad = pad();

            width = pad + theme().textWidth(valueName) + pad;
            height = pad + theme.textHeight() + pad;
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            AdwaitaGuiTheme theme = theme();

            if (mouseOver)
                roundedRect().bounds(this)
                             .radius(smallRadius())
                             .color(ColorUtils.withAlpha(theme.accentColor(), 0.4))
                             .render();

            boolean isSelected = get() == this.value;
            RichText text = valueName.boldIf(isSelected);
            Color textColor = isSelected ? theme.accentColor() : theme.textColor();

            renderer().text(
                    text,
                    x + width / 2 - theme.textWidth(text) / 2,
                    y + pad(),
                    textColor
            );
        }

        @Override
        protected void onPressed(int button) {
            super.onPressed(button);
            handlePressed();
        }
    }
}
