" Automatic timer start on Vim enter and stop on Vim leave
autocmd VimEnter * call StartPomodoroTimer()
autocmd VimLeave * call StopPomodoroTimer()

function! StartPomodoroTimer()
  silent !java -cp ~/.vim/pack/my-plugins/start/jvim-pomodoro/bin/main/ Main start &
  call StartFileMonitor()
endfunction

function! StopPomodoroTimer()
  silent !java -cp ~/.vim/pack/my-plugins/start/jvim-pomodoro/bin/main/ Main stop &
endfunction

" Simple file monitor for executing Vim commands
let s:monitor_file = expand('~/.vim/pack/my-plugins/start/jvim-pomodoro/data/monitor.txt')
let s:last_content = ''

function! StartFileMonitor()
    let s:monitor_timer = timer_start(6000, {-> SimpleFileMonitor()}, {'repeat': -1})
endfunction

function! SimpleFileMonitor()
    if filereadable(s:monitor_file)
        let content = join(readfile(s:monitor_file), "\n")
        if content != s:last_content && !empty(content)
            let s:last_content = content
            
            try
                " Выполняем команду из файла
                execute content
                echo "Command executed: " . content
            catch
                echo "Error executing command: " . content
                echo "Error message: " . v:exception
            endtry
        endif
    endif
endfunction
