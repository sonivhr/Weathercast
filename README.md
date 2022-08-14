# WeatherForecast

How to clone the project?
- Open Android Studio
- Go to File menu -> select New -> click on Project from Version Control
- Select Repository URL, select Git from Version Control drop down
- Put following in URL link text box: https://github.com/sonivhr/WeatherForecast.git
- Choose Directory where you want to save this project
- Click Clone

How to build and run the project?
- If you are using new Android Studio Arctic Fox, then you should be able to run the project without an issue.
- If you are using other version of Android Studio then note down it's version by clicking on Help menu, click About and note down the version
- Next, go to File menu, select Project Structure, click on Project pane, make sure to select Android Gradle Plugin Version same as your Android Studio Version (the one that you noted down in above step).
- The project should build fine and you should be able to run the project.

What is implemented in project?
- This application uses OpenWeatherMap API and shows weather of next five days (with 3 hour periodic weather rows). By default, it shows London city weather, moreover you can use search by city feature to check out weather of any city.
- For now it shows temperature as max/min degree celsius, date-time and weather description.

Future implementation scope?
- DONE: For now city name is hardcoded, a search box can be provided so the user can check out the weather of any city in the world.
- DONE: Implement Unit test cases for the logic code coverage
- DONE: UI test cases to verify functional behavior of the application
