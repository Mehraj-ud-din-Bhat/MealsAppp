package com.example.mealsapp.room

import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Ingredients")
class Ingredient {
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
    var mealID:Int=0
    var ingredient:String=""
}