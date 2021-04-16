#!/bin/bash

# Make Bash strict
set -o nounset
set -o errexit
set -o pipefail

VERSION_NAME="$1"

# TODO: Remove this debugging nonsense
if [[ -z "${ANDROID_KEYSTORE}" ]]; then
  echo "ANDROID_KEYSTORE is unset"
else
  echo "ANDROID_KEYSTORE is unset"
fi
if [[ -z "${ANDROID_KEYSTORE_PASSWORD}" ]]; then
  echo "ANDROID_KEYSTORE_PASSWORD is unset"
else
  echo "ANDROID_KEYSTORE_PASSWORD is unset"
fi
echo "ANDROID_KEYSTORE_KEY_ALIAS = ${ANDROID_KEYSTORE_KEY_ALIAS}"
if [[ -z "${ANDROID_KEYSTORE_KEY_PASSWORD}" ]]; then
  echo "ANDROID_KEYSTORE_KEY_PASSWORD is unset"
else
  echo "ANDROID_KEYSTORE_KEY_PASSWORD is unset"
fi

set +x  # Disable debug (if enabled) so we won't leak secrets accidentally
echo "${ANDROID_KEYSTORE}" | base64 -d > /tmp/keystore.jks
exec ./gradlew app:publishApps \
  -PenableGpp=true \
  -PversionName="${VERSION_NAME}" \
  -PsigningKeystorePath=/tmp/keystore.jks
