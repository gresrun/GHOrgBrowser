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

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import net.greghaines.ghorgbrowser.model.GitHubCommit;
import net.greghaines.ghorgbrowser.model.PaginatedResponse;
import net.greghaines.ghorgbrowser.model.Repository;
import net.greghaines.ghorgbrowser.service.GitHubException;
import net.greghaines.ghorgbrowser.service.GitHubService;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * RetrofitGitHubService communicates with GitHub via their REST API using Retrofit.
 */
public class RetrofitGitHubService implements GitHubService {

    private static final TypeReference<List<Repository>> TYPE_REF_LIST_REPO = 
            new TypeReference<List<Repository>>(){};
    private static final TypeReference<List<GitHubCommit>> TYPE_REF_LIST_COMMIT = 
            new TypeReference<List<GitHubCommit>>(){};
    private static final Pattern COMMA_PATTERN = Pattern.compile(",");
    private static final Pattern SEMICOLON_PATTERN = Pattern.compile(";");
    private static final Pattern EQUAL_PATTERN = Pattern.compile("=");

    private interface RetrofitInterface {

        /**
         * List repositories for the given organization.
         * @param orgName the organization name
         * @param page the page to view
         * @param perPage the number of results per page (up to 100)
         * @return response with the list of repositories
         */
        @GET("/orgs/{orgName}/repos")
        Response listOrganizationRepos(@Path("orgName") String orgName, @Query("page") int page, 
                @Query("per_page") int perPage);
    
        /**
         * List commits for the given repository.
         * @param ownerName the repository owner name
         * @param repoName the repository name
         * @param page the page to view
         * @param perPage the number of results per page (up to 100)
         * @return the response with the list of commits
         */
        @GET("/repos/{ownerName}/{repoName}/commits")
        Response listRepoCommits(@Path("ownerName") String ownerName, @Path("repoName") String repoName,
                @Query("page") int page, @Query("per_page") int perPage);
    }

    private final ObjectMapper objectMapper;
    private final RetrofitInterface retrofitInterface;
    private final JavaType listRepoJavaType;
    private final JavaType listCommitJavaType;

    /**
     * Constructor.
     * @param restAdapter the Retrofit REST adapter
     * @param objectMapper the Jackson object mapper
     */
    public RetrofitGitHubService(final RestAdapter restAdapter, final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.retrofitInterface = restAdapter.create(RetrofitInterface.class);
        this.listRepoJavaType = this.objectMapper.getTypeFactory().constructType(TYPE_REF_LIST_REPO);
        this.listCommitJavaType = this.objectMapper.getTypeFactory().constructType(TYPE_REF_LIST_COMMIT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponse<Repository> listOrganizationRepos(final String orgName, final int page, 
            final int perPage) throws GitHubException {
        final PaginatedResponse<Repository> pResponse;
        try {
            final Response response = this.retrofitInterface.listOrganizationRepos(orgName, page, perPage);
            final List<Repository> repos = this.objectMapper.readValue(response.getBody().in(), 
                    this.listRepoJavaType);
            pResponse = new PaginatedResponse<>(repos, page, perPage, findLastPage(response));
        } catch (RetrofitError | IOException e) {
            throw new GitHubException(e);
        }
        return pResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginatedResponse<GitHubCommit> listRepoCommits(final String ownerName, final String repoName, 
            final int page, final int perPage) throws GitHubException {
        final PaginatedResponse<GitHubCommit> pResponse;
        try {
            final Response response = this.retrofitInterface.listRepoCommits(ownerName, repoName, page, perPage);
            final List<GitHubCommit> commits = this.objectMapper.readValue(response.getBody().in(), 
                    this.listCommitJavaType);
            pResponse = new PaginatedResponse<>(commits, page, perPage, findLastPage(response));
        } catch (RetrofitError | IOException e) {
            throw new GitHubException(e);
        }
        return pResponse;
    }

    private static Integer findLastPage(final Response response) {
        Header linkHeader = null;
        for (final Header header : response.getHeaders()) {
            if ("Link".equals(header.getName())) {
                linkHeader = header;
                break;
            }
        }
        Integer lastPage = null;
        if (linkHeader != null) {
            final String[] rels = COMMA_PATTERN.split(linkHeader.getValue());
            for (final String rel : rels) {
                final String[] relParts = SEMICOLON_PATTERN.split(rel);
                final String relType = EQUAL_PATTERN.split(relParts[1].trim())[1].trim();
                if ("\"last\"".equals(relType)) {
                    // FIXME: Ugly!
                    final String linkUrl = relParts[0];
                    lastPage = Integer.valueOf(linkUrl.substring(linkUrl.length() - 2, linkUrl.length() - 1));
                }
            }
        }
        return lastPage;
    }
}
