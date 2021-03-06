package innopolis.igor;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by igor on 15.02.2017.
 */
public class JarClassLoader extends ClassLoader {
    public String getJarFile() {
        return jarFile;
    }

    public void setJarFile(String jarFile) {
        this.jarFile = jarFile;
    }

    private String jarFile = ""; //Path to the jar file
    private Hashtable classes = new Hashtable(); //used to cache already defined classes

    public JarClassLoader() {
        super(JarClassLoader.class.getClassLoader()); //calls the parent class loader's constructor
    }

    public Class loadClass(String className) throws ClassNotFoundException {
        return findClass(className);
    }

    public Class findClass(String className) {
        byte classByte[];
        Class result = null;

        result = (Class) classes.get(className); //checks in cached classes
        if (result != null) {
            return result;
        }

        try {
            return findSystemClass(className);
        } catch (Exception e) {
        }

        try {
            JarFile jar = new JarFile(jarFile);
            JarEntry entry = jar.getJarEntry(
                    "Users/igor/IdeaProjects/SerializationAndReflection/out/production/SerializationAndReflection/innopolis/igor/Animal.class");
            if(entry == null)
                throw new Exception("Null entry in jar file.");
            InputStream is = jar.getInputStream(entry);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int nextValue = is.read();
            while (-1 != nextValue) {
                byteStream.write(nextValue);
                nextValue = is.read();
            }

            classByte = byteStream.toByteArray();
            result = defineClass("innopolis.igor." + className, classByte, 0, classByte.length, null);
            classes.put("innopolis.igor." + className, result);
            return result;
        } catch (Exception e) {
            System.out.println("ERROR:" + e.getMessage());
            return null;
        }
    }

}