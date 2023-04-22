
# SpellCaster

SpellCaster is a service for evaluating expressions against JSON objects using Spring Expression Language (SpEL).

## API Endpoints

### POST /context/{appName}
Create a new context for the given appName using the provided JSON object.

**Request Parameters:**
- appName (path): Name of the application
- context (body): JSON object to be used as context

**Response:**
- 200 OK: Returns a string representation of the context

### POST /context/{appName}/file
Create a new context for the given appName using the provided file.

**Request Parameters:**
- appName (path): Name of the application
- context (formData): File containing the context

**Response:**
- 200 OK: Returns a string representation of the context

### GET /context/{appName}/{key}
Retrieve the context value for the given appName and key.

**Request Parameters:**
- appName (path): Name of the application
- key (path): Key for the value in the context

**Response:**
- 200 OK: Returns the value associated with the key

### PUT /context/{appName}/{key}
Update the context value for the given appName and key.

**Request Parameters:**
- appName (path): Name of the application
- context (body): JSON object to be used as context
- key (path): Key for the value in the context

**Response:**
- 200 OK

### DELETE /context/{appName}/{key}
Delete the context value for the given appName and key.

**Request Parameters:**
- appName (path): Name of the application
- key (path): Key for the value in the context

**Response:**
- 200 OK

### POST /context/{appName}/{key}/eval
Evaluate a SpEL expression against the context associated with the given appName and key.

**Request Parameters:**
- appName (path): Name of the application
- expression (body): SpEL expression to be evaluated
- key (path): Key for the value in the context

**Response:**
- 200 OK: Returns the result of the evaluated expression

### POST /context/{appName}/{key}/evalJson
Evaluate a JSON Expression against the context associated with the given appName and key.

**Request Parameters:**
- appName (path): Name of the application
- jsonExpression (body): JSON Expression to be evaluated
- key (path): Key for the value in the context

**Response:**
- 200 OK: Returns the result of the evaluated JSON Expression

### POST /expression/validate
Validate a JSON Expression.

**Request Parameters:**
- jsonExpression (body): JSON Expression to be validated

**Response:**
- 200 OK: Returns a boolean value indicating whether the JSON Expression is valid or not


Getting Started
To run the SpellCaster service, you can use the following command:

```bash
./mvnw spring-boot:run
```
This will start the Spring Boot application on the default port 8080. You can then use a tool like Postman or curl to interact with the RESTful API.
