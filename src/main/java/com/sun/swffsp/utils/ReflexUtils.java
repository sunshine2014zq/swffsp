package com.sun.swffsp.utils;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 反射工具类
 *
 * @author sun
 * @date 2019/2/28 11:44
 */
public class ReflexUtils {

    /**
     * 反射获取字段的值
     *
     * @param clazz
     * @param name
     * @param object
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object getFieldValue(Class clazz, String name, Object object) throws ReflectiveOperationException {
        String getMethodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
        Method method = getMethod(clazz, getMethodName);
        return invokeMethod(method, object);
    }

    /**
     * 反射设置字段的值
     *
     * @param clazz
     * @param object
     * @param name
     * @param val
     * @throws ReflectiveOperationException
     */
    public static void setFieldValue(Class clazz, Object object, String name, Object val) throws ReflectiveOperationException {
        Field field = getField(clazz, name);
        field.setAccessible(true);
        field.set(object, val);
    }

    /**
     * 通过反射将第二个对象不为空的字段的值覆盖到第一个对象并返回第一个对象
     *
     * @param clazz
     * @param b1
     * @param b2
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object mergeNotNull(Class clazz, Object b1, Object b2) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] fields = getFieldsNoStatic(clazz);
        for (Field field : fields) {
            if (field == null) {
                continue;
            }
            String fieldName = field.getName();
            String setMethodName = "set" + toFirstLetterUpperCase(fieldName);
            String getMethodName = "get" + toFirstLetterUpperCase(fieldName);
            Object value = invokeMethod(getMethod(clazz, getMethodName), b2);
            if (null != value) {
                invokeMethod(getMethod(clazz, setMethodName, field.getType()), b1, value);
            }
        }
        return b1;
    }

    /**
     * 将一个单词首字母大写
     *
     * @param str
     * @return
     */
    private static String toFirstLetterUpperCase(String str) {
        if (null == str) {
            return null;
        }
        if (str.length() == 1) {
            return str.toUpperCase();
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


    /**
     * 获取非静态的所有字段-包括父类的
     *
     * @param clazz
     * @return
     */
    public static Field[] getFieldsNoStatic(Class clazz) {
        Field[] fields = getFields(clazz);
        List<Field> result = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            if (!Modifier.isStatic(field.getModifiers())) {
                result.add(field);
            }
        }
        return result.toArray(new Field[result.size()]);
    }

    /**
     * 获取所有字段-包括父类的-包括类型private,public,protected...
     *
     * @param clazz
     * @return
     */
    public static Field[] getFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>();

        while (clazz != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass(); //得到父类,然后赋给自己
        }
        Field[] result = new Field[fieldList.size()];
        return fieldList.toArray(result);
    }

    /**
     * 根据name获取字段-包括父类的，以及所有的类型。
     *
     * @param clazz
     * @param name
     * @return
     * @throws NoSuchFieldException
     */
    public static Field getField(Class clazz, String name) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            field = null;
        }
        while (field == null) {
            clazz = clazz.getSuperclass();
            if (clazz != null) {
                try {
                    field = clazz.getDeclaredField(name);
                } catch (NoSuchFieldException e) {
                    field = null;
                }
            } else {
                break;
            }
        }
        return field;
    }

    /**
     * 根据传入的方法名字符串，获取对应的public方法，包括父类的
     *
     * @param clazz          类的Class对象
     * @param name           方法名
     * @param parameterTypes 方法的形参对应的Class类型【可以不写】
     * @return 方法对象【Method类型】
     */
    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes)
            throws NoSuchMethodException, SecurityException {
        return clazz.getMethod(name, parameterTypes);
    }

    /**
     * 根据传入的方法对象，调用对应的方法
     *
     * @param method 方法对象
     * @param obj    要调用的实例对象【如果是调用静态方法，则可以传入null】
     * @param args   传入方法的实参【可以不写】
     * @return 方法的返回值【没有返回值，则返回null】
     */
    public static Object invokeMethod(Method method, Object obj, Object... args)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        method.setAccessible(true);
        return method.invoke(obj, args);
    }

    /**
     * 获取字段泛型class数组
     *
     * @param field
     * @return
     */
    public static Class<?>[] getGenericClass(Field field) {
        // 获取f字段的通用类型
        Type fc = field.getGenericType(); // 关键的地方得到其Generic的类型
        if (fc != null && fc instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) fc;
            Type[] types = pt.getActualTypeArguments();
            if (types != null && types.length > 0) {
                Class<?>[] classes = new Class<?>[types.length];
                for (int i = 0; i < classes.length; i++) {
                    classes[i] = (Class<?>) types[i];
                }
                return classes;
            }
        }
        return null;
    }
}


