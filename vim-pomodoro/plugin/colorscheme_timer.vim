" ==================================================================
" File: colorscheme_timer.vim
" Description: Apply blue color scheme after delay 
" Autor: AlexandAnatoliev
" Version: 0.1.0
" Last Modified: 20.11.2025
" ==================================================================
autocmd VimEnter * call SetBlueThemeAfterDelay()

function! SetBlueThemeAfterDelay()
  " Set a timer for 25 minutes
    let timer = timer_start(1500000, 'ApplyBlueTheme')
endfunction

function! ApplyBlueTheme(timer)
  colorscheme blue
endfunction
