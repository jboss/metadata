/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 87568 $
 */
public class LocaleEncodingMetaData extends IdMetaDataImpl {
    private static final long serialVersionUID = 1;

    protected String locale;
    protected String encoding;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
