package bridge;

import java.util.Arrays;

public enum GameStatus {
    GAME_START,
    GAME_END,
//    GAME_RESTART,
    GAME_SUCCESS,
//    GAME_FAIL,
    CREATE_BRIDGE,
    PROCESS_END,
    GAME_Q("Q"),
    GAME_R("R"),
    ;

    private String command;

    GameStatus() {

    }

    GameStatus(String command) {
        this.command = command;
    }

    public static GameStatus from(String command) {
        GameStatus gameStatus = Arrays.stream(values()).filter(value -> value.equals(command)).findAny().orElseThrow(IllegalArgumentException::new);
        return gameStatus;

    }

}
