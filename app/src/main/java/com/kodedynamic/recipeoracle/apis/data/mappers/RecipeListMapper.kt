package com.kodedynamic.recipeoracle.apis.data.mappers

import com.kodedynamic.recipeoracle.apis.data.models.RecipeListDto
import com.kodedynamic.recipeoracle.apis.domain.models.RecipeModel
import com.kodedynamic.recipeoracle.common.Mapper
import javax.inject.Inject

class RecipeListMapper @Inject constructor(
    private val recipeMapper: RecipeMapper
) : Mapper<RecipeListDto, List<RecipeModel>> {
    override fun map(param: RecipeListDto): List<RecipeModel> {
        return param.recipesList?.map { recipeMapper.map(it) } ?: emptyList()
    }
}