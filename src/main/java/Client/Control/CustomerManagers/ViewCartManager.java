package Client.Control.CustomerManagers;

import Client.Control.Manager;
import Client.ViewController.Controller;
import Client.ViewController.customer.cart.ViewCartController;
import Models.Account.Account;
import Models.Account.Customer;
import Models.Shop.Category.Sort;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;

import java.util.*;

public class ViewCartManager extends Manager {

    private static Sort currentSort;
    private static List<Product> products;
    private Customer customer = (Customer) account;

    public ViewCartManager(Account account, Addresses address, Manager manager) {
        super(account, address, manager);
        products = Product.getAllProducts();
//        Controller controller = loadFxml(Addresses.VIEW_CART);
//        update(controller);
    }

    public ViewCartManager(Account account) {
        super(account);
        products = Product.getAllProducts();
    }

    public void update(Controller c) {
        ViewCartController controller = (ViewCartController) c;
        controller.init();
    }

    public void productQuantity(String id, boolean isIncrease) {
        Product product;
        if ((product = customer.getCart().getProductInCartById(id)) != null) {
            if (isIncrease)
                customer.getCart().addProduct(product);
            else {
                customer.getCart().removeProduct(product);
            }
        }
//        } else throw new ProductDoNotExistInCartException("Product does not exist in cart");
    }

    public double getTotalPrice(Discount discount) {
        return customer.getCart().getTotalPrice(discount);
    }

    public HashMap<Product, Integer> getProductsInCart() {
        return customer.getCart().getProductsMap();
    }

    public void clearCart() {
        customer.getCart().empty();
    }

    public void purchase() {
        new PurchaseManager(account, Addresses.VIEW_CART, this);
    }

    public static class ProductDoNotExistAtAllException extends Exception {
        public ProductDoNotExistAtAllException(String message) {
            super(message);
        }
    }

    public static class ProductDoNotExistInCartException extends Exception {
        public ProductDoNotExistInCartException(String message) {
            super(message);
        }
    }

    public String showAvailableSorts() {
        return "price\n" +
                "name\n" +
                "rating";
    }

    public static ArrayList<Object> sort(String sort, boolean isAscending) {
        products = mainCategory.getAllProducts();
        currentSort = new Sort(sort, isAscending);
        applySort();
        return new ArrayList<>(products);
    }

    private static void applySort() {
        if (currentSort == null) {
            return;
        }
        String field = currentSort.getField();
        if (field.equals("price")) {
            sortByPrice();
        } else if (field.equals("name")) {
            sortByName();
        } else {
            sortByRating();
        }
        if (!currentSort.isAscending()) {
            Collections.reverse(products);
        }
    }

    private static void sortByPrice() {
        Product[] productsForSort = products.toArray(new Product[0]);
        for (int i = 0; i < productsForSort.length; i++) {
            for (int j = i + 1; j < productsForSort.length; j++) {
                if (productsForSort[i].getPrice() > productsForSort[j].getPrice()) {
                    Product temp = productsForSort[i];
                    productsForSort[i] = productsForSort[j];
                    productsForSort[j] = temp;
                }
            }
        }
        products = Arrays.asList(productsForSort);
    }

    private static void sortByName() {
        Product[] productsForSort = products.toArray(new Product[0]);
        for (int i = 0; i < productsForSort.length; i++) {
            for (int j = i + 1; j < productsForSort.length; j++) {
                if (productsForSort[i].getName().compareTo(productsForSort[j].getName()) > 0) {
                    Product temp = productsForSort[i];
                    productsForSort[i] = productsForSort[j];
                    productsForSort[j] = temp;
                }
            }
        }
        products = Arrays.asList(productsForSort);
    }

    private static void sortByRating() {
        Product[] productsForSort = products.toArray(new Product[0]);
        for (int i = 0; i < productsForSort.length; i++) {
            for (int j = i + 1; j < productsForSort.length; j++) {
                if (productsForSort[i].getRate() > productsForSort[j].getRate()) {
                    Product temp = productsForSort[i];
                    productsForSort[i] = productsForSort[j];
                    productsForSort[j] = temp;
                }
            }
        }
        products = Arrays.asList(productsForSort);
    }

    public boolean isEnteredSortFieldValid(String field) {
        return field.equals("price") || field.equals("name") || field.equals("rating");
    }

    public String currentSort() {
        return currentSort.toString();
    }

    public ArrayList<Object> disableSort() {
        currentSort = null;
        products = mainCategory.getAllProducts();
        return new ArrayList<>(products);
    }

    public void showProduct(String id) {
        new ProductPageManager(account, Product.getProductById(id), Addresses.VIEW_CART, this);
    }

    public boolean isCartEmpty() {
        return customer.getCart().getProducts().size() == 0;
    }

    public static ArrayList<String> getSortFields() {
        ArrayList<String> fields = new ArrayList<>();
        fields.add("price");
        fields.add("name");
        fields.add("rating");
        return fields;
    }
}
