package Models.Shop;

import Models.Account.Account;
import Models.Account.Customer;
import Models.Account.Seller;
import Models.Address;
import Models.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Product {
    private static ArrayList<Product> allProducts = new ArrayList<Product>();
    private String id;
    private ProductStatus status;
    private String name;
    private String companyName;
    private double price;
    private Seller seller;
    private boolean isAvailable;
    private Category category;
    private String description;
    private ArrayList<Rate> allRates;
    private ArrayList<Customer> allBuyers;
    private List<Comment> allComments;
    private ArrayList<Feature> features;
    private Auction auction;
    //TODO different sellers for one product


    public Product(String id, String name, String companyName, double price, Seller seller,
                   boolean isAvailable, Category category, String description, ArrayList<Feature> features) {
        this.id = id;
        this.name = name;
        this.companyName = companyName;
        this.price = price;
        this.seller = seller;
        this.isAvailable = isAvailable;
        this.category = category;
        this.description = description;
        this.features = features;
        allProducts.add(this);
        this.status = ProductStatus.UNDER_REVIEW_FOR_CONSTRUCTION;
    }

    public Feature getFeatureByName(String name) {
        for (Feature feature : features) {
            if (feature.getName().equals(name)) {
                return feature;
            }
        }
        return null;
    }

    public static ArrayList<Product> getProductsByName(String name) {
        ArrayList<Product> products = new ArrayList<Product>();
        for (Product product : allProducts) {
            if (product.getName().equals(name)) {
                products.add(product);
            }
        }
        return products;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public String getName() {
        return name;
    }

    public static boolean hasProductWithId(String id) {
        return false;
    }

    public static Product getProductById(String id) {
        for (Product product : allProducts) {
            if (product.getId().toLowerCase().equals(id.toLowerCase())) {
                return product;
            }
        }
        return null;
    }

    public static void deleteProduct(Product product) {

    }

    public String viewProductInShort() {
        //ToDo
        return null;
    }

    public static ArrayList<String> viewProductsInShort(Seller seller) {
        ArrayList<String> allProductsInShort = new ArrayList<String>();
        for (Product product : allProducts) {
            if (product.getSeller().equals(seller)) {
                allProductsInShort.add(product.viewProductInShort());
            }
        }
        return allProductsInShort;
    }

    public String getId() {
        return id;
    }

    public List<String> getComments() {
        List<String> comments = new ArrayList<>();
        for (Comment comment : allComments) {
            comments.add(comment.getText());
        }
        return comments;
    }

    public Seller getSeller() {
        return seller;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public String getAttributes() {
        //TODO make better
        return "Name: " + name +
                "\nId: " + id +
                "\nCompany name: " + companyName +
                "\nSeller: " + seller.getName() +
                "\nDescription: " + description +
                "\n" + category.getFeaturesNames().toString();
    }

    public String digest() {
        return "\nDescription: " + description +
                "\nPrice: " + price +
                "\nDiscount: " + auction.getDiscountAmount();
    }

    public String getRate() {
        int sum = 0;
        for (Rate rate : allRates) {
            sum += rate.getScore();
        }
        return "" + sum / allRates.size();
    }

    public void addRate(Account account, int rate) {
        allRates.add(new Rate(account, rate, this));
    }

    public void addComment(Comment comment) {
        allComments.add(comment);
    }

    public enum ProductStatus {
        UNDER_REVIEW_FOR_CONSTRUCTION, UNDER_REVIEW_FOR_EDITING, CONFIRMED
    }

    public static ProductStatus parseProductStatus(String statusName) {
        if (statusName.equals("UNDER_REVIEW_FOR_CONSTRUCTION")) {
            return ProductStatus.UNDER_REVIEW_FOR_CONSTRUCTION;
        } else if (statusName.equals("UNDER_REVIEW_FOR_EDITING")) {
            return ProductStatus.UNDER_REVIEW_FOR_EDITING;
        } else if (statusName.equals("CONFIRMED")) {
            return ProductStatus.CONFIRMED;
        } else {
            return null;
        }
    }

    public Category getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getPrice() {
        return price;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static void open(){
        File folder = new File(Address.PRODUCTS.get());
        if(!folder.exists()) folder.mkdirs();
        else {
            for (File file : folder.listFiles()) {
                allProducts.add(open(file));
            }
        }
    }

    public static Product open(File file){
        StringBuilder json = new StringBuilder();
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNext()) {
                json.append(reader.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Gson.INSTANCE.get().fromJson(json.toString(),Product.class);
    }

    public static void save(){
        for (Product product : allProducts) {
            save(product);
        }
    }

    public static void save(Product product){
        try {
            String jsonAccount = Gson.INSTANCE.get().toJson(product);
            try {
                FileWriter file = new FileWriter(Address.PRODUCTS.get() +"\\"+product.getId()+".json");
                file.write(jsonAccount);
                file.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", companyName='" + companyName + '\'' +
                ", price=" + price +
                ", seller=" + seller +
                ", isAvailable=" + isAvailable +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", allComments=" + allComments +
                '}';
    }
}
