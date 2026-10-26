package com.example.el_combat_arena;

import java.util.ArrayList;

public class Personas_Valorant {

    String nome = "";
    float vida = 200;
    int estamina = 0;
    int ataque = 0;
    int alcance = 0;
    float defesa = 0;
    Itens itens[] = new Itens[2];
    int velocidade = 0;
    Habilidades habilidades = new Habilidades();

    double dano(double danobase)    {
       return danobase * (1 - defesa/100.0);
    }



}
