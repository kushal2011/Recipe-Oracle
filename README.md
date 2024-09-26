# Recipe App

## Overview
This Recipe App is a personal project aimed at providing a user-friendly interface to discover and explore a wide variety of recipes. The app features a feed-like home screen with recipes categorized under different sections such as Gujarati, Rajasthani, Quick and Easy, etc. Each section is presented in a horizontal scroll format, allowing users to easily browse through various recipes. 

## Screenshots

| Home Screen | Categories Screen | Chat with AI |
|:-----------:|:-----------------:|:------------:|
| <img src="https://github.com/user-attachments/assets/0df59321-04c6-4943-998f-7201298b8ecf" width="250"/> | <img src="https://github.com/user-attachments/assets/105df60f-d287-49ae-a649-b13ce29b2cc4" width="250"/> | <img src="https://github.com/user-attachments/assets/fb185aef-e8ff-432a-a960-308adf285a48" width="250"/> |

| Recipe Detail View | Search Feature |
|:------------------:|:--------------:|
| <img src="https://github.com/user-attachments/assets/eb5bdd60-c4d2-4251-b1d8-2d66a89a81b8" width="250"/> | <img src="https://github.com/user-attachments/assets/aa794a2f-73e7-48e0-80a7-23cbb6135f91" width="250"/> |




## Download
[Get the app on the Play Store](https://play.google.com/store/apps/details?id=com.kodedynamic.recipeoracle)


### Key Features

1. **Home Screen Feed**:
   - The home screen displays a series of horizontal scrolls, each representing a different category of recipes like "Gujarati," "Rajasthani," "Quick and Easy," etc.
   - Each category contains a list of recipes that users can scroll through horizontally.
   - A "See All" button is available for each category, redirecting users to a dedicated screen that displays all the recipes under that category.

2. **Search Functionality**:
   - Users can search for specific recipes using the search bar.
   - If the requested recipe is not available in the backend database, the app leverages AI models to generate the recipe data dynamically.
   - This newly generated recipe data is then updated in the backend for future use.

3. **AI-Powered Features**:
   - AI models, managed via Firebase Remote Config, are used to generate and display recipe data that isn't already available.
   - A unique feature called "Chat with AI" allows users to engage in a conversation with an AI model about any recipe, providing a more interactive and personalized user experience.

4. **Backend Integration**:
   - The app is integrated with a backend that stores all the recipe data.
   - Newly generated recipes by the AI are automatically updated in the backend, ensuring that the data remains current and comprehensive.

5. **Secret Keys Configuration**:
   - To run the app, users need to add the following keys in the `secret.properties` file:
     ```
     GEMENI_API_KEY=
     SECRET_KEY=
     OPENAI_API_KEY=
     ```
   - These keys are essential for the app to interact with various APIs and services.

## Future Development (TODO List)

- **Move Secret Key**: Migrate the secret keys from static storage to retrieving them from a secret manager in the backend for enhanced security.

- **Optional Login**: Implement an optional login feature to offer personalized recipe recommendations based on user preferences.

- **Week Planner**: Introduce a week planner that helps users plan their meals for the week. Based on the selected plan, notify users of the ingredients needed.

- **Ingredient-Based Recipe Suggestions**: Allow users to upload an image of ingredients they have, and suggest recipes that can be made using only those ingredients.

- **"Cook Now" Button**: For each recipe, include a prominent "Cook Now" button that initiates a step-by-step guided cooking mode.

- **Ratings and Reviews**: Display star ratings for each recipe and let users add their own ratings and reviews. (Backend implementation completed)

- **Cooking Timer**: Integrate a simple cooking timer that users can set with one tap for each recipe.

- **Home Feed Enhancements**:
  - Add a "Trending Recipes" section to showcase currently popular recipes.
  - Add a "Seasonal Recipes" section highlighting recipes suitable for the current season.
  - Add a "Kid-Friendly Recipes" section featuring easy-to-make and appealing recipes for children.
  - Add a "Recommended for You" section based on user preferences and behavior.
  - Add a "Popular This Week" section showcasing recipes that are trending this week.
  - Add a "Desserts" section to highlight sweet treats. (Backend implementation completed)

- **Notifications**: Implement a notification system to alert users about new recipes, reminders, and special offers.

- **Save Recipe Feature**: Allow users to save their favorite recipes for quick access later.

- **Fun Fact Component**: In the recipe detail view, add a component that displays a fun fact about the recipe or ingredient.

- **Advertisements**: Integrate ads into the app to generate revenue.

- **Premium Option**: Add an option for users to remove ads by purchasing a premium version of the app.

- **Search Filters**: Improve the search functionality by adding advanced filters like cuisine, preparation time, difficulty level, etc.

- **Auto Update**: Implement an auto-update feature to ensure that users always have the latest version of the app.


## Installation and Setup
1. Clone the repository to your local machine.
2. Add the required API keys in the `secret.properties` file:
```
  GEMENI_API_KEY="KEY_HERE"
  SECRET_KEY="KEY_HERE"
  OPENAI_API_KEY="KEY_HERE"

```
3. Download the `google-services.json` file from your Firebase Console and place it in the `app` directory of the project:

    ```bash
    /app/google-services.json
    ```
4. Build and run the app on your Android/iOS device or emulator.


## Contribution
This project is open for contributions. Feel free to fork the repository, make changes, and create a pull request. For major changes, please open an issue first to discuss what you would like to change.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact
For any questions or feedback, feel free to reach out via email at [kushal@kodedynamic.com].
