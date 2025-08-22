# Простой Hello-плагин для Vim

Нужен для проверки взаимодействия vim и java

#### Установка
 
* Сохранить папку с плагином jvim-hello/ в папку `~/.vim/pack/my-plugins/start/`:
```
~/.vim/
└── pack/
    └── my-plugins/
        └── start/
            └── jvim-hello/
                ├── plugin/
                │   └── jvim_hello.vim
                └── java/
                    └── JvimHello.java
```

* Скомпилировать java файл:
```
cd ~/.vim/pack/my-plugins/start/java/
javac JvimHello.java
```

* Перезагрузить vim или выполнить команду:

```
:source ~/.vim/pack/my-plugins/start/jvim-hello/plugin/jvim_hello.vim
```

#### Использование 

На команду: `:JvimHello`

...vim ответит в командной строке: `hello java vim`
