/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import java.util.regex.Pattern;

import static loa.Piece.*;
import static loa.Square.*;

/** Represents the state of a game of Lines of Action.
 *  @author Joe Mo
 */
class Board {

    /** Default number of moves for each side that results in a draw. */
    static final int DEFAULT_MOVE_LIMIT = 60;

    /** Pattern describing a valid square designator (cr). */
    static final Pattern ROW_COL = Pattern.compile("^[a-h][1-8]$");

    /** A Board whose initial contents are taken from INITIALCONTENTS
     *  and in which the player playing TURN is to move. The resulting
     *  Board has
     *        get(col, row) == INITIALCONTENTS[row][col]
     *  Assumes that PLAYER is not null and INITIALCONTENTS is 8x8.
     *
     *  CAUTION: The natural written notation for arrays initializers puts
     *  the BOTTOM row of INITIALCONTENTS at the top.
     */
    Board(Piece[][] initialContents, Piece turn) {
        initialize(initialContents, turn);
    }

    /** A new board in the standard initial position. */
    Board() {
        this(INITIAL_PIECES, BP);
    }

    /** A Board whose initial contents and state are copied from
     *  BOARD. */
    Board(Board board) {
        this();
        copyFrom(board);
    }

    /** Set my state to CONTENTS with SIDE to move. */
    void initialize(Piece[][] contents, Piece side) {
        for (int i = 0; i < _board.length; i++) {
            _board[i] = contents[i / BOARD_SIZE][i % BOARD_SIZE];
        }

        _turn = side;
        _moveLimit = DEFAULT_MOVE_LIMIT;
    }

    /** Set me to the initial configuration. */
    void clear() {
        initialize(INITIAL_PIECES, BP);
        _moves.clear();
        _whiteRegionSizes.clear();
        _blackRegionSizes.clear();
        _winnerKnown = false;
        _winner = null;
        _subsetsInitialized = false;
    }

    /** Set my state to a copy of BOARD. */
    void copyFrom(Board board) {
        if (board == this) {
            return;
        }

        _moves.clear();
        _whiteRegionSizes.clear();
        _blackRegionSizes.clear();
        _moves.addAll(board._moves);
        _whiteRegionSizes.addAll(board._whiteRegionSizes);
        _blackRegionSizes.addAll(board._blackRegionSizes);
        System.arraycopy(board._board, 0, _board, 0, board._board.length);

        _turn = board._turn;
        _moveLimit = board._moveLimit;
        _winnerKnown = board._winnerKnown;
        _winner = board._winner;
        _subsetsInitialized = board._subsetsInitialized;
    }

    /** Return the contents of the square at SQ. */
    Piece get(Square sq) {
        return _board[sq.index()];
    }

    /** Set the square at SQ to V and set the side that is to move next
     *  to NEXT, if NEXT is not null. */
    void set(Square sq, Piece v, Piece next) {
        _turn = next != null ? next : _turn;
        _board[sq.index()] = v;
    }

    /** Set the square at SQ to V, without modifying the side that
     *  moves next. */
    void set(Square sq, Piece v) {
        set(sq, v, null);
    }

    /** Set limit on number of moves by each side that results in a tie to
     *  LIMIT, where 2 * LIMIT > movesMade(). */
    void setMoveLimit(int limit) {
        if (2 * limit <= movesMade()) {
            throw new IllegalArgumentException("move limit too small");
        }
        _moveLimit = 2 * limit;
    }

    /** Assuming isLegal(MOVE), make MOVE. This function assumes that
     *  MOVE.isCapture() will return false.  If it saves the move for
     *  later retraction, makeMove itself uses MOVE.captureMove() to produce
     *  the capturing move. */
    void makeMove(Move move) {
        assert isLegal(move);

        _subsetsInitialized = false;
        Square from = move.getFrom(), to = move.getTo();
        Piece piece = get(from);

        _moves.add(get(to) == piece.opposite() ? move.captureMove() : move);
        set(to, piece, piece.opposite());
        set(from, EMP);
    }

