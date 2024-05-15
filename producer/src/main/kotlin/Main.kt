package org.example

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

fun main(args: Array<String>) {
//    val topic = args[0]
//    val partition: Int = args[1].toInt()
//    val key = args[2]
//    val value = args[3]

    producing("test-topic", 1, "test-key", "test-value")
}

private fun producing(topic: String, partition: Int, key: String, value: String) {
    val properties = Properties()
    properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, "3.37.179.157:9092")
    properties.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
    properties.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
    properties.setProperty(ACKS_CONFIG, "1")

    val kafkaProducer: KafkaProducer<String, String> = KafkaProducer(properties)
    val record: ProducerRecord<String, String> = ProducerRecord(topic, partition, key, value)
    kafkaProducer.send(record)
}

