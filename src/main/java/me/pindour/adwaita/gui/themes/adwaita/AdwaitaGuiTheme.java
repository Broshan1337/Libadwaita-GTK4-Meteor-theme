package me.pindour.adwaita.gui.themes.adwaita;

import me.pindour.adwaita.AdwaitaAddon;
import me.pindour.adwaita.api.animation.Easing;
import me.pindour.adwaita.api.text.RichText;
import me.pindour.adwaita.api.text.RichTextSegment;
import me.pindour.adwaita.gui.screens.AdwaitaModuleScreen;
import me.pindour.adwaita.gui.screens.AdwaitaModulesScreen;
import me.pindour.adwaita.gui.themes.adwaita.colors.AdwaitaColor;
import me.pindour.adwaita.gui.themes.adwaita.variants.AdwaitaVariant;
import me.pindour.adwaita.gui.themes.adwaita.widgets.*;
import me.pindour.adwaita.gui.themes.adwaita.widgets.container.WAdwaitaSection;
import me.pindour.adwaita.gui.themes.adwaita.widgets.container.WAdwaitaView;
import me.pindour.adwaita.gui.themes.adwaita.widgets.container.WAdwaitaWindow;
import me.pindour.adwaita.gui.themes.adwaita.widgets.input.*;
import me.pindour.adwaita.gui.themes.adwaita.widgets.pressable.*;
import me.pindour.adwaita.gui.themes.adwaita.widgets.settings.WAdwaitaDoubleEdit;
import me.pindour.adwaita.gui.themes.adwaita.widgets.settings.WAdwaitaIntEdit;
import me.pindour.adwaita.gui.themes.adwaita.widgets.settings.WAdwaitaKeybind;
import me.pindour.adwaita.gui.widgets.WGuiTexture;
import me.pindour.adwaita.gui.widgets.input.WMultiSelect;
import me.pindour.adwaita.gui.widgets.input.WSearch;
import me.pindour.adwaita.gui.widgets.pressable.WColorPicker;
import me.pindour.adwaita.gui.widgets.pressable.WOpenIndicator;
import me.pindour.adwaita.renderer.AdwaitaRenderer;
import me.pindour.adwaita.renderer.text.RichTextRenderer;
import meteordevelopment.meteorclient.gui.GuiTheme;
import meteordevelopment.meteorclient.gui.WidgetScreen;
import meteordevelopment.meteorclient.gui.renderer.packer.GuiTexture;
import meteordevelopment.meteorclient.gui.tabs.TabScreen;
import meteordevelopment.meteorclient.gui.utils.AlignmentX;
import meteordevelopment.meteorclient.gui.utils.CharFilter;
import meteordevelopment.meteorclient.gui.widgets.*;
import meteordevelopment.meteorclient.gui.widgets.containers.WSection;
import meteordevelopment.meteorclient.gui.widgets.containers.WView;
import meteordevelopment.meteorclient.gui.widgets.containers.WWindow;
import meteordevelopment.meteorclient.gui.widgets.input.WDropdown;
import meteordevelopment.meteorclient.gui.widgets.input.WSlider;
import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
import meteordevelopment.meteorclient.gui.widgets.pressable.*;
import meteordevelopment.meteorclient.renderer.text.TextRenderer;
import meteordevelopment.meteorclient.renderer.text.VanillaTextRenderer;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.accounts.Account;
import meteordevelopment.meteorclient.systems.config.Config;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.misc.Keybind;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import net.minecraft.client.gui.screen.Screen;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static meteordevelopment.meteorclient.MeteorClient.mc;
import net.minecraft.client.util.MacWindowUtil;

public class AdwaitaGuiTheme extends GuiTheme {
    private final Map<AdwaitaColor, Color> colorCache;

