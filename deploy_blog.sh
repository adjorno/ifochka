#!/bin/bash

set -e

# === Config ===
BLOG_MODULE=blog
BUILD_DIR=$BLOG_MODULE/build/kotlin-webpack/js/productionExecutable
TEMP_DIR=/tmp/gh-pages-deploy
WORKTREE_DIR=/tmp/gh-pages-worktree
CNAME_VALUE=ifochka.com

echo "🔨 Building production site..."
./gradlew :$BLOG_MODULE:jsBrowserProductionWebpack

echo "🧹 Cleaning temporary deploy directory..."
rm -rf $TEMP_DIR
mkdir -p $TEMP_DIR

echo "📦 Copying production files..."
cp -r $BUILD_DIR/* $TEMP_DIR/
rm -f $TEMP_DIR/*.map # Remove large source maps

echo "$CNAME_VALUE" > "$TEMP_DIR/CNAME"

echo "🌲 Preparing git worktree for gh-pages..."
git worktree remove $WORKTREE_DIR --force || true
git worktree prune
git worktree add $WORKTREE_DIR gh-pages

echo "🚚 Copying files into gh-pages branch..."
rm -rf $WORKTREE_DIR/*
cp -r $TEMP_DIR/* $WORKTREE_DIR/

echo "✅ Committing and pushing to gh-pages..."
cd $WORKTREE_DIR
git add .
git commit -m "🚀 Deploy blog to GitHub Pages" || echo "✅ No changes to commit"
git push origin gh-pages

echo "🧽 Cleaning up worktree..."
cd -
git worktree remove $WORKTREE_DIR --force

echo "🎉 Deployment complete: https://ifochka.com/blog"
