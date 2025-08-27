" ==================================================================
" File: jvim_timer.vim
" Description: Simple Vim work time measurement plugin 
" Autor: AlexandAnatoliev
" Version: 0.1.0
" Last Modified: 27.08.25
" ==================================================================

" Automatic timer start on Vim enter and stop on Vim leave
autocmd VimEnter * call StartTimer()
autocmd VimLeave * call StopTimer()

" ------------------------------------------------------------------  
" Function: StartTimer()
" Description: Function to save start time to file
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! StartTimer()
    silent !echo "" > /tmp/vim_start_time.txt
    silent !date +\%s\%3N > /tmp/vim_start_time.txt
endfunction

" ------------------------------------------------------------------  
" Function: StopTimer()
" Description: Function to start JavaTimer
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! StopTimer()
    silent !java -cp ~/.vim/pack/my-plugins/start/jvim-timer/java/ JvimTimer &
endfunction