//    /**
//     * 获取包名
//     *
//     * @return 包名【String类型】
//     */
//    public static String getPackage(Class<?> clazz) {
//        Package pck = clazz.getPackage();
//        if (null != pck) {
//            return pck.getName();
//        } else {
//            return "没有包！";
//        }
//    }
//
//    /**
//     * 获取继承的父类的全类名
//     *
//     * @return 继承的父类的全类名【String类型】
//     */
//    public static String getSuperClassName(Class<?> clazz) {
//        Class<?> superClass = clazz.getSuperclass();
//        if (null != superClass) {
//            return superClass.getName();
//        } else {
//            return "没有父类！";
//        }
//    }
//
//    /**
//     * 获取全类名
//     *
//     * @return 全类名【String类型】
//     */
//    public static String getClassName(Class<?> clazz) {
//        return clazz.getName();
//    }
//
//    /**
//     * 获取实现的接口名
//     *
//     * @return 所有的接口名【每一个接口名的类型为String，最后保存到一个List集合中】
//     */
//    public static List<String> getInterfaces(Class<?> clazz) {
//        Class<?>[] interfaces = clazz.getInterfaces();
//        int len = interfaces.length;
//
//        List<String> list = new ArrayList<String>();
//        for (int i = 0; i < len; i++) {
//            Class<?> itfc = interfaces[i];
//
//            // 接口名
//            String interfaceName = itfc.getSimpleName();
//
//            list.add(interfaceName);
//        }
//
//        return list;
//    }
//
////    /**
////     * 获取所有属性
////     *
////     * @return 所有的属性【每一个属性添加到StringBuilder中，最后保存到一个List集合中】
////     */
////    public static List<StringBuilder> getFields(Class<?> clazz) {
////        Field[] fields = clazz.getDeclaredFields();
////        int len = fields.length;
////
////        List<StringBuilder> list = new ArrayList<StringBuilder>();
////        StringBuilder sb = null;
////        for (int i = 0; i < len; i++) {
////            Field field = fields[i];
////            sb = new StringBuilder();
////
////            // 修饰符
////            String modifier = Modifier.toString(field.getModifiers());
////            sb.append(modifier + " ");
////
////            // 数据类型
////            Class<?> type = field.getType();
////            String typeName = type.getSimpleName();
////            sb.append(typeName + " ");
////
////            // 属性名
////            String fieldName = field.getName();
////            sb.append(fieldName + ";");
////
////            list.add(sb);
////        }
////
////        return list;
////    }
//
//
//    /**
//     * 获取所有公共的属性
//     *
//     * @return 所有公共的属性【每一个属性添加到StringBuilder中，最后保存到一个List集合中】
//     */
//    public static List<StringBuilder> getPublicFields(Class<?> clazz) {
//        Field[] fields = clazz.getFields();
//        int len = fields.length;
//
//        List<StringBuilder> list = new ArrayList<StringBuilder>();
//        StringBuilder sb = null;
//        for (int i = 0; i < len; i++) {
//            Field field = fields[i];
//            sb = new StringBuilder();
//
//            // 修饰符
//            String modifier = Modifier.toString(field.getModifiers());
//            sb.append(modifier + " ");
//
//            // 数据类型
//            Class<?> type = field.getType();
//            String typeName = type.getSimpleName();
//            sb.append(typeName + " ");
//
//            // 属性名
//            String fieldName = field.getName();
//            sb.append(fieldName + ";");
//
//            list.add(sb);
//        }
//
//        return list;
//    }
//
//    /**
//     * 获取所有构造方法
//     *
//     * @return 所有的构造方法【每一个构造方法添加到StringBuilder中，最后保存到一个List集合中】
//     */
//    public static List<StringBuilder> getConstructors(Class<?> clazz) {
//        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
//        int len = constructors.length;
//
//        List<StringBuilder> list = new ArrayList<StringBuilder>();
//        StringBuilder sb = null;
//        for (int i = 0; i < len; i++) {
//            Constructor<?> constructor = constructors[i];
//            sb = new StringBuilder();
//
//            // 修饰符
//            String modifier = Modifier.toString(constructor.getModifiers());
//            sb.append(modifier + " ");
//
//            // 方法名（类名）
//            String constructorName = clazz.getSimpleName();
//            sb.append(constructorName + " (");
//
//            // 形参列表
//            Class<?>[] parameterTypes = constructor.getParameterTypes();
//            int length = parameterTypes.length;
//            for (int j = 0; j < length; j++) {
//                Class<?> parameterType = parameterTypes[j];
//
//                String parameterTypeName = parameterType.getSimpleName();
//
//                if (j < length - 1) {
//                    sb.append(parameterTypeName + ", ");
//                } else {
//                    sb.append(parameterTypeName);
//                }
//
//            }
//
//            sb.append(") {}");
//
//            list.add(sb);
//        }
//
//        return list;
//    }
//
//    /**
//     * 获取所有自身的方法
//     *
//     * @return 所有自身的方法【每一个方法添加到StringBuilder中，最后保存到一个List集合中】
//     */
//    public static List<StringBuilder> getMethods(Class<?> clazz) {
//        Method[] methods = clazz.getDeclaredMethods();
//        int len = methods.length;
//
//        List<StringBuilder> list = new ArrayList<StringBuilder>();
//        StringBuilder sb = null;
//        for (int i = 0; i < len; i++) {
//            Method method = methods[i];
//            sb = new StringBuilder();
//
//            // 修饰符
//            String modifier = Modifier.toString(method.getModifiers());
//            sb.append(modifier + " ");
//
//            // 返回值类型
//            Class<?> returnClass = method.getReturnType();
//            String returnType = returnClass.getSimpleName();
//            sb.append(returnType + " ");
//
//            // 方法名
//            String methodName = method.getName();
//            sb.append(methodName + " (");
//
//            // 形参列表
//            Class<?>[] parameterTypes = method.getParameterTypes();
//            int length = parameterTypes.length;
//
//            for (int j = 0; j < length; j++) {
//                Class<?> parameterType = parameterTypes[j];
//
//                // 形参类型
//                String parameterTypeName = parameterType.getSimpleName();
//
//                if (j < length - 1) {
//                    sb.append(parameterTypeName + ", ");
//                } else {
//                    sb.append(parameterTypeName);
//                }
//
//            }
//
//            sb.append(") {}");
//
//            list.add(sb);
//        }
//
//        return list;
//    }
//
//    /**
//     * 获取所有公共的方法
//     *
//     * @return 所有公共的方法【每一个方法添加到StringBuilder中，最后保存到一个List集合中】
//     */
//    public static List<StringBuilder> getPublicMethods(Class<?> clazz) {
//        Method[] methods = clazz.getMethods();
//        int len = methods.length;
//
//        List<StringBuilder> list = new ArrayList<StringBuilder>();
//        StringBuilder sb = null;
//        for (int i = 0; i < len; i++) {
//            Method method = methods[i];
//            sb = new StringBuilder();
//
//            // 修饰符
//            String modifier = Modifier.toString(method.getModifiers());
//            sb.append(modifier + " ");
//
//            // 返回值类型
//            Class<?> returnClass = method.getReturnType();
//            String returnType = returnClass.getSimpleName();
//            sb.append(returnType + " ");
//
//            // 方法名
//            String methodName = method.getName();
//            sb.append(methodName + " (");
//
//            // 形参列表
//            Class<?>[] parameterTypes = method.getParameterTypes();
//            int length = parameterTypes.length;
//
//            for (int j = 0; j < length; j++) {
//                Class<?> parameterType = parameterTypes[j];
//
//                // 形参类型
//                String parameterTypeName = parameterType.getSimpleName();
//
//                if (j < length - 1) {
//                    sb.append(parameterTypeName + ", ");
//                } else {
//                    sb.append(parameterTypeName);
//                }
//
//            }
//
//            sb.append(") {}");
//
//            list.add(sb);
//        }
//
//        return list;
//    }
//
//    /**
//     * 获取所有的注解名
//     *
//     * @return 所有的注解名【每一个注解名的类型为String，最后保存到一个List集合中】
//     */
//    public static List<String> getAnnotations(Class<?> clazz) {
//        Annotation[] annotations = clazz.getAnnotations();
//        int len = annotations.length;
//
//        List<String> list = new ArrayList<String>();
//        for (int i = 0; i < len; i++) {
//            Annotation annotation = annotations[i];
//
//            String annotationName = annotation.annotationType().getSimpleName();
//            list.add(annotationName);
//        }
//
//        return list;
//    }
//
//    /**
//     * 获取父类的泛型
//     *
//     * @return 父类的泛型【Class类型】
//     */
//    public static Class<?> getSuperClassGenericParameterizedType(Class<?> clazz) {
//        Type genericSuperClass = clazz.getGenericSuperclass();
//
//        Class<?> superClassGenericParameterizedType = null;
//
//        // 判断父类是否有泛型
//        if (genericSuperClass instanceof ParameterizedType) {
//            // 向下转型，以便调用方法
//            ParameterizedType pt = (ParameterizedType) genericSuperClass;
//            // 只取第一个，因为一个类只能继承一个父类
//            Type superClazz = pt.getActualTypeArguments()[0];
//            // 转换为Class类型
//            superClassGenericParameterizedType = (Class<?>) superClazz;
//        }
//
//        return superClassGenericParameterizedType;
//    }
//
//    /**
//     * 获取接口的所有泛型
//     *
//     * @return 所有的泛型接口【每一个泛型接口的类型为Class，最后保存到一个List集合中】
//     */
//    public static List<Class<?>> getInterfaceGenericParameterizedTypes(Class<?> clazz) {
//        Type[] genericInterfaces = clazz.getGenericInterfaces();
//        int len = genericInterfaces.length;
//
//        List<Class<?>> list = new ArrayList<Class<?>>();
//        for (int i = 0; i < len; i++) {
//            Type genericInterface = genericInterfaces[i];
//
//            // 判断接口是否有泛型
//            if (genericInterface instanceof ParameterizedType) {
//                ParameterizedType pt = (ParameterizedType) genericInterface;
//
//                // 得到所有的泛型【Type类型的数组】
//                Type[] interfaceTypes = pt.getActualTypeArguments();
//
//                int length = interfaceTypes.length;
//
//                for (int j = 0; j < length; j++) {
//                    // 获取对应的泛型【Type类型】
//                    Type interfaceType = interfaceTypes[j];
//                    // 转换为Class类型
//                    Class<?> interfaceClass = (Class<?>) interfaceType;
//                    list.add(interfaceClass);
//                }
//
//            }
//
//        }
//
//        return list;
//    }
//
//    /**
//     * 打印包名
//     */
//    public static void printPackage(Class<?> clazz) {
//        System.out.println(getPackage(clazz));
//    }
//
//    /**
//     * 打印继承的父类的全类名
//     */
//    public static void printSuperClassName(Class<?> clazz) {
//        System.out.println(getSuperClassName(clazz));
//    }
//
//    /**
//     * 打印全类名
//     */
//    public static void printClassName(Class<?> clazz) {
//        System.out.println(getClassName(clazz));
//    }
//
//    /**
//     * 打印实现的接口
//     */
//    public static void printInterfaces(Class<?> clazz) {
//        List<String> list = getInterfaces(clazz);
//        int size = list.size();
//        if (0 < size) {
//            for (int i = 0; i < size; i++) {
//                System.out.println(list.get(i));
//            }
//        } else {
//            System.out.println("没有实现接口！");
//        }
//    }
//
////    /**
////     * 打印所有属性
////     */
////    public static void printFields(Class<?> clazz) {
////        List<StringBuilder> list = getFields(clazz);
////        int size = list.size();
////        if (0 < size) {
////            for (int i = 0; i < size; i++) {
////                System.out.println(list.get(i));
////            }
////        } else {
////            System.out.println("没有属性！");
////        }
////    }
//
//    /**
//     * 打印所有公共的属性
//     */
//    public static void printPublicFields(Class<?> clazz) {
//        List<StringBuilder> list = getPublicFields(clazz);
//        int size = list.size();
//        if (0 < size) {
//            for (int i = 0; i < size; i++) {
//                System.out.println(list.get(i));
//            }
//        } else {
//            System.out.println("没有属性！");
//        }
//    }
//
//    /**
//     * 打印所有构造方法
//     */
//    public static void printConstructors(Class<?> clazz) {
//        List<StringBuilder> list = getConstructors(clazz);
//        int size = list.size();
//        if (0 < size) {
//            for (int i = 0; i < size; i++) {
//                System.out.println(list.get(i));
//            }
//        } else {
//            System.out.println("没有构造方法！");
//        }
//    }
//
//    /**
//     * 打印所有方法
//     */
//    public static void printMethods(Class<?> clazz) {
//        List<StringBuilder> list = getMethods(clazz);
//        int size = list.size();
//        if (0 < size) {
//            for (int i = 0; i < size; i++) {
//                System.out.println(list.get(i));
//            }
//        } else {
//            System.out.println("没有方法！");
//        }
//    }
//
//    /**
//     * 打印所有公共的方法
//     */
//    public static void printPublicMethods(Class<?> clazz) {
//        List<StringBuilder> list = getPublicMethods(clazz);
//        int size = list.size();
//        if (0 < size) {
//            for (int i = 0; i < size; i++) {
//                System.out.println(list.get(i));
//            }
//        } else {
//            System.out.println("没有方法！");
//        }
//    }
//
//    /**
//     * 打印所有的注解
//     */
//    public static void printAnnotations(Class<?> clazz) {
//        List<String> list = getAnnotations(clazz);
//        int size = list.size();
//        if (0 < size) {
//            for (int i = 0; i < size; i++) {
//                System.out.println(list.get(i));
//            }
//        } else {
//            System.out.println("没有注解！");
//        }
//    }
//
//    /**
//     * 打印父类的泛型名
//     */
//    public static void printSuperClassGenericParameterizedType(Class<?> clazz) {
//        Class<?> superClassGenericParameterizedType = getSuperClassGenericParameterizedType(clazz);
//        if (null != superClassGenericParameterizedType) {
//            System.out.println(superClassGenericParameterizedType.getSimpleName());
//        } else {
//            System.out.println("父类没有泛型！");
//        }
//    }
//
//    /**
//     * 打印接口的泛型
//     */
//    public static void printInterfaceGenericParameterizedTypes(Class<?> clazz) {
//        List<Class<?>> list = getInterfaceGenericParameterizedTypes(clazz);
//        int size = list.size();
//        if (0 < size) {
//            for (int i = 0; i < size; i++) {
//                System.out.println(list.get(i).getSimpleName());
//            }
//        } else {
//            System.out.println("没有泛型接口！");
//        }
//    }
//
//    /**
//     * 打印一个类的相关信息
//     *
//     * @param clazz
//     */
//    public static void printAll(Class<?> clazz) {
//        System.out.print("【包名】  ");
//        printPackage(clazz);
//
//        System.out.print("【类名】  ");
//        System.out.println(clazz.getSimpleName());
//
//        System.out.println("\n【父类全类名】");
//        printSuperClassName(clazz);
//        System.out.println("【全类名】");
//        printClassName(clazz);
//        System.out.println("\n【所有已实现的接口】");
//        printInterfaces(clazz);
//
//        System.out.println("\n【属性】");
////        printFields(clazz);
//        System.out.println("\n【构造方法】");
//        printConstructors(clazz);
//        System.out.println("\n【方法】");
//        printMethods(clazz);
//
//        System.out.println("\n【公共的属性】");
//        printPublicFields(clazz);
//        System.out.println("\n【公共的方法】");
//        printPublicMethods(clazz);
//    }
//
//    /**
//     * 根据Class类型，获取对应的实例【要求必须有无参的构造器】
//     *
//     * @return 对应的实例【Object类型】
//     */
//    public static Object getNewInstance(Class<?> clazz) throws InstantiationException, IllegalAccessException {
//        return clazz.newInstance();
//    }
//
//    /**
//     * 根据传入的类的Class对象，以及构造方法的形参的Class对象，获取对应的构造方法对象
//     *
//     * @param clazz          类的Class对象
//     * @param parameterTypes 构造方法的形参的Class对象【可以不写】
//     * @return 构造方法对象【Constructor类型】
//     */
//    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes)
//            throws NoSuchMethodException, SecurityException {
//        return clazz.getDeclaredConstructor(parameterTypes);
//    }
//
//    /**
//     * 根据传入的构造方法对象，以及，获取对应的实例
//     *
//     * @param constructor 构造方法对象
//     * @param initargs    传入构造方法的实参【可以不写】
//     * @return 对应的实例【Object类型】
//     */
//    public static Object getNewInstance(Constructor<?> constructor, Object... initargs)
//            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//        constructor.setAccessible(true);
//        return constructor.newInstance(initargs);
//    }
//
////    /**
////     * 根据传入的属性名字符串，修改对应的属性值
////     *
////     * @param clazz 类的Class对象
////     * @param name  属性名
////     * @param obj   要修改的实例对象
////     * @param value 修改后的新值
////     */
////    public static void setField(Class<?> clazz, String name, Object obj, Object value)
////            throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
////        Field field = clazz.getDeclaredField(name);
////        field.setAccessible(true);
////        field.set(obj, value);
////    }
//

//
//
//

