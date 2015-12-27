val akkaVer = "2.3.11"
val logbackVer = "1.1.3"
val scalaVer = "2.11.7"

lazy val compileOptions = Seq(
  "-unchecked",
  "-deprecation",
  "-language:_",
  "-target:jvm-1.7",
  "-encoding", "UTF-8"
)

lazy val commonDependencies = Seq(
  "com.typesafe.akka"        %% "akka-actor"                 % akkaVer,
  "com.typesafe.akka"        %% "akka-slf4j"                 % akkaVer,
  "ch.qos.logback"           %  "logback-classic"            % logbackVer
)

lazy val fttas = project in file(".")
name := "Death&Taxes"
version := "0.0.1"
scalaVersion := scalaVer
scalacOptions ++= compileOptions
unmanagedSourceDirectories in Compile := List((scalaSource in Compile).value)
parallelExecution in ThisBuild := false
libraryDependencies ++= commonDependencies