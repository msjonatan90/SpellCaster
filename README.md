# SpellCaster
# SpellCaster

SpellCaster is a web service that allows you to evaluate SpEL (Spring Expression Language) expressions against JSON objects. The service is built using Spring Boot and provides a RESTful API to interact with the SpEL engine.

## Endpoints

### `POST /api/v1/evaluate`

This endpoint allows you to evaluate a SpEL expression against a JSON object.

#### Request Body

The request body should be a JSON object containing the following properties:

- `expression`: A SpEL expression to be evaluated.
- `context`: A JSON object against which the SpEL expression should be evaluated.

#### Response

The response is a JSON object containing the following properties:

- `result`: The result of the SpEL expression evaluation.
- `success`: A boolean value indicating whether the evaluation was successful.

#### Example

Request:

```json
{
  "expression": "#root.actors.?[birthYear < 1980].name",
  "context": {
    "title": "Inception",
    "year": 2010,
    "director": {
      "name": "Christopher Nolan",
      "birthYear": 1970
    },
    "actors": [
      {
        "id": 1,
        "name": "Leonardo DiCaprio",
        "birthYear": 1974
      },
      {
        "id": 2,
        "name": "Joseph Gordon-Levitt",
        "birthYear": 1981
      },
      {
        "id": 3,
        "name": "Ellen Page",
        "birthYear": 1987
      }
    ],
    "genres": [
      "Action",
      "Sci-Fi",
      "Thriller"
    ]
  }
}
```

Response:

```json

{
  "result": [
    "Leonardo DiCaprio"
  ],
  "success": true
}
```

GET `/api/v1/contexts/{contextId}`
This endpoint allows you to retrieve a stored JSON context object by its ID.

Path Parameters
contextId: The ID of the stored JSON context object.
Response
The response is a JSON object containing the stored JSON context object.

Example
Request: GET `/api/v1/contexts/1`

Response:

```json
{
  "title": "Inception",
  "year": 2010,
  "director": {
    "name": "Christopher Nolan",
    "birthYear": 1970
  },
  "actors": [
    {
      "id": 1,
      "name": "Leonardo DiCaprio",
      "birthYear": 1974
    },
    {
      "id": 2,
      "name": "Joseph Gordon-Levitt",
      "birthYear": 1981
    },
    {
      "id": 3,
      "name": "Ellen Page",
      "birthYear": 1987
    }
  ],
  "genres": [
    "Action",
    "Sci-Fi",
    "Thriller"
  ]
}
```
Getting Started
To run the SpellCaster service, you can use the following command:

```bash
./mvnw spring-boot:run
```
This will start the Spring Boot application on the default port 8080. You can then use a tool like Postman or curl to interact with the RESTful API.
