#!/usr/bin/env sh
. _config.sh

echo "ACTION_NAME=$ACTION_NAME"
URL="$($WSK action get $ACTION_NAME --url | tail -1)"
echo "URL=$URL"
time curl -v "${URL}"