/*
 * Copyright The JBoss Metadata Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.metadata.parser.jbossweb;

import java.util.HashMap;
import java.util.Map;

/**
 * An enumeration of all the possible XML elements in the jboss-web schema, by name.
 *
 * @author Remy Maucherat
 */
public enum Element {
    // must be first
    UNKNOWN(null),

    // jboss-web elements in alpha order
    ANNOTATION("annotation"),

    CACHE_NAME("cache-name"),
    CLASS_LOADING("class-loading"),
    CLASS_NAME("class-name"),
    CONFIG_FILE("config-file"),
    CONFIG_NAME("config-name"),
    CONTEXT_ROOT("context-root"),

    DEPENDS("depends"),
    DENY_UNCOVERED_HTTP_METHODS("deny-uncovered-http-methods"),
    DISABLE_AUDIT("disable-audit"),
    DISABLE_CROSS_CONTEXT("disable-cross-context"),
    DISTINCT_NAME("distinct-name"),

    EMPTY_ROLE_SEMANTIC("empty-role-semantic"),
    ENABLE_WEBSOCKETS("enable-websockets"),
    EXECUTOR_NAME("executor-name"),

    HTTP_METHOD_CONSTRAINT("http-method-constraint"),
    HTTP_HANDLER("http-handler"),

    JACC_STAR_ROLE_ALLOW("jacc-star-role-allow"),
    JACC_CONTEXT_ID("jacc-context-id"),

    LISTENER("listener"),
    LISTENER_TYPE("listener-type"),
    LOADER_REPOSITORY("loader-repository"),
    LOADER_REPOSITORY_CONFIG("loader-repository-config"),

    MAX_ACTIVE_SESSIONS("max-active-sessions"),
    MESSAGE_DESTINATION("message-destination"),
    METHOD("method"),
    MODULE("module"),
    MULTIPART_CONFIG("multipart-config"),

    OVERLAY("overlay"),

    PARAM("param"),
    PROACTIVE_AUTHENTICATION("proactive-authentication"),

    REPLICATION_CONFIG("replication-config"),
    REPLICATION_GRANULARITY("replication-granularity"),
    ROLE_ALLOWED("role-allowed"),
    RUN_AS("run-as"),
    RUN_AS_PRINCIPAL("run-as-principal"),

    SECURITY_DOMAIN("security-domain"),
    SECURITY_ROLE("security-role"),
    SERVLET("servlet"),
    SERVLET_NAME("servlet-name"),
    SERVLET_SECURITY("servlet-security"),
    SERVLET_CONTAINER("servlet-container"),
    SERVER_INSTANCE("server-instance"),
    DEFAULT_ENCODING("default-encoding"),
    SESSION_CONFIG("session-config"),
    SYMBOLIC_ENABLED("symbolic-linking-enabled"),

    TRANSPORT_GUARANTEE("transport-guarantee"),

    USE_JBOSS_AUTHORIZATION("use-jboss-authorization"),

    VALVE("valve"),
    VIRTUAL_HOST("virtual-host"),

    WEBSERVICE_DESCRIPTION("webservice-description"),
    WEBSERVICE_DESCRIPTION_NAME("webservice-description-name"),
    WSDL_PUBLISH_LOCATION("wsdl-publish-location"),;

    private final String name;

    Element(final String name) {
        this.name = name;
    }

    /**
     * Get the local name of this element.
     *
     * @return the local name
     */
    public String getLocalName() {
        return name;
    }

    private static final Map<String, Element> MAP;

    static {
        final Map<String, Element> map = new HashMap<String, Element>();
        for (Element element : values()) {
            final String name = element.getLocalName();
            if (name != null) map.put(name, element);
        }
        MAP = map;
    }

    public static Element forName(String localName) {
        final Element element = MAP.get(localName);
        return element == null ? UNKNOWN : element;
    }
}
