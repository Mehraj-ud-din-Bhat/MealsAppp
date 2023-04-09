package com.example.mealsapp.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.nio.charset.CodingErrorAction.REPLACE

@Dao
interface MealDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMeal(meal: Meal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addIngredients(list: ArrayList<Ingredient>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMeasures(list: ArrayList<Measure>)

    @Query("SELECT * from Ingredients where mealID=:mealID")
    fun getIngredients(mealID:Int):List<Ingredient>
    @Query("SELECT * from Measures where mealID=:mealID")
    fun getMeasures(mealID:Int):List<Measure>
    @Query("SELECT M.* FROM Meal M JOIN Ingredients I on I.mealID=M.mealID JOIN Measures MM ON M.mealID=MM.mealID Where " +
            "M.strMeal like '%' || :s || '%' OR " +
            "M.strArea like '%' || :s || '%' OR " +
            "M.strCategory like '%' || :s || '%' OR " +
            "M.strInstructions like '%' || :s || '%' OR " +
            "MM.measure like '%' || :s || '%' OR " +
            "I.ingredient like '%' || :s || '%'")
    fun searchMeals(s:String):List<Meal>

}