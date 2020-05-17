package Control;

import Models.Account.Account;
import Models.Shop.Category.Category;
import Models.Shop.Off.Discount;
import View.Menu;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public abstract class Manager {
    protected static Account account;
    protected final static Category mainCategory = new Category("mainCategory", null, null, new ArrayList<>());
    protected Menu menu;
    protected boolean isPrincipalExists = false;

    public Manager(Account account) {
        Manager.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public Category getMainCategory() {
        return mainCategory;
    }

    public Menu getMenu() {
        return menu;
    }

    public boolean userExistsWithUsername(String username) {
        return Account.hasAccountWithUsername(username);
    }

    protected boolean checkAccountType(String type) {
        return type.matches("^(customer|seller|principal)$");
    }

    public boolean checkEmail(String email) {
        return email.matches("^\\S+@\\S+\\.\\S+$");
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\d{11}$");
    }

    public boolean checkNumber(String number) {
        return number.matches("^\\d+(\\.\\d+)?$");
    }

    public boolean checkDate(String date) {
        //return date.matches("^\\d{2}-\\d{2}-\\d{4}$");
        return date.matches("^(\\d{4}-\\d{2}-\\d{2})$");
    }

    protected Date parseDate(String stringDate) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = format.parse(stringDate);
        } catch (Exception ignored) {

        }
        return date;
    }

    public boolean checkPercent(String date) {
        return date.matches("^(100|(\\d{1,2}))$");
    }

    public String showCategories() {
        StringBuilder result = new StringBuilder();
        buildCategoryList(mainCategory, result, 1);
        return result.toString();
    }

    private void buildCategoryList(Category currentCategory, StringBuilder categoryField, int categoryLevel) {
        if (currentCategory != mainCategory) {
            categoryField.append("\n");
        }
        categoryField.append("-".repeat(Math.max(0, categoryLevel)));
        categoryField.append(currentCategory.getName());
        for (Category category : currentCategory.getSubCategories()) {
            buildCategoryList(category, categoryField, categoryLevel + 1);
        }
    }

    public boolean isPrincipalExists() {
        return isPrincipalExists;
    }

    protected String generateNewId() {
        Random rand = new Random();
        return String.valueOf(rand.nextInt(100000));
    }

    public void viewPersonalInfo() {
        System.out.println(account.toString());
        new ViewPersonalInfoManager(account);
    }

    public boolean isDiscountCodeValid(String id) {
        return Discount.getDiscountById(id) != null;
    }
}
