package com.api.version.Controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.version.VersionApplication;
import com.functions.Functions;
import com.functions.dao.Query;
import com.functions.models.FileDE;
import com.functions.models.Objeto;

@RestController
public class Gpe {

    Query query;

    private String password = "Y29uZWN0aW9uX2dwZQ==";
    
    public Gpe(){
        byte[] bytes;
        try {
            bytes = VersionApplication.class.getResourceAsStream("json/config.json").readAllBytes();
            JSONArray array = Functions.JsonReader(bytes).getJSONArray("databases");
            JSONObject json = Functions.JsonReader(VersionApplication.class.getResourceAsStream("json/server.json").readAllBytes());
            for (int i = 0; i < array.length(); i++) {
                if(json.getString("server").equals(array.getJSONObject(i).getString("name"))){
                    if(array.getJSONObject(i).getString("type").equals("mysql")){
                        query = new Query(array.getJSONObject(i).getString("url"),array.getJSONObject(i).getString("user"),array.getJSONObject(i).getString("password"));
                    }
                    break;
                }
            }
           
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
    }

    @PostMapping("/gpe/query")
    public String Query(@RequestBody HashMap<String,String> map){

        try{
            Object[] verify = {"string",map.get("sql"),map.get("token"),map.get("user"),map.get("data")};
            if(!Functions.verify(verify)){
                String pw = map.get("token")+map.get("user")+map.get("data")+new String(Base64.getDecoder().decode(password.getBytes()));
                Object[] parametros = {1, pw, map.get("sql")};
               // Object[] parametros = {1,map.get("sql")};
            
                String sql = new String(FileDE.dencode(parametros));

                //String type = map.get("type");
                query.isOpen(true);
                Object[] p = {sql,"objetos"};
                Objeto item = (Objeto)query.query(p).get(0);
                query.isOpen(false);

               

                parametros[2] = new JSONObject(item.getHashMap()).toString();

                return new String(FileDE.encode(parametros));

            }else{
                return null;
            }
        }catch(Exception e){
            return null;
        }
       
        
    }

    @PostMapping("/gpe/ced")
    public HashMap<String,String> CED(@RequestBody HashMap<String,String> map){

        try{
            Object[] verify = {"string",map.get("sql"),map.get("token"),map.get("user"),map.get("data")};
            if(!Functions.verify(verify)){

                String pw = map.get("token")+map.get("user")+map.get("data")+new String(Base64.getDecoder().decode(password.getBytes()));
                Object[] parametros = {1, pw, map.get("sql")};

                
                String sql = new String(FileDE.dencode(parametros));

                query.isOpen(true);
                Object[] p = {sql};
                boolean b = !query.CED(p);
                query.isOpen(false);



                map.put("Return", b+"");

                return map;
            }else{
                return null;
            }
        }catch(Exception e){
            return null;
        }
    }
   
}
