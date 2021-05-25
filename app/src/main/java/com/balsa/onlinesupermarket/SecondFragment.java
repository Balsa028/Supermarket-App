package com.balsa.onlinesupermarket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SecondFragment extends Fragment {


    private EditText editTextAdress, editTextZipCode, editTextPhone, editTextEmail;
    private Button btnBack, btnNext;
    private TextView txtWarning;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_cart_fragment, null);
        initViews(view);


        Bundle bundle = getArguments();
        if (bundle != null) {
            String jsonOrder = bundle.getString("order");
            if (jsonOrder != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<Order>() {
                }.getType();
                Order order = gson.fromJson(jsonOrder, type);
                if (order != null) {
                    editTextAdress.setText(order.getAddress());
                    editTextZipCode.setText(order.getZipCode());
                    editTextPhone.setText(order.getPhoneNumber());
                    editTextEmail.setText(order.getEmail());
                }
            }
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, new FirstFragment());
                transaction.commit();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateDate()) {
                    txtWarning.setVisibility(View.GONE);

                    ArrayList<Item> cartItems = Utils.getAllCartItems(getActivity());
                    if (cartItems != null) {

                        //creating order with empty constructor cause thing like payment method and success field i willl add in third fragment
                        Order order = new Order();
                        order.setAddress(editTextAdress.getText().toString());
                        order.setEmail(editTextEmail.getText().toString());
                        order.setPhoneNumber(editTextPhone.getText().toString());
                        order.setZipCode(editTextZipCode.getText().toString());
                        order.setTotalPrice(getTotalPrice(Utils.getAllCartItems(getActivity())));
                        order.setCartItems(Utils.getAllCartItems(getActivity()));
                        //cause of problem with arrays and parceleble i am using gson library to parse to json and vice versa
                        Gson gson = new Gson();
                        String jsonOrder = gson.toJson(order);

                        Bundle bundle = new Bundle();
                        bundle.putString("order", jsonOrder);
                        ThirdFragment thirdFragment = new ThirdFragment();
                        thirdFragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, thirdFragment);
                        transaction.commit();

                    }
                } else {
                    txtWarning.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    private double getTotalPrice(ArrayList<Item> items) {
        double total = 0;
        for (Item i : items) {
            total += i.getPrice();
        }
        return total;
    }

    private boolean validateDate() {
        if (editTextEmail.getText().toString().equals("")
                || editTextPhone.getText().toString().equals("")
                || editTextZipCode.getText().toString().equals("")
                || editTextAdress.getText().toString().equals("")) {

            return false;
        }
        return true;
    }

    private void initViews(View view) {
        editTextAdress = view.findViewById(R.id.editTextAdress);
        editTextZipCode = view.findViewById(R.id.editTextZipCode);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        btnBack = view.findViewById(R.id.btnBack);
        btnNext = view.findViewById(R.id.btnNextSecFragment);
        txtWarning = view.findViewById(R.id.txtWarning);
    }
}
