package me.pindour.adwaita.gui.themes.adwaita.widgets.pressable;

import me.pindour.adwaita.api.animation.Animation;
import me.pindour.adwaita.api.animation.Direction;
import me.pindour.adwaita.api.animation.Easing;
import me.pindour.adwaita.api.text.RichText;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaGuiTheme;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import me.pindour.adwaita.gui.widgets.IConditionalWidget;
import me.pindour.adwaita.utils.ColorUtils;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.renderer.packer.GuiTexture;
import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
import meteordevelopment.meteorclient.utils.render.color.Color;

import java.util.function.BooleanSupplier;

public class WAdwaitaButton extends WButton implements IConditionalWidget, AdwaitaWidget {
    private BooleanSupplier visibilityCondition;
    private RichText richText;

    private Animation hoverAnimation;

    public WAdwaitaButton(RichText text, GuiTexture texture) {
        super(text.getPlainText(), texture);
        this.richText = text;
    }

    public WAdwaitaButton(GuiTexture texture) {
        super(null, texture);
    }

    @Override
    public void init() {
        hoverAnimation = new Animation(Easing.QUAD_OUT, 250);
    }

    // Buttons use the dedicated (smaller) button corner radius.
    @Override
    public float smallRadius() {
        return buttonRadius();
    }

    @Override
    protected void onCalculateSize() {
        double pad = pad();

        if (richText != null) {
            textWidth = theme().textWidth(richText);

            width = pad + textWidth + pad;
            height = pad + theme.textHeight() + pad;
        }
        else {
            double s = theme.textHeight();

            width = pad + s + pad;
            height = pad + s + pad;
        }
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (!shouldRender(mouseOver)) return;

        double hoverProgress = hoverAnimation.getProgress();

        if (mouseOver && hoverProgress == 0)
            hoverAnimation.start();

        if (!mouseOver && hoverProgress > 0)
            hoverAnimation.reset();

        AdwaitaGuiTheme theme = theme();

        Color bg = theme.backgroundColor.get(pressed, mouseOver);
        Color accent = ColorUtils.withAlpha(theme.accentColor(), 0.8);
        Color outline = ColorUtils.interpolateColor(bg, accent, hoverProgress);

        double pad = pad();

        background(bg, outline).render();

        if (richText != null) {
            renderer().text(
                    richText,
                    x + width / 2 - textWidth / 2,
                    y + pad, theme.textColor()
            );
        }
        else {
            double ts = theme.textHeight();
            renderer.quad(x + width / 2 - ts / 2, y + pad, ts, ts, texture, theme.textColor());
        }
    }

    public void set(RichText text) {
        if (richText == null || Math.round(theme().textWidth(richText)) != textWidth) invalidate();

        richText = text;
    }

    @Override
    public void set(String text) {
        set(RichText.of(text));
    }

    @Override
    public BooleanSupplier getVisibilityCondition() {
        return visibilityCondition;
    }

    @Override
    public void setVisibilityCondition(BooleanSupplier condition) {
        visibilityCondition = condition;
    }
}
