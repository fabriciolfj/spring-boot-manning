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
- o spring por default utiliz-se a implementação hibernate da especificação bean validation
- podemos criar nossas anotações (caso vincule-as em classes que implementam constraintvalidator), ou utilizar as que o javax (jakarta agora) nos oferece. Exemplo:
````
    @Min(value = 1, message = "A course should have a minimum of 1 rating")
    @Max(value = 5, message = "A course should have a maximum of 5 rating")
    private int ration;
````
- precisamos também adicionar a dependência:
```
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>

```

### Spring data
- permite acesso/manipular dados em diferentes fontes, seja ela em base relacionais ou não relacionais.
- abstrai toda a configuração necessária para se comunicar com a base de dados, apenas fornecemos algumas informacoes no properties da aplicacao
- fornece apis que nos auxiliam na realizacao de crud no databasep
- existem alguns submodulos dentro do spring data, entre eles o spring data commons, que fornece uma base para outros:
- spring data commons, oferece as seguintes interfaces (ordem de hierarquia): repository -> crudrepository -> pagingandsortingrepository -> base expecífica (ex: springdatajpa)

#### Diferença entre @SpringBootTest e @DataJpaTest
- a anotação @SpringBootTest levanta todo contexto do spring, ou seja, ele cria um applicationContext que será utilizado nos testes. No entanto as vezes estamos testando algum específico, e não faz sentido carrega todos os beans da aplicação.
-  a anotação @DataJpaTest, carrega apenas os beans correspondentes ao Jpa. Utíl quando vamos testar apenas a camada DAO.


#### Query methods
- spring data nos fornece uma linguagem fluente para consulta ao banco de dados
- onde escrevemos o que precisamos na descrição do método
- esse mecanismo é conhecido como query methods
- abaixo um exemplo:
```
public List<Customer> findByCityOrderByCustomerDesc(final String city)
```
- para mais detalhes acesse: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods

#### Named Query
- consulta associada a uma entidade
- vinculo a mesma na própria classe
- os parametros de nome e query são obrigatórios
- o hint e lock mode são opcionais
- exemplo abaixo de criação:
```
@NamedQueries({
        @NamedQuery(name = "Course.findAllByCategoryAndRating", query = "select c from Course where c.category=?1 and c.ration=?2")
})
public class Course 
```
- exemplo de uso
```
Iterable<Course> findAllByCategoryAndRating(final String category, int rating);
```
- namedquery não é recomendável, pois polui o pojo com instruções jpql, alem de não dar suporte a query nativas

#### Query
- fornece acima da assintura do método a consulta jpql ou sql nativa
- podemos também executar instruções dml
- alguns exemplos abaixo:
```
    @Query("select c from Course c where c.category =:category and c.ration > :rating")
    Iterable<Course> findAllByCategoryAnRatingGreaterThan(@Param("category") final String category, @Param("rating") final int rating);
    @Query(value = "select * from course where ration =?1", nativeQuery = true)
    Iterable<Course> findAllByRating(int rating);
    @Modifying
    @Transactional
    @Query("update course c set c.ration = :rating where c.name = :name")
    int updateCourseRatingByName(@Param("rating") int rating, @Param("name") final String name);
```

