# Task Administrator Exercise

The solution consists of using a **Dispatcher** class that has thee queues to process **Tasks**: *pending*, *working* and *finished*.

Each **Task** is a Thread that executes a single Task. **Dispatcher** handles the thread pool of a specific max size and keep ny other task waiting in queue.

The solution does not have persistence, thus does not work in different clusters. On order to have that ability it is required to persist tasks on a common database between clusters / containers. 

Some testing was added, but some concurrency or random use cases are hard to test due to the non determinist nature of the problem.

On the UI, there is a glitch. It is possible to add duplicate values to calculate with the same status, but the table is not able to show them because I have no key duplication (I would require some DB unique key).

The UI does not automatically updates, it is required to manually refresh for simplicity.

Finally, there is no difference between PENDING_EXECUTION: not started and sent to queue. Maybe a new state is required.

## Prerequisites

Java JDK 8 and Maven are required to run the project.

## Compiling

As in any Maven project, it may be required to run:

```bash
mvn clean install
```

## Running

Like any other Spring Boot Maven project, run:

```bash
mvn spring-boot:run
```

This will deploy the application to a tomcat in [Localhost at port 8080](http://localhost:8080).

For consuming the API, it is possible to hit [Swagger](http://localhost:8080/swagger).

## Shutdown

In order to shutdown the server, run:

```bash
curl -X POST localhost:8080/shutdown
```

This does not kill the process, but if the application runs in a container, just deploy another container.

Using scripts make the application system-dependent and using containers is a much preferred option nowadays.

In a production system, such an endpoint should require authentication. For this exercise I configured the Spring Actuator module to bypass authentication. 

## Author

* **Sebastian D'Agostino**
