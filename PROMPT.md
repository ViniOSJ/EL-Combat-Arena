# Instruções para o agente de IA: Arena de Combate

## Contexto do projeto
Simulador de combate por turnos entre dois personagens, feito para aprendizado.
- Back-end: Java (Spring Boot, Maven)
- Front-end: HTML, CSS e JavaScript puro (sem framework), consumindo a API via `fetch`
- Banco de dados: **etapa posterior**. Na primeira versão o estado do combate fica em memória.

Quem programa é iniciante. Priorize código simples, legível e bem nomeado em vez de soluções "espertas".

## Regras de negócio (fonte da verdade)
Implemente exatamente isto. Se algo conflitar com um pedido meu, avise antes de seguir.

| ID | Regra |
|---|---|
| RN01 | A vida inicial de cada personagem é 200. |
| RN02 | O dano base é um inteiro aleatório entre 5 e 15, incluindo os extremos. |
| RN03 | Maior velocidade determina a iniciativa inicial. |
| RN04 | Velocidades iguais resultam em sorteio da iniciativa. Nenhum personagem pode ter vantagem por id ou ordem de cadastro. |
| RN06 | O dano final aplicado nunca pode ser negativo. |
| RN07 | Vida igual ou inferior a zero determina derrota (zero exato também derrota). |
| RN08 | Desistência encerra o combate e dá a vitória ao oponente. |
| RN09 | Combate encerrado não aceita novas ações, e a ação rejeitada não altera nenhum estado. |
| RN10 | Valores inválidos são rejeitados antes de afetar o combate. |

Personagem: `id`, `nome`, `vida`, `ataque`, `defesa`, `velocidade`.
O resultado do combate deve informar o **vencedor** e o **motivo do encerramento** (derrota por dano ou desistência).

Detalhe importante: a vida pode ficar negativa (ex.: 10 de vida recebendo 12 de dano resulta em -2). Não force a vida para zero.

## Pendências: NÃO decida sozinho
Estes pontos ainda não foram definidos. Não invente valores nem fórmulas. Se a tarefa depender de um deles, pare e me pergunte, e proponha opções com prós e contras.

1. **RN05, fórmula da defesa:** como o valor de `defesa` vira uma porcentagem de redução do dano, e qual o limite máximo de redução (para evitar personagens invulneráveis).
2. **Arredondamento do dano final:** recomendação em análise é arredondar para baixo (inteiro), com mínimo de 0. Só aplique depois que eu confirmar.
3. **Papel do atributo `ataque`:** por enquanto é só um atributo cadastrado e não influencia o dano. Não o use em nenhum cálculo. Se houver atributo sem função documentada, avise.
4. **Limites numéricos dos atributos** (velocidade, defesa, ataque) para validação (RN10): ainda não definidos.

Enquanto a fórmula da defesa estiver pendente, deixe o cálculo isolado em um único método, fácil de trocar, e marque-o com `// TODO: aguardando definição da fórmula (RN05)`.

## Estrutura de pacotes
```
com.exemplo.arena
├── controller   # recebe HTTP e devolve resposta; sem regra de negócio
├── service      # orquestra o combate e aplica as regras
├── model        # Personagem, Combate, estado do combate
├── dto          # objetos de entrada e saída da API
└── exception    # exceções próprias e handler global
```
Front-end em `src/main/resources/static/` (`index.html`, `style.css`, `app.js`).

## Regras de código
1. Siga as camadas: Controller chama Service. Nunca coloque regra de negócio no Controller.
2. Mantenha as regras do combate (iniciativa, dano, derrota, desistência) em classes simples, sem depender de HTTP, para poderem ser testadas sozinhas.
3. A aleatoriedade (sorteio do dano e do desempate) deve vir de uma dependência injetável (ex.: `java.util.Random` recebido pelo construtor), para que os testes consigam controlá-la. Não use `Math.random()` solto no meio da lógica.
4. Use DTOs (de preferência `record`) para entrada e saída. Não exponha o modelo interno na API.
5. Injeção por construtor. Não use `@Autowired` em campo.
6. Valide entradas com Bean Validation e trate erros em um único `@RestControllerAdvice`.
7. Status HTTP corretos: 200, 201 (criação), 400 (valor inválido), 404 (combate não encontrado), 409 (ação em combate já encerrado).
8. Nomes de classes, métodos e variáveis em inglês; mensagens ao usuário em português.
9. Métodos curtos, uma responsabilidade cada. Sem código comentado e sem imports não usados.
10. Não adicione bibliotecas novas nem banco de dados sem perguntar.

## Testes
Escreva testes (JUnit 5) junto com cada regra. Casos obrigatórios da especificação:

- **CT01** início: ambos com 200 de vida e combate ativo.
- **CT02** velocidade 15 vs 10: o de 15 age primeiro.
- **CT03** velocidades iguais: iniciativa sorteada, sem vantagem fixa. Teste que os dois podem vencer o desempate, sem exigir distribuição perfeita em poucas execuções.
- **CT04** dano base sempre entre 5 e 15, incluindo os extremos.
- **CT05** redução por defesa: **só depois** que a fórmula for definida.
- **CT06** 200 de vida recebendo 10 de dano resulta em 190.
- **CT07** 10 de vida recebendo 12 de dano resulta em -2, derrota e fim do combate.
- **CT08** desistência: combate termina, desistente perde, oponente vence.
- **CT09** ação após o término é rejeitada e nada muda no estado.
- **CT10** atributos inválidos (ex.: velocidade negativa) são rejeitados.

Casos extras: a luta termina quando a vida chega **exatamente a zero**; personagem derrotado não age; ação inválida não reduz vida.

Rode `./mvnw test` antes de dizer que terminou.

## O que NÃO fazer
- Não refatore arquivos que não fazem parte da tarefa.
- Não apague nem enfraqueça testes para "fazer passar".
- Não invente regras, endpoints, campos ou classes que não foram pedidos.
- Não preencha pendências com suposições silenciosas.

## Como trabalhar comigo
1. Antes de codar, resuma em poucas linhas o que vai fazer. Se algo for ambíguo, pergunte.
2. Faça mudanças pequenas, uma de cada vez, começando pelas regras de negócio e testes, depois API, depois front-end.
3. Ao final, explique em português simples o que mudou e por quê, para que eu aprenda.
4. Liste os arquivos criados ou alterados e diga qual regra (RN) ou teste (CT) cada mudança cobre.

## Comandos úteis
- Rodar: `./mvnw spring-boot:run`
- Testar: `./mvnw test`

## Definição de pronto
- [ ] Compila sem erros ou avisos novos
- [ ] Testes passam e cobrem as regras da tarefa
- [ ] Regras (RN) respeitadas e pendências não decididas por conta própria
- [ ] Ações em combate encerrado retornam erro sem alterar o estado
- [ ] Explicação em português entregue
