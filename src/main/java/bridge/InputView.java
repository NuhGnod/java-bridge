package bridge;

import camp.nextstep.edu.missionutils.Console;

/**
 * 사용자로부터 입력을 받는 역할을 한다.
 */
public class InputView {
    private static final int MIN_BRIDGE_SIZE = 3;
    private static final int MAX_BRIDGE_SIZE = 20;
    private static final InputView inputViewInstance = new InputView();

    public static InputView getInstance() {
        return inputViewInstance;
    }

    private InputView() {

    }

    /**
     * 다리의 길이를 입력받는다.
     */
    public int readBridgeSize() {
        System.out.println(GameMessage.MAKE_BRIDGE.message);
        int bridgeSize = Integer.parseInt(Console.readLine());

        try {
            validateBridgeSize(bridgeSize);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            readBridgeSize();
        }
        return bridgeSize;
    }

    private void validateBridgeSize(int bridgeSize) {
        if (bridgeSize > MAX_BRIDGE_SIZE || bridgeSize < MIN_BRIDGE_SIZE) {
            System.out.println("error");
            throw new IllegalArgumentException(ExceptionMessage.INVALID_MAKE_BRIDGE.message);
        }
    }

    /**
     * 사용자가 이동할 칸을 입력받는다.
     */
    public String readMoving() {
        System.out.println(GameMessage.MOVE_BRIDGE.message);
        String moveDirection = Console.readLine();
        try {
            validateMoveCommand(moveDirection);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            readMoving();
        }
        return moveDirection;

    }

    private void validateMoveCommand(String moveDirection) {
        if (!(moveDirection.equals("U") || moveDirection.equals("D"))) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_MOVE_BRIDGE.message);
        }
    }

    /**
     * 사용자가 게임을 다시 시도할지 종료할지 여부를 입력받는다.
     */
    public String readGameCommand() {
        return null;
    }


    private enum GameMessage {
        MAKE_BRIDGE("다리의 길이를 입력해주세요."),
        MOVE_BRIDGE("이동할 칸을 선택해주세요. (위: U, 아래: D)");

        private String message;

        GameMessage(String message) {
            this.message = message;
        }
    }

    private enum ExceptionMessage {
        INVALID_MAKE_BRIDGE("[ERROR] 다리 길이는 %d부터 %d 사이의 숫자여야 합니다.",
                MIN_BRIDGE_SIZE, MAX_BRIDGE_SIZE),
        INVALID_MOVE_BRIDGE("[ERROR] 다리는 위(U) 또는 아래(D) 방향으로만 건널 수 있습니다."),
        ;
        private String message;

        ExceptionMessage(String message) {
            this.message = message;
        }

        ExceptionMessage(String message, int minBridgeSize, int maxBridgeSize) {
            this.message = String.format(message, minBridgeSize, maxBridgeSize);
        }
    }
}
