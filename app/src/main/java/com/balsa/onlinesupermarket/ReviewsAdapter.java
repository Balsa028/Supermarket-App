package com.balsa.onlinesupermarket;

import android.os.strictmode.CredentialProtectedWhileLockedViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {


    private ArrayList<Review> reviews = new ArrayList<>();

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ReviewsAdapter.ViewHolder holder, int position) {

        holder.txtUserName.setText(reviews.get(position).getUsername());
        holder.txtDate.setText(reviews.get(position).getDate());
        holder.txtReviewText.setText(reviews.get(position).getText());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtUserName, txtReviewText, txtDate;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtReviewText = itemView.findViewById(R.id.txtReviewText);
            txtDate = itemView.findViewById(R.id.txtDate);

        }
    }
}
