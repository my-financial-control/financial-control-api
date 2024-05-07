* Adicionar validação a todas as controllers para impedir valores inválidos para os parâmetros month e year;
* Adicionar validações a TransactionRequestDTO
* Remover a anotação DisplayName de todos os testes e utilizar nomes descritivos em todos os métodos de teste
* Adicionar validação aos endpoints que recebem mês e ano como filtros. Caso seja enviado apenas o ano, deve ser retornado um erro
* Adicionar documentação Swagger
* Adicionar Spring Security para autenticação e autorização
* Adicionar endpoint de totais de empréstimos
  * Total emprestado
  * Total a receber
  * Total pago
  * Avaliar se há a possibilidade de todos os endpoints de valores terem campos de agregação
* Adicionar paginação a API
* Criar class ExtractBorrowing seguindo o exemplo da classe Extract de Transactions
* 