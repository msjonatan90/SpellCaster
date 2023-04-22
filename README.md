
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


## Expression Types

SpellCaster supports two types of expressions: SpEL Expression and JSON Expression.

### SpEL Expression

SpEL (Spring Expression Language) is a powerful expression language that supports querying and manipulating an object graph at runtime. In SpellCaster, SpEL expressions can be used for evaluating expressions against the context.

**Example:**
``` groovy
#root.movie.title == 'Inception'
```

### JSON Expression

JSON Expression is a custom format for representing SpEL expressions as JSON objects. This format allows for easy manipulation of expressions in different contexts, such as user interfaces or programmatic interfaces.

The JSON Expression schema represents SpEL expressions as a tree-like structure, making it easier to work with and manipulate programmatically. Here's an example of how a JSON Expression schema might look like:

```json
{
  "type": "Expression",
  "root": {
    "type": "BinaryOperation",
    "operator": "+",
    "left": {
      "type": "VariableReference",
      "name": "a"
    },
    "right": {
      "type": "MethodCall",
      "name": "multiply",
      "arguments": [
        {
          "type": "VariableReference",
          "name": "b"
        },
        {
          "type": "Literal",
          "value": 2,
          "dataType": "Integer"
        }
      ]
    }
  }
}
```
In this example, the JSON Expression schema represents the SpEL expression a + multiply(b, 2). The schema has a hierarchical structure, with each node representing an expression element (e.g., variable, literal, method call, or operation). The "type" field indicates the element's type, while other fields store relevant information for that element.

Here's a brief overview of the possible node types and their fields:

- Expression: The root node representing the entire expression.
"root": Contains the root element of the expression (e.g., a BinaryOperation, VariableReference, or MethodCall).
- BinaryOperation: Represents a binary operation (e.g., addition, subtraction, multiplication, or division).
"operator": The binary operator (e.g., +, -, *, /).
"left" and "right": The left and right operands of the operation, which can be any expression element.
- VariableReference: Represents a reference to a variable in the JSON context.
"name": The name of the variable.
- Literal: Represents a literal value (e.g., a number, string, or boolean).
"value": The literal value.
"dataType": The data type of the literal (e.g., "Integer", "Double", "String", "Boolean").
- MethodCall: Represents a method call with zero or more arguments.
"name": The name of the method.
"arguments": An array of expression elements representing the method's arguments.

This JSON Expression schema provides a flexible and easily understandable representation of SpEL expressions, which can be used for programmatic manipulation and evaluation within the SpELCaster service.



Getting Started
To run the SpellCaster service, you can use the following command:

```bash
./mvnw spring-boot:run
```
This will start the Spring Boot application on the default port 8080. You can then use a tool like Postman or curl to interact with the RESTful API.
