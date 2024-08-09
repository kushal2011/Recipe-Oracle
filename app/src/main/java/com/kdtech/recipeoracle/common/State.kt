package com.kdtech.recipeoracle.common

interface State {
    val id: String
}

inline fun <reified T> State?.to() = this as? T