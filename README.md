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
- sugestão: uma forma de fazer uso da construção das propriedades via construct da classe, sem uso das propriedades setters, é anota-la com @ConstructorBinding.

### Customizando logs no spring boot
- podemos customizar os logs no console e no arquivo gerado, via properties:
````
#logging:
#  file:
#    console: clr(%d{dd-MM-yyyy HH:mm:ss.SSS}){yellow}%clr(${PID:- }){verde} %magenta([%thread]) %highlight([%-5level])%clr(%-40.40logger{39}){cyan} %msg%n
#    name: application.log
#  logback:
#    rollingpolicy:
#      max-file-size: 1MB
#      max-history: 1
````
- podemos fazer uso de outra lib de log, retirando antes a lib padrão do started web no pom do projeto:
````
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
			<exclusions>
				<exclusion>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-logging</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
````
- No exemplo desse projeto, fui substituida a lib logback pela log4j2.
- abaixo a nova lib inserida no pom e , caso queira, uma personzalição do log, que devemos deixar dentro da pasta resources do projeto.
````
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-log4j2</artifactId>
		</dependency>
````
- configuração do log personalizada:
````
<Configuration status="WARN">
    <Properties>
        <Property name="LOG_PATTERN">
            %d{yyyy-MM-dd HH:mm:ss.SSS} [%5p] [%15.15t] %-40.40c{1.} : %m%n%ex
        </Property>
    </Properties>
    <Appenders>

        <Console name="ConsoleAppender" target="SYSTEM_OUT">
            <PatternLayout pattern="${LOG_PATTERN}" />
        </Console>
        <RollingFile name="FileAppender"
                     fileName="logs/application-log4j2.log"
                     filePattern="logs/application-log4j2-%d{yyyy-MM-dd}-%i.log">
            <PatternLayout>
                <Pattern>${LOG_PATTERN}</Pattern>
            </PatternLayout>
            <Policies>
                <SizeBasedTriggeringPolicy size="10MB" />
                <TimeBasedTriggeringPolicy interval="7" />
            </Policies>
            <DefaultRolloverStrategy max="10"/>
        </RollingFile>
    </Appenders>
    <Loggers>
        <Logger name="com.manning.sbip" level="debug" additivity="false">
            <AppenderRef ref="FileAppender"/>
        </Logger>
        <Logger name="com.manning.sbip" level="debug" additivity="false">
            <AppenderRef ref="ConsoleAppender"/>
        </Logger>
        <Logger name="org.springframework.boot" level="info" additivity="false">
            <AppenderRef ref="ConsoleAppender"/>
        </Logger>
        <Root level="info">
            <AppenderRef ref="FileAppender"/>
            <AppenderRef ref="ConsoleAppender"/>
        </Root>
    </Loggers>
</Configuration>
````
- o nome do arquivo deve ser log4j2.xml (yml e json também são suportados) ou log4j2-spring.xml

### Bean validation
- o spring por default utiliza a implementação hibernate da especificação bean validation
- podemos criar nossas anotações (caso vincule-as em classes que implementam constraintvalidator), ou utilizar as que o javax (jakarta agora) nos oferece. Exemplo:
````
    @Min(value = 1, message = "A course should have a minimum of 1 rating")
    @Max(value = 5, message = "A course should have a maximum of 5 rating")
    private int ration;
