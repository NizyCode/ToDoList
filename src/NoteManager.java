import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;



public class NoteManager {

    //Заметка
    static class Note{
        String text; //Текст заметки
        String createdAt; //Дата и время создания
        boolean isCompleted; //Флаг выполнена ли заметка

        Note(String text){
            this.text = text;
            //Берём дату и время с ПК и превращаем дату в строку
            this.createdAt = LocalDateTime.now().format((DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
            //Новая заметка изначально всегда невыполнена
            this.isCompleted = false;
        }
    }
}
