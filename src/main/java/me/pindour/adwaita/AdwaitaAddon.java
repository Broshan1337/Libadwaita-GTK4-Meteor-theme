package me.pindour.adwaita;

import me.pindour.adwaita.api.render.RoundedRect;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaGuiTheme;
import com.mojang.logging.LogUtils;
import me.pindour.adwaita.renderer.AdwaitaRenderer;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.gui.GuiThemes;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

public class AdwaitaAddon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();

    public static final String MOD_ID = "adwaita-addon";

    @Override
    public void onInitialize() {
        LOG.info("Initializing Adwaita Addon");

        GuiThemes.add(new AdwaitaGuiTheme());

        RoundedRect.get().registerRenderer(AdwaitaRenderer.get());
    }

    public static Identifier identifier(String path) {
        return Identifier.of(MOD_ID, path);
    }

    @Override
    public String getPackage() {
        return "me.pindour.adwaita";
    }
}
