package org.jboss.metadata.web.spec;

/**
 * Web application spec metadata.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 81860 $
 */
public class Web25MetaData extends WebMetaData {
    private static final long serialVersionUID = 1;
    private boolean metadataComplete;

    public boolean isMetadataComplete() {
        return metadataComplete;
    }

    public void setMetadataComplete(boolean metadataComplete) {
        this.metadataComplete = metadataComplete;
    }

}
