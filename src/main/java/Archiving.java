import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * класс для архивации данных
 */
public class Archiving {
    /**
     * метод для архивации файлов
     * @param pathToFile - путь к файлу
     */
    private void archivingFile(String pathToFile) {
        try (FileOutputStream fos = new FileOutputStream(pathToFile + ".zip");
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            File fileToZip = new File(pathToFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            zipOut.close();
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл по указанному пути не найден!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * метод для архивации папок
     * @param pathToDirectory - путь к папке
     */
    private void archivingDirectory(String pathToDirectory) {
        try (FileOutputStream fos = new FileOutputStream(pathToDirectory + ".zip");
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            File fileToZip = new File(pathToDirectory);
            zipFileInDirectory(fileToZip, fileToZip.getName(), zipOut);
        } catch (FileNotFoundException e) {
            System.out.println("Папка не найдена по указанному пути!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * метод для архивирования файлов, находящихся внутри папки
     * @param fileToZip - архивирование файла
     * @param fileName - наименование файла
     * @param zipOut - запись файла в архив потоком
     */
    private void zipFileInDirectory(File fileToZip, String fileName, ZipOutputStream zipOut) {
        if (fileToZip.isDirectory()) {
            File[] directoryContent = fileToZip.listFiles();
            assert directoryContent != null;
            for (File contentFile : directoryContent) {
                zipFileInDirectory(contentFile, fileName + contentFile.getName(), zipOut);
            }
            return;
        }
        try (FileInputStream fis = new FileInputStream(fileToZip)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Директория пуста");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * метод для архивации файлов и папок
     * @param pathToFiles - набор путей к файлам из вводных данных
     * @see InputReader command
     *
     */
    public void archivedFilesAndDirectory(String[] pathToFiles) {
        for (String path : pathToFiles) {
            File file = new File(path);
            if (file.isDirectory()) {
                archivingDirectory(path);
            } else if (file.isFile()) {
                archivingFile(path);
            }
        }
        createPipeFile(pathToFiles);
    }

    /**
     * создание Pipe-файла с информацией о заархивированных файлах и папках
     * @param pathToFiles - набор путей к файлам из вводных данных
     * @see InputReader command
     */
    private void createPipeFile(String[] pathToFiles) {
        File pipe = new File(InputReader.PATH_OF_DIRECTORY, InputReader.PIPE_FILE_NAME);
        try (FileWriter fileWriter = new FileWriter(pipe)) {
            for (String s : pathToFiles) {
                fileWriter.write(s + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
