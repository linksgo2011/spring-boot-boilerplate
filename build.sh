#!/usr/bin/env bash

./gradlew build
./gradlew archiveTestArtifacts
./gradlew publish --info
