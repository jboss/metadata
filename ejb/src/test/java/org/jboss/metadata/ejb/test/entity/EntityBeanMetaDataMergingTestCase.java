package org.jboss.metadata.ejb.test.entity;

import org.jboss.metadata.ejb.spec.EntityBeanMetaData;
import org.junit.Test;

public class EntityBeanMetaDataMergingTestCase {

    @Test
    public void testMerging() {
        EntityBeanMetaData ebMetaData1 = new EntityBeanMetaData();
        ebMetaData1.setName("Entity Bean 1");
        EntityBeanMetaData ebMetaData2 = new EntityBeanMetaData();
        ebMetaData2.setName("Entity Bean 2");
        ebMetaData1.merge(ebMetaData1, ebMetaData2);
    }
}
