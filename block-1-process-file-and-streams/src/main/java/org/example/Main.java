package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();
        Optional<Person> optionalPerson;
        try {
            personList = FileAnalyzer.processPersons(args[0]);
        } catch (InvalidLineFormatException exception) {
            exception.printStackTrace();
        }
        personList.forEach(System.out::println);
        // a)
        personList = FileAnalyzer.processPersons(args[0],
           person -> person.getAge() < 25 && person.getAge() > 0);
        personList.forEach(System.out::println);

        // b)
        personList = FileAnalyzer.processPersons(args[0],
           person ->  !person.getName().startsWith("A"));
        personList.forEach(System.out::println);

        // c)
        personList = FileAnalyzer.processPersons(args[0],
           person -> person.getAge() < 25 && person.getAge() > 0);
        optionalPerson = personList.stream().filter(person -> person.getTown().equals("Madrid")).findFirst();
        System.out.printf(optionalPerson.orElseThrow(() -> new RuntimeException("Not found.")).toString());

        // d)
        personList = FileAnalyzer.processPersons(args[0],
                person -> person.getAge() < 25 && person.getAge() > 0);
        optionalPerson = personList.stream().filter(person -> person.getTown().equals("Barcelona")).findFirst();
        System.out.printf(optionalPerson.orElseThrow(() -> new RuntimeException("Not found.")).toString());
    }
}