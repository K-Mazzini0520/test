package com.example.demo.model;

import java.util.List;

public class Pokemon {
    private String name;
    private int height;
    private List<Stat> stats;
    private List<Type> types;
    private Sprites sprites;

    public String getName() 
    {
    	return name;
    } 
    public void setName(String name) 
    {
    	this.name = name;
    }
    
    public int getHeight() 
    {
    	return height;
    } 
    public void setHeight(int height) 
    {
    	this.height = height;
    }
    
    public List<Type> getTypes() 
    {
    	return types;
    } 
    public void setTypes(List<Type> types) 
    {
    	this.types = types;
    }
    
    public List<Stat> getStats() 
    {
    	return stats;
    } 
    public void setStats(List<Stat> stats) 
    {
    	this.stats = stats;
    }
    
    public Sprites getSprites() 
    {
    	return sprites;
    } 
    public void setSprites(Sprites sprites) 
    {
    	this.sprites = sprites;
    }

    public static class Type {
        private TypeInfo type;
        private String url;

        public TypeInfo getType() {
        	return type;
        }
        
        public void setType(TypeInfo type) {
        	this.type = type;
        }
        public String getUrl() {
        	return url;
        }
        
        public void setUrl(String url) {
        	this.url = url;
        }
        

        public static class TypeInfo {
            private String name;

            public String getName() {
            	return name;
            }
            
            public void setName(String name) {
            	this.name = name;
            }
        }
    }
    public static class Stat {
        private int base_stat;

        public int getBase_stat() {
        	return base_stat;
        }
        
        public void setBase_stat(int base_stat) {
        	this.base_stat = base_stat;
        }
        


    }

    public static class Sprites {
        private String front_default;

        public String getFront_default() {
        	return front_default;
        }
        
        public void setFront_default(String front_defalt) {
        	this.front_default = front_defalt;
        }
    }
}