#### QueryDSL
- junto com o spring data, podemos fazer uso do Criteria API do JPA
- uma api para escrever consultas ou modificações no banco de dados de forma fluente
- no entanto é muito verbosa
- a querydsl veio para sanar o ponto negativo acima do Criteria
- ela permite escrever consultas de forma fluente, fazendo validação de tipo e da query, em tempo de compilação
- são geradas classes, com base nas classes com anotação @Entity. Exemplo: Customer, gerada QCustomer
- e essas estão os operadores de consulta e a validação estática.
- Para fazer uso devemos adicionar a depêndencia baixo:
```
		<dependency>
			<groupId>com.querydsl</groupId>
			<artifactId>querydsl-apt</artifactId>
		</dependency>
		<dependency>
			<groupId>com.querydsl</groupId>
			<artifactId>querydsl-jpa</artifactId>
		</dependency>
		
			<plugin>
				<groupId>com.mysema.maven</groupId>
				<artifactId>apt-maven-plugin</artifactId>
				<version>1.1.3</version>
				<executions>
					<execution>
						<phase>generate-sources</phase>
						<goals>
							<goal>process</goal>
						</goals>
						<configuration>
							<outputDirectory>target/generated-sources/java</outputDirectory>
							<processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
						</configuration>
					</execution>
				</executions>
			</plugin>
```
- extender a classe abaixo:
````
public interface CourseRepository extends PagingAndSortingRepository<Course, Long>, QuerydslPredicateExecutor<Course> {
````
- exemplo de uso
```
		QCourse course = QCourse.course;
		JPAQuery query1 = new JPAQuery(entityManager);
		query1.from(course).where(course.category.eq("Spring"));

		assertThat(query1.fetch().size()).isEqualTo(3);
```

#### Conditional bean
- spring permite delegar ao mesmo a criação de objetos(injeção de dependência) e seu ciclo de vida
- podemos orientar o spring a criar esses objetos via uma condição
- abaixo alguns exemplos mais populares:
```
    @Bean
    @Conditional(RelationDatabaseCondition.class)
    public RelationalDataSourceConfiguration dataSourceConfiguration() {
        return new RelationalDataSourceConfiguration();
    }
    
    @Configuration
    @ConditionalOnProperty(value = "fetureToggle.notification", havingValue = "true", matchIfMissing=true)
    class NotificationSender {

    }
    havingValue = se for true
    matchIfMissing = se não for informado, valerá o valor true
```
- podemos fazer uso dessas anotações em nível de class ou método

#### Arquivo spring.factories
- é um arquivo carregado pelo spring automaticamente, no momento da inicialização
- nele encontra-se diversas configurações, entre elas configurações automáticas
- a localização deste arquivo é: resources/META-INF

#### Failure Analyzer
- uma infraestrutura de análise de falhas oferecida pelo spring
- executa validações para verificar se a aplicação será construida corretamente (tempo de build)
- para isso a aplicação precisa lançar alguma exception no seu ciclo de vida de construção
- abaixo um exemplo de uma exception, que está sendo monitorada pelo failure analyzer. O evento é lançado antes de subir a aplicação
```
    @EventListener(classes = ContextRefreshedEvent.class) //é executado apos o application listener
    public void listener() {
        //throw new UrlNotAccessibleException(url);
    }
```
- registra o problema e possível solução no console
- precisa estar registrado no spring.factories
- Um exemplo de uma configuração:
```
public class UrlNotAccessibleFailureAnalyzer extends AbstractFailureAnalyzer<UrlNotAccessibleException> { //vai disparar caso ocorra a exception informada

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, UrlNotAccessibleException cause) {
        return new FailureAnalysis("Não foi possível acessar a url: " + cause.getUrl(),  "Valide a url e certifique-se de que esta acessivel", cause);
    }
}

```
- exemplo de um arquivo spring.factories, indicando a classe failure analyzer criada e a configuração necessária:
```
org.springframework.boot.diagnostics.FailureAnalyzer=\
com.manning.sbip.ch01.springbootappdemo.exceptions.failureanalyzer.UrlNotAccessibleFailureAnalyzer
```
- obs: caso tenha mais de um failure analyzer, separe por vingula

#### Spring actuator
- expõe via apis, métricas da sua aplicação
- podemos personalizar algumas métricas/observabilidades
- abaixo um exemplo onde no endpoint /health, será incluso o health customizavel (verifica uma api externa, antes de decidir de o microservice está pronto)
- obs: precisamos implementar a interface HealthIndicator e seguir a convenção NomeHealthIndicator
```
@Component
public class DogsApiHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try {
            ParameterizedTypeReference<Map<String, String>> ref = new ParameterizedTypeReference<>() {
            };
            ResponseEntity<Map<String, String>> result =
                    new RestTemplate().exchange("https://dog.ceo/api/breeds/image/random", HttpMethod.GET, null, ref);

            if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null) {
                return Health.up().withDetails(result.getBody()).build();
            }

            return Health.down().withDetail("status", result.getStatusCode()).build();
        } catch (RestClientException e) {
            return Health.down().withException(e).build();
        }
    }
}
```
- outro pronto e enriquecer o endpoint /info
- obs: essa configuração será substituida pelos arquivos build-info e git.properties, caso estejam no classpath da aplicação
```
management:
  info:
    env:
      enabled: true

info:
  build:
    artifact: @project.artifactId@
    name: @project.name@
    description: @project.description@
    version: @project.version@
    properties.java.version: @java.version@
  app:
    name: Aplicativo de estudo
    description: aplicativo feito para explorar os recursos do spring boot
    version: 1.0
ou gradle
springBoot {
  buildInfo()
}
```
- fornecendo arquivos mencionados acima:
```
<plugin>
				<groupId>com.mysema.maven</groupId>
				<artifactId>apt-maven-plugin</artifactId>
				<version>1.1.3</version>
				<executions>
					<execution>
						<phase>generate-sources</phase>
						<goals>
							<goal>process</goal>
						</goals>
						
			<plugin>
				<groupId>pl.project13.maven</groupId>
				<artifactId>git-commit-id-plugin</artifactId>
			</plugin>
```
- podemos também fornecedor informações a este endpoint de forma programática (similar ao health)
- para isso devemos implementar a interface InfoContributor

#### Metricas
- o spring boot utiliza por tráz o micrometer para configurar as métricas
- ele é uma fachada, onde podemos utilizar implementações para ferramentas específicas, afim de efetuar uma integração
- exemplo de um endpoint:
```
http://localhost:8081/actuator/metrics
http://localhost:8081/actuator/metrics/jvm.gc.pause
```
- podemos adicionar tags comuns, afim de identificar a origem dos dados mostrados nas metricas
- por exemplo:
```
    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("app", "courses");
    }
    
    http://localhost:8081/actuator/metrics/jvm.gc.pause?tag=app:courses
```
- spring boot permite criar métrica personalizadas
- todas as metricas precisam ser catalogadas no MeterRegistry (um bean de exemplo abaixo)
```
    @Bean
    public Gauge createCoursesGauge(final MeterRegistry meterRegistry, final CourseService service) {
        return Gauge.builder("api.courses.created.gauge", service::count)
                .description("Total de cursos disponiveis")
                .register(meterRegistry);
    }
    
    http://localhost:8081/actuator/metrics/api.courses.created.gauge
```
- temos alguns medidores como:
  - counter -> um valor número que pode ser incrementado, desvangatem que não persisti o valor, no caso de reinicio da app, informação é perdida
  - gauge -> medidor, podemos consultar a base de dados para expor alguma métrica, como: total de clientes criados
  - timer -> uso para medicao do tempo gasto para criar um recurso ou busca de alguma informação
  - distributionSummary -> resumo de dados a uma entidade, como: quantidade (count), soma de incidencias de uma valor(total), valor máximo fornecido (max)