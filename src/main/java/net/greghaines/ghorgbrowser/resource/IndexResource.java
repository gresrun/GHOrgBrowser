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

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import net.greghaines.ghorgbrowser.view.IndexView;

import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;

/**
 * IndexResource serves the index page.
 */
@Path("/")
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.TEXT_HTML)
public class IndexResource {

    @GET
    @Timed(name = "html.timer")
    @Metered(name = "html.meter")
    public IndexView getIndexView() {
        return new IndexView();
    }

    @POST
    @Timed(name = "form.timer")
    @Metered(name = "form.meter")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response formCreate(@FormParam("orgName") final String orgName,
            @FormParam("sortBy") final String sortBy) throws NoSuchMethodException {
        final URI newURI = UriBuilder.fromResource(OrgReposResource.class)
                .queryParam("sortBy", sortBy)
                .build(orgName);
        return Response.seeOther(newURI).build();
    }
}