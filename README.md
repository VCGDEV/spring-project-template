# Spring Project Template

This is a project template with all libraries required in order to create a REST based aplication

## Getting Started 

There are some configurat you need to add to local environment before runnin g the application


- Add `artifactory_user` & `artifactory_password` as environment variable environmet variable
- Run SQL scripts to load

database, you can get a copy from administrator


### Prerequisites


- Required sofware
    - Postgresql
	

### Running Spring Boot Application


From a terminal:

```

$ ./gradlew bootRun -- args='-- spring.profiles.active=dev'
```


## Running the tests

There are two kind of tests you may want to run


### Unit Testing and Jacoco Coverage


```
$ ./gradlew clean test jacocoTestReport jacocoTestCoverageVerification
```

### Integration testing


In order to run  this tests you need to run the spring application before,and then type in a terminal


```

$ ./gradlew integrationTest

```

## Authors


* **Victor de la Cruz**  * Initial work* - [vcgdev](https://github.com/VCGDEV)
* **Lucy Sanchez**  * Update docs- [Lucy](https://github.com/lucysriver)
