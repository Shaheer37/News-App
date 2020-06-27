# News Application
This is a very simple (for now) News app that takes most popular news for the day from [News API](https://newsapi.org/) and displays them.

The main purpose of the application is to showcase and experiment with Android technologies.

<img src="./images/NewsList.png" width = "264" height = "464"/> <img src="./images/NewsDetails.png" width = "264" height = "464"/> <img src="./images/MasterDetailFlow.png" width = "469" height = "264"/>

## Implementation Details
The solution is implemented using **[Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)** and the structure can be seen in the following diagram;

![](./images/Architecture.png)

The implementation contains following features;
1. **Master Detail Flow** for screen wider than 600dp
2. **Dark Theme** support for Android 10
3. Paginated News data using **Jetpack Paging 3** with **Coroutine Flows** 
4. The user can always swipe down on the list to refresh the data from the API.
5. All the project dependencies are injected using **Dagger2**
6. The data is fetched from the [News API](https://newsapi.org/) using **Retrofit2**
7. Json parsing is done using Google GSON.
8. The project also contains **Unit** and **Instrumented tests** implemented using **JUnit4 Androidx tests, Espresso and Mockito**.

## Project Setup
1. Clone the project and run it in Android studio.
2. Go to [News API Page](https://newsapi.org/) and Sign up\Register.
3. Copy the generated API key from your account details page.
4. Store the copied API key in your computers Environment Variables by name: __NewsApiKey__
5. Build and Run the application \m/.
