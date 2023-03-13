#PME BLOG TASK

*A simple blog api where users can sign up, create posts and view all posts created.*
*Non-Logged-In Users can only view posts created.*

On users' sign-up, an OTP is sent to the email provided which must be verified.
Failure to verify email via OTP will always lead to Authorization Exception when trying to 
access a Protected Resource.

###Tech Stack Used:

Java, SpringBoot, MySQL DB

Author: *Sir Darey*


##List of End Points

GET `/api/blog/posts`

GET `/api/blog/posts/{id}`

GET `/api/blog/users/posts` [Protected]

POST  `/api/blog/posts` [Protected]

POST `/api/blog/users`

POST `/api/blog/users/verify_email` 

Application runs on port: 8201 (when run locally)
***Full Swagger Documentation url: http://localhost:8201/swagger-ui/index.html***

##STEPS TO RUN APPLICATION

1. Clone this repo to your Java IDE
2. Run the application
3. Hit the documentation url and follow the instructions outlined