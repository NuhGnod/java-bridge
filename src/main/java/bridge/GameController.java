package bridge;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


public class GameController {
    private InputView inputView;
    private OutputView outputView;
    private BridgeGame bridgeGame;
    private int gameTryCount = 0;
    private final Map<GameStatus, Supplier<GameStatus>> gameTower;

    public GameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameTower = new EnumMap(GameStatus.class);
        initializeGameTower();
    }

    public void service() { //다리부터 생성.
        GameStatus gameStatus = logic(GameStatus.CREATE_BRIDGE);
        while (gameStatus != GameStatus.PROCESS_END) {
            gameStatus = logic(gameStatus);
        }
    }

    private GameStatus logic(GameStatus gameStatus) {
        try {
            return gameTower.get(gameStatus).get();
        } catch (NullPointerException e) {
            return GameStatus.PROCESS_END;
        }
    }

    private void initializeGameTower() {
        gameTower.put(GameStatus.GAME_START, this::startGame);
        gameTower.put(GameStatus.CREATE_BRIDGE, this::createBridge);
//        gameTower.put(GameStatus.GAME_RESTART, this::restartGame);
        gameTower.put(GameStatus.GAME_END, this::gameEnd);
//        gameTower.put(GameStatus.GAME_FAIL, this::gameFail);
        gameTower.put(GameStatus.GAME_SUCCESS, this::gameSuccess);
        gameTower.put(GameStatus.GAME_Q, this::gameFail);
        gameTower.put(GameStatus.GAME_R, this::restartGame);
    }

    private GameStatus gameSuccess() {
        outputView.printResult(true, gameTryCount);
        return GameStatus.PROCESS_END;
    }

    private GameStatus gameFail() {
        outputView.printResult(false, gameTryCount);
        return GameStatus.PROCESS_END;
    }

    private GameStatus gameEnd() {
        String command = inputView.readGameCommand();
        return gameStatusFrom(command);
    }

    private GameStatus gameStatusFrom(String command) {
        return GameStatus.from(command);
        // Enum에서 매칭되는거 있는지 찾는 메서드를 둬도 괜찮을듯

    }
    private GameStatus restartGame() {
        bridgeGame.retry();
        outputView.reset();
        return GameStatus.GAME_START;
    }

    private GameStatus createBridge() {
        outputView.printStartMessage();
        int bridgeSize = inputView.readBridgeSize();
        List<String> bridge = new BridgeMaker(new BridgeRandomNumberGenerator()).makeBridge(bridgeSize);
        bridgeGame = BridgeGame.getBridgeGameInstance();
        bridgeGame.setBridge(bridge); //다리 생성.
        return GameStatus.GAME_START;
    }

    public GameStatus startGame() {
        gameTryCount++;
        for (int i = 0; i < bridgeGame.getBridge().size(); i++) {
            String moveDirection = inputView.readMoving(); // 움직임.
            Boolean flag = bridgeGame.move(moveDirection); // 정상적인 움직임 여부.
            outputView.printMap(bridgeGame.getBridge(), bridgeGame.getCount(), flag, moveDirection);
            if (flag == false) {
                return GameStatus.GAME_END;
            }
        }
        return GameStatus.GAME_SUCCESS;

    }


}
