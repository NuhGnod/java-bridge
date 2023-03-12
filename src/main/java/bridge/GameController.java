package bridge;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


public class GameController {
    private InputView inputView;
    private OutputView outputView;
    private int gameTryCount = 0;
    private final Map<GameStatus, Supplier<GameStatus>> gameTower;

    public GameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.gameTower = new EnumMap<GameStatus, Supplier<GameStatus>>(GameStatus.class);
        initializeGameTower();
    }

    public void service() {
        GameStatus gameStatus = logic(GameStatus.CREATE_BRIDGE);
        while( gameStatus != GameStatus.GAME_END){
            gameStatus = logic(gameStatus);
        }
    }

    private GameStatus logic(GameStatus gameStatus) {

        return gameStatus;
    }

    private void initializeGameTower() {
        gameTower.put(GameStatus.GAME_START, this.startGame());
        gameTower.put(GameStatus.CREATE_BRIDGE, this.createBridge());

    }

    private Supplier<GameStatus> createBridge() {

        return null;
    }

    public Supplier<GameStatus> startGame() {
        outputView.printStartMessage();
        gameTryCount++;
        // 다리 길이 입력 받음.
        try {
            int bridgeSize = inputView.readBridgeSize();
            List<String> list = new BridgeMaker(new BridgeRandomNumberGenerator()).makeBridge(bridgeSize);
            BridgeGame bridgeGame = BridgeGame.getBridgeGameInstance();
            bridgeGame.setBridge(list); //다리 생성.
            while (true) {

                String moveDirection = inputView.readMoving(); // 움직임.
                Boolean flag = bridgeGame.move(moveDirection);
                outputView.printMap(bridgeGame.getBridge(), bridgeGame.getCount(), flag, moveDirection);

                if (flag == false) {

                    String command = inputView.readGameCommand();
                    if (command.equals("Q")) {
                        outputView.printResult(flag, gameTryCount);
                        break;
                    }
                    if (command.equals("R")) {
                        bridgeGame.retry();
                        gameTryCount++;
                        outputView.reset();
                    }
                }
                if (flag == true && bridgeSize == bridgeGame.getCount()) {
                    //성공.
                    outputView.printResult(flag, gameTryCount);
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
