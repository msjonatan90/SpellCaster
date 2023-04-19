package com.mpc.spellcaster

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@EnableRedisRepositories
@SpringBootApplication
class SpellcasterApplication {

	static void main(String[] args) {
		SpringApplication.run(SpellcasterApplication, args)
	}

}
