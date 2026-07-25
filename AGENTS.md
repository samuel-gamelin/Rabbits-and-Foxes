# Repository guidance

## Project overview

Rabbits and Foxes is a Java Swing puzzle game inspired by JumpIN'. It uses a
Model-View-Controller architecture on a 5x5 game board.

## Build and development commands

- `./gradlew clean build` builds and tests the project, then creates the fat
  JAR at `build/libs/Rabbits-and-Foxes.jar`.
- `./gradlew test` runs the complete test suite.
- `./gradlew clean compileJava` compiles without packaging.
- `./gradlew shadowJar` creates the fat JAR directly.
- `java -jar build/libs/Rabbits-and-Foxes.jar` runs the packaged application.
- The application entry point is `ui.MainMenu`.

The Gradle wrapper is the supported build entry point. The build uses the Java
25 toolchain and Kotlin DSL. Treat `build.gradle.kts` as the source of truth for
dependency and plugin versions.

## Architecture

- `src/main/java/model/` contains game state and rules:
  - `Board` manages the 5x5 grid.
  - `Piece` is the base for `Rabbit`, `Fox`, and `Mushroom`.
  - `Tile` represents a board position.
- `src/main/java/ui/` contains Swing views:
  - `GameView` is the main game interface.
  - `MainMenu` is the application entry point.
  - `LevelSelector` and `LevelBuilder` manage level selection and creation.
- `src/main/java/controller/GameController.java` coordinates user input and
  model updates.
- `src/main/java/util/Solver.java` implements breadth-first puzzle solving.
- Undo and redo use stack-based move history.

## Resources

- Game assets and configuration live under `src/main/resources/`.
- Default levels are stored in
  `src/main/resources/levels/LevelData.json`.
- The packaged JAR must contain compiled classes and runtime resources, not
  Java source files.

## Testing and CI

- Tests mirror production packages under `src/test/java/`.
- `GameViewTest` requires a graphical environment.
- GitHub Actions runs the full suite with Xvfb.
- Audio devices may be unavailable in headless environments. Audio resources
  may therefore be `null`; preserve the existing null-safe behavior.

## Dependencies

- Gson parses level data.
- JTattoo provides the Swing look and feel.
- SLF4J and Log4j 2 provide logging.
- Lombok generates boilerplate at compile time.
- JUnit provides the test framework.
- The Shadow plugin creates the executable fat JAR.

## Working conventions

- Keep changes focused and preserve the MVC package boundaries.
- Add or update tests when behavior changes.
- Run `./gradlew test` for code changes and `./gradlew clean build` for build,
  packaging, dependency, or CI changes.
- Do not commit `.gradle/`, `build/`, or other generated output.
