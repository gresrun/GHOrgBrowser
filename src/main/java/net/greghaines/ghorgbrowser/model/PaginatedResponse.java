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
package net.greghaines.ghorgbrowser.model;

import java.util.List;

/**
 * PaginatedResponse wraps a list of values with pagination data.
 * @param <T> the element type
 */
public class PaginatedResponse<T> {

    private final List<T> elements;
    private final Integer currentPage;
    private final Integer elementsPerPage;
    private final Integer lastPage;

    /**
     * Constructor.
     * @param elements the list of elements
     * @param currentPage the current page number
     * @param elementsPerPage number of elements per page
     * @param lastPage the last page number
     */
    public PaginatedResponse(final List<T> elements, final Integer currentPage, final Integer elementsPerPage, 
            final Integer lastPage) {
        this.elements = elements;
        this.currentPage = currentPage;
        this.elementsPerPage = elementsPerPage;
        this.lastPage = lastPage;
    }

    /**
     * @return the elements
     */
    public List<T> getElements() {
        return this.elements;
    }

    /**
     * @return the current page number
     */
    public Integer getCurrentPage() {
        return this.currentPage;
    }

    /**
     * @return the number of elements per page
     */
    public Integer getElementsPerPage() {
        return this.elementsPerPage;
    }

    /**
     * @return the last page number
     */
    public Integer getLastPage() {
        return this.lastPage;
    }
}
