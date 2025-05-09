#!/usr/bin/env bash
set -e

if [ ! -f /opt/bigfile.bin ]; then
  echo "➤ Creating 2 GB file at /opt/bigfile.bin…"
  fallocate -l 2G /opt/bigfile.bin
  chmod 644 /opt/bigfile.bin
fi

echo "Starting WildFly #1 on port 8080 (no offset)…"
/opt/wf1/bin/standalone.sh \
  -b 0.0.0.0 \
  &

echo "Starting WildFly #2 on port 8180 (+100 offset)…"
/opt/wf2/bin/standalone.sh \
  -b 0.0.0.0 \
  -Djboss.socket.binding.port-offset=100 \
  &

# Wait on both so the container stays alive
wait
