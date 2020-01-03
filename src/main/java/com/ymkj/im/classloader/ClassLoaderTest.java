package com.ymkj.im.classloader;

import java.io.*;

/**
 * 当一个ClassLoader实例需要加载某个类时，它会试图亲自搜索某个类之前，先把这个任务委托给它的父类加载器，这个过程是由上至下依次检查的，首先由最顶层的类加载器Bootstrap ClassLoader试图加载，如果没加载到，则把任务转交给Extension ClassLoader试图加载，如果也没加载到，则转交给App ClassLoader 进行加载，
 * 如果它也没有加载得到的话，则返回给委托的发起者，由它到指定的文件系统或网络等URL中加载该类。如果它们都没有加载到这个类时，
 * 则抛出ClassNotFoundException异常。否则将这个找到的类生成一个类的定义，并将它加载到内存当中，最后返回这个类在内存中的Class实例对象。
 */
public class ClassLoaderTest extends ClassLoader{
    private String path;

    public static void main(String[] args) {
        //BootStrap ClassLoader，称为启动类加载器，是Java类加载层次中最顶层的类加载器，负责加载JDK中的核心类库
        System.out.println(System.getProperty("sun.boot.class.path"));
        //EtxClassLoader 称为扩展类加载器，负责加载Java的扩展类库，
        // Java 虚拟机的实现会提供一个扩展库目录，该类加载器在此目录里面查找并加载 Java 类。
        // 默认加载JAVA_HOME/jre/lib/ext/目下的所有jar
        System.out.println(System.getProperty("java.ext.dirs"));

        //AppClassLoader 称为系统类加载器，负责加载应用程序classpath目录下的所有jar和class文件
        System.out.println(System.getProperty("java.class.path"));

        ClassLoader cl =  ClassLoaderTest.class.getClassLoader();
        System.out.println(cl.toString());
        System.out.println(cl.getParent().toString());
        System.out.println(cl.getParent().getParent());
        ClassLoader cl2 = Thread.currentThread().getContextClassLoader();
        System.out.println(cl2.toString());

    }

    public ClassLoaderTest(String path){
        this.path=path;
    }

//自定义类加载器，通过继承classLoader并重写findClass
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] bytes = loadClassData(name);
            return defineClass(name, bytes, 0, bytes.length);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private byte[] loadClassData(String name) throws IOException {
        name = path + name + ".class";
        InputStream is = null;
        ByteArrayOutputStream outputStream = null;
        try {
            is = new FileInputStream(new File(name));
            outputStream = new ByteArrayOutputStream();
            int i = 0;
            while ((i = is.read()) != -1) {
                outputStream.write(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (is != null) {
                is.close();
            }
        }

        return outputStream.toByteArray();
    }
}
