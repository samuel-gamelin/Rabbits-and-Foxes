# Class Diagram

This diagram shows the actual class structure of the Rabbits and Foxes game following the MVC pattern.

```mermaid
classDiagram
    %% Core Model Classes
    class Piece {
        <<abstract>>
        -final PieceType pieceType
        +Piece(PieceType pieceType)
        +getPieceType(): PieceType
        +toString(): String*
    }

    class PieceType {
        <<enumeration>>
        FOX
        MUSHROOM
        RABBIT
    }

    class MovablePiece {
        <<interface>>
        +move(Move move, Board board): boolean
        +getPossibleMoves(Board board, int x, int y): List~Move~
    }

    class Rabbit {
        -final RabbitColour colour
        +Rabbit(RabbitColour colour)
        +createRabbit(String str): Rabbit
        +move(Move move, Board board): boolean
        +getPossibleMoves(Board board, int x, int y): List~Move~
        -validatePath(Move move, Board board): boolean
        -verticalMove(Move move, Board board): boolean
        -horizontalMove(Move move, Board board): boolean
        +getColour(): RabbitColour
        +toString(): String
    }

    class RabbitColour {
        <<enumeration>>
        BROWN
        WHITE
        GRAY
    }

    class Fox {
        -final Direction direction
        -final Fox otherHalf
        -final FoxType foxType
        -final boolean id
        +Fox(Direction direction, boolean id)
        +createFox(String str): Fox
        +move(Move move, Board board): boolean
        +getPossibleMoves(Board board, int x, int y): List~Move~
        -validatePath(Move move, Board board, boolean location): boolean
        -getRelativeLocation(): boolean
        +getDirection(): Direction
        +getOtherHalf(): Fox
        +getFoxType(): FoxType
        +id(): boolean
        +toString(): String
    }

    class Direction {
        <<enumeration>>
        LEFT
        RIGHT
        UP
        DOWN
    }

    class FoxType {
        <<enumeration>>
        HEAD
        TAIL
    }

    class Mushroom {
        +Mushroom()
        +toString(): String
    }

    class Board {
        +SIZE: int = 5
        +EMPTY: String = "X"
        -final Tile[][] tiles
        -final List~BoardListener~ boardListeners
        -String name
        +Board(String name)
        +Board(Board board)
        +createBoard(String name, String representation): Board
        +move(Move move): boolean
        +tileType(int x, int y): boolean
        +isInWinningState(): boolean
        +isOccupied(int x, int y): boolean
        +getPiece(int x, int y): Piece
        +setPiece(Piece piece, int x, int y): boolean
        +removePiece(int x, int y): Piece
        +addListener(BoardListener boardListener): void
        +getPossibleMoves(): List~Move~
        +getName(): String
        +setName(String name): void
        +toString(): String
    }

    class BoardListener {
        <<interface>>
        +handleBoardChange(): void
    }

    class Tile {
        +HOLE: char = 'H'
        +FILLED: char = 'F'
        -type: char
        +Tile(char type)
        +getType(): char
        +isHole(): boolean
        +isFilled(): boolean
        +toString(): String
    }

    %% Utility Classes
    class Move {
        +final int xStart
        +final int yStart
        +final int xEnd
        +final int yEnd
        +Move(int xStart, int yStart, int xEnd, int yEnd)
        +direction(): MoveDirection
        +xDistance(): int
        +yDistance(): int
    }

    class MoveDirection {
        <<enumeration>>
        HORIZONTAL
        VERTICAL
        INVALID
    }

    class Node {
        -final Board board
        +Node(Board board)
        +getChildren(): Set~Node~
        +getMoveTo(Node node): Move
        +isWinningNode(): boolean
        +getBoard(): Board
    }

    class Solver {
        -lastHint: List~Node~
        +getNextBestMove(Board board): Move
        -cleanNodeList(List~Node~ nodeList): List~Node~
        -breadthFirstSearch(Node root): List~Node~
    }

    %% Controller Classes
    class GameController {
        -final List~Integer~ moveList
        -Board board
        -boolean isDefaultLevel
        -int currentLevel
        -ArrayDeque~Move~ undoMoveStack
        -ArrayDeque~Move~ redoMoveStack
        +GameController(Board board, int level)
        +registerClick(int x, int y): ClickValidity
        +getPossibleMoves(int x, int y): List~Move~
        +clearPendingPosition(): void
        +reset(): Board
        +getNextBestMove(): Move
        +undoMove(): boolean
        +redoMove(): boolean
        +incrementLevel(): void
        +isDefaultLevel(): boolean
        +getCurrentLevel(): int
        +getUndoMoveStack(): ArrayDeque~Move~
        +setUndoMoveStack(ArrayDeque~Move~): void
        +getRedoMoveStack(): ArrayDeque~Move~
        +setRedoMoveStack(ArrayDeque~Move~): void
    }

    class ClickValidity {
        <<enumeration>>
        VALID
        INVALID
        VALID_MOVE_MADE
        INVALID_MOVE_MADE
    }

    %% View Classes
    class GameView {
        -final JButton menuReset, menuHelp, menuHint, menuUndo, menuRedo, menuMain, menuSaveButton, menuQuit
        -final JButton[][] buttons
        -final GameController gameController
        -JCheckBox showPossibleMovesBox
        -Board board
        -boolean gameState
        -Move bestMove
        +GameView(Board board, int level)
        +GameView(Board board, int level, ArrayDeque~Move~, ArrayDeque~Move~)
        +save(String path): boolean
        +handleBoardChange(): void
        +actionPerformed(ActionEvent e): void
        +mouseClicked(MouseEvent e): void
        +run(): void
    }

    class MainMenu {
        -final JButton btnStart, btnSelectLevel, btnBuildLevel, btnHelp, btnLoadGame, btnQuitGame
        +MainMenu()
        +main(String[] args): void
        +actionPerformed(ActionEvent e): void
        -addMainMenuButton(JButton button): void
    }

    class LevelSelector {
        -final JButton btnStartLevel, btnMainMenu, btnCustomLevels, btnNextPage, btnLastPage
        -final JButton btnLeftLevel, btnMiddleLevel, btnRightLevel, btnDeleteLevel
        -final List~Board~ allDefaultLevels
        -List~Board~ allCustomLevels
        -int pageNumber, lastPage
        -boolean custom
        +LevelSelector()
        +actionPerformed(ActionEvent e): void
        -updateView(List~Board~ levelList): void
    }

    class LevelBuilder {
        -final JButton menuReset, menuHelp, deletePiece, flipFox, menuMainScreen, saveBoard
        -final JButton[][] buttons
        -Board board
        -int numberOfFoxes, numberOfMushrooms
        +LevelBuilder()
        +actionPerformed(ActionEvent e): void
        +handleBoardChange(): void
        +mouseClicked(MouseEvent e): void
        -placePiece(int x, int y): void
    }

    %% Utility Classes
    class Resources {
        +INVALID_MOVE: Clip
        +SOLVED: Clip
        +RABBIT_BROWN: ImageIcon
        +RABBIT_WHITE: ImageIcon
        +RABBIT_GRAY: ImageIcon
        +FOX_HEAD_UP: ImageIcon
        +FOX_HEAD_DOWN: ImageIcon
        +FOX_HEAD_LEFT: ImageIcon
        +FOX_HEAD_RIGHT: ImageIcon
        +FOX_TAIL_UP: ImageIcon
        +FOX_TAIL_DOWN: ImageIcon
        +FOX_TAIL_LEFT: ImageIcon
        +FOX_TAIL_RIGHT: ImageIcon
        +MUSHROOM: ImageIcon
        +NUMBER_OF_LEVELS: int
        +getImageIconByName(String fieldName): ImageIcon
        +loadJsonObjectFromPath(String path, boolean isAbsolutePath): JsonObject
        +getDefaultBoardByLevel(int level): Board
        +getAllDefaultBoards(): List~Board~
        +getAllUserBoards(): List~Board~
        +addUserLevel(Board board): boolean
        +removeUserLevel(String name): void
    }

    class GUIUtilities {
        +SIDE_LENGTH: double
        +FONT_SIZE: int
        +fc: JFileChooser
        +configureFrame(JFrame frame): void
        +applyDefaults(): void
        +displayMessageDialog(Component parent, String message, String title): void
        +displayOptionDialog(Component parent, String message, String title, Object[] options): int
        +bindKeyStroke(JComponent component, String keystroke, String actionName, Runnable method): void
        +createMenuBarButton(String text, boolean enableShortcut): JButton
        +generateGameBoardButton(int x, int y): JButton
        +stylizeButton(JButton button, ActionListener actionListener): void
        +clearButtonBorders(JButton[][] buttons): void
        +updateView(JButton[][] buttons, Board board): void
    }

    %% Relationships
    Piece <|-- Rabbit : extends
    Piece <|-- Fox : extends
    Piece <|-- Mushroom : extends
    MovablePiece <|.. Rabbit : implements
    MovablePiece <|.. Fox : implements

    Piece --> PieceType : uses
    Rabbit --> RabbitColour : uses
    Fox --> Direction : uses
    Fox --> FoxType : uses
    Fox --> Fox : otherHalf
    Move --> MoveDirection : uses

    Board "1" *-- "25" Tile : contains
    Board --> BoardListener : notifies

    GameController --> Board : manages
    GameController --> Move : creates
    GameController --> ClickValidity : returns

    GameView --> GameController : delegates
    GameView --> Board : observes
    BoardListener <|.. GameView : implements
    BoardListener <|.. LevelBuilder : implements

    LevelSelector --> Board : selects
    LevelBuilder --> Board : creates
    MainMenu --> GameView : launches
    MainMenu --> LevelSelector : launches
    MainMenu --> LevelBuilder : launches

    Solver --> Board : analyzes
    Solver --> Node : uses
    Node --> Board : wraps
    Node --> Move : generates

    Resources --> Board : loads
    GUIUtilities --> GameView : supports
    GUIUtilities --> LevelSelector : supports
    GUIUtilities --> LevelBuilder : supports
    GUIUtilities --> MainMenu : supports
```

*Last updated: September 27, 2025*