package com.api.version.Controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.version.Dao.Query;
import com.api.version.Model.Objeto;
import com.functions.Functions;

@RestController
@CrossOrigin(origins = "*")
public class Versao {

    @GetMapping("/versoes")
	public List<HashMap<String, Object>> getVersoes(){
		
		Object[] p = {"select id,id_prog,data_lancamento,versao,user,status from tb_versao WHERE status = 0","objeto"};
		Query.isOpen(true);
		var maps = Query.queryHash(p);
		Query.isOpen(false);
		
		return maps;

	}

	@GetMapping("/versoes/{value}")
	public List<HashMap<String,String>> getVersao(@PathVariable String value){
		Object[] p = {"select id,id_prog,data_lancamento,versao,user,status from tb_versao WHERE status = 0 AND (id = '"+value+"' or versao = '"+value+"') ","objeto"};
		Query.isOpen(true);
		var item = Query.query(p);
		Query.isOpen(false);
		List<HashMap<String,String>> lista = new ArrayList<>();
		for (int i = 0; i < item.size(); i++) {
			HashMap<String, String> map = new HashMap<>();
			Objeto versao = (Objeto)item.get(i);
			for (int j = 0; j < versao.getKeys().size(); j++) {
				map.put(versao.getKeys().get(j).toString(), versao.getsFirst(versao.getKeys().get(j).toString()));
			}
			
			lista.add(map);
		}

		return lista;
	}

	@GetMapping("/versao/{idprograma}")
	public HashMap<String,Object> getVersaoPrograma(@PathVariable String idprograma){
		Object[] parametros = {"""
			select
			tv.id_prog,
			tp.nome,
			tv.versao,
			tv.status,
			if(tv.status = 0,
			'ATIVO',
			'INATIVO') as situacao
		from
			tb_versao tv
		left join tb_programa tp on
			tv.id_prog = tp.id
				"""+" WHERE tv.status = 0 and tv.id_prog = '"+idprograma+"' order by tv.id desc limit 1","objeto"};
		Query.isOpen(true);
		
		var l = Query.queryHash(parametros);
		Query.isOpen(false);
		HashMap<String,Object> map = new HashMap<>();
		if(!l.isEmpty()){
			map = l.get(0);
		}

		return map;
		
	}	

	@PostMapping("/versoes/save")
	public ResponseEntity<Object> saveVersao(@RequestBody Map<String, String> mapping){
		
		String sql = "";
		if(Functions.isNull(mapping.get("versao"))){
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		Object[] verify = {"long",mapping.get("id_prog"),mapping.get("user"),mapping.get("status")};
		if(Functions.verify(verify)){
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		Query.isOpen(true);

		if(Query.Count("SELECT COUNT(*) FROM tb_programa where id = '"+mapping.get("id_prog")+"' AND status = 0").equals("0")){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		String type = "INSERT INTO";
		String where = "";
		if(!Functions.isNull(mapping.get("id"))){
			if(!Query.Count("SELECT COUNT(*) FROM tb_versao WHERE id ='"+mapping.get("id")+"' AND status = 0").equals("0")){
				type = "UPDATE";
				where = " WHERE id = '"+mapping.get("id")+"'";
			}else{
				Query.isOpen(false);
				
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
		}

		sql = type+" tb_versao SET id_prog = '"+mapping.get("id_prog")+"',user='"+mapping.get("user")+"',status='"+mapping.get("status")+"'"
			+",versao='"+mapping.get("versao")+"'";
		
		if(!Functions.isNull(mapping.get("file"))){
			sql += ",file=\""+mapping.get("file")+"\"";
		}

		sql += where;
		
		
		if(!Query.CED(sql)){
			Query.isOpen(false);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Query.isOpen(false);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/versoes/delete/{id}")
	public ResponseEntity<Object> disableVersion(@PathVariable String id){
		ResponseEntity<Object> rs = new ResponseEntity<>(HttpStatus.OK);
		Query.isOpen(true);
		if(Query.Count("SELECT COUNT(*) FROM tb_versao WHERE id ='"+id+"'").equals("0")){
			rs = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else{
			if(!Query.CED("UPDATE tb_versao SET status = 1 WHERE id = '"+id+"'")){
				rs = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			Query.isOpen(false);
		}
		return rs;
	}

	

}
