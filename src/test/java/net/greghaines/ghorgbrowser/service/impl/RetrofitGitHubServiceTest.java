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
package net.greghaines.ghorgbrowser.service.impl;

import static org.junit.Assert.*;
import static net.greghaines.ghorgbrowser.service.GitHubService.DEFAULT_ELEMENTS_PER_PAGE;

import java.util.List;

import net.greghaines.ghorgbrowser.model.CommitInfo;
import net.greghaines.ghorgbrowser.model.GitHubCommit;
import net.greghaines.ghorgbrowser.model.Owner;
import net.greghaines.ghorgbrowser.model.PaginatedResponse;
import net.greghaines.ghorgbrowser.model.Repository;
import net.greghaines.ghorgbrowser.service.GitHubException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit.RestAdapter;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * RetrofitGitHubServiceTest tests RetrofitGitHubService.
 */
public class RetrofitGitHubServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(RetrofitGitHubServiceTest.class);
    private static final String ORG_NAME = "Netflix";
    private static final int PAGE = 1;

    private RetrofitGitHubService service;

    @Before
    public void setUp() {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .build();
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        this.service = new RetrofitGitHubService(restAdapter, objectMapper, new MetricRegistry());
    }

    @Test
    public void testListOrganizationRepos_Success() throws GitHubException {
        final PaginatedResponse<Repository> pagedRepos = 
                this.service.listOrganizationRepos(ORG_NAME, PAGE, DEFAULT_ELEMENTS_PER_PAGE);
        assertNotNull(pagedRepos);
        assertEquals((Integer) PAGE, pagedRepos.getCurrentPage());
        assertNotNull(pagedRepos.getLastPage());
        LOG.info("Last Page={}", pagedRepos.getLastPage());
        final List<Repository> repos = pagedRepos.getElements();
        assertNotNull(repos);
        assertFalse(repos.isEmpty());
        for (final Repository repo : repos) {
            assertNotNull(repo);
            LOG.info("{}", repo);
            final Owner owner = repo.getOwner();
            assertNotNull(owner);
            assertEquals(ORG_NAME, owner.getLogin());
        }
    }

    @Test(expected = GitHubException.class)
    public void testListOrganizationRepos_BadOrg() throws GitHubException {
        this.service.listOrganizationRepos("Netflix111122223333", PAGE, DEFAULT_ELEMENTS_PER_PAGE);
    }

    @Test
    public void testRepoCommits_Success() throws GitHubException {
        final String repoName = "asgard";
        final PaginatedResponse<GitHubCommit> pagedCommits = 
                this.service.listRepoCommits(ORG_NAME, repoName, PAGE, DEFAULT_ELEMENTS_PER_PAGE);
        assertNotNull(pagedCommits);
        assertEquals((Integer) PAGE, pagedCommits.getCurrentPage());
        assertNull(pagedCommits.getLastPage());
        final List<GitHubCommit> ghCommits = pagedCommits.getElements();
        assertNotNull(ghCommits);
        assertFalse(ghCommits.isEmpty());
        for (final GitHubCommit ghCommit : ghCommits) {
            assertNotNull(ghCommit);
            assertNotNull(ghCommit.getAuthor());
            final CommitInfo commitInfo = ghCommit.getCommit();
            assertNotNull(commitInfo);
            assertNotNull(commitInfo.getAuthor());
        }
    }

    @Test(expected = GitHubException.class)
    public void testRepoCommits_BadRepo() throws GitHubException {
        this.service.listRepoCommits(ORG_NAME, "omgwtfbbq111", PAGE, DEFAULT_ELEMENTS_PER_PAGE);
    }
}
