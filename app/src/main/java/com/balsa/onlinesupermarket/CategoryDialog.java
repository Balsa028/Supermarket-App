package com.balsa.onlinesupermarket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class CategoryDialog extends DialogFragment {


    public interface CategoryInterface {
        void getItemsByCategory(String category);
    }

    private CategoryInterface categoryInterface;
    private ListView listView;

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_categories, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String activity = bundle.getString("activity");
            ArrayList<String> categories = bundle.getStringArrayList("categories");
            if (categories != null) {
                initViews(view);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categories);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //using switch statement cause we are calling dialog from two different places and we need diffrent behevior
                        switch (activity) {
                            case "main_activity":
                                Intent intent = new Intent(getActivity(), SearchActivity.class);
                                intent.putExtra("category", categories.get(position));
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                getActivity().startActivity(intent);
                                dismiss();
                                break;
                            case "search_activity":
                                try {
                                    categoryInterface = (CategoryInterface) getActivity();
                                    categoryInterface.getItemsByCategory(categories.get(position));
                                    dismiss();
                                } catch (ClassCastException e) {
                                    e.printStackTrace();
                                    dismiss();
                                }
                                break;
                            default:
                                break;

                        }
                    }
                });
            }
        }
        return builder.create();
    }

    private void initViews(View view) {
        listView = view.findViewById(R.id.listView);
    }
}
