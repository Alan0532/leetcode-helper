package com.leetcode.helper;

import com.alibaba.fastjson.JSON;
import com.leetcode.helper.model.LeetCodeNode;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class LeetCodeHelper {

    public static void code(String parameter) {
        code(parameter, null);
    }

    public static void code(String parameter, String methodName) {
        try {
            Class<?> mainClass = getMainApplicationClass();
            Class<?> clazz = Class.forName(mainClass.getName() + "$Solution");
            Object outerObject = mainClass.newInstance();
            Constructor<?> constructor = clazz.getDeclaredConstructor(getMainApplicationClass());
            constructor.setAccessible(true);
            // new Solution
            Object object = constructor.newInstance(outerObject);
            Method[] methods = clazz.getMethods();
            boolean notBreak = true;
            Method invokeMethod = null;
            // first method or methodName
            for (Method method : methods) {
                if (methodName != null && methodName.equals(method.getName())) {
                    invokeMethod = method;
                    notBreak = false;
                    break;
                } else if (methodName == null) {
                    Class<?> declaringClass = method.getDeclaringClass();
                    if (declaringClass.getName().equals(clazz.getName())) {
                        if (invokeMethod == null) {
                            invokeMethod = method;
                            notBreak = false;
                        } else {
                            throw new Exception("More than one method was found, please specify methodName");
                        }
                    }
                }
            }
            if (notBreak) {
                throw new Exception("Method " + methodName + " in the Class Solution was not found");
            }
            invokeSolution(parameter, object, invokeMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void invokeSolution(String strParameter, Object object, Method method) throws Exception {
        method.setAccessible(true);
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] strParameters = getStringParameters(strParameter, parameterTypes.length);
        Object[] parameters = strParametersToParameters(strParameters, parameterTypes);
        Object invokeOb = method.invoke(object, parameters);
        if (invokeOb instanceof LeetCodeNode) {
            System.out.println(invokeOb);
        } else {
            System.out.println(JSON.toJSONString(invokeOb));
        }
    }

    private static Object[] strParametersToParameters(String[] strParameters, Class<?>[] parameterTypes) throws Exception {
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> clazz = parameterTypes[i];
            String strParameter = strParameters[i];
            if (clazz.isPrimitive() || clazz.isArray() || "java.lang.String".equals(clazz.getName())) {
                buildParameters(clazz, strParameter, i, parameters);
            } else {
                Object parameter = clazz.newInstance();
                if (parameter instanceof LeetCodeNode) {
                    parameters[i] = ((LeetCodeNode) parameter).convert(strParameter);
                } else {
                    throw new Exception("Unsupported parameter type: " + clazz.getName());
                }
            }
        }
        return parameters;
    }

    private static String[] getStringParameters(String parameter, int length) throws Exception {
        int strlen = parameter.length();
        String[] parameters = new String[length];
        while (strlen > 0) {
            int index = parameter.lastIndexOf(" = ", strlen);
            if (--length < 0) {
                throw new Exception("The number of parameter lists does not match");
            } else {
                parameters[length] = parameter.substring(index + 3, strlen);
                strlen = parameter.lastIndexOf(", ", strlen - 1);
            }
        }
        return parameters;
    }

    private static void buildParameters(Class<?> clazz, String strParameter, int i, Object[] parameters) throws Exception {
        if (clazz.isPrimitive() || "java.lang.String".equals(clazz.getName())) {
            //primitive or string object
            parameters[i] = JSON.parseObject(strParameter, clazz);
        } else if (clazz.isArray()) {
            Class<?> componentType = clazz.getComponentType();
            if (componentType.isPrimitive() || "java.lang.String".equals(componentType.getName())) {
                //primitive or string array
                parameters[i] = JSON.parseObject(strParameter, clazz);
            } else if (componentType.isArray()) {
                buildArray(clazz, clazz, strParameter, i, parameters);
            } else {
                //Array type
                Object parameter = clazz.newInstance();
                if (parameter instanceof LeetCodeNode) {
                    parameters[i] = ((LeetCodeNode) parameter).convertArray(strParameter);
                } else {
                    throw new Exception("Unsupported parameter array type: " + componentType.getName());
                }
            }
        } else {
            throw new Exception("Unsupported parameter type: " + clazz.getName());
        }
    }

    private static void buildArray(Class<?> rootClass, Class<?> clazz, String strParameter, int i, Object[] parameters) throws Exception {
        if (clazz.isPrimitive() || "java.lang.String".equals(clazz.getName())) {
            parameters[i] = JSON.parseObject(strParameter, rootClass);
        } else if (clazz.isArray()) {
            Class<?> componentType = clazz.getComponentType();
            if (componentType.isPrimitive() || "java.lang.String".equals(componentType.getName())) {
                parameters[i] = JSON.parseObject(strParameter, rootClass);
            } else if (componentType.isArray()) {
                buildArray(rootClass, componentType, strParameter, i, parameters);
            } else {
                //LeetCodeNode not support matrix
                throw new Exception("Unsupported parameter array type: " + clazz.getName());
            }
        }
    }

    private static Class<?> getMainApplicationClass() throws Exception {
        Class<?> clazz = null;
        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if ("main".equals(stackTraceElement.getMethodName())) {
                clazz = Class.forName(stackTraceElement.getClassName());
            }
        }
        if (clazz == null) {
            throw new ClassNotFoundException();
        }
        return clazz;
    }


}