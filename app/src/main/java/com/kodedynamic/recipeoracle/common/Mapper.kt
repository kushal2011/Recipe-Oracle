package com.kodedynamic.recipeoracle.common

interface Mapper<I, O> {
    fun map(param: I): O
}
