package com.example.mealsapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.mealsapp.R
import com.example.mealsapp.app.BaseApplication
import com.example.mealsapp.room.Meal

class MealsAdapter(val mealsList:ArrayList<Meal>) :RecyclerView.Adapter<MealsAdapter.MealViewHolder>() {

    class  MealViewHolder(val view:View):ViewHolder(view)
    {
        lateinit var  mealName:TextView
        lateinit var  thumbImg:ImageView
        lateinit var  ing:TextView
        lateinit var  measures:TextView

        fun bind(meal: Meal)
        {
            mealName=view.findViewById(R.id.mealName)
            ing=view.findViewById(R.id.ingre)
            measures=view.findViewById(R.id.measures)
            mealName.setText(meal.strMeal)
            thumbImg=view.findViewById(R.id.image)
            thumbImg.visibility=View.VISIBLE
            if(meal.strMealThumb.isNotEmpty()) {
                Glide.with(BaseApplication.context).load(meal.strMealThumb)
                    .into(thumbImg)


            }else{
                thumbImg.visibility=View.GONE
            }


            var ing="Ingredients:\n"
            for(i in 0..(meal.ingredient.size-1))
            {

                ing="$ing  ${meal.ingredient.get(i).ingredient}"
                if(meal.measures.size>i)
                {
                  ing="$ing: ${meal.measures.get(i).measure} ,"
                }
            }

            this.ing.setText(ing)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meal, parent, false)

        return MealViewHolder(itemView)
    }

    override fun getItemCount(): Int {
          return   mealsList.size
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {

        holder.bind(mealsList[position])


    }


}