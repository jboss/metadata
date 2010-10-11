/*
 * JBoss, Home of Professional Open Source
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.metadata.web.spec;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.jboss.metadata.javaee.support.AugmentableMetaData;
import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 83549 $
 */
@XmlType(name = "form-login-configType", propOrder = { "loginPage", "errorPage" })
public class FormLoginConfigMetaData extends IdMetaDataImpl implements AugmentableMetaData<FormLoginConfigMetaData> {
    private static final long serialVersionUID = 1;

    private String loginPage;
    private String errorPage;

    public String getLoginPage() {
        return loginPage;
    }

    @XmlElement(name = "form-login-page")
    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getErrorPage() {
        return errorPage;
    }

    @XmlElement(name = "form-error-page")
    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    public void augment(FormLoginConfigMetaData webFragmentMetaData, FormLoginConfigMetaData webMetaData,
            boolean resolveConflicts) {
        if (getLoginPage() == null) {
            setLoginPage(webFragmentMetaData.getLoginPage());
        } else if (webFragmentMetaData.getLoginPage() != null) {
            if (!resolveConflicts && !getLoginPage().equals(webFragmentMetaData.getLoginPage())
                    && (webMetaData == null || webMetaData.getLoginPage() == null)) {
                throw new IllegalStateException("Unresolved conflict on login page: " + getLoginPage());
            }
        }
        if (getErrorPage() == null) {
            setErrorPage(webFragmentMetaData.getErrorPage());
        } else if (webFragmentMetaData.getErrorPage() != null) {
            if (!resolveConflicts && !getErrorPage().equals(webFragmentMetaData.getErrorPage())
                    && (webMetaData == null || webMetaData.getErrorPage() == null)) {
                throw new IllegalStateException("Unresolved conflict on error page: " + getErrorPage());
            }
        }
    }
}
