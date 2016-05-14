import com.clear.dao.CreateNative;
import org.apache.ibatis.jdbc.SQL;
import org.junit.Test;

/**
 * Created by chengweisen on 2015/5/1.
 */
public class CreateNativeTest {

    @Test
    public void nativeTest() {
        CreateNative.createMdNativ("D:\\assets\\Study");
    }

    @Test
    public void formateSQLTest() {
        String t_user = new SQL()
                .UPDATE("t_user")
                .SET("user_name = ?")
                .WHERE("ID = ?").toString();
        System.out.println(t_user);
    }

}
