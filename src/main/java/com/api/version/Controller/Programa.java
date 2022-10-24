package com.api.version.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.api.version.Dao.Query;
import com.api.version.Model.Objeto;
import com.functions.Functions;

@RestController
@CrossOrigin(origins = "*")
public class Programa {
    
    @GetMapping("/programa")
    public List<HashMap<String, Object>> getPrograms(){
        List<HashMap<String,Object>> l = new ArrayList<>();
        Query.isOpen(true);
        Object[] psql = {"select id,nome,user,status,date_format(data_cadastro,'%d/%m/%Y %H:%i:%S') as data_cadastro from tb_programa WHERE status = 0","objeto"};
        var itens = Query.query(psql);
        Query.isOpen(false);

        for (int i = 0; i < itens.size(); i++) {
            Objeto obj = (Objeto)itens.get(i);
            HashMap<String,Object> map = new HashMap<>(); 
			for (int j = 0; j < obj.getKeys().size(); j++) {
				map.put(obj.getKeys().get(j).toString(), obj.getsFirst(obj.getKeys().get(j).toString()));
			}
            l.add(map);
        }

        return l;
    }

    @GetMapping("/programa/{id}")
    public List<HashMap<String, Object>> getProgram(@PathVariable String id){
        List<HashMap<String,Object>> l = new ArrayList<>();
        Query.isOpen(true);
        Object[] psql = {"select id,nome,user,status,date_format(data_cadastro,'%d/%m/%Y %H:%i:%S') as data_cadastro from tb_programa WHERE status = 0 AND id = '"+id+"'","objeto"};
        var itens = Query.query(psql);
        Query.isOpen(false);

        for (int i = 0; i < itens.size(); i++) {
            Objeto obj = (Objeto)itens.get(i);
            HashMap<String,Object> map = new HashMap<>(); 
			for (int j = 0; j < obj.getKeys().size(); j++) {
				map.put(obj.getKeys().get(j).toString(), obj.getsFirst(obj.getKeys().get(j).toString()));
			}
            l.add(map);
        }
        
        return l;
    }

    @PostMapping("/programa")
    public ResponseEntity<Object> saveProgram(@RequestBody Map<String, String> mapping){
        Object[] p = {"string",mapping.get("nome"),mapping.get("user")};
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);
        if(Functions.verify(p)){
            response = new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }else{
            Query.isOpen(true);
            String type = "INSERT INTO";
            String where = "";
            boolean right = true;
            if(!Functions.isNull(mapping.get("id"))){
                if(Query.Count("SELECT COUNT(*) FROM tb_programa WHERE id = '"+mapping.get("id")+"' AND status = 0").equals("0")){
                    response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    right = false;
                }else{
                    type = "UDPATE";
                    where = " WHERE id = '"+mapping.get("id")+"'";
                }
            }

            if(right){
                String sql = type+" tb_programa SET nome = '"+mapping.get("nome")+"',user='"+mapping.get("user")+"'"+where;
                if(!Query.CED(sql)){
                    response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        Query.isOpen(false);
        return response;
    }

    @GetMapping("/programa/delete/{id}")
    public ResponseEntity<Object> DisableProgrma(@PathVariable String id){
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.OK);

        Query.isOpen(true);
        if(Query.Count("SELECT COUNT(*) FROM tb_programa WHERE id = '"+id+"' AND status = 0").equals("0")){
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            if(!Query.CED("UPDATE tb_programa SET status = 1 WHERE id = '"+id+"'")){
                response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        Query.isOpen(false);
        return response;
    }

    @GetMapping("/download/{programa}")
	public ResponseEntity<InputStreamResource> downloadVersion(@PathVariable String programa) throws FileNotFoundException{

		Query.isOpen(true);

		ResponseEntity<InputStreamResource> rs = new ResponseEntity<>(HttpStatus.OK);

		String idprograma = Query.Count("SELECT id FROM tb_programa WHERE nome = '"+programa+"'");
		//Verifica se o programa existe ou se está ativo
		if(Query.Count("SELECT COUNT(*) FROM tb_programa WHERE nome = '"+programa+"' AND status = 0").equals("0")){
			
			rs = new ResponseEntity<>(HttpStatus.NOT_FOUND);

		}else{

			//Passa de base 64 para byte
			byte[] bytes = Base64.getDecoder().decode(Query.search("SELECT file_prog FROM tb_programa WHERE id = '"+idprograma+"'").get(0));
			File f;
			try {
				//Cria um arquivo temporário para escrever o programa nele
				f = Files.createTempFile(idprograma, ".jar").toFile();
				FileOutputStream fo = new FileOutputStream(f);
				fo.write(bytes);
				fo.close();
				InputStreamResource resource = new InputStreamResource(new FileInputStream(f));
			
				HttpHeaders ht = new HttpHeaders();
				
				//Da o nome ao arquvio
				ht.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+programa+".jar\"");
				rs = ResponseEntity.ok()
				.headers(ht)
				.contentLength(f.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
				//deleta o arquivo temporário
				f.delete();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				rs = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			

		
		}

		Query.isOpen(false);
		return rs;

	}

	@PostMapping("/upload/{programa}")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String programa) {
       
		Query.isOpen(true);
		ResponseEntity<Object> rs = new ResponseEntity<>(HttpStatus.OK);
		try {
			String idprograma = Query.Count("SELECT id FROM tb_programa WHERE nome = '"+programa+"'");
			//Verifica se o programa existe e se está ativo
			if(Query.Count("SELECT COUNT(*) FROM tb_programa WHERE nome = '"+programa+"' AND status = 0").equals("0")){

				rs = new ResponseEntity<>(HttpStatus.NOT_FOUND);

			}
			else{
				//Passa o arquivo para base64 para salvar no banco de dados
				String str_file = new String(Base64.getEncoder().encode(file.getBytes()));
				//salva o arquivo no banco
				Query.CED("UPDATE tb_programa SET file_old = file_prog, file_prog = '"+str_file+"' WHERE id = '"+idprograma+"' AND status = 0");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rs = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Query.isOpen(false);
		return rs;
    }


}
