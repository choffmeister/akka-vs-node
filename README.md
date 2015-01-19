# Akka vs NodeJS

## Dependencies

You must have [MongoDB](http://www.mongodb.org/), [SBT](http://www.scala-sbt.org/), [NodeJS](http://nodejs.org/) and [wrk](https://github.com/wg/wrk) installed.

## Usage

~~~ bash
$ mongo localhost:27017/akkavsnode fill.js
$ cd akka && sbt pack
$ cd node && npm install
$ ./run.sh
~~~