    /** Retract (unmake) one move, returning to the state immediately before
     *  that move.  Requires that movesMade () > 0. */
    void retract() {
        if (movesMade() < 1) {
            return;
        }

        if (_winnerKnown) {
            _winnerKnown = false;
            _winner = null;
        }

        _subsetsInitialized = false;
        Move move = _moves.get(movesMade() - 1);
        Square from = move.getFrom(), to = move.getTo();
        Piece piece = get(to);

        _moves.remove(movesMade() - 1);
        set(to, move.isCapture() ? piece.opposite() : EMP, _turn.opposite());
        set(from, piece);
    }

    /** Return the Piece representing who is next to move. */
    Piece turn() {
        return _turn;
    }

    /** Return true iff FROM - TO is a legal move for the player currently on
     *  move. */
    boolean isLegal(Square from, Square to) {
        if (from.isValidMove(to)) {
            int dir = from.direction(to);
            int steps = piecesInLine(dir, from, true);
            return from.distance(to) == steps && !blocked(from, to);
        }
        return false;
    }

    /** Return true iff MOVE is legal for the player currently on move.
     *  The isCapture() property is ignored. */
    boolean isLegal(Move move) {
        return isLegal(move.getFrom(), move.getTo());
    }

    /** Return a sequence of all legal moves from this position. */
    List<Move> legalMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        for (Square sq : ALL_SQUARES) {
            if (get(sq) == _turn) {
                moves.addAll(legalMoves(sq));
            }
        }
        return moves;
    }

    /** Return a sequence of all legal moves from a certain square SQ. */
    List<Move> legalMoves(Square sq) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int dir = 0; dir < 8; dir++) {
            int steps = piecesInLine(dir, sq, true);
            Square dest = sq.moveDest(dir, steps);

            if (dest != null && isLegal(sq, dest)) {
                boolean isCapture = get(dest) != EMP;
                Move move = isCapture ? Move.mv(sq, dest, true)
                        : Move.mv(sq, dest);
                moves.add(move);
            }
        }
        return moves;
    }

    /** Return true iff the game is over (either player has all his
     *  pieces contiguous or there is a tie). */
    boolean gameOver() {
        return winner() != null;
    }

    /** Return true iff SIDE's pieces are continguous. */
    boolean piecesContiguous(Piece side) {
        return getRegionSizes(side).size() == 1;
    }

    /** Return the winning side, if any.  If the game is not over, result is
     *  null.  If the game has ended in a tie, returns EMP. */
    Piece winner() {
        if (!_winnerKnown) {
            if (piecesContiguous(WP)) {
                _winner = WP;
                _winnerKnown = true;
            } else if (piecesContiguous(BP)) {
                _winner = BP;
                _winnerKnown = true;
            } else if (movesMade() >= _moveLimit) {
                _winner = EMP;
                _winnerKnown = true;
            }
        }
        return _winner;
    }

    /** Return the total number of moves that have been made (and not
     *  retracted).  Each valid call to makeMove with a normal move increases
     *  this number by 1. */
    int movesMade() {
        return _moves.size();
    }

    @Override
    public boolean equals(Object obj) {
        Board b = (Board) obj;
        return Arrays.deepEquals(_board, b._board) && _turn == b._turn;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(_board) * 2 + _turn.hashCode();
    }

    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("===%n");
        for (int r = BOARD_SIZE - 1; r >= 0; r -= 1) {
            out.format("    ");
            for (int c = 0; c < BOARD_SIZE; c += 1) {
                out.format("%s ", get(sq(c, r)).abbrev());
            }
            out.format("%n");
        }
        out.format("Next move: %s%n===", turn().fullName());
        return out.toString();
    }

    /** Return true if a move from FROM to TO is blocked by an opposing
     *  piece or by a friendly piece on the target square. */
    private boolean blocked(Square from, Square to) {
        assert from.isValidMove(to);

        Piece current = get(from);
        Piece opposing = current.opposite();

        if (get(to) == current) {
            return true;
        }

        int dir = from.direction(to);
        Square sq = from.moveDest(dir, 1);
        while (sq != to) {
            if (get(sq) == opposing) {
                return true;
            }
            sq = sq.moveDest(dir, 1);
        }
        return false;
    }

    /** Return the size of the as-yet unvisited cluster of squares
     *  containing P at and adjacent to SQ.  VISITED indicates squares that
     *  have already been processed or are in different clusters.  Update
     *  VISITED to reflect squares counted. */
    private int numContig(Square sq, boolean[][] visited, Piece p) {
        if (get(sq) == p && !visited[sq.row()][sq.col()]) {
            int count = 1;
            visited[sq.row()][sq.col()] = true;
            for (Square adj : sq.adjacent()) {
                count += numContig(adj, visited, p);
            }
            return count;
        }
        return 0;
    }

    /** Set the values of _whiteRegionSizes and _blackRegionSizes. */
    private void computeRegions() {
        if (_subsetsInitialized) {
            return;
        }
        _whiteRegionSizes.clear();
        _blackRegionSizes.clear();

        boolean[][] visited = new boolean[BOARD_SIZE][BOARD_SIZE];
        for (Square sq : ALL_SQUARES) {
            int row = sq.row(), col = sq.col();
            Piece p = get(sq);
            if (p == WP && !visited[row][col]) {
                _whiteRegionSizes.add(numContig(sq, visited, p));
            } else if (p == BP && !visited[row][col]) {
                _blackRegionSizes.add(numContig(sq, visited, p));
            }
        }

        Collections.sort(_whiteRegionSizes, Collections.reverseOrder());
        Collections.sort(_blackRegionSizes, Collections.reverseOrder());
        _subsetsInitialized = true;
    }

    /** Return the sizes of all the regions in the current union-find
     *  structure for side S. */
    List<Integer> getRegionSizes(Piece s) {
        computeRegions();
        if (s == WP) {
            return _whiteRegionSizes;
        } else {
            return _blackRegionSizes;
        }
    }

    /** Return number of pieces in a line on the board given the DIRECTION,
     *  the initial square SQ, and boolean BOTHDIR (to indicate whether to
     *  count the pieces on the line both in front of and behind the initial
     *  square). */
    int piecesInLine(int direction, Square sq, boolean bothDir) {
        int count = get(sq) != EMP ? 1 : 0;
        int dist = 1;

        Square curr = sq.moveDest(direction, dist);
        while (curr != null) {
            dist++;
            count += get(curr) != EMP ? 1 : 0;
            curr = sq.moveDest(direction, dist);
        }

        if (bothDir) {
            direction = (direction + 4) % 8;
            curr = sq.moveDest(direction, 1);
            count += curr != null ? piecesInLine(direction, curr, false) : 0;
        }

        return count;
    }

    /** The standard initial configuration for Lines of Action (bottom row
     *  first). */
    static final Piece[][] INITIAL_PIECES = {
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { EMP, BP,  BP,  BP,  BP,  BP,  BP,  EMP }
    };

    /** Current contents of the board.  Square S is at _board[S.index()]. */
    private final Piece[] _board = new Piece[BOARD_SIZE  * BOARD_SIZE];

    /** List of all unretracted moves on this board, in order. */
    private final ArrayList<Move> _moves = new ArrayList<>();
    /** Current side on move. */
    private Piece _turn;
    /** Limit on number of moves before tie is declared.  */
    private int _moveLimit;
    /** True iff the value of _winner is known to be valid. */
    private boolean _winnerKnown;
    /** Cached value of the winner (BP, WP, EMP (for tie), or null (game still
     *  in progress).  Use only if _winnerKnown. */
    private Piece _winner;

    /** True iff subsets computation is up-to-date. */
    private boolean _subsetsInitialized;

    /** List of the sizes of contiguous clusters of pieces, by color. */
    private final ArrayList<Integer>
        _whiteRegionSizes = new ArrayList<>(),
        _blackRegionSizes = new ArrayList<>();
}
