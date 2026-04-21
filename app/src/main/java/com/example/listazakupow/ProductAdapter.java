package com.example.listazakupow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.VH> {

    public interface OnDelete {
        void onDelete(ProductItem p);
    }

    private List<ProductItem> list;
    private OnDelete listener;

    public ProductAdapter(List<ProductItem> list, OnDelete listener) {
        this.list = list;
        this.listener = listener;
    }

    public static class VH extends RecyclerView.ViewHolder {
        TextView name, qty, cat;
        ImageButton delete;

        public VH(View v) {
            super(v);
            name = v.findViewById(R.id.tvProductName);
            qty = v.findViewById(R.id.tvQuantity);
            cat = v.findViewById(R.id.tvCategory);
            delete = v.findViewById(R.id.btnDelete);
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup p, int v) {
        return new VH(LayoutInflater.from(p.getContext())
                .inflate(R.layout.item_product, p, false));
    }

    @Override
    public void onBindViewHolder(VH h, int i) {
        ProductItem p = list.get(i);
        h.name.setText(p.getName());
        h.qty.setText("Ilość: " + p.getQuantity());
        h.cat.setText("Kategoria: " + p.getCategory());

        h.delete.setOnClickListener(v -> listener.onDelete(p));
    }

    @Override
    public int getItemCount() { return list.size(); }
}