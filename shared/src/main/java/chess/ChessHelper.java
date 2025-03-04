package chess;

import java.util.Collection;

import static chess.ChessPiece.checkDirection;
import chess.ChessPiece.PieceType;
public class ChessHelper {
    public static boolean help1(int i, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> validMoves, boolean checkcheck) {
        if((board.positionToIndex(myPosition)+(8*i)) > 55){
            if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), null))) {
                return false;
            } else {
                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), PieceType.QUEEN));
                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), PieceType.BISHOP));
                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), PieceType.KNIGHT));
                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), PieceType.ROOK));
            }
        } else {
            System.out.println((board.positionToIndex(myPosition)+(8*i)) > 55);
            if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), null))) {
                return false;
            } else {
                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), null));
            }
        }
        if(myPosition.getRow()==2 && checkDirection(board, myPosition, i+1, ChessPiece.Direction.NORTH)==1) {
            if((board.positionToIndex(myPosition)+(8*i)) > 55){
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), null))){
                    return false;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), PieceType.QUEEN));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), PieceType.BISHOP));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), PieceType.KNIGHT));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), PieceType.ROOK));
                }
            } else {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), null))) {
                    return false;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), null));
                }
            }
        }
        return true;
    }
    public static boolean help2(int i, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> validMoves, boolean checkcheck) {
        System.out.println("he");
        if((board.positionToIndex(myPosition)-(8*i)) < 8) {
            System.out.println("he1");
            if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                    board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null))) {
                return false;
            } else {
                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), PieceType.QUEEN));
                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), PieceType.BISHOP));
                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), PieceType.KNIGHT));
                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), PieceType.ROOK));
            }
        } else {
            System.out.println((board.positionToIndex(myPosition)-(8*i)));
            if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                    board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null))) {
                return false;
            } else {
                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null));
            }
        }
        if(myPosition.getRow()==7 && checkDirection(board, myPosition, i+1, ChessPiece.Direction.SOUTH)==1) {
            if((board.positionToIndex(myPosition)-(8*2)) < 8){
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), null))){
                    return false;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), PieceType.QUEEN));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), PieceType.BISHOP));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), PieceType.KNIGHT));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), PieceType.ROOK));
                }
            } else {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(),
                        new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), null))){
                    return false;
                } else{
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), null));
                }
            }
        }
        return true;
    }
    public static boolean help3(int i, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> validMoves, boolean checkcheck) {
        if(board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.WHITE) {
            if((board.positionToIndex(myPosition)+(9*i)) > 55){
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), null))) {
                    return false;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), PieceType.QUEEN));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), PieceType.BISHOP));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), PieceType.KNIGHT));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), PieceType.ROOK));
                }
            } else {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), null))) {
                    return false;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), null));
                }
            }
        } else {
            if((board.positionToIndex(myPosition)-(9*i)) < 8){
                System.out.println(board.positionToIndex(myPosition)-(7*i));
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), null))) {
                    return false;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), PieceType.QUEEN));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), PieceType.BISHOP));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), PieceType.KNIGHT));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), PieceType.ROOK));
                }
            } else {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), null))) {
                    return false;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), null));
                }
            }
        }
        return true;
    }
    public static boolean help4(int i, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> validMoves, boolean checkcheck) {
        if(board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.WHITE) {
            if((board.positionToIndex(myPosition)+(7*i)) > 55){
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), null))) {
                    return false;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), PieceType.QUEEN));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), PieceType.BISHOP));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), PieceType.KNIGHT));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), PieceType.ROOK));
                }
            } else {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), null))) {
                    return false;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), null));
                }
            }
        } else {
            System.out.println("here");
            if((board.positionToIndex(myPosition)-(7*i)) < 8){
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), null))) {
                    return false;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), PieceType.QUEEN));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), PieceType.BISHOP));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), PieceType.KNIGHT));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), PieceType.ROOK));
                }
            } else {
                System.out.println("her");
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), null))) {
                    return false;
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), null));
                }
            }
        }
        return true;
    }
}
