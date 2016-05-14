package com.clear.domain;

import java.io.Serializable;

/**
 * Created by chengweisen on 2014/12/14.
 */
public class TemplateBean implements Serializable {

    /* tomcat jpda 调试端口号 */
    private int tomcatJpdaPort;

    /* server port */
    private int serverPort;

    /* http port default 8080 */
    private int httpConnectorPort;

    /* default 8433 */
    private int httpRedirectPort;

    private int ajpConnectPort;

    private int ajpRedirectPort;

    /* default null */
    private String defaultHostName;

    public int getTomcatJpdaPort() {
        return tomcatJpdaPort;
    }

    public void setTomcatJpdaPort(int tomcatJpdaPort) {
        this.tomcatJpdaPort = tomcatJpdaPort;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getHttpConnectorPort() {
        return httpConnectorPort;
    }

    public void setHttpConnectorPort(int httpConnectorPort) {
        this.httpConnectorPort = httpConnectorPort;
    }

    public int getHttpRedirectPort() {
        return httpRedirectPort;
    }

    public void setHttpRedirectPort(int httpRedirectPort) {
        this.httpRedirectPort = httpRedirectPort;
    }

    public int getAjpConnectPort() {
        return ajpConnectPort;
    }

    public void setAjpConnectPort(int ajpConnectPort) {
        this.ajpConnectPort = ajpConnectPort;
    }

    public int getAjpRedirectPort() {
        return ajpRedirectPort;
    }

    public void setAjpRedirectPort(int ajpRedirectPort) {
        this.ajpRedirectPort = ajpRedirectPort;
    }

    public String getDefaultHostName() {
        return defaultHostName;
    }

    public void setDefaultHostName(String defaultHostName) {
        this.defaultHostName = defaultHostName;
    }
}
