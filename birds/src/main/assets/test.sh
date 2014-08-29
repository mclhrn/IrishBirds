#!/bin/bash          

FILES=$(find . -type f -name '*.jpg')
for file in $FILES
do
  cwebp $FILE -q 75 -o $FILE.webp
done