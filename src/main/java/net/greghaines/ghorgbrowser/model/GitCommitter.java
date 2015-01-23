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
package net.greghaines.ghorgbrowser.model;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * GitCommitter is either the author or committer of a Git commit.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitCommitter {

    @JsonProperty
    private String name;
    @JsonProperty
    private String email;
    @JsonProperty
    private DateTime date;

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return the date
     */
    public DateTime getDate() {
        return this.date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(final DateTime date) {
        this.date = date;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "GitCommitter [name=" + this.name + ", email=" + this.email + ", date=" + this.date + "]";
    }
}
