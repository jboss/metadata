package org.jboss.metadata.appclient.spec;

import org.jboss.metadata.javaee.spec.MessageDestinationsMetaData;
import org.jboss.metadata.javaee.spec.RemoteEnvironmentRefsGroupMetaData;

/**
 * A EarEnvironmentRefsGroupMetaData.
 *
 * @author <a href="alex@jboss.com">Alexey Loubyansky</a>
 * @version $Revision: 1.1 $
 */
public class AppClientEnvironmentRefsGroupMetaData extends RemoteEnvironmentRefsGroupMetaData {
    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 8714123546582134095L;

    private MessageDestinationsMetaData messageDestinations;

    private String callbackHandler;

    public MessageDestinationsMetaData getMessageDestinations() {
        return messageDestinations;
    }

    public void setMessageDestinations(MessageDestinationsMetaData messageDestinations) {
        this.messageDestinations = messageDestinations;
    }

    public String getCallbackHandler() {
        return callbackHandler;
    }

    public void setCallbackHandler(final String callbackHandler) {
        this.callbackHandler = callbackHandler;
    }
}
