<div align="center">
  <a id="english"></a>
  <h1>Vim stats plugin</h1>
  <p>Vim utility to get stats</p>

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
vimstat/
├── data
│  ├── git_day_commit.txt
│  └── git_hash.txt
├── plugin
│  └── vimstat.vim
├── pom.xml
├── README.md
├── src
│  ├── main
│  │  └── java
│  │     ├── Colors.java
│  │     ├── GitStats.java
│  │     ├── Main.java
│  │     ├── Stats.java
│  │     └── TimeStats.java
│  └── test
│     └── java
│        ├── ColorsTest.java
│        ├── GitStatsTest.java
│        └── TimeStatsTest.java
└── target
   └── vimstat-0.8.7.jar
```

<div align="center">
  <h4>Manual installation</h4>
</div>

* Build plugin by Maven
```
mvn package
```

* Copy the plugin to the  `.vim/` folder:
```
cp -r vimstat/ ~/.vim/pack/my-plugins/start/
```

* Reload Vim or run the command:
```
:source ~/.vim/pack/my-plugins/start/vimstat/plugin/vimstat.vim
```

<div align="center">
  <h4>Plugin uninstallation</h4>
</div>

* To uninstall the plugin, delete its folder:
```
rm -r ~/.vim/pack/my-plugins/start/vimstat/
```

<div align="center">
  <h4>Testing</h4>
</div>

* Running all unit tests
```
mvn test
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
  - per session:         0 h  2 min 35 sec
  - per day:             0 h 27 min 32 sec
  - average per month:   0 h  0 min  1 sec
  =========================================
  =========================================
                Commit stats:
  -----------------------------------------
  - Commits per day: 2
  =========================================
```

<div align="center">
  <h4>Requirements</h4>
</div>
 
* Java installed
* Vim installed
* Maven installed

<div align="center">
  <h4>Compatibility</h4>
</div>
 
* Vim 7.0 and above
* Java 8 and above
* Maven 3 and above

<div align="center">
  <h4>Class hierarchy</h4>
</div>

```mermaid
classDiagram

  class Stats {
    - pathToLong: String
    + Stats(pathToLong: String)
    + writeLong(value: Long )
    + readLong() long 
    + getFileDate(pathToFile: String) LocalDate
    + isFileExists(pathToFile: String) boolean 
  }

  class GitStats {
    - pathToString: String 
    + GitStats(pathToString: String, pathToLong: String)
    + getLastCommitHash() String 
    + writeString(hash: String) 
    + readString() String
  }

  class TimeStats {
    + TimeStats(pathToLong: String)
    + getSessionTime(): long 
    + deleteFile() 
  }

  Stats <|-- GitStats
  Stats <|-- TimeStats
```

<div align="center">
  <h4>Class call hierarchy</h4>
</div>

```mermaid
classDiagram
  
  class vimstat.vim {
  }

  class Main {
  }

  class enum Colors {
  }

  class GitStats {
  }

  class git_hash.txt {
  }

  class git_day_commit.txt {
  }

  class TimeStats {
  }

  class time_session.txt {
  }

  class time_day.txt {
  }

  class time_month.txt {
  }

  class time_yesterday.txt {
  }

  vimstat.vim --|> Main : calls
  Main --|> GitStats : calls
  Main --|> TimeStats : calls
  enum Colors -- Main : use
  enum Colors -- GitStats : use
  enum Colors -- TimeStats : use
  GitStats --|> git_hash.txt : writes / reads
  GitStats --|> git_day_commit.txt : writes / reads
  TimeStats --|> time_session.txt : writes / reads
  TimeStats --|> time_day.txt : writes / reads
  TimeStats --|> time_month.txt : writes / reads
  TimeStats --|> time_yesterday.txt : writes / reads
