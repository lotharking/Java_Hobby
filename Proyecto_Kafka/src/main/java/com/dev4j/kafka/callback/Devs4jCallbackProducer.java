package com.dev4j.kafka.callback;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Devs4jCallbackProducer {

	public static final Logger log = LoggerFactory.getLogger(Devs4jCallbackProducer.class);

	public static void main(String[] args) {

		long starttime = System.currentTimeMillis();
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092"); // Kafka's Broker for connection
		props.put("acks", "1"); // ACK confirmation of nodes
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer"); // Serializer how string
																								// the key
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer"); // Serializer how
																									// string the value
		props.put("linger.ms", "10");

		try (Producer<String, String> producer = new KafkaProducer<>(props);) {
			for (int i = 0; i < 10000; i++) {
				producer.send(new ProducerRecord<String, String>("devs4j-topic", String.valueOf(i), "devs4j-value"),
						(metadata, exception) -> {
							if (exception != null) {
								log.info("there was an error {}", exception.getMessage());
							}
							log.info("Offset {}, Partition {}, Topic {}", metadata.offset(), metadata.partition(),
									metadata.topic());
						});
			}
			producer.flush();
		}

		log.info("processing time = {} ms ", System.currentTimeMillis() - starttime);

	}

}
