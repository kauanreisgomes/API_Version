package com.api.version.Controller;

import java.util.ArrayList;
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

}
