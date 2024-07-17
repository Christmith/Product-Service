# Product-Service

Product-Service is a microservice application for managing product information. It provides RESTful APIs to perform CRUD operations on product data.

## Table of Contents

- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation](#installation)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

To run this project, you will need:

- Java 8 or later
- Maven 3.6.0 or later
- A MongoDB database

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/Christmith/product-service.git
   cd product-service
   
 2. **MongoDB Configuration**
    Update your `application.properties` or `application.yml` file with the following MongoDB configuration:
   
      ```properties
      spring.data.mongodb.uri=mongodb+srv://yohanchristmith:2024NewGen@mydatabase.po2pjlw.mongodb.net/?retryWrites=true&w=majority&appName=myDatabase
      spring.data.mongodb.database=Product
