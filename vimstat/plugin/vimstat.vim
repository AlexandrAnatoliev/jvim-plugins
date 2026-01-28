" ==================================================================
" File: vimstat.vim
" Description: Vim plugin for work self-management 
" Author: AlexandrAnatoliev
" Version: 0.8.0
" Last Modified: 28.01.2026
" ==================================================================

" Plugin event handlers {{{
augroup CommitStats
  autocmd!
  autocmd VimEnter * call StartVimStat()
  autocmd BufWritePost * call UpdateVimStat()
  autocmd VimLeave * call StopVimStat()
augroup END
" }}}

" StartVimStat function {{{
" ------------------------------------------------------------------  
" Function: StartVimStat()
" Description: Function to start a vimstat plugin
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! StartVimStat()
  silent !java -cp ~/.vim/pack/my-plugins/start/vimstat/target/classes Main start &
endfunction
" }}}

" UpdateVimStat function {{{
" ------------------------------------------------------------------  
" Function: UpdateVimStat()
" Description: Function to update a vim stats 
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! UpdateVimStat()
  silent !java -cp ~/.vim/pack/my-plugins/start/vimstat/target/classes Main update &
endfunction
" }}}

" StopVimStat function {{{
" ------------------------------------------------------------------  
" Function: StopVimStat()
" Description: Function to stop a vimstat plugin
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! StopVimStat()
  silent !java -cp ~/.vim/pack/my-plugins/start/vimstat/target/classes Main stop &
endfunction
" }}}
