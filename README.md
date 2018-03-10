# SWEngAndroidApp
Android application cooking app source code for the Software Engineering project.

# Python Server
When running the app, you must run the python server for the example recipe and presentation to display. 

1. On windows, please refer to the [Flask documentation](http://flask.pocoo.org/docs/0.12/installation/#pip-and-setuptools-on-windows). 

2. You'll also need to change the IP address the Java code refer to in `PythonClient.java` at the constant to your local IP on your network, for example:
```java
 public static final String IP_ADDR = "192.168.0.20";
```   
