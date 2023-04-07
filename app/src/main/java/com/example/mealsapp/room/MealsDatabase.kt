package com.example.mealsapp.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mealsapp.app.BaseApplication

@Database(entities = [Meal::class,Ingredient::class,Measure::class], version = 2)
abstract  class MealsDatabase  :RoomDatabase(){
    abstract fun mealDAO(): MealDAO
    companion object{

        fun getInstance():MealsDatabase{
            return Room.databaseBuilder(
                BaseApplication.context,
                MealsDatabase::class.java,
                 "MEALS_DB").fallbackToDestructiveMigration()
                .allowMainThreadQueries().build()
        }
    }




}