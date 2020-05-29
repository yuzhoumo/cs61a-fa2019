package loa;

import java.util.ArrayList;
import java.util.Collection;
import static loa.Piece.*;

/** Methods for calculating heuristic scores.
 *  @author Joe Mo
 */
public class Heuristics {

    /** Values for positions on the board. Edge values are penalized heavily
     *  while central values have higher heuristic scores. */
    private static final int[] PIECE_SQUARE_TABLE =
        { -50, -25, -20, -20, -20, -20, -25, -50,
          -25,  10,  10,  10,  10,  10,  10, -25,
          -20,  10,  25,  25,  25,  25,  10, -20,
          -20,  10,  25,  50,  50,  25,  10, -20,
          -20,  10,  25,  50,  50,  25,  10, -20,
          -20,  10,  25,  25,  25,  25,  10, -20,
          -25,  10,  10,  10,  10,  10,  10, -25,
          -50, -25, -20, -20, -20, -20, -25, -50 };

    /** Values for the sum of the minimum possible distances from the center
     *  of mass for any number of pieces from 1-12 (index = numPieces - 1). */
    private static final int[] MIN_DISTANCE_SUM =
        { 0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 12, 14 };

    /** Return a heuristic value for BOARD, given BOARD and GAME. */
    public static int heuristicEstimate(Board board, Game game) {
        int sign = board.turn() == BP ? -1 : 1;
        initialize(board, board.turn(), game);
        return sign * aggregateScore();
    }

    /** Return combined score of all of heuristics metrics. */
    private static int aggregateScore() {
        ArrayList<Double> scores = new ArrayList<>();

        Square com = com();
        scores.add(centralizationScore());
        scores.add(comPositionScore(com));
        scores.add(1000 * concentrationScore(com));

        double sum = getRandom(1, 2);
        for (double score : scores) {
            sum += score;
        }

        return (int) Math.round(sum);
    }

    /** Return a heuristic score based on an average of how centralized the
     *  pieces on BOARD are. The score is higher for pieces closer to the
     *  center of the board and lower for pieces closer to the edges. */
    private static double centralizationScore() {
        double total = 0, numPieces = _pieceLocations.size();
        for (Square sq : _pieceLocations) {
            total += PIECE_SQUARE_TABLE[sq.index()];
        }
        return total / numPieces;
    }

    /** Return a heuristic score based on the position of the center of mass.
     *  A COM closer to the edges results in a better score. */
    private static double comPositionScore(Square com) {
        final int maxVal = 50;
        double total = 0, numPieces = _pieceLocations.size();
        for (Square sq : _pieceLocations) {
            int score = PIECE_SQUARE_TABLE[sq.index()];
            if (score < 0) {
                score = -score;
            } else {
                score = maxVal - score;
            }
            total += score;
        }
        return total / numPieces;
    }

    /** Return a heuristic score based on how closely concentrated the pieces
     *  are relative to the COM (center of mass) of the pieces. */
    private static double concentrationScore(Square com) {
        double sumOfDistances = 0;
        double sumOfMinDistances = MIN_DISTANCE_SUM[_pieceLocations.size() - 1];

        for (Square sq : _pieceLocations) {
            sumOfDistances += com.distance(sq);
        }

        double surplusOfDistances = sumOfDistances - sumOfMinDistances;
        return 1.0 / surplusOfDistances;
    }

    /** Return the square at which the center of mass of all the pieces is
     *  located. */
    private static Square com() {
        double sumRows = 0, sumCols = 0;
        double numPieces = _pieceLocations.size();
        for (Square sq : _pieceLocations) {
            sumRows += sq.row();
            sumCols += sq.col();
        }

        return Square.sq((int) Math.round(sumCols / numPieces),
                (int) Math.round(sumRows / numPieces));
    }

    /** Sets the _pieceLocations variable to contain all the squares on BOARD
     *  that contain pieces equal to SIDE. Set _board to BOARD. Set _game to
     *  GAME. */
    private static void initialize(Board board, Piece side, Game game) {
        ArrayList<Square> locations = new ArrayList<>();
        for (Square sq : Square.ALL_SQUARES) {
            if (board.get(sq) == side) {
                locations.add(sq);
            }
        }
        _pieceLocations = locations;
        _game = game;
    }

    /** Return a random integer between MIN and MAX. */
    private static int getRandom(int min, int max) {
        return _game.randInt((max - min) + 1) + min;
    }

    /** Used to store the current game. */
    private static Game _game;

    /** Used to store all the squares on the board that contain the current
     *  side's pieces. */
    private static Collection<Square> _pieceLocations;

}
