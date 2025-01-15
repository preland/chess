package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    ChessPosition position[] = new ChessPosition[64];
    ChessPiece pieces[] = new ChessPiece[64];
    public ChessBoard() {
        for (int i = 0; i < 64; i++) {
            position[i] = new ChessPosition(i/8, i%8);
            pieces[i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.NONE);
        }
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "position=" + Arrays.toString(position) +
                ", pieces=" + Arrays.toString(pieces) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(position, that.position) && Objects.deepEquals(pieces, that.pieces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(position), Arrays.hashCode(pieces));
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int idx = (position.getRow()/8) + (position.getColumn()%8);
        this.pieces[idx] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return this.pieces[position.getRow()/8 + position.getColumn()%8];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        String board = """
                rnbqkbnr
                pppppppp
                ++++++++
                ++++++++
                ++++++++
                ++++++++
                PPPPPPPP
                RNBQKBNR
                """;
        for (int i = 55; i > 0;) {
            System.out.print(i+ " ");
            ChessPiece piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.NONE);
            switch(board.charAt(i)) {
                case 'R':
                     piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
                    break;
                case 'N':
                     piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
                    break;
                case 'B':
                     piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
                    break;
                case 'K':
                     piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
                    break;
                case 'Q':
                     piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
                    break;
                case 'P':
                     piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
                    break;
                case 'r':
                     piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
                    break;
                case 'n':
                     piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
                    break;
                case 'b':
                     piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
                    break;
                case 'k':
                     piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
                    break;
                case 'q':
                     piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
                    break;
                case 'p':
                     piece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
                    break;
                default:
                    break;
            }
            addPiece(new ChessPosition(i/8,i%8),  piece);
            if(i != 55 && (i % 8 == 7)) {
                i -= 8;
            } else {
                i++;
            }
        }
    }
}
