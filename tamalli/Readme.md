# Tamalli
### Backend Rest API with Spring Boot, MySQL and MongoDB

PyMEs are an important part of the Mexican economy and are spread in each part of the country.
Although they are usually small, there are a lot of them and are closely linked to the generation 
of employment. The coronavirus pandemic forced PyMEs to implement digital channels to survive what 
also meant new opportunities of grow and expansion for them.

I this project a Rest API was created to manage the  basic shopping process of  a food e-commerce. A registered user has an order and can add or remove products to it,  and change  the status of the  order  from in progress 
to paid. Also, we can  add, modify the price and remove products in the database and add a description to 
each one of them if it is needed. Both  payment and delivery processes are out the scope of this project.

#### This project is developed with:
- Spring Boot 2.7.7
- Maven 3.8.6
- JDK 11

#### Requirements
You must have installed on your computer:
- Java 11
- MySQL Workbench 8.0
- Mongo 6.0.3
- IntelliJ IDEA
- Postman

#### Steps to run the application on your computer
- Download or clone this repository.

- In MySQL create a database called `tamalli_ecommerce`
- In Mongo DB create a database called `tamalli`  
  - Create a collection  called `product_description`
- In the `application.properties` file update the `user`, `password` and the `url`  for  accessing both databases
- Open IntelliJ IDE and import the project
- Run `TamalliApplication`, this  class is in src->java->com.accenture.tamalli. Tables will be created automatically. Also,  MySQL DB has initial data to test.
- Now you can make your requests using postman

#### Tables (MySQL)
Customers One-To- Many relationship with Orders 

| Table:       | Customers      |
|--------------|----------------|
| customer id  | Long  PK       |
| first name   | String         |
| last name    | String         |
| password     | String         |
| email        | String         |
| address      | String         |
| phone number | String         |
| orders       | List<Order> FK |

orders One-To- Many relationship with Order details

| Table:          | Orders               |
|-----------------|----------------------|
| order id        | Long  PK             |
| paid  name      | Boolean              |
| purchase date   | DateTime             |
| total cost      | Big Decimal          |
| customer        | Customer FK          |
| order detail FK | List<OrderDetail> FK |


| Table:                | Order details |
|-----------------------|---------------|
| detail order id       | Long  PK      |
| product line          | String        |
| product ordered       | String        |
| product price ordered | Big Decimal   |
| quantity ordered      | int           |
| order id              | Order FK      |
| product id            | Product FK    |

| Table:           | Products    |
|------------------|-------------|
| product id       | Long  PK    |
| price            | Big Decimal |
| product name     | String      |
| product type     | String      |

The following tables inherit from Products table with a JOINED strategy:

| Table:           | Tamales  |
|------------------|----------|
| product id       | Long  FK |
| weight kilograms | double   |

| Table:           | Drinks   |
|------------------|----------|
| product id       | Long  FK |
| capacity litters | double   |

#### Collection (Mongo)
| Table:           | Products |
|------------------|----------|
| product id       | Long  PK |
| description head | String   |
| description body | String   |
| url image        | String   |
# Endpoints

## Customer
### Get all customer (GET)

http://localhost:(port)/api/tamalli/customers

### Get customer by id (GET)

http://localhost:(port)/api/tamalli/customers/{customerId}

### Add new customer (POST)

http://localhost:(port)/api/tamalli/customers

Body:
{
"address":"monterrey",
"email":"rola@gmail.com",
"firstName":"Rolando",
"lastName":"Garcia",
"password":"1469o",
"phoneNumber":"16468645"
}

Note: You cannot add  new customer with an email that is already in the DB.

### Update customer (PUT)

http://localhost:(port)/api/tamalli/customers/{customerId}

Body:
{
"address":"monterrey",
"email":"rola@gmail.com",
"firstName":"Rolando",
"lastName":"Garcia",
"password":"1469o",
"phoneNumber":"16468645"
}

Note: You cannot change the email to one  that is already in use.
### Update customer partially (PATCH)

http://localhost:(port)/api/tamalli/customers/{customerId}

Body with the parameters you are going to change:
{
"address":"monterrey",
"password":"1469o",
"phoneNumber":"16468645"
}


Note: You cannot change the email to one  that is already in use.

### Delete customer (DELETE)

http://localhost:(port)/api/tamalli/customers/{customerId}

