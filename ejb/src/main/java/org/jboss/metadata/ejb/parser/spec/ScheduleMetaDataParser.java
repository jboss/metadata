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

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.jboss.metadata.ejb.spec.ScheduleMetaData;
import org.jboss.metadata.property.PropertyReplacer;

/**
 * Parses and creates metadata out of &lt;schedule&gt; element of ejb-jar.xml
 * <p/>
 * Author: Jaikiran Pai
 */
public class ScheduleMetaDataParser extends AbstractMetaDataParser<ScheduleMetaData> {
    public static final ScheduleMetaDataParser INSTANCE = new ScheduleMetaDataParser();

    /**
     * Creates and returns {@link ScheduleMetaData}
     * <p/>
     *
     * @param reader
     * @return
     * @throws XMLStreamException
     */
    @Override
    public ScheduleMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        ScheduleMetaData schedule = new ScheduleMetaData();
        this.processElements(schedule, reader, propertyReplacer);
        return schedule;

    }

    /**
     * Parses the child elements of &lt;schedule&gt; element and updates the passed {@link ScheduleMetaData} accordingly.
     *
     * @param schedule The metadata to update during parsing the elements.
     * @param reader
     * @throws XMLStreamException
     */
    @Override
    protected void processElement(ScheduleMetaData schedule, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case SECOND:
                String second = getElementText(reader, propertyReplacer);
                schedule.setSecond(second);
                return;

            case MINUTE:
                String minute = getElementText(reader, propertyReplacer);
                schedule.setMinute(minute);
                return;

            case HOUR:
                String hour = getElementText(reader, propertyReplacer);
                schedule.setHour(hour);
                return;

            case DAY_OF_MONTH:
                String dayOfMonth = getElementText(reader, propertyReplacer);
                schedule.setDayOfMonth(dayOfMonth);
                return;

            case MONTH:
                String month = getElementText(reader, propertyReplacer);
                schedule.setMonth(month);
                return;

            case YEAR:
                String year = getElementText(reader, propertyReplacer);
                schedule.setYear(year);
                return;

            case DAY_OF_WEEK:
                String dayOfWeek = getElementText(reader, propertyReplacer);
                schedule.setDayOfWeek(dayOfWeek);
                return;

            default:
                throw unexpectedElement(reader);

        }
    }
}
