# webvtt-to-txt
batch converter from .vtt to .txt

<b>How to use:</b>

  run WebvttToTxt in terminal, passing in the paths of the files you want to convert into args:
      java WebvttToTxt /User/<i>username</i>/<i>dir</i>/<i>file</i>.vtt /User/<i>username</i>/<i>dir</i>/<i>file</i>.vtt
  or passing in a directory, with multiple .vtt files in it:
      java WebvttToTxt /User/<i>username</i>/<i>dir</i>
      
  
  both of these methods will skip over any file without a .vtt extention, so
      java WebvttToTxt /User/<i>username</i>/<i>dir</i>/<i>file</i>.indd /User/<i>username</i>/<i>dir</i>/<i>file</i>.vtt
  will still convert the second file, and
      java WebvttToTxt /User/<i>username</i>/<i>dir</i>
  will work even if the directory is a mix of .vtt and other file types
  
  
  Works on Mac and presumably Linux, I don't know about Windows.
  
  If you find any bugs, email me at njfletcher215@gmail.com
