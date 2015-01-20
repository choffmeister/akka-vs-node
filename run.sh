#!/bin/bash

WARMUP_DURATION="5"
DURATION="60"

# clear results
cat /dev/null > ./results.txt
echo -e "System\tConcurrency\tRequestsPerSec\tLatencyAvg\tLatency50\tLatency90\tLatency99" >>./results.txt

# start servers
cd akka && ./target/pack/bin/server >/dev/null &
akka_pid=$!
cd node && node ./server.js >/dev/null &
node_pid=$!

# warm up servers
sleep 1
wrk -c1 -t1 "-d$WARMUP_DURATION" http://localhost:8080/api/user >/dev/null
wrk -c1 -t1 "-d$WARMUP_DURATION" http://localhost:8081/api/user >/dev/null
sleep 1

for CONCURRENCY in 1 2 4 8 16 32 64 128 256; do
  echo -e -n "akka\t$CONCURRENCY\t" >>./results.txt
  wrk --script ./report.lua "-c$CONCURRENCY" "-t$CONCURRENCY" "-d$DURATION" http://localhost:8080/api/user 2>>./results.txt
  sleep 1
  echo -e -n "node\t$CONCURRENCY\t" >>./results.txt
  wrk --script ./report.lua "-c$CONCURRENCY" "-t$CONCURRENCY" "-d$DURATION" http://localhost:8081/api/user 2>>./results.txt
  sleep 1
done

# stop servers
sleep 1
kill -9 $akka_pid
kill -9 $node_pid

exit 0
