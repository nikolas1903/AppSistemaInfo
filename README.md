# AppSistemaInfo
Emulador usado para Debug: Pixel 5 API 30.
App criado para o processo de seleção da empresa SistemaInfo.

O App consiste em: 
- Tela Splash de 2 segundos, antes do App iniciar;
- Criação de conta por e-mail;
- Confirmação e autenticação de criação da conta enviada por e-mail;
- Login por e-mail, autenticado pelo FireBase;
- Campo de esqueci minha senha, validando se já existe um e-mail cadastrado, e caso exista, é enviado um e-mail para redefinição da senha;
- CRUD de Pessoa, com os campos NOME, CPF, ENDEREÇO E TELEFONE;
- Os campos NOME e CPF são obrigatórios, e o campo de CPF deve conter exatamente 11 caractéres.

Após preenchimento dos dados, o App os armazena no Banco de Dados SQLite, e gera um ID aleatório de 1 a 100 (pode ser alterado no código).
Após salvar no banco, é gerado um Toast de sucesso, junto com o CPF cadastrado, e o usuário é redirecionado para uma nova Activity mostrando que a pessoa foi cadastrada com sucesso.

PROBLEMAS ENFRENTADOS:
- Não consegui criar uma máscara para o CPF e para o Telefone, para deixar o Input com os formatos corretos
Ex.:
CPF XXX.XXX.XXX-XX
Telefone (XX) X XXXX-XXXX
- Não consegui exibir somente os 4 primeiros dígitos numerais do CPF.
