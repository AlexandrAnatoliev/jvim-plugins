" Простой эхо-плагин для взаимодействия с Java
function! s:JvimEcho(...)
    if a:0 == 0
        echo "Usage: :Jvim <text>"
        return
    endif
    
    let l:text = join(a:000, ' ')
    
    " Вызываем Java программу и получаем результат
    let l:result = system('java -cp ~/.vim/pack/my-plugins/start/jvim-echo/java/ JvimEcho "' . l:text . '"')
    
    echo l:result
endfunction

" Регистрируем команду
command! -nargs=* Jvim call s:JvimEcho(<f-args>)
