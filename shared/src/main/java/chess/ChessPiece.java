package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static chess.ChessHelper.*;
import static java.lang.Math.abs;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    ChessGame.TeamColor pieceColor;
    PieceType type;
    boolean underBlackAttack;
    boolean underWhiteAttack;
    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
      this.pieceColor = pieceColor;
      this.type = type;
      this.underBlackAttack = false;
      this.underWhiteAttack = false;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }
    public boolean isUnderWhiteAttack() {
        return underWhiteAttack;
    }

    public void setUnderWhiteAttack(boolean underWhiteAttack) {
        this.underWhiteAttack = underWhiteAttack;
    }

    public boolean isUnderBlackAttack() {
        return underBlackAttack;
    }

    public void setUnderBlackAttack(boolean underBlackAttack) {
        this.underBlackAttack = underBlackAttack;
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
    }
            /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        Collection<ChessMove> validIndices = List.of();
        ChessPiece selectedPiece = board.getPiece(myPosition);
        int selectedIndex = board.positionToIndex(myPosition);
        boolean nb, sb, eb, wb, nbt, sbt, ebt, wbt;

        if( selectedPiece.getPieceType() == null ){
            return validMoves;
        }
        switch(selectedPiece.getPieceType()) {
            case KING:
                validMoves = handleKingMoves(board,myPosition, true);
                break;
            case QUEEN:
                validMoves = handleQueenMoves(board, myPosition, true);
                break;
            case BISHOP:
                validMoves = handleBishopMoves(board, myPosition, true);
                break;
            case ROOK:
                validMoves = handleRookMoves(board, myPosition, true);
                break;
            case KNIGHT:
                validMoves = handleKnightMoves(board, myPosition, true);
                break;
            case PAWN: validMoves = handlePawnMoves(board, myPosition, true); break;
            default:
                break;
        }
        return validMoves;
    }
    public Collection<ChessMove> handleKingMoves(ChessBoard board, ChessPosition myPosition, boolean checkcheck) {
        ChessBoard testBoard = null;
        try {
            testBoard = (ChessBoard) board.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        Collection<ChessMove> validMoves = new ArrayList<>();
        ChessGame.TeamColor opponent = board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE 
          ? ChessGame.TeamColor.BLACK : ChessGame.TeamColor.WHITE;
        boolean nb = false;
        boolean sb = false;
        boolean eb = false;
        boolean wb = false;
        boolean nbt = false;
        boolean sbt = false;
        boolean ebt = false;
        boolean wbt = false;
        validMoves = kingQueenCheckDiagonals(1, board, myPosition, validMoves, nb, sb, eb, wb, nbt, sbt, ebt, wbt);
        nb = false;
        sb = false;
        eb = false;
        wb = false;
        nbt = false;
        sbt = false;
        ebt = false;
        wbt = false;
        validMoves = kingQueenCheckStraights(1, board, myPosition, validMoves, nb, sb, eb, wb, nbt, sbt, ebt, wbt);
        return validMoves;
    }
        public Collection<ChessMove> handleQueenMoves(ChessBoard board, ChessPosition myPosition, boolean checkcheck) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        boolean nb = false;
        boolean sb = false;
        boolean eb = false;
        boolean wb = false;
        boolean nbt = false;
        boolean sbt = false;
        boolean ebt = false;
        boolean wbt = false;
        validMoves = kingQueenCheckDiagonals(8, board, myPosition, validMoves, nb, sb, eb, wb, nbt, sbt, ebt, wbt);
        nb = false;
        sb = false;
        eb = false;
        wb = false;
        nbt = false;
        sbt = false;
        ebt = false;
        wbt = false;
        validMoves = kingQueenCheckStraights(8, board, myPosition, validMoves, nb, sb, eb, wb, nbt, sbt, ebt, wbt);
        return validMoves;
    }
    public Collection<ChessMove> handleRookMoves(ChessBoard board, ChessPosition myPosition, boolean checkcheck) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        boolean nb = false;
        boolean sb = false;
        boolean eb = false;
        boolean wb = false;
        boolean nbt = false;
        boolean sbt = false;
        boolean ebt = false;
        boolean wbt = false;

        for (int i = 1; i < 9; i++) {
            int nv = (checkDirection(board, myPosition, i, ChessHelper.Direction.NORTH));
            int sv = (checkDirection(board, myPosition, i, ChessHelper.Direction.SOUTH));
            int ev = (checkDirection(board, myPosition, i, ChessHelper.Direction.EAST));
            int wv = (checkDirection(board, myPosition, i, ChessHelper.Direction.WEST));
            if(!nb) {
                nbt = nv>2;
            }
            if(!sb) {
                sbt = sv>2;
            }
            if(!eb) {
                ebt = ev>2;
            }
            if(!wb) {
                wbt = wv>2;
            }
            if(nv%2==1 && !nb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, 
                        board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), null));
                }
            }

            if(sv%2==1 && !sb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, 
                        board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null));
                }
            }

            if(ev%2==1 && !eb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, 
                        board.indexToPosition(board.positionToIndex(myPosition) - (i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + i), null));
                }
            }

            if(wv%2==1 && !wb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, 
                        board.indexToPosition(board.positionToIndex(myPosition) - (i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - i), null));
                }
            }
            nb = nbt;
            sb = sbt;
            eb = ebt;
            wb = wbt;
            if(nb || sb || eb || wb) {
                System.out.print("blocking on: " + nb + sb + eb + wb);
                System.out.println(i);
            }
        }
        return validMoves;
    }
    public Collection<ChessMove> handleBishopMoves(ChessBoard board, ChessPosition myPosition, boolean checkcheck) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        boolean nb = false;
        boolean sb = false;
        boolean eb = false;
        boolean wb = false;
        boolean nbt = false;
        boolean sbt = false;
        boolean ebt = false;
        boolean wbt = false;
        for (int i = 1; i < 9; i++) {
            int nv = (checkDirection(board, myPosition, i, ChessHelper.Direction.NORTHEAST));
            int sv = (checkDirection(board, myPosition, i, ChessHelper.Direction.SOUTHWEST));
            int ev = (checkDirection(board, myPosition, i, ChessHelper.Direction.SOUTHEAST));
            int wv = (checkDirection(board, myPosition, i, ChessHelper.Direction.NORTHWEST));
            if(!nb) {
                nbt = nv>2;
            }
            if(!sb) {
                sbt = sv>2;
            }
            if(!eb) {
                ebt = ev>2;
            }
            if(!wb) {
                wbt = wv>2;
            }
            if(nv%2==1 && !nb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, 
                        board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), null));
                }
            }

            if(sv%2==1 && !sb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, 
                        board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), null));
                }
            }

            if(ev%2==1 && !eb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, 
                        board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), null));
                }
            }

            if(wv%2==1 && !wb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, 
                        board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), null));
                }
            }
            nb = nbt;
            sb = sbt;
            eb = ebt;
            wb = wbt;
            if(nb || sb || eb || wb) {
                System.out.print("blocking on: " + nb + sb + eb + wb);
                System.out.println(i);
            }
        }
        return validMoves;
    }
    public Collection<ChessMove> handleKnightMoves(ChessBoard board, ChessPosition myPosition, boolean checkcheck) {
        int selectedIndex = board.positionToIndex(myPosition);
        ChessPiece selectedPiece = board.getPiece(myPosition);
        Collection<ChessMove> validMoves = new ArrayList<>();
        boolean nb = false;
        boolean sb = false;
        boolean eb = false;
        boolean wb = false;
        boolean nbt = false;
        boolean sbt = false;
        boolean ebt = false;
        boolean wbt = false;
        for (int i = 0; i < 64; i++) {

            if(i/8 == selectedIndex/8){
                continue;
            }
            if(i%8 == selectedIndex%8) {
                continue;
            }// 17,10, -6, -15, -17, -10, 6, 15
            int col1 = myPosition.col;
            int row1 = myPosition.row;
            int row2 = (i/8)+1;
            int col2 = (i%8)+1;

            System.out.print("cols:");
            System.out.print(col1);
            System.out.println(col2);
            System.out.print("rows:");
            System.out.print(row1);
            System.out.println(row2);
            System.out.print("result");
            System.out.println(abs(col1-col2)+abs(row1-row2));
            if((abs(col1-col2)+abs(row1-row2) == 3) && selectedPiece.getPieceType() != null) {
                if((board.pieces[i] != null) && selectedPiece.pieceColor == board.pieces[i].pieceColor ) {
                        continue;
                }
                validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
            }

        }
        return validMoves;
    }
    public Collection<ChessMove> handlePawnMoves(ChessBoard board, ChessPosition myPosition, boolean checkcheck) {
        Collection<ChessMove> validMoves = new ArrayList<>();
        boolean nb = false;
        boolean sb = false;
        boolean eb = false;
        boolean wb = false;
        boolean nbt = false;
        boolean sbt = false;
        boolean ebt = false;
        boolean wbt = false;
        for (int i = 1; i < 2; i++) {
            int nv = (checkDirection(board, myPosition, i, ChessHelper.Direction.NORTH));
            int sv = (checkDirection(board, myPosition, i, ChessHelper.Direction.SOUTH));
            int ev;
            int wv;
            if(board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.WHITE) {
                ev = (checkDirection(board, myPosition, i, ChessHelper.Direction.NORTHWEST));
                wv = (checkDirection(board, myPosition, i, ChessHelper.Direction.NORTHEAST));
            }
            else {
                ev = (checkDirection(board, myPosition, i, ChessHelper.Direction.SOUTHEAST));
                wv = (checkDirection(board, myPosition, i, ChessHelper.Direction.SOUTHWEST));
            }
            if(!nb) {
                nbt = nv>2;
            }
            if(!sb) {
                sbt = sv>2;
            }
            if(!eb) {
                ebt = ev>2;
            }
            if(!wb) {
                wbt = wv > 2;
            }
            if(nv==1 && !nb && board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.WHITE) {
                HelpReturn ret = ChessHelper.help1(i, board, myPosition, validMoves, checkcheck);
                board = ret.board();

            }

            if(sv==1 && !sb && board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.BLACK) {
                HelpReturn ret = ChessHelper.help2(i, board, myPosition, validMoves, checkcheck);
                board = ret.board();
            }

            if(ev==3 && !eb && i<2) {
                HelpReturn ret = ChessHelper.help3(i, board, myPosition, validMoves, checkcheck);
                board = ret.board();
            }

            if(wv==3 && !wb && i<2) {
                HelpReturn ret = ChessHelper.help4(i, board, myPosition, validMoves, checkcheck);
                board = ret.board();
            }
            nb = nbt;
            sb = sbt;
            eb = ebt;
            wb = wbt;
        }
        return validMoves;
    }
}
