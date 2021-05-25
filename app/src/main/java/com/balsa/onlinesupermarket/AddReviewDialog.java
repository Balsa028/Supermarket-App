package com.balsa.onlinesupermarket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.balsa.onlinesupermarket.ItemActivity.SINGLE_ITEM_KEY;

public class AddReviewDialog extends DialogFragment {

    private TextView txtName, txtWarning;
    private Button btnAddReview;
    private EditText edtTextUserName, edtTextReviewText;

    public interface AddReviewDialogInterface {
        void addReview(Review review);
    }

    private AddReviewDialogInterface addReviewDialogInterface;


    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.add_review_dialog, null);
        initViews(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Item item = bundle.getParcelable(SINGLE_ITEM_KEY);
            if (item != null) {
                txtName.setText(item.getName());
                btnAddReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edtTextReviewText.getText().toString().equals("") || edtTextUserName.getText().toString().equals("")) {
                            txtWarning.setVisibility(View.VISIBLE);
                            txtWarning.setText(R.string.warning_message);
                        } else {
                            txtWarning.setVisibility(View.GONE);
                            String date = getCurrentDate();
                            Review review = new Review(item.getId(), edtTextUserName.getText().toString(), edtTextReviewText.getText().toString(), date);
                            try {
                                addReviewDialogInterface = (AddReviewDialogInterface) getActivity();
                                addReviewDialogInterface.addReview(review);
                                dismiss();
                            } catch (ClassCastException e) {
                                e.printStackTrace();
                                dismiss();
                            }
                        }

                    }
                });
            }
        }


        return builder.create();
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat pattern = new SimpleDateFormat("dd-MM-yyyy");
        return pattern.format(calendar.getTime());
    }

    private void initViews(View view) {
        txtName = view.findViewById(R.id.txtReviewName);
        txtWarning = view.findViewById(R.id.txtWarning);
        btnAddReview = view.findViewById(R.id.btnAddReview);
        edtTextReviewText = view.findViewById(R.id.editTextReviewText);
        edtTextUserName = view.findViewById(R.id.editTextUserName);

    }
}
