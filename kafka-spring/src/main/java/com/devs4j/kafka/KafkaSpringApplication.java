package com.devs4j.kafka;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import com.devs4j.kafka.models.Devs4jTransaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

@SpringBootApplication
public class KafkaSpringApplication {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private ObjectMapper mapper;
	
	private static final Logger log = LoggerFactory.getLogger(KafkaSpringApplication.class);

	@KafkaListener(id = "devs4jId", autoStartup = "true"
			, topics ="devs4j-topic",containerFactory="listenerContainerFactory",groupId="devs4j", 
			properties ={"max.poll.interval.ms:4000","max.poll.records:50"})
	public void listen(List<ConsumerRecord<String, String>> messages) {
		for(ConsumerRecord<String, String> message : messages) {
			//Devs4jTransaction transaction = mapper.readValue(message.value(), Devs4jTransaction.class);
			log.info("Partition {}, Ofset {}, Key {}, Message {} ", message.partition(), 
					message.offset(), message.key(), message.value());
		}
		//log.info("Batch complete.");
	}
	
	@Scheduled(fixedRate = 10000)
	public void SendKafkaMessages() throws JsonProcessingException {
		Faker faker = new Faker();
		for(int i=0; i < 10000; i++) {
			Devs4jTransaction transaction = new Devs4jTransaction();
			transaction.setNombre(faker.name().firstName());
			transaction.setApellido(faker.name().lastName());
			transaction.setUsername(faker.name().username());
			transaction.setMonto(faker.number().randomDouble(4, 0, 20000));
			kafkaTemplate.send("devs4j-topic", transaction.getUsername(), 
					mapper.writeValueAsString(transaction));
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringApplication.class, args);
	}

}
