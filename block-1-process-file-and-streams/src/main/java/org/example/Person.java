package org.example;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Person {
    private String name;

    private String town;

    private int age;

    public Person(String name, String town, int age) {
        this.name = name;
        this.town = town;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", town: " + (town.isEmpty() ? "unknown" : town) + ", age: " + (age == 0 ? "unknown" : age);
    }
}
