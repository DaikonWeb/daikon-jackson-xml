#!/usr/bin/env sh
PROJECT_DIR=$(cd $(dirname $0)/..; pwd -P)
PROJECT_NAME=$(basename $PROJECT_DIR)
TAG=$1

sed -ie "s|'com.github.DaikonWeb:daikon:.*'|'com.github.DaikonWeb:daikon:${TAG}'|g" build.gradle
sed -ie "s|'com.github.DaikonWeb:${PROJECT_NAME}:.*'|'com.github.DaikonWeb:${PROJECT_NAME}:${TAG}'|g" README.md
sed -ie "s|<version>.*</version>|<version>${TAG}</version>|g" README.md

git commit -am "Release ${TAG}"
git tag $TAG
git push
git push --tags
