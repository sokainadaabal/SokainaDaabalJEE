# Mise en oeuvre d'un micro-service

## Introduction 

Dans cette activité, nous apprendrons à créer un micro-service de gestion des comptes bancaires.

## Les dépendances
Le projet va initiliser avec ses dépendances suivantes :
* Spring Web
* Spring Data JPA
* H2 Database
* Lombok
* Spring GraphQL
* Mysql 
## Crée les entités JPA
Dans le package ```ma.enset.microService.entities```, nous aons ajouter les entités suivantes : 
* ``` Client.java ```
``` java
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    @OneToMany(mappedBy = "client")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Compte> compteList;
}

```
* ``` Compte.java ```
``` java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Compte {
    @Id
    private String id;
    private Date creatAt;
    private Double balance;
    private String currency;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    @ManyToOne
    private  Client client;
}
```
Dans le packages ```ma.enset.microService.enums````, nous avons ajouter une enumeration a fin de determiner le type de compte.
* ``` AccountType.java ```
``` java
public enum AccountType {
    CURRENT_ACCOUNT,SAVING_ACCOUNT
}
```
## Les répositories
Dans le packages ```ma.enset.microService.repositories```, nous avons ajouter les interfaces suivantes : 
* ``` ClientRepository.java ```
``` java 
public interface ClientRepository extends JpaRepository<Client,Long>{
}
```
* ``` CompteRepository.java ```
``` java 
@RepositoryRestResource
public interface CompteRepository extends JpaRepository<Compte,String> {
    @RestResource(path = "/byType")
    List<Compte> findByType(@Param("t") AccountType type); // spring rest va implementer la methode.
    @RestResource(path = "/byClient")
    List<Compte> findByClient(@Param("c") Client client); // spring rest va implementer la methode.

}
```


Remarque :
 * ```@RepositoryRestResource``` : permet la génération automatique de endpoint RESTful pour effectuer des opérations CRUD (Create, Read, Update, Delete) sur les données sous-jacentes gérées par le référentiel. 
 * ```@RestResource(path = " ")``` : est une annotation fournie par Spring Data qui permet de personnaliser les endPoints RESTful générés pour un référentiel (repository) spécifique. cette notation utiliser pour les methodes. comme l'endPoint RESTful suivant:  ```http://localhost:8080/comptes/search/byType?t=CURRENT_ACCOUNT```.

## Les services
Dans le package ```ma.enset.microService.service```, nous ajoutons l'interface ``` CompteService ``` et leur calsse d'implementation.
* ``` CompteService.java ```
``` java 
public interface CompteService {
     CompteResponseDTO addCompte(CompteRequestDTO compteRequestDTO);

     CompteResponseDTO updateCompte(String id, CompteRequestDTO compteRequestDTO);
}
```
* ```CompteServiceImpl.java ```
```java

@Service
@Transactional
public class CompteServiceImpl implements CompteService {
    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private CompteMapper compteMapper;
    @Override
    public CompteResponseDTO addCompte(CompteRequestDTO compteRequestDTO) {
        Compte compte = compteMapper.getCompte(compteRequestDTO);
        Compte compte1=compteRepository.save(compte);
        return compteMapper.getCompteResponseDTO(compte1);
    }

    @Override
    public CompteResponseDTO updateCompte(String id, CompteRequestDTO compteRequestDTO) {
        Compte compteFind=compteRepository.findById(id).orElseThrow();
        Compte compte = new Compte();
        if(compteFind!=null) compte=compteMapper.getCompteById(compteFind,compteRequestDTO);
        return compteMapper.getCompteResponseDTO(compteRepository.save(compte));
    }
}

```
## Les DTOs

Les DTOs sont des objets légers utilisés pour transférer des données entre les entites JPA et GraphQL.
Dans le package ```ma.enset.microService.dto```, nous avons ajouter les classe suivantes :

