import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.nio.file.Files.delete;

public class Main {
    public static void main(String[] args) throws IOException {

        saveGame("g:/Мой диск/Games/savegames/save1.dat",
                new GameProgress(94, 10, 2, 254.32));
        saveGame("g:/Мой диск/Games/savegames/save2.dat",
                new GameProgress(99, 5, 3, 301.5));
        saveGame("g:/Мой диск/Games/savegames/save3.dat",
                new GameProgress(90, 7, 4, 399));

        File dir = new File("g:/Мой диск/Games/savegames");
        File[] listOfFiles = dir.listFiles();
        assert listOfFiles != null;
        zipFiles("g:/Мой диск/Games/savegames/zipGame.zip", listOfFiles);
        deleteDataFile(listOfFiles);
    }

    private static void deleteDataFile(File[] listOfFiles) {
        if (listOfFiles == null) {
            return;
        }
        for (File fileName : listOfFiles) {
            try {
                delete(fileName.toPath());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void zipFiles(String zipName, File[] fileNames) {
        if (fileNames == null) {
            return;
        }
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipName))) {
            for (File fileName : fileNames) {
                try (FileInputStream fis = new FileInputStream(fileName)) {
                    ZipEntry entry = new ZipEntry(String.valueOf(fileName));
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()]; // считываем содержимое файла в массив byte
                    zout.write(buffer); // добавляем содержимое к архиву
                    zout.closeEntry(); // закрываем текущую запись для новой записи
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void saveGame(String path, GameProgress gameProgress) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(gameProgress); // запишем экземпляр класса в файл
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}