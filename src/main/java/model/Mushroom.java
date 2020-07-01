package model;

/***
 * A class representing a Mushroom piece.
 *
 * @author Abdalla El Nakla
 * @author Samuel Gamelin
 */
public class Mushroom extends Piece {

    /**
     * Construct a new mushroom.
     */
    public Mushroom() {
        super(PieceType.MUSHROOM);
    }

    @Override
    public String toString() {
        return "MU";
    }
}
