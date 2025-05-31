#!/bin/bash

set -e

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

echo "📦 Copying production files to /blog..."
mkdir -p "$WORKTREE_DIR/blog"
cp -r $BUILD_DIR/* "$WORKTREE_DIR/blog/"

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
