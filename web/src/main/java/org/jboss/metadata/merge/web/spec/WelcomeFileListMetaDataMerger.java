/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.metadata.merge.web.spec;

import java.util.ArrayList;
import java.util.List;

import org.jboss.metadata.merge.javaee.support.IdMetaDataImplMerger;
import org.jboss.metadata.web.spec.WelcomeFileListMetaData;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 75470 $
 */
public class WelcomeFileListMetaDataMerger extends IdMetaDataImplMerger {
    public static void augment(WelcomeFileListMetaData dest, WelcomeFileListMetaData webFragmentMetaData, WelcomeFileListMetaData webMetaData,
                               boolean resolveConflicts) {
        // Note: as this is purely additive, webMetaData is useless
        if (dest.getWelcomeFiles() == null) {
            dest.setWelcomeFiles(webFragmentMetaData.getWelcomeFiles());
        } else if (webFragmentMetaData.getWelcomeFiles() != null) {
            List<String> mergedWelcomeFiles = new ArrayList<String>();
            for (String welcomeFile : dest.getWelcomeFiles()) {
                mergedWelcomeFiles.add(welcomeFile);
            }
            for (String welcomeFile : webFragmentMetaData.getWelcomeFiles()) {
                boolean found = false;
                for (String check : dest.getWelcomeFiles()) {
                    if (check.equals(welcomeFile)) {
                        found = true;
                    }
                }
                if (!found)
                    mergedWelcomeFiles.add(welcomeFile);
            }
            dest.setWelcomeFiles(mergedWelcomeFiles);
        }
    }
}
