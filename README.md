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

<div align="center">
  <h4>Installation</h4>
</div>
 
* Save the plugin folder `jvim-example-plugin/` 
to `~/.vim/pack/my-plugins/start/`:
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

* Compile the Java file:
```
cd ~/.vim/pack/my-plugins/start/java/
javac JvimExamlePlugin.java
```

* Reload Vim or execute the command:

```
:source ~/.vim/pack/my-plugins/start/jvim-example-plugin/plugin/jvim_example_plugin.vim
```

<div align="center">
  <h4>Plugin List</h4>
</div>
 
 * [vim-hello-plugin](vim-hello-plugin/plugin/hello.vim)

Simple Hello plugin for testing Vim interaction.
Enter command: `Hello` and Vim will respond on command line:
`hello vim`

 * [jvim-hello](jvim-hello/README.md)

Designed to test interaction between Vim and Java.
On command: `:JvimHello` Vim will respond on command line:
`hello java vim`

 * [jvim-echo](jvim-echo/README.md)

Designed to test interaction between Vim and Java.
On command: `:Jvim Hello!!!` Vim will respond on command line:
`Hello!!!`

* [jvim-timer](jvim-timer/README.md)

Simple plugin for measuring Vim working time.
Designed for self-monitoring and productivity.

Displays Vim running time after finishing work and closing Vim.

* [vim-autocomplete](vim-autocomplete/README.md)

Simple Vim autocomplete plugin with hint.
Designed for self-monitoring and productivity.

<div align="center">
  <h4>Requirements</h4>
</div>
 
* Java installed
* Compilled JvimExamplePlugin.class file in the specified directory. 

<div align="center">
  <h4>Compatibility</h4>
</div>
 
* Vim 7.0 and above
* Java 8 and above

<div align="center">
  <h4>Debugging</h4>
</div>

If the plugin doesn't work, test the Java file manually in terminal:

```
cd ~/.vim/pack/my-plugins/start/jvim-example-plugin/java
java JvimExamplePlugin
```
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

