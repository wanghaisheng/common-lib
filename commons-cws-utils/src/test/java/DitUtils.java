import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import org.junit.Test;

/**
 * Created by chengweisen on 2015/4/1.
 */
public final class DitUtils {

    @Test
    public void dbmapTest() {
        System.setProperty("os.base", "d:/");
        com.clear.v3.bin.DitUtils.put(123L, new TableMapEventData());
        System.out.println(com.clear.v3.bin.DitUtils.get(123L) == null);
    }
}
