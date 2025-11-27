<div align="center">

  <a id="english"></a>
  <h1>Simple Vim Plugins In Java</h1>
  <p>Created for educational purposes to test interaction
  between Vim and Java</p>

  [![EN](https://img.shields.io/badge/English-🇬🇧-blue)](#english)
  [![RU](https://img.shields.io/badge/Русский-🇷🇺-red)](#russian)
  ![Version 0.6.4](https://img.shields.io/badge/Version-0.6.4-orange.svg)
  ![Stars](https://img.shields.io/github/stars/AlexandrAnatoliev/jvim-plugins.svg?style=flat)
  ![Forks](https://img.shields.io/github/forks/AlexandrAnatoliev/jvim-plugins.svg?style=flat)
  ![GitHub repo size](https://img.shields.io/github/repo-size/AlexandrAnatoliev/jvim-plugins)
</div>

  > **Author:** Alexandr Anatoliev

  > **GitHub:** [AlexandrAnatoliev](https://github.com/AlexandrAnatoliev)

---

<div align="center">
  <h2>Plugin Installation</h2>
</div>

* Clone the plugins repository:
```
git clone https://github.com/AlexandrAnatoliev/jvim-plugins 
```

* Navigate to the root repository:
```
cd jvim-plugins/
```

* Make the scripts executable:
```
chmod +x scripts/*.sh
```

Install the plugin using the script:
* with test execution:
```
./scripts/install_plugin.sh [plugin]
```
* without running tests:
```
./scripts/install_plugin.sh [plugin] --no-test
```

* When installing a plugin, it is installed in appropriate Vim directory. 
```
~/.vim/
└── pack/
    └── my-plugins/
        └── start/
            └── jvim-example-plugin/
```

* Reload Vim or execute the command:

```
:source ~/.vim/pack/my-plugins/start/jvim-example-plugin/plugin/jvim_example_plugin.vim
```

---

<div align="center">
  <h2>Plugin Uninstallation</h2>
</div>

* To uninstall the plugin using the script:
```
./scripts/uninstall_plugin.sh [plugin]
```

---

<div align="center">
  <h2>Files Structure</h2>
</div>

```
jvim-plugins 
├── pomodoro
├── README.md
└── scripts
    ├── build.sh
    ├── build_and_run_tests.sh
    ├── check_jdk.sh
    ├── check_junit.sh
    ├── check_vim.sh
    ├── compile.sh
    ├── compile_tests.sh
    ├── copy_plugin_to_vim.sh
    ├── install_plugin.sh
    ├── run_tests.sh
    └── uninstall_plugin.sh
```
 
---

<div align="center">
  <h2>Script Usage</h2>
</div>

The `install_plugin.sh [plugin]` script runs the following scripts sequentially:
* `build.sh [plugin]` - builds plugin Java files;
* `build_and_run_tests.sh [plugin]` - runns JUnit tests;
* `copy_plugin_to_vim.sh [plugin]` - copies the build plugin to the appropriate 
Vim directory;

The `install_plugin.sh [plugin] --no-test` script runs without testing:
* `build.sh [plugin]`
* `copy_plugin_to_vim.sh [plugin]`

* Run the script for automatic plugin build:
```
./scripts/build.sh [plugin]
```

This script runs:
* Checks if Vim is installed
```
./scripts/check_vim.sh
```

* Checks if JDK is installed
```
./scripts/check_jdk.sh
```

* Compiles Java files 
```
./scripts/compile.sh [plugin]
```

* Run the script for automatic compilation and test execution
```
./scripts/build_and_run_tests.sh [plugin]
```

This script runs:
* Checks if JUnit is installed
```
./scripts/check_junit.sh
```

* Compiles JUnit classes 
```
./scripts/compile_tests.sh [plugin]
```

* Runs tests:
```
./scripts/run_tests.sh [plugin]
```

---

<div align="center">
  <h2>Plugin List</h2>
</div>
 
<div align="center">
  <h3>Pomodoro Plugin</h3>
</div>

[pomodoro](pomodoro/README.md) - Simple Vim pomodoro plugin.

Use Vim to edit files, and after 25 minutes it will change Vim's color scheme 
to remind you to take a break.
Designed for self-monitoring and productivity.

<div align="center">
  <h4>Plugin installation</h4>
</div>

* with test execution:
```
./scripts/install_plugin.sh pomodoro
```

* without running tests:
```
./scripts/install_plugin.sh pomodoro --no-test
```

<div align="center">
  <h4>Plugin uninstallation</h4>
</div>

```
./scripts/uninstall_plugin.sh pomodoro
```

---

<div align="center">
  <h2>Requirements</h2>
</div>
 
* Java installed
* Vim installed
* .class files built into designated folder
* JUnit 5 installed (optional)

---

<div align="center">
  <h2>Compatibility</h2>
</div>
 
* Vim 7.0 and above
* Java 8 and above

---

<div align="center">

  <a id="russian"></a>
  <h1>Простые плагины для Vim на языке Java</h1>
  <p>Пишу их учебных целях для проверки взаимодействия Vim и Java</p>

  [![EN](https://img.shields.io/badge/English-🇬🇧-blue)](#english)
  [![RU](https://img.shields.io/badge/Русский-🇷🇺-red)](#russian)
  ![Version 0.6.4](https://img.shields.io/badge/Version-0.6.4-orange.svg)
  ![Stars](https://img.shields.io/github/stars/AlexandrAnatoliev/jvim-plugins.svg?style=flat)
  ![Forks](https://img.shields.io/github/forks/AlexandrAnatoliev/jvim-plugins.svg?style=flat)
  ![GitHub repo size](https://img.shields.io/github/repo-size/AlexandrAnatoliev/jvim-plugins)

</div>

<div align="center">
  <h4>Установка</h4>
</div>
 
* Сохранить папку с плагином `jvim-example-plugin/` 
в папку `~/.vim/pack/my-plugins/start/`:
```
~/.vim/
└── pack/
    └── my-plugins/
        └── start/
            └── jvim-example-plugin/
                ├── plugin/
                │   └── jvim_example_plugin.vim
                └── java/
                    └── JvimExamlePlugin.java
```

* Скомпилировать Java файл:
```
cd ~/.vim/pack/my-plugins/start/java/
javac JvimExamlePlugin.java
```

* Перезагрузить Vim или выполнить команду:

```
:source ~/.vim/pack/my-plugins/start/jvim-example-plugin/plugin/jvim_example_plugin.vim
```

<div align="center">
  <h4>Список плагинов</h4>
</div>
 
 * [vim-hello-plugin](vim-hello-plugin/plugin/hello.vim)

Простой плагин Hello для проверки взаимодействия Vim.
Введите команду: `Hello` и Vim ответит к командной строке `hello vim`

 * [jvim-hello](jvim-hello/README.md)

Нужен для проверки взаимодействия Vim и Java
На команду: `:JvimHello` Vim ответит в командной строке: `hello java vim`

 * [jvim-echo](jvim-echo/README.md)

Нужен для проверки взаимодействия Vim и Java
На команду: `:Jvim Hello!!!` Vim ответит в командной строке: `Hello!!!`

* [jvim-timer](jvim-timer/README.md)

Простой плагин для измерения времени работы Vim.
Нужен для самоконтроля и производительности.

По окончании работы и закрытия Vim выводит время работы Vim.

* [vim-autocomplete](vim-autocomplete/README.md)

Простой Vim плагин для автодополнения с меню подсказки.
Нужен для производительности.

<div align="center">
  <h4>Требования</h4>
</div>
 
* Установленная Java
* Скомпилированный файл JvimExamplePlugin.class в указанной директории

<div align="center">
  <h4>Совместимость</h4>
</div>
 
* Vim 7.0 и выше
* Java 8 и выше

<div align="center">
  <h4>Дебаг</h4>
</div>

Если плагин не работает, 
проверьте работу Java файла вручную в терминале:

```
cd ~/.vim/pack/my-plugins/start/jvim-example-plugin/java
java JvimExamplePlugin
```

