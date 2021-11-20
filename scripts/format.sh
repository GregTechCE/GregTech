#!/bin/bash

npm install -g @htmlacademy/editorconfig-cli
IFS=$'\n' read -rd '' -a FILES <<< "$(git --no-pager diff --name-only HEAD "$(git merge-base HEAD master)")"
ERROR=""
for i in "${FILES[@]}"
do
    if [[ "$i" == *".java"* || "$i" == *".json"* ]]
    then
        if ! editorconfig-cli -e ../.editorconfig "../$i" > OUTPUT
        then
            ERROR="error"
        fi
        if [[ "$OUTPUT" == "Line: "* ]]
        then
            echo "$OUTPUT"
        fi
    fi
done

if [[ "$ERROR" != "" ]]
then
    echo "Found files with improper formatting"
    exit 1
else
    echo "No formatting issues"
fi
