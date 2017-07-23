#!/bin/bash

cd simple-video-share-frontend && npm run build && cp -r dist index.html ../simple-video-share-full/src/main/resources/static
