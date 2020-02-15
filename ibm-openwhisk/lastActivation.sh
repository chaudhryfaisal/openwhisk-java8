#!/usr/bin/env sh
. ./_config.sh

ID="$($WSK activation list "${ACTION_NAME}" -l 1 | tail -1 | cut -d' ' -f3)"
INFO="$($WSK activation get $ID)"

echo "$INFO"
echo "$ID"