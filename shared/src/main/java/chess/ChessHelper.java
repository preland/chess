package chess;

import java.util.Collection;

import chess.ChessPiece.PieceType;

import static java.lang.Math.abs;

public class ChessHelper {
    public static HelpReturn help1(int i, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> validMoves, boolean checkcheck) {
        if((board.positionToIndex(myPosition)+(8*i)) > 55){
            if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, 
                    board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), null))) {
                return new HelpReturn(i, board, myPosition, validMoves, checkcheck, false);
            } else {
                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), PieceType.QUEEN));
                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), PieceType.BISHOP));
                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), PieceType.KNIGHT));
                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), PieceType.ROOK));
            }
        } else {
            System.out.println((board.positionToIndex(myPosition)+(8*i)) > 55);
            if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, 
                    board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), null))) {
                return new HelpReturn(i, board, myPosition, validMoves, checkcheck, false);
            } else {
                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), null));
            }
        }
        if(myPosition.getRow()==2 && checkDirection(board, myPosition, i+1, Direction.NORTH)==1) {
            if((board.positionToIndex(myPosition)+(8*i)) > 55){
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, 
                        board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), null))){
                    return new HelpReturn(i, board, myPosition, validMoves, checkcheck, false);
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), PieceType.QUEEN));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), PieceType.BISHOP));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), PieceType.KNIGHT));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), PieceType.ROOK));
                }
            } else {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), null))) {
                    return new HelpReturn(i, board, myPosition, validMoves, checkcheck, false);
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * 2)), null));
                }
            }
        }
        return new HelpReturn(i, board, myPosition, validMoves, checkcheck, true);
    }
    public static HelpReturn help2(int i, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> validMoves, boolean checkcheck) {
        System.out.println("he");
        if((board.positionToIndex(myPosition)-(8*i)) < 8) {
            System.out.println("he1");
            if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                    board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null))) {
                return new HelpReturn(i, board, myPosition, validMoves, checkcheck, false);
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
                return new HelpReturn(i, board, myPosition, validMoves, checkcheck, false);
            } else {
                validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null));
            }
        }
        if(myPosition.getRow()==7 && checkDirection(board, myPosition, i+1, Direction.SOUTH)==1) {
            if((board.positionToIndex(myPosition)-(8*2)) < 8){
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), null))){
                    return new HelpReturn(i, board, myPosition, validMoves, checkcheck, false);
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), PieceType.QUEEN));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), PieceType.BISHOP));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), PieceType.KNIGHT));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), PieceType.ROOK));
                }
            } else {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(),
                        new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), null))){
                    return new HelpReturn(i, board, myPosition, validMoves, checkcheck, false);
                } else{
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * 2)), null));
                }
            }
        }
        return new HelpReturn(i, board, myPosition, validMoves, checkcheck, true);
    }
    public static HelpReturn help3(int i, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> validMoves, boolean checkcheck) {
        if(board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.WHITE) {
            if((board.positionToIndex(myPosition)+(9*i)) > 55){
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), null))) {
                    return new HelpReturn(i, board, myPosition, validMoves, checkcheck, false);
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), PieceType.QUEEN));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), PieceType.BISHOP));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), PieceType.KNIGHT));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), PieceType.ROOK));
                }
            } else {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), null))) {
                    return new HelpReturn(i, board, myPosition, validMoves, checkcheck, false);
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (7 * i)), null));
                }
            }
        } else {
            if((board.positionToIndex(myPosition)-(9*i)) < 8){
                System.out.println(board.positionToIndex(myPosition)-(7*i));
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), null))) {
                    return new HelpReturn(i, board, myPosition, validMoves, checkcheck, false);
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), PieceType.QUEEN));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), PieceType.BISHOP));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), PieceType.KNIGHT));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), PieceType.ROOK));
                }
            } else {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), null))) {
                    return new HelpReturn(i, board, myPosition, validMoves, checkcheck, false);
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (7 * i)), null));
                }
            }
        }
        return new HelpReturn(i, board, myPosition, validMoves, checkcheck, true);
    }
    public static HelpReturn help4(int i, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> validMoves, boolean checkcheck) {
        if(board.getPiece(myPosition).getTeamColor()== ChessGame.TeamColor.WHITE) {
            if((board.positionToIndex(myPosition)+(7*i)) > 55){
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), null))) {
                    return new HelpReturn(i, board, myPosition, validMoves, checkcheck, false);
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), PieceType.QUEEN));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), PieceType.BISHOP));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), PieceType.KNIGHT));
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), PieceType.ROOK));
                }
            } else {
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), null))) {
                    return new HelpReturn(i, board, myPosition, validMoves, checkcheck, false);
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (9 * i)), null));
                }
            }
        } else {
            System.out.println("here");
            if((board.positionToIndex(myPosition)-(7*i)) < 8){
                if(checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition,
                        board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), null))) {
                    return new HelpReturn(i, board, myPosition, validMoves, checkcheck, false);
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
                    return new HelpReturn(i, board, myPosition, validMoves, checkcheck, false);
                } else {
                    validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (9 * i)), null));
                }
            }
        }
        return new HelpReturn(i, board, myPosition, validMoves, checkcheck, true);
    }
    /*public static Thing1Return dothing1 (int i, ChessBoard board, ChessPosition myPosition, boolean nb, boolean sb, boolean eb, boolean wb,
                          boolean nbt, boolean sbt, boolean ebt, boolean wbt, boolean checkcheck, Collection<ChessMove> validMoves) {

          if (!nb) {
              nbt = nv > 2;
          }
          if (!sb) {
              sbt = sv > 2;
          }
          if (!eb) {
              ebt = ev > 2;
          }
          if (!wb) {
              wbt = wv > 2;
          }
          if (nv % 2 == 1 && !nb) {
              if (checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, 
                      board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null))) {
                  return new Thing1Return(i, board, myPosition, nb, sb, eb, wb, nbt, sbt, ebt, wbt, checkcheck, validMoves, false);
              } else {
                  validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + (8 * i)), null));
              }
          }

          if (sv % 2 == 1 && !sb) {
              if (checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, 
                      board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null))) {
                  return new Thing1Return(i, board, myPosition, nb, sb, eb, wb, nbt, sbt, ebt, wbt, checkcheck, validMoves, false);
              } else {
                  validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - (8 * i)), null));
              }
          }

          if (ev % 2 == 1 && !eb) {
              if (checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, 
                      board.indexToPosition(board.positionToIndex(myPosition) + (i)), null))) {
                  return new Thing1Return(i, board, myPosition, nb, sb, eb, wb, nbt, sbt, ebt, wbt, checkcheck, validMoves, false);
              } else {
                  validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) + i), null));
              }
          }

          if (wv % 2 == 1 && !wb) {
              if (checkcheck == false && board.underAttack(board.getPiece(myPosition).getTeamColor(), new ChessMove(myPosition, 
                      board.indexToPosition(board.positionToIndex(myPosition) - (i)), null))) {
                  return new Thing1Return(i, board, myPosition, nb, sb, eb, wb, nbt, sbt, ebt, wbt, checkcheck, validMoves, false);
              } else {
                  validMoves.add(new ChessMove(myPosition, board.indexToPosition(board.positionToIndex(myPosition) - i), null));
              }
          }
          nb = nbt;
          sb = sbt;
          eb = ebt;
          wb = wbt;
        return new Thing1Return(i, board, myPosition, nb, sb, eb, wb, nbt, sbt, ebt, wbt, checkcheck, validMoves, true);
      }*/
  public static Thing2Return dothing2 (int i, ChessBoard board, ChessPosition myPosition, int nv, int sv, int ev, int wv,
        boolean nb, boolean sb, boolean eb, boolean wb, boolean nbt, boolean sbt, boolean ebt, boolean wbt) {
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
        return new Thing2Return(nbt, sbt, ebt, wbt);
    }
  public static int checkPosition(ChessBoard board, int oidx, int idx, int m, boolean diagonal) {
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
    public static int checkDirection(ChessBoard board, ChessPosition position, int m, Direction d) {

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

}
