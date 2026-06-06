package me.pindour.adwaita.gui.themes.adwaita.widgets;

import me.pindour.adwaita.gui.themes.adwaita.AdwaitaWidget;
import meteordevelopment.meteorclient.gui.WidgetScreen;
import meteordevelopment.meteorclient.gui.widgets.WAccount;
import meteordevelopment.meteorclient.systems.accounts.Account;
import meteordevelopment.meteorclient.utils.render.color.Color;

public class WAdwaitaAccount extends WAccount implements AdwaitaWidget {
    public WAdwaitaAccount(WidgetScreen screen, Account<?> account) {
        super(screen, account);
    }

    @Override
    protected Color loggedInColor() {
        return theme().greenColor();
    }

    @Override
    protected Color accountTypeColor() {
        return theme().textSecondaryColor();
    }
}
