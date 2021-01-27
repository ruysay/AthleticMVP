# Android Base Application
This project contains the base application upon which the Android take-home interview will be implemented.

![2020-12-30 12 10 57](https://user-images.githubusercontent.com/299434/103378717-1ef83700-4a98-11eb-97c1-a3ffd1238208.gif)


## Application Design
This application:
* is meant to run as a simple, standalone app that connects to a lightweight REST backend
* is 100% Kotlin, uses coroutines and some Flow Preview APIs
* is implemented with an MVP architecture that uses DataState, ViewState and Transformers to separate concerns (see the [mvp](https://github.com/TheAthleticInterview/android/tree/develop/app/src/main/java/com/theathletic/interview/mvp) package and inline docs for more info)
* uses Koin for dependency injection, Coil for image loading, Material Design where possible, Android Architecture Components for lifecycle management
* is incomplete! (see next section)

## Take Home Interview
This project is a starting point for interview candidates to display technical skills and design creativity.

As such, it intentionally contains:
* incomplete experiences or features
* bugs
* broken patterns
* leaks or inefficiencies

In addition to access to the repository, candidates will receive several tasks to implement in the codebase.

The panel interview will discuss application architecture and any implementations provided by the candidate.
