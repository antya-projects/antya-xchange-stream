Build the application
=========================

1) Navigate to the project root directory
2) At console, run ./gradlew clean build to build the war file

Run the application
===========================
1) Build the war file as stated above
2) Deploy the war file to any web container of choice

Run on > Tomcat 8
============================
The application provides an easy way to deploy on tomcat provided you have CATALINA_HOME setup
Simply run ./gradlew tomcatDist from the project root folder and it will build and deploy application to localhost:8080 tomcat instance.

Health endpoint
==============================
http://localhost:8080/antya-xchange-stream/health


