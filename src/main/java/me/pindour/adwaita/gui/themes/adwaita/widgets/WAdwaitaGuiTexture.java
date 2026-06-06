package me.pindour.adwaita.gui.themes.adwaita.widgets;

import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import me.pindour.adwaita.gui.widgets.WGuiTexture;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.renderer.packer.GuiTexture;

public class WAdwaitaGuiTexture extends WGuiTexture implements AdwaitaWidget {

    public WAdwaitaGuiTexture(GuiTexture texture, double size) {
        super(texture, size);
    }

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        renderer.quad(x, y, size, size, texture, color);
    }
}
