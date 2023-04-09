package com.example.mealsapp.ui

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.mealsapp.R
import com.example.mealsapp.viewmodels.MainViewModel

class MainView : AppCompatActivity() {
    lateinit var  viewModel:MainViewModel
    lateinit var  addMeals:Button
    lateinit var  searchMealsByIng:Button
    lateinit var  searchMealsLocal:Button
    lateinit var mProgressDialog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel=  ViewModelProvider(this).get(MainViewModel::class.java)
        addMeals=findViewById(R.id.addMealToDb)
        searchMealsByIng=findViewById(R.id.searchForMealsNetwork)
        searchMealsLocal=findViewById(R.id.searchforMealsLocal)

        addMeals.setOnClickListener {
            showProgress("Saving a few meals for you...")
            viewModel.insertMeals()
        }

        searchMealsByIng.setOnClickListener {

            startActivity(Intent(MainView@this,SearchMeals::class.java))
        }


        searchMealsLocal.setOnClickListener {

            startActivity(Intent(MainView@this,SearchMealsLocal::class.java))
        }


        viewModel.mealSaveStatus.observe(this){
            hideProgress()
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
}