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
- a seguir a lista de precedência das configurações:
  - linha de comando
  - variavel de ambiente
  - arquivo de configuração
  - @PropertySource
  - class main   

### ConfigurationProperties
- uma forma de representar uma configuração personalizada em uma classe java, para isso precisamos anotar-la com @ConfigurationProperties("prefixo")
- cada variável de instância dessa classe, deve corresponder ao sufixo da propriedade salientada na anotação. Exemplo: prefixo: app.url, sufixo: port, host (ter variáveis de instância com nome port, host e corresponder ao tipo).
- Além das situações salientadas acima, devemos adicionar a depêndencia abaixo, para inserir as propriedades no meta-data da app.
```
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-configuration-processor</artifactId>
			<optional>true</optional>
		</dependency>
```
- por fim, referenciar no scan da aplicação a classe properties:
```
@EnableConfigurationProperties(ApplicationProperties.class)
@SpringBootApplication
@ComponentScan
public class SpringBootAppDemoApplication {
```
