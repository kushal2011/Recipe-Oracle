package com.kdtech.recipeoracle.apis.data.mappers

import com.kdtech.recipeoracle.apis.data.models.CategoriesDto
import com.kdtech.recipeoracle.apis.domain.models.CategoriesModel
import com.kdtech.recipeoracle.common.Mapper
import javax.inject.Inject

class CategoriesMapper @Inject constructor(
    private val cuisineMapper: CuisineMapper
) : Mapper<CategoriesDto, CategoriesModel> {
    override fun map(param: CategoriesDto): CategoriesModel {
        with(param) {
            return CategoriesModel(
                cuisines = cuisines?.map { cuisineMapper.map(it) } ?: emptyList()
            )
        }
    }
}
