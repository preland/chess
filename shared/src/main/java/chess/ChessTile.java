package chess;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class ChessTile {

    ChessPiece piece;
    //ChessTile northTile, northeastTile, eastTile, southeastTile, southTile, southwestTile, westTile, northwestTile;
    private final Map<tileDirection, ChessTile> connectedTiles = new EnumMap<>(tileDirection.class);
    public enum tileDirection {
        northTile, northeastTile, eastTile, southeastTile, southTile, southwestTile, westTile, northwestTile
    }

    @Override
    public String toString() {
        StringBuilder connectString = new StringBuilder();
        for (tileDirection direction : connectedTiles.keySet()) {
            connectString.append(direction.name()).append(":").append(connectedTiles.get(direction).getClass().getSimpleName()).append(" ");
        }
        return "ChessTile{" +
                "piece=" + piece +
                ", connectedTiles=" + connectString.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessTile chessTile = (ChessTile) o;
        return Objects.equals(piece, chessTile.piece) && Objects.equals(connectedTiles, chessTile.connectedTiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, connectedTiles);
    }

    public ChessTile() {
        piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.NONE);
        /*northTile = null;
        northeastTile = null;
        eastTile = null;
        southeastTile = null;
        southTile = null;
        southwestTile = null;
        westTile = null;
        northwestTile = this;*/
    }

    public ChessPiece getPiece() {
      return piece;
    }
    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }
    public ChessTile getConnectedTile(tileDirection tileDirection) {
        return connectedTiles.get(tileDirection);
    }
    public void setConnectedTiles(tileDirection tileDirection, ChessTile chessTile) {
        connectedTiles.put(tileDirection,chessTile);
    }

    public static class EdgeTile extends ChessTile {
        @Override
        public ChessTile getConnectedTile(tileDirection tileDirection) {
            return this;
        }

        @Override
        public String toString() {
            return "EdgeTile";
        }
    }

}
