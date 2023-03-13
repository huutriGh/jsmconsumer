package com.aptech.jsmconsumer;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

@SpringBootApplication
public class JsmconsumerApplication implements CommandLineRunner {

	Logger logger = LoggerFactory.getLogger(JsmconsumerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JsmconsumerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		ConnectionFactory connectionFactory = new JmsConnectionFactory("amqp://huutri-pc:56722");
		Connection connection = connectionFactory.createConnection("admin", "admin");
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//Queue
		//Destination destination = session.createQueue("jms-queue");
		//Topic
		Destination destination = session.createTopic("jms-Topic");
		MessageConsumer consumer = session.createConsumer(destination);
		logger.info("Start receiving message ....");
		String body;
		do {

			Message msg = consumer.receive();
			body = ((TextMessage) msg).getText();
			logger.info("Received: " + body);

		} while (!body.equalsIgnoreCase("closed"));

		connection.close();
		
	}

}
