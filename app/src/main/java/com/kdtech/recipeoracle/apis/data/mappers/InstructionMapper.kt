package com.kdtech.recipeoracle.apis.data.mappers
import com.kdtech.recipeoracle.apis.data.models.InstructionDto
import com.kdtech.recipeoracle.apis.domain.models.InstructionModel
import com.kdtech.recipeoracle.common.Mapper
import javax.inject.Inject

class InstructionMapper @Inject constructor() : Mapper<InstructionDto, InstructionModel> {
    override fun map(param: InstructionDto): InstructionModel {
        with(param) {
            return InstructionModel(
                instructionId = instructionId.orEmpty(),
                recipeId = recipeId.orEmpty(),
                step = step.orEmpty()
            )
        }
    }
}
