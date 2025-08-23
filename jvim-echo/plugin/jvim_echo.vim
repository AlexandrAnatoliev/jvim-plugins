" ==================================================================
" File: jvim_echo.vim
" Description: Simple echo plugin for Vim-Java interaction testing
" Autor: AlexandAnatoliev
" Version: 0.1.0
" Last Modified: 23.08.25
" ==================================================================

if exists('g:loaded_vim_echo')
  finish
endif

let g:loaded_vim_echo = 1

" ------------------------------------------------------------------  
" Function: s:JvimEcho(...)
" Description: Main function that handles Java-Vim echo interaction
" Parameters:
"   ... - Variable numbers of arguments (text to echo)
" Returns: None
" ------------------------------------------------------------------  
function! s:JvimEcho(...)
  if a:0 == 0
    echo "Usage: :Jvim <text>"
    echo "Example: :Jvim Hello World!"
    return
  endif
    
  let l:text = join(a:000, ' ')
    
  let l:result = system('java -cp ~/.vim/pack/my-plugins/start/jvim-echo/java/ JvimEcho "' . l:text . '"')
    
  echo l:result
endfunction

" ------------------------------------------------------------------  
" Command Registration
" Description: Register :Jvim command for user interaction
" Usage: :Jvim <any text>
" Example: :Jvim Hello Vim World!
" ------------------------------------------------------------------  
command! -nargs=* Jvim call s:JvimEcho(<f-args>)
