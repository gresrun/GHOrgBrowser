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

import net.greghaines.ghorgbrowser.model.Repository;

/**
 * OrgReposView shows a list of organization repositories.
 */
public class OrgReposView extends AbstractView {

    private final String orgName;
    private final List<Repository> repos;

    /**
     * Constructor.
     * @param orgName the organization name
     * @param repos the repositories
     */
    public OrgReposView(final String orgName, final List<Repository> repos) {
        super("orgRepos");
        this.orgName = orgName;
        this.repos = repos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle() {
        return this.orgName + " Repos";
    }

    /**
     * @return the organization name
     */
    public String getOrgName() {
        return this.orgName;
    }

    /**
     * @return the repositories
     */
    public List<Repository> getRepos() {
        return this.repos;
    }
}
