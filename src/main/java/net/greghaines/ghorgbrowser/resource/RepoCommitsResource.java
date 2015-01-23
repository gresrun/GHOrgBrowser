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
package net.greghaines.ghorgbrowser.resource;

import io.dropwizard.jersey.params.IntParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.greghaines.ghorgbrowser.model.GitHubCommit;
import net.greghaines.ghorgbrowser.model.PaginatedResponse;
import net.greghaines.ghorgbrowser.service.GitHubException;
import net.greghaines.ghorgbrowser.service.GitHubService;
import net.greghaines.ghorgbrowser.view.RepoCommitsView;

import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;

/**
 * RepoCommitsResource serves the list of commits for a repository.
 */
@Path("/repos/{orgName}/{repoName}/commits")
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.TEXT_HTML)
public class RepoCommitsResource {

    private final GitHubService gitHubService;

    /**
     * Constructor.
     * @param gitHubService the GitHub service
     */
    public RepoCommitsResource(final GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    /**
     * Show the RepoCommitsView.
     * @param orgName the organization name
     * @param repoName the repository name
     * @param page the page offset
     * @param perPage the commits per page
     * @return the view
     * @throws GitHubException if there was a problem communicating with GitHub
     */
    @GET
    @Timed(name = "get.timer")
    @Metered(name = "get.meter")
    public RepoCommitsView getRepoCommitsView(@PathParam("orgName") final String orgName, 
            @PathParam("repoName") final String repoName, 
            @QueryParam("page") @DefaultValue("1") final IntParam page,
            @QueryParam("perPage") @DefaultValue("30") final IntParam perPage) throws GitHubException {
        final PaginatedResponse<GitHubCommit> commits = 
                this.gitHubService.listRepoCommits(orgName, repoName, page.get(), perPage.get());
        return new RepoCommitsView(orgName, repoName, commits);
    }
}
