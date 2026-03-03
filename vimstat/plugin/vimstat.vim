" ==================================================================
" File: vimstat.vim
" Description: Vim plugin for work self-management 
" Author: AlexandrAnatoliev
" Version: 0.8.39
" Last Modified: 03.03.2026
" ==================================================================

" Plugin event handlers {{{
augroup CommitStats
  autocmd!
  autocmd VimEnter * call <SID>StartVimStat()
  autocmd BufWritePost * call <SID>UpdateVimStat()
  autocmd VimLeave * call <SID>StopVimStat()
augroup END
" }}}

" StartVimStat function {{{
" ------------------------------------------------------------------  
" Function: StartVimStat()
" Description: Function to start a vimstat plugin
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! s:StartVimStat()
  silent !java -jar ~/.vim/pack/my-plugins/start/vimstat/target/vimstat-0.8.39.jar start &
endfunction
" }}}

" UpdateVimStat function {{{
" ------------------------------------------------------------------  
" Function: UpdateVimStat()
" Description: Function to update a vim stats 
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! s:UpdateVimStat()
  silent !java -jar ~/.vim/pack/my-plugins/start/vimstat/target/vimstat-0.8.39.jar update &
endfunction
" }}}

" StopVimStat function {{{
" ------------------------------------------------------------------  
" Function: StopVimStat()
" Description: Function to stop a vimstat plugin
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! s:StopVimStat()
  silent !java -jar ~/.vim/pack/my-plugins/start/vimstat/target/vimstat-0.8.39.jar stop &
endfunction
" }}}
