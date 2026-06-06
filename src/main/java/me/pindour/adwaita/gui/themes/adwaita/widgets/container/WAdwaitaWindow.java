package me.pindour.adwaita.gui.themes.adwaita.widgets.container;

import me.pindour.adwaita.api.animation.Animation;
import me.pindour.adwaita.api.animation.Direction;
import me.pindour.adwaita.api.render.Corners;
import me.pindour.adwaita.gui.screens.AdwaitaModulesScreen;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaGuiTheme;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import me.pindour.adwaita.gui.widgets.pressable.WOpenIndicator;
import me.pindour.adwaita.utils.ColorUtils;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.utils.Cell;
import meteordevelopment.meteorclient.gui.utils.WindowConfig;
import meteordevelopment.meteorclient.gui.widgets.WWidget;
import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
import meteordevelopment.meteorclient.gui.widgets.containers.WWindow;
import meteordevelopment.meteorclient.utils.render.color.Color;
//? if >=1.21.9
import net.minecraft.client.gui.Click;

public class WAdwaitaWindow extends WWindow implements AdwaitaWidget {
    private static final int SHADOW_OFFSET = 2;

    private AdwaitaModulesScreen modulesScreen;
    private boolean shouldSnap = false;
    private int gridSize;

    private double mouseOffsetX;
    private double mouseOffsetY;

    private Animation animation;
    private Animation cornerAnimation;

    public WAdwaitaWindow(WWidget icon, String title) {
        super(icon, title);
    }

    @Override
    public void init() {
        super.init();

        animation = new Animation(
                theme().guiAnimationEasing(),
                theme().guiAnimationDuration(),
                expanded ? Direction.FORWARDS : Direction.BACKWARDS
        );
        cornerAnimation = new Animation(
                theme().guiAnimationEasing(),
                theme().guiAnimationDuration(),
                expanded ? Direction.FORWARDS : Direction.BACKWARDS
        );

        if (header instanceof WAdwaitaHeader adwaitaHeader)
            adwaitaHeader.setIndicator(expanded);
    }

    public void initSnapping(AdwaitaModulesScreen modulesScreen, int gridSize) {
        this.modulesScreen = modulesScreen;
        this.gridSize = gridSize;
        shouldSnap = true;
    }

    /**
     * Identical implementation to {@link WContainer#add}
     */
    public <T extends WWidget> Cell<T> addDirect(T widget) {
        widget.parent = this;
        widget.theme = theme;

        Cell<T> cell = new Cell<>(widget).centerY();
        cells.add(cell);

        widget.init();
        invalidate();

        return cell;
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        AdwaitaGuiTheme theme = theme();
        Color backgroundColor = ColorUtils.withAlpha(theme.mantleColor(), theme.windowOpacity());

        int shadowOffset = getShadowOffset();

        // Shadow rectangle
        if (theme.windowShadow()) {
            Color shadowColor = theme.shadowColor();

            roundedRect().pos(x - shadowOffset, y - shadowOffset)
                         .size(width + shadowOffset * 2,
                                 header.height + (height - header.height) * animation.getProgress() + shadowOffset * 2)
                         .radius(radius() + shadowOffset / 2f)
                         .color(shadowColor)
                         .render();

        }

        // Inner rectangle
        if (expanded || animation.isRunning())
            roundedRect().pos(x, y + header.height)
                         .size(width, height - header.height)
                         .radius(radius() - shadowOffset, Corners.BOTTOM)
                         .color(backgroundColor)
                         .render();
    }

    @Override
    public boolean render(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (!visible) return true;

        double progress = animation.getProgress();
        boolean useScissor = animation.isRunning();

        if (useScissor) {
            int shadowOffset = getShadowOffset();

            renderer.scissorStart(
                    x - shadowOffset,
                    y - shadowOffset,
                    width + shadowOffset * 2,
                    (height - header.height) * progress + header.height + shadowOffset * 2
            );
        }

        boolean toReturn = super.render(renderer, mouseX, mouseY, delta);

        if (useScissor) renderer.scissorEnd();

        return toReturn;
    }

    @Override
    protected void renderWidget(WWidget widget, GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        if (expanded || animation.isRunning() || widget instanceof WHeader)
            widget.render(renderer, mouseX, mouseY, delta);
    }

    @Override
    protected boolean propagateEvents(WWidget widget) {
        return widget instanceof WHeader || expanded;
    }

