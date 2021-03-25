#language: pt
  Funcionalidade: Aprender Cucumber
    Como um aluno
    Eu quero aprender a utilizar Cucumber
    Para que eu possa automatizar critérios de aceitação


  Cenário: Deve incrementar contador
    Dado que o valor do contador é 15
    Quando eu incrementar em 3
    Então o valor do contador será 18

  Cenário: Deve incrementar contador
    Dado que o valor do contador é 123
    Quando eu incrementar em 35
    Então o valor do contador será 158

  Cenário:  Deve calcular atraso na entrega
    Dado que a entrega é dia 05/04/2018
    Quando a entrega atrasar em 2 dias
    Então a entrega será efetuada em 07/04/2018

  Cenário:  Deve calcular atraso na entrega da china
    Dado que a entrega é dia 05/04/2018
    Quando a entrega atrasar em 2 meses
    Então a entrega será efetuada em 05/06/2018

    Cenário: Deve criar steps genéricos para estes passos
      Dado que o ticket é "AF345"
      E que o valor da passagem é R$ 230,45
      E que o nome do passageiro é "Fulano da Silva"
      E que o telefone do passageiro é 9999-9999
      Quando criar os steps
      Então o teste vai funcionar

