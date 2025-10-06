package com.example.automovel;

import com.example.automovel.model.Role;
import com.example.automovel.model.RoleName;
import com.example.automovel.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AutomovelApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomovelApplication.class, args);
	}

	@Bean
	public CommandLineRunner initializer(RoleRepository roleRepository) {
		return args -> {
			// Cria ROLE_CLIENTE se não existir
			if (roleRepository.findByName(RoleName.ROLE_CLIENTE).isEmpty()) {
				Role clienteRole = new Role();
				clienteRole.setName(RoleName.ROLE_CLIENTE);
				roleRepository.save(clienteRole);
			}
			// Cria ROLE_EMPRESA se não existir
			if (roleRepository.findByName(RoleName.ROLE_EMPRESA).isEmpty()) {
				Role empresaRole = new Role();
				empresaRole.setName(RoleName.ROLE_EMPRESA);
				roleRepository.save(empresaRole);
			}
			// Cria ROLE_BANCO se não existir
			if (roleRepository.findByName(RoleName.ROLE_BANCO).isEmpty()) {
				Role bancoRole = new Role();
				bancoRole.setName(RoleName.ROLE_BANCO);
				roleRepository.save(bancoRole);
			}
		};
	}
}