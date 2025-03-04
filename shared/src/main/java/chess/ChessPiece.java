package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST,
        NORTHEAST,
        NORTHWEST,
        SOUTHEAST,
        SOUTHWEST
    }
    int checkPosition(ChessBoard board, int oidx, int idx, int m, boolean diagonal) {
        int orow = oidx /8;
        int ocol = oidx%8;
        int nrow = idx / 8;
        int ncol = idx % 8;
        if(idx>63 | idx<0) {
            System.out.print(oidx + " " + idx);
            return 4;
        }
        if(diagonal){
            if (abs(orow-nrow) != m | abs(ocol-ncol) != m ){
                System.out.print(oidx + ";" + idx);

                return 4;
            }

         } else if(((((oidx % 8) == 7) && (idx > oidx && idx %8!=7)) | (((oidx % 8) == 0) && (oidx > idx && idx%8!=0))) 
             | ((((idx % 8) == 7) && (oidx > idx && oidx%8!=7)) | (((idx % 8) == 0) && (idx > oidx && oidx%8!=0)))) {
            System.out.print(oidx + ":" + idx);
            return 4;
        }
        if(board.pieces[idx] == null) {
            return 1;
        }
        if(board.pieces[idx].getTeamColor() == board.pieces[oidx].getTeamColor()) {
            return 4;
        }
        if(board.pieces[idx].getPieceType() != null) {
            return 3;
        }
        return 1;
    }
    int checkDirection(ChessBoard board, ChessPosition position, int m, Direction d) {

        int ret = 0;
        switch(d) {
            case NORTH -> {
                ret = checkPosition(board,board.positionToIndex(position), board.positionToIndex(position)+(8*m), m,false);
                break;
            }
            case SOUTH -> {
                ret = checkPosition(board, board.positionToIndex(position), board.positionToIndex(position)-(8*m), m,false);
                break;
            }
            case EAST -> {
                ret = checkPosition(board, board.positionToIndex(position), board.positionToIndex(position)+m, m,false);
                break;
            }
            case WEST -> {
                ret = checkPosition(board, board.positionToIndex(position), board.positionToIndex(position)-m, m,false);
                break;
            }
            case NORTHEAST -> {
                ret = checkPosition(board, board.positionToIndex(position), board.positionToIndex(position)+(9*m), m,true);
                break;
            }
            case NORTHWEST -> {
                ret = checkPosition(board, board.positionToIndex(position), board.positionToIndex(position)+(7*m), m,true);
                break;
            }
            case SOUTHEAST -> {
                ret = checkPosition(board, board.positionToIndex(position), board.positionToIndex(position)-(7*m), m,true);
                break;
            }
            case SOUTHWEST -> {
                ret = checkPosition(board, board.positionToIndex(position), board.positionToIndex(position)-(9*m), m,true);
                break;
            }
            default -> throw new IllegalStateException("Unexpected value: " + d);
        }
        return ret;
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
        for (int i = 1; i < 2; i++) {
            int nv = (checkDirection(board, myPosition, i, Direction.NORTHEAST));
            int sv = (checkDirection(board, myPosition, i, Direction.SOUTHWEST));
            int ev = (checkDirection(board, myPosition, i, Direction.SOUTHEAST));
            int wv = (checkDirection(board, myPosition, i, Direction.NORTHWEST));
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
                    validMoves.add(new ChessMove(myPosition, 
                          board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), null));
            }

            if(sv%2==1 && !sb) {
                    validMoves.add(new ChessMove(myPosition, 
                          board.indexToPosition(board.positionToIndex(myPosition)-(9*i)), null));
            }

            if(ev%2==1 && !eb) {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(7*i)), null));
            }

            if(wv%2==1 && !wb) {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(7*i)), null));
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
        nb = false;
        sb = false;
        eb = false;
        wb = false;
        nbt = false;
        sbt = false;
        ebt = false;
        wbt = false;
        for (int i = 1; i < 2; i++) {
            int nv = (checkDirection(board, myPosition, i, Direction.NORTH));
            int sv = (checkDirection(board, myPosition, i, Direction.SOUTH));
            int ev = (checkDirection(board, myPosition, i, Direction.EAST));
            int wv = (checkDirection(board, myPosition, i, Direction.WEST));
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
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(8*i)), null));
            }

            if(sv%2==1 && !sb) {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(8*i)), null));
            }

            if(ev%2==1 && !eb) {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+i), null));
            }

            if(wv%2==1 && !wb) {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-i), null));
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
        for (int i = 1; i < 9; i++) {
            int nv = (checkDirection(board, myPosition, i, Direction.NORTHEAST));
            int sv = (checkDirection(board, myPosition, i, Direction.SOUTHWEST));
            int ev = (checkDirection(board, myPosition, i, Direction.SOUTHEAST));
            int wv = (checkDirection(board, myPosition, i, Direction.NORTHWEST));
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
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), 
                      new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), null));
                }
            }

            if(sv%2==1 && !sb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(),
                      new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), null));
                }
            }

            if(ev%2==1 && !eb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(),
                      new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), null));
                }
            }

            if(wv%2==1 && !wb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(),
                      new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), null))) {
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
        nb = false;
        sb = false;
        eb = false;
        wb = false;
        nbt = false;
        sbt = false;
        ebt = false;
        wbt = false;
        for (int i = 1; i < 9; i++) {
                /*    if(board.pieces[board.positionToIndex(myPosition)-7*i].getPieceType() == null){
                        if(board.pieces[board.positionToIndex(myPosition)-7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)-9*i].getPieceType() == null) {
                        if(board.pieces[board.positionToIndex(myPosition)-9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+7*i].getPieceType() == null) {
                        if(board.pieces[board.positionToIndex(myPosition)+7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+9*i].getPieceType() == null) {
                        if(board.pieces[board.positionToIndex(myPosition)+9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
                        }
                    }*/
            int nv = (checkDirection(board, myPosition, i, Direction.NORTH));
            int sv = (checkDirection(board, myPosition, i, Direction.SOUTH));
            int ev = (checkDirection(board, myPosition, i, Direction.EAST));
            int wv = (checkDirection(board, myPosition, i, Direction.WEST));
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
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), null));
                }
            }

            if(sv%2==1 && !sb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null));
                }
            }

            if(ev%2==1 && !eb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + i), null));
                }
            }

            if(wv%2==1 && !wb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (i)), null))) {
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
                /*    if(board.pieces[board.positionToIndex(myPosition)-7*i].getPieceType() == null){
                        if(board.pieces[board.positionToIndex(myPosition)-7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)-9*i].getPieceType() == null) {
                        if(board.pieces[board.positionToIndex(myPosition)-9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+7*i].getPieceType() == null) {
                        if(board.pieces[board.positionToIndex(myPosition)+7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+9*i].getPieceType() == null) {
                        if(board.pieces[board.positionToIndex(myPosition)+9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
                        }
                    }*/
            int nv = (checkDirection(board, myPosition, i, Direction.NORTH));
            int sv = (checkDirection(board, myPosition, i, Direction.SOUTH));
            int ev = (checkDirection(board, myPosition, i, Direction.EAST));
            int wv = (checkDirection(board, myPosition, i, Direction.WEST));
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
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), null));
                }
            }

            if(sv%2==1 && !sb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null));
                }
            }

            if(ev%2==1 && !eb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + i), null));
                }
            }

            if(wv%2==1 && !wb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (i)), null))) {
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
                /*    if(board.pieces[board.positionToIndex(myPosition)-7*i].getPieceType() == null){
                        if(board.pieces[board.positionToIndex(myPosition)-7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)-9*i].getPieceType() == null) {
                        if(board.pieces[board.positionToIndex(myPosition)-9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+7*i].getPieceType() == null) {
                        if(board.pieces[board.positionToIndex(myPosition)+7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+9*i].getPieceType() == null) {
                        if(board.pieces[board.positionToIndex(myPosition)+9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
                        }
                    }*/
            int nv = (checkDirection(board, myPosition, i, Direction.NORTHEAST));
            int sv = (checkDirection(board, myPosition, i, Direction.SOUTHWEST));
            int ev = (checkDirection(board, myPosition, i, Direction.SOUTHEAST));
            int wv = (checkDirection(board, myPosition, i, Direction.NORTHWEST));
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
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), null));
                }
            }

            if(sv%2==1 && !sb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), null));
                }
            }

            if(ev%2==1 && !eb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), null))) {
                        break;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), null));
                }
            }

            if(wv%2==1 && !wb) {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), null))) {
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
            //abs(col1)-abs(col2) + abs(row1)-abs(row2) = 3
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
            if(abs(col1-col2)+abs(row1-row2) == 3) {
                if(selectedPiece.getPieceType() != null) {
                    if(board.pieces[i] != null) {
                        if (selectedPiece.pieceColor == board.pieces[i].pieceColor) {
                            continue;
                        }
                    }
                    //if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(i), null))) {
                    //        break;
                    //} else {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
                    //}
                }
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
                /*    if(board.pieces[board.positionToIndex(myPosition)-7*i].getPieceType() == null){
                        if(board.pieces[board.positionToIndex(myPosition)-7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)-9*i].getPieceType() == null) {
                        if(board.pieces[board.positionToIndex(myPosition)-9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+7*i].getPieceType() == null) {
                        if(board.pieces[board.positionToIndex(myPosition)+7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+9*i].getPieceType() == null) {
                        if(board.pieces[board.positionToIndex(myPosition)+9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), null));
                        }
                    }*/
            int nv = (checkDirection(board, myPosition, i, Direction.NORTH));
            int sv = (checkDirection(board, myPosition, i, Direction.SOUTH));
            int ev;
            int wv;
            if(board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.WHITE) {
                ev = (checkDirection(board, myPosition, i, Direction.NORTHWEST));
                wv = (checkDirection(board, myPosition, i, Direction.NORTHEAST));
            }
            else {
                ev = (checkDirection(board, myPosition, i, Direction.SOUTHEAST));
                wv = (checkDirection(board, myPosition, i, Direction.SOUTHWEST));
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
                if((board.positionToIndex(myPosition)+(8*i)) > 55){
                    if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), null))) {
                            break;
                    } else {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), PieceType.QUEEN));
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), PieceType.BISHOP));
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), PieceType.KNIGHT));
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), PieceType.ROOK));
                    }
                } else {
                    System.out.println((board.positionToIndex(myPosition)+(8*i)) > 55);
                    if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), null))) {
                            break;
                    } else {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), null));
                    }
                }
                if(myPosition.getRow()==2) {
                    if(checkDirection(board, myPosition, i+1, Direction.NORTH)==1) {
                        if((board.positionToIndex(myPosition)+(8*i)) > 55){
                            if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), null))){
                                    break;
                            } else {
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), PieceType.QUEEN));
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), PieceType.BISHOP));
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), PieceType.KNIGHT));
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), PieceType.ROOK));
                            }
                        } else {
                            if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), null))) {
                                    break;
                            } else {
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), null));
                            }
                        }
                    }
                }

            }

            if(sv==1 && !sb && board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.BLACK) {
                System.out.println("he");
                if((board.positionToIndex(myPosition)-(8*i)) < 8) {
                    System.out.println("he1");
                    if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null))) {
                            break;
                    } else {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), PieceType.QUEEN));
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), PieceType.BISHOP));
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), PieceType.KNIGHT));
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), PieceType.ROOK));
                    }
                } else {
                    System.out.println((board.positionToIndex(myPosition)-(8*i)));
                    if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null))) {
                            break;
                    } else {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null));
                    }
                }
                if(myPosition.getRow()==7) {
                    if(checkDirection(board, myPosition, i+1, Direction.SOUTH)==1) {
                        if((board.positionToIndex(myPosition)-(8*2)) < 8){
                            if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), null))){
                                    break;
                            } else {
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), PieceType.QUEEN));
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), PieceType.BISHOP));
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), PieceType.KNIGHT));
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), PieceType.ROOK));
                            }
                        } else {
                            if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), null))){
                                    break;
                            } else{
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), null));
                            }
                        }
                    }
                }
            }

            if(ev==3 && !eb && i<2) {
                if(board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.WHITE) {
                    if((board.positionToIndex(myPosition)+(9*i)) > 55){
                        if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), null))) {
                                break;
                        } else {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), PieceType.QUEEN));
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), PieceType.BISHOP));
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), PieceType.KNIGHT));
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), PieceType.ROOK));
                        }
                    } else {
                        if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), null))) {
                                break;
                        } else {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), null));
                        }
                    }
                } else {
                    if((board.positionToIndex(myPosition)-(9*i)) < 8){
                        System.out.println(board.positionToIndex(myPosition)-(7*i));
                        if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), null))) {
                                break;
                        } else {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), PieceType.QUEEN));
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), PieceType.BISHOP));
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), PieceType.KNIGHT));
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), PieceType.ROOK));
                        }
                    } else {
                        if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), null))) {
                                break;
                        } else {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), null));
                        }
                    }
                }
            }

            if(wv==3 && !wb && i<2) {

                if(board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.WHITE) {
                    if((board.positionToIndex(myPosition)+(7*i)) > 55){
                        if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), null))) {
                                break;
                        } else {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), PieceType.QUEEN));
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), PieceType.BISHOP));
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), PieceType.KNIGHT));
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), PieceType.ROOK));
                        }
                    } else {
                        if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), null))) {
                                break;
                        } else {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), null));
                        }
                    }
                } else {
                    System.out.println("here");
                    if((board.positionToIndex(myPosition)-(7*i)) < 8){
                        if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), null))) {
                                break;
                        } else {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), PieceType.QUEEN));
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), PieceType.BISHOP));
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), PieceType.KNIGHT));
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), PieceType.ROOK));
                    }
                    } else {
                        System.out.println("her");
                        if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), null))) {
                                break;
                        } else {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), null));
                        }
                    }
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
}
