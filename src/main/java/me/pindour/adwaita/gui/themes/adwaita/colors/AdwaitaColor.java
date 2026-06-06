package me.pindour.adwaita.gui.themes.adwaita.colors;

/**
 * Semantic color slots used throughout the theme.
 *
 * <p>The names map onto libadwaita's design tokens as follows:
 * <ul>
 *     <li>{@link #Base} – window / main panel background</li>
 *     <li>{@link #Mantle} – header bar / tab bar surface</li>
 *     <li>{@link #Crust} – deepest surface (shadows, sidebar)</li>
 *     <li>{@link #Surface0}/{@link #Surface1}/{@link #Surface2} – card backgrounds (normal/hover/active)</li>
 *     <li>{@link #Overlay0}/{@link #Overlay1}/{@link #Overlay2} – borders &amp; dividers (normal/hover/active)</li>
 *     <li>{@link #Text}/{@link #Subtext1}/{@link #Subtext0} – primary &amp; secondary text</li>
 *     <li>{@link #Blue} – accent, {@link #Red} – destructive, {@link #Green} – success, {@link #Yellow} – warning</li>
 * </ul>
 */
public enum AdwaitaColor {
    // Status / accent
    Red,
    Blue,
    Yellow,
    Green,

    // Text
    Text,
    Subtext1,
    Subtext0,

    // Borders / dividers
    Overlay2,
    Overlay1,
    Overlay0,

    // Card surfaces
    Surface2,
    Surface1,
    Surface0,

    // Structural backgrounds
    Base,
    Mantle,
    Crust
}
