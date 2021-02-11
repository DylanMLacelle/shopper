package ca.on.conestogac.dml.shopper;

public class ReceiptObject {

    private int mId;
    private String mStoreName;
    private String mDate;
//    private double mPriceTotal;

    public ReceiptObject(int id, String storeName, String date) {
        mId = id;
        mStoreName = storeName;
        mDate = date;
        //mPriceTotal = price;
    }


    /*public double getPriceTotal() {
        return mPriceTotal;
    }*/

    public String getDate() {
        return mDate;
    }

    public String getStoreName() {
        return mStoreName;
    }

    public int getId() {
        return mId;
    }
}
