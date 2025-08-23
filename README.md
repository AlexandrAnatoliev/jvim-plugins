<div align="center">

  <a id="russian"></a>
  <h1>Простые плагины для Vim на языке Java</h1>
  <p>Пишу их учебных целях для проверки взаимодействия Vim и Java</p>

  [![EN](https://img.shields.io/badge/English-🇬🇧-blue)](#english)
  [![RU](https://img.shields.io/badge/Русский-🇷🇺-red)](#russian)
  ![Version 0.1.0](https://img.shields.io/badge/Version-0.1.0-orange.svg)
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
проверьте работу Java файла вручную в термаинале:

```
cd ~/.vim/pack/my-plugins/start/jvim-example-plugin/java
java JvimExamplePlugin
```
