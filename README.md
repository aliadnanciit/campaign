# android-task

Android App using custom API

| Campaign Listing | Detail Campaign Screen |
| ------------- | ------------- |
| <img src="https://github.com/aliadnanciit/campaign/blob/main/screenshots/campaign_listing_screenshot.png" width="200" height="400">  | <img src="https://github.com/aliadnanciit/campaign/blob/main/screenshots/campaign_detail_screenshot.png" width="200" height="400">  |

## Description

A simple app that have following basic functionality.
-  Connects to the server and parse and fetch list of campaigns
-  Show only those campaigns whose name or description is not null or empty
-  Show list of all valid campaigns with image having aspect ratio 4:3 and show its name and description over image
-  Show two columns of campaign list in landscape mode otherwise show one campaign
-  If user click to any campaign then open in new full screen detail UI and user can go back to listing UI


## Tech Stack
- Kotlin - Programming language (Sealed classes for UI state handling)
- Dagger - Used to provide all dependencies to classes
- Retrofit with OkHttp3 - Parse http request/response API
- Glide - Show image loading
- RxJava - Reactive Programming (for managing threads)
- LiveData - use LiveData to see UI update with data changes.
- Data Binding - bind UI components in layouts to data sources
- Moshi - Advanced library for json parsing from http api response

## App arch components
- ViewModel: provide state to view for UI classes
- UseCase: Keep all business rules and logic
- Repository: Have all data fetching logic and provide date to use case of view model
- Service: Api that fetch data from server and provide to Repository

## Unit testing

Added unit tests for view model, useCase and repository.