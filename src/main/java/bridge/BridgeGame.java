package bridge;

import java.util.List;

/**
 * 다리 건너기 게임을 관리하는 클래스
 */
public class BridgeGame {
    private List<String> bridge;
    private static final BridgeGame bridgeGame = new BridgeGame();
    private int count = 0;
    public static BridgeGame getBridgeGameInstance(){
        return bridgeGame;
    }
    private BridgeGame(){

    }

    public List<String> getBridge() {
        return bridge;
    }

    public void setBridge(List<String> bridge) {
        this.bridge = bridge;
    }

    /**
     * 사용자가 칸을 이동할 때 사용하는 메서드
     * <p>
     * 이동을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public boolean move(String direction) {
        int oldCount = this.count;
        this.count++;
        return validateMove(direction, oldCount);

    }

    /**
     * 다리의 형태와 입력한 이동방향이 올바른지 확인.
    */
    private boolean validateMove(String direction, int index) {
        String command = bridge.get(index);
        return command.equals(direction);
    }

    /**
     * 사용자가 게임을 다시 시도할 때 사용하는 메서드
     * <p>
     * 재시작을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void retry() {
    }
}
