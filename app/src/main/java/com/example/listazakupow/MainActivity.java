package com.example.listazakupow;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    EditText name, qty;
    Spinner spinner;
    RecyclerView rv;
    DatabaseHelper db;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.etProductName);
        qty = findViewById(R.id.etProductQuantity);
        spinner = findViewById(R.id.spinnerCategory);
        rv = findViewById(R.id.rvProducts);

        db = new DatabaseHelper(this);

        rv.setLayoutManager(new LinearLayoutManager(this));

        load();

        findViewById(R.id.btnAdd).setOnClickListener(v -> add());

        findViewById(R.id.btnClear).setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Potwierdzenie")
                    .setMessage("Usunąć wszystko?")
                    .setPositiveButton("TAK", (d,w)->{
                        db.clearAll();
                        load();
                    })
                    .setNegativeButton("NIE", null)
                    .show();
        });
    }

    void load() {
        adapter = new ProductAdapter(db.getAllProducts(), p -> {
            db.deleteProduct(p.getName());
            load();
        });
        rv.setAdapter(adapter);
    }

    void add() {
        String n = name.getText().toString();
        String q = qty.getText().toString();

        if(n.isEmpty() || q.isEmpty()) return;

        db.insertProduct(n, Integer.parseInt(q),
                spinner.getSelectedItem().toString());

        name.setText("");
        qty.setText("");

        load();
    }
}