package com.dev4j.kafka.transactional;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dev4j.kafka.producers.Dev4jProducer;

public class TransactionalProducer {
	
	public static final Logger log = LoggerFactory.getLogger(TransactionalProducer.class);
	
	public static void main (String[] args) {
		
		long starttime = System.currentTimeMillis();
		Properties props=new Properties();
		props.put("bootstrap.servers","localhost:9092"); // Kafka's Broker for connection
		props.put("acks","1"); // ACK confirmation of nodes
		props.put("transactional.id","devs4j-producer-id"); 
		props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer"); // Serializer how string the key
		props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer"); // Serializer how string the value
		props.put("linger.ms", "10");
		
		try (Producer<String, String> producer = new KafkaProducer<>(props);) {	
			try {
				producer.initTransactions();
				producer.beginTransaction();
				for (int i = 0;i<100000;i++) {
					producer.send(new ProducerRecord<String, String>("devs4j-topic", String.valueOf(i), "devs4j-value"));
					if(i==50000) {
						throw new Exception("Unexpected Exception");
					}
				}
				producer.commitTransaction();
				producer.flush();
			} catch (Exception e) {
				log.error("Error", e);
				producer.abortTransaction();
			}
		} 
		
		log.info("processing time = {} ms ", System.currentTimeMillis() - starttime);
		
	}

}
