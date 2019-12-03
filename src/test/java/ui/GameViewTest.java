package ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import resources.Resources;
import util.Move;

import java.io.File;
import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameViewTest {
    private Board board;
    private GameView gameView;

    @BeforeEach
    public void setUp() {
        board = Resources.getDefaultBoardByLevel(1);
        gameView = new GameView(board, 1);
        gameView.setVisible(false);
    }

    @Test
    public void testSaveGame() {
        assertTrue(gameView.save("testSave.json"));

        Gson gson = new Gson();

        JsonObject savedBoardObject = Resources.loadJsonObjectFromPath(new File("testSave.json").getAbsolutePath(),
                true); // Reload the board as a JSON object

        JsonObject jsonObject = new JsonObject(); // Create a mock JSON object that should be equal to the one that was just loaded
        jsonObject.addProperty("name", board.getName());
        jsonObject.addProperty("board", board.toString());
        jsonObject.addProperty("undoMoves", gson.toJson(new ArrayDeque<Move>()));
        jsonObject.addProperty("redoMoves", gson.toJson(new ArrayDeque<Move>()));

        assertEquals(jsonObject, savedBoardObject); // Check that these two JSON objects are the same

        assertTrue(new File("testSave.json").delete()); // Ensure the file is properly deleted
    }
}
