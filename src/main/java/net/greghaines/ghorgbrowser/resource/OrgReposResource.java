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

import java.util.Collections;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.greghaines.ghorgbrowser.model.PaginatedResponse;
import net.greghaines.ghorgbrowser.model.Repository;
import net.greghaines.ghorgbrowser.service.GitHubException;
import net.greghaines.ghorgbrowser.service.GitHubService;
import net.greghaines.ghorgbrowser.utils.ReflectivePropertyComparator;
import net.greghaines.ghorgbrowser.utils.ReflectivePropertyComparator.SortDirection;
import net.greghaines.ghorgbrowser.view.OrgReposView;

import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;

/**
 * OrgReposResource serves the list of organization repositories.
 */
@Path("/org/{orgName}/repos")
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.TEXT_HTML)
public class OrgReposResource {

    private final GitHubService gitHubService;

    public OrgReposResource(final GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GET
    @Timed(name = "get.timer")
    @Metered(name = "get.meter")
    public OrgReposView getOrgReposView(@PathParam("orgName") final String orgName, 
            @QueryParam("sortBy") final String sortBy) throws GitHubException {
        final PaginatedResponse<Repository> repoResp = this.gitHubService.listOrganizationRepos(orgName, 0, 100);
        Collections.sort(repoResp.getElements(), new ReflectivePropertyComparator(sortBy, SortDirection.DESC));
        return new OrgReposView(orgName, repoResp.getElements().isEmpty() ? null : repoResp.getElements());
    }
}
