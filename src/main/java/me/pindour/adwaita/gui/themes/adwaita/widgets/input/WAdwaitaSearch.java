package me.pindour.adwaita.gui.themes.adwaita.widgets.input;

import me.pindour.adwaita.api.render.Corners;
import me.pindour.adwaita.api.text.RichText;
import me.pindour.adwaita.api.text.TextScale;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaGuiTheme;
import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import me.pindour.adwaita.gui.themes.adwaita.icons.AdwaitaBuiltinIcons;
import me.pindour.adwaita.gui.widgets.input.WSearch;
import me.pindour.adwaita.utils.ColorUtils;
import me.pindour.adwaita.utils.search.SearchResult;
import me.pindour.adwaita.utils.search.results.ModuleSearchResult;
import me.pindour.adwaita.utils.search.results.SettingSearchResult;
import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
import meteordevelopment.meteorclient.gui.renderer.packer.GuiTexture;
import meteordevelopment.meteorclient.gui.widgets.WLabel;
import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
import meteordevelopment.meteorclient.utils.render.color.Color;

public class WAdwaitaSearch extends WSearch implements AdwaitaWidget {

    @Override
    protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
        AdwaitaGuiTheme theme = theme();
        if (!theme.windowShadow()) return;

        Color shadowColor = theme.shadowColor();

        // Shadow rectangle
        int shadowOffset = 2;
        roundedRect().pos(x - shadowOffset, y - shadowOffset)
                     .size(width + shadowOffset * 2, height + shadowOffset * 2)
                     .radius(radius() + shadowOffset)
                     .color(shadowColor)
                     .render();
    }

    @Override
    protected WSearchHeader createHeader(WSearch search) {
        return new WAdwaitaHeader(search);
    }

    @Override
    protected WResultsContainer createResultsContainer() {
        return new WAdwaitaResultsContainer();
    }

    @Override
    protected WSearchResult createSearchResult(SearchResult result) {
        return new WAdwaitaResult(result);
    }

    private static class WAdwaitaHeader extends WSearchHeader implements AdwaitaWidget {

        public WAdwaitaHeader(WSearch search) {
            super(search);
        }

        @Override
        public void init() {
            AdwaitaGuiTheme theme = theme();

            // Row container
            WHorizontalList row = add(theme.horizontalList()).expandX().pad(theme.scale(12)).widget();

            // Search texture
            row.add(theme.texture(AdwaitaBuiltinIcons.SEARCH.texture(), theme.textHeight())).center();

            // Search textbox
            WAdwaitaTextBox textBox = (WAdwaitaTextBox) theme.textBox("", "Search for modules...");
            textBox.shouldRenderBackground(false);

            row.add(textBox).expandX();
            search.initTextBox(textBox);

            // Hint label
            row.add(theme.label("ESC to close"));
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            roundedRect().bounds(this)
                         .color(theme().crustColor())
                         .radius(radius(), Corners.TOP)
                         .render();
        }
    }

    private static class WAdwaitaResultsContainer extends WResultsContainer implements AdwaitaWidget {
        @Override
        public void init() {
            super.init();

            addDirect(theme.label("Left click to toggle module; Right click to open the module's settings.").color(theme().textSecondaryColor())).pad(theme.pad()).centerX();
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            AdwaitaGuiTheme theme = theme();

            roundedRect().bounds(this)
                         .color(ColorUtils.withAlpha(theme.baseColor(), theme.windowOpacity()))
                         .radius(radius(), Corners.BOTTOM)
                         .render();
        }
    }

    private static class WAdwaitaResult extends WSearchResult implements AdwaitaWidget {
        private AdwaitaGuiTheme theme;

        public WAdwaitaResult(SearchResult result) {
            super(result);
        }

        @Override
        public void init() {
            theme = theme();

            // Row container
            WHorizontalList row = add(theme.horizontalList()).expandX().pad(6).widget();

            // Result type icon
            row.add(new WResultType(result)).pad(theme.pad()).center();

            // Result info container
            WVerticalList infoColumn = row.add(theme.verticalList()).expandX().widget();

            // Result title
            infoColumn.add(theme.label(RichText.of(result.title())));

            // Result description
            RichText desc = RichText.of(result.description()).scale(TextScale.SMALL.get());
            WLabel descLabel = infoColumn.add(theme.label(desc)).widget();
            descLabel.color = theme.textSecondaryColor();
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            if (!mouseOver) return;

            Color outlineColor = ColorUtils.withAlpha(
                    theme.accentColor(),
                    theme.backgroundOpacity() * 0.5
            );

            background(getBackgroundColor(pressed, false), outlineColor).render();
        }

        public static class WResultType extends WContainer implements AdwaitaWidget {
            private final SearchResult result;
            private Color color;

            public WResultType(SearchResult result) {
                this.result = result;
            }

            @Override
            public void init() {
                color = getColor();

                add(theme().texture(getIcon(), theme.textHeight()).color(color)).pad(theme.pad()).center();
            }

            @Override
            protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
                roundedRect().bounds(this)
                             .color(ColorUtils.withAlpha(color, 60))
                             .radius(smallRadius())
                             .render();
            }

            private Color getColor() {
                return switch (result) {
                    case ModuleSearchResult r -> r.hasAlias() ? theme().yellowColor() : theme().greenColor();
                    case SettingSearchResult ignored -> theme().blueColor();
                    default -> theme().textSecondaryColor();
                };
            }

            private GuiTexture getIcon() {
                return switch (result) {
                    case ModuleSearchResult ignored -> AdwaitaBuiltinIcons.CUBE.texture();
                    case SettingSearchResult ignored -> AdwaitaBuiltinIcons.SETTING.texture();
                    default -> AdwaitaBuiltinIcons.QUESTION_MARK.texture();
                };
            }
        }
    }
}