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
 * Repository represents a GitHub repository.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {

    @JsonProperty
    private Owner owner;
    @JsonProperty("name")
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty
    private String description;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty("forks_count")
    private Integer forksCount;
    @JsonProperty("stargazers_count")
    private Integer stargazersCount;
    @JsonProperty("watchers_count")
    private Integer watchersCount;
    @JsonProperty
    private Integer size;

    /**
     * @return the owner
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(final Owner owner) {
        this.owner = owner;
    }

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
     * @return the full name
     */
    public String getFullName() {
        return this.fullName;
    }

    /**
     * @param fullName the full name to set
     */
    public void setFullName(final String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
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
     * @return the forks count
     */
    public Integer getForksCount() {
        return this.forksCount;
    }

    /**
     * @param forksCount the forks count to set
     */
    public void setForksCount(final Integer forksCount) {
        this.forksCount = forksCount;
    }

    /**
     * @return the stargazers count
     */
    public Integer getStargazersCount() {
        return this.stargazersCount;
    }

    /**
     * @param stargazersCount the stargazers count to set
     */
    public void setStargazersCount(final Integer stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    /**
     * @return the watchers count
     */
    public Integer getWatchersCount() {
        return this.watchersCount;
    }

    /**
     * @param watchersCount the watchers count to set
     */
    public void setWatchersCount(final Integer watchersCount) {
        this.watchersCount = watchersCount;
    }

    /**
     * @return the size
     */
    public Integer getSize() {
        return this.size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(final Integer size) {
        this.size = size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Repository [owner=" + this.owner + ", name=" + this.name + ", fullName=" + this.fullName 
                + ", description=" + this.description + ", htmlUrl=" + this.htmlUrl + ", forksCount=" 
                + this.forksCount + ", stargazersCount=" + this.stargazersCount + ", watchersCount=" 
                + this.watchersCount + ", size=" + this.size + "]";
    }
}
