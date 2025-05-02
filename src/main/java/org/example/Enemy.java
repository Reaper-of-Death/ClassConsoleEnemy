package org.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)  // Указываем стратегию наследования
@Table(name = "enemy")  // Таблица для базового класса
public abstract class Enemy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @Column(name = "Name")
    private String name;
    @Column(name = "Hp")
    private int hp;
    @Column(name = "Damage")
    private int Damage;

    // Геттеры и сеттеры

    public Enemy() {}

    public Enemy(String name, int hp, int Damage) {
        this.name = name;
        this.hp = hp;
        this.Damage = Damage;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return Damage;
    }

    public void setDamage(int Damage) {
        this.Damage = Damage;
    }

    public String attack() {
        return "Враг" + getName() + "атаковал";
    }

    public String bum() {
        return "Враг" + getName() + "сделал Буум!";
    }

    public String makeClone() {
        return "Враг" + getName() + "клонировал себя";
    }

    public String movement() {
        return "Враг" + getName() + "двинулся";
    }
}
