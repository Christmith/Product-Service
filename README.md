# Product-Service

Product-Service is a microservice application for managing product information. It provides RESTful APIs to perform CRUD operations on product data.

## Table of Contents

- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Running the Tests](#running-the-tests)
- [Built With](#built-with)
- [Contributing](#contributing)
- [Versioning](#versioning)
- [Authors](#authors)
- [License](#license)
- [Acknowledgments](#acknowledgments)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

To run this project, you will need:

- Java 8 or later
- Maven 3.6.0 or later
- A MySQL database
- A MongoDB database

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/Christmith/product-service.git
   cd product-service
   
 2. **Configure the databases:**

     MySQL Configuration:
    
     Create a database named product_service and update the application.properties file located in src/main/resources with your MySQL database configuration.
     Make sure to replace yourusername and yourpassword with your actual database username and password.  
    
      ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/product_service
        spring.datasource.username=yourusername
        spring.datasource.password=yourpassword
        spring.jpa.hibernate.ddl-auto=update
  
MongoDB Configuration

Update your `application.properties` or `application.yml` file with the following MongoDB configuration:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/product_service
