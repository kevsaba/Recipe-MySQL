# recipe

Build status
[![CircleCI](https://circleci.com/gh/kevsaba/sfg-pet-clinic/tree/master.svg?style=svg)](https://circleci.com/gh/kevsaba/recipe/tree/master)

This is a project done with Spring Boot, Maven, JPA, MySQL, JUnit5, Lombok, Mockito and Thymeleaf (front end).

The idea of this project its to have a full app with CRUD operations, unit test, integration test bringing the spring context.
- Using different JPA annotations to get persistence on the Db. For H2 embed database and MySQL. Where H2 will be set as default one when no profile defined (dev,prod)
in case of dev or prod profiles then the application-dev or application-prod.yml will be used where the corresponding db configuration is set. This can connect to a local MySQL installed in the machine or to a Docker instance of MYSQL that is set to persist on the host to not loose data everytime the container is deleted 
- Using spring annotations getting familiar with them, different types such as @Component @Controller @Configuration @Autowired and many other same as different ways
of creating beans for different purposes like some with annotations like @Component or others under a @Configuration class and annotated with @Bean to do dependency injection
for, for example thir party services like the ChuckNorrisService example.
-Using I18n for internacionalization of the messages so we can have it in different .properties defined accordingly to each language, also in the default there are example to change the labels for the validations done via the @Valid on the controllers when doing the serialization of a json to an object
