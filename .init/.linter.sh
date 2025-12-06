#!/bin/bash
cd /home/kavia/workspace/code-generation/online-shopping-mall-system-287261-287271/springboot_backend
./gradlew check
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi
