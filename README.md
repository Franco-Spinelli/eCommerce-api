# Ecommerce Spring Boot Project

## Description

This ecommerce project is built with Spring Boot, providing a platform where a single ADMIN user can manage product publication. CUSTOMER users can add products to their cart, place orders with or without shipping, and manage their shipping addresses.

>[!IMPORTANT]
>Terms of Use

 >**ADMIN User**: There can only be one ADMIN user in the system. This user is responsible for publishing products.
>**Creating the ADMIN User**: The ADMIN user must be created through the following endpoint:
>  - [POST http://localhost:8080/auth/signup-admin](http://localhost:8080/auth/signup-admin)
 > - Attempting to create more than one ADMIN user will result in an exception.
>- **Loading Products**: Products must be loaded using the following endpoints, only after the ADMIN user has been created:
>  - For a low substantial amount:
   > - [GET http://localhost:8080/api/products/fetch-products](http://localhost:8080/api/products/fetch-products)
>  - For a high substantial amount:
   > - [GET http://localhost:8080/api/products/fetch-more-products](http://localhost:8080/api/products/fetch-more-products)
 > - These endpoints retrieve data from two different APIs.

### Customer Functions

- **Adding Products to Cart**: Customers can add products to their cart.
- **Placing Orders**: Customers can place purchase orders, specifying whether shipping is required or not.
- **Managing Addresses**: Customers can manage their list of addresses for shipping, adding or modifying existing addresses.

## Technologies

- Java 11 or higher
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- OpenAPI (for API documentation)
