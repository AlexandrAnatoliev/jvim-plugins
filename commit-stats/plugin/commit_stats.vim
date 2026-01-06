" ==================================================================
" File: commit_stats.vim
" Description: Vim plugin for work self-management 
" Author: AlexandrAnatoliev
" Version: 0.7.5
" Last Modified: 05.01.2026
" ==================================================================

" Plugin event handlers {{{
augroup CommitStats
  autocmd!
  autocmd VimEnter * call StartCommitStats()
  autocmd BufWritePost * call UpdateCommitStats()
  autocmd VimLeave * call StopCommitStats()
augroup END
" }}}

" StartCommitStats function {{{
" ------------------------------------------------------------------  
" Function: StartCommitStats()
" Description: Function to start a commit-stats plugin
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! StartCommitStats()
  silent !java -cp ~/.vim/pack/my-plugins/start/commit-stats/bin/main Main start &
endfunction
" }}}

" UpdateCommitStats function {{{
" ------------------------------------------------------------------  
" Function: UpdateCommitStats()
" Description: Function to update a commit stats 
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! UpdateCommitStats()
  silent !java -cp ~/.vim/pack/my-plugins/start/commit-stats/bin/main Main update &
endfunction
" }}}

" StopCommitStats function {{{
" ------------------------------------------------------------------  
" Function: StopCommitStats()
" Description: Function to stop a commit-stats plugin
" Parameters: None
" Returns: None
" ------------------------------------------------------------------  
function! StopCommitStats()
  silent !java -cp ~/.vim/pack/my-plugins/start/commit-stats/bin/main Main stop &
endfunction
" }}}
