# Tallerify

Tallerify is a "Spotify" Android client, for academic uses (its a work for the subject "Taller 2" in Facultad de Ingenieria UBA).

[![Coverage Status](https://coveralls.io/repos/github/saantiaguilera/fiuba-taller-II-tallerify-android/badge.svg?branch=develop)](https://coveralls.io/github/saantiaguilera/fiuba-taller-II-tallerify-android?branch=develop)

[![Build Status](https://travis-ci.org/tallerify/fiuba-taller-II-tallerify-android.svg?branch=master)](https://travis-ci.org/tallerify/fiuba-taller-II-tallerify-android)

## Application

<img src="http://i.imgur.com/gYYaFDJ.png" height="320"> <img src="http://i.imgur.com/cJN7kZj.png" height="320"> <img src="http://i.imgur.com/QoRc3QX.png" height="320"> <img src="http://i.imgur.com/JbPkJ8u.png" height="320">

## Documentation

For a full documentation of all the clases, you can see the [javadoc documentation](http://htmlpreview.github.io/?https://github.com/tallerify/fiuba-taller-II-tallerify-android/blob/develop/javadoc/index.html)

## Structure

FMP is a monolythic single-activity native android app. It uses a lot of state-of-art dependencies (eg RxJava) and has some architecture and design patterns applied to. FMP supports any phone with at least api level 16 and with internet and gps access.

Currently it lacks of loading views (while a request is processing) and retries (in case it fails). This will be available for future iterations outside of the assigned scope.

### Dependencies

Dependencies can be divided between project and application.

#### Project

1. **Travis**: We are using Travis as tool for our CI.
2. **Coveralls / Jacoco**: We are using coveralls for test coverage and jacoco as a tool for packaging and uploading the hits/misses
3. **Gradle**: Gradle 3.5 was picked as DSL and buildtool system for the project because of its simplicity in android environments and overall
4. **Proguard**: Proguard is being used at release for reducing the dex count and avoid the multidex. For debug we are using the built-in shrinker android provides
5. **LeakCanary**: Leak Canary is available for debug builds to notify about possible leaks while developing
6. **Stetho**: Stetho is also available for debug builds as a interceptor
7. **Junit / Robolectric**: For testing we are using Junit as framework and robolectric for mimicking the android system

#### Application

1. **SupportLibrary**: We have included design and cardview for UI purposses. CustomTabs was also added since latest facebook versions have it as a provided dependency (they might use it if the phone is able)
2. **Rx**: RxJava and RxAndroid are core for the functionality of the application. Both features are in every corner of the app making easier the flow and movements of data. Also RxBindings was added for view bidings, RxLocation for gms locations, RxPermissions for simple permission requests to the system and RxPaparazzo for interactions with the phone gallery.
3. **Retrofit**: For networking requests, retrofit was used.
4. **Conductor**: For architectural structuring, we have decided to work with Conductor, to avoid the complex and depth lifecycles android has.
5. **Coordinators**: For design structuring, we have decided to create a MVP/MVI with Coordinators.
6. **Toasty**: Toasty its just added for notifying errors when they appear. If error handling appears in a future scope, this library will most likely dissappear
7. **Facebook**: Facebook dependency for login in
8. **Firebase**: Firebase authentication and chat dependencies are incorporated
9. **Fresco**: For image loading

### Packages

For understand a bit easier the packages of the application, we will list them and minimally provide information about what they contain.

- **com.u.tallerify**: Contains root project classes, that are agnostic to every other classes and the application itself. For example, the application class
- **com.u.tallerify.annotations**: Annotations useful for proguard or compile time
- **com.u.tallerify.change_handler**: Animations for views/controllers
- **com.u.tallerify.contract**: Contracts that presenters and views implements, to limit the scope and visibility they can interact with
- **com.u.tallerify.controller**: Where all the controllers are. You can think of the controllers as the "activities" or "fragments". 
- **com.u.tallerify.entry_points**: The app entry points, this are the activities/services/broadcast_receivers the application has and can be interacted with as entry points
- **com.u.tallerify.model**: Models/Dtos of the application.
- **com.u.tallerify.model.entity**: Model entities. The difference between the `model` ones is that entities must have a unique identifier attached to each instance.
- **com.u.tallerify.networking**: Classes used for networking purposes, eg the RestClient or a Deserializer
- **com.u.tallerify.networking.interactors**: Interactors are middlewares between the external abstractions (eg. services) and the front-end application. When a class wants to interact with the outside, they have to do it with an Interactor. This doesnt apply only for backend, but also for example for a Facebook login or a location request to the system.
- **com.u.tallerify.networking.services**: Services are sets of abstractions of the backend endpoints, you can think of as containers or repositories of request contracts someone can perform
- **com.u.tallerify.presenter**: The presenters. Presenters are used to decouple the bussiness logic from the views.
- **com.u.tallerify.supplier**: Suppliers are classes that supply on demand presenters or views for a given model in a particular context (lets say in a horizontal list where songs/artists/albums/playlists are displayed)
- **com.u.tallerify.utils**: Random magic bag of utility static classes
- **com.u.tallerify.view**: The views of the application

## Contributing

Please fork and create a PR, we will review it as soon as we can

Dependencies should download automatically. Please ensure you are running with the latest android and google repository versions and you have gradle 3.5 / android 2.3

For building a release apk please run `./gradlew app:assembleRelease -PFirebaseServiceAccountFilePath=path`
