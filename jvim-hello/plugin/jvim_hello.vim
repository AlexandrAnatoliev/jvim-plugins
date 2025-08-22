" Упрощенная версия Java плагина
if exists("g:loaded_jvim_hello")
    finish
endif
let g:loaded_jvim_hello = 1

command! -nargs=0 JvimHello call s:RunJvimHello()

function! s:RunJvimHello()
    " Запускаем Java программу и получаем результат
    let result = system('java -cp ~/.vim/pack/my-plugins/start/jvim-hello/java/ JvimHello')
    " Проверяем ошибки
    if v:shell_error != 0
        echo "Ошибка: " . result
    else
        " Выводим результат
        echo result
    endif
endfunction
