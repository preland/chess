package service.request_result;

public record JoinGameRequest(String authorization, String playerColor, int gameID) {
}
