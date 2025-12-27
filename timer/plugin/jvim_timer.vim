" ==================================================================
" File: jvim_timer.vim
" Description: Simple Vim work time measurement plugin 
" Author: AlexandAnatoliev
" Version: 0.6.19
" Last Modified: 27.12.2025
" ==================================================================

" Automatic timer start on Vim enter and stop on Vim leave {{{
augroup Timer
    autocmd!
    autocmd VimEnter * call StartTimer()
    autocmd VimLeave * call StopTimer()
augroup END
" }}}

" StartTimer function {{{
" ------------------------------------------------------------------  
" Function: StartTimer()
" Description: Function to record the current time 
" as start time in temporary file
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! StartTimer()
    silent !java -cp ~/.vim/pack/my-plugins/start/timer/bin/main/ Main start 
endfunction
" }}}

" StopTimer function {{{
" ------------------------------------------------------------------  
" Function: StopTimer()
" Description: Function to read start time, calculates duration, 
" displays result and cleans up temporary file
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! StopTimer()
    silent !java -cp ~/.vim/pack/my-plugins/start/timer/bin/main/ Main stop &
endfunction
" }}}
