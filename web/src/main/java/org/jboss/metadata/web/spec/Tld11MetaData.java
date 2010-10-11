package org.jboss.metadata.web.spec;

import java.util.List;

/**
 * TLD spec metadata.
 *
 * @author Remy Maucherat
 * @version $Revision: 81860 $
 */
public class Tld11MetaData extends TldMetaData {
    private static final long serialVersionUID = 1;

    private String info;

    @Override
    public String getVersion() {
        return "1.1";
    }

    public void setTlibversion(String tlibVersion) {
        super.setTlibVersion(tlibVersion);
    }

    public void setJspversion(String jspVersion) {
        super.setJspVersion(jspVersion);
    }

    public void setShortname(String shortName) {
        super.setShortName(shortName);
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setTags(List<TagMetaData> tags) {
        super.setTags(tags);
    }

}
