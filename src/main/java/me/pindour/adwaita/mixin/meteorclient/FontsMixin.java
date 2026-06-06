package me.pindour.adwaita.mixin.meteorclient;

import me.pindour.adwaita.AdwaitaAddon;
import me.pindour.adwaita.renderer.text.RichTextRenderer;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaGuiTheme;
import meteordevelopment.meteorclient.gui.GuiThemes;
import meteordevelopment.meteorclient.renderer.Fonts;
import meteordevelopment.meteorclient.renderer.text.FontFace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Fonts.class, remap = false)
public abstract class FontsMixin {

    @Inject(method = "load", at = @At("HEAD"))
    private static void adwaita$load(FontFace fontFace, CallbackInfo ci) {
        if (!(GuiThemes.get() instanceof AdwaitaGuiTheme theme)) return;

        if (theme.textRenderer() instanceof RichTextRenderer currentRenderer)
             if (currentRenderer.fontFace.equals(fontFace)) return;

        try {
            theme.setTextRenderer(new RichTextRenderer(fontFace));
        } catch (Exception e) {
            AdwaitaAddon.LOG.error("Failed to load font: {}", fontFace, e);
        }
    }
}
