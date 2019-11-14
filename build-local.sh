#!/usr/bin/env bash

export NEXUS_BASE_URI=http://192.168.1.86:8081
export NEXUS_CI_USER=admin
export NEXUS_CI_PASS=admin123

./gradlew build
./gradlew archiveTestArtifacts
./gradlew publish --info
