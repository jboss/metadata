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

import org.jboss.metadata.ejb.spec.NamedMethodMetaData;
import org.jboss.metadata.ejb.spec.ScheduleMetaData;
import org.jboss.metadata.ejb.spec.TimerMetaData;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.parser.ee.Accessor;
import org.jboss.metadata.parser.ee.DescriptionGroupMetaDataParser;
import org.jboss.metadata.property.PropertyReplacer;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.time.temporal.ChronoField.OFFSET_SECONDS;

/**
 * Parses and creates metadata out of &lt;timer&gt; element in ejb-jar.xml
 * <p/>
 * <p/>
 * Author: Jaikiran Pai
 */
public class TimerMetaDataParser extends AbstractMetaDataParser<TimerMetaData> {
    public static final TimerMetaDataParser INSTANCE = new TimerMetaDataParser();

    /**
     * Creates and returns {@link TimerMetaData}
     *
     * @param reader
     * @return
     * @throws XMLStreamException
     */
    @Override
    public TimerMetaData parse(XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        TimerMetaData timerMetaData = new TimerMetaData();
        this.processElements(timerMetaData, reader, propertyReplacer);
        return timerMetaData;
    }

    private static Calendar parseDateTime(String dateTime) {
        final TemporalAccessor ta = DateTimeFormatter.ISO_DATE_TIME.parse(dateTime);
        final LocalDateTime localDateTime = LocalDateTime.from(ta);
        final ZoneId zoneId;
        if (ta.isSupported(OFFSET_SECONDS)) {
            zoneId = ZoneId.from(ta);
        } else {
            zoneId = ZoneId.systemDefault();
        }
        return GregorianCalendar.from(ZonedDateTime.of(localDateTime, zoneId));
    }

    /**
     * Parses the child elements of the &lt;timer&gt; element and updates the passed {@link TimerMetaData} accordingly.
     *
     * @param timer  The metadata to update while parsing
     * @param reader
     * @throws XMLStreamException
     */
    @Override
    protected void processElement(final TimerMetaData timer, XMLStreamReader reader, PropertyReplacer propertyReplacer) throws XMLStreamException {
        Accessor<DescriptionGroupMetaData> accessor = new Accessor<DescriptionGroupMetaData>() {
            @Override
            public DescriptionGroupMetaData get() {
                DescriptionGroupMetaData descriptionGroupMetaData = timer.getDescriptionGroup();
                if (descriptionGroupMetaData == null) {
                    descriptionGroupMetaData = new DescriptionGroupMetaData();
                    timer.setDescriptionGroup(descriptionGroupMetaData);
                }
                return descriptionGroupMetaData;
            }
        };
        if (DescriptionGroupMetaDataParser.parse(reader, accessor))
            return;

        final EjbJarElement ejbJarElement = EjbJarElement.forName(reader.getLocalName());
        switch (ejbJarElement) {
            case TIMEZONE:
                String timezone = getElementText(reader, propertyReplacer);
                timer.setTimezone(timezone);
                return;

            case INFO:
                String info = getElementText(reader, propertyReplacer);
                timer.setInfo(info);
                return;

            case PERSISTENT:
                String persistent = getElementText(reader, propertyReplacer);
                if (persistent == null || persistent.trim().isEmpty()) {
                    throw unexpectedValue(reader, new Exception("Unexpected null or empty value for <persistent> element"));
                }
                timer.setPersistent(Boolean.parseBoolean(persistent));
                return;

            case TIMEOUT_METHOD:
                NamedMethodMetaData timeoutMethod = NamedMethodMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                timer.setTimeoutMethod(timeoutMethod);
                return;

            case START:
                String start = getElementText(reader, propertyReplacer);
                timer.setStart(parseDateTime(start));
                return;
            case END:
                String end = getElementText(reader, propertyReplacer);
                timer.setEnd(parseDateTime(end));
                return;
            case SCHEDULE:
                ScheduleMetaData schedule = ScheduleMetaDataParser.INSTANCE.parse(reader, propertyReplacer);
                timer.setSchedule(schedule);
                return;

            default:
                throw unexpectedElement(reader);

        }
    }
}
