package tutorial.ch01.sec01;

public class SwitchExample {

    private static String CONDITION = "CONDITION";

    // compile with javac -Xlint:fallthrough
    // In IntelliJ: Settings > Build, Execution, Deployment > Compiler > Java Compiler > Additional

    @SuppressWarnings("fallthrough")
    public static void main(String[] args) {
        String condition = "CONDITION 2";
        String result;
        switch (condition) {
            case "CONDITION 1":
                result = CONDITION + " " + 1;
            case "CONDITION 2":
                result = CONDITION + " " + 2;
            case "CONDITION 3":
                result = CONDITION + " " + 3;
                break;
            case "CONDITION 4":
                result = CONDITION + " " + 4;
                break;
            default:
                result = CONDITION + " DEFAULT";
        }
        System.out.println(result);
    }

}
