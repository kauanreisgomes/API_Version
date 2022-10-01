package com.api.version;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.functions.dao.Query;
import com.functions.models.Mail;
import com.functions.models.Objeto;



@SpringBootApplication
public class VersionApplication {

	public static void main(String[] args) {
		Objeto item = new Objeto();
		Query q = new Query("jdbc:mysql://localhost:3306/api?useTimezone=true&serverTimezone=America/Sao_Paulo", "api", "123456789");
		q.isOpen(true);
		System.out.println(q.Count("SELECT count(*) FROM tb_versao"));
		q.isOpen(false);
		//SpringApplication.run(VersionApplication.class, args);
	}

	

}
