import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;


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
            //Новая заметка по умолчанию невыполнена
            this.isCompleted = false;
        }

        //Переопределяем метод toString()
        //Он будет вызван, когда мы попытаемся напечатать объект "Note"
        @Override
        public String toString(){
            String status = isCompleted ? "[x]" : "[]";
            return status + " " + createdAt + " - " + text;
        }
    }

    private ArrayList<Note> notes; //Список всех заметок
    private Scanner scanner; //Для чтения команд
    private final String SAVE_FILE = "notes.txt"; //Имя файла для сохранения

    //Конструктор выполняется при создании объекта менеджера
    public NoteManager(){
        notes = new ArrayList<>(); //Инициализирует пустой список
        scanner = new Scanner(System.in); //Сканер для ввода с клавиатуры
        loadFromFile(); //При запуске загружаем сохранённые заметки (если есть)
    }

    //Метод для загрузки заметок из файла
    private void loadFromFile(){
        File file = new File(SAVE_FILE);
        if(!file.exists()){
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null) {
                // Формат строки в файле: "текст|дата_создания|выполнена"
                // Например: "Купить молоко|31.12.2025 23:59:59|false"
                String[] parts = line.split("\\|"); // Разделяем по символу '|' (экранируем, т.к. | спецсимвол)
                if (parts.length == 3) {            // Убеждаемся, что строка корректна
                    String text = parts[0];
                    String createdAt = parts[1];
                    boolean isCompleted = Boolean.parseBoolean(parts[2]); // Преобразуем "true"/"false" в boolean

                    // Создаём объект Note (но конструктор сам ставит дату создания – нам нужно восстановить старую)
                    // Проще: создадим заметку через конструктор, а потом перезапишем поля.
                    Note note = new Note(text);     // Временно создаст новую дату и isCompleted=false
                    note.createdAt = createdAt;      // Восстанавливаем сохранённую дату
                    note.isCompleted = isCompleted;  // Восстанавливаем статус выполнения
                    notes.add(note);                 // Добавляем в список
                }
            }
            System.out.println("Загружено заметок: " + notes.size());
        } catch (IOException e) {
            // Если произошла ошибка чтения, выводим сообщение, но не прерываем программу.
            System.out.println("Ошибка при загрузке файла: " + e.getMessage());
        }
    }

    // ========== МЕТОД ДЛЯ СОХРАНЕНИЯ ЗАМЕТОК В ФАЙЛ ==========
    private void saveToFile() {
        // PrintWriter удобен для записи строк с автоматическим переводом строки.
        // Используем try-with-resources для автоматического закрытия.
        try (PrintWriter writer = new PrintWriter(new FileWriter(SAVE_FILE))) {
            for (Note note : notes) {
                // Формат: текст|дата_создания|выполнена
                writer.println(note.text + "|" + note.createdAt + "|" + note.isCompleted);
            }
            System.out.println("Заметки сохранены в файл " + SAVE_FILE);
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении файла: " + e.getMessage());
        }
    }

    // ========== МЕТОД ДЛЯ ДОБАВЛЕНИЯ НОВОЙ ЗАМЕТКИ ==========
    private void addNote() {
        System.out.print("Введите текст заметки: ");
        String text = scanner.nextLine();  // Читаем всю строку, которую ввёл пользователь

        // Если пользователь ввёл пустую строку (или только пробелы), можно отменить добавление.
        if (text.trim().isEmpty()) {
            System.out.println("Заметка не может быть пустой!");
            return;
        }

        Note newNote = new Note(text);   // Создаём объект заметки (дата и статус установятся в конструкторе)
        notes.add(newNote);              // Добавляем в список
        System.out.println("Заметка добавлена!");
    }

    // ========== МЕТОД ДЛЯ ПРОСМОТРА ВСЕХ ЗАМЕТОК ==========
    private void listNotes() {
        if (notes.isEmpty()) {           // Если список пуст
            System.out.println("У вас нет заметок.");
            return;
        }

        System.out.println("\n=== ВАШИ ЗАМЕТКИ ===");
        // Цикл с индексом, чтобы выводить номер заметки (для удобства удаления/отметки)
        for (int i = 0; i < notes.size(); i++) {
            // Выводим номер (индекс+1) и саму заметку (через её toString())
            System.out.println((i + 1) + ". " + notes.get(i));
        }
        System.out.println("===================\n");
    }

    // ========== МЕТОД ДЛЯ ОТМЕТКИ ЗАМЕТКИ КАК ВЫПОЛНЕННОЙ ==========
    private void markAsCompleted() {
        if (notes.isEmpty()) {
            System.out.println("Нет заметок для отметки.");
            return;
        }

        listNotes();   // Сначала показываем список с номерами
        System.out.print("Введите номер заметки, которую хотите отметить выполненной: ");
        int index;
        try {
            index = Integer.parseInt(scanner.nextLine()) - 1; // Преобразуем строку в число и переводим в индекс (0-база)
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: нужно ввести число!");
            return;
        }

        // Проверяем, что индекс в пределах списка
        if (index >= 0 && index < notes.size()) {
            Note note = notes.get(index);
            if (note.isCompleted) {
                System.out.println("Эта заметка уже выполнена!");
            } else {
                note.isCompleted = true;   // Меняем флаг
                System.out.println("Заметка \"" + note.text + "\" отмечена как выполненная!");
            }
        } else {
            System.out.println("Неверный номер заметки!");
        }
    }

    // ========== МЕТОД ДЛЯ УДАЛЕНИЯ ЗАМЕТКИ ==========
    private void deleteNote() {
        if (notes.isEmpty()) {
            System.out.println("Нет заметок для удаления.");
            return;
        }

        listNotes();
        System.out.print("Введите номер заметки для удаления: ");
        int index;
        try {
            index = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: нужно ввести число!");
            return;
        }

        if (index >= 0 && index < notes.size()) {
            Note removed = notes.remove(index);   // Удаляем и получаем удалённый объект
            System.out.println("Заметка \"" + removed.text + "\" удалена!");
        } else {
            System.out.println("Неверный номер заметки!");
        }
    }

    // ========== ГЛАВНОЕ МЕНЮ ==========
    private void showMenu() {
        System.out.println("\n========== МЕНЕДЖЕР ЗАМЕТОК ==========");
        System.out.println("1. Добавить заметку");
        System.out.println("2. Показать все заметки");
        System.out.println("3. Отметить заметку как выполненную");
        System.out.println("4. Удалить заметку");
        System.out.println("5. Выйти и сохранить");
        System.out.println("======================================");
        System.out.print("Выберите действие (1-5): ");
    }

    // ========== ОСНОВНОЙ ЦИКЛ РАБОТЫ ПРОГРАММЫ ==========
    public void run() {
        System.out.println("Добро пожаловать в Менеджер заметок!");
        boolean running = true;

        while (running) {
            showMenu();                     // Показываем меню
            String choice = scanner.nextLine(); // Читаем выбор пользователя

            switch (choice) {
                case "1":
                    addNote();
                    break;
                case "2":
                    listNotes();
                    break;
                case "3":
                    markAsCompleted();
                    break;
                case "4":
                    deleteNote();
                    break;
                case "5":
                    System.out.println("Сохраняем и выходим...");
                    saveToFile();           // Сохраняем все заметки в файл
                    running = false;        // Выход из цикла
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова (1-5).");
            }
        }
        scanner.close();   // Закрываем сканер (освобождаем ресурс)
        System.out.println("До свидания!");
    }

    // ========== ТОЧКА ВХОДА В ПРОГРАММУ ==========
    public static void main(String[] args) {
        NoteManager manager = new NoteManager(); // Создаём объект менеджера (загрузит файл)
        manager.run();                           // Запускаем основной цикл
    }
}

