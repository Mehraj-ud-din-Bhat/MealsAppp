package com.example.mealsapp.room

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "Meal")
class Meal {
       @PrimaryKey
      var mealID:Int=0
     var strMeal: String=""
    var strDrinkAlternate: String=""
    var strCategory: String=""
    var strArea: String=""
    var strInstructions: String=""
    var strMealThumb: String=""
    var strTags: String=""
    var strYoutube: String=""
    var strSource: String=""
    var strImageSource: String=""
    var strCreativeCommonsConfirmed:String=""
    var dateModified: String=""

    @Ignore
    var ingredient=ArrayList<Ingredient>()
    @Ignore
    var measures=ArrayList<Measure>()


}