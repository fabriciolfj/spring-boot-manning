# spring-boot-manning
- spring boot foi desenvolvido para facilitar a vida do programador, onde preocupações com a parte de infraestrutura da aplicação, foram mitigadas
- preocupações estas como: servlet container (aonde rodar), dependências compativeis entre si, versionamento e etc (atraves das dependências starter - agrupamento de dependências).
- spring boot faz o meio de campo entre o desenvolvedor e o spring framework.
- spring boot configura automaticamente vários componentes do aplicativo, com base nas depdendências e configurações disponíveis.
- classe main gerada (se o aplicativo foi gerado via cli ou site do spring initializr) consiste na anotação @SpringBootApplication, que engloba:
  - @EnableAutoConfiguration
  - @ComponentScan 
  - @SpringBootConfiguration 

## Gerenciando configurações
- spring permite externalizarmos as configurações de nossos aplicativos e podemos diferencia-las por ambiente (dev, hom ou prod por exemplo)
- alem de externalizar, podemos fazer uso de configurações via linha de comando ou variáveis de ambiente.
