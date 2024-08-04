Backend Project Summary
The backend of the BookStore application is built using Spring Boot. It handles user registration, authentication, and book management functionalities.

Features
User Registration with OTP Verification:
When a user registers, an OTP is sent to their mobile number for verification using Twilio.
Upon successful verification, the user can log in.

User Authentication:
Implemented using Spring Security.
Upon login, a JWT token is generated and returned to the user for accessing secured endpoints.

Book Management:
Uses Spring Data JPA for database interactions with an H2 in-memory database.

Technologies Used
Spring Boot
Spring Security
Spring Boot JPA
Spring Boot Web
H2 Database
Twilio for OTP services
JWT for authentication

Contact
If you have any questions or suggestions, feel free to reach out to me at siddheshgupta596@gmail.com.
