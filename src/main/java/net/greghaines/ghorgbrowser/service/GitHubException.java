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

/**
 * GitHubException is thrown when an error occurs while communicating with the GitHub API.
 */
public class GitHubException extends Exception {

    private static final long serialVersionUID = -562046587081050485L;

    /**
     * Default constructor.
     */
    public GitHubException() {
        super();
    }

    /**
     * Constructor.
     * @param message the message
     */
    public GitHubException(final String message) {
        super(message);
    }

    /**
     * Constructor.
     * @param cause the cause
     */
    public GitHubException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructor.
     * @param message the message
     * @param cause the cause
     */
    public GitHubException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
