#!/usr/bin/env sh
. _config.sh
time $WSK action $ACTION_TYPE $ACTION_NAME "$ACTION_FILE" \
    --web raw --memory 128 --main "${ACTION_MAIN}" --docker openwhisk/java8action \
    -p "APPLICATION_CLASS" "$APPLICATION_CLASS" \
    $ACTION_PARAMS
./test_remote.sh
./lastActivation.sh