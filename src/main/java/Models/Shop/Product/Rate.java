package Models.Shop.Product;

import Models.Account.Account;

public class Rate {
    private Account account;
    private int score;
    private Product product;

    public Rate(Account account, int score, Product product) {
        this.account = account;
        this.score = score;
        this.product = product;
    }

    public int getScore() {
        return score;
    }
}