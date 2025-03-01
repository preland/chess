package service;

public record JoinGameRequest(String authorization, String playerColor, int gameID) {
}
