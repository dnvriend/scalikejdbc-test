# scalikejdbc-test
A small study project on [scalikejdbc](http://scalikejdbc.org/) with the [playframework](https://www.playframework.com/)

## Usage
- clone the project
- sbt test

## Notice
You should, for now, clone `git@github.com:dnvriend/scalikejdbc-play-support.git` and do `sbt publishLocal` for it to work.

## Structure
The project consist of some test cases that all extend from TestSpec that setup a GuiceApplication (ie play application)
and launches the tests.

Have fun!