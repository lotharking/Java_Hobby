package com.dev4j.kafka.producers;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dev4jProducer {
	
	public static final Logger log = LoggerFactory.getLogger(Dev4jProducer.class);
			
	public static void main (String[] args) {
		
		Properties props=new Properties();
		props.put("bootstrap.servers","localhost:9092"); // Kafka's Broker for connection
		props.put("acks","1"); // ACK confirmation of nodes
		props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer"); // Serializer how string the key
		props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer"); // Serializer how string the value
		props.put("linger.ms", "10");
		
		try (Producer<String, String> producer = new KafkaProducer<>(props);) {			
			for (int i = 0;i<100;i++) {
				producer.send(new ProducerRecord<String, String>("devs4j-topic", "devs4j-key", String.valueOf(i)));				
			}
			producer.flush();
		} 
		
	}

}
