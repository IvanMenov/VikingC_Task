# VikingC_Task


The task description you can find in TaskVikingC_backend/Test task Java _ Spring _ Camel.docx

Running the applications:

## Backend (TaskVikingC_backend)

Update the application.properties with the corresponding settings for the ftp server- host, port, user, password, FTP	directory.
  
Add the api keys for the Hotels, Weather, Covid stats REST services in application.properties as well.

When starting the application in the common way, without passing any arguments, it will only allow for manual mode of interaction, through the browser.
 
When you start up the app with the following arguments:
 
 	isAutomatic=true 
	time={time} (the time that the automated process should run at, for example time=8:30, or time=21:45)
	
	or
	
	isAutomatic=true 
	interval={interval} (Interval between process runs, this includes initial delay as well. Interval value should be in milliseconds, default is 60000 which is 1 minute)
 
you will enable automated mode so the app will run on a schedule based on the arguments values that you have provided, parsing data from data.csv and 

uploading the processed data to the ftp. Enabling automatic mode doesn't disable the manual mode, but only enhances the running application.

Since I am using Eclipse IDE for backend development, in order to pass the arguments I found the easiest way to be through 

Run -> Run Configuration -> Spring Boot App -> add the main class- TaskVikingCApplication and add the arguments in Override properties just as shown above. 
		
### REST API
 
 #### Get country data
 
	GET http://{host}:{port}/rest/data

##### Request:
```sh
Query Params:
	String country,
	String city,
	String date
```

##### Response:
```sh
CountryData in application/json format
```
#### Upload data to FTP

	[POST] http://{host}:{port}/rest/data/upload

##### Request Body:

```sh
CountryData in application/json format
```	
	
The applications is developed on Java 11 and Spring Boot version is 2.6.1.


## Front-end (taskvikingc_frontend):

Simple react application to interact with the backend.

Go to taskvikingc_frontend

npm install

npm start

When started the front -end application the endpoint is - http://{host}:{port} (defaults port is 3000)
