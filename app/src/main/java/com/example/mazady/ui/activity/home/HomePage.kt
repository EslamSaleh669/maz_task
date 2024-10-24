package com.example.mazady.ui.activity.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mazady.R
import com.example.mazady.data.model.PropertiesModel
import com.example.mazady.data.network.response.*
import com.example.mazady.ui.activity.secondpage.SecondPage
import com.example.mazady.ui.adapter.MainCatAdapter
import com.example.mazady.ui.adapter.OptionChildAdapter
import com.example.mazady.ui.adapter.OptionsAdapter
import com.example.mazady.ui.adapter.SubCatAdapter
import com.example.mazady.util.AutoDispose
import com.example.mazady.util.MyApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Named


class HomePage : AppCompatActivity() {

    private lateinit var categoryAutoComplete: AutoCompleteTextView
    private lateinit var subCategoryAutoComplete: AutoCompleteTextView
    private lateinit var mainLayout: LinearLayout
    private lateinit var outputText: TextView
    private lateinit var nextBtn: Button
    private var selectedPropertiesList = ArrayList<PropertiesModel>()


    @Inject
    @field:Named("home")
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }


    private val autoDispose: AutoDispose = AutoDispose()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        (application as MyApplication).appComponent?.inject(this)
        autoDispose.bindTo(this.lifecycle)
        initVariables()
        getMainCategories();

        findViewById<TextView>(R.id.btn_confirm).setOnClickListener {
            if (selectedPropertiesList.size > 0) {


                viewData()

            } else {
                Toast.makeText(this, "Please select value", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun initVariables() {

        categoryAutoComplete = findViewById<AutoCompleteTextView>(R.id.main_cat_auto_complete)
        subCategoryAutoComplete = findViewById<AutoCompleteTextView>(R.id.sub_cat_auto_complete)
        mainLayout = findViewById<LinearLayout>(R.id.main_layout)
        outputText = findViewById<TextView>(R.id.output_text)
        nextBtn = findViewById<Button>(R.id.next_btn)

    }

    private fun getMainCategories() {
        autoDispose.add(
            viewModel.getMainCategories().observeOn(AndroidSchedulers.mainThread()).subscribe({

                initCategoriesAutoComplete(it.data!!.categories)

            }, {


            })
        )
    }


    private fun initCategoriesAutoComplete(categories: List<CategoriesItem?>?) {


        categoryAutoComplete.threshold = 0

        val arrayAdapter =
            MainCatAdapter(
                applicationContext,
                R.layout.support_simple_spinner_dropdown_item,
                categories
            )
        categoryAutoComplete.setAdapter(arrayAdapter)




        categoryAutoComplete.setOnClickListener {
            categoryAutoComplete.showDropDown()

        }


        categoryAutoComplete.setOnFocusChangeListener { v, hasFocus ->

            if (hasFocus) {
                hideKeyboard(this)
                categoryAutoComplete.showDropDown()

            }

        }





        categoryAutoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                hideKeyboard(this)
                categoryAutoComplete.clearFocus()
                categoryAutoComplete.dismissDropDown()
                val selectedObject = parent!!.getItemAtPosition(position) as CategoriesItem
                categoryAutoComplete.setText(selectedObject.name.toString())
                mainLayout.removeAllViews()
                selectedPropertiesList.clear()
                val propModel = PropertiesModel()
                propModel.propertyName = "Category"
                propModel.propertySelectedOptionName = selectedObject.name.toString()
                propModel.propertySelectedOptionId = selectedObject.id
                selectedPropertiesList.add(propModel)


                initSubCategoriesAutoComplete(selectedObject.children!!)

            }


    }


    private fun initSubCategoriesAutoComplete(subCategories: List<ChildrenItem?>) {

        subCategoryAutoComplete.threshold = 0

        val arrayAdapter =
            SubCatAdapter(
                applicationContext,
                R.layout.support_simple_spinner_dropdown_item,
                subCategories
            )
        subCategoryAutoComplete.setAdapter(arrayAdapter)




        subCategoryAutoComplete.setOnClickListener {
            subCategoryAutoComplete.showDropDown()

        }


        subCategoryAutoComplete.setOnFocusChangeListener { v, hasFocus ->

            if (hasFocus) {
                hideKeyboard(this)
                subCategoryAutoComplete.showDropDown()

            }

        }



        subCategoryAutoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                hideKeyboard(this)
                subCategoryAutoComplete.clearFocus()
                subCategoryAutoComplete.dismissDropDown()
                val selectedObject = parent!!.getItemAtPosition(position) as ChildrenItem
                subCategoryAutoComplete.setText(selectedObject.name.toString())
                mainLayout.removeAllViews()
                val propModel = PropertiesModel()
                propModel.propertyName = "Sub Category"
                propModel.propertySelectedOptionName = selectedObject.name.toString()
                propModel.propertySelectedOptionId = selectedObject.id
                selectedPropertiesList.add(propModel)

                getProperties(selectedObject.id!!)

            }

    }


    private fun getProperties(id: Int) {

        autoDispose.add(
            viewModel.getProperties(id).observeOn(AndroidSchedulers.mainThread()).subscribe({


                for (item in it.data!!) {

                    createDynamicDropDown(item!!.name!!, item.options)


                }


            }, {


            })
        )

    }


    private fun createDynamicDropDown(label: String, options: List<OptionsItem?>?) {

        val editedOptions = appendOtherOption(options)

        val propertyLabelParameters: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        propertyLabelParameters.setMargins(0, 50, 0, 6)


        val otherTextParameters: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        otherTextParameters.setMargins(0, 35, 0, 6)


        val propertyLabel = TextView(this)
        propertyLabel.layoutParams = propertyLabelParameters
        propertyLabel.textSize = 16f
        propertyLabel.text = label

//

        val otherEdittext = EditText(this)

        otherEdittext.textSize = 17f

        otherEdittext.setPadding(35, 35, 35, 35);
        otherEdittext.background =
            ContextCompat.getDrawable(applicationContext, R.drawable.edittext_background)

        otherEdittext.hint = "Specify Here"
        otherEdittext.layoutParams = otherTextParameters
        otherEdittext.visibility = View.GONE


        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {


            }
        }

        otherEdittext.addTextChangedListener(textWatcher)


        //


        val optionAutoComplete = AutoCompleteTextView(this)
        optionAutoComplete.threshold = 0
        optionAutoComplete.textSize = 17f

        optionAutoComplete.setPadding(35, 35, 35, 35);
        optionAutoComplete.background =
            ContextCompat.getDrawable(applicationContext, R.drawable.edittext_background)


        optionAutoComplete.setCompoundDrawablesRelativeWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_arrow_down,
            0
        );

        val arrayAdapter =
            OptionsAdapter(
                applicationContext,
                R.layout.support_simple_spinner_dropdown_item,
                editedOptions
            )
        optionAutoComplete.setAdapter(arrayAdapter)

        optionAutoComplete.setOnClickListener {
            optionAutoComplete.showDropDown()

        }
        optionAutoComplete.setOnFocusChangeListener { v, hasFocus ->

            if (hasFocus) {
                hideKeyboard(this)
                optionAutoComplete.showDropDown()

            }
        }





        optionAutoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                hideKeyboard(this)
                optionAutoComplete.clearFocus()
                optionAutoComplete.dismissDropDown()
                val selectedObject = parent!!.getItemAtPosition(position) as OptionsItem
                optionAutoComplete.setText(selectedObject.name.toString())
                if (selectedObject.name.toString() == "Other") {
                    otherEdittext.visibility = View.VISIBLE
                }

                val propModel = PropertiesModel()
                propModel.propertyName = label
                propModel.propertySelectedOptionName = selectedObject.name.toString()
                propModel.propertySelectedOptionId = selectedObject.id
                selectedPropertiesList.add(propModel)



                if (selectedObject.child == true) {

                    getOptionChild(selectedObject.id!!)


                }

            }

        mainLayout.addView(propertyLabel)
        mainLayout.addView(optionAutoComplete)
        mainLayout.addView(otherEdittext)





    }

    private fun appendOtherOption(options: List<OptionsItem?>?): List<OptionsItem> {
        val optionsItem = OptionsItem()
        optionsItem.id = 0
        optionsItem.name = "Other"
        optionsItem.child = false

        val editedList = ArrayList<OptionsItem>()

        editedList.add(optionsItem)

        for (item in options!!) {

            editedList.add(item!!)


        }

        return editedList

    }



    private fun getOptionChild(optionId:Int){


        val propertyLabelParameters: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        propertyLabelParameters.setMargins(0, 50, 0, 6)





        autoDispose.add(
            viewModel.getOptionChild(optionId)
                .observeOn(AndroidSchedulers.mainThread()).subscribe({


                    var childOptionLabel = TextView(this)
                    var childOptionAutoComplete = AutoCompleteTextView(this)


                    childOptionLabel = TextView(this)
                    childOptionLabel.layoutParams = propertyLabelParameters
                    childOptionLabel.textSize = 16f
                    childOptionLabel.text = it.data!![0]!!.name


                    childOptionAutoComplete = AutoCompleteTextView(this)
                    childOptionAutoComplete.threshold = 0
                    childOptionAutoComplete.textSize = 17f

                    childOptionAutoComplete.setPadding(35, 35, 35, 35);
                    childOptionAutoComplete.background =
                        ContextCompat.getDrawable(
                            applicationContext,
                            R.drawable.edittext_background
                        )


                    childOptionAutoComplete.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        0,
                        0,
                        R.drawable.ic_arrow_down,
                        0
                    );

                    val arrayAdapter2 =
                        OptionChildAdapter(
                            applicationContext,
                            R.layout.support_simple_spinner_dropdown_item,
                            it.data[0]!!.options
                        )
                    childOptionAutoComplete.setAdapter(arrayAdapter2)

                    childOptionAutoComplete.setOnClickListener {
                        childOptionAutoComplete.showDropDown()
                        hideKeyboard(this)

                    }
                    childOptionAutoComplete.setOnFocusChangeListener { v, hasFocus ->

                        if (hasFocus) {
                            hideKeyboard(this)
                            childOptionAutoComplete.showDropDown()

                        }
                    }


                    childOptionAutoComplete.onItemClickListener=
                    AdapterView.OnItemClickListener { parent, view, position, id ->
                        hideKeyboard(this)
                        childOptionAutoComplete.clearFocus()
                        childOptionAutoComplete.dismissDropDown()
                        val selectedObject2 =
                            parent!!.getItemAtPosition(position) as OptionChildItem
                        childOptionAutoComplete.setText(selectedObject2.name.toString())



                        val optionModel = PropertiesModel()
                        optionModel.propertyName = it.data[0]!!.name
                        optionModel.propertySelectedOptionName =
                            selectedObject2.name.toString()
                        optionModel.propertySelectedOptionId = selectedObject2.id
                        selectedPropertiesList.add(optionModel)
                    }


                    mainLayout.addView(childOptionLabel)
                    mainLayout.addView(childOptionAutoComplete)

                }, {
                    println(it.toString())


                })
        )


    }
    private fun viewData() {

        var output = "the output is: \n"

        for (item in selectedPropertiesList) {
            output += "${item.propertySelectedOptionName} selected from ${item.propertyName}  \n"
        }

        outputText.text = output

        nextBtn.visibility = View.VISIBLE
        nextBtn.setOnClickListener {


            startActivity(
                Intent(this, SecondPage::class.java)
            )


        }
    }

    private fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}