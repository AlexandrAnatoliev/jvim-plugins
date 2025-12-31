<div align="center">

  <a id="english"></a>
  <h1>Simple Vim Plugins In Java</h1>
  <p>Created for educational purposes to test interaction
  between Vim and Java</p>

  [![EN](https://img.shields.io/badge/English-🇬🇧-blue)](#english)
  [![RU](https://img.shields.io/badge/Русский-🇷🇺-red)](#russian)
  ![Version 0.7.3](https://img.shields.io/badge/Version-0.7.3-orange.svg)
  ![Stars](https://img.shields.io/github/stars/AlexandrAnatoliev/jvim-plugins.svg?style=flat)
  ![Forks](https://img.shields.io/github/forks/AlexandrAnatoliev/jvim-plugins.svg?style=flat)
  ![GitHub repo size](https://img.shields.io/github/repo-size/AlexandrAnatoliev/jvim-plugins)
</div>

  > **Author:** Alexandr Anatoliev

  > **GitHub:** [AlexandrAnatoliev](https://github.com/AlexandrAnatoliev)

---

<div align="center">
  <h2>Table of Contents</h2>
</div>

* [Plugin Installation](#plugin-installation)
* [Plugin Uninstallation](#plugin-uninstallation)
* [Files Structure](#files-structure)
* [Script Usage](#script-usage)
* [Plugin List](#plugin-list)
* [Contributing](#contributing)
* [Requirements](#requirements)
* [Compatibility](#compatibility)
* [Contact](#contact)

---

<div align="center">
  <a id="plugin-installation"></a>
  <h2>Plugin Installation</h2>
</div>

* Clone the plugins repository:
```
git clone https://github.com/AlexandrAnatoliev/jvim-plugins 
```

* Navigate to the root:
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

* When installing a plugin, it is installed in the appropriate Vim directory. 
```
~/.vim/
└── pack/
    └── my-plugins/
        └── start/
            └── jvim-example-plugin/
```

* Reload Vim or execute a command:

```
:source ~/.vim/pack/my-plugins/start/jvim-example-plugin/plugin/jvim_example_plugin.vim
```

---

<div align="center">
  <a id="plugin-uninstallation"></a>
  <h2>Plugin Uninstallation</h2>
</div>

* To uninstall the plugin using the script:
```
./scripts/uninstall_plugin.sh [plugin]
```

---

<div align="center">
  <a id="files-structure"></a>
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
  <a id="script-usage"></a>
  <h2>Script Usage</h2>
</div>

The `install_plugin.sh [plugin]` script runs the following scripts sequentially:
* `build.sh [plugin]` - builds plugin Java files;
* `build_and_run_tests.sh [plugin]` - runs JUnit tests;
* `copy_plugin_to_vim.sh [plugin]` - copies the build plugin to the appropriate 
Vim directory;

The `install_plugin.sh [plugin] --no-test` script runs without testing:
* `build.sh [plugin]`
* `copy_plugin_to_vim.sh [plugin]`

Script for automatic plugin building:
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

Script for automatic compilation and test execution
```
./scripts/build_and_run_tests.sh [plugin]
```

This script runs:
* Checks if JUnit is installed
```
./scripts/check_junit.sh
```

* If case of an error, check the path to JUnit using command: 
```
dpkg -L junit5 | grep junit-jupiter-api
```
```
dpkg -L junit5 | grep junit-platform-console-standalone
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
  <a id="plugin-list"></a>
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
<div align="center">
  <h3>Timer Plugin</h3>
</div>

[timer](timer/README.md) - Simple Vim Working Time Measurement Plugin.

 After closing Vim, you will see:
```
  =========================================
                Vim uptime:
  -----------------------------------------
  - per session:         0 h  0 min  8 sec
  - per day:             0 h 19 min 59 sec
  - average per month:   0 h 57 min 14 sec
  =========================================
```
Designed for self-monitoring and productivity.

<div align="center">
  <h4>Plugin installation</h4>
</div>

* with test execution:
```
./scripts/install_plugin.sh timer
```

* without running tests:
```
./scripts/install_plugin.sh timer --no-test
```

<div align="center">
  <h4>Plugin uninstallation</h4>
</div>

```
./scripts/uninstall_plugin.sh timer
```

---

<div align="center">
  <a id="contributing"></a>
  <h2>Contributing</h2>
</div>

<div align="center">
  <h4>Setup Instructions</h4>
</div>

1. Fork this repository by clicking on the "Fork" 
  button at the top-right corner of this page. 
  This creates a copy of the repository in your 
  GitHub account.

2. Clone your forked repository by clicking the 
  "Code" button. That will open small window.
  In its you can copy and paste the URL to 
  your local machine with the command:

```bash
git clone https://github.com/<your-username>/jvim-plugins.git
```

3. Navigate to your project folder:

```bash
cd jvim-plugins
```

4. Add a reference to the original repository for 
  future updates:

```bash
git remote add upstream https://github.com/AlexandrAnatoliev/jvim-plugins.git
```

(Remember to keep here the original repository URL, 
  not your forked one, so the username in this 
  needs to be `AlexandrAnatoliev`, not your 
  own username.)

5. Check the remotes for your local repository:

```bash
git remote -v
```

You should now see the origin (added automatically when cloning) and upstream 
  remotes listed.

``` 
origin  https://github.com/<your-username>/jvim-plugins.git (fetch)
origin  https://github.com/<your-username>/jvim-plugins.git (push)
upstream        https://github.com/AlexandrAnatoliev/jvim-plugins.git (fetch)
upstream        https://github.com/AlexandrAnatoliev/jvim-plugins.git (push)
```

6. Pull from the upstream repository 
to your master branch to keep it in sync with 
the main project:

```bash
git pull upstream master
```

7. Create a new branch with the command:

```bash
git switch -c fix-issue
```

Now you are ready to start working on the issues!
Remember to pull from the upstream repository every once in a while 
to keep your local repository 
up to date with the main project.

_Note: I recommend to always create new branch 
with each Issue you solve! Otherwise, the pull 
requests will get too large and there could be 
merge conflicts._

<div align="center">
  <h4>Submitting Your Changes</h4>
</div>

Once you've made the necessary changes to fix the issue, 
you're ready to submit your changes!

1. Stage your changes with the command:

```bash
git add files-that-you-changed
```

2. Commit your changes with the command:

```bash
git commit -m "Fixed issue"
```

3. Push your changes to your forked repository 
with the command:

```bash
git push origin fix-issue
```

Once you've pushed your changes to GitHub, 
you're ready to create a pull request. 
Go to your forked repository on GitHub.

- You should see message "fix-issue had recent pushes" 
(or whatever your branch name is) and button 
"Compare & pull request" next to it.

- Click the "Compare & pull request" button 
to proceed to the pull request page of the original 
jvim-plugins repository.

- Fill in the title and description boxes 
with details about the problem and 
how you got it to work. You can also add some 
additional information such as screenshots, 
if you want.

- Finally, click "Create pull request" to finish 
creating the pull request.

Congratulations on making your open source 
contribution on my GitHub project!

Sit back and wait for feedback 
on the pull request. If everything is 
fine, you should get the pull request merged. 
If not, you will be requested to make changes 
to your code.

Remember to wait for me to review your pull 
request, do not close it yourself.
If you are asked to make changes, you can do 
so by committing them to the same branch, 
there is no need to close the current Pull 
Request and open a new one.

---

<div align="center">
  <a id="contact"></a>
  <h2>Contact</h2>
</div>

For any queries, feel free to open an issue,
write to [Discussions](https://github.com/AlexandrAnatoliev/jvim-plugins/discussions/58)
or reach out to me at per-1986@list.ru.

---

<div align="center">
  <a id="requirements"></a>
  <h2>Requirements</h2>
</div>
 
* Java installed
* Vim installed
* JUnit 5 installed (optional)

---

<div align="center">
  <a id="compatibility"></a>
  <h2>Compatibility</h2>
</div>
 
* Vim 7.0 and above
* Java 8 and above

---

<div align="center">

  <a id="russian"></a>
  <h1>Простые плагины для Vim на языке Java</h1>
  <p>Пишу их в учебных целях для проверки взаимодействия Vim и Java</p>

  [![EN](https://img.shields.io/badge/English-🇬🇧-blue)](#english)
  [![RU](https://img.shields.io/badge/Русский-🇷🇺-red)](#russian)
  ![Version 0.7.3](https://img.shields.io/badge/Version-0.7.3-orange.svg)
  ![Stars](https://img.shields.io/github/stars/AlexandrAnatoliev/jvim-plugins.svg?style=flat)
  ![Forks](https://img.shields.io/github/forks/AlexandrAnatoliev/jvim-plugins.svg?style=flat)
  ![GitHub repo size](https://img.shields.io/github/repo-size/AlexandrAnatoliev/jvim-plugins)

</div>

  > **Author:** Alexandr Anatoliev

  > **GitHub:** [AlexandrAnatoliev](https://github.com/AlexandrAnatoliev)

---

<div align="center">
  <h2>Table of Contents</h2>
</div>

* [Установка](#plugin-installation-ru)
* [Удаление Плагина](#plugin-uninstallation-ru)
* [Структура Файлов](#files-structure-ru)
* [Использование Скриптов](#script-usage-ru)
* [Список Плагинов](#plugin-list-ru)
* [Участие В Разработке](#contributing-ru)
* [Требования](#requirements-ru)
* [Совместимость](#compatibility-ru)
* [Контакты](#contact-ru)

---

<div align="center">
  <a id="plugin-installation-ru"></a>
  <h2>Установка</h2>
</div>

* Клонируйте репозиторий:
```
git clone https://github.com/AlexandrAnatoliev/jvim-plugins 
```

* Перейдите в корневую директорию:
```
cd jvim-plugins/
```

* Сделайте скрипты исполняемыми:
```
chmod +x scripts/*.sh
```

Установите плагин с помощью скрипта:
* с выполнением тестов:
```
./scripts/install_plugin.sh [plugin]
```
* без запуска тестов:
```
./scripts/install_plugin.sh [plugin] --no-test
```

* При установке плагин помещается в соответствующую директорию Vim. 
```
~/.vim/
└── pack/
    └── my-plugins/
        └── start/
            └── jvim-example-plugin/
```

* Перезагрузите Vim или выполните команду:

```
:source ~/.vim/pack/my-plugins/start/jvim-example-plugin/plugin/jvim_example_plugin.vim
```

---

<div align="center">
  <a id="plugin-uninstallation-ru"></a>
  <h2>Удаление плагина</h2>
</div>

* Для удаления плагина используйте скрипт:
```
./scripts/uninstall_plugin.sh [plugin]
```

---

<div align="center">
  <a id="files-structure-ru"></a>
  <h2>Структура файлов</h2>
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
  <a id="script-usage-ru"></a>
  <h2>Использование скриптов</h2>
</div>

Скрипт `install_plugin.sh [plugin]` выполняет следующие скрипты последовательно:
* `build.sh [plugin]` - сборка Java файлов плагина;
* `build_and_run_tests.sh [plugin]` - сборка и запуск JUnit тестов;
* `copy_plugin_to_vim.sh [plugin]` - копирование собранного плагина в 
соответствующую директорию Vim.

Скрипт `install_plugin.sh [plugin] --no-test` выполняется без тестирования:
* `build.sh [plugin]`
* `copy_plugin_to_vim.sh [plugin]`

Скрипт для автоматической сборки плагина:
```
./scripts/build.sh [plugin]
```

Этот скрипт выполняет:
* Проверяет установлен ли Vim
```
./scripts/check_vim.sh
```

* Проверяет установлен ли JDK
```
./scripts/check_jdk.sh
```

* Компилирует Java файлы
```
./scripts/compile.sh [plugin]
```

Скрипт для автоматической компиляции и выполнения тестов
```
./scripts/build_and_run_tests.sh [plugin]
```

Этот скрипт выполняет:
* Проверяет установлен ли JUnit
```
./scripts/check_junit.sh
```
* В случае ошибки, проверьте путь до JUnit используя команды:
```
dpkg -L junit5 | grep junit-jupiter-api
```
```
dpkg -L junit5 | grep junit-platform-console-standalone
```
* Компилирует JUnit классы
```
./scripts/compile_tests.sh [plugin]
```

* Запускает тесты:
```
./scripts/run_tests.sh [plugin]
```

---

<div align="center">
  <a id="plugin-list-ru"></a>
  <h2>Список плагинов</h2>
</div>
 
<div align="center">
  <h3>Pomodoro плагин</h3>
</div>

[pomodoro](pomodoro/README.md) - Простой Pomodoro плагин для Vim.

Используйте Vim для редактирования файлов и через 25 минут он изменит цветовую
схему Vim, чтобы напомнить вам о необходимости сделать перерыв. Разработан для 
самоконтроля и эффективности.

<div align="center">
  <h4>Установка</h4>
</div>

* с выполнением тестов:
```
./scripts/install_plugin.sh pomodoro
```

* без запуска тестов:
```
./scripts/install_plugin.sh pomodoro --no-test
```

<div align="center">
  <h4>Удаление плагина</h4>
</div>

```
./scripts/uninstall_plugin.sh pomodoro
```

<div align="center">
  <h3>Timer плагин</h3>
</div>

[timer](timer/README.md) - Простой плагин для измерения времени работы Vim.

По окончании работы и закрытия Vim будет выведено:
```
  =========================================
                Vim uptime:
  -----------------------------------------
  - per session:         0 h  0 min  8 sec
  - per day:             0 h 19 min 59 sec
  - average per month:   0 h 57 min 14 sec
  =========================================
```
Нужен для самоконтроля и производительности.

<div align="center">
  <h4>Установка</h4>
</div>

* с выполнением тестов:
```
./scripts/install_plugin.sh timer
```

* без запуска тестов:
```
./scripts/install_plugin.sh timer --no-test
```

<div align="center">
  <h4>Удаление плагина</h4>
</div>

```
./scripts/uninstall_plugin.sh timer
```

---

<div align="center">
  <a id="contributing-ru"></a>
  <h2>Участие В Разработке</h2>
</div>

<div align="center">
  <h4>Инструкции по Установке</h4>
</div>

1. Сделайте "форк" этого репозитория нажатием 
  кнопки "Fork" в правом верхнем углу страницы.
  Это создаст копию ркпозитория на Вашем GitHub 
  аккаунте.

2.Клонируйте Ваш "форк" репозиторий нажатием
  кнопки "Code". Откроется маленькое окно.
  Скопируйте из него URL и выполните на своем 
  компьютере команду:

```bash
git clone https://github.com/<your-username>/jvim-plugins.git
```

3. Перейдите в папку с проектом:

```bash
cd jvim-plugins
```

4. Добавьте ссылку на оригинальный репозиторий
  для будущих обновлений:

```bash
git remote add upstream https://github.com/AlexandrAnatoliev/jvim-plugins.git
```

(Напомню, здесь должен быть URL оригинального
репозитория, а не "форкнутого" Вами, так что
username в нем должно быть `AlexandrAnatoliev`,
а не Ваш собственный username.)

5. Проверьте ремоуты для своего репозитория:

```bash
git remote -v
```

Вы должны увидеть origin (добавляется автоматически при клонировании) 
и upstream ремоуты:

``` 
origin  https://github.com/<your-username>/jvim-plugins.git (fetch)
origin  https://github.com/<your-username>/jvim-plugins.git (push)
upstream        https://github.com/AlexandrAnatoliev/jvim-plugins.git (fetch)
upstream        https://github.com/AlexandrAnatoliev/jvim-plugins.git (push)
```

6. Выполните pull из upstream репозитория в Вашу
  master ветку, чтобы синхронизировать ее с основным
  проектом:

```bash
git pull upstream master
```

7. Создайте новую ветку командой:

```bash
git switch -c fix-issue
```

Сейчас Вы готовы начать работать с issue!
Помните, каждый раз сначала делать pull
из upstream репозитория, чтобы держать содержимое
Вашего локального репозитория в соответствии
с главным проектом.

_Примечание: Рекомендую всегда создавать новую
ветвь для каждого issue, который Вы выполняете!
Иначе pull request будут слишком большими и 
возможно возникнут конфликты слияния._

<div align="center">
  <h4>Отправка Ваших изменений</h4>
</div>

После того как Вы решили задачу, Вы готовы отправить 
изменения! 

1. Добавьте Ваши изменения в отслеживание:

```bash
git add files-that-you-changed
```

2. Сделайте коммит:

```bash
git commit -m "Fixed issue"
```

3. Отправить изменения в Ваш "форк" репозиторий:

```bash
git push origin fix-issue
```

После того как Вы отправили Ваши изменения на 
GitHub, Вы готовы создать pull request.
Перейдите на Ваш "форк" репозитория на GitHub.

- Вы увидите надпись "fix-issue had recent pushes" 
(или как Ваша ветка называется) и кнопку 
"Compare & pull request" на ней.

- Нажмите кнопку "Compare & pull request" и перейдете
на страницу pull request оригинального репозитория
проекта jvim-plugins.

- Заполните поля title и description подробностямм
о задаче и Вашем ее решении. Вы можете также добавить
иную информацию, такую как скриншоты, если хотите.

- В конце нажмите кнопку "Create pull request" 
чтобы закончить создание pull request.

Поздравляю, Вы сделали свой вклад в мой open source проект!

Можете расслабиться и подождать пока не сделают
ревью Вашего кода. Если все хорошо, Ваш pull request
вольют в основную ветку. Если нет, Вам будет 
предложено внести изменения в Ваш код.

Помните, что нужно подождать ревью Вашего pull 
request, не закрывайте его сами.
Если Вас просят сделать изменения, Вы можете
коммититить их в ну же самую ветвь, не нужно
закрывать текущий pull request и открывать новый.

---

<div align="center">
  <a id="contact-ru"></a>
  <h2>Контакты</h2>
</div>

Столкнувшись с затруднениями, не стесняйтесь
открыть issue, написать в
[Discussions](https://github.com/AlexandrAnatoliev/jvim-plugins/discussions/58)
или мне на почту per-1986@list.ru.

---

<div align="center">
  <a id="requirements-ru"></a>
  <h2>Требования</h2>
</div>
 
* Установленная Java
* Установленный Vim
* Установленный JUnit (опционально)

---

<div align="center">
  <a id="compatibility-ru"></a>
  <h2>Совместимость</h2>
</div>
 
* Vim 7.0 и выше
* Java 8 и выше
