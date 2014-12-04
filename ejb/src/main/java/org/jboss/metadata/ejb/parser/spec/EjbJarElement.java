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

package org.jboss.metadata.ejb.parser.spec;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of all XML elements that are allowed in a ejb-jar.xml
 * <p/>
 * Note that this enumeration contains all possible elements allowed in the ejb-jar.xml,
 * irrespective of its xsd/dtd version.
 * <p/>
 * User: Jaikiran Pai
 */
public enum EjbJarElement {
    UNKNOWN(null),

    ABSTRACT_SCHEMA_NAME("abstract-schema-name"),
    ACCESS_TIMEOUT("access-timeout"),
    ACKNOWLEDGE_MODE("acknowledge-mode"),
    ACTIVATION_CONFIG("activation-config"),
    ACTIVATION_CONFIG_PROPERTY("activation-config-property"),
    ACTIVATION_CONFIG_PROPERTY_NAME("activation-config-property-name"),
    ACTIVATION_CONFIG_PROPERTY_VALUE("activation-config-property-value"),
    AFTER_BEGIN_METHOD("after-begin-method"),
    AFTER_COMPLETION_METHOD("after-completion-method"),
    APPLICATION_EXCEPTION("application-exception"),
    AROUND_INVOKE("around-invoke"),
    AROUND_TIMEOUT("around-timeout"),
    ASSEMBLY_DESCRIPTOR("assembly-descriptor"),
    ASYNC_METHOD("async-method"),


    BEAN_METHOD("bean-method"),
    BEFORE_COMPLETION_METHOD("before-completion-method"),
    BUSINESS_LOCAL("business-local"),
    BUSINESS_REMOTE("business-remote"),

    CASCADE_DELETE("cascade-delete"),
    CLASS("class"),
    CMP_FIELD("cmp-field"),
    CMP_VERSION("cmp-version"),
    CMR_FIELD("cmr-field"),
    CMR_FIELD_NAME("cmr-field-name"),
    CMR_FIELD_TYPE("cmr-field-type"),
    CONCURRENCY_MANAGEMENT_TYPE("concurrency-management-type"),
    CONCURRENT_METHOD("concurrent-method"),
    CONTAINER_TRANSACTION("container-transaction"),
    CREATE_METHOD("create-method"),

    DAY_OF_MONTH("day-of-month"),
    DAY_OF_WEEK("day-of-week"),
    DEPENDS_ON("depends-on"),
    DESCRIPTION("description"),
    DESTINATION_TYPE("destination-type"),


    EJB_CLASS("ejb-class"),
    EJB_CLIENT_JAR("ejb-client-jar"),
    EJB_NAME("ejb-name"),
    EJB_QL("ejb-ql"),
    EJB_RELATION("ejb-relation"),
    EJB_RELATION_NAME("ejb-relation-name"),
    EJB_RELATIONSHIP_ROLE("ejb-relationship-role"),
    EJB_RELATIONSHIP_ROLE_NAME("ejb-relationship-role-name"),
    END("end"),
    ENTERPRISE_BEANS("enterprise-beans"),
    ENTITY("entity"),
    EXCEPTION_CLASS("exception-class"),
    EXCLUDE_DEFAULT_INTERCEPTORS("exclude-default-interceptors"),
    EXCLUDE_CLASS_INTERCEPTORS("exclude-class-interceptors"),
    EXCLUDE_LIST("exclude-list"),

    FIELD_NAME("field-name"),

    HOME("home"),
    HOUR("hour"),

    INFO("info"),
    INHERITED("inherited"),
    INIT_METHOD("init-method"),
    INIT_ON_STARTUP("init-on-startup"),
    INTERCEPTOR("interceptor"),
    INTERCEPTORS("interceptors"),
    INTERCEPTOR_BINDING("interceptor-binding"),
    INTERCEPTOR_CLASS("interceptor-class"),
    INTERCEPTOR_ORDER("interceptor-order"),


