import com.alibaba.fastjson.JSON;
import com.clear.bootstarp.MapDBUtils;
import com.clear.domain.TemplateBean;
import org.junit.Test;

/**
 * Created by Administrator on 2015/7/17.
 */
public class MapDBUtilsTest {

    @Test
    public void testMapdb(){
        System.setProperty("base.dir","d:/");
        TemplateBean templateBean = new TemplateBean();
        templateBean.setAjpConnectPort(8081);
        MapDBUtils instance = MapDBUtils.getInstance();
        //instance.setTempBean("templateBean", templateBean);
        TemplateBean templateBean1 = instance.getTempBean("tomcat");
        System.out.println(JSON.toJSON(templateBean1));
    }
}
