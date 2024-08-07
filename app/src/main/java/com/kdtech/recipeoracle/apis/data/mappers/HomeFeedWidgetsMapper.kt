package com.kdtech.recipeoracle.apis.data.mappers

import com.kdtech.recipeoracle.apis.data.models.HomeFeedWidgetsDto
import com.kdtech.recipeoracle.apis.domain.models.HomeFeedWidgetsModel
import com.kdtech.recipeoracle.common.Mapper
import javax.inject.Inject

class HomeFeedWidgetsMapper @Inject constructor(
    private val widgetMapper: WidgetMapper
) : Mapper<HomeFeedWidgetsDto, HomeFeedWidgetsModel> {
    override fun map(param: HomeFeedWidgetsDto): HomeFeedWidgetsModel {
        with(param) {
            return HomeFeedWidgetsModel(
                widgetsList = widgetsList?.map { widgetMapper.map(it) } ?: emptyList()
            )
        }
    }
}