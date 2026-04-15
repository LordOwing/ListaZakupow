package com.example.listazakupow;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText productNameInput;
    private EditText quantityInput;
    private Button addButton;
    private Button clearButton;
    private RecyclerView productsRecycler;
    private ProductAdapter adapter;
    private DatabaseHelper dbHelper;
    private List<ProductItem> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productNameInput = findViewById(R.id.productName);
        quantityInput = findViewById(R.id.quantity);
        addButton = findViewById(R.id.addButton);
        clearButton = findViewById(R.id.clearButton);
        productsRecycler = findViewById(R.id.productsRecycler);

        dbHelper = new DatabaseHelper(this);

        productsRecycler.setLayoutManager(new LinearLayoutManager(this));

        loadProducts();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showClearDialog();
            }
        });
    }

    private void loadProducts() {
        products = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_PRODUCTS, null);

        int nameColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
        int quantityColumnIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_QUANTITY);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(nameColumnIndex);
                int quantity = cursor.getInt(quantityColumnIndex);
                products.add(new ProductItem(name, quantity));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        adapter = new ProductAdapter(products);
        productsRecycler.setAdapter(adapter);
    }

    private void addProduct() {
        String name = productNameInput.getText().toString().trim();
        String quantityStr = quantityInput.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Nazwa produktu nie może być pusta", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity <= 0) {
                Toast.makeText(this, "Ilość musi być większa od zera", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Podaj prawidłową ilość", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, name);
        values.put(DatabaseHelper.COLUMN_QUANTITY, quantity);
        db.insert(DatabaseHelper.TABLE_PRODUCTS, null, values);
        db.close();

        loadProducts();

        productNameInput.setText("");
        quantityInput.setText("");

        Toast.makeText(this, "Produkt dodany", Toast.LENGTH_SHORT).show();
    }

    private void showClearDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Potwierdzenie")
                .setMessage("Czy na pewno chcesz usunąć wszystkie produkty?")
                .setPositiveButton("TAK", (dialog, which) -> {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.delete(DatabaseHelper.TABLE_PRODUCTS, null, null);
                    db.close();
                    loadProducts();
                    Toast.makeText(MainActivity.this, "Lista wyczyszczona", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("ANULUJ", (dialog, which) -> dialog.dismiss())
                .show();
    }
}