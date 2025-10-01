# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Java Swing-based puzzle game based on JumpIN' that follows the MVC design pattern. The game involves moving rabbits and foxes on a 5x5 board to solve puzzles.

## Build and Development Commands

### Build and Package
- `./gradlew clean build` - Builds the project and creates `Rabbits-and-Foxes.jar` in the `build/libs` directory (fat JAR with dependencies)
- `./gradlew clean compileJava` - Compiles the project without packaging
- `./gradlew shadowJar` - Creates the fat JAR directly
- Note: JAR contains only compiled classes and resources (no source files)

### Testing
- `./gradlew test` - Runs all JUnit 5 tests
- `./gradlew test --tests '*' --tests '!ui.GameViewTest'` - Run tests excluding GameViewTest

### Running the Application
- `java -jar build/libs/Rabbits-and-Foxes.jar` - Run the packaged application
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
- **Lombok**: Used throughout for `@Getter`, `@Setter`, `@Slf4j` annotations (migrated from Log4j to SLF4J)
- **Gson**: JSON parsing for level data
- **JTattoo**: Look and feel theming
- **SLF4J + Log4j 2**: Modern logging framework (migrated from Log4j 1.x)
- **JUnit 5**: Testing framework (version 5.13.4)
- **Shadow Plugin**: Creates fat JAR with all dependencies bundled
- **Dependency Updates**: Managed automatically by Dependabot - do not manually update dependencies

## Important Development Notes

### Build System
- **Gradle**: Version 9.1.0 with Kotlin DSL (build.gradle.kts)
- **Java Version**: Targets Java 25 via toolchain configuration
- **Shadow Plugin**: Version 8.3.9 for fat JAR creation
  - Plugin ID: `com.gradleup.shadow` (new maintainership, not `com.github.johnrengelman.shadow`)
  - Shadow 8.1.1 and earlier are incompatible with Gradle 9.x
  - Minimum version for Gradle 9 compatibility: 8.3.7
- **JUnit Platform Launcher**: Required as `testRuntimeOnly` dependency for Gradle 9
- **Test Exclusion in CI**: GameViewTest excluded when `CI=true` environment variable is set (configured in build.gradle.kts)

### Resource Management
- Game assets (images, sounds) in `src/main/resources/`
- Level definitions in JSON format
- Images for different piece orientations (fox head/tail directions, rabbit colors)

### Documentation
- **Architecture Diagrams**: Located in `docs/` directory using Mermaid format
  - `docs/class-diagram.md` - Interactive class diagram showing MVC structure
  - `docs/sequence-diagram.md` - Game interaction flow diagram
  - Renders natively in GitHub and VSCode (with Mermaid extension)
- **User Manual**: `docs/user-manual.md` - Complete game guide with screenshots
- **Milestone Documentation**: `docs/milestones/` - Project milestone history
- **Legacy**: Original Violet UML files removed (were in `documentation/uml/`)

### Testing Structure
- Tests mirror main package structure under `src/test/java/`
- Comprehensive test coverage for model classes and utilities
- All 40 tests pass with Java 25 and JUnit 5.13.4

### CI/CD Configuration
- **GitHub Actions**: `.github/workflows/maven.yml`
  - Uses `xvfb-run` for headless GUI testing (provides virtual X11 display)
  - All 40 tests run in CI, including GameViewTest
  - Ubuntu runner with Java 25
- **Headless Environment Handling**:
  - Display: Xvfb provides virtual framebuffer for Swing/AWT components
  - Audio: Resources gracefully degrade when audio devices unavailable
  - Audio clips (`Resources.INVALID_MOVE`, `Resources.SOLVED`) may be null in CI

### Known Limitations
- **Java 25 Warnings**: Reflection warnings from Log4j (`sun.reflect.Reflection.getCallerClass is not supported`)
  - This is a known compatibility issue with older logging libraries on Java 25
  - Does not affect functionality, can be safely ignored
- **GameView Requirements**:
  - Requires display server for initialization (Xvfb in CI)
  - Audio components gracefully handle unavailable audio devices
- **Audio Resources**: Sound effects may be null in headless environments
  - Code already has proper null checks for all audio usage

## Git Workflow

### Branch Management
- **Main branch**: `main` (not `master`)
- **Feature branches**: Create from `main`, name with prefix (e.g., `feature/`, `fix/`)
- **Stacked PRs**: PRs can target feature branches; will auto-retarget to `main` when base merges

### Pull Request Workflow
1. Create feature branch from latest `main`
2. Make changes and commit
3. Push branch and create PR
4. Resolve merge conflicts by pulling latest `main` into feature branch
5. Enable auto-merge once checks pass and reviews approved
6. PR merges automatically when all conditions met

### Key Contributors
- `Abdoltim` (Abdalla El Nakla)
- `danihashweh` (Dani Hashweh)
- `john-breton` (John Breton)
- `mo-5` (Mohamed Radwan)
- `samuel-gamelin` (Samuel Gamelin)
