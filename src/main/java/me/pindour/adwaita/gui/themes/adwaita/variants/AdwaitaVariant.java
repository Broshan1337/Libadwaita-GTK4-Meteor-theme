package me.pindour.adwaita.gui.themes.adwaita.variants;

import me.pindour.adwaita.gui.themes.adwaita.colors.AdwaitaColor;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;

import java.util.EnumMap;
import java.util.Map;

/**
 * The two canonical libadwaita variants: {@link #LIGHT} (Adwaita Light) and {@link #DARK} (Adwaita Dark).
 *
 * <p>Each variant carries the full set of {@link AdwaitaColor} slots. Color values are taken from
 * GNOME's libadwaita named colors, with intermediate hover/active surface and border shades
 * interpolated to give the GUI a clean, neutral, modern feel.
 */
public enum AdwaitaVariant {
    LIGHT(builder()
            // Status / accent
            .set(AdwaitaColor.Blue,     53, 132, 228)   // accent blue       #3584E4
            .set(AdwaitaColor.Red,      224, 27, 36)    // destructive red   #E01B24
            .set(AdwaitaColor.Green,    46, 194, 126)   // success green     #2EC27E
            .set(AdwaitaColor.Yellow,   229, 165, 10)   // warning yellow    #E5A50A
            // Text
            .set(AdwaitaColor.Text,     28, 28, 28)     // text primary      #1C1C1C
            .set(AdwaitaColor.Subtext1, 60, 60, 60)
            .set(AdwaitaColor.Subtext0, 92, 92, 92)     // text secondary    #5C5C5C
            // Borders / dividers (#D3D3D3 and slightly stronger states)
            .set(AdwaitaColor.Overlay0, 211, 211, 211)
            .set(AdwaitaColor.Overlay1, 196, 196, 196)
            .set(AdwaitaColor.Overlay2, 181, 181, 181)
            // Card surfaces (surface / hover / active)
            .set(AdwaitaColor.Surface0, 255, 255, 255)  // surface           #FFFFFF
            .set(AdwaitaColor.Surface1, 242, 242, 242)
            .set(AdwaitaColor.Surface2, 235, 235, 235)
            // Structural backgrounds
            .set(AdwaitaColor.Base,     255, 255, 255)  // elevated surfaces / header / inputs
            .set(AdwaitaColor.Mantle,   250, 250, 250)  // window background #FAFAFA
            .set(AdwaitaColor.Crust,    235, 235, 235)  // deepest (shadows / sidebar)
    ),

    DARK(builder()
            // Status / accent
            .set(AdwaitaColor.Blue,     53, 132, 228)   // accent blue       #3584E4
            .set(AdwaitaColor.Red,      255, 123, 123)  // destructive red   #FF7B7B
            .set(AdwaitaColor.Green,    143, 240, 164)  // success green     #8FF0A4
            .set(AdwaitaColor.Yellow,   248, 228, 92)   // warning yellow    #F8E45C
            // Text
            .set(AdwaitaColor.Text,     222, 221, 218)  // text primary      #DEDDDA
            .set(AdwaitaColor.Subtext1, 191, 190, 187)
            .set(AdwaitaColor.Subtext0, 160, 160, 160)  // text secondary    #A0A0A0
            // Borders / dividers (#474747 and slightly stronger states)
            .set(AdwaitaColor.Overlay0, 71, 71, 71)
            .set(AdwaitaColor.Overlay1, 85, 85, 85)
            .set(AdwaitaColor.Overlay2, 98, 98, 98)
            // Card surfaces (surface / hover / active)
            .set(AdwaitaColor.Surface0, 45, 45, 45)     // surface           #2D2D2D
            .set(AdwaitaColor.Surface1, 56, 56, 56)
            .set(AdwaitaColor.Surface2, 69, 69, 69)
            // Structural backgrounds
            .set(AdwaitaColor.Base,     45, 45, 45)      // elevated surfaces / header / inputs
            .set(AdwaitaColor.Mantle,   30, 30, 30)      // window background #1E1E1E
            .set(AdwaitaColor.Crust,    24, 24, 24)      // deepest (shadows / sidebar)
    );

    private final Map<AdwaitaColor, SettingColor> colors;

    AdwaitaVariant(Builder builder) {
        this.colors = builder.colors;
    }

    public SettingColor getColor(AdwaitaColor color) {
        return colors.getOrDefault(color, new SettingColor(255, 255, 255));
    }

    private static Builder builder() {
        return new Builder();
    }

    private static final class Builder {
        private final Map<AdwaitaColor, SettingColor> colors = new EnumMap<>(AdwaitaColor.class);

        Builder set(AdwaitaColor type, int r, int g, int b) {
            colors.put(type, new SettingColor(r, g, b));
            return this;
        }
    }
}
