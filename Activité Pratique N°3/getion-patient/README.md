# Spring Boot - ORM avec Spring Data JPA | Gestion des patients

Dans cette partie on va traiter le mapping objet relationnel (ORM), étant donné comme faire une relation et correspondance entre les objets d’une application et les données stockées dans une table dans une BDDR, cette opération à travers le JDBC.

- ``` JDBC ``` : Java DataBase Connectivity qui permet à une application Java de communiquer avec n’importe quel SGBDR. Egalement nous allons voir l’utilité d’utilisation des frameworks lors du développement :
  - Le gain du temps
  - La portabilité d’application avec n’importe quel SGBD
  - Les performances
- ``` JPA ``` : Java Persistance API est une spécification crée par SUN pour standardiser le ORM, et on peut considérer que ce JPA est une interface globale et comme implémentation nous avons le choix de travailler avec différents outils : Hibernate / TopLink / EclipseLink. Nous avons testé les 2 moyens de Mapping Objet Relationnel :
  - Les annotations JPA
- ```  Java Bean ```  : grain de café Et pour ce faire nous nous avons basé sur le Spring Boot qui est une version de Spring framework, crée principalement pour les applications micro-services, et il se base sur l’auto-configuration.
- ```  Spring Data ``` : un module de Spring qui facilite le Mapping Objet Relationnel basé sur JPA.

## Les dépandances  :

Nous devons créer le projet avec les dépendances suivantes :
 
 - Spring web
 - Spring Data JPA
 - Lombok
 - H2 Database

## Configuration de base de données 

H2 est une base de données mémoire pour le test, dans le fichier ```application.properties``` on va configurer notre base de données de test.

``` properties
  spring.h2.console.enabled=true
  spring.datasource.url=jdbc:h2:mem:patients_db
  server.port=8080
  spring.jpa.show-sql=true
```

## Les classes 
 Dans le packages entities ```ma.enset.getionpatiet.entities```, nous créons les entités suivantes :
 - ```Patient.java```
    
## Les répositories 
 
 Dans le packages reporistories ```ma.enset.getionpatiet.repositories```, nous créons les reporistories  suivantes :
- ```PatientRepository.java```
    
## Test 

Pour effectuer le test, nous avons créer des données dans la classe ```GetionPatientApplication.java```  qui contient le code suivant :

``` java 

@SpringBootApplication
public class GetionPatientApplication implements CommandLineRunner {

    @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(GetionPatientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /**
         * add a new patient
         */
        patientRepository.save(new Patient(null,"Sokaina",new Date(),false,56));
        patientRepository.save(new Patient(null,"Saad",new Date(),true,56));
        patientRepository.save(new Patient(null,"Ahmed saad",new Date(),true,56));
        System.out.println("-------------List of patients----------------");
        patientRepository.findAll().forEach(p->{
            System.out.println(p);
        });
        /**
         * search all patient
         */
        System.out.println("-----------------------------");
        for (Patient p: patientRepository.findAll()
             ) {
            System.out.println(p.getId());
            System.out.println(p.getNom());
            System.out.println(p.getDateNaissance());
            System.out.println(p.getScore());
            System.out.println(p.isMalade());
            System.out.println("<=============>");
        }
        System.out.println("-----------------------------");
        /**
         * search a patient
         */
         Patient patient = patientRepository.findById(2L).orElse(null);
        if(patient!=null){
                System.out.println(patient.getId());
                System.out.println(patient.getNom());
                System.out.println(patient.getDateNaissance());
                System.out.println(patient.getScore());
                System.out.println(patient.isMalade());
        }
        System.out.println("-----------------------------");
         /**
         * update a patient
         */
         patient.setScore(790);
        /**
         * delete patient
         */
        patientRepository.deleteById(1l);
        for (int i = 0; i < 100; i++) {
            patientRepository.save(new Patient(null, "Mohammed",
                    new Date(), false, 56));
        }

        for (Patient patient1 : patientRepository.findAll(PageRequest.of(0, 5))) {
            System.out.println(patient1.getId());
            System.out.println(patient1.getNom());
            System.out.println(patient1.getScore());
            System.out.println(patient1.getDateNaissance());
            System.out.println(patient1.isMalade());
            System.out.println("=========");
        }

        Page<Patient> pages = patientRepository.findAll(PageRequest.of(0, 5));
        pages.getTotalElements();
        pages.getTotalPages();
        pages.getNumber();
        List<Patient> patientList = pages.getContent();


        List<Patient> list = patientRepository.findByMalade(false);

        Page<Patient> list2 = patientRepository.findByMalade(false, PageRequest.of(0, 5));


    }
}

```
 Aprés avoir éxécuter le code, accéder à ce lien ``` http://localhost:8080/h2-console ``` est vérifier si les données existe dans la base de données H2.
 
 ## Changer Base de données à MySQL
 
 On va ajouter ces parametres au fichier ``` application.properties ```.
 
 ``` properties
server.port=8080
spring.datasource.username=root
spring.datasource.password=
spring.datasource.url=jdbc:mysql://localhost:3306/role_user?createDatabaseIfNotExist=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
spring.jpa.show-sql=true
```

et dans le fichier ``` pom.xml ``` rajouter cette dépandance :

```` xml
  <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.32</version>
 </dependency>
````

## Test 
Pour tester si migration de h2 vers MySQL bien passe . Vérifier si les données sont insérer dans la base de données avec succés.



Ce travail nous a permis de bien connaitre comment le mapping objet relationnel se fait au niveau du Java grace à l’API JPA, et on a initié avec Hibernate, et bien évidemment la manière de configuration d’un projet Spring pour faciliter la tâche au développeur.
