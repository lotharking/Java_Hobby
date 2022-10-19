package com.devs4j.kafka;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.concurrent.ListenableFuture;

@SpringBootApplication
public class KafkaSpringApplication implements CommandLineRunner{
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(KafkaSpringApplication.class);

	@KafkaListener(id = "devs4jId", autoStartup = "false"
			, topics ="devs4j-topic",containerFactory="listenerContainerFactory",groupId="devs4j", 
			properties ={"max.poll.interval.ms:4000","max.poll.records:10"})
	public void listen(List<ConsumerRecord<String, String>> messages) {
		log.info("Start reading messages...");
		for(ConsumerRecord<String, String> message : messages) {
			log.info("Partition = {}, Offset = {}, Key = {}, Value = {} ", message.partition(),message.offset(),message.key(),message.value());			
		}
		log.info("Process complete.");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringApplication.class, args);
	}
	
	@Scheduled(fixedDelay = 1000, initialDelay = 5000)
	public void print() {
		log.info("Devs4j rocks!");
	}

	@Override
	public void run(String... args) throws Exception {
		for(int i=0;i<100;i++) {
			kafkaTemplate.send("devs4j-topic", String.valueOf(i), String.format("Sample message %d", i));			
		}
	}

}
