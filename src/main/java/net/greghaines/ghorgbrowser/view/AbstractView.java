/*
 * Copyright 2015 Greg Haines
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.greghaines.ghorgbrowser.view;

import io.dropwizard.views.View;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.google.common.base.Function;

/**
 * AbstractView implements common view functionality.
 */
public abstract class AbstractView extends View {
    
    private static final DateTimeFormatter ISO_FORMAT = ISODateTimeFormat.dateTime();
    private static final DateTimeFormatter HUMAN_FORMAT = DateTimeFormat.shortDateTime().withLocale(Locale.US);

    private final List<String> additionalJS;

    /**
     * Constructor.
     * @param user the logged in user or null
     * @param templateName the name of the template
     * @param additionalJS additional JavaScript imports
     */
    protected AbstractView(final String templateName, final String... additionalJS) {
        super(templateName + ".mustache", StandardCharsets.UTF_8);
        this.additionalJS = Arrays.asList(additionalJS);
    }

    /**
     * @return the view's title
     */
    public abstract String getTitle();
    
    /**
     * @return additional JavaScript imports
     */
    public List<String> getAdditionalJS() {
        return this.additionalJS;
    }

    /**
     * @return a function for formatting timestamps into an easier-to-read format 
     */
    public Function<String,String> dateFormat() {
        return new Function<String, String>() {
            /**
             * {@inheritDoc}
             */
            @Override
            public String apply(final String input) {
                String result = input;
                try {
                    result = ISO_FORMAT.parseDateTime(input).toString(HUMAN_FORMAT);
                } catch (IllegalArgumentException iae) {
                    result = input;
                }
                return result;
            }
        };
    }
}
