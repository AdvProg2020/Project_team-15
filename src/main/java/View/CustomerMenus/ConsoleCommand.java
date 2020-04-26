package View.CustomerMenus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ConsoleCommand {
    EXIT("(?i)exit"),

    EDIT("(?i)edit (\\S+)"),

    SHOW_PRODUCTS("(?i)show products"),

    VIEW_PRODUCT_ID("(?i)view (\\S+)"),

    INCREASE_PRODUCT("(?i)increase (\\S+)"),

    DECREASE_PRODUCT("(?i)decrease (\\S+)"),

    SHOW_TOTAL_PRICE("(?i)show total price"),

    PURCHASE("(?i)purchase"),

    VIEW_CART("(?i)view cart"),

    VIEW_ORDERS("(?i)view orders"),

    VIEW_DISCOUNT_CODES("(?i)view discount codes"),

    VIEW_BALANCE("(?i)view balance"),
    //show order [orderId]
    SHOW_ORDER("(?i)show order (\\S+)"),
//rate [productId] [1-5],
    RATE("(?i)rate (\\S+) (\\d)"),

    VIEW_PERSONAL_INFO("(?i)view personal info");

    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);
    }

    ConsoleCommand(String commandPatternString) {
        this.commandPattern = Pattern.compile(commandPatternString);
    }
}