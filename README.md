# TODO
- add save recipe feature
- handle image generation
- optional login for personalized recommendation
- premium feature (chat with recipe)
- add button to send user to youtube
- add week planner , based on plan let user know what ingredients would be needed
- share with all the details of recipe and link to playstore app
- get all ingredients from image and suggest recipe that can be made using only that
- user can request a variant of the recipe where it can be made without a particular ingridient
- "Cook Now" Button: For each recipe, include a prominent "Cook Now" button that starts a step-by-step guided cooking mode.
- Ratings and Reviews: Display star ratings for each recipe and let users quickly add their own ratings.
- Cooking Timer: Integrate a simple timer function for recipes that users can set with one tap.
- In HomeFeed
  - add Top Rated Recipes
  - add Trending Recipes
  - add Seasonal Recipes
  - add Kid-Friendly Recipes (Recipes that are easy to make and appealing to children)
  - add Recommended for You (based on user preferences and behavior)
  - add Popular This Week
  - add Healthy Recipes (Nutritionally balanced recipes for health-conscious users)
  - add Desserts
  - add Asian Delights (Could include Chinese, Japanese, Thai, etc.)
  - add Quick & Easy (30-minute meals across cuisines)
- 



# BUGS
- app crash when launching without internet

# Sample Data for home screen

{
"widgets": [
{
"id": "aaa",
"widget_type": "type1",
"title": "recipes",
"shouldShowSeeAll": true,
"data": [
{
"name": "Classic Tomato Spaghetti",
"prepTime": "30 minutes",
"image": "",
"ingredients": [
{
"name": "Spaghetti",
"quantity": "200g"
}
],
"instructions": [
"Cook the spaghetti in a large pot of boiling salted water until al dente.",
"Heat the olive oil in a pan and sauté garlic until fragrant."
],
"isVegan": true,
"isVegetarian": true,
"isEggiterian": false,
"isNonVeg": false,
"isJain": false
}
]
}
]
}