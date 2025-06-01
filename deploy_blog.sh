#!/bin/bash

set -e

echo "🔧 Inserting commit SHA..."
COMMIT_SHA=$(git rev-parse --short HEAD)

cat <<EOF > blog/src/jsMain/kotlin/com/ifochka/blog/Version.kt
package com.ifochka.blog

val commitSha: String = "$COMMIT_SHA"
EOF

echo "🔨 Building production site..."
./gradlew :blog:jsBrowserDistribution

BUILD_DIR=blog/build/dist/js/productionExecutable
TEMP_DIR=$(mktemp -d -t deploy-XXXX)
WORKTREE_DIR="$TEMP_DIR/gh-pages"

echo "🧹 Cleaning temporary deploy directory..."
git worktree prune
rm -rf "$TEMP_DIR"

mkdir -p "$WORKTREE_DIR"
git worktree add --checkout "$WORKTREE_DIR" gh-pages

echo "🧼 Cleaning old /blog folder..."
rm -rf "$WORKTREE_DIR/blog"
mkdir -p "$WORKTREE_DIR/blog"

echo "📦 Copying production files to /blog..."
cp -r $BUILD_DIR/* "$WORKTREE_DIR/blog/"

echo "📄 Copying 404.html to root of gh-pages..."
cp 404.html "$WORKTREE_DIR/404.html"

echo "🌐 Setting CNAME..."
echo ifochka.com > "$WORKTREE_DIR/CNAME"

echo "📤 Committing and pushing to gh-pages..."
cd "$WORKTREE_DIR"
git add .
git commit -m "🚀 Deploy blog to /blog path"
git push origin gh-pages

cd -
git worktree remove "$WORKTREE_DIR"

echo "✅ Blog successfully deployed to https://ifochka.com/blog/"
