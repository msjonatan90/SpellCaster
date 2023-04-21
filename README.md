# SpellCaster


SpellCaster is a web application that allows users to define and execute business logic using SpEL (Spring Expression Language) expressions.

The technical requirements for SpellCaster include the use of Java Spring Framework and the React JavaScript library for the user interface. The application should be able to receive input from the user in the form of JSON objects and use SpEL expressions to perform actions based on that input. The output should also be in the form of JSON objects.

The functional requirements for SpellCaster include the ability for users to define and save SpEL expressions and to execute them on demand. The application should support a variety of SpEL expression types, including literals, variables, method calls, and operators. Users should also be able to define custom functions in SpEL to extend the functionality of the application.

In addition to the core functionality, SpellCaster should also have the ability to stream input directly into Redis using a UUID generated key, and should be able to filter and manipulate JSON objects using SpEL expressions.

The proposed solution for SpellCaster is a web application that allows users to define and execute business logic using SpEL expressions. The solution uses Java Spring Framework for the backend and React JavaScript library for the frontend.

Users can define SpEL expressions in a custom JSON expression model and execute them on demand. The application supports a variety of SpEL expression types, including literals, variables, method calls, and operators. Users can also define custom functions in SpEL to extend the functionality of the application.

The solution includes the ability to stream input directly into Redis using a UUID generated key, and allows for filtering and manipulation of JSON objects using SpEL expressions.

The solution also includes a Swagger API documentation, which provides a clear and user-friendly way for developers to understand the endpoints and functionality of the application. The application is deployed using Docker and can be easily scaled horizontally to handle large amounts of traffic.

Overall, SpellCaster provides a powerful tool for developers and business analysts to define and execute complex business logic using a flexible and intuitive language.


