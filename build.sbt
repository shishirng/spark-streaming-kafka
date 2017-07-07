name := "kafkastreams"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka_2.11" % "0.10.2.1",
  "org.apache.kafka" % "kafka-clients" % "0.10.2.1",
  "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % "2.1.1",
  "org.apache.spark" % "spark-streaming_2.11" % "2.1.1" % "provided",

  "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.6",
  "com.fasterxml.jackson.core" % "jackson-core" % "2.8.6",
  "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.6"
)
// META-INF discarding
mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
{
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
}