" Простой плагин Hello для Vim
" Автор: deepseek
" Версия: 0.1.0

" 1 Установка
" 
" Сохранить файл hello.vim в папку:
" ~/.vim/
" ├── plugin/
" │   └── hello.vim
"
" 2 Использование 
"
" После перезагрузки или команды:
" :source ~/.vim/plugin/hello.vim
"
" ...можно использовать команду:
" :Hello
"
" ...vim ответит к командной строке
" hello vim

" Проверяем, что плагин еще не загружен
if exists("g:loaded_hello")
    finish
endif
let g:loaded_hello = 1

" Определяем команду :Hello
command! -nargs=0 Hello call HelloWorld()

" Функция, которая выводит приветствие
function! HelloWorld()
    echo "hello vim"
endfunction
