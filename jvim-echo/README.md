<div align="center">

  <a id="russian"></a>
  <h1>Простой эха-плагин для Vim</h1>
  <p>Нужен для проверки взаимодействия vim и Java

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
 
* Сохранить папку с плагином jvim-echo/ в папку `~/.vim/pack/my-plugins/start/`:
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

* Скомпилировать java файл:
```
cd ~/.vim/pack/my-plugins/start/java/
javac JvimEcho.java
```

* Перезагрузить vim или выполнить команду:

```
:source ~/.vim/pack/my-plugins/start/jvim-echo/plugin/jvim_echo.vim
```

<div align="center">
  <h4>Использование</h4>
</div>
 
На команду: `:Jvim Hello!!!`

...vim ответит в командной строке: `Hello!!!`
