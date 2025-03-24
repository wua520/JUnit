package junit;

import java.io.File;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * MiniJUnit - 一个简易的 JUnit 替代工具。
 * 该工具可以自动发现并运行指定包中的测试方法。
 */
public class MiniJUnit {

    /**
     * 主方法，运行指定包下的所有测试方法。
     *
     * @param args 命令行参数（未使用）。
     */
    public static void main(String[] args) {
        runTests("test0"); // 这里填写你的测试包路径
    }

    /**
     * 运行指定包下的所有测试方法。
     *
     * @param packageName 需要测试的包名。
     */
    public static void runTests(String packageName) {
        List<Method> testMethods = new ArrayList<>();
        try {
            // 通过反射获取包中的所有类
            List<Class<?>> classes = getClasses(packageName);

            // 筛选符合条件的测试方法
            for (Class<?> clazz : classes) {
                for (Method method : clazz.getDeclaredMethods()) {
                    // 选择以 "test" 开头 或者 具有 @Test 注解的方法
                    if (method.getName().startsWith("test") || method.isAnnotationPresent(Test.class)) {
                        testMethods.add(method);
                    }
                }
            }

            System.out.println("=== 开始执行测试 ===");
            int passed = 0, failed = 0;
            int testIndex = 1; // 测试编号

            // 遍历并执行所有测试方法
            for (Method method : testMethods) {
                try {
                    Object instance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
                    String methodName = method.getName();
                    String methodDescription = getMethodDescription(methodName);

                    // 执行测试方法
                    try {
                        method.invoke(instance);  // 运行测试方法
                        System.out.println("测试" + testIndex + " [通过] " + methodDescription);
                        passed++;
                    } catch (AssertionError e) {
                        System.out.println("测试" + testIndex + " [失败] " + methodDescription);
                        e.printStackTrace();
                        failed++;
                    }
                } catch (Exception e) {
                    String methodName = method.getName();
                    String methodDescription = getMethodDescription(methodName);
                    System.out.println("测试" + testIndex + " [异常] " + methodDescription);
                    e.printStackTrace();
                    failed++;
                }
                testIndex++; // 增加测试编号
            }
            System.out.println("=== 测试完成: 通过 " + passed + "，失败 " + failed + " ===");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取测试方法的描述信息。
     *
     * @param methodName 测试方法名。
     * @return 对应的中文描述。
     */
    private static String getMethodDescription(String methodName) {
        switch (methodName) {
            case "testJosephusWithSmallGroup":
                return "测试小规模约瑟夫环";
            case "testJosephusWithKEquals1":
                return "测试 k 等于 1 的情况";
            case "testJosephusWithLargeGroup":
                return "测试大规模约瑟夫环";
            case "testJosephusWithNKEquals":
                return "测试 n 和 k 相等的情况";
            case "testJosephusWithOnePerson":
                return "测试只有一个人时的情况";
            default:
                return methodName;  // 默认返回方法名
        }
    }

    /**
     * 获取指定包中的所有类。
     *
     * @param packageName 需要获取的包名。
     * @return 该包下的所有 Class 对象列表。
     * @throws Exception 如果无法找到包或加载类时抛出异常。
     */
    public static List<Class<?>> getClasses(String packageName) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');
        URL resource = Thread.currentThread().getContextClassLoader().getResource(path);

        if (resource == null) {
            throw new RuntimeException("无法找到包: " + packageName);
        }

        File directory = new File(resource.toURI());
        for (File file : directory.listFiles()) {
            if (file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().replace(".class", "");
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }
}
