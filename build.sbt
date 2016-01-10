name := "spark-experiments"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.2.1" % "provided",
  // "org.apache.spark" %% "spark-sql" % "1.2.1" % "provided",
  // "org.apache.spark" %% "spark-mllib" % "1.2.1" % "provided",
  "com.clearspring.analytics" % "stream" % "2.5.0",
  "com.twitter" % "algebird-core_2.10" % "0.10.2",
  "com.hadoop.gplcompression" % "hadoop-lzo" % "0.4.19" % "provided"
)

resolvers ++= Seq(
  "HadoopLzo" at "http://maven.twttr.com"
)
