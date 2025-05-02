package org.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Fly")
@PrimaryKeyJoinColumn(name = "ID_Enemy")
public class Fly extends Enemy {
    @Column(name = "Spit")
    private int spit;
    @Column(name = "Wing")
    private int wing;

    public Fly() {}

    public Fly(String name, int hp, int damage, int spit, int wing) {
        super(name, hp, damage);
        this.spit = spit;
        this.wing = wing;
    }

    public int getSpit() {
        return spit;
    }

    public void setSpit(int spit) {
        this.spit = spit;
    }

    public int getWing() {
        return wing;
    }

    public void setWing(int wing) {
        this.wing = wing;
    }

    @Override
    public String attack() {
        return "Летающий враг " + getName() + " плюнул кислотой";
    }

    @Override
    public String bum() {
        return "Враг" + getName() + "сделал Буум!";
    }

    @Override
    public String movement() {
        return "Летающий" + getName() + "враг немного пролетел вперёд";
    }

    @Override
    public String makeClone() {
        return "Враг" + getName() + "клонировал себя";
    }

    public String regeneration() {
        return "Враг" + getName() + "восстановил часть HP";
    }

    public String bust() {
        return "Враг" + getName() + "ускорился";
    }

    public String toString(){
        return "ID: " + getId() + ", Имя: " + getName() + ", HP: " + getHp() + ", Урон: " + getDamage() +
                        ", Кислота: " + getSpit() + ", Крылья: " + getWing();
    }
}
