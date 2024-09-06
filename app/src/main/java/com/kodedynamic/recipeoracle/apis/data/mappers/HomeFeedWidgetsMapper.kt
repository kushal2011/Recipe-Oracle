package com.kodedynamic.recipeoracle.apis.data.mappers

import com.kodedynamic.recipeoracle.apis.data.models.HomeFeedWidgetsDto
import com.kodedynamic.recipeoracle.apis.domain.models.HomeFeedWidgetsModel
import com.kodedynamic.recipeoracle.common.Mapper
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