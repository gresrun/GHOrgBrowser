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
package net.greghaines.ghorgbrowser.service;

import net.greghaines.ghorgbrowser.model.GitHubCommit;
import net.greghaines.ghorgbrowser.model.PaginatedResponse;
import net.greghaines.ghorgbrowser.model.Repository;

/**
 * GitHubService communicates with GitHub via their REST API.
 */
public interface GitHubService {

    /**
     * GitHub defaults to 30 elements per page.
     */
    int DEFAULT_ELEMENTS_PER_PAGE = 30;

    /**
     * List repositories for the given organization.
     * @param orgName the organization name
     * @param page the page to view
     * @param perPage the number of results per page (up to 100)
     * @return response with the list of repositories
     * @throws GitHubException if there was a problem communicating with GitHub
     */
    PaginatedResponse<Repository> listOrganizationRepos(String orgName, int page, int perPage) throws GitHubException;

    /**
     * List commits for the given repository.
     * @param ownerName the repository owner name
     * @param repoName the repository name
     * @param page the page to view
     * @param perPage the number of results per page (up to 100)
     * @return the response with the list of commits
     * @throws GitHubException if there was a problem communicating with GitHub
     */
    PaginatedResponse<GitHubCommit> listRepoCommits(String ownerName, String repoName, int page, 
            int perPage) throws GitHubException;
}
