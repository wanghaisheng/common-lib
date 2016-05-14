import com.clear.bb.utils.ConfigBean;
import com.clear.bb.utils.LoadConfig;
import org.junit.Test;

import java.util.List;

/**
 * Created by chengweisen on 2015/4/2.
 */
public class ConfigBeanTest {

    @Test
    public void loadConfigBeanTest() {
        System.setProperty("os.base", "d:/");
        List<ConfigBean> configBeans = LoadConfig.getConfigBeans();
    }
}
