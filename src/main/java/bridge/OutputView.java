package bridge;

import java.util.Arrays;
import java.util.List;

/**
 * 사용자에게 게임 진행 상황과 결과를 출력하는 역할을 한다.
 */
public class OutputView {
    private static final OutputView outputViewInstance = new OutputView();
    private String print[] = {"[", "["};


    public static OutputView getInstance() {
        return outputViewInstance;
    }

    private OutputView() {
    }

    /**
     * 현재까지 이동한 다리의 상태를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     *
     * @param bridge
     * @param count
     * @param flag
     */
    public String[] printMap(List<String> bridge, int count, Boolean flag, String direction) {
        int index = count - 1;
        String moving = " O ";
        String space = "";
        if (index > 0) {
            space = "|";
        }
        if (flag == false) moving = " X ";
        int number = movingDirectionSet.valueOfLabel(direction);
        int other = (2 - number) / 2; // 0 => 1, 1 => 0
        print[number] += space + moving;
        print[other] += space + "   ";

//        if (bridge.get(index).equals("U")) {
//            print[0] += space + moving;
//            print[1] += space + "   ";
//        }
//        if (bridge.get(index).equals("D")) {
//            print[1] += space + moving;
//            print[0] += space + "   ";
//        }
        System.out.println(print[0] + "]");
        System.out.println(print[1] + "]");
        return print;

    }

    /**
     * 게임의 최종 결과를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     *
     * @param flag
     * @param gameTryCount
     */
    public void printResult(Boolean flag, int gameTryCount) {
        System.out.println();
        System.out.println(gameMessage.END_MESSAGE.message);
        System.out.println(print[0] + "]");
        System.out.println(print[1] + "]");
        String gameStatus = "성공";
        if (flag == false) {
            gameStatus = "실패";
        }
        System.out.println();
        System.out.println(gameMessage.END_GAME_STATUS.message + gameStatus);
        System.out.println(gameMessage.END_GAME_TRY_COUNT.message + gameTryCount);
    }

    public void printStartMessage() {
        System.out.println(gameMessage.START_MESSAGE.message);
    }

    public void reset() {
        this.print[0] = "[";
        this.print[1] = "[";
    }

    private enum movingDirectionSet {
        U("U", 0), D("D", 1);

        private final String label;
        private final int number;

        movingDirectionSet(String d, int i) {
            this.label = d;
            this.number = i;
        }

        public static int valueOfLabel(String label) {
            movingDirectionSet movingDirectionSet = Arrays.stream(values())
                    .filter(value -> value.label.equals(label))
                    .findFirst()
                    .orElse(null);
            return movingDirectionSet.number;
        }
    }

    private enum gameMessage {
        START_MESSAGE("다리 건너기 게임을 시작합니다."),
        END_MESSAGE("최종 게임 결과"),
        END_GAME_STATUS("게임 성공 여부: "),
        END_GAME_TRY_COUNT("총 시도한 횟수: "),
        ;
        private String message;

        gameMessage(String message) {
            this.message = message;
        }

    }
}
