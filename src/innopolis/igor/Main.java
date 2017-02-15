package innopolis.igor;

public class Main {

    public static void main(String[] args) {
        People people = new People();
        people.setAge(24);
        people.setName("Anna");
        people.setSalary(1000000);
        MyXml.savePeopleToXml(people);

        People people2 = (People)MyXml.loadPeopleFromXml("file.xml");
        System.out.println(people2);
    }
}
