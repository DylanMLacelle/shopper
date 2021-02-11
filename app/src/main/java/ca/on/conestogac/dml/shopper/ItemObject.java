package ca.on.conestogac.dml.shopper;

public class ItemObject {

    private int mId;
    private String mItemName;
    private boolean mIsTaxed;
    private double mPrice;

    public ItemObject(int id, String name, boolean isTaxed, double price) {
        mId = id;
        mItemName = name;
        mIsTaxed = isTaxed;
        mPrice = price;
    }

    public int getmId() {
        return mId;
    }

    public String getmItemName() {
        return mItemName;
    }

    public boolean ismIsTaxed() {
        return mIsTaxed;
    }

    public double getmPrice() {
        return mPrice;
    }
}
