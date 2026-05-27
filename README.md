   ПРОЕКТ: МЕНЕДЖЕР ЗАМЕТОК (TO-DO LIST) / NOTE MANAGER (TO-DO LIST)

   Назначение (Русский):
     Консольное приложение для управления списком задач (заметок).
     Позволяет добавлять, просматривать, отмечать выполненными и удалять заметки.
     Все данные автоматически сохраняются в файл notes.txt и загружаются при запуске.

   Основные возможности:
     - Добавление заметки (текст + автоматическая дата/время создания)
     - Просмотр всех заметок с номерами, статусом, датой и текстом
     - Отметка заметки как выполненной
     - Удаление заметки
     - Автосохранение в файл при выходе
     - Автозагрузка из файла при старте

   Как запустить:
     1. Убедитесь, что установлен JDK (8 или новее).
     2. Сохраните этот файл как NoteManager.java.
     3. Откройте терминал в папке с файлом.
     4. Скомпилируйте: javac NoteManager.java
     5. Запустите:      java NoteManager
     6. Следуйте инструкциям в консоли (выбор действия цифрами 1-5).

   Формат файла сохранения (notes.txt):
     Каждая строка содержит одну заметку в формате:
     текст|дата_создания|статус
     Пример: Купить молоко|31.12.2025 23:59:59|false

   Структура программы:
     - Внутренний класс Note – представляет одну заметку.
     - Класс NoteManager – управляет списком, меню, вводом/выводом, файлами.
     - Метод main – точка входа.

   Возможные улучшения (задания для самостоятельной работы):
     - Редактирование существующих заметок
     - Установка дедлайнов (срока выполнения)
     - Сортировка по дате или статусу
     - Поиск по ключевым словам
     - Категории / теги для заметок
     - Графический интерфейс (JavaFX/Swing)

   ENGLISH DESCRIPTION

   Purpose:
     A console task manager (to-do list) that allows you to add, view,
     mark as completed, and delete notes. All data is automatically saved
     to notes.txt and loaded on startup.

   Features:
     - Add a note (text + automatic creation date/time)
     - View all notes with numbers, status, date and text
     - Mark a note as completed
     - Delete a note
     - Auto-save to file on exit
     - Auto-load from file on startup

   How to run:
     1. Install JDK 8+.
     2. Save this file as NoteManager.java.
     3. Open terminal in the file's folder.
     4. Compile: javac NoteManager.java
     5. Run:      java NoteManager
     6. Follow the console menu (enter numbers 1-5).

   Save file format (notes.txt):
     Each line: text|creation_date|status
     Example: Buy milk|31.12.2025 23:59:59|false

   Project structure:
     - Inner class Note – represents a single note.
     - Class NoteManager – manages the list, menu, I/O, files.
     - main method – entry point.

   Possible improvements:
     - Edit existing notes
     - Set deadlines
     - Sort by date or status
     - Keyword search
     - Categories / tags
     - GUI version (JavaFX/Swing)
