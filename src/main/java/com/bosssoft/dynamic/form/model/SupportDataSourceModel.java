 package com.bosssoft.dynamic.form.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("datasources")
 public class SupportDataSourceModel {

    @XStreamImplicit(itemFieldName="datasource")
    private List<DataSourceElement> supportDataSources=new ArrayList<DataSourceElement>();

    public List<DataSourceElement> getSupportDataSources() {
        return supportDataSources;
    }

    public void setSupportDataSources(List<DataSourceElement> supportDataSources) {
        this.supportDataSources = supportDataSources;
    }
    
    
}
