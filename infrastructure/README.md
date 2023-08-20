# react-app project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Building

### Configuring build

In order to build application, some variables must be provided (for example database credentials).
For security reason,
such configuration variables should never be committed into the repository.

In order to provide to application in the build time, all required variables a ${basedir}/.env file
should be created.
In this file, we provide key=value pair of each required variable, ex:
``
GOOGLE_CLIENT_ID=123.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=
QUARKUS_DATASOURCE_USERNAME=testuser
``

### Compiling

``./gradlew build``

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```
./gradlew quarkusDev
```

## Packaging and running the application

The application can be packaged using `./gradlew quarkusBuild`.
It produces the `react-app-1.0.0-SNAPSHOT-runner.jar` file in the `build` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/lib` directory.

The application is now runnable using `java -jar build/react-app-1.0.0-SNAPSHOT-runner.jar`.

If you want to build an _über-jar_, just add the `--uber-jar` option to the command line:

```
./gradlew quarkusBuild --uber-jar
```

## Creating a native executable

You can create a native executable using: `./gradlew build -Dquarkus.package.type=native`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container
using: `./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./build/react-app-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please
consult https://quarkus.io/guides/gradle-tooling#building-a-native-executable.