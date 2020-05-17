package Control.CustomerManagers;

import Models.Account.Account;
import Models.Shop.Off.Discount;
import Models.Shop.Product.Product;
import View.CustomerMenus.customer.ViewCartMenu;

import java.util.List;

public class ViewCartManager extends CustomerManager {
    public ViewCartManager(Account account) {
        super(account);
        this.menu = new ViewCartMenu(this);
    }

    public String showProducts() {
        List<String> productsInfos = customer.getCart().showProductsInShort();
        StringBuilder allProductsInfo = new StringBuilder();
        for (String productsInfo : productsInfos) {
            allProductsInfo.append(productsInfo);
        }
        return allProductsInfo.toString();
    }

    public void ProductQuantity(String id, boolean isIncrease) throws ProductDoNotExistAtAllException,ProductDoNotExistInCartException {
        Product product;
        if ((product = Product.getProductById(id)) != null) {
            if (isIncrease)
                customer.getCart().addProduct(product);
            else {
                if (!customer.getCart().deleteProduct(product))
                    throw new ProductDoNotExistInCartException("Product does not exist in cart");
            }
        } else throw new ProductDoNotExistAtAllException("Product does not exist at all");
    }

    public double getTotalPrice(Discount discount) {
        return customer.getCart().getTotalPrice(discount);
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
}
