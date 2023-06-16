import java.util.Arrays;
import java.util.List;
import utils.*;

public class App {

    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        PrintingIntegers printingIntegers = new PrintingIntegers(nums);
        printingIntegers.invoke();
    }

}
