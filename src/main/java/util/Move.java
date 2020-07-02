package util;

import lombok.AllArgsConstructor;

/**
 * This class represents the move coordinates for the game. Since move will be
 * used in multiple classes its easier to represent the start and end points of
 * a move using one object.
 *
 * @author Mohamed Radwan
 * @author Samuel Gamelin
 * @version 4.0
 */
@AllArgsConstructor
public final class Move {

    public final int xStart;

    public final int yStart;

    public final int xEnd;

    public final int yEnd;

    /**
     * This method is used to compute the direction of movement. Since each piece
     * has move restrictions this method will be used to initially determine if a
     * move is valid.
     *
     * @return Direction enum HORIZONTAL, VERTICAL, INVALID
     */
    public MoveDirection direction() {
        if ((xStart == xEnd) && (yStart != yEnd)) {
            return MoveDirection.VERTICAL;
        } else if ((xStart != xEnd) && (yStart == yEnd)) {
            return MoveDirection.HORIZONTAL;
        }
        return MoveDirection.INVALID;
    }

    /**
     * This method calculates the distance the object needs to move in the x
     * direction.
     *
     * @return the distance in the Horizontal direction.
     */
    public int xDistance() {
        return xEnd - xStart;
    }

    /**
     * This method calculates the distance the object needs to move in the x
     * direction.
     *
     * @return the distance in the Vertical direction.
     */
    public int yDistance() {
        return yEnd - yStart;
    }

    public enum MoveDirection {
        HORIZONTAL, VERTICAL, INVALID
    }
}
