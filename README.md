# LocationTrackerApplication
Sample Android Application to keep track the current location of the device in a background service and share it the Activities in the application

It tracks the location of the device using a background service with Google LocationServices API Client and share the location to the Activity using a broadcast receiver. The background service will keep running as long as the application exists in the memory and sticks to the notification drawer. 

The service handles the following functionalities, 

<ul>
Feature:
Application should allow to start and end a trip
Location will be tracked for the duration of the trip.
Location should be tracked at a regular time interval of 5 seconds
Locations should be described as an JSON output.

Sample output:
{
"trip_id": "1",
"start_time":"2021-04-06T10:10:10Z",
"end_time": "2021-04-06T11:11:11Z",
"locations":[
{
"latitude": 1.235,
"longitide": 8.984,
"timestamp": "2021-04-06T10:10:10Z",
"accuracy": 10.9
},
{
"latitude": 1.235,
"longitide": 8.984,
"timestamp": "2021-04-06T10:15:10Z",
"accuracy": 10.0
}
]
}

</ul>

Result

![track_location_device_devdeeds](https://raw.githubusercontent.com/gautamlook/LocationTracker/main/device-2021-09-19-121719.png)

![track_location_device_devdeeds](https://raw.githubusercontent.com/gautamlook/LocationTracker/main/device-2021-09-19-121742.png)
