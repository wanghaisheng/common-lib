package com.clear.path.impl;

import com.clear.bootstarp.MapDBUtils;
import com.clear.domain.TemplateBean;
import com.clear.path.CreateInfo;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by chengweisen on 2014/11/16.
 * 创建实例目录
 * bin
 * logs
 * conf
 * temp
 * webapps
 * 2.ROOTXML_PATH 目录下面的ROOT.xml文件
 * 3.创建server.xml文件及商口的分配
 * 4.生成启动脚本start.sh文件
 */
public final class CreateInstanceContentPath implements CreateInfo {

    private String root;

    private String serverPath;

    private String tomcatPath;

    private String webappRoot;

    private String configs;

    private VelocityEngine ve;

    private static final String ROOTXML_PATH = "/conf/Catalina/localhost";

    private static final String BASEDIR = System.getProperty("base.dir");

    public CreateInstanceContentPath(String serverRoot, String userRoot, String tomcatRoot, String configs, String webappRoot) {
        this.root = serverRoot;
        this.serverPath = userRoot;
        this.tomcatPath = tomcatRoot;
        this.configs = configs;
        this.webappRoot = webappRoot;
        ve = new VelocityEngine();
        ve.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, BASEDIR);
        ve.init();
        superContextPath();
    }

    public void superContextPath() {

        if (root == null) {
            new Exception("not exists instance root....");
            return;
        }

        if (serverPath == null) {
            new Exception("not exists instance hosts....");
            return;
        }

        if (tomcatPath == null) {
            new Exception("not exists tomcat catalina home....");
            return;
        }

        String fullPath = root + File.separatorChar + serverPath;

        String binPath = fullPath + File.separatorChar + "bin";

        String stopsh = BASEDIR + "/temp/stop.sh";

        try {
            FileUtils.forceMkdir(new File(fullPath));
            FileUtils.forceMkdir(new File(binPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileUtils.copyDirectoryToDirectory(new File(tomcatPath + File.separatorChar + "conf"), new File(fullPath));
            FileUtils.copyDirectoryToDirectory(new File(tomcatPath + File.separatorChar + "logs"), new File(fullPath));
            FileUtils.copyDirectoryToDirectory(new File(tomcatPath + File.separatorChar + "work"), new File(fullPath));
            FileUtils.copyFileToDirectory(new File(stopsh), new File(binPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        MapDBUtils instance = MapDBUtils.getInstance();
        TemplateBean templateBean = instance.getTempBean("tomcat");

        if (templateBean == null) {
            /* 默认值 */
            templateBean = new TemplateBean();
            templateBean.setAjpConnectPort(8009);
            templateBean.setAjpRedirectPort(8443);
            templateBean.setDefaultHostName("");
            templateBean.setHttpConnectorPort(8080);
            templateBean.setHttpRedirectPort(8443);
            templateBean.setServerPort(8005);
            templateBean.setTomcatJpdaPort(8000);
        } else {
            templateBean.setAjpConnectPort(templateBean.getAjpConnectPort() + 1);
            templateBean.setAjpRedirectPort(templateBean.getAjpRedirectPort() + 1);
            templateBean.setDefaultHostName("");
            templateBean.setHttpConnectorPort(templateBean.getHttpConnectorPort() + 1);
            templateBean.setHttpRedirectPort(templateBean.getHttpRedirectPort() + 1);
            templateBean.setServerPort(templateBean.getServerPort() + 1);
            templateBean.setTomcatJpdaPort(templateBean.getTomcatJpdaPort() + 1);
        }

        createStartsh(templateBean.getTomcatJpdaPort(), binPath);

        createRoot(webappRoot + File.separatorChar + serverPath, fullPath + ROOTXML_PATH);

        createServerxml(templateBean, fullPath + "/conf");

        instance.setTempBean("tomcat", templateBean);

    }

    private void createStartsh(int ports, String pathbin) {
        Template template = ve.getTemplate("temp/start.vm");
        VelocityContext vc = new VelocityContext();
        vc.put("tomcatJpdaPort", ports);
        StringWriter stringWriter = new StringWriter();
        template.merge(vc, stringWriter);
        try {
            FileUtils.write(new File(pathbin + File.separatorChar + "start.sh"), stringWriter.toString());
            stringWriter.flush();
            stringWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createRoot(String rootPath, String repath) {
        Template template = ve.getTemplate("temp/ROOT.vm");
        VelocityContext vc = new VelocityContext();
        vc.put("rootPath", rootPath);
        StringWriter stringWriter = new StringWriter();
        template.merge(vc, stringWriter);
        try {
            File fs = new File(repath + File.separatorChar + "ROOT.xml");
            if (fs.exists()) {
                FileUtils.forceDelete(fs);
            }
            FileUtils.write(fs, stringWriter.toString());
            stringWriter.flush();
            stringWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createServerxml(TemplateBean templateBean, String repath) {
        Template template = ve.getTemplate("temp/server.vm");
        VelocityContext vc = new VelocityContext();
        vc.put("serverPort", templateBean.getServerPort());
        vc.put("httpConnectorPort", templateBean.getHttpConnectorPort());
        vc.put("httpRedirectPort", templateBean.getHttpRedirectPort());
        vc.put("ajpConnectPort", templateBean.getAjpConnectPort());
        vc.put("ajpRedirectPort", templateBean.getAjpRedirectPort());
        vc.put("defaultHostName", templateBean.getDefaultHostName());
        StringWriter stringWriter = new StringWriter();
        template.merge(vc, stringWriter);
        try {
            File fs = new File(repath + File.separatorChar + "server.xml");
            if (fs.exists()) {
                FileUtils.forceDelete(fs);
            }
            FileUtils.write(fs, stringWriter.toString());
            stringWriter.flush();
            stringWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
