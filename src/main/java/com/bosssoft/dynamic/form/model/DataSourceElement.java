package com.bosssoft.dynamic.form.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("datasource")
public class DataSourceElement {
    
    private String key;

    @XStreamAsAttribute
    private String type;
    
    @XStreamAsAttribute
    private String version;
    
    @XStreamAsAttribute
    private String jar;
    
    @XStreamAsAttribute
    private String driver;
    
    @XStreamAsAttribute
    private String urlTemplate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getJar() {
        return jar;
    }

    public void setJar(String jar) {
        this.jar = jar;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

   
    public String getUrlTemplate() {
        return urlTemplate;
    }

    public void setUrlTemplate(String urlTemplate) {
        this.urlTemplate = urlTemplate;
    }

    @Override
    public String toString() {
        return "DataSourceMate [key=" + key + ", type=" + type + ", version=" + version + ", jar=" + jar + ", driver="
            + driver + ", urlTemplate=" + urlTemplate + "]";
    }

    

    

}
