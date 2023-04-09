package com.example.mealsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mealsapp.room.Ingredient
import com.example.mealsapp.room.Meal
import com.example.mealsapp.room.MealsDatabase
import com.example.mealsapp.room.Measure
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MainViewModel : ViewModel() {
    val database = MealsDatabase.getInstance()
    var mealSaveStatus=MutableLiveData<Boolean>()


    fun insertMeals() {
        /**
         *
         * ADD RANDOM MEALS IN DB from search c
         */
        GlobalScope.launch {
            val url = URL("https://www.themealdb.com/api/json/v1/1/search.php?f=c")
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.connect()

            if (conn.responseCode == 200) {
                val inputStream: InputStream = BufferedInputStream(conn.content as InputStream)
                val sb: StringBuilder = java.lang.StringBuilder()

                var ch: Int
                while (inputStream.read().also { ch = it } != -1) {
                    sb.append(ch.toChar())
                }

                var str = sb.toString()


                formatJsonArray(str)


            }


        }

    }

    fun formatJsonArray(string: String) {
        try {
            val jsonObject = JSONObject(string)
            addInDatabase((jsonObject.getJSONArray("meals")))
        }catch (e:java.lang.Exception){
            mealSaveStatus.postValue(false)
        }



    }


    fun addInDatabase(list: JSONArray) {

        for (i in 0 until list.length()) {
            val meal: JSONObject = list.get(i) as JSONObject

            if (meal != null) {
                val mealModel = Meal()

                if (meal.has("idMeal")) {
                    if(meal.get("idMeal")!=null) {
                        mealModel.mealID = (meal.get("idMeal") as String).toInt()
                    }
                }

                if (meal.has("strMeal")) {
                    if(meal.get("strMeal")!=null) {
                        mealModel.strMeal = (meal.get("strMeal") as String)
                    }
                }

                if (meal.has("strDrinkAlternate")) {

                    if(meal.get("strDrinkAlternate")!=null) {
                      //  mealModel.strDrinkAlternate = (meal.get("strDrinkAlternate") as String)
                    }
                }

                if (meal.has("strCategory")) {
                    if(meal.get("strCategory")!=null) {
                        mealModel.strCategory = (meal.get("strCategory") as String)
                    }
                }



                if (meal.has("strArea")) {
                    if(meal.get("strArea")!=null) {
                        mealModel.strArea = (meal.get("strArea") as String)
                    }
                }


                if (meal.has("strInstructions")) {
                    if(meal.get("strInstructions")!=null) {
                        mealModel.strInstructions = (meal.get("strInstructions") as String)
                    }
                }
                if (meal.has("strMealThumb")) {
                    if(meal.get("strMealThumb")!=null) {
                        mealModel.strMealThumb = (meal.get("strMealThumb") as String)
                    }
                }

                if (meal.has("strYoutube")) {
                    if(meal.get("strYoutube")!=null) {
                        mealModel.strYoutube = (meal.get("strYoutube") as String)
                    }
                }


                val ingredients=ArrayList<Ingredient>()

               for( i in 1..20)
                {
                   if(meal.has("strIngredient$i"))
                   {
                       val ingredient=Ingredient()
                       ingredient.mealID=mealModel.mealID
                       if(meal.get( "strIngredient$i")!=null) {
                           if(meal.get("strIngredient$i")  is String) {
                               ingredient.ingredient = meal.get("strIngredient$i") as String
                               ingredients.add(ingredient)
                           }
                       }
                   }

                }
                var measures=ArrayList<Measure>()
                for( i in 1..20)
                {
                    if(meal.has("strMeasure$i"))
                    {
                        val measure=Measure()
                        measure.mealID=mealModel.mealID
                        if(meal.get( "strMeasure$i")!=null) {
                            if(meal.get("strMeasure$i")  is String) {
                                measure.measure = meal.get("strMeasure$i") as String
                               measures.add(measure)
                            }
                        }
                    }

                }

                database.mealDAO().addMeal(mealModel)
                database.mealDAO().addMeasures(measures)
                database.mealDAO().addIngredients(ingredients)



            }


        }

        mealSaveStatus.postValue(true)
    }


    /**
     *
     * SEARCH MEALS SECTION
     *
     *
     */

     var mealSearchLiveData=MutableLiveData<ArrayList<Meal>>()

    fun searchMeals(searchQuery:String) {
        /**
         *
         * ADD RANDOM MEALS IN DB from search c
         */
        GlobalScope.launch {
            val url = URL("https://www.themealdb.com/api/json/v1/1/search.php?s=$searchQuery")
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.connect()

            if (conn.responseCode == 200) {
                val inputStream: InputStream = BufferedInputStream(conn.content as InputStream)
                val sb: StringBuilder = java.lang.StringBuilder()

                var ch: Int
                while (inputStream.read().also { ch = it } != -1) {
                    sb.append(ch.toChar())
                }

                var str = sb.toString()

                if(str!=null && str.length>0)
                {
                    formatSearchResponse(str)
                }else{
                    mealSearchLiveData.postValue(null)
                }


            }


        }

    }

  private  fun formatSearchResponse(string: String) {
        val jsonObject = JSONObject(string)
        if(jsonObject.has("meals"))
        {
            if(jsonObject.get(("meals")) is JSONArray) {
                mealSearchLiveData.postValue(parseJsonArray(jsonObject.getJSONArray("meals")))
            }else{
                mealSearchLiveData.postValue(null)
            }
        }


    }


   private fun parseJsonArray(list: JSONArray):ArrayList<Meal>{
        var mealList=ArrayList<Meal>()
        for (i in 0 until list.length()) {
            val meal: JSONObject = list.get(i) as JSONObject

            if (meal != null) {
                val mealModel = Meal()

                if (meal.has("idMeal")) {
                    if(meal.get("idMeal")!=null) {
                        mealModel.mealID = (meal.get("idMeal") as String).toInt()
                    }
                }

                if (meal.has("strMeal")) {
                    if(meal.get("strMeal")!=null) {
                        mealModel.strMeal = (meal.get("strMeal") as String)
                    }
                }

                if (meal.has("strDrinkAlternate")) {

                    if(meal.get("strDrinkAlternate")!=null) {
                        //  mealModel.strDrinkAlternate = (meal.get("strDrinkAlternate") as String)
                    }
                }

                if (meal.has("strCategory")) {
                    if(meal.get("strCategory")!=null) {
                        mealModel.strCategory = (meal.get("strCategory") as String)
                    }
                }



                if (meal.has("strArea")) {
                    if(meal.get("strArea")!=null) {
                        mealModel.strArea = (meal.get("strArea") as String)
                    }
                }


                if (meal.has("strInstructions")) {
                    if(meal.get("strInstructions")!=null) {
                        mealModel.strInstructions = (meal.get("strInstructions") as String)
                    }
                }
                if (meal.has("strMealThumb")) {
                    if(meal.get("strMealThumb")!=null) {
                        mealModel.strMealThumb = (meal.get("strMealThumb") as String)
                    }
                }

                if (meal.has("strYoutube")) {
                    if(meal.get("strYoutube")!=null) {
                        mealModel.strYoutube = (meal.get("strYoutube") as String)
                    }
                }


                val ingredients=ArrayList<Ingredient>()

                for( i in 1..20)
                {
                    if(meal.has("strIngredient$i"))
                    {
                        val ingredient=Ingredient()
                        ingredient.mealID=mealModel.mealID
                        if(meal.get( "strIngredient$i")!=null) {
                            if(meal.get("strIngredient$i")  is String) {
                                ingredient.ingredient = meal.get("strIngredient$i") as String
                                ingredients.add(ingredient)
                            }
                        }
                    }

                }
                var measures=ArrayList<Measure>()
                for( i in 1..20)
                {
                    if(meal.has("strMeasure$i"))
                    {
                        val measure=Measure()
                        measure.mealID=mealModel.mealID
                        if(meal.get( "strMeasure$i")!=null) {
                            if(meal.get("strMeasure$i")  is String) {
                                measure.measure = meal.get("strMeasure$i") as String
                                measures.add(measure)
                            }
                        }
                    }

                }

               mealModel.ingredient=ingredients
               mealModel.measures=measures
                mealList.add(mealModel)



            }


        }

        return  mealList
    }


    fun saveMeal(mealModel:Meal)
    {
        try {
            database.mealDAO().addMeal(mealModel)
            database.mealDAO().addMeasures(mealModel.measures)
            database.mealDAO().addIngredients(mealModel.ingredient)
        }catch (e:java.lang.Exception){}



    }

    /**
     *
     *
     * SEARCH MEALS
     */


    fun searchMeal(string: String)
    {
        GlobalScope.launch {
            var list=ArrayList<Meal>()
            list=database.mealDAO().searchMeals(string) as ArrayList<Meal>
            for(m in list)
            {
                m.ingredient=database.mealDAO().getIngredients(m.mealID) as ArrayList<Ingredient>
                m.measures=database.mealDAO().getMeasures(m.mealID) as ArrayList<Measure>
            }
            mealSearchLiveData.postValue(list)


        }
    }



//    private fun formatResponse(str:String){
//        try {
//            var stringArray=str.split(System.lineSeparator())
//
//            var list=ArrayList<String>()
//            var lineCounter=0;
//            var newString=""
//
//            for(s in stringArray){
//                if(s.isEmpty())
//                {
//                    lineCounter++
//                    if(lineCounter==3)
//                    {
//                        var a=  newString.get(newString.length-1)
//
//                        if(a.toString()==",")
//                        {
//                            newString=newString.dropLast(1)
//                        }
//
//                        var str="{ $newString }"
//                        list.add(str)
//                        lineCounter=0;
//                        newString=""
//
//                    }
//                }else{
//                    newString="$newString $s"
//                }
//
//            }
//
//            val json=ArrayList<JSONObject>()
//            for(m in list)
//            {
//                json.add(JSONObject(m))
//
//            }
//
//        }catch (e:java.lang.Exception){
//            Log.e("MEAL PARSE ERRPR",""+e.message)
//        }
//
//
//
//
//    }

}