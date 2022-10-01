package com.api.version.Model;

import java.util.ArrayList;
import java.util.List;


public class Objeto {

    public List<List<Object>> l = new ArrayList<>();
    public String toString;
    public List<String> valuestosearch = new ArrayList<>();

    public Objeto() {
    }

    public List<Object> get(String name_column) {
        name_column = name_column.toLowerCase();
        List<Object> lista = new ArrayList<>();
        if (l.isEmpty()) {
            return lista;
        }
        for (int i = 0; i < l.get(0).size(); i++) {
            if (((String) l.get(0).get(i)).toLowerCase().equals(name_column)) {
                lista = new ArrayList<>();
                for (int j = 1; j < l.size(); j++) {
                    lista.add(l.get(j).get(i));
                }
                return lista;
            }
        }
        
        lista.add("");
        return lista;
    }

    public void set(String name_column, List<Object> valores){
        name_column = name_column.toLowerCase();
        if (!valores.isEmpty()) {
            if(l.isEmpty()){
                List<Object> columns = new ArrayList<>();
                List<Object> lista_valores = new ArrayList<>();
                lista_valores.add(valores);
                columns.add(name_column);
                l.add(columns);
                l.add(lista_valores);
            }
        }
    }

    public Object getFirst(String name_column) {
        name_column = name_column.toLowerCase();
        if (l.isEmpty()) {
            return null;
        }
        for (int i = 0; i < l.get(0).size(); i++) {
            if (((String) l.get(0).get(i)).toLowerCase().equals(name_column)) {
                return l.get(1).get(i);
            }
        }
        
        return null;
    }

    public String getsFirst(String name_column){
        name_column = name_column.toLowerCase();
        if (l.isEmpty()) {
            return "";
        }
        for (int i = 0; i < l.get(0).size(); i++) {
            if (((String) l.get(0).get(i)).toLowerCase().equals(name_column)) {
                return (String)l.get(1).get(i);
            }
        }
        return "";
    }

    public void setFirst(String name_column, String Value){
        name_column =  name_column.toLowerCase();
        if (!(l.isEmpty())) {
            for (int i = 0; i < l.get(0).size(); i++) {
                if (((String) l.get(0).get(i)).toLowerCase().equals(name_column)) {
                    l.get(1).remove(i);
                    l.get(1).add(i,Value);
                }
            }  
        }
    }

    public void setNewValue(String name_column, String Value){
        l.get(0).add(name_column.toLowerCase());
        l.get(1).add(Value);
    }

    public Object sumAll(String name_column, Object number){
        name_column =  name_column.toLowerCase();
        if (!(l.isEmpty())) {
            if(number.getClass().equals(long.class)){
                long n = 0;
                
                    for (int i = 0; i < l.get(0).size(); i++) {
                        if (((String) l.get(0).get(i)).toLowerCase().equals(name_column)) {
                            try{
                                n += Long.parseLong(l.get(0).get(i).toString());
                            }catch(NullPointerException | NumberFormatException e){}
                        }
                    }
                    return n;
            }else if(number.getClass().equals(double.class)){
                double n = 0.0;

                for (int i = 0; i < l.get(0).size(); i++) {
                    if (((String) l.get(0).get(i)).toLowerCase().equals(name_column)) {
                        try{
                            n += Double.parseDouble(l.get(0).get(i).toString());
                        }catch(NullPointerException | NumberFormatException e){}
                    }
                }

                return n;
            }else if(number.getClass().equals(int.class)){
                int n = 0;

                for (int i = 0; i < l.get(0).size(); i++) {
                    if (((String) l.get(0).get(i)).toLowerCase().equals(name_column)) {
                        try{
                            n += Integer.parseInt(l.get(0).get(i).toString());
                        }catch(NullPointerException | NumberFormatException e){}
                    }
                }

                return n;
            }
        }

        return null;

        
    }

    public List<Object> getKeys(){
        if (l.isEmpty()) {
            return null;
        }
        return l.get(0);
    }

    public String toString(){
        return toString;
    }
}
