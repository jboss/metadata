/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import java.util.List;

import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImplWithDescriptions;

/**
 * The tag/validator metadata
 *
 * @author Remy Maucherat
 * @version $Revision: 81768 $
 */
public class ValidatorMetaData extends IdMetaDataImplWithDescriptions {
    private static final long serialVersionUID = 1;

    private String validatorClass;
    private List<ParamValueMetaData> initParams;

    public String getValidatorClass() {
        return validatorClass;
    }

    public void setValidatorClass(String validatorClass) {
        this.validatorClass = validatorClass;
    }

    public List<ParamValueMetaData> getInitParams() {
        return initParams;
    }

    public void setInitParams(List<ParamValueMetaData> initParams) {
        this.initParams = initParams;
    }

}
