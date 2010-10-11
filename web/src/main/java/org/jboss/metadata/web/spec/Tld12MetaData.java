package org.jboss.metadata.web.spec;

import java.util.List;

/**
 * TLD spec metadata.
 *
 * @author Remy Maucherat
 * @version $Revision: 81860 $
 */
public class Tld12MetaData extends TldMetaData {
    private static final long serialVersionUID = 1;

    private String displayName;
    private String smallIcon;
    private String largeIcon;
    private String description;

    @Override
    public String getVersion() {
        return "1.2";
    }

    public void setTags(List<TagMetaData> tags) {
        super.setTags(tags);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSmallIcon() {
        return smallIcon;
    }

    public void setSmallIcon(String smallIcon) {
        this.smallIcon = smallIcon;
    }

    public String getLargeIcon() {
        return largeIcon;
    }

    public void setLargeIcon(String largeIcon) {
        this.largeIcon = largeIcon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
