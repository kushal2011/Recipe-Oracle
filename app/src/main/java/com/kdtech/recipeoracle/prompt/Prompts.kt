package com.kdtech.recipeoracle.prompt

object Prompts {
    fun getPromptForIngredients() : String {
        return "Please generate a list of food ingredients in JSON format. " +
                "Each item in the list should contain the following fields: " +
                "\"name\" (the name of the ingredient), \"image_url\" (a URL to an image of the ingredient), " +
                "and \"calories\" (the number of calories per 100 grams of the ingredient). " +
                "The list should include a variety of common ingredients such as potato, tomato, onion, and others. " +
                "Here is the structure for each item:\n" +
                "{\n" +
                "  \"name\": \"ingredient_name\",\n" +
                "  \"image_url\": \"http://example.com/image.jpg\",\n" +
                "  \"calories\": 0\n" +
                "}\n\n" +
                "Response should only contain JSON and nothing else."

    }
}