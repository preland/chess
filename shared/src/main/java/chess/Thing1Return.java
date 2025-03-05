package chess;

import java.util.Collection;

public record Thing1Return(int i, ChessBoard board, ChessPosition myPosition, boolean nb, boolean sb, boolean eb, boolean wb,
                           boolean nbt, boolean sbt, boolean ebt, boolean wbt, boolean checkcheck, Collection<ChessMove> validMoves, boolean willBreak) {
}