    @Override
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);

        if (header instanceof WAdwaitaHeader adwaitaHeader)
            adwaitaHeader.setIndicator(expanded);

        if (animation != null)
            animation.reverse();

        if (expanded && cornerAnimation != null)
            cornerAnimation.finishedAt(Direction.FORWARDS);
    }

    @Override
    protected WHeader header(WWidget icon) {
        return new WAdwaitaHeader(icon);
    }

    private class WAdwaitaHeader extends WHeader {
        private WHorizontalList list;
        private WOpenIndicator indicator;

        public WAdwaitaHeader(WWidget icon) {
            super(icon);
        }

        @Override
        public void init() {
            if (icon != null) {
                createList();
                add(icon).centerY().pad(4);
            }

            if (beforeHeaderInit != null) {
                createList();
                beforeHeaderInit.accept(this);
            }

            boolean hasIcon = beforeHeaderInit != null || icon != null;
            add(theme.label(title, true)).expandCellX().centerY().pad(hasIcon ? 0 : 12);

            indicator = add(theme().openIndicator(expanded)).pad(4).right().centerY().widget();
            indicator.action = () -> setExpanded(!expanded);
        }

        private void createList() {
            list = add(theme.horizontalList()).expandX().widget();
            list.spacing = 0;
        }

        @Override
        public <T extends WWidget> Cell<T> add(T widget) {
            if (list != null) return list.add(widget);
            return super.add(widget);
        }

        @Override
        protected void onCalculateSize() {
            super.onCalculateSize();
            minWidth = 200;
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            AdwaitaGuiTheme theme = theme();
            double cornerProgress = cornerAnimation.getProgress();

            // Start corner animation if we're collapsing,
            // and we're at the end of the main animation
            if (!expanded
                    && animation.getProgress() <= 0.0
                    && !cornerAnimation.isRunning()
                    && cornerProgress > 0) {
                cornerAnimation.start(Direction.BACKWARDS);
            }

            roundedRect().bounds(this)
                         .radii(radius(),
                                radius(),
                                (float) (radius() * (1 - cornerProgress)),
                                (float) (radius() * (1 - cornerProgress)))
                         .color(theme.crustColor())
                         .render();

            // Shadow under the header
            if (expanded || animation.isRunning()) {
                Color transparentColor = ColorUtils.withAlpha(theme.baseColor(), 0);

                Color semiTransparentColor = ColorUtils.withAlpha(
                        theme.baseColor(),
                        0.5 * theme.windowOpacity()
                );

                renderer.quad(
                        x,
                        y + height,
                        width,
                        12,
                        semiTransparentColor,
                        semiTransparentColor,
                        transparentColor,
                        transparentColor
                );
            }
        }

        @Override
        public boolean render(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            boolean render = super.render(renderer, mouseX, mouseY, delta);
            animProgress = 1; // Small hack to cancel out WWindow scissors, so we can use our animation
            return render;
        }

        @Override
        public boolean onMouseClicked(Click click, boolean used) {
            boolean clicked = super.onMouseClicked(
                    //? if >=1.21.9
                    click,
                    //? if <=1.21.8
                    //mouseX, mouseY, button,
                    used
            );

            if (clicked && shouldSnap) {
                //? if >=1.21.9 {
                mouseOffsetX = click.x() - x;
                mouseOffsetY = click.y() - y;
                //?} else {
                /*mouseOffsetX = mouseX - x;
                mouseOffsetY = mouseY - y;
                *///?}
            }

            return clicked;
        }

        @Override
        public boolean mouseReleased(Click click) {
            if (shouldSnap) modulesScreen.showGrid(false);
            return super.mouseReleased(
                    //? if >=1.21.9
                    click
                    //? if <=1.21.8
                    //mouseX, mouseY, button
            );
        }

        @Override
        public void onMouseMoved(double mouseX, double mouseY, double lastMouseX, double lastMouseY) {
            if (!dragging) return;

            double deltaX = shouldSnap ? snapToGrid(mouseX - mouseOffsetX) - x : mouseX - lastMouseX;
            double deltaY = shouldSnap ? snapToGrid(mouseY - mouseOffsetY) - y : mouseY - lastMouseY;

            WAdwaitaWindow.this.move(deltaX, deltaY);

            moved = true;
            movedX = x;
            movedY = y;

            if (id != null) {
                WindowConfig config = theme.getWindowConfig(id);

                config.x = x;
                config.y = y;
            }

            if (shouldSnap && !modulesScreen.showGrid()) modulesScreen.showGrid(true);
            dragged = true;
        }

        public void setIndicator(boolean open) {
            indicator.open = open;
        }
    }

    private double snapToGrid(double value) {
        return Math.round(value / gridSize) * gridSize;
    }

    private int getShadowOffset() {
        return theme().windowShadow() ? SHADOW_OFFSET : 0;
    }
}