package com.example.mealsapp.ui

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealsapp.R
import com.example.mealsapp.room.Meal
import com.example.mealsapp.viewmodels.MainViewModel

class SearchMeals : AppCompatActivity() {
    lateinit var  btnSaveMeals:Button
    lateinit var  btnGetMeals:Button
    lateinit var searchEt:EditText
    lateinit var recyclerView: RecyclerView
    lateinit var mProgressDialog:ProgressDialog
    lateinit var  viewModel:MainViewModel
    var mealsList=ArrayList<Meal>()
    lateinit var  mealsAdapter: MealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search_meals)
        viewModel=  ViewModelProvider(this).get(MainViewModel::class.java)
        btnSaveMeals=findViewById(R.id.saveMeals)
        btnGetMeals=findViewById(R.id.retrieveMeals)
        searchEt=findViewById(R.id.searchEt)
        recyclerView=findViewById(R.id.recyclerView)
        initAdapter()
        observeMealsLiveData()

        btnSaveMeals.setOnClickListener {
            saveMeals()
        }
        btnGetMeals.setOnClickListener {
            getMeals()
        }


    }

    /**
     *
     * RECYCLER
     */
    fun initAdapter()
    {
        mealsAdapter= MealsAdapter(mealsList)
        recyclerView.adapter=mealsAdapter
        recyclerView.layoutManager=LinearLayoutManager(this)

    }

    /***
     *
     *
     * OBSERVES THE LIVE DATA CHANGES
     */
    fun observeMealsLiveData()
    {
        viewModel.mealSearchLiveData.observe(this){
           hideProgress()
            if(it!=null)
            {
                hideProgress()
                mealsList.clear()
                mealsList.addAll(it)
                mealsAdapter.notifyDataSetChanged()


            }else{
                searchEt.error="NOT FOUND"
            }
        }
    }
    fun getMeals()
    {
        if(searchEt.text.isNotEmpty()) {
            mealsList.clear()
            mealsAdapter.notifyDataSetChanged()
            viewModel.searchMeals(searchEt.text.toString())
            showProgress("Retrieving  Meals")
        }else{
            searchEt.error="Required"
        }


    }


    fun showProgress(msg:String)
    {
        try {
            mProgressDialog= ProgressDialog(this)
            mProgressDialog.setTitle("Please wait")
            mProgressDialog.setMessage(msg)
            mProgressDialog.show()
        }catch (e:java.lang.Exception){}

    }

    fun hideProgress()
    {
        if(mProgressDialog!=null)
        {
            mProgressDialog.dismiss()
        }
    }

    fun saveMeals()
    {
        if(mealsList.size==0)
        {
            return
        }
        for(m in mealsList)
        {
            viewModel.saveMeal(m)
        }

        Toast.makeText(this,"Meals Saved",Toast.LENGTH_LONG).show()
    }
}