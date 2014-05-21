import sbt._
import Keys._

object Build extends Build {

  lazy val project = Project("root", file(".")).settings(

    // basics
    name         := "scala-benchmarks",
    organization := "com.danieltrinh",
    version      := "0.1.0",
    scalaVersion := "2.10.3",

    // dependencies
    libraryDependencies ++= Seq(
      "com.google.code.java-allocation-instrumenter" % "java-allocation-instrumenter" % "2.0",
      "com.google.code.gson"                         % "gson"                         % "1.7.1",
      "com.github.scala-blitz"                       %% "scala-blitz"                 % "1.0-M1",
      "org.scalaz"                                   %% "scalaz-core"                 % "7.0.6",
      "org.json4s"                                   %% "json4s-jackson"              % "3.2.8",
      "org.scalacheck"                               %% "scalacheck"                  % "1.11.3",
      "com.github.scala-incubator.io"                %% "scala-io-core"               % "0.4.2",
      "com.github.scala-incubator.io"                %% "scala-io-file"               % "0.4.2"
    ),
    resolvers += "sonatypeSnapshots" at "http://oss.sonatype.org/content/repositories/snapshots",

    // enable forking in run
    fork in run := true,

    // Uncomment this to have compiler print scala code without any scala features.
    // scalacOptions ++= Seq("-print"),

    // we need to add the runtime classpath as a "-cp" argument to the `javaOptions in run`, otherwise caliper
    // will not see the right classpath and die with a ConfigurationException
    javaOptions in run <++= (fullClasspath in Runtime) map { cp => Seq("-cp", sbt.Build.data(cp).mkString(":")) }

  )
}