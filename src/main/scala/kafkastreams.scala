import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.streaming.api.java.JavaDStream
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming._
import com.fasterxml.jackson.databind.ObjectMapper

/**
  * Created by shishir on 28/06/17.
  */
object kafkastreams {
  def main(args: Array[String]){

    //val conf = new SparkConf().setMaster("yarn-client").setAppName("kafkastreams")
    val conf = new SparkConf().setAppName("kafkastreams")

    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc, Seconds(1))

    val kafkaConf = Map(
      "bootstrap.servers" -> "10.144.102.121:9092,10.144.102.122:9092,10.144.102.123:9092,10.144.102.124:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "kafka",
      "auto.offset.rest" -> "smallest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val topics = Array("stream-1")

    val stream = KafkaUtils.createDirectStream(
      ssc,
      PreferConsistent,
      Subscribe[String, String] (topics, kafkaConf)
    )

    print("***shishir****")

    stream.foreachRDD(rdd => {
      rdd.collect().foreach({
        str =>
          println("Data -> " + str)

      })
    })

    /*
    val records = stream.map(record => (record.key(), record.value()))

    val lines = records.reduceByKey((key1, key2) => key1+key2)
    lines.print()
  */
    ssc.start()
    ssc.awaitTermination()
  }
}