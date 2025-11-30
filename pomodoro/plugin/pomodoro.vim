" ==================================================================
" File: pomodoro.vim
" Description: Vim pomodoro plugin for work time self-management 
" Author: AlexanddAnatoliev
" Version: 0.6.5
" Last Modified: 30.11.2025
" ==================================================================

" Automatic timer start on Vim enter and stop on Vim leave
autocmd VimEnter * call StartPomodoroTimer()
autocmd VimLeave * call StopPomodoroTimer()

" ------------------------------------------------------------------  
" Function: StartPomodoroTimer()
" Description: Function to start a timer 
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! StartPomodoroTimer()
  silent !java -cp ~/.vim/pack/my-plugins/start/pomodoro/bin/main/ Main start &
  call StartFileMonitor()
endfunction

" ------------------------------------------------------------------  
" Function: StopPomodoroTimer()
" Description: Function to stop a timer 
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! StopPomodoroTimer()
  silent !java -cp ~/.vim/pack/my-plugins/start/pomodoro/bin/main/ Main stop &
endfunction

" ------------------------------------------------------------------  
" Simple file monitor for executing Vim commands
" ------------------------------------------------------------------  
let s:monitor_file = expand('~/.vim/pack/my-plugins/start/pomodoro/data/monitor.txt')
let s:last_content = ''

" ------------------------------------------------------------------  
" Function: StartFileMonitor()
" Description: Function to start file monitor  
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! StartFileMonitor()
    let s:monitor_timer = timer_start(6000, {-> SimpleFileMonitor()}, {'repeat': -1})
endfunction

" ------------------------------------------------------------------  
" Function: SimpleFileMonitor()
" Description: Function to file monitor for executing Vim commands 
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! SimpleFileMonitor()
    if filereadable(s:monitor_file)
        let content = join(readfile(s:monitor_file), "\n")
        if content != s:last_content && !empty(content)
            execute content
            let s:last_content = content
        endif
    endif
endfunction

command! -nargs=0 ShowPomodoroTime call s:RunShowPomodoroTime()

function! s:RunShowPomodoroTime()
    " Запускаем Java программу и получаем результат
    let result = system('java -cp ~/.vim/pack/my-plugins/start/pomodoro/bin/main/ Main show_time')
    " Проверяем ошибки
    if v:shell_error != 0
        echo "Ошибка: " . result
    else
        " Выводим результат
        echo result 
    endif
endfunction
