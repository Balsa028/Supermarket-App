package com.balsa.onlinesupermarket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class FirstFragment extends Fragment implements CartItemAdapter.DeleteItemInterface, CartItemAdapter.CalculateTotal {


    @Override
    public void passTotalPrice(double total) {
        txtTotalPrice.setText(String.valueOf(total) + " din.");
    }

    @Override
    public void onDeleteItemResult(Item item) {
        Utils.DeleteCartItem(getActivity(), item);
        ArrayList<Item> cartItems = Utils.getAllCartItems(getActivity());
        if (cartItems != null) {
            if (cartItems.size() > 0) {
                txtEmptyCart.setVisibility(View.GONE);
                allItemsRelLayout.setVisibility(View.VISIBLE);
                adapter.setItems(cartItems);
            } else {
                txtEmptyCart.setVisibility(View.VISIBLE);
                allItemsRelLayout.setVisibility(View.GONE);
            }
        } else {
            txtEmptyCart.setVisibility(View.VISIBLE);
            allItemsRelLayout.setVisibility(View.GONE);
        }

    }

    private RecyclerView cartItemsRecView;
    private TextView txtTotalPrice, txtEmptyCart;
    private Button btnNext;
    private CartItemAdapter adapter;
    private RelativeLayout allItemsRelLayout;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment_layout, null);
        initViews(view);

        adapter = new CartItemAdapter(getActivity(), this);
        cartItemsRecView.setAdapter(adapter);
        cartItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<Item> cartItems = Utils.getAllCartItems(getActivity());
        if (cartItems != null) {
            if (cartItems.size() > 0) {
                txtEmptyCart.setVisibility(View.GONE);
                allItemsRelLayout.setVisibility(View.VISIBLE);
                adapter.setItems(cartItems);
            } else {
                txtEmptyCart.setVisibility(View.VISIBLE);
                allItemsRelLayout.setVisibility(View.GONE);
            }
        } else {
            txtEmptyCart.setVisibility(View.VISIBLE);
            allItemsRelLayout.setVisibility(View.GONE);
        }
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, new SecondFragment());
                transaction.commit();
            }
        });


        return view;
    }

    private void initViews(View view) {
        cartItemsRecView = view.findViewById(R.id.CartItemsRevView);
        txtTotalPrice = view.findViewById(R.id.txtTotalPrice);
        btnNext = view.findViewById(R.id.btnNext);
        allItemsRelLayout = view.findViewById(R.id.allItemsRelativeLayout);
        txtEmptyCart = view.findViewById(R.id.txtEmptyCart);
    }
}
