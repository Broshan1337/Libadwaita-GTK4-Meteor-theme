package me.pindour.adwaita.gui.themes.adwaita.icons;

import me.pindour.adwaita.AdwaitaAddon;
import me.pindour.adwaita.api.icons.AdwaitaIcons;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.renderer.packer.GuiTexture;
import meteordevelopment.meteorclient.systems.modules.Categories;

import java.util.Locale;

/*
 * Sources: https://www.svgrepo.com/collection/lightning-design-utility-icons/
 *          https://www.svgrepo.com/collection/clarity-project-icons/
 *          https://www.svgrepo.com/collection/iconsax-bold-oval-icons/ (goated rounded icons)
 */
public enum AdwaitaBuiltinIcons {
    ARROW,
    BOOKMARK_NO,
    BOOKMARK_YES,
    COPY,
    CUBE,
    EDIT,
    EXPLOIT,
    EYE,
    IMPORT,
    MINUS,
    MOVEMENT,
    PLUS,
    QUESTION_MARK,
    RESET,
    SWORDS,
    USER,
    SEARCH,
    SETTING;

    private final String path;
    private GuiTexture texture;

    AdwaitaBuiltinIcons() {
        this.path = "textures/icons/gui/" + name().toLowerCase(Locale.ROOT) + ".png";
    }

    public static void init() {
        for (AdwaitaBuiltinIcons icon : values())
            icon.initIcon();

        // Init icons for Meteor
        AdwaitaIcons.registerCategoryIcon(Categories.Combat.name, SWORDS.texture());
        AdwaitaIcons.registerCategoryIcon(Categories.Player.name, USER.texture());
        AdwaitaIcons.registerCategoryIcon(Categories.Movement.name, MOVEMENT.texture());
        AdwaitaIcons.registerCategoryIcon(Categories.Render.name, EYE.texture());
        AdwaitaIcons.registerCategoryIcon(Categories.World.name, CUBE.texture());
    }

    public void initIcon() {
        this.texture = GuiRenderer.addTexture(AdwaitaAddon.identifier(path));
    }

    public GuiTexture texture() {
        if (texture == null) throw new IllegalStateException("Icon " + name() + " not initialized.");
        return texture;
    }
}