```

<div align="center">
  <a id="russian"></a>
  <h1>Vim stats plugin</h1>
  <p>Vim-утилита для получения статистики</p>

  [![EN](https://img.shields.io/badge/English-🇬🇧-blue)](#english)
  [![RU](https://img.shields.io/badge/Русский-🇷🇺-red)](#russian)
  ![Stars](https://img.shields.io/github/stars/AlexandrAnatoliev/jvim-plugins.svg?style=flat)
  ![Forks](https://img.shields.io/github/forks/AlexandrAnatoliev/jvim-plugins.svg?style=flat)
  ![GitHub repo size](https://img.shields.io/github/repo-size/AlexandrAnatoliev/jvim-plugins)
</div>

<div align="center">
  <h4>Структура файлов плагина</h4>
</div>

* Плагин устанавливается в соответствующую директорию папки `.vim/`
и создает следующую структуру файлов:

```
vimstat/
├── data
│  ├── git_day_commit.txt
│  └── git_hash.txt
├── plugin
│  └── vimstat.vim
├── pom.xml
├── README.md
├── src
│  ├── main
│  │  └── java
│  │     ├── Colors.java
│  │     ├── GitStats.java
│  │     ├── Main.java
│  │     ├── Stats.java
│  │     └── TimeStats.java
│  └── test
│     └── java
│        ├── ColorsTest.java
│        ├── GitStatsTest.java
│        └── TimeStatsTest.java
└── target
   └── vimstat-0.8.7.jar
```

<div align="center">
  <h4>Ручная установка</h4>
</div>

* Соберите плагин с помощью maven:
```
mvn package
```

* Скопируйте плагин в папку `.vim/`:
```
cp -r vimstat/ ~/.vim/pack/my-plugins/start/
```

* Перезагрузите Vim или выполните команду:
```
:source ~/.vim/pack/my-plugins/start/vimstat/plugin/vimstat.vim
```

<div align="center">
  <h4>Удаление плагина</h4>
</div>

* Чтобы удалить плагин, удалите его папку:
```
rm -r ~/.vim/pack/my-plugins/start/vimstat/
```

<div align="center">
  <h4>Тестирование</h4>
</div>

* Запуск всех тестов
```
mvn test
```

<div align="center">
  <h4>Использование</h4>
</div>

* Воспользуйтесь Vim для редактирования файла:
```
$ vim example.md
```

* После закрытия Vim, вы увидите:
```
  =========================================
                Vim uptime:
  -----------------------------------------
  - per session:         0 h  2 min 35 sec
  - per day:             0 h 27 min 32 sec
  - average per month:   0 h  0 min  1 sec
  =========================================
  =========================================
                Commit stats:
  -----------------------------------------
  - Commits per day: 2
  =========================================
```

<div align="center">
  <h4>Требования</h4>
</div>
 
* Установленная Java 
* Установленный Vim 
* Установленный Maven

<div align="center">
  <h4>Совместимость</h4>
</div>
 
* Vim 7.0 и выше
* Java 8 и выше
* Maven 3 и выше

<div align="center">
  <h4>Иерархия классов</h4>
</div>

```mermaid
classDiagram

  class Stats {
    - pathToLong: String
    + Stats(pathToLong: String)
    + writeLong(value: Long )
    + readLong() long 
    + getFileDate(pathToFile: String) LocalDate
    + isFileExists(pathToFile: String) boolean 
  }

  class GitStats {
    - pathToString: String 
    + GitStats(pathToString: String, pathToLong: String)
    + getLastCommitHash() String 
    + writeString(hash: String) 
    + readString() String
  }

  class TimeStats {
    + TimeStats(pathToLong: String)
    + getSessionTime(): long 
    + deleteFile() 
  }

  Stats <|-- GitStats
  Stats <|-- TimeStats
```

<div align="center">
  <h4>Иерархия вызовов классов</h4>
</div>

```mermaid
classDiagram
  
  class vimstat.vim {
  }

  class Main {
  }

  class enum Colors {
  }

  class GitStats {
  }

  class git_hash.txt {
  }

  class git_day_commit.txt {
  }

  class TimeStats {
  }

  class time_session.txt {
  }

  class time_day.txt {
  }

  class time_month.txt {
  }

  class time_yesterday.txt {
  }

  vimstat.vim --|> Main : calls
  Main --|> GitStats : calls
  Main --|> TimeStats : calls
  enum Colors -- Main : use
  enum Colors -- GitStats : use
  enum Colors -- TimeStats : use
  GitStats --|> git_hash.txt : writes / reads
  GitStats --|> git_day_commit.txt : writes / reads
  TimeStats --|> time_session.txt : writes / reads
  TimeStats --|> time_day.txt : writes / reads
  TimeStats --|> time_month.txt : writes / reads
  TimeStats --|> time_yesterday.txt : writes / reads
```
