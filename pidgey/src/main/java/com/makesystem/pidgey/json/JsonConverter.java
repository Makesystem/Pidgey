/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.makesystem.pidgey.lang.ObjectsHelper;
import com.makesystem.pidgey.thread.ThreadPool;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author Richeli.vargas
 */
public class JsonConverter {

    private final static ObjectMapper mapper;
    private final static Map<Class, JavaType> collectionsTypes = new LinkedHashMap<>();

    static {
        mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static final <O> String toJson(final O object) throws JsonProcessingException {
        final Class objectType = object.getClass();
        if (Collection.class.isAssignableFrom(objectType)) {
            return mapper.writer().writeValueAsString(object);
        } else if (Map.class.isAssignableFrom(objectType)) {
            final Class<?> collectionType = ObjectsHelper.getCollectionType((Collection) object);
            if (collectionType == null) {
                return mapper.writer().writeValueAsString(object);
            } else {
                final JavaType type = collectionsTypes.getOrDefault(collectionType, 
                        mapper.getTypeFactory().constructCollectionType(Collection.class, collectionType));
                collectionsTypes.put(collectionType, type);                
                return mapper.writerFor(type).writeValueAsString(object);
            }
        } else {
            return mapper.writer().writeValueAsString(object);
        }
    }

    public static void main(String[] args) throws JsonProcessingException {

        final Map<Person, Integer> people = new LinkedHashMap<>();
        final Map<Action, Integer> actions = new LinkedHashMap<>();

        for (int i = 0; i < 10; i++) {
            final int index = i;
            final Person person = new Person("Fulado De tal " + index, 20);
            final Action action = new Action(person, "NASCEU");
            people.put(person, index);
            actions.put(action, index);
        }

        System.out.println(toJson(people));
        System.out.println(toJson(actions));

    }

    public static void main_list(String[] args) throws JsonProcessingException {

        final Collection<Person> people = new LinkedList<>();
        final Collection<Action> actions = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            final int index = i;
            final Person person = new Person("Fulado De tal " + index, 20);
            final Action action = new Action(person, "NASCEU");
            people.add(person);
            actions.add(action);
        }

        System.out.println(toJson(people));
        System.out.println(toJson(actions));

    }

    public static void main_multithread(String[] args) throws JsonProcessingException {

        final ThreadPool pool = new ThreadPool();
        final int numberOfTimes = 1; //pool.getNumberOfThreads() * 2;
        for (int i = 0; i < numberOfTimes; i++) {
            final int index = i;
            pool.execute(() -> {
                try {
                    final Person person = new Person("Fulado De tal " + index, 20);
                    final Action action = new Action(person, "NASCEU");
                    System.out.println(toJson(person));
                    System.out.println(toJson(action));
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }

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
