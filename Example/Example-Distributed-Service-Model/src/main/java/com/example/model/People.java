package com.example.model;

import java.util.List;

public class People {

    private String name;
    private int age;
    private long birthday;
    private List<String> skills;
    private People boss;

    public People(String name, int age, long birthday, List<String> skills, People boss) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.skills = skills;
        this.boss = boss;
    }

}
