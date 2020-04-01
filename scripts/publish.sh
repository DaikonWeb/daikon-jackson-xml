#!/usr/bin/env sh
PROJECT_DIR=$(cd $(dirname $0)/..; pwd -P)
PROJECT_NAME=$(basename $PROJECT_DIR)
TAG=$1

cd ${PROJECT_DIR}

if git rev-parse "$TAG" >/dev/null 2>&1; then
  echo "tag $TAG already exists";
  exit 0
fi

sed -i -e "s|'com.github.DaikonWeb:daikon:.*'|'com.github.DaikonWeb:daikon:${TAG}'|g" build.gradle && rm build.gradle-e
sed -i -e "s|'com.github.DaikonWeb:${PROJECT_NAME}:.*'|'com.github.DaikonWeb:${PROJECT_NAME}:${TAG}'|g" README.md && rm README.md-e
sed -i -e "s|<version>.*</version>|<version>${TAG}</version>|g" README.md && rm README.md-e

git commit -am "Release ${TAG}"
git tag $TAG
git push
git push --tags
