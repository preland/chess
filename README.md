# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.
##
## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.


[Sequence Diagram](https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pZ19fQA0MH246snQHOOTM3Mow8BICKtTAL6YwiUwBazsXJTl-YNQI2MTU7Pz+qpLUCuP632b27tfhzYnG4sDOx1E5SgURimSgAApItFYpRIgBHHxqMAASiOxVEp0KsnkShU6nK9hQYAAqh04bd7jiiYplGpVASjDpSgAxJCcGA0yhMmA6ML04CjTBMkms05gvEqcpoHwIBC4kQqdlSllkkBQuQoAXw25MxnaaXqdnGUoKDgcfkdJlqqj4s5a0mqUq6lD6hQ+MCpOHAP2pE2Ss3atlnK02u2+-2O8Ea3JnIGXMoRaHIqCRVTKrCpkGywrHK4wG4de59GDlNbPIP++oQADW6CrNYOTsoRfgyHM5QATE4nDAagBpbrlobisbVmC1ub11KNltoNtzjvoDimLy+fwBaDsCkwAAyEGiSQCaQyWV7eWLxVL1TqTVaBnUCTQE7Fo1mCzey0OEtQUKAtS0nO5p3GdcZheRZligtZAQuQs5XVFBygQM9eThU9zzRDFYhxRNDFdcN3XJSlDTpCtp1NYkI0tTkYB5PlDSFEUYG-MQ3RlZN7zQxVlVVYjNTI1kKLAOMAz-d4ODo5l3UY8o4FSFAQCbGAZOWJ0XRTZDS1w3kczzTBQNBVCSmuAYaNGNd5z6Rdl1bWdEM7czCmyPsYEHYcxwnayp1slyngXYMnNXYK+kOTdt28PxAi8FB0BPM9fGYS90kyTBPLvIpqEfaQAFFj0K+pCuaFo31UD9ukc5t0Dc9kzKsuqVwQkK+g4FBuEyKC+kq1kPyi0z9OAvLnQVGBMPsNKcNSv18MxIj5RIwkxLJLqepQOFNspFB5PNSNCitIxur2tkBvfRIw3oxS+PGiEZDOzIc2wRIDB0pM9OBAz5rAYyEHzUbuyAqzprSgGwHa4agO7HKwAHIcR3HJIenBha1DzaLOFi3dAihO1jxhGAAHFpzZDLr2y29mAsx8SdKir7GnWqwvqtBGu+tMWrZtr22Gsz2WIjCYTJ0ZVBw0XycWwjPtWmR1o9GAKTAMW1EDXn0AOhioyYlW2WQWIYAgAAzZXyZuhTeIsx6idiNXVFe96xBE+7mpPKXxchkafvc8awOZ8W7YRyoYGaQCHz9+HEd8lG0c9tRIexrcd3igJsB8KBsG4eA9UyUnpxSTKbxyWn+MsypagaJmWeCTXP16QOUAAOWnCP8rG93+la1t+eeJvW9s-mkN9m3Jq9fU1bhOA85QNWZexOXRNu8SYGAW0p4H6dZh7tBtbu46mInzI2TQFBknN0YYGSDJUhge5L5b6dLcOkGVvKdeOHntQ3rQD7Xa5iCD+G9pze0FvdUGZZP7f1zIDYenMzjR28kjPyvRoGgMxoDZOuM06WG6phC+AApCAvIC5XwCDoBAoAmzU1LkLSO5RqhUhfC0JurMGzswnNnYAeCoBwAgJhKAswm4AElpDtxOIAsCDl67Q2eNw3h-DBFyLmKI6Q0MR5pnoQJGAAArEhaAp7EN5N-dES0l6kRXmSGAJteRf2nHCNR+9eKH3KLYzgbIm42O8MMR+YiX46zHuhR+NQdC6LUlgV2a0rFK30SYhxTdQnhPAL+KhiiBHQB3vXZxFpdblBQBwDInikA0CSF4iAYSIlrz5J-Nk-pDBnwvgorsvITYQACXdIJ5Q1bGLQE7P+LsVrdndj0gxYDgYQIYWWJuvTIbwNhvdJBPlkYThmWMzBYBsGeDinuLwPCezelgMAbA2dCDxESEXKm8NtEVwqEVEqZUKrGE5iBUaVldq9XKP1YwAsJnl0eh87a0hnooAXstNCy8rbWMBTtEFOSjocnKMCraF1nk8VyV0p6W1+n-yGW7N5WK9rjN9m-Du7yQWQw0QgjyNMY4rNRoCpOmAAj+yjrS5B-YYDSmwJkDg3QyyMo2YcFlCzEHssHAAFhgM3CA8BEiZAwPynogrYGbLMDjIAA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
