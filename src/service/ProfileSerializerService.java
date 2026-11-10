package service;

import model.ResumeData;
import java.io.*;

public class ProfileSerializerService {
    public static void saveProfile(ResumeData data, File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        }
    }

    public static ResumeData loadProfile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ResumeData) ois.readObject();
        }
    }
}
