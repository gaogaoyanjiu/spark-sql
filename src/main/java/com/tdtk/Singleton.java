package com.tdtk;

public class Singleton {
    
    private Integer id;
    private String name;
    private Integer age;
    // 私有构造
    private Singleton() {}
    
    private static Singleton single = null;
    
    // 静态代码块
    static{
        single = new Singleton();
    }
    
    public static Singleton getInstance() {
        return single;
    }
    
    
    public static void main(String [] args){
    
        Singleton singleton = new Singleton();
        singleton.setId(1);
        singleton.setName("tom");
        singleton.setAge(23);
        System.out.println(singleton);
        
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Singleton{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
