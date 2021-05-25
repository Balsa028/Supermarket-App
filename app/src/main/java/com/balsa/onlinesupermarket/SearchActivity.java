package com.balsa.onlinesupermarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.GatewayInfo;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements CategoryDialog.CategoryInterface {

    private MaterialToolbar toolbar;
    private EditText editTextSearchBar;
    private ImageView searchIcon;
    private TextView firstCategory, secondCategory, thirdCategory, allCategories;
    private RecyclerView searchRecView;
    private BottomNavigationView bottomNavigationView;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        initBottomNaviView();
        //setting toolbar
        setSupportActionBar(toolbar);
        //initializing adapter

        adapter = new ItemAdapter(this);
        searchRecView.setLayoutManager(new GridLayoutManager(this, 2));
        searchRecView.setAdapter(adapter);

        //getting data from main activity
        Intent intent = getIntent();
        if (intent != null) {
            String category = intent.getStringExtra("category");
            if (category != null) {
                ArrayList<Item> items = Utils.getItemsByCategory(SearchActivity.this, category);
                if (items != null) {
                    adapter.setItems(items);
                    updateUserSuggestionWhenSearched(items);
                }
            }
        }

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSearch();
            }
        });
        editTextSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                initSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ArrayList<String> categories = Utils.getCategories(this);
        if (categories != null) {
            if (categories.size() > 0) {
                if (categories.size() == 1) {
                    handleCategories(categories, 1);
                } else if (categories.size() == 2) {
                    handleCategories(categories, 2);
                } else {
                    handleCategories(categories, 3);
                }
            }
        }

        allCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryDialog dialog = new CategoryDialog();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("categories", Utils.getCategories(SearchActivity.this));
                bundle.putString("activity", "search_activity");
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "search_actvity");
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void handleCategories(ArrayList<String> strings, int i) {
        switch (i) {
            case 1:
                firstCategory.setVisibility(View.VISIBLE);
                firstCategory.setText(strings.get(0));
                secondCategory.setVisibility(View.GONE);
                thirdCategory.setVisibility(View.GONE);
                firstCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<Item> items = Utils.getItemsByCategory(SearchActivity.this, strings.get(0));
                        if (items != null) {
                            adapter.setItems(items);
                            updateUserSuggestionWhenSearched(items);
                        }
                    }
                });
                break;
            case 2:
                firstCategory.setVisibility(View.VISIBLE);
                firstCategory.setText(strings.get(0));
                secondCategory.setVisibility(View.VISIBLE);
                secondCategory.setText(strings.get(1));
                thirdCategory.setVisibility(View.GONE);
                firstCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<Item> items = Utils.getItemsByCategory(SearchActivity.this, strings.get(0));
                        if (items != null) {
                            adapter.setItems(items);
                            updateUserSuggestionWhenSearched(items);
                        }
                    }
                });
                secondCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<Item> items = Utils.getItemsByCategory(SearchActivity.this, strings.get(1));
                        if (items != null) {
                            adapter.setItems(items);
                            updateUserSuggestionWhenSearched(items);
                        }
                    }
                });
                break;
            case 3:
                firstCategory.setVisibility(View.VISIBLE);
                firstCategory.setText(strings.get(0));
                secondCategory.setVisibility(View.VISIBLE);
                secondCategory.setText(strings.get(1));
                thirdCategory.setVisibility(View.VISIBLE);
                thirdCategory.setText(strings.get(2));

                firstCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<Item> items = Utils.getItemsByCategory(SearchActivity.this, strings.get(0));
                        if (items != null) {
                            adapter.setItems(items);
                            updateUserSuggestionWhenSearched(items);
                        }
                    }
                });
                secondCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<Item> items = Utils.getItemsByCategory(SearchActivity.this, strings.get(1));
                        if (items != null) {
                            adapter.setItems(items);
                            updateUserSuggestionWhenSearched(items);
                        }
                    }
                });
                thirdCategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<Item> items = Utils.getItemsByCategory(SearchActivity.this, strings.get(2));
                        if (items != null) {
                            adapter.setItems(items);
                            updateUserSuggestionWhenSearched(items);
                        }
                    }
                });
                break;
            default:
                firstCategory.setVisibility(View.GONE);
                secondCategory.setVisibility(View.GONE);
                thirdCategory.setVisibility(View.GONE);
                break;
        }

    }

    private void initSearch() {
        if (!editTextSearchBar.getText().toString().equals("")) {
            String name = editTextSearchBar.getText().toString();
            ArrayList<Item> searchedItems = Utils.searchByString(this, name);

            if (searchedItems != null) {
                adapter.setItems(searchedItems);
                updateUserSuggestionWhenSearched(searchedItems);
            }

        }
    }

    private void updateUserSuggestionWhenSearched(ArrayList<Item> items) {
        for (Item i : items) {
            Utils.updateUserSuggestion(this, i, 1);
        }
    }

    private void initBottomNaviView() {
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        break;
                    case R.id.home:
                        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.cartBottomNavView:
                        Intent cartIntent = new Intent(SearchActivity.this, CartActivity.class);
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

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottonNavViewSearch);
        editTextSearchBar = findViewById(R.id.editTextSearchBar);
        searchIcon = findViewById(R.id.imageSearch);
        firstCategory = findViewById(R.id.txtFirstCategory);
        secondCategory = findViewById(R.id.txtSecondCategory);
        thirdCategory = findViewById(R.id.txtThirdCategory);
        searchRecView = findViewById(R.id.recViewSearched);
        allCategories = findViewById(R.id.txtAllCategories);
    }

    @Override
    public void getItemsByCategory(String category) {

        ArrayList<Item> items = Utils.getItemsByCategory(this, category);
        if (items != null) {
            adapter.setItems(items);
            updateUserSuggestionWhenSearched(items);
        }
    }
}