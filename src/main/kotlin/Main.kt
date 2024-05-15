import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

fun main(args: Array<String>) {
    val properties = Properties()
    properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    properties.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
    properties.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
    properties.setProperty(ACKS_CONFIG, "1")

    val kafkaProducer: KafkaProducer<String, String> = KafkaProducer(properties)
    val record: ProducerRecord<String, String> = ProducerRecord("test-topic", "key", "test-value")

    println("record : $record")
    println("started")
    kafkaProducer.send(record)
    println("completed")
    kafkaProducer.close()
}
