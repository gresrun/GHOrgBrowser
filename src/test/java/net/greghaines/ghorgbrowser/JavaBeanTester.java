package net.greghaines.ghorgbrowser;

import static org.junit.Assert.*;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.powermock.api.mockito.PowerMockito;

/**
 * This helper class can be used to unit test the get/set methods of JavaBean-style Value Objects.
 * @author rob.dawson
 */
public final class JavaBeanTester {
    
    /**
     * Tests the get/set methods of the specified class.
     * @param <T> the type parameter associated with the class under test
     * @param clazz the Class under test
     * @param skipThese the names of any properties that should not be tested
     * @throws IntrospectionException thrown if the Introspector.getBeanInfo() method throws this exception for the
     * class under test
     */
    public static <T> void test(final Class<T> clazz, final String... skipThese) throws IntrospectionException {
        final Set<String> skipPropNames = new HashSet<String>((skipThese == null) 
                ? Collections.<String>emptyList() 
                : Arrays.asList(skipThese));
        final PropertyDescriptor[] props = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
        for (final PropertyDescriptor prop : props) {
            // Check the list of properties that we don't want to test
            if (skipPropNames.contains(prop.getName())) {
                continue;
            }
            findBooleanIsMethods(clazz, prop);
            final Method getter = prop.getReadMethod();
            final Method setter = prop.getWriteMethod();
            if (getter != null && setter != null) {
                // We have both a get and set method for this property
                final Class<?> returnType = getter.getReturnType();
                final Class<?>[] params = setter.getParameterTypes();
                if (params.length == 1 && params[0] == returnType) {
                    // The set method has 1 argument, which is of the same type as the return type of the get method, 
                    // so we can test this property
                    try {
                        // Build a value of the correct type to be passed to the set method
                        final Object value = buildValue(returnType);
                        // Build an instance of the bean that we are testing (each property test gets a new instance)
                        final T bean = clazz.newInstance();
                        // Call the set method, then check the same value comes back out of the get method
                        setter.invoke(bean, value);
                        assertEquals(String.format("Failed while testing property %s", prop.getName()), 
                                value, getter.invoke(bean));
                    } catch (Exception ex) {
                        fail(String.format("An exception was thrown while testing the property %s: %s", 
                                prop.getName(), ex.toString()));
                    }
                }
            }
        }
        try {
            final T bean = clazz.newInstance();
            assertNotNull(bean.toString());
            assertEquals(bean.hashCode(), bean.hashCode());
        } catch (Exception ex) {
            fail("An exception was thrown while testing toString() and hashCode(): " + ex.toString());
        }
    }

    private static Object buildValue(final Class<?> clazz) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, SecurityException, InvocationTargetException {
        Object value;
        // Specific rules for common classes
        if (clazz == String.class) {
            value = "testvalue";
        } else if (clazz.isArray()) {
            value = Array.newInstance(clazz.getComponentType(), 1);
        } else if (clazz == boolean.class || clazz == Boolean.class) {
            value = true;
        } else if (clazz == int.class || clazz == Integer.class) {
            value = 1;
        } else if (clazz == long.class || clazz == Long.class) {
            value = 1L;
        } else if (clazz == double.class || clazz == Double.class) {
            value = 1.0D;
        } else if (clazz == float.class || clazz == Float.class) {
            value = 1.0F;
        } else if (clazz == char.class || clazz == Character.class) {
            value = 'Y';
        } else if (clazz.isEnum()) {
            value = clazz.getEnumConstants()[0];
        } else {
            // Try to mock it
            try {
                value = PowerMockito.mock(clazz);
            } catch (IllegalArgumentException ie) {
                // Final class??
                value = null;
            }
            if (value == null) {
                // Next check for a no-argument constructor
                for (final Constructor<?> ctr : clazz.getConstructors()) {
                    if (ctr.getParameterTypes().length == 0) {
                        // The class has a no-argument constructor, so just call it
                        value = ctr.newInstance();
                        break;
                    }
                }
            }
            if (value == null) {
                fail("Unable to build an instance of class " + clazz.getName() + ", please add some code to the "
                        + JavaBeanTester.class.getName() + " class to do this.");
            }
        }
        return value;
    }

    /**
     * Hunt down missing Boolean read method if needed as {@link Introspector} cannot find 'is' getters 
     * for Boolean type properties.
     * @param clazz the type being introspected
     * @param descriptor the property descriptor found so far
     */
    private static <T> void findBooleanIsMethods(final Class<T> clazz, final PropertyDescriptor descriptor)
            throws IntrospectionException {
        if ((descriptor.getReadMethod() == null) && (descriptor.getPropertyType() == Boolean.class)) {
            descriptor.setReadMethod(new PropertyDescriptor(descriptor.getName(), clazz).getReadMethod());
        }
    }
    
    private JavaBeanTester() {
        // Utility class
    }
}
