# PrivacyRadarVSpring
Our app is divided into Java and XML. The Java files are the backend files that dictate how and what happens when interacting with the UI. The XML files are the layout files for the UI as well as any drawables, text strings, and colors that we used throughout the app.

JAVA code path: app/src/main/java/rk/android/app/privacydashboard
Activities: are where the backend functionality for the layouts are defined.
Account: This is where user’s accounts are created, signed in, and signed out.
Intro: This is where the app walkthrough is run.
Log: This is where Logs are made, stored, and retrieved for users.
Main: This is the backend for the apps dashboard.
Settings: Users will find the permissions controls here and app settings.
Splash: This controls the loading screen while the app is loading.
Constants: App strings and other data is defined here.
Helper: This holds the app's listener as well as creates the callback loop.
Async: The listener is set up here.
Seekbar: The repeat listener is set up here.
Manager: More app strings and preferences are set here.
Model: Sets up the callback for presenting the app and permissions timeline.
Service: This is where when permissions are detected it properly identifies the app and permission and stores accordingly.
Util: Holds the files that create the Pie chart and other utilities.
Views: Sets up smaller classes for other scripts to call on.

XML code path: app/src/main/res
Anim: This folder holds the code for animations that the front end uses.
Color: Holds a color switcher for when different colors are selected.
Drawable: This has anything from png files, UI backgrounds, to xml vector files.
Font: This is where different fonts are set up that are used throughout the app.
Layout: Here is where the UI wireframe is created.
Mipmap: Different sizeable icons and backgrounds are set up here.
Values: This has the colors, text strings, and switchable attributes. 
