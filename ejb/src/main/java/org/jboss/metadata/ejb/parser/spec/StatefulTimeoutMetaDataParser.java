/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.metadata.ejb.parser.spec;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.StatefulTimeoutMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * Parses and creates metadata out of &lt;stateful-timeout&gt; element of ejb-jar.xml
 * <p/>
 *
 * @author Stuart Douglas
 */
public class StatefulTimeoutMetaDataParser extends AbstractMetaDataParser<StatefulTimeoutMetaData> {
    public static final StatefulTimeoutMetaDataParser INSTANCE = new StatefulTimeoutMetaDataParser();

    private static final Map<String, TimeUnit> TIME_UNITS;

    static {
        final Map<String, TimeUnit> units = new HashMap<String, TimeUnit>();
        units.put("Days", TimeUnit.DAYS);
        units.put("Hours", TimeUnit.HOURS);
        units.put("Minutes", TimeUnit.MINUTES);
        units.put("Seconds", TimeUnit.SECONDS);
        units.put("Milliseconds", TimeUnit.MILLISECONDS);
        units.put("Microseconds", TimeUnit.MICROSECONDS);
        units.put("Nanoseconds", TimeUnit.NANOSECONDS);
        TIME_UNITS = Collections.unmodifiableMap(units);
    }


    /**
     * Creates and returns {@link org.jboss.metadata.ejb.spec.StatefulTimeoutMetaData}
     * <p/>
     *
     * @param reader
     * @return
     * @throws javax.xml.stream.XMLStreamException
     *
     */
    @Override
    public StatefulTimeoutMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        StatefulTimeoutMetaData schedule = new StatefulTimeoutMetaData();
        this.processElements(schedule, reader, propertyReplacer);

        return schedule;

    }

    @Override
    protected void processElement(final StatefulTimeoutMetaData schedule, final XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case UNIT:
                processUnitElement(schedule, reader, propertyReplacer);
                break;
            case TIMEOUT:
                processValueElement(schedule, reader, propertyReplacer);
                break;
            default:
                super.processElement(schedule, reader, propertyReplacer);
        }
    }

    private void processValueElement(final StatefulTimeoutMetaData schedule, final XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        schedule.setTimeout(Long.valueOf(getElementText(reader, propertyReplacer)));
    }

    protected void processUnitElement(StatefulTimeoutMetaData metaData, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        TimeUnit unit = TIME_UNITS.get(getElementText(reader, propertyReplacer));
        if (unit != null) {
            metaData.setUnit(unit);
        } else {
            throw new RuntimeException("Cannot parse unit in <stateful-timeout> " + reader.getElementText());
        }
    }
}
