package innopolis.igor;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
//        People people = new People();
//        people.setAge(24);
//        people.setName("Anna");
//        people.setSalary(1000000);
//        MyXml.savePeopleToXml(people);
//
//        People people2 = (People)MyXml.loadPeopleFromXml("file.xml");
//        System.out.println(people2);

//        saveUrl("./People.jar","https://github.com/IgorTurist/SerializationAndReflection/blob/master/src/innopolis/People.jar");
        JarClassLoader loader = new JarClassLoader();
        loader.setJarFile("c:\\Users\\igor\\IdeaProjects\\SerializationAndReflection\\out\\artifacts\\Animal.jar");
        Class<?> class_obj = null;
        try {
            class_obj = loader.loadClass("Animal");
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        if(class_obj == null) {
            System.out.println("Program can't get class People from class loader");
            return;
        }

        Object obj = null;
        try {
            obj = class_obj.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        MyXml.savePeopleToXml(obj);
    }

    public static void saveUrl(final String filename, final String urlString){
        try(BufferedInputStream in = new BufferedInputStream(new URL(urlString).openStream());
            FileOutputStream fout = new FileOutputStream(filename)) {

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
