package com.balsa.onlinesupermarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public class ResultFragment extends Fragment {

    private TextView txtMessage;
    private Button btnGoBackToShop;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_layout, null);
        txtMessage = view.findViewById(R.id.txtMessage);
        btnGoBackToShop = view.findViewById(R.id.btnGoBackToShop);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String jsonOrder = bundle.getString("order");
            if (jsonOrder != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<Order>() {
                }.getType();
                Order order = gson.fromJson(jsonOrder, type);
                if (order != null) {
                    if (order.getSuccess().equals("true")) {
                        txtMessage.setText("Payment was successful.");
                        //erasing all cart items when payment is finished
                        Utils.removeItemsFromCart(getActivity());
                        //updating popularity field in out Item class after its bought
                        for (Item item : order.getCartItems()) {
                            Utils.increasePopularityPoint(getActivity(), item, 1);
                        }
                        // updating user point after purchase (value = 4 per item)
                        for (Item i : order.getCartItems()) {
                            Utils.updateUserSuggestion(getActivity(), i, 4);
                        }
                    } else {
                        txtMessage.setText("Something went wrong, your payment isn't accepted \nPlease try again.");
                    }
                } else {
                    txtMessage.setText("Something went wrong, your payment isn't accepted \nPlease try again.");
                }
            } else {
                txtMessage.setText("Something went wrong, your payment isn't accepted \nPlease try again.");
            }
        }

        btnGoBackToShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


        return view;
    }
}
