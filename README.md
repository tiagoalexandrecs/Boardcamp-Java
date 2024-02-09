Boardcamp is an AAPI which manages  board games`rentals. It is written in Java and applies layered architecture , with repositories, services and controllers, as well as Models, Dtos and exceptions.

In order to persist the data, the API connects to a PostgreSQL database, in which there are three entities: Customers, Games and Rentals. Each one has both GET and  POST endpoints. Moreover, the Rentals have an update feature, which is applied through a PUT endpoint.
endpoints, and for Rentals, there's also an update feature, which is applied through a PUT endpoint.

All the main aspects and exceptions were tested with both Unit and Integration Tests, which can be accessed and ran in the "test" folder

Besides Java, some main dependencies which were applied are :
- Spring Data JPA
- Hibernate
- Validation
- Spring Web
- Spring Dev Tools
- Lombok
- Jakarta

APIś endpoints :
- GET /customers = Retrieves all customers registered in the database
- GET /customers/:id = Retrieves a specific customer
- POST /customers = Submit a customerDto as body to insert a customer in the database
   GET /games = Retrieves all games registered in the database
- POST /games = Submit a gameDto as body to insert a game in the database
- GET /rentals = Retrieves all rentals registered in the database
- POST /rentals = By submitting a rentalDto, creates a rent in the database
- PUT /rentals/:id/return = Closes an ongoing rental

