#!/bin/bash

# clear results
cat /dev/null > ./results.txt

# start servers
cd akka && ./target/pack/bin/server &
akka_pid=$!
cd node && node ./server.js &
node_pid=$!

# warm up servers
sleep 5
wrk -c1 -t1 -d5 http://localhost:8080/api/user > /dev/null
wrk -c1 -t1 -d5 http://localhost:8081/api/user > /dev/null
sleep 5

for i in 1 2 3 5 8 13 21 34 55 89 144; do
  echo "Akka $i"
  wrk "-c$i" "-t$i" -d15 http://localhost:8080/api/user >> ./results.txt
  sleep 1
  echo "NodeJS $i"
  wrk "-c$i" "-t$i" -d15 http://localhost:8081/api/user >> ./results.txt
  sleep 1
done

# stop servers
sleep 5
kill -9 $akka_pid
kill -9 $node_pid

exit 0