    private RichTextRenderer textRenderer;

    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgCorners = settings.createGroup("Corners");
    private final SettingGroup sgAnimations = settings.createGroup("Animations");
    private final SettingGroup sgColors = settings.createGroup("Colors");
    private final SettingGroup sgSnapping = settings.createGroup("Snapping");
    private final SettingGroup sgScreens = settings.createGroup("Screens");
    private final SettingGroup sgStarscript = settings.createGroup("Starscript");

    // General

    public final Setting<Double> scale = sgGeneral.add(new DoubleSetting.Builder()
            .name("scale")
            .description("Scale of the GUI.")
            .defaultValue(1)
            .min(0.75)
            .sliderRange(0.75, 4)
            .onSliderRelease()
            .onChanged(aDouble -> {
                if (mc.currentScreen instanceof WidgetScreen) ((WidgetScreen) mc.currentScreen).invalidate();
            })
            .build()
    );

    public final Setting<AlignmentX> moduleAlignment = sgGeneral.add(new EnumSetting.Builder<AlignmentX>()
            .name("module-alignment")
            .description("How module titles are aligned.")
            .defaultValue(AlignmentX.Center)
            .build()
    );

    public final Setting<Boolean> categoryIcons = sgGeneral.add(new BoolSetting.Builder()
            .name("category-icons")
            .description("Displays icons next to module categories.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Boolean> hideHUD = sgGeneral.add(new BoolSetting.Builder()
            .name("hide-HUD")
            .description("Hide HUD when in GUI.")
            .defaultValue(false)
            .onChanged(v -> {
                if (mc.currentScreen instanceof WidgetScreen) mc.options.hudHidden = v;
            })
            .build()
    );

    public final Setting<Boolean> showBorders = sgGeneral.add(new BoolSetting.Builder()
            .name("show-borders")
            .description("Render subtle 1px borders on surfaces, like libadwaita.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Integer> shadowIntensity = sgGeneral.add(new IntSetting.Builder()
            .name("shadow-intensity")
            .description("Intensity of the soft drop shadow under windows (0 disables it).")
            .defaultValue(60)
            .sliderRange(0, 100)
            .build()
    );

    // Corners

    public final Setting<Integer> windowRadius = sgCorners.add(new IntSetting.Builder()
            .name("window-radius")
            .description("Corner radius for windows and top-level panels (libadwaita uses 12px).")
            .defaultValue(12)
            .sliderRange(0, 20)
            .build()
    );

    public final Setting<Integer> cardRadius = sgCorners.add(new IntSetting.Builder()
            .name("card-radius")
            .description("Corner radius for inner cards, module boxes and inputs (libadwaita uses 8px).")
            .defaultValue(8)
            .sliderRange(0, 12)
            .build()
    );

    public final Setting<Integer> buttonRadius = sgCorners.add(new IntSetting.Builder()
            .name("button-radius")
            .description("Corner radius for buttons (libadwaita uses 6px).")
            .defaultValue(6)
            .sliderRange(0, 12)
            .build()
    );

    // Animations

    public final Setting<Easing> guiAnimation = sgAnimations.add(new EnumSetting.Builder<Easing>()
            .name("gui-animation-easing")
            .description("The easing function used for UI animations. libadwaita uses an ease-out curve.")
            .defaultValue(Easing.EASE_OUT)
            .build()
    );

    public final Setting<Integer> guiAnimationDuration = sgAnimations.add(new IntSetting.Builder()
            .name("gui-animation-duration")
            .description("Duration of the animation in milliseconds.")
            .defaultValue(200)
            .sliderRange(1, 1000)
            .build()
    );

    // Colors

    public final Setting<AdwaitaVariant> variant = sgColors.add(new EnumSetting.Builder<AdwaitaVariant>()
            .name("variant")
            .description("The libadwaita variant (palette) to use.")
            .defaultValue(AdwaitaVariant.DARK)
            .onChanged(this::updateCache)
            .build()
    );

    private final Setting<SettingColor> accentColor = sgColors.add(new ColorSetting.Builder()
            .name("accent-color")
            .description("The main accent color used throughout the UI.")
            .defaultValue(new SettingColor(53, 132, 228))
            .build()
    );

    public final Setting<Double> windowOpacity = sgColors.add(new DoubleSetting.Builder()
            .name("window-opacity")
            .description("Controls the opacity of the windows.")
            .defaultValue(1)
            .sliderRange(0, 1)
            .decimalPlaces(2)
            .build()
    );

    public final Setting<Double> backgroundOpacity = sgColors.add(new DoubleSetting.Builder()
            .name("background-opacity")
            .description("Controls the opacity of the backgrounds of UI elements.")
            .defaultValue(1)
            .sliderRange(0, 1)
            .decimalPlaces(2)
            .build()
    );

    // Snapping

    public final Setting<Boolean> snapModuleCategories = sgSnapping.add(new BoolSetting.Builder()
            .name("snap-module-categories")
            .description("Snaps category windows to the grid.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Integer> snappingGridSize = sgSnapping.add(new IntSetting.Builder()
            .name("grid-size")
            .description("The size of the snapping grid.")
            .defaultValue(10)
            .sliderRange(5, 50)
            .build()
    );

    // Screens

    public final Setting<Boolean> adwaitaSearchScreen = sgScreens.add(new BoolSetting.Builder()
            .name("search-screen")
            .description("Replaces Meteor's search window with Adwaita's search screen.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Boolean> adwaitaEntityTypeListScreen = sgScreens.add(new BoolSetting.Builder()
            .name("entity-type-list-screen")
            .description("Replaces Meteor's entity selection screen with the Adwaita version.")
            .defaultValue(true)
            .build()
    );

    // Three state colors

    public final ThreeStateColor backgroundColor = new ThreeStateColor(
            this::surface0Color,
            this::surface1Color,
            this::surface2Color
    );

    public final ThreeStateColor outlineColor = new ThreeStateColor(
            this::overlay0Color,
            this::overlay1Color,
            this::overlay2Color
    );

    public final ThreeStateColor scrollbarColor = new ThreeStateColor(
            this::surface0Color,
            this::surface1Color,
            this::surface2Color
    );

    // Starscript

    private final Setting<SettingColor> starscriptText = color(sgStarscript, "starscript-text", "Color of text in Starscript code.", new SettingColor(169, 183, 198));
    private final Setting<SettingColor> starscriptBraces = color(sgStarscript, "starscript-braces", "Color of braces in Starscript code.", new SettingColor(150, 150, 150));
    private final Setting<SettingColor> starscriptParenthesis = color(sgStarscript, "starscript-parenthesis", "Color of parenthesis in Starscript code.", new SettingColor(169, 183, 198));
    private final Setting<SettingColor> starscriptDots = color(sgStarscript, "starscript-dots", "Color of dots in starscript code.", new SettingColor(169, 183, 198));
    private final Setting<SettingColor> starscriptCommas = color(sgStarscript, "starscript-commas", "Color of commas in starscript code.", new SettingColor(169, 183, 198));
    private final Setting<SettingColor> starscriptOperators = color(sgStarscript, "starscript-operators", "Color of operators in Starscript code.", new SettingColor(169, 183, 198));
    private final Setting<SettingColor> starscriptStrings = color(sgStarscript, "starscript-strings", "Color of strings in Starscript code.", new SettingColor(106, 135, 89));
    private final Setting<SettingColor> starscriptNumbers = color(sgStarscript, "starscript-numbers", "Color of numbers in Starscript code.", new SettingColor(104, 141, 187));
    private final Setting<SettingColor> starscriptKeywords = color(sgStarscript, "starscript-keywords", "Color of keywords in Starscript code.", new SettingColor(204, 120, 50));
    private final Setting<SettingColor> starscriptAccessedObjects = color(sgStarscript, "starscript-accessed-objects", "Color of accessed objects (before a dot) in Starscript code.", new SettingColor(152, 118, 170));

    public AdwaitaGuiTheme() {
        super("Adwaita");

        settingsFactory = new AdwaitaSettingsWidgetFactory(this);
        colorCache = new EnumMap<>(AdwaitaColor.class);

        updateCache(variant.get());
    }

    private Setting<SettingColor> color(SettingGroup group, String name, String description, SettingColor color) {
        return group.add(new ColorSetting.Builder()
                .name(name + "-color")
                .description(description)
                .defaultValue(color)
                .build());
    }

    // Widgets

    @Override
    public WWindow window(WWidget icon, String title) {
        return w(new WAdwaitaWindow(icon, title));
    }

    public WLabel label(RichText text, double maxWidth) {
        if (maxWidth == 0) return w(new WAdwaitaLabel(text));
        return w(new WAdwaitaMultiLabel(text, maxWidth));
    }

    public WLabel label(RichText text) {
        return label(text, 0);
    }

    @Override
    public WLabel label(String text, boolean title, double maxWidth) {
        if (maxWidth == 0) return w(new WAdwaitaLabel(RichText.of(text).boldIf(title)));
        return w(new WAdwaitaMultiLabel(RichText.of(text).boldIf(title), maxWidth));
    }

    @Override
    public WHorizontalSeparator horizontalSeparator(String text) {
        return w(new WAdwaitaHorizontalSeparator(text));
    }

    @Override
    public WVerticalSeparator verticalSeparator() {
        return w(new WAdwaitaVerticalSeparator());
    }

    public WAdwaitaButton button(RichText text, GuiTexture texture) {
        return w(new WAdwaitaButton(text, texture));
    }

    public WAdwaitaButton button(RichText text) {
        return button(text, null);
    }

    @Override
    public WButton button(String text, GuiTexture texture) {
        return button(RichText.of(text), texture);
    }

    @Override
    public WButton button(GuiTexture texture) {
        return w(new WAdwaitaButton(texture));
    }

    //? if >=1.21.10 {
    @Override
    protected WConfirmedButton confirmedButton(String text, String confirmText, GuiTexture texture) {
        return w(new WAdwaitaConfirmedButton(text, confirmText, texture));
    }
    //?}

    @Override
    public WMinus minus() {
        return w(new WAdwaitaMinus());
    }

    //? if >=1.21.10 {
    @Override
    public WConfirmedMinus confirmedMinus() {
        return w(new WAdwaitaConfirmedMinus());
    }
    //?}

    @Override
    public WPlus plus() {
        return w(new WAdwaitaPlus());
    }

    @Override
    public WCheckbox checkbox(boolean checked) {
        return w(new WAdwaitaCheckbox(checked));
    }

    @Override
    public WSlider slider(double value, double min, double max) {
        return w(new WAdwaitaSlider(value, min, max));
    }

    public WTextBox textBox(String text, String placeholder, String title, double padding, CharFilter filter, Class<? extends WTextBox.Renderer> renderer) {
        return w(new WAdwaitaTextBox(text, placeholder, title, padding, filter, renderer));
    }

    public WTextBox textBox(String text, String placeholder, String title, CharFilter filter, Class<? extends WTextBox.Renderer> renderer) {
        return textBox(text, placeholder, title, pad(), filter, renderer);
    }

    public WTextBox textBox(String text, CharFilter filter, double padding) {
        return textBox(text, null, "", padding, filter, null);
    }

    @Override
    public WTextBox textBox(String text, String placeholder, CharFilter filter, Class<? extends WTextBox.Renderer> renderer) {
        return textBox(text, placeholder, "", filter, renderer);
    }

    public <T> WDropdown<T> dropdown(String title, T[] values, T value) {
        return w(new WAdwaitaDropdown<>(title, values, value));
    }

    @SuppressWarnings("unchecked")
    public <T extends Enum<?>> WDropdown<T> dropdown(String title, T value) {
        Class<?> klass = value.getDeclaringClass();
        T[] values = (T[]) klass.getEnumConstants();
        return dropdown(title, values, value);
    }

    @Override
    public <T> WDropdown<T> dropdown(T[] values, T value) {
        return dropdown(null, values, value);
    }

    @Override
    public WTriangle triangle() {
        return w(new WAdwaitaTriangle());
    }

    @Override
    public WTooltip tooltip(String text) {
        return w(new WAdwaitaTooltip(text));
    }

    @Override
    public WView view() {
        return w(new WAdwaitaView());
    }

    @Override
    public WSection section(String title, boolean expanded, WWidget headerWidget) {
        return w(new WAdwaitaSection(title, expanded, headerWidget));
    }

    @Override
    public WAccount account(WidgetScreen screen, Account<?> account) {
        return w(new WAdwaitaAccount(screen, account));
    }

    @Override
    public WWidget module(Module module) {
        return w(module(module, module.title));
    }

    //? if >=1.21.11
    @Override
    public WWidget module(Module module, String title) {
        return w(new WAdwaitaModule(module, title));
    }

    @Override
    public WQuad quad(Color color) {
        return w(new WAdwaitaQuad(color));
    }

    @Override
    public WTopBar topBar() {
        return w(new WAdwaitaTopBar());
    }

    @Override
    public WFavorite favorite(boolean checked) {
        return w(new WAdwaitaFavorite(checked));
    }

    public WAdwaitaKeybind adwaitaKeybind(Keybind keybind) {
        return adwaitaKeybind(keybind, Keybind.none());
    }

    public WAdwaitaKeybind adwaitaKeybind(Keybind keybind, Keybind defaultValue) {
        return adwaitaKeybind(null, keybind, defaultValue);
    }

    public WAdwaitaKeybind adwaitaKeybind(String title, Keybind keybind, Keybind defaultValue) {
        return w(new WAdwaitaKeybind(title, keybind, defaultValue));
    }

    public WOpenIndicator openIndicator(boolean open) {
        return w(new WAdwaitaOpenIndicator(open));
    }

    public WGuiTexture texture(GuiTexture texture, double size) {
        return w(new WAdwaitaGuiTexture(texture, size));
    }

    public WColorPicker colorPicker(Color color, GuiTexture overlayTexture) {
        return w(new WAdwaitaColorPicker(color, overlayTexture));
    }

    public <T> WMultiSelect<T> multiSelect(String title, List<T> items) {
        return w(new WAdwaitaMultiSelect<>(title, items));
    }

    public WSearch search() {
        return w(new WAdwaitaSearch());
    }

    // Settings widgets

    public WAdwaitaIntEdit adwaitaIntEdit(IntSetting setting) {
        return w(new WAdwaitaIntEdit(setting));
    }

    public WAdwaitaDoubleEdit adwaitaDoubleEdit(String title, String description, double value, double min, double max, int decimalPlaces, double sliderMin, double sliderMax, boolean noSlider) {
        return w(new WAdwaitaDoubleEdit(title, description, null, value, min, max, decimalPlaces, sliderMin, sliderMax, noSlider));
    }

    public WAdwaitaDoubleEdit adwaitaDoubleEdit(DoubleSetting setting) {
        return w(new WAdwaitaDoubleEdit(setting));
    }

    // Animations

    public Easing guiAnimationEasing() {
        return guiAnimation.get();
    }

    public int guiAnimationDuration() {
        return guiAnimationDuration.get();
    }

    // Colors - Accent

    public Color accentColor() {
        return accentColor.get();
    }

    // Colors - Main

    public Color greenColor() {
        return colorCache.get(AdwaitaColor.Green);
    }

    public Color yellowColor() {
        return colorCache.get(AdwaitaColor.Yellow);
    }

    public Color redColor() {
        return colorCache.get(AdwaitaColor.Red);
    }

    public Color blueColor() {
        return colorCache.get(AdwaitaColor.Blue);
    }

    // Colors - Overlay

    public Color overlay2Color() {
        return colorCache.get(AdwaitaColor.Overlay2);
    }

    public Color overlay1Color() {
        return colorCache.get(AdwaitaColor.Overlay1);
    }

    public Color overlay0Color() {
        return colorCache.get(AdwaitaColor.Overlay0);
    }

    // Colors - Surface

    public Color surface2Color() {
        return colorCache.get(AdwaitaColor.Surface2);
    }

    public Color surface1Color() {
        return colorCache.get(AdwaitaColor.Surface1);
    }

    public Color surface0Color() {
        return colorCache.get(AdwaitaColor.Surface0);
    }

    // Colors - Base

    public Color baseColor() {
        return colorCache.get(AdwaitaColor.Base);
    }

    public Color mantleColor() {
        return colorCache.get(AdwaitaColor.Mantle);
    }

    public Color crustColor() {
        return colorCache.get(AdwaitaColor.Crust);
    }

    // Colors - Text

    public Color textColor() {
        return colorCache.get(AdwaitaColor.Text);
    }

    public Color textSecondaryColor() {
        return colorCache.get(AdwaitaColor.Subtext0);
    }

    public Color textHighlightColor() {
        return colorCache.get(AdwaitaColor.Blue);
    }

    // Opacity

    public double windowOpacity() {
        return windowOpacity.get();
    }

    public double backgroundOpacity() {
        return backgroundOpacity.get();
    }

    // Shadow

    public boolean windowShadow() {
        return shadowIntensity.get() > 0;
    }

    /**
     * Soft drop shadow color: a black tint whose alpha scales with the shadow-intensity setting.
     * libadwaita uses a stronger shadow on dark backgrounds (~0.35) and a lighter one on light (~0.15).
     */
    public Color shadowColor() {
        double maxAlpha = variant.get() == AdwaitaVariant.LIGHT ? 0.3 : 0.6;
        double alpha = (shadowIntensity.get() / 100.0) * maxAlpha;
        return new Color(0, 0, 0, (int) (alpha * 255));
    }

    // Starscript

    @Override
    public Color starscriptTextColor() {
        return starscriptText.get();
    }

    @Override
    public Color starscriptBraceColor() {
        return starscriptBraces.get();
    }

    @Override
    public Color starscriptParenthesisColor() {
        return starscriptParenthesis.get();
    }

    @Override
    public Color starscriptDotColor() {
        return starscriptDots.get();
    }

    @Override
    public Color starscriptCommaColor() {
        return starscriptCommas.get();
    }

    @Override
    public Color starscriptOperatorColor() {
        return starscriptOperators.get();
    }

    @Override
    public Color starscriptStringColor() {
        return starscriptStrings.get();
    }

    @Override
    public Color starscriptNumberColor() {
        return starscriptNumbers.get();
    }

    @Override
    public Color starscriptKeywordColor() {
        return starscriptKeywords.get();
    }

    @Override
    public Color starscriptAccessedObjectColor() {
        return starscriptAccessedObjects.get();
    }

    // Colors - Other

    private void updateCache(AdwaitaVariant newVariant) {
        colorCache.clear();

        for (AdwaitaColor color : AdwaitaColor.values()) {
            SettingColor settingColor = newVariant.getColor(color);
            colorCache.put(color, settingColor);
        }
    }

    // Screens

    @Override
    public TabScreen modulesScreen() {
        return new AdwaitaModulesScreen(this);
    }

    @Override
    public boolean isModulesScreen(Screen screen) {
        return screen instanceof AdwaitaModulesScreen;
    }

    @Override
    public WidgetScreen moduleScreen(Module module) {
        return new AdwaitaModuleScreen(this, module);
    }

    // Text renderer

    @Override
    public TextRenderer textRenderer() {
        return Config.get().customFont.get() ? richTextRenderer() : VanillaTextRenderer.INSTANCE;
    }

    public RichTextRenderer richTextRenderer() {
        if (textRenderer == null) {
            try {
                setTextRenderer(new RichTextRenderer(Config.get().font.get()));
            } catch (Exception e) {
                AdwaitaAddon.LOG.error("Failed to load TextRenderer: ", e);
            }
        }

        return textRenderer;
    }

    public void setTextRenderer(RichTextRenderer renderer) {
        if (textRenderer != null) textRenderer.destroy();
        this.textRenderer = renderer;
    }

    // Text

    public double textWidth(RichTextSegment segment) {
        return scale(Config.get().customFont.get()
                ? richTextRenderer().getWidth(segment, segment.getText().length())
                : textRenderer().getWidth(segment.getText()));
    }

    public double textWidth(RichText text) {
        return scale(Config.get().customFont.get()
                ? richTextRenderer().getWidth(text)
                : textRenderer().getWidth(text.getPlainText()));
    }

    @Override
    public double textWidth(String text, int length, boolean title) {
        return scale(Config.get().customFont.get()
                ? richTextRenderer().getWidth(RichText.of(text).boldIf(title), length)
                : textRenderer().getWidth(text, length, title));
    }

    @Override
    public double textWidth(String text) {
        return textWidth(RichText.of(text));
    }

    public double textHeight(RichText text) {
        return scale(Config.get().customFont.get()
                ? richTextRenderer().getHeight(text)
                : textRenderer().getHeight());
    }

    @Override
    public double textHeight(boolean title) {
        return scale(textRenderer().getHeight(title));
    }

    @Override
    public double textHeight() {
        return textHeight(false);
    }

    // Other

    @Override
    public void beforeRender() {
        super.beforeRender();
        AdwaitaRenderer.get().setTheme(this);
    }

    @Override
    public double scale(double value) {
        double scaled = value * scale.get();

        if (MacWindowUtil.IS_MAC) {
            scaled /= (double) mc.getWindow().getWidth() / mc.getWindow().getFramebufferWidth();
        }

        return scaled;
    }

    @Override
    public boolean categoryIcons() {
        return categoryIcons.get();
    }

    @Override
    public boolean hideHUD() {
        return hideHUD.get();
    }

    public class ThreeStateColor {
        private final Supplier<Color> normal, hovered, pressed;

        public ThreeStateColor(Supplier<Color> normal, Supplier<Color> hovered, Supplier<Color> pressed) {
            this.normal = normal;
            this.hovered = hovered;
            this.pressed = pressed;
        }

        public Color get() {
            return normal.get();
        }

        public Color get(float alpha) {
            return withAlpha(normal.get(), alpha);
        }

        public Color get(boolean pressed, boolean hovered, boolean bypassDisableHoverColor) {
            if (pressed) return this.pressed.get();
            return (hovered && (bypassDisableHoverColor || !disableHoverColor)) ? this.hovered.get() : this.normal.get();
        }

        public Color get(boolean pressed, boolean hovered, boolean bypassDisableHoverColor, float alpha) {
            Color color = get(pressed, hovered, bypassDisableHoverColor);
            return withAlpha(color, alpha);
        }

        public Color get(boolean pressed, boolean hovered) {
            return get(pressed, hovered, false);
        }

        public Color get(boolean pressed, boolean hovered, float alpha) {
            return get(pressed, hovered, false, alpha);
        }

        public Color get(boolean hovered) {
            return get(false, hovered, false);
        }

        public Color get(boolean hovered, float alpha) {
            return get(false, hovered, false, alpha);
        }

        private Color withAlpha(Color color, float alpha) {
            Color result = color.copy().a((int) (255 * alpha));
            result.validate();
            return result;
        }
    }
}
