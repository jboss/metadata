package org.jboss.metadata.ejb.test.entity;

import org.jboss.metadata.ejb.spec.EjbType;
import org.jboss.metadata.ejb.spec.GenericBeanMetaData;
import org.junit.Test;

public class EntityBeanMetaDataMergingTestCase {

    @Test
    public void testMerging() {
        GenericBeanMetaData ebMetaData1 = new GenericBeanMetaData(EjbType.ENTITY);
        ebMetaData1.setName("Entity Bean 1");
        GenericBeanMetaData ebMetaData2 = new GenericBeanMetaData(EjbType.ENTITY);
        ebMetaData2.setName("Entity Bean 2");
        ebMetaData1.merge(ebMetaData1, ebMetaData2);
    }
}
