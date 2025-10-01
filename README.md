## Rabbits and Foxes

This GitHub repository is used for the development of a game based on JumpIN' that follows the MVC design pattern.

## Development

##### Tools

- [IntelliJ IDEA](https://www.jetbrains.com/idea/download),
  [Eclipse](https://www.eclipse.org/downloads/packages/release)
- [Java 25](https://adoptium.net/temurin/releases/?os=any&arch=any&package=jdk&version=25)
- [Lombok](https://projectlombok.org)
- [Maven](https://maven.apache.org/download.cgi)
- [Mermaid](https://mermaid.js.org/) for UML diagrams (renders natively in GitHub and VSCode)

##### Getting Started

1. Clone this repository.

2. Eclipse
    1. Ensure you have the Lombok plugin installed and configured.
    2. Open the Eclipse IDE, and click File -> Import.
    3. Select Maven and then Existing Maven Projects, then click Next.
    4. Click Browse and select the directory containing the repository you've cloned. Click Finish.

3. IntelliJ IDEA
    1. Ensure you have the Lombok plugin installed and configured.
    2. Open the IntelliJ IDEA IDE, and click File -> Open.
    3. Select the directory containing the repository you've cloned and click Open.

4. The entry point of the program is the main method of the MainMenu class under the ui package.
5. The project can be built by invoking a `mvn clean package` command from the project's root directory. This will
   generate a runnable jar file in the `target` directory named `Rabbits-and-Foxes.jar`.

##### Additional Tools

- Contributions are made through GitHub, on this repository
- GitHub issues are used for task and ticket tracking

## Class Diagram

View the interactive class diagram: [docs/class-diagram.md](docs/class-diagram.md)

*Last updated: September 27, 2025*

## Sequence Diagram

View the interactive sequence diagram: [docs/sequence-diagram.md](docs/sequence-diagram.md)

*Last updated: September 27, 2025*

## Known issues

Currently, there are no known issues.

> If you notice a bug, please add it to Issues tab. Make sure you include how to recreate the bug!

## Documentation

Documentation for the project can be
found [here](documentation/Rabbits%20and%20Foxes%20Documentation%20-%20User%20Manual%20and%20Design%20Decisions.pdf).

## The Team

- [Mohamed Radwan](https://github.com/MohamedRadwan)
- [Samuel Gamelin](https://github.com/samuel-gamelin)
- [Dani Hashweh](https://github.com/danihashweh)
- [John Breton](https://github.com/john-breton)
- [Abdalla El Nakla](https://github.com/abdallaelnakla)

## Credits for Graphical Resources

The pictures and graphical resources used in this game were
obtained [here](https://www.smartgames.eu/uk/one-player-games/jumpin).

## License and Disclaimer

> This application is for educational purposes. JumpIN' is a registered commercial product. The developers are not responsible for the distribution of this product.
