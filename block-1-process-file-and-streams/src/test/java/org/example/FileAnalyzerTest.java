package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class FileAnalyzerTest {

    @Test
    void processPersonsOK() {
        List<Person> expectedPersons = List.of(new Person("Jesús", "Logroño", 41),
                new Person("Andres", "Madrid", 19),
                new Person("Angel Mari", "Valencia", 0),
                new Person("Laura Saenz", "", 23),
                new Person("Maria Calvo", "", 38),
                new Person("Roberto", "Madrid", 20),
                new Person("Carles", "Barcelona", 37));
        String filePath = "src\\test\\java\\resources\\peopleOK.csv";

        List<Person> realPersons = Assertions.assertDoesNotThrow(() -> FileAnalyzer.processPersons(filePath));

        Assertions.assertEquals(expectedPersons, realPersons);
    }

    @Test
    void processPersonsFailsByNoName() {
        InvalidLineFormatException expectedException = new InvalidLineFormatException("Name is required");
        String filePath = "src\\test\\java\\resources\\peopleFAIL1.csv";

        InvalidLineFormatException realException =
                Assertions.assertThrows(InvalidLineFormatException.class, () -> FileAnalyzer.processPersons(filePath));

        Assertions.assertEquals(expectedException.getMessage(), realException.getMessage());
    }

    @Test
    void processPersonsFailsNoEnoughtDelimiters() {
        InvalidLineFormatException expectedException = new InvalidLineFormatException("At least 2 delimiters are required (given 1)");
        String filePath = "src\\test\\java\\resources\\peopleFAIL2.csv";

        InvalidLineFormatException realException =
                Assertions.assertThrows(InvalidLineFormatException.class, () -> FileAnalyzer.processPersons(filePath));

        Assertions.assertEquals(expectedException.getMessage(), realException.getMessage());
    }

    @Test
    void processAndFilterPersonsUnder25Years() {
        List<Person> expectedPersons = List.of(new Person("Andres", "Madrid", 19),
                new Person("Laura Saenz", "", 23),
                new Person("Roberto", "Madrid", 20));
        String filePath = "src\\test\\java\\resources\\peopleOK.csv";

        List<Person> realPersons = Assertions.assertDoesNotThrow(
                () -> FileAnalyzer.processPersons(filePath, person -> person.getAge() < 25  && person.getAge() > 0));

        Assertions.assertEquals(expectedPersons, realPersons);
    }

    @Test
    void processAndFilterPersonsIfNameDoesNotStartWithA() {
        List<Person> expectedPersons = List.of(new Person("Jesús", "Logroño", 41),
                new Person("Laura Saenz", "", 23),
                new Person("Maria Calvo", "", 38),
                new Person("Roberto", "Madrid", 20),
                new Person("Carles", "Barcelona", 37));
        String filePath = "src\\test\\java\\resources\\peopleOK.csv";

        List<Person> realPersons = Assertions.assertDoesNotThrow(
                () -> FileAnalyzer.processPersons(filePath, person -> !person.getName().startsWith("A")));

        Assertions.assertEquals(expectedPersons, realPersons);
    }
}