package com.kodedynamic.recipeoracle.apis.data.mappers

import com.kodedynamic.recipeoracle.apis.data.models.IngredientsDto
import com.kodedynamic.recipeoracle.apis.domain.models.IngredientsModel
import com.kodedynamic.recipeoracle.common.Mapper
import javax.inject.Inject

class IngredientsMapper @Inject constructor() : Mapper<IngredientsDto, IngredientsModel> {
    override fun map(param: IngredientsDto): IngredientsModel {
        with(param) {
            return IngredientsModel(
                imageUrl = imageUrl.orEmpty(),
                ingredientId = ingredientId.orEmpty(),
                ingredientName = ingredientName.orEmpty(),
                quantity = quantity.orEmpty(),
                recipeId = recipeId.orEmpty()
            )
        }
    }
}
