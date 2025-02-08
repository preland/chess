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

    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
      this.pieceColor = pieceColor;
      this.type = type;
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
        PAWN,
        NONE
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
        //if even, invalid
        //if 3 or 4, blocks
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
                //System.out.println(oidx % 8);
                System.out.print(oidx + ";" + idx);

                return 4;
            }

         } else if(((((oidx % 8) == 7) && (idx > oidx && idx %8!=7)) | (((oidx % 8) == 0) && (oidx > idx && idx%8!=0))) | ((((idx % 8) == 7) && (oidx > idx && oidx%8!=7)) | (((idx % 8) == 0) && (idx > oidx && oidx%8!=0)))) {
            System.out.print(oidx + ":" + idx);
            return 4;
        }
        if(board.pieces[idx].getTeamColor() == board.pieces[oidx].getTeamColor()) {
            return 4;
        }
        if(board.pieces[idx].getPieceType() != PieceType.NONE) {
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
        System.out.println("Checking distance " + m + " with direction " + d + " results in: " + ret);
        return ret;
    }
    /*public boolean underAttack(ChessBoard board, ChessPosition position, ChessGame.TeamColor color) {

        switch(color) {
            case BLACK:
                for (int i = 0; i < 64; i++) {
                    ChessPosition tempPos = board.indexToPosition(i);
                    ChessPiece tempPiece = board.getPiece(tempPos);
                    if (tempPiece.getPieceType() == PieceType.KING) {
                        break;
                    }
                    if (tempPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                        break;
                    }
                    for(ChessMove move : tempPiece.pieceMoves(board, tempPos)) {
                        if(move.getEndPosition() == position) {
                            return true;
                        }
                    }
                }
                break;
            case WHITE:

                for (int i = 0; i < 64; i++) {
                    ChessPosition tempPos = board.indexToPosition(i);
                    ChessPiece tempPiece = board.getPiece(tempPos);
                    if (tempPiece.getPieceType() == PieceType.KING) {
                        break;
                    }
                    if (tempPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                        break;
                    }
                    for (ChessMove move : tempPiece.pieceMoves(board, tempPos)) {
                        if (move.getEndPosition() == position) {
                            return true;
                        }
                    }
                }
                break;
            default:
                break;
        }

        return false;
    }*/
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
        switch(selectedPiece.getPieceType()) {
            case KING:
                nb = false;
                sb = false;
                eb = false;
                wb = false;
                nbt = false;
                sbt = false;
                ebt = false;
                wbt = false;
                for (int i = 1; i < 2; i++) {
                /*    if(board.pieces[board.positionToIndex(myPosition)-7*i].getPieceType() == PieceType.NONE){
                        if(board.pieces[board.positionToIndex(myPosition)-7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)-9*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)-9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+7*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)+7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+9*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)+9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
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
                        if(!board.underAttack(board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), board.getPiece(myPosition).getTeamColor()))
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), PieceType.NONE));
                    }

                    if(sv%2==1 && !sb) {
                        if(!board.underAttack(board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), board.getPiece(myPosition).getTeamColor()))
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(9*i)), PieceType.NONE));
                    }

                    if(ev%2==1 && !eb) {
                        if(!board.underAttack(board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), board.getPiece(myPosition).getTeamColor()))
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(7*i)), PieceType.NONE));
                    }

                    if(wv%2==1 && !wb) {
                        if(!board.underAttack(board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), board.getPiece(myPosition).getTeamColor()))
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(7*i)), PieceType.NONE));
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
                /*    if(board.pieces[board.positionToIndex(myPosition)-7*i].getPieceType() == PieceType.NONE){
                        if(board.pieces[board.positionToIndex(myPosition)-7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)-9*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)-9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+7*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)+7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+9*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)+9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
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
                        if(!board.underAttack(board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), board.getPiece(myPosition).getTeamColor()))
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(8*i)), PieceType.NONE));
                    }

                    if(sv%2==1 && !sb) {
                        if(!board.underAttack(board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), board.getPiece(myPosition).getTeamColor()))
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(8*i)), PieceType.NONE));
                    }

                    if(ev%2==1 && !eb) {
                        if(!board.underAttack(board.indexToPosition(board.positionToIndex(myPosition) + i), board.getPiece(myPosition).getTeamColor()))
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+i), PieceType.NONE));
                    }

                    if(wv%2==1 && !wb) {
                        if(!board.underAttack(board.indexToPosition(board.positionToIndex(myPosition) - i), board.getPiece(myPosition).getTeamColor()))
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-i), PieceType.NONE));
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
                break;
            case QUEEN:
                nb = false;
                sb = false;
                eb = false;
                wb = false;
                nbt = false;
                sbt = false;
                ebt = false;
                wbt = false;
                for (int i = 1; i < 9; i++) {
                /*    if(board.pieces[board.positionToIndex(myPosition)-7*i].getPieceType() == PieceType.NONE){
                        if(board.pieces[board.positionToIndex(myPosition)-7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)-9*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)-9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+7*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)+7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+9*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)+9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
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
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(9*i)), PieceType.NONE));
                    }

                    if(sv%2==1 && !sb) {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(9*i)), PieceType.NONE));
                    }

                    if(ev%2==1 && !eb) {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(7*i)), PieceType.NONE));
                    }

                    if(wv%2==1 && !wb) {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(7*i)), PieceType.NONE));
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
                /*    if(board.pieces[board.positionToIndex(myPosition)-7*i].getPieceType() == PieceType.NONE){
                        if(board.pieces[board.positionToIndex(myPosition)-7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)-9*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)-9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+7*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)+7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+9*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)+9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
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
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(8*i)), PieceType.NONE));
                    }

                    if(sv%2==1 && !sb) {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(8*i)), PieceType.NONE));
                    }

                    if(ev%2==1 && !eb) {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+i), PieceType.NONE));
                    }

                    if(wv%2==1 && !wb) {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-i), PieceType.NONE));
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
                break;
            case BISHOP:
                nb = false;
                sb = false;
                eb = false;
                wb = false;
                nbt = false;
                sbt = false;
                ebt = false;
                wbt = false;
                for (int i = 1; i < 9; i++) {
                /*    if(board.pieces[board.positionToIndex(myPosition)-7*i].getPieceType() == PieceType.NONE){
                        if(board.pieces[board.positionToIndex(myPosition)-7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)-9*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)-9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+7*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)+7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+9*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)+9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
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
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(9*i)), PieceType.NONE));
                    }

                    if(sv%2==1 && !sb) {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(9*i)), PieceType.NONE));
                    }

                    if(ev%2==1 && !eb) {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(7*i)), PieceType.NONE));
                    }

                    if(wv%2==1 && !wb) {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(7*i)), PieceType.NONE));
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
                break;
            case ROOK:
                nb = false;
                sb = false;
                eb = false;
                wb = false;
                nbt = false;
                sbt = false;
                ebt = false;
                wbt = false;
                for (int i = 1; i < 9; i++) {
                /*    if(board.pieces[board.positionToIndex(myPosition)-7*i].getPieceType() == PieceType.NONE){
                        if(board.pieces[board.positionToIndex(myPosition)-7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)-9*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)-9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+7*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)+7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+9*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)+9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
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
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(8*i)), PieceType.NONE));
                    }

                    if(sv%2==1 && !sb) {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(8*i)), PieceType.NONE));
                    }

                    if(ev%2==1 && !eb) {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+i), PieceType.NONE));
                    }

                    if(wv%2==1 && !wb) {
                        validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-i), PieceType.NONE));
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
                break;
            case KNIGHT:
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
                        if(selectedPiece.getPieceType() != PieceType.NONE) {
                            if(selectedPiece.pieceColor == board.pieces[i].pieceColor) {
                                continue;
                            }

                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    }

                }
                break;
            case PAWN:
                nb = false;
                sb = false;
                eb = false;
                wb = false;
                nbt = false;
                sbt = false;
                ebt = false;
                wbt = false;
                for (int i = 1; i < 2; i++) {
                /*    if(board.pieces[board.positionToIndex(myPosition)-7*i].getPieceType() == PieceType.NONE){
                        if(board.pieces[board.positionToIndex(myPosition)-7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)-9*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)-9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+7*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)+7*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()){
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
                        }
                    } else if(board.pieces[board.positionToIndex(myPosition)+9*i].getPieceType() == PieceType.NONE) {
                        if(board.pieces[board.positionToIndex(myPosition)+9*i].getTeamColor() == board.pieces[board.positionToIndex(myPosition)].getTeamColor()) {
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(i), PieceType.NONE));
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
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(8*i)), PieceType.QUEEN));
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(8*i)), PieceType.BISHOP));
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(8*i)), PieceType.KNIGHT));
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(8*i)), PieceType.ROOK));
                        } else {
                            System.out.println((board.positionToIndex(myPosition)+(8*i)) > 55);
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), PieceType.NONE));
                        }
                        if(myPosition.getRow()==2) {
                            if(checkDirection(board, myPosition, i+1, Direction.NORTH)==1) {
                                if((board.positionToIndex(myPosition)+(8*i)) > 55){
                                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(8*2)), PieceType.QUEEN));
                                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(8*2)), PieceType.BISHOP));
                                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(8*2)), PieceType.KNIGHT));
                                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(8*2)), PieceType.ROOK));
                                } else {
                                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), PieceType.NONE));
                                }
                            }
                        }

                    }

                    if(sv==1 && !sb && board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.BLACK) {
                        System.out.println("he");
                        if((board.positionToIndex(myPosition)-(8*i)) < 8) {
                            System.out.println("he1");
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(8*i)), PieceType.QUEEN));
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(8*i)), PieceType.BISHOP));
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(8*i)), PieceType.KNIGHT));
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(8*i)), PieceType.ROOK));
                        } else {
                            System.out.println((board.positionToIndex(myPosition)-(8*i)));
                            validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), PieceType.NONE));
                        }
                        if(myPosition.getRow()==7) {
                            if(checkDirection(board, myPosition, i+1, Direction.SOUTH)==1) {
                                if((board.positionToIndex(myPosition)-(8*2)) < 8){
                                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(8*2)), PieceType.QUEEN));
                                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(8*2)), PieceType.BISHOP));
                                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(8*2)), PieceType.KNIGHT));
                                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(8*2)), PieceType.ROOK));
                                } else {
                                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), PieceType.NONE));
                                }
                            }
                        }
                    }

                    if(ev==3 && !eb && i<2) {
                        if(board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.WHITE) {
                            if((board.positionToIndex(myPosition)+(9*i)) > 55){
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(7*i)), PieceType.QUEEN));
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(7*i)), PieceType.BISHOP));
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(7*i)), PieceType.KNIGHT));
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(7*i)), PieceType.ROOK));
                            } else {
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), PieceType.NONE));
                            }
                        } else {
                                if((board.positionToIndex(myPosition)-(9*i)) < 8){
                                    System.out.println(board.positionToIndex(myPosition)-(7*i));
                                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(7*i)), PieceType.QUEEN));
                                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(7*i)), PieceType.BISHOP));
                                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(7*i)), PieceType.KNIGHT));
                                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(7*i)), PieceType.ROOK));
                                } else {
                                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), PieceType.NONE));
                                }
                        }
                    }

                    if(wv==3 && !wb && i<2) {

                        if(board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.WHITE) {
                            if((board.positionToIndex(myPosition)+(7*i)) > 55){
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(9*i)), PieceType.QUEEN));
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(9*i)), PieceType.BISHOP));
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(9*i)), PieceType.KNIGHT));
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)+(9*i)), PieceType.ROOK));
                            } else {
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), PieceType.NONE));
                            }
                        } else {
                            System.out.println("here");
                            if((board.positionToIndex(myPosition)-(7*i)) < 8){

                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(9*i)), PieceType.QUEEN));
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(9*i)), PieceType.BISHOP));
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(9*i)), PieceType.KNIGHT));
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition)-(9*i)), PieceType.ROOK));
                            } else {
                                System.out.println("her");
                                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), PieceType.NONE));
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
            default:
                break;
        }
        return validMoves;
    }
}
