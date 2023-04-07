package com.example.mealsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mealsapp.R
import com.example.mealsapp.viewmodels.MainViewModel

class MainView : AppCompatActivity() {
    lateinit var  viewModel:MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         viewModel=  ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.insertMeals()
    }
}