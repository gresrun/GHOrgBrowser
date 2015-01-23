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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Organization represents a GitHub owner.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Owner {

    @JsonProperty
    private String login;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty("avatar_url")
    private String avatarUrl;

    /**
     * @return the owner login
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * @param login the owner login to set
     */
    public void setLogin(final String login) {
        this.login = login;
    }

    /**
     * @return the HTML URL
     */
    public String getHtmlUrl() {
        return this.htmlUrl;
    }

    /**
     * @param htmlUrl the HTML URL to set
     */
    public void setHtmlUrl(final String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    /**
     * @return the avatar URL
     */
    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    /**
     * @param avatarUrl the avatar URL to set
     */
    public void setAvatarUrl(final String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Owner [login=" + this.login + ", htmlUrl=" + this.htmlUrl + ", avatarUrl=" + this.avatarUrl + "]";
    }
}
