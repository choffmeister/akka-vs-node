#!/bin/bash

# clear results
cat /dev/null > ./results.txt

# start servers
cd akka && ./target/pack/bin/server &
cd node && node ./server.js &
sleep 5

# warm up servers
wrk -c1 -t1 -d5 http://localhost:8080/api/user > /dev/null
wrk -c1 -t1 -d5 http://localhost:8081/api/user > /dev/null
sleep 5

for i in 1 2 3 5 8 13 21 34 55 89 144; do
  echo "Akka $i"
  wrk "-c$i" "-t$i" -d5 http://localhost:8080/api/user >> ./results.txt
  sleep 1
  echo "NodeJS $i"
  wrk "-c$i" "-t$i" -d5 http://localhost:8081/api/user >> ./results.txt
  sleep 1
done

# stop servers
killall java
killall node

exit 0