package com.kdtech.recipeoracle.apis.data.mappers

import com.kdtech.recipeoracle.apis.data.models.WidgetDto
import com.kdtech.recipeoracle.apis.domain.models.WidgetModel
import com.kdtech.recipeoracle.common.Mapper
import javax.inject.Inject

class WidgetMapper @Inject constructor(
    private val recipeMapper: RecipeMapper
) : Mapper<WidgetDto, WidgetModel> {
    override fun map(param: WidgetDto): WidgetModel {
        with(param) {
            return WidgetModel(
                widgetId = widgetId.orEmpty(),
                shouldShowSeeAll = shouldShowSeeAll ?: false,
                title = title.orEmpty(),
                widgetType = widgetType.orEmpty(),
                recipesList = recipesList?.map { recipeMapper.map(it) } ?: emptyList()
            )
        }
    }
}