" ==================================================================
" File: pomodoro.vim
" Description: Vim pomodoro plugin for work time self-management 
" Author: AlexandrAnatoliev
" Version: 0.7.3
" Last Modified: 31.12.2025
" ==================================================================

" Automatic timer start on Vim enter and stop on Vim leave {{{
augroup Pomodoro
    autocmd!
    autocmd VimEnter * call StartPomodoroTimer()
    autocmd VimLeave * call StopPomodoroTimer()
augroup END
" }}}

" StartPomodoroTimer function {{{
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
" }}}

" StopPomodoroTimer function {{{
" ------------------------------------------------------------------  
" Function: StopPomodoroTimer()
" Description: Function to stop a timer 
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! StopPomodoroTimer()
    silent !java -cp ~/.vim/pack/my-plugins/start/pomodoro/bin/main/ Main stop &
endfunction
" }}}

" ------------------------------------------------------------------  
" Simple file monitor for executing Vim commands
" ------------------------------------------------------------------  
" set variables {{{
let s:monitor_file = expand('~/.vim/pack/my-plugins/start/pomodoro/data/monitor.txt')
let s:last_content = ''
" }}}

" StartFileMonitor function {{{
" ------------------------------------------------------------------  
" Function: StartFileMonitor()
" Description: Function to start file monitor  
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! StartFileMonitor()
    if !exists('s:monitor_timer')
        let s:monitor_timer = timer_start(6000, {-> SimpleFileMonitor()}, {'repeat': -1})
    endif
endfunction
" }}}

" SimpleFileMonitor function {{{
" ------------------------------------------------------------------  
" Function: SimpleFileMonitor()
" Description: Function to file monitor for executing Vim commands 
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! SimpleFileMonitor()
    if filereadable(s:monitor_file)
        let content = join(readfile(s:monitor_file), "\n")
        if content !=# s:last_content && !empty(content)
            execute content
            let s:last_content = content
        endif
    endif
endfunction
" }}}

" command ShowPomodoroTime {{{
command! -nargs=0 ShowPomodoroTime call s:RunShowPomodoroTime()
" }}}

" RunShowPomodoroTime function {{{
" ------------------------------------------------------------------  
" Function: s:RunShowPomodoroTime()
" Description: Internal function to execute Pomodoro time display  
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! s:RunShowPomodoroTime()
    let result = system('java -cp ~/.vim/pack/my-plugins/start/pomodoro/bin/main/ Main show_time')
    if v:shell_error !=# 0
        echo "ERROR: " . result
    else
        echo result 
    endif
endfunction
" }}}
