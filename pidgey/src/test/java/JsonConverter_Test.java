
import com.makesystem.pidgey.json.JsonConverter;
import com.makesystem.pidgey.monitor.MonitorHelper;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Richeli.vargas
 */
public class JsonConverter_Test {
    
    public static void readMap_Test() {
        final String finalJson = "{\"A person\":{\"name\":\"A person\",\"age\":20}}";
        final Person person = new Person("A person", 20);
        final Map<String, Person> map = new LinkedHashMap<>();        
        map.put(person.getName(), person);
        
        MonitorHelper.execute(() -> finalJson.equals(JsonConverter.write(map))).print();
    }
    
    public static void main(String[] args) {
        
        readMap_Test();
    }
    
    
    public static class Person {

        private String name;
        private int age;

        public Person() {
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" + "name=" + name + ", age=" + age + '}';
        }
    }

    public static class Action {

        private Person person;
        private String action;

        public Action() {
        }

        public Action(Person person, String action) {
            this.person = person;
            this.action = action;
        }

        public Person getPerson() {
            return person;
        }

        public void setPerson(Person person) {
            this.person = person;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        @Override
        public String toString() {
            return "Action{" + "person=" + person + ", action=" + action + '}';
        }
    }
    
}
