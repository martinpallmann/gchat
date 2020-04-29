package de.martinpallmann.gchat.tck;

import java.lang.reflect.InvocationTargetException;

class Reflection {
//    public static ResponseTestCase responseTestCase(String name) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        return Class
//                .forName(name)
//                .asSubclass(ResponseTestCase.class)
//                .getDeclaredConstructor()
//                .newInstance();
//    }

    public static EventTestCase eventTestCase(String name) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return Class
                .forName(name)
                .asSubclass(EventTestCase.class)
                .getDeclaredConstructor()
                .newInstance();
    }
}