* ``` CompteRequestDTO.java ```
``` java 
@Data @NoArgsConstructor @AllArgsConstructor @Builder

public class CompteRequestDTO {

    private Double balance;
    private String currency;
    private AccountType type;

}
```
* ``` CompteResponseDTO.java ```
``` java 
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompteResponseDTO {
    private String id;
    private Date creatAt;
    private Double balance;
    private String currency;
    private AccountType type;

}
```

## Les mappers

Les mappers sont des composants logiciels utilisés pour convertir des objets d'un type à un autre, dans notre cas on va covertire des objets de type JPA a autre de type GraphQL.

Dans le package ```ma.enset.microService.mappers```, nous ajetons les mappers suivants : 

* ```CompteMapper.java```
``` java
@Component
public class CompteMapper
{
    @Autowired
    CompteRepository compteRepository;
    public CompteResponseDTO fromCompte(Compte compte){
        return getCompteResponseDTO(compte);
    }

    public static CompteResponseDTO getCompteResponseDTO(Compte compte) {
        CompteResponseDTO compteResponseDTO = new CompteResponseDTO();
        BeanUtils.copyProperties(compte,compteResponseDTO);
        return compteResponseDTO;
    }
    public Compte getCompte(CompteRequestDTO compteRequestDTO){
        Compte compte = new Compte();
        compte.setId(UUID.randomUUID().toString());
        compte.setCreatAt(new Date());
        BeanUtils.copyProperties(compteRequestDTO,compte);
        return compte;
    }
    public Compte getCompte(Compte compte, Compte contFind){
        BeanUtils.copyProperties(compte,contFind);
        return contFind;
    }

    public Compte getCompteById(Compte compteFind, CompteRequestDTO compteRequestDTO) {
        Compte compte = new Compte();
        BeanUtils.copyProperties(compteRequestDTO,compte);
        compte.setId(compteFind.getId());
        compte.setCreatAt(compteFind.getCreatAt());
        return  compte;
    }
}
```
## Tester la couche DAO 

Pour tester notre application ,  nous ajoutons ce code dans la classe ```MicroServiceApplication.java```.
``` java
@SpringBootApplication

public class MicroServiceApplication {
    @Autowired
    CompteRepository compteRepository;
    @Autowired
    ClientRepository clientRepository;
    public static void main(String[] args) {
        SpringApplication.run(MicroServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start()
    {
        return args -> {
            Stream.of("Sokaina","Saad","Ahmed saad","Mohammed saad").forEach((m)->
                   clientRepository.save(Client.builder().nom(m).build()));
            clientRepository.findAll().forEach(client -> {
                for(int i=0 ; i<10; i++){
                    Compte compte =Compte.builder()
                            .id(UUID.randomUUID().toString())
                            .type(Math.random()>0.5? AccountType.SAVING_ACCOUNT:AccountType.CURRENT_ACCOUNT)
                            .balance(1000*Math.random()*90000)
                            .creatAt(new Date())
                            .currency("MAD")
                            .client(client)
                            .build();
                    compteRepository.save(compte);
                }
            });
            clientRepository.findAll().forEach(client -> {
               List<Compte> compteList=compteRepository.findByClient(client);
               client.setCompteList(compteList);
            });
        };
    }
}

```

- ``` @Bean ``` : ca permet de lancer la methode start lorsque l'application demarre. 
- Nous avons utliser les repositories JPA pour creer les comptes bancaires.
- pour verifie que les comptes sont ajoute a la base de donnee, on va consulter l'interface graphique de MySql ``` phpMyAdmin ```.

