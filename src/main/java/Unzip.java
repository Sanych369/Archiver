import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Класс для деархивации заархивированных файлов
 *
 * @author Sanych
 */
public class Unzip {

    /**
     * метод, для деархивации файлов, указанных в Pipe-файле
     * получает пути архивированных файлов из Pipe-файла и проводит деархивацию в каталог,
     * где находится Pipe-файл
     */
    public void unzipFromPipeFile(String[] pathsFromFile) {
        pathsFromFile = readPipeFile();
        for (String s : pathsFromFile) {
            String pathFileFromZip = s + ".zip";
            File destinationFolder = new File(InputReader.PATH_OF_DIRECTORY);
            byte[] buffer = new byte[1024];

            try {
                ZipInputStream zis = new ZipInputStream(new FileInputStream(pathFileFromZip));
                ZipEntry zipEntry = zis.getNextEntry();

                while (zipEntry != null) {
                    File newFile = newFile(destinationFolder, zipEntry);
                    if (zipEntry.isDirectory()) {
                        if (!newFile.isDirectory() && !newFile.mkdirs()) {
                            throw new IOException("Ошибка создания каталога " + newFile);
                        }
                    } else {
                        File parent = newFile.getParentFile();
                        if (!parent.isDirectory() && !parent.mkdirs()) {
                            throw new IOException("Ошибка создания каталога " + parent);
                        }

                        FileOutputStream fos = new FileOutputStream(newFile);
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                    }
                    zipEntry = zis.getNextEntry();
                }
                zis.closeEntry();
                zis.close();
            } catch (FileNotFoundException e) {
                System.out.println("PIPE-файл не найден");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * метод для создания файлов
     *
     * @param destinationDir - папка назначения для деархивированных данных
     * @param zipEntry       - класс, предоставляющий ZIP-архив и средства работы с ним
     * @return - деархивированный файл
     * @throws IOException
     * @see ZipEntry
     */
    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destinationFile = new File(destinationDir, zipEntry.getName());

        String destinationDirPath = destinationDir.getCanonicalPath();
        String destinationFilePath = destinationFile.getCanonicalPath();

        if (!destinationFilePath.startsWith(destinationDirPath + File.separator)) {
            throw new IOException("Каталог назначения неверен" + zipEntry.getName());
        }
        return destinationFile;
    }

    /**
     * метод для чтения Pipe-файла
     *
     * @return - массив путей для архивированных файлов из Pipe-файла
     */
    public String[] readPipeFile() {
        ArrayList<String> pathsFromPipe = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(InputReader.PATH_OF_DIRECTORY + "\\pipe.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String readPathsFilesFromPipeFile = bufferedReader.readLine();
            while (readPathsFilesFromPipeFile != null) {
                pathsFromPipe.add(readPathsFilesFromPipeFile);
                readPathsFilesFromPipeFile = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathsFromPipe.toArray(new String[0]);
    }
}
