package com.leo.searchablespinnerexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.leo.searchablespinner.SearchableSpinner
import com.leo.searchablespinner.interfaces.OnItemSelectListener
import kotlinx.android.synthetic.main.searchable_spinner_implementation.*

class KotlinImplementation : AppCompatActivity() {

    var isTextInputLayoutClicked: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.searchable_spinner_implementation)
        val searchableSpinner = SearchableSpinner(this)
        searchableSpinner.windowTitle = "SEARCHABLE SPINNER"
        searchableSpinner.onItemSelectListener = object : OnItemSelectListener {
            override fun setOnItemSelectListener(position: Int, selectedString: String) {
                Toast.makeText(
                    this@KotlinImplementation,
                    "${searchableSpinner.selectedItem}  ${searchableSpinner.selectedItemPosition}",
                    Toast.LENGTH_SHORT
                ).show()
                if (isTextInputLayoutClicked)
                    textInputSpinner.editText?.setText(selectedString)
                else
                    editTextSpinner.setText(selectedString)
            }
        }

        //Setting Visibility for views in SearchableSpinner
//        searchableSpinner.searchViewVisibility = SearchableSpinner.SpinnerView.GONE
//        searchableSpinner.negativeButtonVisibility = SearchableSpinner.SpinnerView.GONE
//        searchableSpinner.windowTitleVisibility = SearchableSpinner.SpinnerView.GONE

        val androidVersionList = arrayListOf(
            "Cupcake",
            "Donut",
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
            "10"
        )

        searchableSpinner.setSpinnerListItems(androidVersionList)
        textInputSpinner.editText?.keyListener = null
        textInputSpinner.editText?.setOnClickListener {
            isTextInputLayoutClicked = true
            searchableSpinner.show()
        }

        editTextSpinner.keyListener = null
        editTextSpinner.setOnClickListener {
            searchableSpinner.highlightSelectedItem = false
            isTextInputLayoutClicked = false
            searchableSpinner.show()
        }
    }
}
