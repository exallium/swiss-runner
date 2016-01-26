#swiss-runner

SwissRunner utilizes the following principles and technologies:

* Sender/Receiver as dictated by uncle bob to link Presenters to the Model layer
* MVP Architecture, Where JFX is V, Core is P, and JDBC is M
* Reactive X for cross-layer communication and state change handling
* H2 as a database layer
* JUnit 4 tests for Core

* To build

```
./gradlew clean build
```

* To test

```
./gradlew clean :core:test
```