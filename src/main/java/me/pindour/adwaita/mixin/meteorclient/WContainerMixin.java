package me.pindour.adwaita.mixin.meteorclient;

import me.pindour.adwaita.gui.widgets.IWidgetBackport;
import meteordevelopment.meteorclient.gui.widgets.WWidget;
import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
import org.spongepowered.asm.mixin.Mixin;
//? if <=1.21.10 {
/*import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.utils.Cell;
import meteordevelopment.meteorclient.gui.widgets.containers.WView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static meteordevelopment.meteorclient.utils.Utils.getWindowHeight;
*///? }

@Mixin(value = WContainer.class, remap = false)
public abstract class WContainerMixin extends WWidget implements IWidgetBackport {
    //? if <=1.21.10 {

    /*@Final @Shadow public List<Cell<?>> cells;

    @Shadow protected abstract void renderWidget(WWidget widget, GuiRenderer renderer, double mouseX, double mouseY, double delta);

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void adwaita$render(GuiRenderer renderer, double mouseX, double mouseY, double delta, CallbackInfoReturnable<Boolean> cir) {
        if (super.render(renderer, mouseX, mouseY, delta)) {
            cir.setReturnValue(true);
            return;
        }

        WView view = adwaita$getView();
        double windowHeight = getWindowHeight();

        for (Cell<?> cell : cells) {
            WWidget widget = cell.widget();

            if (widget.y > windowHeight) break;
            if (widget.y + widget.height <= 0) continue;

            if (shouldRenderWidget(widget, view)) renderWidget(widget, renderer, mouseX, mouseY, delta);
        }

        cir.setReturnValue(false);
    }

    @Override
    public boolean adwaita$isFocused() {
        if (adwaita$isSelfFocused()) return true;

        for (Cell<?> cell : cells) {
            if (((IWidgetBackport) cell.widget()).adwaita$isFocused())
                return true;
        }

        return false;
    }

    @Unique
    private boolean shouldRenderWidget(WWidget widget, WView view) {
        if (view == null) return true;
        if (!((IWidgetBackport)view).adwaita$isWidgetInView(widget)) return false;

        if (widget.mouseOver && !view.mouseOver) {
            widget.mouseOver = false;
        }

        return true;
    }

    *///? }
}
