package com.daniele.listatarefas;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.daniele.listatarefas.repository.UsuarioRepository;

@EnableScheduling
@SpringBootApplication
public class ListaTarefasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListaTarefasApplication.class, args);
	}

	@Autowired
    private PasswordEncoder encoder;

	@Bean //vai ser executado assim que a aplicação subir
	CommandLineRunner initDatabase(UsuarioRepository usuarioRepository){
		//SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return args -> {
			/* tarefaRepository.deleteAll();

			Tarefa t = new Tarefa();
			t.setNome("Estudar React");
			t.setData(formatter.parse("02/03/2023")); 
			t.setRepeticao(Repeticao.DIARIAMENTE);
			t.setCriadaEm(formatter.parse("02/03/2023"));
			tarefaRepository.save(t);

			Tarefa t2 = new Tarefa();
			t2.setNome("Estudar Angular");
			t2.setData(formatter.parse("02/03/2023"));
			t2.setRepeticao(Repeticao.SEMANALMENTE);
			t2.setCriadaEm(formatter.parse("02/03/2023"));
			tarefaRepository.save(t2);

			Tarefa t3 = new Tarefa();
			t3.setNome("Estudar Java");
			t3.setData(formatter.parse("03/02/2023"));
			t3.setRepeticao(Repeticao.MENSALMENTE);
			t3.setCriadaEm(formatter.parse("03/02/2023"));
			tarefaRepository.save(t3);

			Tarefa t4 = new Tarefa();
			t4.setNome("Teste data com repetição diária");
			t4.setData(formatter.parse("04/03/2023"));
			t4.setRepeticao(Repeticao.MENSALMENTE);
			t4.setCriadaEm(formatter.parse("03/03/2023"));
			tarefaRepository.save(t4); */

			/* usuarioRepository.deleteAll();

			Usuario u1 = new Usuario();
			u1.setNome("Daniele");
			u1.setEmail("dani@gmail.com");
			u1.setPerfil(Perfil.ADMIN);
			u1.setSenha(encoder.encode("dani123"));

			usuarioRepository.save(u1); */
			
		};
	}
	
}
