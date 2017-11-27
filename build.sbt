name := "spark-experiments"

version := "1.0"

scalaVersion := "2.10.4"

val sparkVersion = "1.6.3"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  // "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  // "org.apache.spark" %% "spark-mllib" % sparkVersion % "provided",
  "com.clearspring.analytics" % "stream" % "2.5.0",
  "com.twitter" % "algebird-core_2.10" % "0.10.2",
  "com.hadoop.gplcompression" % "hadoop-lzo" % "0.4.19" % "provided"
)

resolvers ++= Seq(
  "HadoopLzo" at "http://maven.twttr.com"
)
