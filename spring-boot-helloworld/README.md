# SpringBoot HelloWorld

How to run?

1. Using Maven: `./mvnw spring-boot:run`

2. Using Gradle: `./gradlew bootRun`

3. Run app as a jar: 
    ```
    // Maven
    java -jar target/spring-boot-helloworld-0.0.1-SNAPSHOT.jar`
   
    // Gradle
    java -jar build/libs/spring-boot-helloworld-0.0.1-SNAPSHOT.jar`
    ```
4. Override properties:

    ```shell
    $ java -jar target/spring-boot-helloworld-0.0.1-SNAPSHOT.jar --app.greeting=Hola
    $ java -jar -Dapp.greeting=Hola target/spring-boot-helloworld-0.0.1-SNAPSHOT.jar
    $
    $ export APP_GREETING=Bonjour
    $ java -jar target/spring-boot-helloworld-0.0.1-SNAPSHOT.jar
    ```
