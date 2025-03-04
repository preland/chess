package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    TeamColor team;
    ChessBoard board;
    public ChessGame() {
        team = TeamColor.WHITE;
        board = new ChessBoard();
        board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return team;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.team = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }
    
    public enum BoardType {
        DEFAULT
    }
    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = board.getPiece(startPosition);

        if(piece == null) {
            return new ArrayList<>();
        }
        Collection<ChessMove> initialMoves = board.getPiece(startPosition).pieceMoves(board, startPosition);
        Collection<ChessMove> finalMoves = new ArrayList<>();
        for (ChessMove move : initialMoves) {
            ChessPiece tempPiece = board.getPiece(move.getEndPosition());
            board.addPiece(move.getStartPosition(), null);
            board.addPiece(move.getEndPosition(), piece);

            if (!isInCheck(piece.getTeamColor())) {
                finalMoves.add(move);
            }
            board.addPiece(move.getEndPosition(), tempPiece);
            board.addPiece(move.getStartPosition(), piece);
        }
       return finalMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if(isInCheck(team)){
            throw new InvalidMoveException();
        }

        if(board.getPiece(move.getStartPosition()) == null){
            throw new InvalidMoveException();
        }

        if(board.getPiece(move.getStartPosition()).getTeamColor() != getTeamTurn()) {
            throw new InvalidMoveException();
        }
        Collection<ChessMove> v = board.getPiece(move.getStartPosition()).pieceMoves(board, move.getStartPosition());
        if(!v.contains(move)) {
            throw new InvalidMoveException();
        }
        if(move.promotionPiece != null) {
            board.addPiece(move.getEndPosition(), new ChessPiece(board.getPiece(move.getStartPosition()).getTeamColor(), move.getPromotionPiece()));
            board.addPiece(move.getStartPosition(), null);
        } else {
            board.addPiece(move.getEndPosition(), new ChessPiece(board.getPiece(move.getStartPosition()).getTeamColor(), 
                  board.getPiece(move.getStartPosition()).getPieceType()));
            board.addPiece(move.getStartPosition(), null);
        }
        setTeamTurn( getTeamTurn() == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPos = board.findKing(teamColor);
        return board.underAttack(teamColor, null);
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if(!isInCheck(teamColor)){
            return false;
        }
        ChessPosition kingPos = board.findKing(teamColor);
        return getAllMoves(teamColor).isEmpty();
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        if(getAllMoves(teamColor).isEmpty()){
            return !isInCheck(teamColor);
        }
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
    public Collection<ChessMove> getAllMoves(TeamColor teamColor) {
        Collection<ChessMove> moves = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            ChessPiece tempPiece = board.getPiece(board.indexToPosition(i));
            if (tempPiece == null) {
                continue;
            }
            if(tempPiece.getTeamColor() != teamColor) {
                continue;
            }
            moves.addAll(validMoves(board.indexToPosition(i)));
        }
        return moves;
    }
}
