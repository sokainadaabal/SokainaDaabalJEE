# Spring MVC JPA Hibernate Spring Data Thymeleaf
# Objectif 
L'objectif de cette activté est de créer dans la premiere partie une application Web JEE basée sur Spring MVC, Thylemeaf et Spring Data JPA qui permet de gérer les patients. dans la deuxiéme partien, on va sécurisé notre application à l'aide de spring security.
# Partie 1 : Gestion Patient | Application Web
Nous allons prendre en charge le projet de gestion des patients que nous allons effectuer dans le cadre de l'activité 3, puis nous allons le transformer en application Web. qui vq permet de réaliser ses fonctionnalié suivante: 
- Afficher les patients.
- Faire la pagination.
- Chercher les patients.
- Supprimer un patient.
- Faire des améliorations supplémentaires.
# Partie 2 : Spring Security - Spring Data | Securing a Web Application.
Spring securite offre plusieure methode pour faire l'authentification dans ce projet nous avons utliser Statefull authentification 
## Statefull authentification 
### Principe : 
Statefull authentification est couramment utilisée dans de nombreuses applications, en particulier pour les applications qui ne nécessitent pas trop d'évolutivité.
### Comment ça fonctionne ? 
La session avec état est créée côté backend et l'ID de référence de session correspondant est envoyé au client. Chaque fois que le client fait une demande au serveur, le serveur localise la mémoire de session en utilisant l'identifiant de référence du client et trouve les informations d'authentification.

Dans ce modèle, vous pouvez facilement imaginer que si la mémoire de session est supprimée côté backend, alors l'ID de référence de session, que le client détient, n'a aucun sens.

![image](https://github.com/sokainadaabal/SokainaDaabalJEE/assets/48890714/650027c1-4d15-4bd9-a9fb-921f8fe617c3)

## Dependencies

Ajoutez les dépendances suivantes au projet ``` pom.xml ```  a fin d'utliser la bilotheque de ``` Spring Security``` :

``` xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
```
## Configuration de la securite | Spring Security
Dans le package ```ma.enset.sec```, on va ajouter class ```SecurityConfig```. ce dernier va nous permettons de configurer la securite de notre application.
```java 
@Bean
    SecurityFilterChain defaultSecurityFilterChain(@NotNull HttpSecurity http) throws Exception {
        http.formLogin().permitAll(); // formulaire authentication contient csrf caché // personnaliser la page
        http.authorizeRequests().requestMatchers("/").permitAll();
        http.authorizeRequests().requestMatchers("/webjars/***").permitAll();
        http.authorizeRequests().requestMatchers("/admin/**").hasAuthority("ADMIN");
        http.authorizeRequests().requestMatchers("/user/**").hasAuthority("USER");
        http.authorizeRequests().anyRequest().authenticated(); // toute request envoyer doit que l'utilisateur authentifier
        http.exceptionHandling().accessDeniedPage("/403");
        return http.build();
    }
``` 
> cette fonction nous permet de donnee les droits d'acces certain url pour des utlisateurs specifiaues. et autorise l'acces a des url sans specifie le role de l'utlisateur. En generale, Cette fontion permet de montionner les requetes qui doivent etre authentifiees.
## Creation des utlisateurs 

Pour cree des utlisateurs de l'application on va ajouter cette fonction.
``` java
 @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        PasswordEncoder passwordEncoder=passwordEncoder();
        ArrayList<UserDetails> users= new ArrayList<>();

        UserDetails user = User.withUsername("user1")
                .username("user1")
                .password(passwordEncoder.encode("1234")) // {noop} si en n'utilise pas Passwo
                .roles("USER")
                .build();
```
# Tools
# Démenstration 