![image](https://user-images.githubusercontent.com/48890714/236681784-f90e2083-fcee-4ff6-b4b0-1e6dbffa4d21.png)


![image](https://user-images.githubusercontent.com/48890714/236681656-4dffb3fa-1567-4902-91fd-b87db42038d9.png)

## Créer le Web service Restfull qui permet de gérer des comptes
Pour creer le web service Restfull qui permet de  gérer des comptes, nous avons besoin seulement d'ajouter une annotation RepositoryRestResource dans l'interface ```CompteRepository.java``. 
Cette annotation nous permet d'utiliser Spring data Rest pour generer automatiquement les endPoints.
* Exemple des endPoints Generer :
  - ```http://localhost:8080/comptes```: permet de récupérer la liste des comptes bancaires.
  
  ![image](https://user-images.githubusercontent.com/48890714/236682864-4fe51bf5-d18a-4dc5-8c68-8e45b87ec852.png)
  - ``` http://localhost:8080/clients```:  permet de récupérer la liste des clients.
  
  ![image](https://user-images.githubusercontent.com/48890714/236682927-d392517f-b194-4b93-ba1a-abb66e8735c5.png)
  
## RestController
Dans le package ```ma.enset.microService.web```, nous avons creer un controller Rest qui permet de gérer des comptes bancaires.
- ``` CompteRestController.java```
``` java
@RestController
@RequestMapping("/api")
public class CompteRestController {
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private CompteMapper compteMapper;
    @Autowired
    private CompteService compteService;
    public CompteRestController(CompteRepository compteRepository) {
        this.compteRepository = compteRepository;
    }
    @GetMapping("/banckCompte")
    public List<Compte> listofCompte(){
        return compteRepository.findAll();
    }
    @GetMapping("/banckCompte/{id}")
    public Compte getCompte(@PathVariable("id") String id){
        return compteRepository.findById(id).orElseThrow(()->new RuntimeException(String.format("Compte non trouve",id)));
    }
    /* En utilisant la projection */
    @PostMapping("/banckCompte")
    public CompteResponseDTO save(@RequestBody CompteRequestDTO compte){
        return  compteService.addCompte(compte);
    }
    @PutMapping("/banckCompte/{id}")
    public Compte update(@PathVariable String id,@RequestBody Compte compte){
        Compte compteFind=compteRepository.findById(id).orElseThrow();
        compteFind=compteMapper.getCompte(compte,compteFind);
        return  compteRepository.save(compteFind);
    }
    @DeleteMapping("/banckCompte/{id}")
    public void deleteCompte(@PathVariable("id") String id){
        compteRepository.deleteById(id);
    }
}
```
nous avons utliser la projection ```CompteResponseDTO ``` pour la methode  save.
## Test EndPoints
maintenant en test les endPoints s'il fonctioone.
- ```http://localhost:8080/api/banckCompte``` : affiche une liste des comptes bancaires.
- ```http://localhost:8080/api/banckCompte/fe910435-f43c-4d12-8de0-b223016f81e5```: permet de recupperer le compte de l'identifiant suivant : ```fe910435-f43c-4d12-8de0-b223016f81e5```.
il existe d'autre endPoints que ce soit pour supprimer, modifier ou ajouter un compte.
## Test Client comme Postman
Maintenat on va tester ses endPoints on utilisons Postman.
![image](https://user-images.githubusercontent.com/48890714/236683760-7e6268bc-8105-43dd-8a86-58fb0939cece.png)

## Générer et tester le documentation Swagger de des API Rest du Web service

Pour la documentation de notre Api, nous utilisons Swagger.
C'est quoi Swagger ?  on peut dire que c’est un ensemble d’outils pour aider les développeurs dans la conception, le build, la documentation et la consommation d’API.
Pour ouvrire l'interface graphique de Swagger, on acceder a le lien suivant : ```http://localhost:8080/swagger-ui/index.html```.
cette interface va illustrer l'ensemble des apis de votre micro service et comment l'utliser.
![image](https://user-images.githubusercontent.com/48890714/236684221-a63b6d00-56f4-485d-b98d-7f4a00daea8c.png)

## Créer un Web service GraphQL pour ce micro-service

## Conclusion
Dans cette activite nous avons connaitre comment cree un micro service et generer une documentation Swagger et aussi nous avons location de creer un web service GraphQL pour le micro-service que nous avons deja cree.


