/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.ejb.jboss;

import org.jboss.metadata.javaee.support.IdMetaDataImpl;

/**
 * @author <a href="mailto:bdecoste@jboss.com">William DeCoste</a>
 * @author <a href="mailto:carlo.dewolf@jboss.com">Carlo de Wolf</a>
 * @version <tt>$Revision: 81860 $</tt>
 */
public class MessagePropertiesMetaData extends IdMetaDataImpl {
    private MethodAttributeMetaData method;
    private String delivery;
    private Integer priority;
    private String className;

    public MethodAttributeMetaData getMethod() {
        return method;
    }

    public void setMethod(MethodAttributeMetaData method) {
        this.method = method;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(100);
        sb.append("[");
        sb.append("className=").append(className);
        sb.append(", priority=").append(priority);
        sb.append(", delivery=").append(delivery);
        sb.append(", method=").append(method);
        sb.append("]");
        return sb.toString();
    }
}
