package innopolis.igor;

import java.io.Serializable;

/**
 * Created by igor on 10.02.2017.
 */
public class People implements Serializable{
    private String name;
    private int age;
    private double salary;

    protected void paySalary(){
        System.out.println("I have salary " + salary);
    }

    public People(String name, Integer age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public People(){
System.out.println("People()");
        this.name = "";
        this.age = 0;
        this.salary = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Name: " + this.getName() + "\r\nAge: " + this.getAge() + "\r\nSalary: " +this.getSalary();
    }
}
