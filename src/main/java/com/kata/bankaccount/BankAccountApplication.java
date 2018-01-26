package com.kata.bankaccount;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;


@SpringBootApplication
@Slf4j
public class BankAccountApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(BankAccountApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		log.info("Hello ! Application started with command-line arguments: {} :)", Arrays.toString(strings));
	}

}
