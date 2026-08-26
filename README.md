Missão Marte — Exemplo OO (console)
=====================================

Este é um exemplo minimalista para aplicar conceitos de Orientação a Objetos no projeto "Missão Marte Unifor".

Conteúdo:

- `src/missao` — código fonte Java (classes: `Nave`, `Passageiro`, `Professor`, `Engenheiro`, `Asteroide`, `Missao`, `Main`).

Compilar e executar (a partir da raiz do repositório):

```bash
javac -d out missaoMarteUnifor/oo-console/src/missao/*.java
java -cp out missao.Main
```

Descrição rápida do jogo em console:

- Comandos: `w` (up), `s` (down), `a` (left), `d` (right), `c` (embarcar se houver passageiro na mesma posição), `q` (sair).
- Objetivo: embarcar todos os passageiros sem colidir com asteroides.

Use este projeto como ponto de partida para exercícios de refatoração (SOLID), testes e aplicação de padrões.


# EQUIPE
- yyRed-dev : João Pedro Palhano Militão
Obs.: Minha dupla saiu da faculdade.

## OBJETIVOS DO TRABALHO
1. ✅ Ajuste de Capacidade: Configurar a capacidade da nave para comportar a nova carga de passageiros do sistema.
2. ✅ Nova Subclasse de Passageiro (Astronauta): Criar a classe Astronauta estendendo Passageiro, definindo suas especificidades e adicionando-o ao fluxo do jogo.
3. Customização Visual: Atualizar a renderização do mapa em console alterando a representação simbólica de elementos (ex: Nave e Asteroides).

4. ✅ Pontuação Polimórfica: Aplicar sobrescrita de métodos (@Override) para que cada tipo de passageiro ofereça uma pontuação distinta ao ser embarcado (Professor: +10, Engenheiro: +15, Astronauta: +20).
5. ✅ Sistema de Vidas na Nave: Implementar o atributo de vidas na classe Nave e a lógica de perda de vidas ao colidir com asteroides.
6. ✅ Mapa Configurável: Permitir que o jogador informe a dimensão do mapa no início da execução.

7. *Inimigos Dinâmicos com IA Simples: Criar a entidade Inimigo com movimentação aleatória pelo mapa a cada turno e verificação de colisão.
8. ✅ Menu de Dificuldades (Enum): Criar o enum Dificuldade (FACIL, MEDIO, DIFICIL) ajustando proporcionalmente recursos, pontuação inicial e quantidade de obstáculos.
9. Persistência Expandida: Ampliar o formato do ranking.json para salvar e carregar dados adicionais da partida (ex: data/hora, passageiros resgatados e nível de dificuldade).

10. Extras
- Plataforma de Pouso (0, 0): Alterar a condição de vitória do jogo. Após resgatar todos os passageiros, o piloto precisa navegar até a Plataforma de Pouso L localizada na coordenada (0, 0) para concluir a missão com sucesso.
- Menu Principal e Reset: Implementar opções de menu interativo no início do programa e a funcionalidade de resetar o arquivo do ranking.
