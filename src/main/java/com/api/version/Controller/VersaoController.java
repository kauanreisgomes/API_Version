package com.api.version.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.version.Dao.Query;
import com.api.version.Model.Objeto;
import com.functions.Functions;

@RestController
public class VersaoController {

    @GetMapping("/versoes")
	public List<HashMap<String, String>> getVersoes(){
		
		Object[] p = {"select id,id_prog,file,data_lancamento,versao,user,status from tb_versao WHERE status = 0","objeto"};
		Query.isOpen(true);
		var item = Query.query(p);
		Query.isOpen(false);
		List<HashMap<String, String>> maps = new ArrayList<>();
		for (int i = 0; i < item.size(); i++) {
			HashMap<String, String> map = new HashMap<>();
			Objeto versao = (Objeto)item.get(i);
			for (int j = 0; j < versao.getKeys().size(); j++) {
				map.put(versao.getKeys().get(j).toString(), versao.getsFirst(versao.getKeys().get(j).toString()));
			}
			if(!Functions.isNull(versao.getsFirst("file"))){
				try {
					File f =new File("C:\\Mais Sabor\\teste.pdf");
					f.createNewFile();
					OutputStream os = new FileOutputStream(f);
					byte[] bytes  = Base64.getDecoder().decode(versao.getsFirst("file").getBytes());
					os.write(bytes);
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			maps.add(map);
		}
		return maps;

	}

	@PostMapping("/versoes/save")
	public ResponseEntity<Object> setVersao(@RequestBody Map<String, String> mapping){
		
		String sql = "";
		if(Functions.isNull(mapping.get("versao"))){
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		Object[] verify = {"long",mapping.get("id_prog"),mapping.get("user"),mapping.get("status")};
		if(Functions.verify(verify)){
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		String type = "INSERT INTO";
		String where = "";
		if(!Functions.isNull(mapping.get("id"))){
			Query.isOpen(true);
			if(!Query.Count("SELECT COUNT(*) FROM tb_versao WHERE id ='"+mapping.get("id")+"'").equals("0")){
				type = "UPDATE";
				where = " WHERE id = '"+mapping.get("id")+"'";
			}else{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			Query.isOpen(false);
		}

		sql = type+" tb_versao SET id_prog = '"+mapping.get("id_prog")+"',user='"+mapping.get("user")+"',status='"+mapping.get("status")+"'"
			+",versao='"+mapping.get("versao")+"'";
		if(!Functions.isNull(mapping.get("file"))){
			sql = ",file='"+mapping.get("file")+"'";
		}
		sql += where;
		
		Query.isOpen(true);
		if(!Query.CED(sql)){
			Query.isOpen(false);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Query.isOpen(false);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/versoes/saves")
	public ResponseEntity<Object> saveVersao(@RequestBody Map<String, String>[] mapping){
		String[] sql = new String[mapping.length];
		for (int i = 0; i < mapping.length; i++) {
			if(Functions.isNull(mapping[i].get("versao"))){
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			Object[] verify = {"long",mapping[i].get("id_prog"),mapping[i].get("user"),mapping[i].get("status")};
			if(Functions.verify(verify)){
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}

			String type = "INSERT INTO";
			String where = "";
			if(!Functions.isNull(mapping[i].get("id"))){
				Query.isOpen(true);
				if(!Query.Count("SELECT COUNT(*) FROM tb_versao WHERE id ='"+mapping[i].get("id")+"'").equals("0")){
					type = "UPDATE";
					where = " WHERE id = '"+mapping[i].get("id")+"'";
				}else{
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
				Query.isOpen(false);
			}

			sql[i] = type+" tb_versao SET id_prog = '"+mapping[i].get("id_prog")+"',user='"+mapping[i].get("user")+"',status='"+mapping[i].get("status")+"'"
			+",versao='"+mapping[i].get("versao")+"'";
			if(!Functions.isNull(mapping[i].get("file"))){
				sql[i] = ",file='"+mapping[i].get("file")+"'";
			}
			sql[i] += where+";";
		}

		Query.isOpen(true);
		for (int i = 0; i < sql.length; i++) {
			if(!Query.CED(sql[i])){
				Query.isOpen(false);
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
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
