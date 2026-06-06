package me.pindour.adwaita.gui.themes.adwaita.widgets.pressable;

import me.pindour.adwaita.api.animation.Animation;
import me.pindour.adwaita.api.animation.Direction;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaGuiTheme;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.widgets.pressable.WCheckbox;

public class WAdwaitaCheckbox extends WCheckbox implements AdwaitaWidget {
    private Animation animation;

    public WAdwaitaCheckbox(boolean checked) {
        super(checked);
    }

    @Override
    public void init() {
        super.init();

        animation = new Animation(
                theme().guiAnimationEasing(),
                500,
                checked ? Direction.FORWARDS : Direction.BACKWARDS
        );
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        // Background is only visible when unchecked or animating
        if (!checked || animation.isRunning()) background(pressed, mouseOver).render();

        // Skip checkmark if unchecked and animation finished
        if (!checked && animation.isFinished()) return;

        renderCheckmark();
    }

    @Override
    protected void onCalculateSize() {
        super.onCalculateSize();
        height *= 0.9;
        width *= 0.9;
    }

    @Override
    protected void onPressed(int button) {
        super.onPressed(button);
        animation.start(checked ? Direction.FORWARDS : Direction.BACKWARDS);
    }

    private void renderCheckmark() {
        AdwaitaGuiTheme theme = theme();
        double progress = animation.getProgress();
        double size = width * progress;
        double minSize = theme.scale(6);

        if (size <= minSize) return;

        double centerOffset = (width - size) / 2;

        roundedRect().pos(x + centerOffset, y + centerOffset)
                     .size(size, size)
                     .radius(smallRadius())
                     .color(theme.accentColor())
                     .outline(theme.accentColor().copy().a(mouseOver ? 140 : 80), 3f)
                     .render();
    }

    public void setChecked(boolean checked) {
        if (this.checked == checked) return;
        this.checked = checked;
        animation.start(checked ? Direction.FORWARDS : Direction.BACKWARDS);
    }
}