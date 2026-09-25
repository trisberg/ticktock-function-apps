package com.example.stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@SpringBootApplication
public class LogSinkApplication {

	@Bean
    IntegrationFlow logConsumerFlow() {
		return IntegrationFlow.from(MessageConsumer.class, (gateway) -> gateway.beanName("logConsumer"))
				.handle((payload, headers) -> {
					if (payload instanceof byte[]) {
						return new String((byte[]) payload);
					}
					return payload;
				})
				.log(LoggingHandler.Level.INFO, "log-consumer", "payload")
				.get();
	}

	private interface MessageConsumer extends Consumer<Message<?>> {}

	public static void main(String[] args) {
		SpringApplication.run(LogSinkApplication.class, args);
	}

}
