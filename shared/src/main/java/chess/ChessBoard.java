package chess;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import static java.lang.Math.abs;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard implements Cloneable{

    public ChessPiece[] pieces;
    public ChessBoard() {
        this.pieces = new ChessPiece[64];
        for (int i = 0; i < 64; i++) {
            pieces[i] = null;
        }


    }

    public Object clone() throws CloneNotSupportedException {
        ChessBoard clonedBoard = (ChessBoard) super.clone();
        clonedBoard.pieces = this.pieces.clone();
        return clonedBoard;
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

    public int positionToIndex(ChessPosition position) {
        return ((position.row-1)*8) + (position.col-1);
    }
    public ChessPosition indexToPosition(int i) {
        return new ChessPosition((i/8)+1,(i%8)+1);
    }
    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        //throw new RuntimeException("Not implemented");
        pieces[positionToIndex(position)] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        //throw new RuntimeException("Not implemented");
        return pieces[positionToIndex(position)];

    }

    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                  ChessPiece piece = pieces[i*8 + j];
                if(piece==null) {
                  System.out.print("+");
                  continue;
                }
                switch (piece.getPieceType()) {
                  case ChessPiece.PieceType.ROOK:
                    if(piece.getTeamColor()==ChessGame.TeamColor.WHITE){
                      System.out.print('R');
                    }
                    else{
                      System.out.print('r');
                    }
                    break;
                  case ChessPiece.PieceType.KNIGHT:
                    if(piece.getTeamColor()==ChessGame.TeamColor.WHITE){
                      System.out.print('N');
                    }
                    else{
                      System.out.print('n');
                    }
                    break;
                  case ChessPiece.PieceType.BISHOP:
                    if(piece.getTeamColor()==ChessGame.TeamColor.WHITE){
                      System.out.print('B');
                    }
                    else{
                      System.out.print('b');
                    }
                    break;
                  case ChessPiece.PieceType.KING:
                    if(piece.getTeamColor()==ChessGame.TeamColor.WHITE){
                      System.out.print('K');
                    }
                    else{
                      System.out.print('k');
                    }
                    break;
                  case ChessPiece.PieceType.QUEEN:
                    if(piece.getTeamColor()==ChessGame.TeamColor.WHITE){
                      System.out.print('Q');
                    }
                    else{
                      System.out.print('q');
                    }
                    break;
                  case ChessPiece.PieceType.PAWN:
                    if(piece.getTeamColor()==ChessGame.TeamColor.WHITE){
                      System.out.print('P');
                    }
                    else{
                      System.out.print('p');
                    }
                    break;
                  default:
                    System.out.print('+');
                    break;
                }
            }
            System.out.println("");
        }
    }
    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
            String layout =
                    "rnbqkbnr\n" +
                    "pppppppp\n" +
                    "++++++++\n" +
                    "++++++++\n" +
                    "++++++++\n" +
                    "++++++++\n" +
                    "PPPPPPPP\n" +
                    "RNBQKBNR\n";
            ChessPiece tempPiece = null;
            int row =8;
            int col =1;
            boolean stupdifix = false;
            for(char c: layout.toCharArray()) {
                if(!stupdifix){
                    stupdifix = true;
                    continue;
                }
                switch (c) {
                    case '\n':
                        col = 1;
                        row--;
                        break;
                    case 'P':
                        //System.out.println("P");
                        tempPiece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
                        break;
                    case 'K':
                        tempPiece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
                        break;
                    case 'Q':
                        tempPiece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
                        break;
                    case 'B':
                        tempPiece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
                        break;
                    case 'N':
                        tempPiece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
                        break;
                    case 'R':
                        tempPiece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
                        break;
                    case 'p':
                        tempPiece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
                        break;
                    case 'k':
                        tempPiece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
                        break;
                    case 'q':
                        tempPiece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
                        break;
                    case 'b':
                        tempPiece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
                        break;
                    case 'n':
                        tempPiece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
                        break;
                    case 'r':
                        tempPiece = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
                        break;
                    default:
                        tempPiece = null;
                        break;
                }
                if(row==0) {
                    break;
                }
                //System.out.println(row);
                if(c != '\n') {
                addPiece(new ChessPosition(row, col), tempPiece);
                    col++;
                }

            }
        addPiece(new ChessPosition(8,1), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
        addPiece(new ChessPosition(8,2), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(8,3), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(8,4), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN));
        addPiece(new ChessPosition(8,5), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING));
        addPiece(new ChessPosition(8,6), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP));
        addPiece(new ChessPosition(8,7), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT));
        addPiece(new ChessPosition(8,8), new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK));
    }

    public ChessPosition findKing(ChessGame.TeamColor teamColor) {
        for (int i = 0; i < 64; i++) {
            if(getPiece(indexToPosition(i)) == null) {
                continue;
            }
            if(getPiece(indexToPosition(i)).getPieceType() != ChessPiece.PieceType.KING){
                continue;
            }
            if(getPiece(indexToPosition(i)).getTeamColor() == teamColor){
                return indexToPosition(i);
            }
        }
        return null;
    }

    public boolean underAttack(ChessGame.TeamColor teamColor, ChessMove testMove) {
        ChessPosition kingPos = findKing(teamColor);
        for (int i = 0; i < 64; i++) {
            if(getPiece(indexToPosition(i)) == null) {
                continue;
            }
            if(getPiece(indexToPosition(i)).getTeamColor() == teamColor){
                continue;
            }
            if(getPiece(indexToPosition(i)).getPieceType() == null){
                continue;
            }
            for(ChessMove move : getPiece(indexToPosition(i)).pieceMoves(this, indexToPosition(i))) {
                if (move.getEndPosition().equals(kingPos)){
                    return true;
                }
            }
        }
        return false;
    }
}
