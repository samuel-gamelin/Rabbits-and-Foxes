# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Java Swing-based puzzle game based on JumpIN' that follows the MVC design pattern. The game involves moving rabbits and foxes on a 5x5 board to solve puzzles.

## Build and Development Commands

### Build and Package
- `mvn clean package` - Builds the project and creates `Rabbits-and-Foxes.jar` in the `target` directory
- `mvn clean compile` - Compiles the project without packaging

### Testing
- `mvn test` - Runs all JUnit 5 tests
- `mvn surefire:test` - Alternative test runner with specific configuration

### Running the Application
- `java -jar target/Rabbits-and-Foxes.jar` - Run the packaged application
- Main class: `ui.MainMenu` (entry point for the application)

## Architecture

### MVC Pattern Implementation
- **Model**: `model` package contains game logic
  - `Board` - Core game state, 5x5 grid management
  - `Piece` hierarchy - `Rabbit`, `Fox`, `Mushroom` with different behaviors
  - `Tile` - Individual board positions
- **View**: `ui` package contains Swing GUI components
  - `GameView` - Main game interface with board visualization
  - `MainMenu` - Application entry point and main menu
  - `LevelSelector` - Level selection interface
  - `LevelBuilder` - Custom level creation tool
- **Controller**: `controller` package
  - `GameController` - Handles user input and coordinates model updates

### Key Components
- **Board System**: 5x5 grid with coordinate-based piece management
- **Piece Movement**: Different movement patterns for rabbits (jump over obstacles) vs foxes (slide in lines)
- **Level System**: JSON-based level storage in `src/main/resources/levels/LevelData.json`
- **Solver**: `util.Solver` provides automated puzzle solving using BFS
- **Undo/Redo**: Move history management with stack-based implementation

### Dependencies
- **Lombok**: Used throughout for `@Getter`, `@Setter`, `@Log4j` annotations
- **Gson**: JSON parsing for level data
- **JTattoo**: Look and feel theming
- **Log4j**: Logging framework
- **JUnit 5**: Testing framework

## Important Development Notes

### Java Version
- Project targets Java 21 (source and target in maven-compiler-plugin)

### Resource Management
- Game assets (images, sounds) in `src/main/resources/`
- Level definitions in JSON format
- Images for different piece orientations (fox head/tail directions, rabbit colors)

### Testing Structure
- Tests mirror main package structure under `src/test/java/`
- Comprehensive test coverage for model classes and utilities