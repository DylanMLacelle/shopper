package ca.on.conestogac.dml.shopper;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ShopperApplication extends Application {

    private static final String DB_NAME = "db_shopper";
    private static final int DB_VERSION = 1;

    private SQLiteOpenHelper helper;

    @Override
    public void onCreate() {
        //Create receipt and item tables to record store name, date, and items on receipts
        helper = new SQLiteOpenHelper(this, DB_NAME, null, DB_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE IF NOT EXISTS receipt(" +
                        "receiptId INTEGER PRIMARY KEY," +
                        "storeName TEXT," +
                        "date TEXT);");
                db.execSQL("CREATE TABLE IF NOT EXISTS item(" +
                        "itemId INTEGER PRIMARY KEY, " +
                        "name TEXT, " +
                        "isTaxed INTEGER, " +
                        "price REAL);");
                db.execSQL("CREATE TABLE IF NOT EXISTS receiptItem(" +
                        "receiptItemId INTEGER PRIMARY KEY, " +
                        "receiptId INTEGER, itemId INTEGER, " +
                        "FOREIGN KEY(receiptId) REFERENCES receipt(receiptId), " +
                        "FOREIGN KEY(itemId) REFERENCES item(itemId));");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                    //No-op
            }
        };
        super.onCreate();
    }

    public void AddReceipt(String store, String date) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO receipt (storeName, date) VALUES('"+store+"','"+date+"')");
    }

    public int GetLastestReceiptId() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM receipt", null);
        cursor.moveToLast();
        return cursor.getInt(0);
    }

    public void AddItem(String itemName, int isTaxed, double price) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO item (name, isTaxed, price) " +
                "VALUES ('" +itemName+ "','" +isTaxed+ "','"+price+"')");
    }

    public int GetLastestItemId() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM item", null);
        cursor.moveToLast();
        return cursor.getInt(0);
    }

    public void AddReceiptItem(int receiptId, int itemId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("INSERT INTO receiptItem (receiptId, itemId) " +
                "VALUES ('"+receiptId+"','"+itemId+"')");
    }

    public ArrayList<ReceiptObject> GetAllReceipts() {
        ArrayList<ReceiptObject> receiptObjects = new ArrayList<ReceiptObject>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM receipt", null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            receiptObjects.add(new ReceiptObject(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2)));
            cursor.moveToNext();
        }
        return receiptObjects;
    }

    public ArrayList<ItemObject> GetAllItemsInReceipt(int receiptId) {
        ArrayList<ItemObject> items = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT item.itemId, name, isTaxed, price " +
                "FROM item " +
                "JOIN receiptItem ON receiptItem.itemId = item.itemId " +
                "WHERE receiptItem.receiptId = ?", new String[] {Integer.toString(receiptId)});

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            boolean taxed = cursor.getInt(2) == 1;
            items.add(new ItemObject(cursor.getInt(0),cursor.getString(1),
                    taxed, cursor.getDouble(3)));
            cursor.moveToNext();
        }

        return items;
    }

    public String GetTotalForSingleReceipt(int receiptId) {
        double totalNum = 0;
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT price, isTaxed from item " +
                "JOIN receiptItem ON receiptItem.itemId = item.itemId" +
                " WHERE receiptItem.receiptId = ?", new String[]{Integer.toString(receiptId)});
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            double tax = 1;
            if(cursor.getInt(1) == 1)
                tax = 1.13;

            totalNum += (cursor.getDouble(0) * tax);
            cursor.moveToNext();
        }

        DecimalFormat df = new DecimalFormat(".");
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        String total = "$" + df.format(totalNum);
        return total;
    }

    public ReceiptObject GetSingleReceipt(int receiptId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from receipt WHERE receiptId = ?",
                new String[] {Integer.toString(receiptId)});
        cursor.moveToFirst();
        return new ReceiptObject(cursor.getInt(0),cursor.getString(1),
                cursor.getString(2));
    }
}
