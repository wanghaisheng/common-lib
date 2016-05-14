import org.junit.Test;

/**
 * Created by chengweisen on 2015/4/6.
 */
public class FormateTest {

    @Test
    public void mainTest() {
        String[] str = {"a", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};
        for (int j = 0; j < 10; j++) {
            System.out.println(String.format("%-24s","adfdfd"));
        }
    }

}
