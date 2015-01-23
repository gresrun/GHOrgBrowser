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
 * CommitInfo contains information about a Git commit.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitInfo {

    @JsonProperty
    private String message;
    @JsonProperty
    private GitCommitter author;
    @JsonProperty
    private GitCommitter committer;
    @JsonProperty("comment_count")
    private Integer commentCount;

    public CommitInfo(){}

    /**
     * @return the message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * @return the author
     */
    public GitCommitter getAuthor() {
        return this.author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(final GitCommitter author) {
        this.author = author;
    }

    /**
     * @return the committer
     */
    public GitCommitter getCommitter() {
        return this.committer;
    }

    /**
     * @param committer the committer to set
     */
    public void setCommitter(final GitCommitter committer) {
        this.committer = committer;
    }

    /**
     * @return the comment count
     */
    public Integer getCommentCount() {
        return this.commentCount;
    }

    /**
     * @param commentCount the comment count to set
     */
    public void setCommentCount(final Integer commentCount) {
        this.commentCount = commentCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CommitInfo [message=" + this.message + ", author=" + this.author + ", committer=" + this.committer
                + ", commentCount=" + this.commentCount + "]";
    }
}
