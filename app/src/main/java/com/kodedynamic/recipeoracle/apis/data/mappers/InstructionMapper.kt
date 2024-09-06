package com.kodedynamic.recipeoracle.apis.data.mappers
import com.kodedynamic.recipeoracle.apis.data.models.InstructionDto
import com.kodedynamic.recipeoracle.apis.domain.models.InstructionModel
import com.kodedynamic.recipeoracle.common.Mapper
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
