package com.example.mealsapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Measures")
class Measure {
     @PrimaryKey(autoGenerate = true)
     var id:Int=0

     var mealID:Int=0
     var measure:String=""
}