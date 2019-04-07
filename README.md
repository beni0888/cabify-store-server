#Cabify Backend Challenge
This my solution for the [Cabify Backend challenge](https://github.com/cabify/backend-challenge/blob/master/README.md).

Client application can be found [here](https://github.com/beni0888/cabify-store-client). 

## Considerations

This set of applications have been developed using the Java programming language, Spring Boot framework, Spring Shell 
library and Spring Boot Admin library. I have also used docker and docker compose in order to provide some facilities to
make extremely easy to run the system.

I have use the OOP paradigm for the development and also have followed the hexagonal architecture in order to provide a clean architecture with the business logic decoupled 
from infrastructure concerns. The system has a pretty high code coverage and I can tell that it is almost bug-free (you
can never be 100% that your code is bug free).

The system is composed by three different applications:
* **Cabify Store Server**: It is the core of the system, consist of a Spring Boot app implementing a REST API with 
several endpoints that allow to perform the different required operations. Here I used a ConcurrentHashMap as data structure
to store the shopping carts so operations over carts are thread safe. In addition, I have implemented an additional synchronization
mechanism through a read/write lock in the cart class to ensure that concurrent operations over the same cart are executed
consistently. I haven't used any concurrent collection for the item repository since operations over items are read only
(I have made the assumption that items cannot be added while the application is running). I would like to have added some
tests to ensure that concurrent operations run consistently but unfortunately I run out of time. I have also added a correlation
ID feature in order to allow the traceability of requests. Available items in the repository are configured through a Spring 
Boot property and are loaded when the application starts. Available discounts are also loaded on application start but they
are not configurable via properties, I would have liked to add this feature but it is not straightforward due to the heterogeneous
nature of the discounts configuration, and finally I didn't have time to investigate and implement that feature. I have
also made the assumption that there is just one applicable discount per cart entry.    

* **Cabify Store Client**: This is a CLI application that implements a client that allows the interaction with the server.
This app is developed using Spring Boot + Spring Shell. It provides several commands that allow to perform all required operations
against the server. The shell contains several commodities like autocompletion and help. I would have liked to add some 
integration tests for the commands but I finally did not get the time to do it.

* **Spring Boot Admin Server**: This is a Spring Boot app implementing the [Spring Boot Admin](https://github.com/codecentric/spring-boot-admin)
which provides a UI over Spring Boot Actuators allowing to admin one or several spring boot apps remotely from a central 
server. I have added this app in order to provide visibility and observability for the server application. Only the server
application is connected to the admin server.

I have developed the system in two different repositories:
* **Cabify Store Server:** A multi-module maven project that contains the cabify store server and the admin server
bootapps. I know that I should have developed this two applications in two different repositories, but I took this decision
in order to ease the development at the beginning and I finally did not find the moment to split it. 
I would like to point out also that I have developed each layer of the hexagonal architecture in a different module, I don't
usually apply this technique in my daily basis but I considered interesting to do it here to make clearer the layer separation
and to make me more conscious of the dependencies between layers. However, in hindsight, I can tell that I would not take 
the same decision again if I had to repeat the exercise, since it add more pain than value.

* **Cabify Store Client:** A single module maven project implementing the client application.    

## Running

In order to run the applications you need to have installed the Java 8 SDK. I have added docker files to build each application
in a docker image, and docker compose file to ease the orchestration of the application startup, but you still need to have
Java installed in order to generate the jar files. Docker images generations is automated through maven, although you can 
still build the images without using maven, that is up to you.

### Manual Running 

You can easily run the system manually without docker, I have added a maven wrapper on each repository and module in order 
to ease the build and run. You can run each app using the spring boot maven plugin `./mvnw spring-boot:run` or executing 
the `jar` file generated after a `./mvnw package`.

#### Store Server and Admin Server

1. Run `./mvnw install` from the repository's root directory in order to build the whole application and make sure that 
all tests pass.
```bash
cd /path/to/cabify-store-server
./mvnw install
```

2. Run Admin Server:
```bash
cd /path/to/cabify-store-server/spring-boot-admin-server
./mvnw spring-boot:run
```

3. Run Store Server:
```bash
cd /path/to/cabify-store-server/infrastructure
./mvnw spring-boot:run
```

4. You can access the admin server through `http://localhost:10011/` (it listens on port `10011` by default), there you can
see the store server application status which should be UP. Store server listens on port `8080` by default.

#### Store Client

1. Run `./mvnw install` from the repository's root directory in order to build the whole application and make sure that 
   all tests pass.
```bash
cd /path/to/cabify-store-client
./mvnw install
```
   
2. Run the client shell application:
```bash
cd /path/to/cabify-store-client
./mvnw spring-boot:run
``` 

3. You can start entering commands to work with the application. First, I recommend to to type `help` in order to get 
help on the different commands use.

### Run with Docker and Docker compose

Each image docker file is located under `/docker` directory on each repository, although the `docker-compose.yml` located
in the cabify store server repository contains the configuration to start up the whole system (store client included).

1. Build the image for the Admin Server:
```bash
cd /path/to/cabify-store-server/spring-boot-admin-server
./mvnw package
```
2. Build the image for the Store Server:
```bash
cd /path/to/cabify-store-server/cabify-store-server
./mvnw package
```
3. Build the image for the Store Client:
```bash
cd /path/to/cabify-store-client
./mvnw package
```
4. Start the container and attach to client shell:
```bash
cd /path/to/cabify-store-server/docker
docker-compose run storeclient
```
The previous command should start the three containers and attach to the client application shell, so you can start working
right after. It's possible that it takes a little bit of time for docker to establish the connections between containers, 
so if you get an error at the very first command you enter, please wait a moment and try again.

## Cabify Store Server REST API

The server REST API is made by the following endpoints:
 
* POST `/cart` 
Creates a new cart.
* POST `/cart/{cartId}/item` Add an item to the cart. 
```json
{ "itemCode": "TSHIRT", "quantity": 1 }
```
* GET `/cart/{cartId}` Get total amount for given cart.
* DELETE `/cart/{cartId}` Remove given cart.

## Cabify Store Client CLI application

The shell application provides the following commands
* add, add-item: Add an item to an existent cart
```
NAME
	add - Add an item to an existent cart

SYNOPSYS
	add [-cart] string  [-item] string  [[-quantity] int]

OPTIONS
	-cart or -cartId  string
		Id of the cart to add the item to
		[Mandatory]

	-item or -itemCode or -code  string
		Code of the item to be added to the cart (VOUCHER|TSHIRT|MUG)
		[Mandatory]

	-quantity  int
		Id of the cart to add the item to
		[Optional, default = 1]

ALSO KNOWN AS
	add-item
```
* add-bulk, add-several-items: Add several items to an existent cart (just one unit per item)
``` 
NAME
	add-bulk - Add several items to an existent cart (just one unit per item)

SYNOPSYS
	add-bulk [-cart] string  [-items] string

OPTIONS
	-cart or -cartId  string
		Id of the cart to add the items to
		[Mandatory]

	-items or -itemCodes or -codes  string
		Comma-separated list of item-codes to be added to the cart (VOUCHER,TSHIRT,MUG,TSHIRT)
		[Mandatory]

ALSO KNOWN AS
	add-several-items
```
* create, create-cart: Create a new shopping cart
``` 
NAME
	create - Create a new shopping cart

SYNOPSYS
	create

ALSO KNOWN AS
	create-cart
```
* delete, delete-cart: Delete the given cart
``` 
NAME
	delete - Delete the given cart

SYNOPSYS
	delete [-cart] string

OPTIONS
	-cart or -cartId  string
		Id of the cart to be deleted
		[Mandatory]

ALSO KNOWN AS
	delete-cart
```
* get-cart-total, total: Return the total amount for the given cart
``` 
NAME
	get-cart-total - Return the total amount for the given cart

SYNOPSYS
	get-cart-total [-cart] string

OPTIONS
	-cart or -cartId  string
		Id of the cart
		[Mandatory]

ALSO KNOWN AS
	total
```

## TODO
* Load available discounts via configuration properties.
* Remove domain entities tests and test them in a transitive way via application services tests.
* Add tests to ensure that concurrent operations on server behaves consistently.
* Refactor server application's Price class to make it receive a simple float or double value (maybe a string) through 
its constructor instead of receive a BigDecimal which is its inner value. I think the code would be clearer this way.
* Add correlation ID for command executions in client application, and propagate it through request to server application, 
so full system traceability is possible. 
* Add integration tests for client commands.
* Connect client application to admin server.