name := "scalikejdbc-test"

organization := "com.github.dnvriend"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.8"
libraryDependencies += "com.h2database" % "h2" % "1.4.193"
libraryDependencies += "org.scalikejdbc" %% "scalikejdbc" % "2.5.0"
libraryDependencies += "org.scalikejdbc" %% "scalikejdbc-config" % "2.5.0"
libraryDependencies +=  "org.scalikejdbc" %% "scalikejdbc-syntax-support-macro" % "2.5.0"
libraryDependencies += "org.scalikejdbc" %% "scalikejdbc-jsr310" % "2.5.0"
libraryDependencies += "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.5.0.1-SNAPSHOT"
libraryDependencies += "org.scalikejdbc" %% "scalikejdbc-test"   % "2.5.0"   % Test
libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % "2.4.12" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.4.12" % Test
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0-M1" % Test
libraryDependencies += "org.typelevel" %% "scalaz-scalatest" % "1.1.1" % Test

parallelExecution in Test := false

fork in Test := true

licenses +=("Apache-2.0", url("http://opensource.org/licenses/apache2.0.php"))

enablePlugins(PlayScala)
disablePlugins(PlayLayoutPlugin)
