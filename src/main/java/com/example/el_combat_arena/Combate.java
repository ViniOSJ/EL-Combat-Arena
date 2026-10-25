package com.example.el_combat_arena;

public class Combate {

    Personas_Valorant Astra = new Personas_Valorant();
    Personas_Valorant Vyse = new Personas_Valorant();

    int distancia = 40;

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
        Astra.vida = 215;
        Vyse.vida = 235;

    }

    boolean podeAtacar(Personas_Valorant atacante) {
        return atacante.alcance >= distancia;
    }

    void testarAlcance() {
        nomePersonas();
        alcancePersonas();

        System.out.println(Astra.nome + " Pode atacar? " + podeAtacar(Astra));
        System.out.println(Vyse.nome + " Pode atacar? " + podeAtacar(Vyse));
    }

    void aproximaPersonas(Personas_Valorant persona) {
        distancia -= persona.velocidade;
    }

    void distanciarPersonas(Personas_Valorant persona) {
        distancia += persona.velocidade;
    }

    // Habilidades

    //void habilidadePersonas() {
     //   Astra.habilidades(0) = stum = 1;
    //}

    void mostrarPersonas() {

    System.out.println(Astra.nome);
    System.out.println(Vyse.nome);

    }

    void testarMovimento() {
        System.out.println("Distância inicial: " + distancia);

        aproximaPersonas(Astra);
        System.out.println("Após Astra se aproximar: " + distancia);

        aproximaPersonas(Vyse);
        System.out.println("Após Vyse se aproximar: " + distancia);
    }

    public static void main(String[] args) {
        Combate combate = new Combate();

        combate.testarAlcance();
    }
}
