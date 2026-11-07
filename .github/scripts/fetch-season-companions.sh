#!/usr/bin/env bash
# Downloads the Fabric 1.21.1 jars this port needs beside its own jar.
# Serene Seasons and GlitchCore are fetched by Modrinth version id.
# The version number 10.1.0.9 is shared by Forge, NeoForge and Fabric.
# NightConfig and typetools stay nested inside GlitchCore and are not separate mods.
set -euo pipefail

OUT_DIR="${1:?destination directory}"
SERENE_ID="${2:?serene seasons version id}"
GLITCH_ID="${3:?glitchcore version id}"
FABRIC_API_VERSION="${4:?fabric-api version_number}"
FABRIC_KOTLIN_VERSION="${5:?fabric-language-kotlin version_number}"
USER_AGENT="${MODRINTH_USER_AGENT:-Nergan/serene-seasons-port (https://github.com/Nergan)}"

mkdir -p "${OUT_DIR}"

get() {
  local url="$1"
  local out="$2"
  local attempt
  for attempt in 1 2 3 4 5; do
    local code
    code="$(curl -sSL -A "${USER_AGENT}" -o "${out}" -w '%{http_code}' "${url}" || true)"
    if [[ "${code}" == "200" ]]; then
      return 0
    fi
    if [[ "${code}" == "429" || "${code}" =~ ^5 ]]; then
      echo "HTTP ${code} for ${url}, retry ${attempt}" >&2
      sleep $((attempt * 15))
      continue
    fi
    echo "HTTP ${code} for ${url}" >&2
    return 1
  done
  echo "Gave up on ${url}" >&2
  return 1
}

save_primary() {
  local json="$1"
  local url filename sha512
  url="$(echo "${json}" | jq -r 'first(.files[] | select(.primary) | .url) // empty')"
  filename="$(echo "${json}" | jq -r 'first(.files[] | select(.primary) | .filename) // empty')"
  sha512="$(echo "${json}" | jq -r 'first(.files[] | select(.primary) | .hashes.sha512) // empty')"
  if [[ -z "${url}" || -z "${filename}" || -z "${sha512}" ]]; then
    echo "No primary file in Modrinth payload" >&2
    exit 1
  fi
  get "${url}" "${OUT_DIR}/${filename}"
  echo "${sha512}  ${OUT_DIR}/${filename}" | sha512sum -c -
}

fetch_version_id() {
  local id="$1"
  get "https://api.modrinth.com/v2/version/${id}" /tmp/modrinth-one.json
  local loader game
  loader="$(jq -r '.loaders | index("fabric")' /tmp/modrinth-one.json)"
  game="$(jq -r '.game_versions | index("1.21.1")' /tmp/modrinth-one.json)"
  if [[ "${loader}" == "null" || "${game}" == "null" ]]; then
    echo "Version ${id} is not Fabric 1.21.1" >&2
    exit 1
  fi
  save_primary "$(cat /tmp/modrinth-one.json)"
}

fetch_filtered() {
  local slug="$1"
  local version="$2"
  get "https://api.modrinth.com/v2/project/${slug}/version" /tmp/modrinth-versions.json
  local json
  json="$(jq -c --arg v "${version}" '
    first(.[] | select(
      .version_number == $v
      and (.loaders | index("fabric"))
      and (.game_versions | index("1.21.1"))
    )) // empty
  ' /tmp/modrinth-versions.json)"
  if [[ -z "${json}" ]]; then
    echo "No Fabric 1.21.1 file for ${slug} version ${version}" >&2
    exit 1
  fi
  save_primary "${json}"
}

fetch_version_id "${SERENE_ID}"
fetch_version_id "${GLITCH_ID}"
fetch_filtered "fabric-api" "${FABRIC_API_VERSION}"
fetch_filtered "fabric-language-kotlin" "${FABRIC_KOTLIN_VERSION}"
