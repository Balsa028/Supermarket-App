package com.balsa.onlinesupermarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainFragment extends Fragment {


    private BottomNavigationView bottomNavigationView;
    private RecyclerView newItemsRecView, popularItemsRecView, suggestedItemsRecView;
    private ItemAdapter newItemsAdapter, popularItemsAdapter, suggestedItemsAdapter;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        initViews(view);
        initBottomNaviView();
//        Utils.clearSharedPreferences(getActivity());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecViews();
    }

    private void initRecViews() {
        newItemsAdapter = new ItemAdapter(getActivity());
        newItemsRecView.setAdapter(newItemsAdapter);
        newItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        popularItemsAdapter = new ItemAdapter(getActivity());
        popularItemsRecView.setAdapter(popularItemsAdapter);
        popularItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        suggestedItemsAdapter = new ItemAdapter(getActivity());
        suggestedItemsRecView.setAdapter(suggestedItemsAdapter);
        suggestedItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        ArrayList<Item> allItems = Utils.getAllItems(getActivity());
        if (allItems != null) {
            Comparator<Item> newItemComparator = new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2) {
                    //if its bigger it will be positive number , if first object is lower it will be negative and if its equal its 0
                    return o1.getId() - o2.getId();
                }
            };
            Collections.sort(allItems, Collections.reverseOrder(newItemComparator));
            newItemsAdapter.setItems(allItems);
        }

        ArrayList<Item> popularItems = Utils.getAllItems(getActivity());
        if (popularItems != null) {
            Comparator<Item> popularItemsComparator = new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2) {
                    return o1.getPopular() - o2.getPopular();
                }
            };
            Collections.sort(popularItems, Collections.reverseOrder(popularItemsComparator));
            popularItemsAdapter.setItems(popularItems);
        }

        ArrayList<Item> suggestedItems = Utils.getAllItems(getActivity());
        if (suggestedItems != null) {
            Comparator<Item> suggestedItemsComparator = new Comparator<Item>() {
                @Override
                public int compare(Item o1, Item o2) {
                    return o1.getUserSuggestion() - o2.getUserSuggestion();
                }
            };
            Collections.sort(suggestedItems, Collections.reverseOrder(suggestedItemsComparator));
            suggestedItemsAdapter.setItems(suggestedItems);
        }

    }

    private void initBottomNaviView() {
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        Intent intent = new Intent(getActivity(), SearchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.home:
                        break;
                    case R.id.cartBottomNavView:
                        Intent cartIntent = new Intent(getActivity(), CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initViews(View view) {
        bottomNavigationView = view.findViewById(R.id.bottonNavView);
        newItemsRecView = view.findViewById(R.id.NewitemsRecView);
        popularItemsRecView = view.findViewById(R.id.PopularitemsRecView);
        suggestedItemsRecView = view.findViewById(R.id.SuggestedRecView);
    }
}
