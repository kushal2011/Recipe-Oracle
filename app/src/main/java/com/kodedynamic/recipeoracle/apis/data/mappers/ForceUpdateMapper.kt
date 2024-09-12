package com.kodedynamic.recipeoracle.apis.data.mappers

import com.kodedynamic.recipeoracle.apis.data.models.ForceUpdateDto
import com.kodedynamic.recipeoracle.apis.domain.models.ForceUpdateModel
import com.kodedynamic.recipeoracle.common.Mapper
import javax.inject.Inject


class ForceUpdateMapper @Inject constructor() : Mapper<ForceUpdateDto, ForceUpdateModel> {
    override fun map(param: ForceUpdateDto): ForceUpdateModel {
        return ForceUpdateModel(
            shouldForceUpdate = param.shouldForceUpdate ?: false,
            updateMessage = param.updateMessage.orEmpty()
        )
    }
}