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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.greghaines.ghorgbrowser.utils.ReflectivePropertyComparator.SortDirection;

/**
 * ReflectivePropertyComparatorTest tests ReflectivePropertyComparator.
 */
public class ReflectivePropertyComparatorTest {

    private List<TestObj> list;

    @Before
    public void setUp() {
        this.list = Arrays.asList(
                new TestObj(91, "afd", new Object()),
                new TestObj(42, null, new Object()),
                new TestObj(null, "twc", new Object()),
                new TestObj(12, "bdf", null),
                new TestObj(11, "mjy", new Object()),
                new TestObj(41, "poi", new Object()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompare_NullSortKey() {
        Collections.sort(this.list, new ReflectivePropertyComparator(null, SortDirection.ASC));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompare_EmptySortKey() {
        Collections.sort(this.list, new ReflectivePropertyComparator("", SortDirection.ASC));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompare_BadMethodName() {
        Collections.sort(this.list, new ReflectivePropertyComparator("quz", SortDirection.ASC));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCompare_IncomparableMethodName() {
        Collections.sort(this.list, new ReflectivePropertyComparator("bar", SortDirection.ASC));
    }

    @Test
    public void testCompare_OK_ASC_Integer() {
        Collections.sort(this.list, new ReflectivePropertyComparator("val", SortDirection.ASC));
        Integer prevVal = null;
        for (final TestObj testObj : this.list) {
            final int comp = nullSafeCompare(testObj.getVal(), prevVal);
            Assert.assertTrue(comp >= 0);
            prevVal = testObj.getVal();
        }
    }

    @Test
    public void testCompare_OK_Null_Integer() {
        Collections.sort(this.list, new ReflectivePropertyComparator("val", null));
        Integer prevVal = null;
        for (final TestObj testObj : this.list) {
            final int comp = nullSafeCompare(testObj.getVal(), prevVal);
            Assert.assertTrue(comp >= 0);
            prevVal = testObj.getVal();
        }
    }

    @Test
    public void testCompare_OK_DESC_Integer() {
        Collections.sort(this.list, new ReflectivePropertyComparator("val", SortDirection.DESC));
        Integer prevVal = Integer.MAX_VALUE;
        for (final TestObj testObj : this.list) {
            final int comp = nullSafeCompare(testObj.getVal(), prevVal);
            Assert.assertTrue(comp <= 0);
            prevVal = testObj.getVal();
        }
    }

    @Test
    public void testCompare_OK_ASC_String() {
        Collections.sort(this.list, new ReflectivePropertyComparator("foo", SortDirection.ASC));
        String prevVal = null;
        for (final TestObj testObj : this.list) {
            final int comp = nullSafeCompare(testObj.getFoo(), prevVal);
            Assert.assertTrue(comp >= 0);
            prevVal = testObj.getFoo();
        }
    }

    @Test
    public void testCompare_OK_Null_String() {
        Collections.sort(this.list, new ReflectivePropertyComparator("foo", null));
        String prevVal = null;
        for (final TestObj testObj : this.list) {
            final int comp = nullSafeCompare(testObj.getFoo(), prevVal);
            Assert.assertTrue(comp >= 0);
            prevVal = testObj.getFoo();
        }
    }

    @Test
    public void testCompare_OK_DESC_String() {
        Collections.sort(this.list, new ReflectivePropertyComparator("foo", SortDirection.DESC));
        String prevVal = "zzzzz";
        for (final TestObj testObj : this.list) {
            final int comp = nullSafeCompare(testObj.getFoo(), prevVal);
            Assert.assertTrue(comp <= 0);
            prevVal = testObj.getFoo();
        }
    }

    private static <T extends Comparable<T>> int nullSafeCompare(final T obj1, final T obj2) {
        final int retVal;
        if (obj1 != null && obj2 != null) {
            retVal = obj1.compareTo(obj2);
        } else if (obj1 != null && obj2 == null) {
            retVal = 1;
        } else if (obj1 == null && obj2 != null) {
            retVal = -1;
        } else {
            retVal = 0;
        }
        return retVal;
    }

    @SuppressWarnings("unused")
    private static class TestObj {

        private final Integer val;
        private final String foo;
        private final Object bar;

        public TestObj(final Integer val, final String foo, final Object bar) {
            this.val = val;
            this.foo = foo;
            this.bar = bar;
        }

        public Integer getVal() {
            return this.val;
        }

        public String getFoo() {
            return this.foo;
        }

        public Object getBar() {
            return this.bar;
        }
    }
}

