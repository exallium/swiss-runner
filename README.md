#swiss-runner

SwissRunner utilizes the following principles and technologies:

* Sender/Receiver as dictated by uncle bob to link Presenters to the Model layer
* MVP Architecture, Where JFX is V, Core is P, and JDBC is M
* Reactive X for cross-layer communication and state change handling
* H2 as a database layer
* JUnit 4 tests for Core

## Running this code

* To build

```
./gradlew clean build
```

* To test

```
./gradlew clean :core:test
```

## Contributing

### Translations

See ```app/src/main/resources/bundles``` directory.  I will only be doing english translations myself, but anyone is free to open a pull
request against this repository with a new set of translations.  Since I am not a language expert, I will ask that the community peer 
reviews these translations to ensure accuracy.  I'll also be checking basic translation via Google Translate.