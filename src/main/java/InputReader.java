import java.io.File;
import java.util.Scanner;

/**
 * Класс для чтения и обработки вводных данных
 */
public class InputReader {

    /**
     * @param PATH_OF_DIRECTORY_PARENT - путь к папке, в которой находится приложение и идёт поиск файлов
     * @param PIPE_FILE_NAME - наименование Pipe-файла
     * @param PATH_OF_DIRECTORY - абсолютный путь
     */
    public final static String PATH_OF_DIRECTORY_PARENT = new File(new File("").getAbsolutePath()).getParent();
    public final static String PIPE_FILE_NAME = "pipe.txt";
    public final static String PATH_OF_DIRECTORY = new File("").getAbsolutePath();

    /**
     * метод обработки ввода пользователя и действий архивации/деархивации в зависимости от ввода
     */
    public void command() {
        try (Scanner in = new Scanner(System.in)) {

            String readInputLine = in.nextLine();
            boolean isArchived = readInputLine.contains("archived");
            boolean isPipeFile = readInputLine.contains("./" + PIPE_FILE_NAME) || readInputLine.contains(".\\" + PIPE_FILE_NAME);

            if (isArchived) {
                readInputLine = readInputLine.replace("> archived", "");
                String[] arrayFilesPaths = readInputLine.split("\\s+");
                arrayFilesPaths = fileSystemSeparator(arrayFilesPaths);
                new Archiving().archivedFilesAndDirectory(arrayFilesPaths);
            } else if (isPipeFile) {
                String[] arrayFilesPaths = readInputLine.split("\\s+");
                arrayFilesPaths = fileSystemSeparator(arrayFilesPaths);
                new Unzip().unzipFromPipeFile(arrayFilesPaths);
            } else {
                System.out.println("Неизвестная команда");
            }
        }
    }

    /**
     * метод для преобразования пути к файлам для различных OS
     *
     * @param arrayFilesPaths - набор путей к файлам и папкам из вводных данных
     * @return преобразованный набор путей к файлам и папкам
     * @see InputReader command
     */
    public String[] fileSystemSeparator(String[] arrayFilesPaths) {
        for (int i = 0; i < arrayFilesPaths.length; i++) {
            if (arrayFilesPaths[i].startsWith("./") || arrayFilesPaths[i].startsWith(".\\")) {
                if (PATH_OF_DIRECTORY_PARENT.contains("\\")) {
                    arrayFilesPaths[i] = PATH_OF_DIRECTORY_PARENT.concat("\\")
                            .concat(arrayFilesPaths[i].substring(2));
                } else {
                    arrayFilesPaths[i] = PATH_OF_DIRECTORY_PARENT.concat("/")
                            .concat(arrayFilesPaths[i].substring(2));
                }
            } else if (arrayFilesPaths[i].startsWith("[A-Z]:")) {
                arrayFilesPaths[i] = arrayFilesPaths[i];
            }
        }
        return arrayFilesPaths;
    }
}