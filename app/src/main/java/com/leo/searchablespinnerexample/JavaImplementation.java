package com.leo.searchablespinnerexample;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.leo.searchablespinner.SearchableSpinner;
import com.leo.searchablespinner.interfaces.OnItemSelectListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class JavaImplementation extends AppCompatActivity {
    private Boolean isTextInputLayoutClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchable_spinner_implementation);
        final TextInputLayout textInputSpinner = findViewById(R.id.textInputSpinner);
        textInputSpinner.setOnKeyListener(null);
        final EditText editTextSpinner = findViewById(R.id.editTextSpinner);
        editTextSpinner.setKeyListener(null);

        final SearchableSpinner searchableSpinner = new SearchableSpinner(this);
        searchableSpinner.setWindowTitle("SEARCHABLE SPINNER");


        ArrayList<String> androidVersionList = new ArrayList<>(Arrays.asList("Cupcake", "Donut",
                "Eclair",
                "Froyo",
                "Gingerbread",
                "Honeycomb",
                "Ice Cream Sandwich",
                "Jelly Bean",
                "KitKat",
                "Lollipop",
                "Marshmallow",
                "Nougat",
                "10"));
        searchableSpinner.setSpinnerListItems(androidVersionList);
        searchableSpinner.setOnItemSelectListener(new OnItemSelectListener() {
            @Override
            public void setOnItemSelectListener(int position, @NotNull String selectedString) {
                Toast.makeText(JavaImplementation.this, searchableSpinner.getSelectedItem()+" "+ searchableSpinner.getSelectedItemPosition(), Toast.LENGTH_SHORT).show();
                if (isTextInputLayoutClicked)
                    textInputSpinner.getEditText().setText(selectedString);
                else
                    editTextSpinner.setText(selectedString);
            }
        });

        textInputSpinner.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTextInputLayoutClicked = true;
                searchableSpinner.setHighlightSelectedItem(true);
                searchableSpinner.show();
            }
        });

        editTextSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTextInputLayoutClicked = false;
                searchableSpinner.setHighlightSelectedItem(false);
                searchableSpinner.show();
            }
        });
    }
}
