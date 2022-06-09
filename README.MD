# Earthquake Monitor

The main goal of this application is to monitor earthquakes in an area defined by the user or provided by the real time location of the user.

## Details
#### Used API:
* https://earthquake.usgs.gov/fdsnws/event/1/ - data for earthquakes
* Google Maps API
#### Activities:
* the app will list with a brief summary of all earthquakes that took place for a define timerange (1/2/4/6/8/10 weeks)
* the app will provide an approximate overview of the zone on a map (e.g. Google Maps) where the earthquake is occuring or occurred 
#### Notifications:
* the app will give some short tips to the user in case of a earthquake using notifications
#### Shared preference:
* the app will store user preferencies (e.g. use dark theme/enamble notifications/timerange for earthquake feed) 
#### Async tasks and Threads:
* requests to different endpoint to extract data will be implemented asynchronously on threads (e.g. using Retrofit)
#### Job Schedular
* the app will retrieve data from endpoints using a JobService sheduled to make requests periodically
#### Broadcast services:
* tasks which will require communication between different app components will be implemented using broadcast services
* (e.g. Sending retrieved data by the JobService to a Activity/Fragment)
## Author
Sîrbu Sandu SAS 
