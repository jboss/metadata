package org.jboss.metadata.web.spec;

import java.util.List;

/**
 * Web application spec metadata.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision: 84989 $
 */
public class Web22MetaData extends WebMetaData {
    private static final long serialVersionUID = 1;

    public boolean isMetadataComplete() {
        return true;
    }

    @Override
    public String getVersion() {
        return "2.2";
    }

    public List<TaglibMetaData> getTaglibs() {
        JspConfigMetaData jspConfig = super.getJspConfig();
        List<TaglibMetaData> taglibs = null;
        if (jspConfig != null) {
            taglibs = jspConfig.getTaglibs();
        }
        return taglibs;
    }

    /**
     * Map the 2.2 taglibs onto jsp-config/taglibs
     *
     * @param taglibs
     */
    public void setTaglibs(List<TaglibMetaData> taglibs) {
        JspConfigMetaData jspConfig = new JspConfigMetaData();
        jspConfig.setTaglibs(taglibs);
        super.setJspConfig(jspConfig);
    }
}
