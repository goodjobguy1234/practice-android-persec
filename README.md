## Setup instruction: 
due to api key is should not publish at public after clone project please,
at "local.properties" file at root set api_key look like this
`APOD_API_KEY="your api key here"`

## Architecture Choice
Due to time constraints, I utilized XML with ViewBinding for the UI and implemented MVVM architecture with Hilt for Dependency Injection.

Regarding state management, I implemented the 'happy case' and established a ViewState structure for loading, errors, and empty states to ensure the app is scalable. While the loading state is logic-ready, I did not have time to finalize the loading UI.

Please note that selecting a very early 'start date' can significantly increase the NASA API response time. Since the loading animation is not visually present (despite the state being active), it may momentarily appear as if the request is not being processed. Also, I didn't finish Detail page with Download and Share Picture functionality.

## Demo link:
https://drive.google.com/drive/folders/1aNSf1pGOo-XA0UHIBHYbJuZTOhyeCD-D?usp=sharing
