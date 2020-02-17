#!/bin/sh
docker build . -t dod
mkdir -p build
docker run --rm --entrypoint cat dod  /home/application/function.zip > build/function.zip

sam local start-api -t sam.yaml -p 3000

