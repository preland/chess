package chess;

import javax.net.ssl.SSLContext;
import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    ChessPiece pieces[] = new ChessPiece[64];
    public ChessBoard() {
        for (int i = 0; i < 64; i++) {
            pieces[i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        }
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "pieces=" + Arrays.toString(pieces) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(pieces, that.pieces);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(pieces);
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        if(null == piece) {
            System.out.println("here");
            piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        }

        pieces[positionToIndex(position)] = piece;
        //throw new RuntimeException("Not implemented");
    }
    ChessPosition indexToPosition(int i) {
        return new ChessPosition((i/8)+1, (i%8)+1);
    }
    int positionToIndex(ChessPosition position) {
        return (position.getColumn()-1) + (position.getRow()-1)*8;
    }
    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return pieces[positionToIndex(position)];
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
//        String board = """
//                RNBQKBNR
//                PPPPPPPP
//                ++++++++
//                ++++++++
//                ++++++++
//                ++++++++
//                pppppppp
//                rnbqkbnr
//                """;
//        char boardArray[] = board.toCharArray();
//        boolean ignore = true;
//        for (int i = positionToIndex(new ChessPosition(8,1)); i > -1;) {
//
//            //System.out.println(i);
//            switch(boardArray[i]) {
//                case 'k':
//                    addPiece(indexToPosition(i), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
//                    break;
//                case 'q':
//                    addPiece(indexToPosition(i), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));
//                    break;
//                case 'b':
//                    addPiece(indexToPosition(i), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
//                    break;
//                case 'n':
//                    addPiece(indexToPosition(i), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
//                    break;
//                case 'r':
//                    addPiece(indexToPosition(i), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
//                    break;
//                case 'p':
//                    addPiece(indexToPosition(i), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN));
//                    break;
//                case 'K':
//                    addPiece(indexToPosition(i), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING));
//                    break;
//                case 'Q':
//                    addPiece(indexToPosition(i), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN));
//                    break;
//                case 'B':
//                    addPiece(indexToPosition(i), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP));
//                    break;
//                case 'N':
//                    addPiece(indexToPosition(i), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT));
//                    break;
//                case 'R':
//                    addPiece(indexToPosition(i), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK));
//                    break;
//                case 'P':
//                    addPiece(indexToPosition(i), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN));
//                    break;
//                default:
//                    System.out.println("here");
//                    addPiece(indexToPosition(i), new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.NONE));
//                    break;
//            }
//            if (i%8 == 7 && !ignore) {
//                i -= 15;
//                ignore = true;
//            } else {
//                i++;
//                ignore = false;
//            }
//
//        }// 56 57 58 59 60 61 62 63 48 49 50 51 52 53 54 55 40
//        //63-48=15; 55-32=23
//        //throw new RuntimeException("Not implemented");
    }
}
