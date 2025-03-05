package chess;

import java.util.Collection;

public record HelpReturn(int i, ChessBoard board, ChessPosition myPosition, Collection<ChessMove> validMoves, boolean checkcheck, boolean willBreak) { }
