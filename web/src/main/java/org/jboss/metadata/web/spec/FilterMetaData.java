/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import java.util.List;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptionGroup;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
public class FilterMetaData extends NamedMetaDataWithDescriptionGroup {
    private static final long serialVersionUID = 1;

    /**
     * The filter class
     */
    private String filterClass;
    /**
     * The filter init-params
     */
    private List<ParamValueMetaData> initParam;
    private boolean asyncSupported = false;
    private boolean asyncSupportedSet = false;

    public String getFilterName() {
        return getName();
    }

    public void setFilterName(String name) {
        super.setName(name);
    }

    public String getFilterClass() {
        return filterClass;
    }

    public void setFilterClass(String filterClass) {
        this.filterClass = filterClass;
    }

    public List<ParamValueMetaData> getInitParam() {
        return initParam;
    }

    public void setInitParam(List<ParamValueMetaData> initParam) {
        this.initParam = initParam;
    }

    public boolean isAsyncSupported() {
        return asyncSupported;
    }

    public void setAsyncSupported(boolean asyncSupported) {
        this.asyncSupported = asyncSupported;
        asyncSupportedSet = true;
    }

    public boolean getAsyncSupportedSet() {
        return asyncSupportedSet;
    }
}
