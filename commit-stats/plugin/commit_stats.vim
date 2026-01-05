" ==================================================================
" File: commit_stats.vim
" Description: Vim plugin for work self-management 
" Author: AlexandrAnatoliev
" Version: 0.7.5
" Last Modified: 05.01.2026
" ==================================================================

" Automatic timer start on Vim enter and stop on Vim leave {{{
augroup CommitStats
  autocmd!
  autocmd VimEnter * call StartCommitStats()
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
  silent !java -cp ~/.vim/pack/my-plugins/start/commit-stats/bin/main/ Main start &
  echo "start StartCommitStats"
endfunction
" }}}
