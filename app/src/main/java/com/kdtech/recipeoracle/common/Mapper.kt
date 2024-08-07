package com.kdtech.recipeoracle.common

interface Mapper<I, O> {
    fun map(param: I): O
}
