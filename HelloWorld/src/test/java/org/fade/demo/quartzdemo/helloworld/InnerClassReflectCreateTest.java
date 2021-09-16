package org.fade.demo.quartzdemo.helloworld;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

public class InnerClassReflectCreateTest {

    @Test
    public void testAError() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clz = Class.forName("org.fade.demo.quartzdemo.helloworld.InnerClassReflectCreateTest$A");
        Object instance = clz.getConstructor().newInstance();
        ((A) instance).test();
    }

    @Test
    public void testARight() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clz = Class.forName("org.fade.demo.quartzdemo.helloworld.InnerClassReflectCreateTest$A");
        Object instance = clz.getDeclaredConstructor(this.getClass()).newInstance(this);
        ((A) instance).test();
    }

    @Test
    public void testB() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clz = Class.forName("org.fade.demo.quartzdemo.helloworld.InnerClassReflectCreateTest$B");
        Object instance = clz.getConstructor().newInstance();
        ((B) instance).test();
    }

    @Test
    public void testCError() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clz = Class.forName("org.fade.demo.quartzdemo.helloworld.C");
        Object instance = clz.getConstructor().newInstance();
        ((C) instance).test();
    }

    @Test
    public void testCRight() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clz = Class.forName("org.fade.demo.quartzdemo.helloworld.C");
        Object instance = clz.getDeclaredConstructor().newInstance();
        ((C) instance).test();
    }

    public class A {
        void test() {
            System.out.println("A-test");
        }
    }

    public static class B {
        void test() {
            System.out.println("B-test");
        }
    }

}

class C {

    void test() {
        System.out.println("C-test");
    }

}
