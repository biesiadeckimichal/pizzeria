package pl.sdacademy.serialization;

import java.io.*;

// nie skonczony kod

public class Main {
    public static void main(String[] args) {
        SerializationSampleObject obj1 = new SerializationSampleObject();
        obj1.setStringValue("Non-Transient String");
        obj1.setTransientStringValue("Transient String");
        obj1.setIntValue(123);
        obj1.setTransientIntValue(987);
        System.out.println(obj1);

        System.out.println("Performing serialization");
        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(
                             new FileOutputStream("SerializationSample.ser"))) {
            outputStream.writeObject(obj1);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Object Serialized");

        SerializationSampleObject obj2 = new SerializationSampleObject();
        System.out.println("Performing deserialization");
        try (ObjectInputStream inputStream =
                new ObjectInputStream(
                        new FileInputStream("SerializationSample.ser"))) {
            Object o = inputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Object deserialized");
    }


}
