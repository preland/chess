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
    ChessPosition[] board = new ChessPosition[64];
    ChessPiece[] pieces = new ChessPiece[64];
    public ChessBoard() {
        for (int i = 0; i < 64; i++) {
            board[i] = new ChessPosition(i/8,i%8);
            pieces[i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.NONE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(board, that.board) && Objects.deepEquals(pieces, that.pieces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(board), Arrays.hashCode(pieces));
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {

        int idx = (position.getRow()*8) + position.getColumn();
        System.out.println(idx);
        pieces[idx] = piece;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int idx = (position.getRow()*8) + position.getColumn();
        return pieces[idx];
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        String layout = """
                rnbqkbnr
                pppppppp
                ++++++++
                ++++++++
                ++++++++
                ++++++++
                PPPPPPPP
                RNBQKBNR
                """;
        for (int i = 0; i < 64; i++) {
            switch (layout.charAt(i)) {
                case 'r':
                    pieces[i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
                    break;
                case 'n':
                    pieces[i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
                    break;
                case 'b':
                    pieces[i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
                    break;
                case 'q':
                    pieces[i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
                    break;
                case 'k':
                    pieces[i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
                    break;
                case 'p':
                    pieces[i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
                    break;
                case 'R':
                    pieces[i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
                    break;
                case 'N':
                    pieces[i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
                    break;
                case 'B':
                    pieces[i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
                    break;
                case 'Q':
                    pieces[i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
                    break;
                case 'K':
                    pieces[i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
                    break;
                case 'P':
                    pieces[i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
                    break;
                default:
                    pieces[i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.NONE);
                    break;
            }
            System.out.print(pieces[i].getPieceType());
        }
        //throw new RuntimeException("Not implemented");
    }
}
