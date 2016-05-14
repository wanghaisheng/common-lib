import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chengweisen on 2015/4/7.
 */
public class ConfingTest {

    @Test
    public void quertTest() {

        int[] i = {1, 2, 3, 4, 5, 6, 7, 8, 9};

        int pageSize = 2;

        int modl = i.length % pageSize;

        int page;
        if (modl > 0) {
            page = i.length / pageSize + 1;
        } else {
            page = i.length / pageSize;
        }

        for (int n = 0; n < pageSize; n++) {
            List<int[]> ints = Arrays.asList(i).subList(n*page, page);
        }
    }

}
