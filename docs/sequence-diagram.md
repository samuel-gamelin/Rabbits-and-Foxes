# Sequence Diagram

This diagram shows the interaction flow for a typical game session in the Rabbits and Foxes application.

```mermaid
sequenceDiagram
    participant User
    participant MainMenu
    participant LevelSelector
    participant GameView
    participant GameController
    participant Board
    participant Resources
    participant Solver

    %% Application Start
    User->>MainMenu: Launch Application
    MainMenu->>Resources: Initialize Resources
    Resources-->>MainMenu: Resources Loaded
    MainMenu->>MainMenu: Display Main Menu

    %% Level Selection Flow
    User->>MainMenu: Select "Select Level"
    MainMenu->>LevelSelector: Create LevelSelector
    LevelSelector->>Resources: getAllDefaultBoards()
    Resources-->>LevelSelector: List<Board>
    LevelSelector->>LevelSelector: Display Level Grid

    User->>LevelSelector: Select Level
    LevelSelector->>Resources: getDefaultBoardByLevel(level)
    Resources-->>LevelSelector: Board
    LevelSelector->>GameView: Create GameView(board, level)

    %% Game Initialization
    GameView->>GameController: Create GameController(board, gameView)
    GameController->>Board: Initialize Board State
    Board-->>GameController: Board Ready
    GameController-->>GameView: Controller Ready
    GameView->>GameView: setupGUI()
    GameView->>GameView: updateView()

    %% Game Play Loop
    loop Game Playing
        User->>GameView: Click on Piece
        GameView->>GameView: Select Piece

        User->>GameView: Click on Target Position
        GameView->>GameController: movePiece(piece, newRow, newCol)
        GameController->>Board: isValidMove(piece, newRow, newCol)
        Board-->>GameController: boolean

        alt Valid Move
            GameController->>Board: movePiece(piece, newRow, newCol)
            Board->>Board: Update piece position
            Board-->>GameController: Move successful
            GameController->>GameView: updateView()
            GameView->>GameView: Refresh display

            GameController->>Board: isSolved()
            Board-->>GameController: boolean

            alt Game Solved
                GameController->>GameView: displayWinMessage()
                GameView->>Resources: SOLVED.play()
            end
        else Invalid Move
            GameController->>GameView: displayInvalidMove()
            GameView->>Resources: INVALID_MOVE.play()
        end
    end

    %% Menu Actions
    User->>GameView: Select "Undo"
    GameView->>GameController: undoMove()
    GameController->>GameView: updateView()

    User->>GameView: Select "Redo"
    GameView->>GameController: redoMove()
    GameController->>GameView: updateView()

    User->>GameView: Select "Reset"
    GameView->>GameController: resetBoard()
    GameController->>Board: Reset to initial state
    Board-->>GameController: Reset complete
    GameController->>GameView: updateView()

    User->>GameView: Select "Solve"
    GameView->>Solver: solve(board)
    Solver->>Board: Analyze current state
    Board-->>Solver: Board state
    Solver->>Solver: Calculate solution path
    Solver-->>GameView: List<Move>
    GameView->>GameView: Animate solution

    %% Save Game
    User->>GameView: Select "Save Game"
    GameView->>GameView: Create save data
    GameView->>GameView: Show file chooser
    User->>GameView: Select save location
    GameView->>GameView: Write game state to file

    %% Load Game
    User->>MainMenu: Select "Open Saved Game"
    MainMenu->>MainMenu: Show file chooser
    User->>MainMenu: Select save file
    MainMenu->>Resources: loadJsonObjectFromPath(path)
    Resources-->>MainMenu: JsonObject
    MainMenu->>Board: createBoard(name, boardString)
    Board-->>MainMenu: Board
    MainMenu->>GameView: Create GameView with loaded state
```

*Last updated: September 27, 2025*