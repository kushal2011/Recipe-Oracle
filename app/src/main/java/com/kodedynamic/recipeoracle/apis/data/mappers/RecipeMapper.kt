package com.kodedynamic.recipeoracle.apis.data.mappers

import com.kodedynamic.recipeoracle.apis.data.models.RecipeDto
import com.kodedynamic.recipeoracle.apis.domain.models.RecipeModel
import com.kodedynamic.recipeoracle.common.Mapper
import java.util.UUID
import javax.inject.Inject

class RecipeMapper @Inject constructor(
    private val ingredientsMapper: IngredientsMapper,
    private val instructionMapper: InstructionMapper
) : Mapper<RecipeDto, RecipeModel> {
    override fun map(param: RecipeDto): RecipeModel {
        with(param) {
            return RecipeModel(
                averageRating = averageRating ?: 0.0,
                course = course.orEmpty(),
                cuisineType = cuisineType.orEmpty(),
                healthRating = healthRating ?: 0,
                recipeId = recipeId?.takeIf {
                    it.isNotEmpty()
                } ?: ("temp" + UUID.randomUUID().toString()),
                imageUrl = imageUrl.orEmpty(),
                isEggiterian = isEggiterian ?: false,
                recipeName = recipeName.orEmpty(),
                prepTime = prepTime ?: 0,
                ratingsCount = ratingsCount ?: 0,
                isJain = isJain ?: false,
                isNonVeg = isNonVeg ?: false,
                isVegan = isVegan ?: false,
                isVegetarian = isVegetarian ?: false,
                instructions = instructionDtos?.map { instructionMapper.map(it) } ?: emptyList(),
                ingredients = ingredientsDtos?.map { ingredientsMapper.map(it) } ?: emptyList()
            )
        }
    }
}
