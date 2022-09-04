## Java Commerce
Java app for learning purposes.

## Building & Running
@see quarkus README.md

# Commerce-app project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

### Configuration

Place all environment variables in file `.env`. That file has being added to `.gitignore`, and should not be committed.

### Running application in DEV mode

You can run your application in dev mode that enables live coding using:

```
./gradlew quarkusDev
```

### Packaging and running the application for production

The application can be packaged using `./gradlew quarkusBuild`. It produces the `react-app-1.0.0-SNAPSHOT-runner.jar`
file in the `build` directory. Be aware that it’s not an self containing _über-jar_, as the dependencies had been copied
into the `build/lib` directory. If you were to run application 'as is', it would run without problems, because of
dependencies being in `lib` folder.

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
