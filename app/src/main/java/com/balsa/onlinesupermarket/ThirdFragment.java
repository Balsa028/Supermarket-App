package com.balsa.onlinesupermarket;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThirdFragment extends Fragment {

    private static final String TAG = "ThirdFragment";


    TextView txtItemNames, txtOrderAdress, txtOrderZipCode, txtOrderPhoneNumber, txtOrderPrice;
    RadioGroup radioGroup;
    RadioButton rbCreditCard, rbPaypal;
    Button btnBackOrder, btnCheckout;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_cart_fragment, null);
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
                    String items = "";
                    for (Item s : order.getCartItems()) {
                        items += "\n\t" + s.getName();
                    }
                    txtItemNames.setText(items);
                    txtOrderPrice.setText(String.valueOf(order.getTotalPrice()) + " din.");
                    txtOrderAdress.setText(order.getAddress());
                    txtOrderPhoneNumber.setText(order.getPhoneNumber());


                    btnBackOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SecondFragment secondFragment = new SecondFragment();
                            Bundle secondBundle = new Bundle();
                            secondBundle.putString("order", jsonOrder);
                            secondFragment.setArguments(secondBundle);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.container, secondFragment);
                            transaction.commit();
                        }
                    });

                    btnCheckout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switch (radioGroup.getCheckedRadioButtonId()) {
                                case R.id.rbCreditCard:
                                    order.setPaymentMethod("Credit Card");
                                    break;
                                case R.id.rbPaypal:
                                    order.setPaymentMethod("PayPal");
                                    break;
                                default:
                                    order.setPaymentMethod("unknown");
                                    break;
                            }
                            //for testing purposes
                            order.setSuccess("true");
                            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor()
                                    .setLevel(HttpLoggingInterceptor.Level.BODY);
                            OkHttpClient client = new OkHttpClient.Builder()
                                    .addInterceptor(interceptor)
                                    .build();
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("https://jsonplaceholder.typicode.com/")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .client(client)
                                    .build();

                            OrderEndPoint orderEndPoint = retrofit.create(OrderEndPoint.class);
                            Call<Order> call = orderEndPoint.newOrder(order);
                            call.enqueue(new Callback<Order>() {
                                @Override
                                public void onResponse(Call<Order> call, Response<Order> response) {
                                    Log.d(TAG, "onResponse: Code: " + response.code());
                                    if (response.isSuccessful()) {
                                        // TODO: 18.5.21. navigate user to another fragment and inform him
                                        String resultString = gson.toJson(response.body());
                                        Bundle resultBundle = new Bundle();
                                        resultBundle.putString("order", resultString);
                                        ResultFragment resultFragment = new ResultFragment();
                                        resultFragment.setArguments(resultBundle);
                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.container, resultFragment);
                                        transaction.commit();

                                    } else {
                                        //in case there is response but its not order we are just navigating user to
                                        //result fragment with null value and handle that in result fragment
                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.container, new ResultFragment());
                                        transaction.commit();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Order> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });


                        }
                    });
                }
            }
        }


        return view;
    }

    private void initViews(View view) {
        txtItemNames = view.findViewById(R.id.txtItemNameOrder);
        txtOrderAdress = view.findViewById(R.id.txtAdressOrder);
        txtOrderZipCode = view.findViewById(R.id.txtZipCode);
        txtOrderPhoneNumber = view.findViewById(R.id.txtPhoneNumberOrder);
        txtOrderPrice = view.findViewById(R.id.txtPriceOrder);
        radioGroup = view.findViewById(R.id.RadioGroup);
        rbCreditCard = view.findViewById(R.id.rbCreditCard);
        rbPaypal = view.findViewById(R.id.rbPaypal);
        btnBackOrder = view.findViewById(R.id.btnBackOrder);
        btnCheckout = view.findViewById(R.id.btnCheckOut);


    }
}
