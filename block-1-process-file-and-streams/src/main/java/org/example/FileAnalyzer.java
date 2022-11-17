package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FileAnalyzer {

    private static final char DELIMITER = ':';

    private static Person processPerson(String[] personData) throws InvalidLineFormatException {
        Person person = new Person();
        if (personData[0].isBlank()) {
            throw new InvalidLineFormatException("Name is required");
        }
        person.setName(personData[0]);
        person.setTown(personData[1]);
        person.setAge((personData.length < 3) ? 0 : Integer.parseInt(personData[2]));
        return person;
    }

    private static void checkDelimiters(String personData) throws InvalidLineFormatException {
        // get quantity of delimiters
        long delimiters = personData.chars().filter(character -> character == DELIMITER).count();
        if (delimiters != 2) {
            throw new InvalidLineFormatException("At least 2 delimiters are required (given " + delimiters + ")");
        }
    }

    public static List<Person> processPersons(String csvPath) throws InvalidLineFormatException {
        List<Person> persons = new ArrayList<>();

        // Obtain file
        Path path = Paths.get(csvPath);
        BufferedReader reader = null;
        try {
            reader = Files.newBufferedReader(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //  Read file content
        try {
            if (reader == null) {
                throw new InvalidLineFormatException("File does not exist");
            }
            while (reader.ready()) {
                String nextPerson = reader.readLine();
                checkDelimiters(nextPerson);
                String[] personData = nextPerson.split(Character.toString(DELIMITER));
                Person person = processPerson(personData);
                persons.add(person);
            }
        } catch (IOException e) {
            e.printStackTrace(); // problem reading file
        }

        return persons;
    }

    public static List<Person> processPersons(String csvPath, Predicate<Person> filter) {
        try {
            List<Person> personList = FileAnalyzer.processPersons(csvPath);
            return personList.stream().filter(filter).toList();
        } catch (InvalidLineFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
