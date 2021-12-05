# VikingC_Task


The task description you can find in Test task Java _ Spring _ Camel.docx

Running the applications:

1 Backend (TaskVikingC_backend)

Update the application.json with the corresponding settings for the ftp server- host, port, user, password, FTP 	directory to upload to.
  
For the Hotels, Weather, Covid stats I have already plugged in some api-keys that I subscribed with(free subscription), so you don't have to add those.

When starting the application in the common way, without passing any arguments, it will only allow for manual mode of interaction, through the browser.
 
When you start up the app with the following arguments:
 
 isAutomatic=true 
  and either
 time={time}  (the time that the automated process should run at, for example time=8:30, or time=21:45)
   or
 interval={interval}  - (Interval between process runs, this includes initial delay as well. Interval value should be in milliseconds, default is 60000 	which is 1 minute)
 
you will enable automated mode so the app will run on a schedule based on the arguments values that you have provided, parsing data from data.csv and 
 
uploading the processed data to the ftp. Enabling automatic mode doesn't disable the manual mode, but only enhances the running application.

Since I am using Eclipse IDE for backend development, in order to pass the arguments I found the easiest way to be through 

Run -> Run Configuration -> Spring Boot App -> add the main class and add the arguments in Override properties like this:
		
isAutomatic=true
time={time}

or 
isAutomatic=true
interval={interval}
 
  REST endpoints:

  GET http://{host}:{port}/rest/data
	request:
		query params:
			String country,
			String city,
			String date
	response:
		CountryData in application/json format

  POST http://{host}:{port}/rest/data/upload
	request:
		body CountryData data in application/json format
	
	
The applications is developed on Java 11 and Spring Boot version is 2.6.1.


2. Front-end (taskvikingc_frontend):

Simple react application to interact with the backend.

Go to taskvikingc_frontend

npm install

npm start

When started the front -end application the endpoint is - http://{host}:{port} (defaults port is 3000)
