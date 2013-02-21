/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * taglib/tag-file metadata
 *
 * @author Remy Maucherat
 * @version $Revision: 75201 $
 */
public class AttributeMetaData extends NamedMetaDataWithDescriptions {
    private static final long serialVersionUID = 1;

    private String required;
    private String rtexprvalue;
    private String type;
    private String fragment;
    private DeferredValueMetaData deferredValue;
    private DeferredMethodMetaData deferredMethod;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public String getRtexprvalue() {
        return rtexprvalue;
    }

    public void setRtexprvalue(String rtexprvalue) {
        this.rtexprvalue = rtexprvalue;
    }

    public String getFragment() {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }

    public DeferredValueMetaData getDeferredValue() {
        return deferredValue;
    }

    public void setDeferredValue(DeferredValueMetaData deferredValue) {
        this.deferredValue = deferredValue;
    }

    public DeferredMethodMetaData getDeferredMethod() {
        return deferredMethod;
    }

    public void setDeferredMethod(DeferredMethodMetaData deferredMethod) {
        this.deferredMethod = deferredMethod;
    }

    public String toString() {
        StringBuilder tmp = new StringBuilder("AttributeMetaData(id=");
        tmp.append(getId());
        tmp.append(",required=");
        tmp.append(required);
        tmp.append(",rtexprvalue=");
        tmp.append(rtexprvalue);
        tmp.append(",fragment=");
        tmp.append(fragment);
        tmp.append(",type=");
        tmp.append(type);
        tmp.append(')');
        return tmp.toString();
    }
}
