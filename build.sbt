name := "spark-experiments"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.2.1" % "provided",
  // "org.apache.spark" %% "spark-sql" % "1.2.1" % "provided",
  // "org.apache.spark" %% "spark-mllib" % "1.2.1" % "provided",
  "com.clearspring.analytics" % "stream" % "2.5.0"
)