package Models.Shop.Log;

import java.util.Date;

public class SellingLog extends Log {
    private double auctionAmount;
    private String buyerName;

    public SellingLog(String id, Date date, double paymentAmount, String address, String phoneNumber, String customerName, boolean isReceived, double auctionAmount, String buyerName) {
        super(id, date, paymentAmount, address, phoneNumber, customerName, isReceived);
        this.auctionAmount = auctionAmount;
        this.buyerName = buyerName;
    }

    public String getBuyerName() {
        return buyerName;
    }


    @Override
    public String toString() {
        return "SellingLog{" +
                "auctionAmount=" + auctionAmount +
                ", buyerName='" + buyerName + '\'' +
                ", id='" + id + '\'' +
                ", date=" + date +
                ", paymentAmount=" + paymentAmount +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", products=" + products +
                ", customerName='" + customerName + '\'' +
                ", isReceived=" + isReceived +
                '}';
    }

    @Override
    public String viewLogInShort() {
return null;
    }
}