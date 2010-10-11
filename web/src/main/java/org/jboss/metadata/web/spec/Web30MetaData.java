package org.jboss.metadata.web.spec;

import org.jboss.metadata.javaee.jboss.NamedModule;

/**
 * Web application spec metadata.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 81860 $
 */
public class Web30MetaData extends WebMetaData implements NamedModule {
    private static final long serialVersionUID = 1;
    private boolean metadataComplete;
    private AbsoluteOrderingMetaData absoluteOrdering;
    private String moduleName;

    public boolean isMetadataComplete() {
        return metadataComplete;
    }

    public void setMetadataComplete(boolean metadataComplete) {
        this.metadataComplete = metadataComplete;
    }

    public AbsoluteOrderingMetaData getAbsoluteOrdering() {
        return absoluteOrdering;
    }

    public void setAbsoluteOrdering(AbsoluteOrderingMetaData absoluteOrdering) {
        this.absoluteOrdering = absoluteOrdering;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

}
