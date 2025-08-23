<div align="center">

  <a id="english"></a>
  <h1>Simple echo plugin for Vim</h1>
  <p>Designed to test interaction between Vim and Java</p>

  [![EN](https://img.shields.io/badge/English-🇬🇧-blue)](#english)
  [![RU](https://img.shields.io/badge/Русский-🇷🇺-red)](#russian)
  ![Version 0.1.0](https://img.shields.io/badge/Version-0.1.0-orange.svg)
  ![Stars](https://img.shields.io/github/stars/AlexandrAnatoliev/jvim-plugins.svg?style=flat)
  ![Forks](https://img.shields.io/github/forks/AlexandrAnatoliev/jvim-plugins.svg?style=flat)
  ![GitHub repo size](https://img.shields.io/github/repo-size/AlexandrAnatoliev/jvim-plugins)

</div>

<div align="center">
  <h4>Installation</h4>
</div>
 
* Save the plugin folder `jvim-echo/` to `~/.vim/pack/my-plugins/start/`:
```
~/.vim/
└── pack/
    └── my-plugins/
        └── start/
            └── jvim-hello/
                ├── plugin/
                │   └── jvim_echo.vim
                └── java/
                    └── JvimEcho.java
```

* Compile the Java file:
```
cd ~/.vim/pack/my-plugins/start/java/
javac JvimEcho.java
```

* Reload Vim or execute the command:

```
:source ~/.vim/pack/my-plugins/start/jvim-echo/plugin/jvim_echo.vim
```

<div align="center">
  <h4>Usage</h4>
</div>
 
On command: `:Jvim Hello!!!`

...Vim will respond in the command line: `Hello!!!`

<div align="center">
  <h4>Requirements</h4>
</div>
 
* Java installed
* Compilled JvimEcho.class file in the specified directory

<div align="center">
  <h4>Compatibility</h4>
</div>
 
* Vim 7.0 and above
* Java 8 and above

<div align="center">
  <h4>Usage Example</h4>
</div>

```
:Jvim Hello world!
:Jvim testing 123
:Jvim "text with spaces"
```
 
<div align="center">
  <h4>Debugging</h4>
</div>

If the plugin doesn't work, 
test the Java command manually in terminal:

```
cd ~/.vim/pack/my-plugins/start/jvim-echo/java
java JvimEcho "test message"
```

<div align="center">

  <a id="russian"></a>
  <h1>Простой эхо-плагин для Vim</h1>
  <p>Нужен для проверки взаимодействия Vim и Java</p>

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
 
* Сохранить папку с плагином `jvim-echo/` в папку `~/.vim/pack/my-plugins/start/`:
```
~/.vim/
└── pack/
    └── my-plugins/
        └── start/
            └── jvim-hello/
                ├── plugin/
                │   └── jvim_echo.vim
                └── java/
                    └── JvimEcho.java
```

* Скомпилировать Java файл:
```
cd ~/.vim/pack/my-plugins/start/java/
javac JvimEcho.java
```

* Перезагрузить Vim или выполнить команду:

```
:source ~/.vim/pack/my-plugins/start/jvim-echo/plugin/jvim_echo.vim
```

<div align="center">
  <h4>Использование</h4>
</div>
 
На команду: `:Jvim Hello!!!`

...Vim ответит в командной строке: `Hello!!!`

<div align="center">
  <h4>Требования</h4>
</div>
 
* Установленная Java
* Скомпилированный файл JvimEcho.class в указанной директории

<div align="center">
  <h4>Требования</h4>
</div>
 
* Vim 7.0 и выше
* Java 8 и выше

<div align="center">
  <h4>Примеры использования</h4>
</div>

```
:Jvim Hello world!
:Jvim testing 123
:Jvim "text with spaces"
```
 
<div align="center">
  <h4>Дебаг</h4>
</div>

Если плагин не работает, 
проверьте работу Java файла вручную в термаинале:

```
cd ~/.vim/pack/my-plugins/start/jvim-echo/java
java JvimEcho "test message"
```
