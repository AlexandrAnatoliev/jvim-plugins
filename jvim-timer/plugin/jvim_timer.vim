" ==================================================================
" File: jvim_timer.vim
" Description: Simple Vim work time measurement plugin 
" Autor: AlexandAnatoliev
" Version: 0.1.3
" Last Modified: 01.09.2025
" ==================================================================

" Automatic timer start on Vim enter and stop on Vim leave
autocmd VimEnter * call StartTimer()
autocmd VimLeave * call StopTimer()

" ------------------------------------------------------------------  
" Function: StartTimer()
" Description: Function to record the current time 
" as start time in temporary file
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! StartTimer()
    silent !java -cp ~/.vim/pack/my-plugins/start/jvim-timer/java/ JvimTimer start 
endfunction

" ------------------------------------------------------------------  
" Function: StopTimer()
" Description: Function to read start time, calculates duration, 
" displays result and cleans up temporary file
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! StopTimer()
    silent !java -cp ~/.vim/pack/my-plugins/start/jvim-timer/java/ JvimTimer stop &
endfunction
