/*
 * Copyright 2020 Martin Pallmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
