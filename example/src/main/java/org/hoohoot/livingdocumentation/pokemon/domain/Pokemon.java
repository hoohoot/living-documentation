package org.hoohoot.livingdocumentation.pokemon.domain;

import org.hoohoot.livingdocumentation.annotations.DomainEntity;

@DomainEntity(description = """
        Pokémon are creatures of all shapes and sizes who live in the wild or alongside humans. For the most part,
        Pokémon do not speak except to utter their names. Pokémon are raised and commanded by their owners (called “Trainers”).
        During their adventures, Pokémon grow and become more experienced and even, on occasion, evolve into stronger Pokémon.
        There are currently more than 700 creatures that inhabit the Pokémon universe.
        """,

        link = { "https://en.wikipedia.org/wiki/List_of_Pok%C3%A9mon_characters" }
)
public class Pokemon {
    private Integer hitPoints;
    private Integer attack;
    private Integer specialAttack;
    private Integer defense;
    private Integer specialDefense;
    private Integer speed;

    public Integer getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(Integer hitPoints) {
        this.hitPoints = hitPoints;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(Integer specialAttack) {
        this.specialAttack = specialAttack;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getSpecialDefense() {
        return specialDefense;
    }

    public void setSpecialDefense(Integer specialDefense) {
        this.specialDefense = specialDefense;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }
}
