package bridge;

import java.util.List;

import static bridge.constant.message.startGameMessage;

public class GameController {
    private InputView inputView;
    private OutputView outputView;
    public GameController() {
    }

    public GameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }
    public void startGame(){
        System.out.println(startGameMessage);
        // 다리 길이 입력 받음.
        try {
            int bridgeSize = inputView.readBridgeSize();
            List<String> list = new BridgeMaker(new BridgeRandomNumberGenerator()).makeBridge(bridgeSize);
            BridgeGame bridgeGame = BridgeGame.getBridgeGameInstance();
            bridgeGame.setBridge(list);
            while (true) {
                String moveDirection = inputView.readMoving();
                Boolean flag = bridgeGame.move(moveDirection);
                outputView.printMap(moveDirection, flag);
            }
        } catch (IllegalArgumentException e) {

        }



    }
}
