# Use case JPA, Hibernate Spring Data, Many To Many | Users-Roles

Dans cette partie on va traiter les relations Many To Many  avec l'utilisation de JPA , Hibernate et spring web pour implémenter un syéteme de gestion hopital. ce projet va initialiser par H2 vers la fin nous merger vers MySQL.

## Les dépandances 
Nous devons créer le projet avec les dépandances suivantes :

  - Spring web
  - Spring Data JPA
  - Lombok
  - H2 Database

## La conception du projet

![image](https://user-images.githubusercontent.com/48890714/230082393-48d4f6c5-6dbf-4adc-8d3c-ddc784623d30.png)

## Configuration de base de données

H2 est une base de données mémoire pour le test, dans le fichier application.properties on va configurer notre base de données de test.

```
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:users_db
server.port=8080
spring.datasource.username=root
spring.datasource.password=
```

## Les entités

Dans le packages entities ```ma.enset.gestionroleuser.entities```, nous créons nos entités suivantes :

- ```Role.java```
- ```User.java```

## Les repositories

Dans le packages reporistories ```ma.enset.gestionroleuser.repositories``` , nous créons les reporistories suivantes :

- ```RoleRepository.java```
- ```UserRepository.java```

## Les services

Dans le package services ```ma.enset.gestionroleuser.services``` , nous créons les services suivants :

- ```UserService.java```
- ```UserServiceImpl.java```

## Les controllers 

Dans le package web ```ma.enset.gestionroleuser.web```, nous créons les controllers suivants :

- ```UserController.java```

## Test 

Pour effectuer le test, nous avons creer des donnees dans la classe GestionRoleUserApplication.java qui contient le code suivant :

``` java
@SpringBootApplication
public class GestionRoleUserApplication  {

    public static void main(String[] args) {

        SpringApplication.run(GestionRoleUserApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean // pour qu'il soit execute au debut de l'application
    // utilisation de lambda
    CommandLineRunner start(UserService userService){
        return args -> {
            User user1= new User();
            user1.setUsername("saad");
            user1.setPassword("123456");
            //userService.addNewUser(user1);

            User user2= new User();
            user2.setUsername("sokaina");
            user2.setPassword("123456");
            //userService.addNewUser(user2);



            Stream.of("student","user","admin").forEach(r->{
                Role role2=new Role();
                role2.setRoleName(r);
                //userService.addRole(role2);
            });


            /*
            userService.addRoleToUser("saad","admin");
            userService.addRoleToUser("saad","user");
            userService.addRoleToUser("sokaina","user");
            userService.addRoleToUser("sokaina","student");
            */

            try{
                User user = userService.Authentification("sokaina","123456");
                System.out.println(user.getUserId());
                System.out.println(user.getUsername());
                System.out.println(user.getPassword());
                System.out.println(" les roles associe a ce utilisateur");
                user.getRoles().forEach(r->{
                    System.out.println("Role => "+r);
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        };
    }

}
```

Aprés avoir éxécuter le code, accéder à ce lien http://localhost:8080/h2-console est vérifier si les données existe dans la base de données H2.

## Changer Base de données à Mysql
On va ajouter ces parametres au fichier application.properties.

``` properties
server.port=8080
spring.datasource.username=root
spring.datasource.password=
spring.datasource.url=jdbc:mysql://localhost:3306/role_user?createDatabaseIfNotExist=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDBDialect
spring.jpa.show-sql=true
```
et dans le fichier pom.xml ajouter cette dépandance :

``` xml
   <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.32</version>
   </dependency>
```

## Test

Pour tester si migration de h2 vers MySQL bien passe . vérifier si les données sont insérer dans la base de données avec succés.

On peut acceder à un URL localhost:8080//users/{username} sachant que username est un paramétre, grace à le code suivant dans le classe ```UserController.java```

``` java
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users/{username}")
    public User user(@PathVariable String username){
        User user = userService.findUserByUserName(username);
        return  user;
    }
}
```
Ce travail nous a permis de bien connaitre comment implémenter les relations Many To Many se que fait au niveau du Java grace à l’API JPA, et on a initié avec Hibernate, et bien évidement la manière de configuration d’un projet Spring pour faciliter la tache au développeur.
