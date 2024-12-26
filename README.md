# METU NCC Societies API

## Overview
The **METU NCC Societies API** is a backend service designed to power the METU NCC Societies App, enabling functionalities such as user authentication, society management, announcements, and reservation handling. Built using Spring Boot, the API ensures secure, scalable, and efficient communication between the frontend and backend systems.

---

## Features

### Authentication
- **Signup and Login APIs**: Facilitates secure user registration and authentication.
- **JWT Authentication**: Provides token-based access control for role-based functionalities.

### Society Management
- **Get Societies**: Retrieve all societies or a specific society's details.
- **Add New Society**: Admin functionality to add societies to the system.
- **User Societies**: Fetch societies a user has joined.

### Announcements
- **Create Announcements**: Allows society presidents to share updates and events.
- **Fetch Announcements**: Retrieve announcements for specific societies or all societies.
- **Update/Delete Announcements**: Modify or remove existing announcements.

### Room Reservations
- **Submit Requests**: Society presidents can request venue reservations.
- **Approve/Reject Requests**: Admins can manage reservation requests to avoid conflicts.
- **View Requests**: Presidents can track the status of their requests.

### Join Requests
- **Submit Join Requests**: Users can request to join societies.
- **Approve/Reject Join Requests**: Society presidents handle membership requests.

### AWS Integration
- **AWS S3**: Handles file uploads for announcement images.
- **AWS RDS**: PostgreSQL database for secure and scalable data storage.
- **AWS IAM**: Manages access control for AWS services.
- **AWS EC2**: Hosts the backend application.

---

## Technologies Used

### Backend
- **Framework**: Spring Boot (Java)
- **Database**: PostgreSQL (hosted on AWS RDS)
- **Cloud Services**: AWS S3, AWS EC2, AWS IAM
- **Dependencies**:
  - Spring Security
  - Spring Data JPA
  - AWS SDK for S3

---

## Deployment
The backend API is hosted on AWS EC2 and integrated with other AWS services:
- **AWS EC2**: Hosts the Spring Boot application.
- **AWS RDS**: Stores user, society, announcement, and reservation data.
- **AWS S3**: Stores announcement images and other media files.

Public API endpoint: [http://18.192.66.96/api](http://18.192.66.96/api)

---

## Setup and Installation

### Prerequisites
- JDK 11 or higher.
- PostgreSQL database.
- AWS account with necessary services configured.

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/cagatay-goz/society-app-api.git
   ```
2. Navigate to the project directory:
   ```bash
   cd society-app-api
   ```
3. Configure the application properties:
   - Update `src/main/resources/application.properties` with your database credentials and AWS configurations.

   Example:
   ```properties
   spring.datasource.url=jdbc:postgresql://<RDS-ENDPOINT>:5432/<DATABASE-NAME>
   spring.datasource.username=<DB-USERNAME>
   spring.datasource.password=<DB-PASSWORD>

   cloud.aws.credentials.access-key=<AWS-ACCESS-KEY>
   cloud.aws.credentials.secret-key=<AWS-SECRET-KEY>
   cloud.aws.region.static=<AWS-REGION>
   ```

4. Build the project:
   ```bash
   mvn clean install
   ```
5. Run the application:
   ```bash
   java -jar target/society-app-api-0.0.1-SNAPSHOT.jar
   ```
6. Access the API via:
   ```
   http://localhost:8080/api
   ```

---

## API Endpoints

### Authentication
- **POST /api/auth/signup**: User registration.
- **POST /api/auth/login**: User login and JWT token retrieval.

### Societies
- **GET /api/societies**: Fetch all societies.
- **GET /api/societies/{id}**: Fetch specific society details.
- **POST /api/societies**: Add a new society (Admin only).

### Announcements
- **GET /api/announcements**: Fetch all announcements.
- **POST /api/announcements**: Create a new announcement.
- **PUT /api/announcements/{id}**: Update an announcement.
- **DELETE /api/announcements/{id}**: Delete an announcement.

### Room Reservations
- **POST /api/reservations**: Submit a reservation request.
- **GET /api/reservations**: Fetch all reservation requests (Admin only).
- **PUT /api/reservations/{id}**: Approve or reject a reservation request.

### Join Requests
- **POST /api/join-requests**: Submit a join request.
- **GET /api/join-requests**: Fetch all pending join requests.
- **PUT /api/join-requests/{id}**: Approve or reject a join request.

---

## Contributors
- **Ekrem Cagatay Goz**: Authentication, JWT integration, and deployment on AWS EC2.
- **Engin Eray Kabalak**: Society, Announcement, and Reservation APIs. AWS S3 integration.
- **Haya Arabi Katibi**: Database configuration and RDS integration.

---

## License
This project is open-source and available under the [MIT License](LICENSE).

---

## References
- [AWS RDS Documentation](https://docs.aws.amazon.com/rds/)
- [AWS S3 Documentation](https://docs.aws.amazon.com/s3/)
- [AWS EC2 Documentation](https://docs.aws.amazon.com/ec2/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
