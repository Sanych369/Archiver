# Archiver
Описание задачи:
Написать программу для архивации и деархивации файлов.
Должна принимать входными параметрами файлы и/или директории (разделение пробелом), которые необходимо заархивировать.
На выходе (pipe) должен формироваться файл, содержащий всю информацию о входных файлах.

Например:
./archiver ./test.file ./dir1 ./dir2 ./test2.file > archived

Для деархивации необходимо передать содержимое такого файла в pipe, не указывая входных файлов.
Файлы должны распаковываться в текущую директорию, откуда выполняется программа.

Например:
cat ./archived | ./archiver

Нужно поддержать работу с linux и macos.

Решение передать в виде ссылки на github, сборка maven или gradle, java версии 11 или 8 по желанию. В readme.md описать процесс сборки и запуска программы.

Неплохим бонусом к решению будет реализация сжатия файлов в результате работы программы.

Оцениваться будут следующие параметры:
 - архитектура выбранного решения
 - оформление и чистота кода, необходимые комментарии и javadoc
 - unit тестирование
 
 Руководство по запуску:
 1. С командной строки проходим полный путь до jar-файла и вызываем команду java -jar имя_файла.jar(например: C:\Users\User\Desktop>java -jar ZipArchiver.jar).
 Далее, требуется ввод путей файлов, которые необходимо заархивировать и указывается команда > archived(например: C:\Users\User\Desktop>file1 C:\Users\User\Desktop>file2 ./file3 > archived)
 2. Значение .\ или ./ (в зависимости от типа системы) возвращает к родительскому каталогу.
 3. Ввод должен оканчиваться "> archived".
 4. Путь к файлу - должен быть полным(с расширением файла), например:  C:\Users\User\Desktop\file.docx
 5. Пути к разным файлам должны быть разделены пробелами
 6. Ошибка из моей командной строки - ошибка кодировки. На вход принимаются только файлы, которые названы. Если запустить программу с IDE - проблем с кодировкой не было.
 7. При архивации по пути, на котором мы запустили наше приложение в командной строке (см. 1), появится текстовый файл с названием pipe.txt, который будет содержать в себе путизаархивированных файлов ((например: C:\Users\User\Desktop>java -jar ZipArchiver.jar - здесь файл Pipe создастся по пути C:\Users\User\Desktop). 
 8. Для деархивации использовать команду ./pipe.txt или .\pipe.txt(в зависимости от типа системы). Дополнительно ничего вводить не нужно
 9. Распаковка пройдёт в каталог, где находится pipe-файл. Поэтому необходим использовать путь каталога, в котором находится JAR-файл.
 10. На ввод принимается только 1 команда, после чего, приложение завершает работу.
 
