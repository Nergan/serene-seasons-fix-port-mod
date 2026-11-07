Fabric 1.21.1 port of Serene Seasons Fix. Serene Seasons 1.21.1 already moves the season with the day clock, so this port does not replace that tick.

`/season time info`, `sync`, `set` and `add` read and write the season clock. `/season get` says when the current dimension is outside the Serene Seasons whitelist. `config/sereneseasonsfix-server.toml` keeps `block_blacklisted` (default true).

Requires Serene Seasons Fabric 1.21.1, GlitchCore, Fabric API and Fabric Language Kotlin. License: MIT. Upstream: https://github.com/123oro321/sereneseasonsfix
