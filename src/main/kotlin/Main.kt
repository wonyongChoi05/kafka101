import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig.*
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.time.LocalDateTime
import java.time.LocalDateTime.*
import java.util.*
import kotlin.random.Random

fun main(args: Array<String>) {
    val properties = Properties()
    properties.setProperty(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    properties.setProperty(KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
    properties.setProperty(VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java.name)
    properties.setProperty(ACKS_CONFIG, "1")

    val kafkaProducer: KafkaProducer<String, String> = KafkaProducer(properties)

    val words =
        listOf("apple", "banana", "orange", "grape", "melon", "kiwi", "strawberry", "pineapple", "peach", "plum")

    val random = Random(System.currentTimeMillis())
    val timer = Timer()
    val hour = 3600000L

    timer.scheduleAtFixedRate(object : TimerTask() {
        override fun run() {
            val now = now()
            if (now.minute == 0 && now.second == 0) {
                val number = random.nextInt(1, 10001)
                for (i: Int in 1..number) {
                    val word = words[random.nextInt(words.size)]
                    val record: ProducerRecord<String, String> = ProducerRecord("test-topic", now.hour.toString(), word)
                    kafkaProducer.send(record)
                    println("Sent message: $number -> $word")
                }
            }
        }
    }, 0, hour)

    println("Producer started. Press any key to stop...")
    readlnOrNull()

    timer.cancel()
    kafkaProducer.close()
    println("Producer stopped.")
}
