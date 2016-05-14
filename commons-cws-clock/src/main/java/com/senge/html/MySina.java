package com.clear.html;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class MySina {

    private WebClient client;

    private final static Logger logger = LoggerFactory.getLogger(MySina.class);

    private final static String ERPLOGINURL = "http://kaoqin.jd.com";

    private String username = "";

    private String password = "";

    private String osbase = "";

    public MySina() {
        client = new WebClient(BrowserVersion.INTERNET_EXPLORER_8);
        osbase = System.getProperty("os.base");
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(osbase + "/conf/conf.properties"));
            this.username = properties.getProperty("username");
            this.password = properties.getProperty("password");
            logger.info("username & password ======>>>  " + username + " & " + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mailLoginBySina() {
        URL loginUrl = getNewUrl(ERPLOGINURL);
        HtmlPage loginPage = getHtmlPage(loginUrl);
        /* 设置登陆信息 */
        HtmlForm loginForm = loginPage.getFormByName("form1");
        HtmlInput u = loginForm.getInputByName("Name");
        HtmlInput psw = loginForm.getInputByName("Password");
        HtmlSubmitInput loginButton = loginForm.getInputByName("Logon");
        u.setValueAttribute(username);
        psw.setValueAttribute(password);
        HtmlPage login = null;
        try {
            login = loginButton.click();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /* 打卡页面 */
        URL kaoUrl = getNewUrl(login.getUrl().toString());
        HtmlPage kaPage = getHtmlPage(kaoUrl);
        HtmlElement clockIn = kaPage.getAnchorByText("打卡");
        try {
            /* 打卡 */
            HtmlPage click = clockIn.click();
            click.getWebResponse();
            DateTime dateTime = DateTime.now();
            String times = dateTime.toString("yyyy-MM-dd HH:mm:ss aa");
            if (click != null) {
                logger.info("times ======== >> " + times);
                insertRecord(times);
                if (StringUtils.contains(times, "PM") || StringUtils.contains(times, "下午")) {
                    if (dateTime.getHourOfDay() >= 22) {
                        insertRecord("++++++++++++++++++++++++++++++++++");
                    } else {
                        insertRecord("----------------------------------");
                    }
                }
            } else {
                mailLoginBySina();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.closeAllWindows();
            client = null;
        }

    }

    public void insertRecord(String time) {
        String path = osbase + "/logs/readme.txt";
        File logs = new File(path);
        if (!logs.exists()) {
            try {
                logs.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileUtils.writeStringToFile(new File(path), time + "\n", "utf-8", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HtmlPage getHtmlPage(URL newUrl) {
        HtmlPage htmlPage = null;
        try {
            htmlPage = client.getPage(newUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return htmlPage;
    }

    public URL getNewUrl(String newUrl) {
        HtmlPage newUrlStr = null;
        try {
            newUrlStr = client.getPage(newUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newUrlStr.getUrl();
    }

}