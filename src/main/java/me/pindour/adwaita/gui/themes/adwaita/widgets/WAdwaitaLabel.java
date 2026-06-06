package me.pindour.adwaita.gui.themes.adwaita.widgets;

import me.pindour.adwaita.api.text.RichText;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.widgets.WLabel;

public class WAdwaitaLabel extends WLabel implements AdwaitaWidget {
    protected RichText richText;

    public WAdwaitaLabel(RichText text) {
        super(text.getPlainText(), false);
        richText = text;
    }

    @Override
    protected void onCalculateSize() {
        width = theme().textWidth(richText);
        height = theme().textHeight(richText);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (text.isEmpty()) return;

        renderer().text(
                richText,
                x,
                y,
                color != null ? color : theme().textColor()
        );
    }

    public void set(RichText text) {
        if (Math.round(theme().textWidth(text)) != width) invalidate();

        this.text = text.getPlainText();
        richText = text;
    }

    @Override
    public void set(String text) {
        set(RichText.of(text));
    }
}
