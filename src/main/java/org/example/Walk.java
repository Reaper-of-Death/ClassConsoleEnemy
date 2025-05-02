package org.example;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Walk")
@PrimaryKeyJoinColumn(name = "ID_Enemy")
public class Walk extends Enemy {
    @Column(name = "Protection")
    private int protection;
    @Column(name = "Rebound")
    private int rebound;

    public Walk() {}

    public Walk(String name, int hp, int damage, int protection, int rebound) {
        super(name, hp, damage);
        this.protection = protection;
        this.rebound = rebound;
    }

    public int getProtectionValue() {
        return protection;
    }

    public void setProtectionValue(int protection) {
        this.protection = protection;
    }

    public int getRebound() {
        return rebound;
    }

    public void setRebound(int rebound) {
        this.rebound = rebound;
    }

    @Override
    public String attack() {
        return "Наземный" + getName() + "враг атаковал мечом";
    }

    @Override
    public String bum() {
        return "Враг" + getName() + "сделал Буум!";
    }

    @Override
    public String movement() {
        return "Наземный враг" + getName() + "сделал шаг вперёд";
    }

    @Override
    public String makeClone() {
        return "Враг" + getName() + "клонировал себя";
    }

    public String protection() {
        return "Враг" + getName() + "выставил щит";
    }

    public String reboundAction() {
        return "Враг" + getName() + "отскочил на " + rebound + " метров";
    }
}
