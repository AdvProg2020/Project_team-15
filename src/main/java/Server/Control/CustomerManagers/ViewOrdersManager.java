package Server.Control.CustomerManagers;

import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Category.Sort;
import Models.Shop.Log.BuyingLog;
import Models.Shop.Product.Product;
import Server.Control.Manager;

import java.util.*;

public class ViewOrdersManager extends Manager {

    private static Sort currentSort;
    private static List<BuyingLog> logs;
    private Customer customer = (Customer) account;
    BuyingLog logToShowProducts;

    public ViewOrdersManager(Account account) {
        super(account);
    }

    public String showAvailableSorts() {
        return "money\n" +
                "date";
    }

    public static ArrayList<Object> sort(String sort, boolean isAscending) {
        logs = ((Customer) account).getAllLogs();
        currentSort = new Sort(sort, isAscending);
        applySort();
        return new ArrayList<>(logs);
    }

    private static void applySort() {
        if (currentSort == null) {
            return;
        }
        String field = currentSort.getField();
        if (field.equals("money")) {
            sortByMoney();
        } else {
            sortByDate();
        }
        if (!currentSort.isAscending()) {
            Collections.reverse(logs);
        }
    }

    private static void sortByMoney() {
        BuyingLog[] logsForSort = logs.toArray(new BuyingLog[0]);
        for (int i = 0; i < logsForSort.length; i++) {
            for (int j = i + 1; j < logsForSort.length; j++) {
                if (logsForSort[i].getMoney() > logsForSort[j].getMoney()) {
                    BuyingLog temp = logsForSort[i];
                    logsForSort[i] = logsForSort[j];
                    logsForSort[j] = temp;
                }
            }
        }
        logs = Arrays.asList(logsForSort);
    }

    private static void sortByDate() {
        BuyingLog[] logsForSort = logs.toArray(new BuyingLog[0]);
        for (int i = 0; i < logsForSort.length; i++) {
            for (int j = i + 1; j < logsForSort.length; j++) {
                if (logsForSort[i].getDate().isBefore(logsForSort[j].getDate())) {
                    BuyingLog temp = logsForSort[i];
                    logsForSort[i] = logsForSort[j];
                    logsForSort[j] = temp;
                }
            }
        }
        logs = Arrays.asList(logsForSort);
    }

    public boolean isEnteredSortFieldValid(String field) {
        return field.equals("money") || field.equals("date");
    }

    public String currentSort() {
        return currentSort.toString();
    }

    public ArrayList<Object> disableSort() {
        currentSort = null;
        logs = ((Customer) account).getAllLogs();
        return new ArrayList<>(logs);
    }

    public BuyingLog getLogById(String id) {
        return customer.getLogById(id);
    }

    public ArrayList<BuyingLog> getLogs() {
        return customer.getAllLogs();
    }

    public void setLogToShowProducts(String logId) {
        logToShowProducts = customer.getLogById(logId);
    }

    public HashMap<Product, Integer> getOrderProductsToShow() {
        HashMap<Product, Integer> orderProductsToShow = new HashMap<>();
        for (String productId : logToShowProducts.getProductIdToNumberMap().keySet()) {
            orderProductsToShow.put(Product.getProductById(productId), logToShowProducts.getProductIdToNumberMap().get(productId));
        }
        return orderProductsToShow;
    }

    public static ArrayList<String> getSortFields() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("money");
        fields.add("date");
        return fields;
    }
}
