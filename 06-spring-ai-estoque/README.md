# 📦 Spring AI - Assistente de Estoque Inteligente

Um projeto educacional que demonstra como criar um **assistente de estoque com inteligência artificial** que entende comandos de voz.

## 🎯 O que o projeto faz

Este é um **gerenciador de estoque com IA** que permite:

- 🎤 **Falar comandos** como: "Adicione 50 unidades de leite na geladeira"
- 🤖 **IA interpreta** e entende o que você quer fazer
- 💾 **Atualiza banco de dados** com quantidade de produtos
- 📢 **Responde em áudio** confirmando a ação

**Exemplo de fluxo:**
```
Você fala: "Quantos produtos temos no warehouse A?"
     ↓
IA transcreve e entende
     ↓
Busca no banco de dados
     ↓
IA responde: "Você tem 150 produtos armazenados"
     ↓
Você ouve a resposta em áudio MP3
```


## ✨ Qual Melhoria Implementada

Este projeto **reutiliza 85% do módulo anterior (05-Budgeting)** e implementa **uma melhoria importante**:

### Validação de Quantidade Negativa

**O Problema:**
- No módulo anterior, você podia registrar "Remova 500 unidades" de um produto com apenas 100
- Isso criaria estoque negativo (impossível na realidade)

**A Solução (o que implementei):**
```java
// UpdateStockUseCase.java
if (newQuantity < 0) {
    throw new IllegalArgumentException(
        "Quantidade não pode ser negativa. Estoque atual: " + product.getQuantity()
    );
}
```

**Como funciona:**
1. Você tenta remover 150 unidades de leite (que tem apenas 100)
2. IA calcula: 100 - 150 = -50
3. **Validação rejeita a operação**
4. Resposta: "Não é possível remover 150 unidades. Só temos 100 em estoque."

**Por que é importante?**
- ✅ Garante integridade dos dados
- ✅ Evita erros de operação
- ✅ Demonstra regra de negócio bem implementada



**Resumo:** Usamos Spring Boot + Spring AI + MySQL para criar um assistente de estoque inteligente!



## 📚 O que Você Aprendeu Neste Desafio

### 1. **Clean Architecture (3 Camadas)**
   - Domain Layer: Regras de negócio puras
   - Application Layer: Use cases
   - Infrastructure Layer: Adaptadores técnicos

### 2. **Domain-Driven Design**
   - Modelar entidades do domínio
   - Separar responsabilidades por camada

### 3. **Spring AI & Tool Calling**
   - Como usar `@Tool` para expor funcionalidades para IA
   - Integração com OpenAI (transcrição + síntese de voz)

### 4. **Repository Pattern**
   - Interface de domínio + implementação técnica
   - Isolamento de persistência

### 5. **Testes Unitários com Mockito**
   - Testar lógica sem banco de dados
   - Validar regras de negócio

### 6. **Reutilização de Padrões**
   - 85% do código vem do módulo anterior
   - Apenas lógica de negócio mudou

### 7. **Validações de Negócio**
   - Não permitir estoque negativo
   - Garantir integridade dos dados

