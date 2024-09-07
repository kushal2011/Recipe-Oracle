package com.kodedynamic.recipeoracle.common

object FirebaseEvents {
    const val ON_DETAILS_CLICKED = "on_details_clicked"
    const val ON_SEE_ALL_CLICKED = "on_see_all_clicked"
    const val ON_CUISINE_CLICKED = "on_cuisine_clicked"
    const val ON_SHARE_RECIPE = "on_share_recipe"
    const val ON_CHAT_CLICKED = "on_chat_clicked"
    const val ON_DETAILS_SCREEN_ENTERED = "on_details_screen_entered"
    const val CHAT_STARTED = "chat_started"
    const val MESSAGE_SENT_BY_USER = "message_sent_by_user"
    const val REQUESTED_RECIPES_FROM_AI = "requested_recipes_from_ai"
    const val GENERATED_RECIPE_UPLOADED = "generated_recipe_uploaded"
    const val SEARCH_RECIPE_API_RETURNED_RESPONSE = "search_recipe_api_returned_response"
}

object ScreenNames {
    const val HOME_SCREEN = "home_screen"
    const val DETAILS_SCREEN = "details_screen"
    const val SEE_ALL_SCREEN = "see_all_screen"
    const val CATEGORIES_SCREEN = "categories_screen"
    const val CHAT_SCREEN = "chat_screen"
    const val SEARCH_SCREEN = "search_screen"
}

object EventParams {
    const val SCREEN_NAME = "screen_name"
    const val RECIPE_NAME = "recipe_name"
    const val RECIPE_ID = "recipe_id"
    const val CUISINE_TYPE = "cuisine_type"
    const val WIDGET_ID = "widget_id"
    const val TITLE = "title"
    const val ENTRY_FROM = "entry_from"
    const val MESSAGE_COUNT = "message_count"
    const val UPLOADED_NO_OF_RECIPES = "uploaded_no_of_recipes"
    const val SEARCH_TERM = "search_term"
    const val ITEMS_RETURNED = "items_returned"
}

object EventValues {
    const val ENTRY_FROM_IN_APP = "in_app"
    const val ENTRY_FROM_DEEPLINK = "deeplink"
    const val ENTRY_FROM_DETAILS = "details"
    const val ENTRY_FROM_BOTTOM_BAR = "bottombar"
}