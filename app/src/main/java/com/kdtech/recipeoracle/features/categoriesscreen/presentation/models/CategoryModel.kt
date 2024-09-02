package com.kdtech.recipeoracle.features.categoriesscreen.presentation.models

import java.util.UUID.randomUUID

data class CategoryModel(
    val id: String = randomUUID().toString(),
    val name: String = "Temp",
    val imageUrl: String = "https://t3.ftcdn.net/jpg/02/14/70/56/360_F_214705677_9uFguoP51vDwJ67BA2udnsjyoPwVMt4L.jpg"
)