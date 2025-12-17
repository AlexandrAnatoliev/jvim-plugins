<div align="center">

  <a id="english"></a>
  <h1>Simple Vim Working Time Measurement Plugin</h1>
  <p>Designed for self-monitoring and productivity</p>

  [![EN](https://img.shields.io/badge/English-🇬🇧-blue)](#english)
  [![RU](https://img.shields.io/badge/Русский-🇷🇺-red)](#russian)
  ![Stars](https://img.shields.io/github/stars/AlexandrAnatoliev/jvim-plugins.svg?style=flat)
  ![Forks](https://img.shields.io/github/forks/AlexandrAnatoliev/jvim-plugins.svg?style=flat)
  ![GitHub repo size](https://img.shields.io/github/repo-size/AlexandrAnatoliev/jvim-plugins)

</div>

<div align="center">
  <h4>Plugin file structure</h4>
</div>

* The plugin installs into the relevant directory of the `.vim/`
folder and creates the following file structure:

```
~/.vim/
└── pack/
    └── my-plugins/
        └── start/
            └── timer/
                ├── bin/
                │   ├── main/
                │   │   ├── Main.class
                │   │   └── Timer.class
                │   └── test/
                │       └── TimerTest.class
                ├── data/
                │   ├── jvim_day_time.txt
                │   ├── jvim_month_time.txt
                │   ├── jvim_session_time.txt
                │   └── jvim_yesterday_time.txt
                ├── plugin/
                │   └── jvim_timer.vim
                └── src/
                    ├── main/
                    │   └── java/
                    │       ├── Main.java
                    │       └── Timer.java
                    └── test/
                        └── java/
                            └── TimerTest.java
```

<div align="center">
  <h4>Manual installation</h4>
</div>

* Copy the plugin to the  `.vim/` folder:
```
cp -r timer/ ~/.vim/pack/my-plugins/start/
```

* Navigate to the plugin's root directory:
```
cd ~/.vim/pack/my-plugins/start/timer/
```

* Compile Java files:
```
javac -d bin/main/ src/main/java/*
```

* Reload Vim or run the command:
```
:source ~/.vim/pack/my-plugins/start/timer/plugin/jvim_timer.vim
```

<div align="center">
  <h4>Plugin uninstallation</h4>
</div>

* To uninstall the plugin, delete its folder:
```
rm -r ~/.vim/pack/my-plugins/start/timer/
```

<div align="center">
  <h4>Testing</h4>
</div>

* Check the path to JUnit using command: 
```
dpkg -L junit5 | grep junit-jupiter-api
```
```
dpkg -L junit5 | grep junit-platform-console-standalone
```

* Building tests with JUnit dependencies:
```
javac -d bin/test/ -cp "bin/main:/usr/share/java/junit-jupiter-api-5.10.1.jar:/usr/share/java/junit-platform-console-standalone-1.9.1.jar" src/test/java/*.java
```

* Running all unit tests
```
java -cp "bin/main:bin/test:/usr/share/java/junit-jupiter-api-5.10.1.jar:/usr/share/java/junit-platform-console-standalone-1.9.1.jar" org.junit.platform.console.ConsoleLauncher --scan-classpath --class-path bin/test
```

<div align="center">
  <h4>Using</h4>
</div>

* Use Vim to edit the file:
```
$ vim example.md
```

* After closing Vim, you will see:
```
  =========================================
                Vim uptime:
  -----------------------------------------
  - per session:         0 h  0 min  8 sec
  - per day:             0 h 19 min 59 sec
  - average per month:   0 h 57 min 14 sec
  =========================================
```
The program output will be green if the runtime is greater then average, 
otherwise red.

<div align="center">
  <h4>Requirements</h4>
</div>
 
* Java installed
* Vim installed
* .class files built into designated folder
* JUnit 5 installed (optional)

<div align="center">
  <h4>Compatibility</h4>
</div>
 
* Vim 7.0 and above
* Java 8 and above

<div align="center">
  <h4>Class call hierarchy</h4>
</div>

```mermaid
classDiagram
  direction LR
  
  class jvim_timer.vim {
    + StartTimer()
    + StopTimer()
  }

  class Main {
    - SESSION_FILE_PATH: String 
      = "/.vim/pack/my-plugins/start/timer/data/jvim_session_time.txt"
    - DAY_FILE_PATH: String 
      = "/.vim/pack/my-plugins/start/timer/data/jvim_day_time.txt"
    - MONTH_FILE_PATH: String  
      = "/.vim/pack/my-plugins/start/timer/data/jvim_month_time.txt"
    - YESTERDAY_FILE_PATH: String 
      = "/.vim/pack/my-plugins/start/timer/data/jvim_yesterday_time.txt";
    + start(): void
    + stop(): void
  }

  class Timer {
    - pathToFile: String
    + Timer(pathToFile: String)
    + writeToFile(value: Long): void
    + readFromFile(): long
    + getSessionTime(): long
    + deleteFile(): void
    + fileIsNotExist(): boolean
    + getFileDate(): LocalDate
  }

  class jvim_start_time.txt {
    + startTime: String 
  }

  class jvim_day_time.txt {
    + dayTime: String
  }

  class jvim_month_time.txt {
    + monthTime: String
  }

  class jvim_yesterday_time.txt {
    + yesterdayTime: String
  }

  jvim_timer.vim --|> Main : calls
  Main --|> Timer : calls
  Timer --|> jvim_start_time.txt : reads/writes
  Timer --|> jvim_day_time.txt : reads/writes
  Timer --|> jvim_month_time.txt : reads/writes
  Timer --|> jvim_yesterday_time.txt : reads/writes
```

<div align="center">

  <a id="russian"></a>
  <h1>Простой плагин для измерения времени работы Vim</h1>
  <p>Нужен для самоконтроля и производительности</p>

  [![EN](https://img.shields.io/badge/English-🇬🇧-blue)](#english)
  [![RU](https://img.shields.io/badge/Русский-🇷🇺-red)](#russian)
  ![Stars](https://img.shields.io/github/stars/AlexandrAnatoliev/jvim-plugins.svg?style=flat)
  ![Forks](https://img.shields.io/github/forks/AlexandrAnatoliev/jvim-plugins.svg?style=flat)
  ![GitHub repo size](https://img.shields.io/github/repo-size/AlexandrAnatoliev/jvim-plugins)

</div>

<div align="center">
  <h4>Файловая структура плагина</h4>
</div>

* Плагин устанавливается в соответствующую директорию папки `.vim/`
и образует следующую файловую структуру:

```
~/.vim/
└── pack/
    └── my-plugins/
        └── start/
            └── timer/
                ├── bin/
                │   ├── main/
                │   │   ├── Main.class
                │   │   └── Timer.class
                │   └── test/
                │       └── TimerTest.class
                ├── data/
                │   ├── jvim_day_time.txt
                │   ├── jvim_month_time.txt
                │   ├── jvim_session_time.txt
                │   └── jvim_yesterday_time.txt
                ├── plugin/
                │   └── jvim_timer.vim
                └── src/
                    ├── main/
                    │   └── java/
                    │       ├── Main.java
                    │       └── Timer.java
                    └── test/
                        └── java/
                            └── TimerTest.java
```

<div align="center">
  <h4>Установка вручную</h4>
</div>

* Скопировать плагин в .vim/ директорию:
```
cp -r timer/ ~/.vim/pack/my-plugins/start/
```

* Перейти в корневой каталог плагина:
```
cd ~/.vim/pack/my-plugins/start/timer/
```

* Скомпилировать Java файлы:
```
javac -d bin/main/ src/main/java/*
```

* Перезагрузить Vim или выполнить команду:
```
:source ~/.vim/pack/my-plugins/start/timer/plugin/jvim_timer.vim
```

<div align="center">
  <h4>Удаление плагина</h4>
</div>

* Чтобы удалить плагин, удалите директорию с плагином:
```
rm -r ~/.vim/pack/my-plugins/start/timer/
```

<div align="center">
  <h4>Тестирование</h4>
</div>

* Проверьте путь до классов JUnit командой: 
```
dpkg -L junit5 | grep junit-jupiter-api
```
```
dpkg -L junit5 | grep junit-platform-console-standalone
```

* Компиляция тестов с зависимостями JUnit:
```
javac -d bin/test/ -cp "bin/main:/usr/share/java/junit-jupiter-api-5.10.1.jar:/usr/share/java/junit-platform-console-standalone-1.9.1.jar" src/test/java/*.java
```

* Запуск всех unit-тестов
```
java -cp "bin/main:bin/test:/usr/share/java/junit-jupiter-api-5.10.1.jar:/usr/share/java/junit-platform-console-standalone-1.9.1.jar" org.junit.platform.console.ConsoleLauncher --scan-classpath --class-path bin/test
```

<div align="center">
  <h4>Использование</h4>
</div>

* Воспользоваться Vim для редактирования файла:
```
$ vim example.md
```

* По окончании работы и закрытия Vim будет выведено:
```
  =========================================
                Vim uptime:
  -----------------------------------------
  - per session:         0 h  0 min  8 sec
  - per day:             0 h 19 min 59 sec
  - average per month:   0 h 57 min 14 sec
  =========================================
```
Вывод программы будет зеленым цветом, если время работы будет больше
  среднего, иначе - красным.

<div align="center">
  <h4>Требования</h4>
</div>
 
* Установленная Java
* Установленный Vim
* Установленный JUnit 5 (опционально)
* Скомпилированные .class файлы в указанной директории

<div align="center">
  <h4>Совместимость</h4>
</div>
 
* Vim 7.0 и выше
* Java 8 и выше

<div align="center">
  <h4>Структура вызовов классов</h4>
</div>

```mermaid
classDiagram
  direction LR
  
  class jvim_timer.vim {
    + StartTimer()
    + StopTimer()
  }

  class Main {
    - SESSION_FILE_PATH: String 
      = "/.vim/pack/my-plugins/start/timer/data/jvim_session_time.txt"
    - DAY_FILE_PATH: String 
      = "/.vim/pack/my-plugins/start/timer/data/jvim_day_time.txt"
    - MONTH_FILE_PATH: String  
      = "/.vim/pack/my-plugins/start/timer/data/jvim_month_time.txt"
    - YESTERDAY_FILE_PATH: String 
      = "/.vim/pack/my-plugins/start/timer/data/jvim_yesterday_time.txt";
    + start(): void
    + stop(): void
  }

  class Timer {
    - pathToFile: String
    + Timer(pathToFile: String)
    + writeToFile(value: Long): void
    + readFromFile(): long
    + getSessionTime(): long
    + deleteFile(): void
    + fileIsNotExist(): boolean
    + getFileDate(): LocalDate
  }

  class jvim_start_time.txt {
    + startTime: String 
  }

  class jvim_day_time.txt {
    + dayTime: String
  }

  class jvim_month_time.txt {
    + monthTime: String
  }

  class jvim_yesterday_time.txt {
    + yesterdayTime: String
  }

  jvim_timer.vim --|> Main : calls
  Main --|> Timer : calls
  Timer --|> jvim_start_time.txt : reads/writes
  Timer --|> jvim_day_time.txt : reads/writes
  Timer --|> jvim_month_time.txt : reads/writes
  Timer --|> jvim_yesterday_time.txt : reads/writes
```
