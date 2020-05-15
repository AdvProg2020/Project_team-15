package Models.Shop;

import Control.Manager;
import Control.Seller.OffsManager;
import Models.Account.Seller;

import java.util.ArrayList;
import java.util.Date;

public class AddOffRequest extends Request{
    private Auction auction;
    private String auctionId;

    public AddOffRequest(String id, Seller seller, Auction auction) {
        super(id, seller);
        this.type = RequestType.ADD_OFF;
        this.auction = auction;
        this.auctionId = auction.getId();
    }

    public void accept() {
        auction.setStatus(Auction.AuctionStatus.CONFIRMED);
    }

    @Override
    protected void loadReference() {
        auction = Auction.getAuctionById(auctionId);
    }

    @Override
    public String toString() {
        return "AddOffRequest{" +
                "auction=" + auction +
                ", id='" + id + '\'' +
                ", seller=" + seller +
                ", type=" + type +
                '}';
    }
}
