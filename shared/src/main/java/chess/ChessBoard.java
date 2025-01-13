package chess;

import java.util.ArrayList;
import java.util.Objects;

import chess.ChessPiece.PieceType;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    //ChessPosition position;
    //ChessPiece piece;
    ArrayList<ArrayList<ChessTile>> tiles;
    public ChessBoard() {
        this(8,8);
    }
    public ChessBoard(int rows, int cols) {
        tiles = new ArrayList<ArrayList<ChessTile> >();
        LayoutTiles(rows, cols);
    }

    @Override
    public String toString() {
        return "ChessBoard{" +
                "tiles=" + tiles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.equals(tiles, that.tiles);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tiles);
    }

    /**
     * Lays out the tiles on the chessboard
     */
    void LayoutTiles(int rows, int cols) {
        boolean northEdge, southEdge, eastEdge, westEdge; 
        ChessTile tempTile;
        //euclidian chessboard
        for (int i = 0; i < rows; i++) {
            tiles.add(new ArrayList<ChessTile>());
            for (int j = 0; j < cols; j++) {
                tiles.get(i).add(new ChessTile());
            }
        }
        for (int i=0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                northEdge = i==(rows-1);
                southEdge = i==0;
                eastEdge = j==(cols-1);
                westEdge = j==0;
                tempTile = tiles.get(i).get(j);
                //literal edge cases

                if(southEdge){
                    tempTile.setSouthwestTile(null);
                    tempTile.setSouthTile(null);
                    tempTile.setSoutheastTile(null);
                } else {
                    tempTile.setSouthTile(tiles.get(i-1).get(j));
                }
                if(westEdge){
                    westEdge = true;
                    tempTile.setNorthwestTile(null);
                    tempTile.setWestTile(null);
                    tempTile.setSouthwestTile(null);
                } else {
                    tempTile.setWestTile(tiles.get(i).get(j-1));
                    if(!southEdge) {
                        tempTile.setSouthwestTile(tiles.get(i-1).get(j-1));
                    }
                }
                if(northEdge) {
                    tempTile.setNorthwestTile(null);
                    tempTile.setNorthTile(null);
                    tempTile.setNortheastTile(null);
                } else {
                    tempTile.setNorthTile(tiles.get(i+1).get(j));
                    if(!westEdge) {
                       tempTile.setNorthwestTile(tiles.get(i+1).get(j-1));
                    }
                }
                if(eastEdge) {
                    tempTile.setNortheastTile(null);
                    tempTile.setEastTile(null);
                    tempTile.setSoutheastTile(null);
                } else {
                    tempTile.setEastTile(tiles.get(i).get(j+1));
                    if(!northEdge) {
                        tempTile.setNortheastTile(tiles.get(i+1).get(j+1));
                    }
                    if(!southEdge) {
                        tempTile.setSoutheastTile(tiles.get(i-1).get(j+1));
                    }
                }
            }
        }
    }
    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        //throw new RuntimeException("Not implemented");
        tiles.get(position.getRow() - 1 ).get(position.getColumn() -1 ).setPiece(piece);
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
        return tiles.get(position.getRow()).get(position.getColumn()).getPiece();

    }
    public void printBoard() {
      printBoard(8,8);
    }
    public void printBoard(int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                  ChessPiece piece = tiles.get(i).get(j).piece;
                if(piece==null) {
                  System.out.print("+");
                  continue;
                }
                switch (piece.getPieceType()) {
                  case ChessPiece.PieceType.ROOK:
                    if(piece.getTeamColor()==ChessGame.TeamColor.WHITE)
                      System.out.print('R');
                    else
                      System.out.print('r');
                    break;
                  case ChessPiece.PieceType.KNIGHT:
                    if(piece.getTeamColor()==ChessGame.TeamColor.WHITE)
                      System.out.print('N');
                    else
                      System.out.print('n');
                    break;
                  case ChessPiece.PieceType.BISHOP:
                    if(piece.getTeamColor()==ChessGame.TeamColor.WHITE)
                      System.out.print('B');
                    else
                      System.out.print('b');
                    break;
                  case ChessPiece.PieceType.KING:
                    if(piece.getTeamColor()==ChessGame.TeamColor.WHITE)
                      System.out.print('K');
                    else
                      System.out.print('k');
                    break;
                  case ChessPiece.PieceType.QUEEN:
                    if(piece.getTeamColor()==ChessGame.TeamColor.WHITE)
                      System.out.print('Q');
                    else
                      System.out.print('q');
                    break;
                  case ChessPiece.PieceType.PAWN:
                    if(piece.getTeamColor()==ChessGame.TeamColor.WHITE)
                      System.out.print('P');
                    else
                      System.out.print('p');
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
        resetBoard(ChessGame.BoardType.DEFAULT);
    }
    public void resetBoard(ChessGame.BoardType boardType) {
        switch (boardType) {
          default:
            String layout = 
            """
            rnbkqbnr
            pppppppp
            ++++++++
            ++++++++
            ++++++++
            ++++++++
            PPPPPPPP
            RNBKQBNR
            """;
            ChessPiece tempPiece = null;
            StringBuilder res = new StringBuilder();
            char[] ch = res.append(layout)
                .reverse()
                .toString()
                .replaceAll("\\s+","")
                .toCharArray();
            for (int i=0; i<64; i++){
                switch(ch[i]){
                    case '\n':
                        break;
                    case 'P':
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
                //System.out.print(ch[i]);
                //if (i/8==0){
                //  System.out.println("");
                //}
                addPiece(new ChessPosition((i/8)+1, (i%8)+1), tempPiece);
            }
            break;
        }
    }
}
