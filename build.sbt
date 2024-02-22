ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

name := "spotify-mood-app"

lazy val root = (project in file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    libraryDependencies ++= List(
      "org.scala-js" %%% "scalajs-dom" % "2.8.0",
      "com.lihaoyi" %%% "utest" % "0.7.4" % "test",
      "io.spray" %%  "spray-json" % "1.3.6",
      "pt.kcry" %%% "sha" % "2.0.1"
    ),
    testFrameworks += new TestFramework("utest.runner.Framework"),
    scalaJSUseMainModuleInitializer := true
  )

jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()
resolvers += "Akka library repository".at("https://repo.akka.io/maven")

val AkkaVersion = "2.8.0"
val AkkaHttpVersion = "10.5.2"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion
)
libraryDependencies += "io.spray" %%  "spray-json" % "1.3.6"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion
