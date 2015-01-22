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
 * GitHubCommit contains all GitHub-specific commit info and wraps the regular Git commit info.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubCommit {

    @JsonProperty
    private String sha;
    @JsonProperty("html_url")
    private String htmlUrl;
    @JsonProperty
    private CommitInfo commit;
    @JsonProperty
    private Owner author;
    @JsonProperty
    private Owner committer;

    /**
     * @return the SHA hash
     */
    public String getSha() {
        return this.sha;
    }

    /**
     * @param sha the SHA hash to set
     */
    public void setSha(final String sha) {
        this.sha = sha;
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
     * @return the commit
     */
    public CommitInfo getCommit() {
        return this.commit;
    }

    /**
     * @param commit the commit to set
     */
    public void setCommit(final CommitInfo commit) {
        this.commit = commit;
    }

    /**
     * @return the author
     */
    public Owner getAuthor() {
        return this.author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(final Owner author) {
        this.author = author;
    }

    /**
     * @return the committer
     */
    public Owner getCommitter() {
        return this.committer;
    }

    /**
     * @param committer the committer to set
     */
    public void setCommitter(final Owner committer) {
        this.committer = committer;
    }
}
