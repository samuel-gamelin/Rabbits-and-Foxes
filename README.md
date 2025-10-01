# Rabbits and Foxes

A Java Swing-based puzzle game inspired by [SmartGames' JumpIN'](https://www.smartgames.eu/uk/one-player-games/jumpin), implementing the Model-View-Controller (MVC) design pattern.

## Features

- **Classic Puzzle Gameplay**: Navigate rabbits to their holes and foxes across the board
- **40+ Levels**: Progressive difficulty from beginner to expert
- **Custom Level Builder**: Create and save your own puzzle designs
- **Smart Solver**: Automated puzzle-solving using breadth-first search (BFS)
- **Undo/Redo**: Full move history management
- **Save/Load**: Persist game state and resume later
- **Modern UI**: JTattoo-themed Swing interface

## Quick Start

### Prerequisites

- **Java 25** (via [Adoptium Temurin](https://adoptium.net/temurin/releases/?os=any&arch=any&package=jdk&version=25))
- **Gradle 9.1.0** (via wrapper, no manual installation needed)

### Build and Run

```bash
# Clone the repository
git clone https://github.com/samuel-gamelin/Rabbits-and-Foxes.git
cd Rabbits-and-Foxes

# Build the project (creates fat JAR with all dependencies)
./gradlew clean build

# Run the game
java -jar build/libs/Rabbits-and-Foxes.jar
```

### Quick Development

```bash
# Run all tests
./gradlew test

# Run tests excluding UI tests
./gradlew test --tests '*' --tests '!ui.GameViewTest'

# Clean build
./gradlew clean compileJava
```

## Architecture

This project follows the **Model-View-Controller (MVC)** design pattern:

- **Model** (`model/`): Game logic, board state, and piece behaviors
  - `Board`: 5x5 grid management
  - `Piece` hierarchy: `Rabbit`, `Fox`, `Mushroom` with movement rules
  - `Tile`: Individual board positions

- **View** (`ui/`): Swing GUI components
  - `GameView`: Main game interface
  - `MainMenu`: Application entry point
  - `LevelSelector`: Level browsing and selection
  - `LevelBuilder`: Custom level creation tool

- **Controller** (`controller/`): User input and model coordination
  - `GameController`: Handles player actions and updates

### Key Components

- **Piece Movement**: Rabbits jump over obstacles; foxes slide in straight lines
- **Level System**: JSON-based level storage (`src/main/resources/levels/LevelData.json`)
- **Solver**: BFS-based automated puzzle solving (`util.Solver`)
- **Undo/Redo**: Stack-based move history

## Documentation

- **[User Manual](docs/user-manual.md)**: Complete gameplay guide with screenshots
- **[Class Diagram](docs/class-diagram.md)**: Interactive Mermaid diagram showing MVC structure
- **[Sequence Diagram](docs/sequence-diagram.md)**: Game interaction flow
- **[Architecture Decisions](docs/decisions/)**: ADRs documenting technical choices
- **[Project Documentation](CLAUDE.md)**: Comprehensive developer guide for Claude Code

### Claude Code Integration

This project includes specialized agents in `.claude/agents/` for automated workflows:
- **`dependency-updater`**: Manages Dependabot PRs and dependency updates
- **`license-auditor`**: Verifies GPL v3 compliance and generates SBOM

See [CLAUDE.md](CLAUDE.md) for detailed usage.

## Development Setup

### IDE Configuration

**IntelliJ IDEA** (Recommended):
1. Install the Lombok plugin
2. Open the project: `File → Open` → Select project directory
3. Gradle will auto-import dependencies

**Eclipse**:
1. Install the Lombok plugin
2. Import project: `File → Import → Gradle → Existing Gradle Project`
3. Select project directory and finish

### Build System

- **Build Tool**: Gradle 9.1.0 with Kotlin DSL
- **Java Target**: Java 25 (via toolchain)
- **Packaging**: Shadow plugin creates fat JAR with dependencies
- **Configuration Cache**: Enabled for faster builds

### Dependencies

- **Gson 2.9.0**: JSON parsing for level data
- **SLF4J 2.0.7 + Log4j 2.20.0**: Modern logging framework
- **JTattoo 1.6.13**: Look and feel theming
- **Lombok 1.18.42**: Reduces boilerplate via annotations
- **JUnit 5.13.4**: Testing framework

All dependencies are GPL v3 compatible. See [NOTICE](NOTICE) for full attributions.

## License

This project is licensed under the **GNU General Public License v3.0** (GPL v3).

- **Why GPL v3?** Required by the JTattoo dependency (GPL v2+), and enables use of Apache 2.0 dependencies
- **License Compatibility**: All runtime dependencies verified compatible
- **Full License**: See [LICENSE](LICENSE) file
- **Third-Party Notices**: See [NOTICE](NOTICE) file for dependency attributions

### Acknowledgments and Trademarks

This is an educational project. JumpIN' is a registered trademark of SmartGames. Graphical assets were obtained from SmartGames. The game mechanics implementation and code are original work by the development team.

## Contributing

Contributions are welcome! Please:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Follow existing code style and MVC patterns
4. Add tests for new functionality
5. Ensure `./gradlew test` passes
6. Update documentation as needed
7. Submit a pull request

### Branch Protection

The `main` branch requires:
- At least 1 approving review
- Passing CI checks
- All conversations resolved

## Testing

- **Test Structure**: Tests mirror `src/main/java/` under `src/test/java/`
- **Coverage**: 40 tests covering model classes and utilities
- **Framework**: JUnit 5.13.4
- **CI Exclusion**: `GameViewTest` excluded in CI environments (headless)

Run tests:
```bash
./gradlew test
```

## The Team

- [Mohamed Radwan](https://github.com/MohamedRadwan)
- [Samuel Gamelin](https://github.com/samuel-gamelin)
- [Dani Hashweh](https://github.com/danihashweh)
- [John Breton](https://github.com/john-breton)
- [Abdalla El Nakla](https://github.com/abdallaelnakla)

## Acknowledgments

- **Puzzle Design**: Inspired by SmartGames' JumpIN'
- **Graphical Assets**: Obtained from [SmartGames JumpIN' product page](https://www.smartgames.eu/uk/one-player-games/jumpin)
- **University Course**: Developed as a Software Engineering project at Carleton University

---

**Entry Point**: `ui.MainMenu.main()`
**Repository**: [samuel-gamelin/Rabbits-and-Foxes](https://github.com/samuel-gamelin/Rabbits-and-Foxes)
