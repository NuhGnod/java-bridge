package bridge;

import java.util.List;


public class GameController {
    private InputView inputView;
    private OutputView outputView;
    private int gameTryCount=0;
    public GameController() {
    }

    public GameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }
    public void startGame(){
        outputView.printStartMessage();
        gameTryCount++;
        // 다리 길이 입력 받음.
        try {
            int bridgeSize = inputView.readBridgeSize();
            List<String> list = new BridgeMaker(new BridgeRandomNumberGenerator()).makeBridge(bridgeSize);
            BridgeGame bridgeGame = BridgeGame.getBridgeGameInstance();
            bridgeGame.setBridge(list);
            while (true) {

                String moveDirection = inputView.readMoving();
                Boolean flag = bridgeGame.move(moveDirection);
                outputView.printMap(bridgeGame.getBridge(), bridgeGame.getCount(), flag, moveDirection);

                if (flag == false) {

                    String command = inputView.readGameCommand();
                    if(command.equals("Q")){
                        outputView.printResult(flag, gameTryCount);

                        break;
                    }
                    if(command.equals("R")){
                        bridgeGame.retry();
                        gameTryCount++;
                        outputView.reset();
                    }
                }
                if (flag == true && bridgeSize == bridgeGame.getCount() ) {
                    //성공.
                    outputView.printResult(flag, gameTryCount);
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
