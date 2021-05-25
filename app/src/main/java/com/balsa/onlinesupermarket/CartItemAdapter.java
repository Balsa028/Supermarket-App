package com.balsa.onlinesupermarket;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    public interface DeleteItemInterface {
        void onDeleteItemResult(Item item);
    }

    public interface CalculateTotal {
        void passTotalPrice(double total);
    }

    private DeleteItemInterface deleteItemInterface;
    private CalculateTotal calculateTotal;
    private ArrayList<Item> items = new ArrayList<>();
    private Context context;
    private Fragment fragment;

    public CartItemAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CartItemAdapter.ViewHolder holder, int position) {
        holder.txtItemName.setText(items.get(position).getName());
        holder.txtPrice.setText(String.valueOf(items.get(position).getPrice()) + " din.");
        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Do you really want to delete this item?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    deleteItemInterface = (DeleteItemInterface) fragment;
                                    deleteItemInterface.onDeleteItemResult(items.get(position));
                                } catch (ClassCastException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                builder.create().show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
        calculateTotalPrice();
        notifyDataSetChanged();
    }

    public void calculateTotalPrice() {
        double total = 0;
        for (Item i : items) {
            total += i.getPrice();
        }
        try {
            calculateTotal = (CalculateTotal) fragment;
            calculateTotal.passTotalPrice(total);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtItemName, txtDelete, txtPrice;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtDelete = itemView.findViewById(R.id.txtDelete);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }
    }
}
