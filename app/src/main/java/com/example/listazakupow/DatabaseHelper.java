package com.example.listazakupow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "shopping.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE = "products";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String QUANTITY = "quantity";
    public static final String CATEGORY = "category";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT NOT NULL, " +
                QUANTITY + " INTEGER NOT NULL, " +
                CATEGORY + " TEXT DEFAULT 'Inne')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void insertProduct(String name, int quantity, String category) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(NAME, name);
        v.put(QUANTITY, quantity);
        v.put(CATEGORY, category);
        db.insert(TABLE, null, v);
        db.close();
    }

    public List<ProductItem> getAllProducts() {
        List<ProductItem> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE, null);

        while (c.moveToNext()) {
            list.add(new ProductItem(
                    c.getString(1),
                    c.getInt(2),
                    c.getString(3)
            ));
        }

        c.close();
        db.close();
        return list;
    }

    public void deleteProduct(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, NAME + "=?", new String[]{name});
        db.close();
    }

    public void clearAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, null, null);
        db.close();
    }
}