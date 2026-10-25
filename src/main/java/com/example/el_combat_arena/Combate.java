package com.example.el_combat_arena;

public class Combate {

    Personas_Valorant Astra = new Personas_Valorant();
    Personas_Valorant Vyse = new Personas_Valorant();

    void nomePersonas() {
        Astra.nome = "Astra";
        Vyse.nome = "Vyse";
    }

    void alcancePersonas() {
        Astra.alcance = 55;
        Vyse.alcance = 35;
    }

    void defensaPersonas() {
        Astra.defesa = 3;
        Vyse.defesa = 5;
    }

    void velocidadePersonas() {
        Astra.velocidade = 5;
        Vyse.velocidade = 3;
    }

    void estaminaPersonas() {
        Astra.estamina = 30;
        Vyse.estamina = 40;
    }

    void ataquePersonas() {
        Astra.ataque = 17;
        Vyse.ataque = 12;
    }

    void vidaPersonas() {
        Astra.vida = +15;
        Vyse.vida = +35;

    }

    void mostrarPersonas() {

    System.out.println(Astra.nome);
    System.out.println(Vyse.nome);

    }
}
