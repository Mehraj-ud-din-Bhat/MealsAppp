package com.example.mealsapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import java.nio.charset.CodingErrorAction.REPLACE

@Dao
interface MealDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMeal(meal: Meal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addIngredients(list: ArrayList<Ingredient>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMeasures(list: ArrayList<Measure>)

}