# Notes for those reviewing this project

## To Run
In the project root
```
$ mvn package
$ java -jar target/kalah-0.0.1-SNAPSHOT.jar 
```

## To Run Tests
In the project root
```
$ mvn test 
```

## To view the API design
Please open the file "src/main/api-definition/kalah-api.yml" in https://editor.swagger.io/

## Process
I took an API design first approach to this project, this is the process I followed:

1. Used IntelliJ to create a SpringBoot project.
2. Created an Open API definition for the Widget API see "src/main/api-definition/widget-api.yml".
3. I used the code generation tools on the swagger site(https://editor.swagger.io/) to generate the API models and controller from the definition.
4. Created the API tests. 
5. Individually implemented the required changes to the API Controller functions to make the API tests work, creating the service, repository tests and repository functions as needed.

I do not have working experience using SpringBoot  so I focussed on learning the basics of Spring Boot and leveraging features supported by the framework.
 
I did not implement any security and minimal work on error handling. If this was a real application being deployed to production I would have secured it and implemented more complete and robust error handling. 

Any queries or issues running the project please contact me at mark.turner0@gmail.com.