package io.portx.model;

import java.io.Serializable;
import java.util.Optional;

public class Person implements Serializable {

    private String name;
    private int age;
    private Optional<Pet> pet;

    public Person(String name, int age, Optional<Pet> pet) {
        this.name = name;
        this.age = age;
        this.pet = pet;
    }

    public void setPet(Optional<Pet> pet) {
        this.pet = pet;
    }
}
