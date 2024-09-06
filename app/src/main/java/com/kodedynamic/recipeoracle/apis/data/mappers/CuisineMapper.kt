package com.kodedynamic.recipeoracle.apis.data.mappers

import com.kodedynamic.recipeoracle.apis.data.models.CuisineDto
import com.kodedynamic.recipeoracle.apis.domain.models.CuisineModel
import com.kodedynamic.recipeoracle.common.Mapper
import javax.inject.Inject

class CuisineMapper @Inject constructor() : Mapper<CuisineDto, CuisineModel> {
    override fun map(param: CuisineDto): CuisineModel {
        with(param) {
            return CuisineModel(
                imageUrl = param.imageUrl.orEmpty(),
                cuisineType = param.cuisineType.orEmpty()
            )
        }
    }
}