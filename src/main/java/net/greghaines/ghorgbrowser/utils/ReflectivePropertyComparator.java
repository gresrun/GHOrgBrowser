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
package net.greghaines.ghorgbrowser.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * ReflectivePropertyComparator sorts based on a property.
 */
public final class ReflectivePropertyComparator implements Comparator<Object>, Serializable {

    /**
     * SortDirection.
     */
    public enum SortDirection {
        /**
         * Ascending sort.
         */
        ASC,
        /**
         * Descending sort.
         */
        DESC;
    }

    private static final long serialVersionUID = -2585230713283829768L;

    private final String sortKey;
    private final int sortMultiplier;
    private transient Method sortMethod;

    /**
     * Constructor.
     * @param sortKey the property name to sort by
     * @param sortDir the sort direction, if null, ASC is assumed
     */
    public ReflectivePropertyComparator(final String sortKey, final SortDirection sortDir) {
        if (sortKey == null || "".equals(sortKey)) {
            throw new IllegalArgumentException("sortKey must not be null or empty");
        }
        this.sortKey = sortKey;
        this.sortMultiplier = (sortDir == null || SortDirection.ASC.equals(sortDir)) ? 1 : -1;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public int compare(final Object obj1, final Object obj2) {
        final int comp;
        try {
            findSortMethod(obj1);
            final Comparable res1 = (Comparable) this.sortMethod.invoke(obj1);
            final Comparable res2 = (Comparable) this.sortMethod.invoke(obj2);
            if (res1 != null && res2 != null) {
                comp = res1.compareTo(res2);
            } else if (res1 != null && res2 == null) {
                comp = 1;
            } else if (res1 == null && res2 != null) {
                comp = -1;
            } else { // Both null
                comp = 0;
            }
        } catch (ReflectiveOperationException | IntrospectionException e) {
            throw new IllegalArgumentException(e);
        }
        return (comp * this.sortMultiplier);
    }

    private void findSortMethod(final Object obj) throws IntrospectionException {
        if (this.sortMethod == null) {
            final PropertyDescriptor[] props = Introspector.getBeanInfo(obj.getClass()).getPropertyDescriptors();
            for (final PropertyDescriptor prop : props) {
                if (this.sortKey.equals(prop.getName())) {
                    this.sortMethod = prop.getReadMethod();
                    if (!Comparable.class.isAssignableFrom(this.sortMethod.getReturnType())) {
                        throw new IntrospectionException("Property named " + this.sortKey + " is not comparable");
                    }
                    break;
                }
            }
            if (this.sortMethod == null) {
                throw new IntrospectionException("Failed to find property named " + this.sortKey);
            }
        }
    }
}
