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
package net.greghaines.ghorgbrowser;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import net.greghaines.ghorgbrowser.resource.IndexResource;
import net.greghaines.ghorgbrowser.resource.OrgReposResource;
import net.greghaines.ghorgbrowser.service.GitHubService;
import net.greghaines.ghorgbrowser.service.impl.RetrofitGitHubService;
import retrofit.RestAdapter;

import com.bazaarvoice.dropwizard.webjars.WebJarBundle;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * GitHubOrgBrowserApplication builds the GitHub Organization Browser application with DropWizard components.
 */
public class GitHubOrgBrowserApplication extends Application<GitHubOrgBrowserConfiguration> {

    private static final String APP_NAME = "GitHub Organization Browser";

    /**
     * Application entry point.
     * @param args the command-line arguments
     * @throws Exception if something goes wrong
     */
    public static void main(final String... args) throws Exception {
        new GitHubOrgBrowserApplication().run(args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return APP_NAME + " v" + getClass().getPackage().getImplementationVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final Bootstrap<GitHubOrgBrowserConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new WebJarBundle());
        bootstrap.addBundle(new ViewBundle());
        bootstrap.getObjectMapper().registerModule(new JodaModule());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(final GitHubOrgBrowserConfiguration config, final Environment env) {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .build();
        final GitHubService gitHubService = new RetrofitGitHubService(restAdapter, env.getObjectMapper());
        env.jersey().register(new IndexResource());
        env.jersey().register(new OrgReposResource(gitHubService));
    }
}
