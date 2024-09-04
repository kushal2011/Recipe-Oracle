# TODO
- share with all the details of recipe and link to playstore app
- add ads 
- add option to remove ads if user buys premium
- add search screen
  - add empty/no data found
- add see all screen
- add toasts
- add loaders

# TODO (Long term)
- optional login for personalized recommendation
- add week planner , based on plan let user know what ingredients would be needed
- get all ingredients from image and suggest recipe that can be made using only that
- "Cook Now" Button: For each recipe, include a prominent "Cook Now" button that starts a step-by-step guided cooking mode.
- Ratings and Reviews: Display star ratings for each recipe and let users quickly add their own ratings. (Done in BE)
- Cooking Timer: Integrate a simple timer function for recipes that users can set with one tap.
- In HomeFeed
  - add Trending Recipes
  - add Seasonal Recipes
  - add Kid-Friendly Recipes (Recipes that are easy to make and appealing to children)
  - add Recommended for You (based on user preferences and behavior)
  - add Popular This Week
  - add Desserts (done in BE)
  - add Asian Delights (Could include Chinese, Japanese, Thai, etc.)
  - snacks , side dish
- Notifications
- add save recipe feature

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