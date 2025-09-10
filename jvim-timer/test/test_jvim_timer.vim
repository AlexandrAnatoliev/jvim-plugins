let s:java_cmd = 'java -cp ' . expand('~/.vim/pack/my-plugins/start/jvim-timer/bin/') . ' Main'

function! TestStartTimer() abort
  if !isdirectory('data')
    call mkdir('data, 'p')
  endif

  if filereadable('data/jvim_session_time.txt')
    call delete('data/jvim_session_time.txt')
  endif

  if filereadable('data/jvim_day_time.txt')
    call delete('data/jvim_day_time.txt')
  endif

  let output = system(s:java_cmd . ' start')

  if v:shell_error != 0
    echoerr 'StartTimer test failed: ' . output
    return 0
  endif

  if !filereadable('data/jvim_session_time.txt')
    echo "Files in data directory:"
    echo system('ls -la data/')
    echoerr "Session file not created"
    return 0
  endif

  let session_content = readfile('data/jvim_session_time.txt')[0]
  echo "Session file created with content: " . session_content
  echo "StartTimer test passed!"
  return 1
endfunction

function! TestStopTimer() abort
  let output = system(s:java_cmd . ' stop')

  if v:shell_error != 0
    echoerr 'StopTimer test failed: ' . output
    return 0
  endif

  if !filereadable('data/jvim_day_time.txt')
    echo "Files in data directory:"
    echo system('ls -la data/')
    echoerr 'Daily data file not updated'
    return 0
  endif

  let daily_content = readfile('data/jvim_day_time.txt')[0]
  echo "Session file created with content: " . daily_content
  echo "StartTimer test passed!"
  return 1
endfunction

echo "Running jvim_timer tests ..."
let start_result = TestStartTimer()
sleep 2
let stop_result = TestStopTimer()

if start_result && stop_result
  echo "All tests passed!"
else
  echo "Some tests failed!"
endif

