package me.pindour.adwaita.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.pindour.adwaita.renderer.AdwaitaRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSystem.class)
public abstract class RenderSystemMixin {
    @Inject(method = "flipFrame", at = @At("TAIL"))
    private static void adwaita$flipFrame(CallbackInfo info) {
        AdwaitaRenderer.get().flipFrame();
    }
}