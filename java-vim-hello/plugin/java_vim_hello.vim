" Упрощенная версия Java плагина
if exists("g:loaded_java_vim_hello")
    finish
endif
let g:loaded_java_vim_hello = 1

command! -nargs=0 JavaVimHello call s:RunJavaVimHello()

function! s:RunJavaVimHello()
    " Запускаем Java программу и получаем результат
    let result = system('java -cp ~/.vim/pack/my-plugins/start/java-vim-hello/java/ JavaVimHello')
    " Проверяем ошибки
    if v:shell_error != 0
        echo "Ошибка: " . result
    else
        " Выводим результат
        echo result
    endif
endfunction
