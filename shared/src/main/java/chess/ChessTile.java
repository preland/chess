package chess;

import java.util.Objects;

public class ChessTile {

    ChessPiece piece;
    ChessTile northTile, northeastTile, eastTile, southeastTile, southTile, southwestTile, westTile, northwestTile;
    @Override
    public String toString() {
        return "ChessTile{" +
                "piece=" + piece +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessTile chessTile = (ChessTile) o;
        return Objects.equals(piece, chessTile.piece) && Objects.equals(northTile, chessTile.northTile) && Objects.equals(northeastTile, chessTile.northeastTile) && Objects.equals(eastTile, chessTile.eastTile) && Objects.equals(southeastTile, chessTile.southeastTile) && Objects.equals(southTile, chessTile.southTile) && Objects.equals(southwestTile, chessTile.southwestTile) && Objects.equals(westTile, chessTile.westTile) && Objects.equals(northwestTile, chessTile.northwestTile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, northTile, northeastTile, eastTile, southeastTile, southTile, southwestTile, westTile, northwestTile);
    }

    public ChessTile() {

    }

    public ChessPiece getPiece() {
      return piece;
    }
    public ChessTile getNorthTile() {
      return northTile;
    }
    public ChessTile getNortheastTile() {
      return northeastTile;
    }
    public ChessTile getEastTile() {
      return eastTile;
    }
    public ChessTile getSoutheastTile() {
      return southeastTile;
    }
    public ChessTile getSouthTile() {
      return southTile;
    }
    public ChessTile getSouthwestTile() {
      return southwestTile;
    }
    public ChessTile getWestTile() {
      return westTile;
    }
    public ChessTile getNorthwestTile() {
      return northwestTile;
    }

    public void setPiece(ChessPiece piece) {
      this.piece = piece;
    }
    public void setNorthTile(ChessTile northTile) {
      this.northTile = northTile;
    }
    public void setNortheastTile(ChessTile northeastTile) {
      this.northeastTile = northeastTile;
    }
    public void setEastTile(ChessTile eastTile) {
      this.eastTile = eastTile;
    }
    public void setSoutheastTile(ChessTile southeastTile) {
      this.southeastTile = southeastTile;
    }
    public void setSouthTile(ChessTile southTile) {
      this.southTile = southTile;
    }
    public void setSouthwestTile(ChessTile southwestTile) {
      this.southwestTile = southwestTile;
    }
    public void setWestTile(ChessTile westTile) {
      this.westTile = westTile;
    }
    public void setNorthwestTile(ChessTile northwestTile) {
      this.northwestTile = northwestTile;
    }

}
