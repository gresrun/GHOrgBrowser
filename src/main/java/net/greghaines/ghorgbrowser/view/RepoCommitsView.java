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

import java.util.List;

import net.greghaines.ghorgbrowser.model.GitHubCommit;
import net.greghaines.ghorgbrowser.model.PaginatedResponse;

/**
 * RepoCommitsView shows a list of commits for a repository.
 */
public class RepoCommitsView extends AbstractView {

    private final String orgName;
    private final String repoName;
    private final PaginatedResponse<GitHubCommit> response;

    /**
     * Constructor.
     * @param orgName the organization name
     * @param repoName the repository name
     * @param response the paginated response
     */
    public RepoCommitsView(final String orgName, final String repoName, 
            final PaginatedResponse<GitHubCommit> response) {
        super("repoCommits");
        this.orgName = orgName;
        this.repoName = repoName;
        this.response = response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return this.orgName + "/" + this.repoName + " Commits";
    }

    /**
     * @return the organization name
     */
    public String getOrgName() {
        return this.orgName;
    }

    /**
     * @return the repository name
     */
    public String getRepoName() {
        return this.repoName;
    }

    /**
     * @return the commits
     */
    public List<GitHubCommit> getCommits() {
        return this.response.getElements();
    }

    /**
     * @return true if this is the first page
     */
    public boolean isFirstPage() {
        return (this.response.getCurrentPage() == 1);
    }

    /**
     * @return the previous page number
     */
    public int getPrevPage() {
        return (this.response.getCurrentPage() - 1);
    }

    /**
     * @return the next page number
     */
    public int getNextPage() {
        return (this.response.getCurrentPage() + 1);
    }

    /**
     * @return the number of elements per page
     */
    public int getPerPage() {
        return this.response.getElementsPerPage();
    }
}
