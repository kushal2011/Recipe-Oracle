package com.kdtech.recipeoracle.apis.data.mappers

import com.kdtech.recipeoracle.apis.data.models.RecipeListDto
import com.kdtech.recipeoracle.apis.domain.models.RecipeModel
import com.kdtech.recipeoracle.common.Mapper
import javax.inject.Inject

class RecipeListMapper @Inject constructor(
    private val recipeMapper: RecipeMapper
) : Mapper<RecipeListDto, List<RecipeModel>> {
    override fun map(param: RecipeListDto): List<RecipeModel> {
        return param.recipesList?.map { recipeMapper.map(it) } ?: emptyList()
    }
}