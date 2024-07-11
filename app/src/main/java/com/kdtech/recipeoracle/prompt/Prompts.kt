package com.kdtech.recipeoracle.prompt

object Prompts {
    fun getPromptForIngredients() : String {
        return "Please generate a list of food ingredients in JSON format. " +
                "Each item in the list should contain the following fields: " +
                "\"name\" (the name of the ingredient), \"image_url\" (It should be empty string), " +
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

    fun getPromptForRecipes(
        searchText: String = ""
    ) : String {
        val searchData = if (searchText.isNotEmpty()) {
            "which has $searchText in its name"
        } else {
            ""
        }
        return "Could you provide a list of recipes $searchData formatted as JSON?" +
                " Each recipe should include the following details:" +
                "\n1. **Name of the Recipe**: A descriptive title for the dish." +
                "\n2. **Image of the Recipe**: A URL link to an image of the prepared dish." +
                "\n3. **Ingredients**: An array of objects where each object contains:" +
                "\n   - **Name of Ingredient**: The specific name of the ingredient." +
                "\n   - **Quantity Required**: The amount of the ingredient needed, including units." +
                "\n4. **Instructions for the Recipe**: An array of strings, each string detailing a step in the recipe." +
                "\n5. **Dietary Information**: Boolean values for each of the following dietary categories:" +
                "\n   - **isVegan**: True if the recipe is suitable for vegans, otherwise False." +
                "\n   - **isVegetarian**: True if the recipe is suitable for vegetarians, otherwise False." +
                "\n   - **isEggiterian**: True if the recipe includes eggs but no other animal products, otherwise False." +
                "\n   - **isNonVeg**: True if the recipe includes any kind of meat, poultry, or seafood, otherwise False." +
                "\n   - **isJain**: True if the recipe adheres to Jain dietary restrictions (no onions, garlic, root vegetables, etc.), otherwise False." +
                "\n\nHere’s an example of what I expect in the JSON output:" +
                "\n\n[" +
                "\n    " +
                "{\n        " +
                "\"name\": \"Classic Tomato Spaghetti\"," +
                "\n        \"image\": \"https://example.com/images/tomato-spaghetti.jpg\"," +
                "\n        \"ingredients\": [" +
                "\n            {\"name\": \"Spaghetti\", \"quantity\": \"200g\"}," +
                "\n            {\"name\": \"Tomatoes\", \"quantity\": \"5, diced\"}," +
                "\n            {\"name\": \"Olive oil\", \"quantity\": \"2 tbsp\"}," +
                "\n            {\"name\": \"Garlic\", \"quantity\": \"2 cloves, minced\"}" +
                "\n        ],\n        \"instructions\": [" +
                "\n            \"Cook the spaghetti in a large pot of boiling salted water until al dente.\"," +
                "\n            \"Heat the olive oil in a pan and sauté garlic until fragrant.\"," +
                "\n            \"Add tomatoes and cook until the sauce thickens.\"," +
                "\n            \"Toss the spaghetti with the sauce and serve hot.\"\n        ]," +
                "\n        \"isVegan\": true," +
                "\n        \"isVegetarian\": true," +
                "\n        \"isEggiterian\": false," +
                "\n        \"isNonVeg\": false," +
                "\n        \"isJain\": false" +
                "\n    }" +
                "\n]" +
                "\n\nPlease generate a list with 3 different recipes that include the above details." +
                "Response should only contain JSON and nothing else." +
                " Thank you"
    }
}