    LOCAL("local"),
    LOCAL_BEAN("local-bean"),
    LOCAL_HOME("local-home"),
    LOCK("lock"),

    MAPPED_NAME("mapped-name"),
    MESSAGE_DESTINATION("message-destination"),
    MESSAGE_DESTINATION_LINK("message-destination-link"),
    MESSAGE_DESTINATION_TYPE("message-destination-type"),
    MESSAGE_DRIVEN("message-driven"),
    MESSAGE_DRIVEN_DESTINATION("message-driven-destination"),
    MESSAGE_SELECTOR("message-selector"),
    MESSAGING_TYPE("messaging-type"),
    METHOD("method"),
    METHOD_INTF("method-intf"),
    METHOD_NAME("method-name"),
    METHOD_PARAM("method-param"),
    METHOD_PARAMS("method-params"),
    METHOD_PERMISSION("method-permission"),
    MINUTE("minute"),
    MODULE_NAME("module-name"),
    MONTH("month"),
    MULTIPLICITY("multiplicity"),

    PASSIVATION_CAPABLE("passivation-capable"),
    PERSISTENCE_TYPE("persistence-type"),
    PERSISTENT("persistent"),
    PRIM_KEY_CLASS("prim-key-class"),
    PRIMKEY_FIELD("primkey-field"),
    POST_ACTIVATE("post-activate"),
    PRE_PASSIVATE("pre-passivate"),

    QUERY("query"),
    QUERY_METHOD("query-method"),

    REENTRANT("reentrant"),
    RELATIONSHIPS("relationships"),
    RELATIONSHIP_ROLE_SOURCE("relationship-role-source"),
    REMOTE("remote"),
    REMOVE_METHOD("remove-method"),
    RESULT_TYPE_MAPPING("result-type-mapping"),
    RETAIN_IF_EXCEPTION("retain-if-exception"),
    ROLE_NAME("role-name"),
    ROLLBACK("rollback"),
    RUN_AS("run-as"),

    SCHEDULE("schedule"),
    SECOND("second"),
    SECURITY_IDENTITY("security-identity"),
    SECURITY_ROLE("security-role"),
    SECURITY_ROLE_REF("security-role-ref"),
    SERVICE_ENDPOINT("service-endpoint"),
    SESSION("session"),
    SESSION_TYPE("session-type"),
    START("start"),
    STATEFUL_TIMEOUT("stateful-timeout"),
    SUBSCRIPTION_DURABILITY("subscription-durability"),

    TIMEOUT("timeout"),
    TIMEOUT_METHOD("timeout-method"),
    TIMER("timer"),
    TIMEZONE("timezone"),
    TRANS_ATTRIBUTE("trans-attribute"),
    TRANSACTION_TYPE("transaction-type"),

    UNCHECKED("unchecked"),
    UNIT("unit"),
    USE_CALLER_IDENTITY("use-caller-identity"),

    YEAR("year"),;

    /**
     * Name of the element
     */
    private final String elementName;

    /**
     * Elements map
     */
    private static final Map<String, EjbJarElement> ELEMENT_MAP;

    static {
        final Map<String, EjbJarElement> map = new HashMap<String, EjbJarElement>();
        for (EjbJarElement element : values()) {
            final String name = element.getLocalName();
            if (name != null) {
                map.put(name, element);
            }
        }
        ELEMENT_MAP = map;
    }


    /**
     * @param name
     */
    EjbJarElement(final String name) {
        this.elementName = name;
    }

    /**
     * Get the local name of this element.
     *
     * @return the local name
     */
    public String getLocalName() {
        return this.elementName;
    }

    /**
     * Returns the {@link EjbJarElement} corresponding to the passed <code>elementName</code>
     * <p/>
     * If no such element exists then {@link EjbJarElement#UNKNOWN} is returned.
     *
     * @param elementName
     * @return
     */
    public static EjbJarElement forName(String elementName) {
        final EjbJarElement element = ELEMENT_MAP.get(elementName);
        return element == null ? UNKNOWN : element;
    }
}
