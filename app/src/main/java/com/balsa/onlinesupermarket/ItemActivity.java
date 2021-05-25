package com.balsa.onlinesupermarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity implements AddReviewDialog.AddReviewDialogInterface {


    private TrackTimeService trackTimeService;
    private boolean isBound;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TrackTimeService.LocalBinder binder = (TrackTimeService.LocalBinder) service;
            trackTimeService = binder.getService();
            trackTimeService.setItem(incomingItem);
            isBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };


    public static final String SINGLE_ITEM_KEY = "single_item";


    private MaterialToolbar toolbar;
    private TextView txtName, txtPrice, txtDescription, txtAddReview;
    private Button btnAddToCart;
    private ImageView itemImage, firstEmptyStar, firstFilledStar, secondEmptyStar, secondFilledStar,
            thirdEmptyStar, thirdFilledStar, fourthEmptyStar, fourtFilledStar, fifthEmptyStar, fifthFilledStar;
    private RelativeLayout firstStarRelLayout, secondStarRelLayout, thirdStarRelLayout,
            fourthStarRelLayout, fifthStarRelLayout;
    private RecyclerView reviewRecView;
    private Item incomingItem;
    private ReviewsAdapter reviewsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        initViews();

        setSupportActionBar(toolbar);
        reviewsAdapter = new ReviewsAdapter();
        reviewRecView.setAdapter(reviewsAdapter);
        reviewRecView.setLayoutManager(new LinearLayoutManager(this));

        //recieving incoming intent for passing data
        Intent intent = getIntent();
        if (intent != null) {
            incomingItem = intent.getParcelableExtra(SINGLE_ITEM_KEY);
            if (incomingItem != null) {
                //calling Method for increasing user suggerstion cause user visited this item (value = 1)
                Utils.updateUserSuggestion(ItemActivity.this, incomingItem, 1);
                txtName.setText(incomingItem.getName());
                txtPrice.setText(String.valueOf(incomingItem.getPrice()) + " din.");
                txtDescription.setText(incomingItem.getDescription());
                Glide.with(ItemActivity.this)
                        .asBitmap()
                        .load(incomingItem.getImageURL())
                        .into(itemImage);

                //adding reviews
                ArrayList<Review> reviews = Utils.getReviews(this, incomingItem.getId());
                if (reviews != null) {
                    if (reviews.size() > 0) {
                        reviewsAdapter.setReviews(reviews);
                    }
                }

                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO: 16.5.21. finish job here and navigate to cart activity
                        Utils.addItemToCart(ItemActivity.this, incomingItem);
                        Intent cartIntent = new Intent(ItemActivity.this, CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);

                    }
                });

                txtAddReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddReviewDialog dialog = new AddReviewDialog();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(SINGLE_ITEM_KEY, incomingItem);
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "add review");
                    }
                });

                handleRating();
            }
        }


    }

    private void handleRating() {
        switch (incomingItem.getRate()) {
            case 0:
                firstEmptyStar.setVisibility(View.VISIBLE);
                firstFilledStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                fourthEmptyStar.setVisibility(View.VISIBLE);
                fourtFilledStar.setVisibility(View.GONE);
                fifthEmptyStar.setVisibility(View.VISIBLE);
                fifthFilledStar.setVisibility(View.GONE);
                break;
            case 1:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                fourthEmptyStar.setVisibility(View.VISIBLE);
                fourtFilledStar.setVisibility(View.GONE);
                fifthEmptyStar.setVisibility(View.VISIBLE);
                fifthFilledStar.setVisibility(View.GONE);
                break;
            case 2:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                fourthEmptyStar.setVisibility(View.VISIBLE);
                fourtFilledStar.setVisibility(View.GONE);
                fifthEmptyStar.setVisibility(View.VISIBLE);
                fifthFilledStar.setVisibility(View.GONE);
                break;
            case 3:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.VISIBLE);
                fourthEmptyStar.setVisibility(View.VISIBLE);
                fourtFilledStar.setVisibility(View.GONE);
                fifthEmptyStar.setVisibility(View.VISIBLE);
                fifthFilledStar.setVisibility(View.GONE);
                break;
            case 4:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.VISIBLE);
                fourthEmptyStar.setVisibility(View.GONE);
                fourtFilledStar.setVisibility(View.VISIBLE);
                fifthEmptyStar.setVisibility(View.VISIBLE);
                fifthFilledStar.setVisibility(View.GONE);
                break;
            case 5:
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.VISIBLE);
                fourthEmptyStar.setVisibility(View.GONE);
                fourtFilledStar.setVisibility(View.VISIBLE);
                fifthEmptyStar.setVisibility(View.GONE);
                fifthFilledStar.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        firstStarRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingItem.getRate() != 1) {
                    Utils.changeRate(ItemActivity.this, incomingItem.getId(), 1);
                    //calling Method for user suggestion for rating item (value = rate*3) vice verse if its degrading it will be deducated
                    Utils.updateUserSuggestion(ItemActivity.this, incomingItem, (1 - incomingItem.getRate()) * 3);
                    incomingItem.setRate(1);
                    handleRating();
                }
            }
        });
        secondStarRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingItem.getRate() != 2) {
                    Utils.changeRate(ItemActivity.this, incomingItem.getId(), 2);
                    //calling Method for user suggestion for rating item (value = rate*3) vice verse if its degrading it will be deducated
                    Utils.updateUserSuggestion(ItemActivity.this, incomingItem, (2 - incomingItem.getRate()) * 3);
                    incomingItem.setRate(2);
                    handleRating();
                }
            }
        });
        thirdStarRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingItem.getRate() != 3) {
                    Utils.changeRate(ItemActivity.this, incomingItem.getId(), 3);
                    //calling Method for user suggestion for rating item (value = rate*3) vice verse if its degrading it will be deducated
                    Utils.updateUserSuggestion(ItemActivity.this, incomingItem, (3 - incomingItem.getRate()) * 3);
                    incomingItem.setRate(3);
                    handleRating();
                }
            }
        });
        fourthStarRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingItem.getRate() != 4) {
                    Utils.changeRate(ItemActivity.this, incomingItem.getId(), 4);
                    //calling Method for user suggestion for rating item (value = rate*3) vice verse if its degrading it will be deducated
                    Utils.updateUserSuggestion(ItemActivity.this, incomingItem, (4 - incomingItem.getRate()) * 3);
                    incomingItem.setRate(4);
                    handleRating();
                }
            }
        });
        fifthStarRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingItem.getRate() != 5) {
                    Utils.changeRate(ItemActivity.this, incomingItem.getId(), 5);
                    //calling Method for user suggestion for rating item (value = rate*3) vice verse if its degrading it will be deducated
                    Utils.updateUserSuggestion(ItemActivity.this, incomingItem, (5 - incomingItem.getRate()) * 3);
                    incomingItem.setRate(5);
                    handleRating();
                }
            }
        });
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);
        txtAddReview = findViewById(R.id.txtAddReview);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        itemImage = findViewById(R.id.itemImage);
        firstEmptyStar = findViewById(R.id.imageFirstEmptyStar);
        firstFilledStar = findViewById(R.id.imageFirstFilledStart);
        secondEmptyStar = findViewById(R.id.imageSecondEmptyStar);
        secondFilledStar = findViewById(R.id.imageSecondFilledStart);
        thirdEmptyStar = findViewById(R.id.imageThirdEmptyStar);
        thirdFilledStar = findViewById(R.id.imageThirdFilledStart);
        fourthEmptyStar = findViewById(R.id.imageFourthEmptyStar);
        fourtFilledStar = findViewById(R.id.imageFourthFilledStart);
        fifthEmptyStar = findViewById(R.id.imageFifthEmptyStar);
        fifthFilledStar = findViewById(R.id.imageFifthFilledStart);
        reviewRecView = findViewById(R.id.reviewsRecView);
        firstStarRelLayout = findViewById(R.id.firstStarRelLayout);
        secondStarRelLayout = findViewById(R.id.secondStarRelLayout);
        thirdStarRelLayout = findViewById(R.id.thirdStarRelLayout);
        fourthStarRelLayout = findViewById(R.id.fourthStarRelLayout);
        fifthStarRelLayout = findViewById(R.id.fifthStarRelLayout);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, TrackTimeService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(connection);
        }
    }

    @Override
    public void addReview(Review review) {
        Utils.addReviews(this, review);
        //updating user suggestion for adding review for item (value = 2)
        Utils.updateUserSuggestion(this, incomingItem, 2);
        ArrayList<Review> reviews = Utils.getReviews(this, review.getItemID());
        if (reviews != null) {
            reviewsAdapter.setReviews(reviews);
        }
    }
}