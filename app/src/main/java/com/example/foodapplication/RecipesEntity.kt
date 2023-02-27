package com.example.foodapplication

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodapplication.models.FoodRecipe
import com.example.foodapplication.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(var foodRecipe: FoodRecipe) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}