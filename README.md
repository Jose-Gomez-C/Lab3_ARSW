# ARSW-LAB3

## Requerimientos 
Se necesita que tenga instalado en su computadora:
```sh
$ Java 1.8
$ Maven 3.6.3
```
con su respectiva configuracion
## Instalación
> **Clonar el repositorio:** 
```sh
$ https://github.com/jose-gome/Lab3_ARSW
```
> **Compilar el proyecto:**
```sh
$ mvn package
```
> **Ejecutar el programa GRAMMAR-CHECKER:** 
```sh
$ mvn exec:java -Dexec.mainClass=edu.eci.arsw.springdemo.ui.Main
```
> **Ejecutar pruebas en los dos proyectos**
```sh
$ mvn test
```
 ## Parte  - Basic Workshop
 To illustrate the use of the Spring framework, and the development environment for its use through Maven (and NetBeans), the configuration of a text analysis application will be made, which makes use of a grammar verifier that requires a spelling checker. The grammar checker will be injected, at the time of execution, with the spelling checker required (for now, there are two available: English and Spanish).
> Open the project sources in NetBeans.
> Review the Spring configuration file already included in the project (src / main / resources). It indicates that Spring will automatically search for the 'Beans' available in the indicated package.
> Making use of the Spring configuration based on annotations mark with the annotations @Autowired and @Service the dependencies that must be injected, and the 'beans' candidates to be injected -respectively-:
- GrammarChecker will be a bean, which depends on something like 'SpellChecker'.

- EnglishSpellChecker and SpanishSpellChecker are the two possible candidates to be injected. One must be selected, or another, but NOT both (there would be dependency resolution conflict). For now, have EnglishSpellChecker used. 
- ![EnglishGrammarChecker](https://i.ibb.co/c3SWrSq/english.png)
- ![SpanishGrammarChecker](https://i.ibb.co/x6PQ4k2/spanish.png)
> Make a test program, where an instance of GrammarChecker is created by Spring, and use it:

## Part II
> Modify the configuration with annotations so that the Bean 'GrammarChecker' now makes use of the SpanishSpellChecker class (so that GrammarChecker is injected with EnglishSpellChecker instead of SpanishSpellChecker.) Verify the new result.
- ![SpanishGrammarChecker](https://i.ibb.co/x6PQ4k2/spanish.png)

## Cinema Book System
> Configure the application to work under a dependency injection scheme, as shown in the previous diagram. The above requires:
- Add the dependencies of Spring. 
- Add the Spring configuration. 
- Configure the application -by means of annotations- so that the persistence scheme is injected at the moment of creation of the 'CinemaServices' bean. 

> Complete the getCinemaByName (), buyTicket (), and getFunctionsbyCinemaAndDate () operations. Implement everything required from the lower layers (for now, the available persistence scheme 'InMemoryCinemasPersistence') by adding the corresponding tests in 'InMemoryPersistenceTest'.
-
> For later queries, we want to implement two functionalities:
- A method 'getFunctionsbyCinemaAndDate' that allows to obtain all the functions of a certain cinema for a certain date. 
- Allow the purchase or reservation of tickets for a certain position of chairs in the room through the 'buyTicket' method. 
> Make a program in which you create (through Spring) an instance of CinemaServices, and rectify the functionality of it: register cinemas, consult cinemas, obtain the functions of certain cinema, buy / book tickets, etc.
> It is wanted that the consultations realize a filtering process of the films to exhibit, said filters look for to give him the facility to the user to see the most suitable films according to his necessity. Adjust the application (adding the abstractions and implementations that you consider) so that the CinemaServices class is injected with one of two possible 'filters' (or possible future filters). The use of more than one at a time is not contemplated:
- (A) Filtered by gender: Allows you to obtain only the list of the films of a certain genre (of a certain cinema and a certain date) (The genre enters by parameter). 
- (B) Filtering by availability: Allows you to obtain only the list of films that have more than x empty seats (of a certain cinema and a certain date) (The number of seats is entered per parameter).
> Add the corresponding tests to each of these filters, and test their operation in the test program, verifying that only by changing the position of the annotations -without changing anything else-, the program returns the list of films filtered in the manner (A ) or in the way (B).
 
 ## Autores
 - **Jose Luis Gomez Camacho**
 - **Brayan Felipe Rojas Bernal**
