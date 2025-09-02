" ==================================================================
" File: autocomplete.vim
" Description: Simple Vim autocomplete plugin with hint 
" Autor: AlexandAnatoliev
" Version: 0.1.0
" Last Modified: 02.09.2025
" ==================================================================

for char in range(char2nr('a'), char2nr('z'))
    execute "inoremap " . nr2char(char) . " " . nr2char(char) . "\<C-n>\<C-p>"
endfor

for char in range(char2nr('A'), char2nr('Z'))
    execute "inoremap " . nr2char(char) . " " . nr2char(char) . "\<C-n>\<C-p>"
endfor