Note: you'll eliminate her/his shopping cart as well.

## Order
### Get all paid orders (GET)

http://localhost:(port)/api/tamalli/orders

### Get customer shopping cart (GET)

http://localhost:(port)/api/tamalli/orders/shoppingCart/{customerId}

### Get customer shopping history (GET)

http://localhost:(port)/api/tamalli/orders/history/{customerId}

### Update to paid status (UPDATE)

http://localhost:(port)/api/tamalli/orders/paid/{customerId}

Body is not needed

Note: You cannot change the status if the shopping cart is empty

## Order Detail (shopping cart)
### Add product by id(POST)

http://localhost:(port)/api/tamalli/orderDetail/{customerId}

Body:{
"productId":1,
"quantity":2
}


Note: You cannot add more than 30 pieces of each product 

### Change product quantity (PUT)

http://localhost:(port)/api/tamalli/orderDetail/{customerId}

Body: {
"productId":1,
"quantity":2
}


Note: You cannot add more than 30 pieces of each product

Note: if the price has changed you cannot modify it. So ypu have to delete it to change the quantity with the new price or  buy what you  have  been ordered with the old price.  

### Remove product by id (DELETE)

http://localhost:(port)/api/tamalli/orderDetail/{customerId}/{productId}

### Remove  all products (DELETE)

http://localhost:(port)/api/tamalli/orderDetail/all/{customerId}

## Product
### Get product by id (GET)

http://localhost:(port)/api/tamalli/products/{productId}

### Get tamal by id (GET)

http://localhost:(port)/api/tamalli/products/tamal/{productId}

### Get drink by id (GET)

http://localhost:(port)/api/tamalli/products/drink/{productId}

### Get all tamales (GET)

http://localhost:(port)/api/tamalli/products/tamal

### Get all drinks (GET)

http://localhost:(port)/api/tamalli/rpoducts/drink

### Get all products (GET)

http://localhost:(port)/api/tamalli/products

### Add new tamal (POST)

http://localhost:(port)/api/tamalli/products/tamal

Body:     "productName": "Tamal dulce",
"price": 13.00,
"productType": "Food",
"weightKilogram": 0.25
}

### Add new drink (POST)

http://localhost:(port)/api/tamalli/products/drink

Body: {
"productName": "Chocolate",
"price": 10.00,
"productType": "Drink",
"capacityLiters": 0.3
}

### Delete product by id (DELETE)

http://localhost:(port)/api/tamalli/products/{id}

Note: You'll eliminate its description as well

Note1: You'll eliminate  this product from all shopping cart

### Change  product price (PUT)

http://localhost:(port)/api/tamalli/products/price

Body:{
"productId":20,
"price":100.0
}

## Product Description
### Get product description by id (GET)
  
http://localhost:(port)/api/tamalli/descriptions/{productId}

### Get all products descriptions (GET)

http://localhost:(port)/api/tamalli/descriptions

### Get all products and their description (GET)

http://localhost:(port)/api/tamalli/descriptions/superProducts

### Add new  product description (POST)

http://localhost:(port)/api/tamalli/descriptions/{productId}

Body: {
"descriptionHead":"El mejor tamal de México",
"descriptionBody":"Elaborado  al estilo CDMX" ,
"urlImage":"https://img.freepik.com/fotos-premium/nacatamal-servido-hoja-platano-cerca-nacatamal-nicaraguense-comida-nicaraguense-nacatamal_550253-296.jpg?w=996"
}


Note: You cannot add a description if the product doesn't exist in the database


### Update product description (PUT)

http://localhost:(port)/api/tamalli/descriptions/{productId}

Body: {
"descriptionHead":"El mejor tamal de México",
"descriptionBody":"Elaborado  al estilo Hidalgo" ,
"urlImage":"https://img.freepik.com/fotos-premium/nacatamal-servido-hoja-platano-cerca-nacatamal-nicaraguense-comida-nicaraguense-nacatamal_550253-296.jpg?w=996"
}

### Update product description partially (PATCH)

http://localhost:(port)/api/tamalli/descriptions/{productId}

Body with the parameters you are going to change: {
"descriptionHead":"El mejor tamal de la zona"
}

### Delete product description (DELETE)

http://localhost:(port)/api/tamalli/descriptions/{productId}

