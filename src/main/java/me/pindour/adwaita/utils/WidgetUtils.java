package me.pindour.adwaita.utils;

import me.pindour.adwaita.gui.themes.adwaita.icons.AdwaitaBuiltinIcons;
import me.pindour.adwaita.gui.themes.adwaita.widgets.pressable.WAdwaitaButton;
import meteordevelopment.meteorclient.gui.utils.Cell;
import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
import meteordevelopment.meteorclient.settings.Setting;

import java.util.function.BooleanSupplier;

public class WidgetUtils {

    public static Cell<WAdwaitaButton> reset(WContainer c, Setting<?> setting, Runnable action) {
        return reset(c, setting, action, null);
    }

    public static Cell<WAdwaitaButton> reset(WContainer c, Setting<?> setting, Runnable action, BooleanSupplier visibilityCondition) {
        WAdwaitaButton button = (WAdwaitaButton) c.getTheme().button(AdwaitaBuiltinIcons.RESET.texture());

        button.setVisibilityCondition(visibilityCondition);
        button.tooltip = "Reset";
        button.action = () -> {
            setting.reset();
            if (action != null) action.run();
        };

        return c.add(button);
    }
}
