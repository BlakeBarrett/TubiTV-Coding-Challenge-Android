# TubiTest
Tubi.tv's coding challenge.
The prompt is as follows:

**Tubi Android Project** 

**Project Overview:**  
Given an Endpoint that returns a list of movies in Json format, create a one page native android app that displays returned data on UI as a list, and also extra functionalities to manipulate data and reflect changes back to UI seamlessly. Please treat this as an open-ended project, rather than finish the requirement. A well-maintained code base is more sought after than quick-hacked code. At Tubi, we think code quality is of the utmost importance.

**Time Estimate:**  
This is intended to be a simple project which should approximately take a few hours of focused productive time to complete. If you find yourself spending more than that, feel free to cut down on scope or reach out for clarification.

**API Endpoint:**  
http://eng-assets.s3-website-us-west-2.amazonaws.com/fixture/movies.json

**Feature Requirement:**  
 * Fetch endpoint data, and display it as a list on UI. There are three attributes; “title”, “image”, and “id” of each movie object. Please display all information in each row.  
 * A “refresh” button that when clicked, re-fetches the data from the endpoint and updates on the UI.
 * A “filter” button that when clicked, sorts the data in an ascending order using id number.
 * Necessary unit tests.  

**Grading:**  
 * Code quality, cleanness, and reusability
 * Error handling, and edge case scenario fallback design
 * Overall code architecture design and extendibility 

**Third party Libraries:**  
Use of popular third-party libraries is allowed, however make your choices as if you were designing and building an application that you would need to maintain long-term.

**Project Submission:**  
Please package the project source code into a zip file, including manifest and gradle, and maintain android project structure. The project should be able to un-zip and ready to run without any structural integrity issues.
