/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.javaee.spec;

import org.jboss.metadata.javaee.support.NamedMetaDataWithDescriptions;

/**
 * Metadata for javaee:mail-sessionType
 *
 * @author Eduardo Martins
 *
 */
public class MailSessionMetaData extends NamedMetaDataWithDescriptions {

    /**
     *
     */
    private static final long serialVersionUID = -8828730452352230094L;

    /**
     *
     */
    private String storeProtocol;

    /**
     *
     */
    private String storeProtocolClass;

    /**
    *
    */
    private String transportProtocol;

    /**
    *
    */
    private String transportProtocolClass;

    /**
   *
   */
    private String host;

    /**
   *
   */
    private String user;

    /**
  *
  */
    private String password;

    /**
 *
 */
    private String from;

    /**
     *
     */
    private PropertiesMetaData properties;

    /**
     *
     * @return
     */
    public String getStoreProtocol() {
        return storeProtocol;
    }

    /**
     *
     * @param storeProtocol
     */
    public void setStoreProtocol(String storeProtocol) {
        this.storeProtocol = storeProtocol;
    }

    /**
     *
     * @return
     */
    public String getStoreProtocolClass() {
        return storeProtocolClass;
    }

    /**
     *
     * @param storeProtocolClass
     */
    public void setStoreProtocolClass(String storeProtocolClass) {
        this.storeProtocolClass = storeProtocolClass;
    }

    /**
     *
     * @return
     */
    public String getTransportProtocol() {
        return transportProtocol;
    }

    /**
     *
     * @param transportProtocol
     */
    public void setTransportProtocol(String transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    /**
     *
     * @return
     */
    public String getTransportProtocolClass() {
        return transportProtocolClass;
    }

    /**
     *
     * @param transportProtocolClass
     */
    public void setTransportProtocolClass(String transportProtocolClass) {
        this.transportProtocolClass = transportProtocolClass;
    }

    /**
     *
     * @return
     */
    public String getHost() {
        return host;
    }

    /**
     *
     * @param host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     *
     * @return
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getFrom() {
        return from;
    }

    /**
     *
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     *
     * @return
     */
    public PropertiesMetaData getProperties() {
        return properties;
    }

    /**
     *
     * @param properties
     */
    public void setProperties(PropertiesMetaData properties) {
        this.properties = properties;
    }

}
