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

import java.beans.IntrospectionException;

import net.greghaines.ghorgbrowser.JavaBeanTester;

import org.junit.Test;

/**
 * GitHubOrgBrowserConfigurationTest tests GitHubOrgBrowserConfiguration.
 */
public class GitHubOrgBrowserConfigurationTest {

    @Test
    public void testGettersSetters() throws IntrospectionException {
        JavaBeanTester.test(GitHubOrgBrowserConfiguration.class);
    }
}
