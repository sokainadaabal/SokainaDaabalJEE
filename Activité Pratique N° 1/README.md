# Principe de l’Inversion de Contrôle et Injection des dépendances

Dans cette partie on va initier l'inversion de contrôle et l'injection des dépendances, par la séparation des codes métier aux codes techniques, ainsi on va faire l'injection des dépandances par le couplage faible et par la dépendances des classes avec des interfaces.

## Architecture d'application 

Il existe deux type d'architectures des applications : 

- ```Monotolithique``` : c'est à dire une seule architecture de l'application, l'application est développée dans un seuke bloc autre mot à dire c'est un modèle traditionnel de programme de développement, conçu comme une unité unifiée autonome et indépendante d'autres apps.
 
   Quelques avantages d'une architecture monolithique:
    - Déploiement facile.
    - Développement : seul base de code  à dévlopper.
    - Performances. 
    - Tests simplifiés.
    - Débogage facile
 
   Inconvénients d'une architecture monolithique :
    - Vitesse de développement plus lente.
    - Évolutivité.
    - Fiabilité.
    - Obstacle à l'adoption de la technologie.
    - Manque de flexibilité.
    - Déploiement.
- ```Micro-Services```: est une méthode architecturale qui repose sur une série de services déployables indépendamment. Ces services ont leur propre logique métier et leur propre base de données avec un objectif précis. La mise à jour, les tests, le déploiement et la mise à l'échelle ont lieu dans chaque service.
  
    Les avantages des microservices :
    - Agilité.
    - Évolutivité flexible.
    - Déploiement continu.
    - Facilité d'administration et de test.
    - Déploiement indépendant.
    - Flexibilité technologique.
    - Fiabilité élevée.
    - Équipes satisfaites.

   Inconvénients des microservices :
    - Développement tentaculaire : plus de complexité.
    - Coûts d'infrastructure exponentiels.
    - Frais organisationnels supplémentaires.
    - Défis de débogage.
    - Manque de standardisation.
    - Manque de responsabilité claire 
![image](https://user-images.githubusercontent.com/48890714/230246863-4872eb6e-7dc3-4a30-a5d2-aea6e1be4d8d.png)

## Exigences d'un projet informatique

Chaque projet informatique à deux types des exigences :
 - ```Exigences Fonctionnelle``` sont les besoins fonctionnelles , les attentes du utilisateurs finale ou bien les beoins métiers de l'entreprise.
 - ```Exigences Techniques``` sont les besoins techniques quand peut résumer dans les points suivants :
    - La performance : ce que concerne le temps de réponse de l'application, le problème de montée en charge, qu'est peut-être résolue par la scalabilité, cette dérnière peut prendre deux formes.
        - Horizontale : se caractérise par l’équilibrage de charge et la tolérance aux pannes, cette scalabilité conciste sur le démarrage de l’application en  plusieurs instances dans différentes machines pour palier au problème de montée en charge, avec un serveur Load Balencer pour l’équilibrage de charge des requete reçu de chez les clients.
        - Verticale : c'est la technique qui permet d'augmenter les resources de la machine ou est exécuté une application, par resources on entend par là, la RAM, le disque dure, le processeur/CPU etc. l'application va créer un thread pour chaque requete d'un client.
     ![image](https://user-images.githubusercontent.com/48890714/230366076-441c472a-7ff6-40f6-9a5e-6a4e699fd221.png)
    - La maintenance : l'application doit être évolutive dans lle temps, mais en prenant en compte la régle ```une application doit être fermée à la modification et ouverte à l’extension``` parce que les modifications peuvent générer les problèmes de régression.
    - La sécurité : les failles de sécurité sont l'un de problème critique au développement informatque, il faut donner plus d'un attention à la persistance des données et la gestion des transactions.
    - Les versions : web / mobile / desktop.

## Inversion de contrôle
L'inversion de contrôle est un principe consite à séparer de tous ce qui métier à tous ce qui est technique. les développeurs s'occupe seulement la partie code  et le framework va occuper la partie technique. on basant sur l'architecture AOP(Aspect Oriented Programming).

```
La programmation orientée aspect (AOP) complète la programmation orientée objet (POO) en offrant une autre façon de penser la structure du programme. L'unité clé de la modularité en POO est la classe, alors qu'en AOP l'unité de modularité est l' aspect . Les aspects permettent la modularisation de préoccupations telles que la gestion des transactions qui couvrent plusieurs types et objets. (Ces préoccupations sont souvent qualifiées de préoccupations transversales dans la littérature AOP.)
```
## Injection des dépandances

Injection des dépandances présent en deux types :
 - Couplage fort : Les classes dépendent des autres classes, il est difficile de faire des modification car chaque fois on va modifier au code source.
 - Couplage faible : Les classes dépendent des interfaces et pas des autres classe, ce couplage facilite l'attribution d'une modification.
 
 ## Spring IOC
 Préférable de faire la laison des composants du programme on utilisant un framework ce pemert de changer les composants ou le comportement facilment.
 Spring IOC consite sur lire un  fichier XML qui déclare quelle sont les différnetes classes à instancier et d'assurer les dépendances entre les différentes instances, et pour faire une nouvelle implémentation dans l'application on déclare dans le fichier xml de spring.
 Il existe deux méthode :
 - ```XML``` :  Spring va lire le fichier xml de configuration spring puis il va s’occuper des injections des dépendances.
 -```Annotation``` : ajouter des annotations aux classes pour déclarer au Spring qu’il doit les instancier au démarrage de l’application, ainsi qu’auprés des objets qu’on doit attribués des dépendances aux autres instances des classes qui sont déjà déclarées.

## Architecture de probléme
## La couche DAO 
Cette couche interroge à la base de données.
- Step 1 : Création d'interface contient la métode qu'on aura besoin dans la couche DAO
``` java
public interface IDao {
     double getdate();
}
```
- Step 2 : Création de classe d'implémentation de interface DAO, dans la quelle on va redéfini la méthode 'getData'.

``` java 
public class DaoImpl implements IDao{

    @Override
    public double getdate() {
        /*
         se connecter à la base de données pour récupérer la température
         */
        System.out.println("Version Base de données");
        double date = Math.random() * 50;
        return date;
    }
}
```
Dans le cas de modifications, on ajoute un autre classe qui va implémenter l'interface DAO sans besoin de modifier le code source. à ce stade la, on applique la régle ```l’application doit être fermée à la modification et ouverte à l’extension```.
## La couche metier
Cette couche continet les besoins fonctionnels de l'application.
- Step 1 : Création d'interface contient la métode qu'on aura besoin dans la couche Metier, cette méthode va faire le traitement de besoin fonctionnel du utilisateur.
``` java
public interface Imetier {
    double calcul();
}
```
- Step 2 : Création de classe d'implémentation de interface Metier, dans la quelle on va redéfini la méthode 'calcul', avec la déclaration d'un objet de type d'interface 'DAO' en utilisant un couplage faible.

``` java 
public class MetierImpl implements Imetier {
    private IDao dao=null; //coublage faible
    @Override
    public double calcul() {
        double tmp=dao.getdate();
        double resultat =tmp*420 / Math.tan(tmp*Math.PI);
        return resultat;
    }
   /*
    Inejecter dans la variable dao  un object une classe qui implémente l'interface IDao
    */
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
```
## La couche présentation 
Cette couche contient la partie présentation ( US user interface).
La communication entre la classe Metier et DAO, besoin de faire l'injection des dépendance. avec deux méthode : 
### Instanciation Statique
Dans la couche présentation, on crée une classe ```Presenatation.java``` ou on appliquera une injection statique des dépendances. il joue le rôle de Factory Class, elle génère les dépendances.

``` java
public class Presentation {
    public static void main(String[] args) throws  Exception{
        /*
         Injection des dépendances par instanciation statique => new  (couplage forte)
         */
        IDao dao=new DaoImpl();
        MetierImpl metier = new MetierImpl();
        metier.setDao(dao);
        System.out.println("Résultat :"+metier.calcul());
        
    }
}
```
### Instatnciation Dynamique
Si en veux réspecter la régle ```l’application doit être fermée à la modification et ouverte à l’extension```, avec la métode statique dans le cas d'une nouvelle extension nous obligons à modifer le code. par contre avec la méthode dynamiqye ne faisant seulement l'injection des dépendances sans changement de code. un simple modification au niveau de fichier ```config.txt```

```
public class Presentation2 {
    // les exception a connaitre FileNotFoundException,ClassNotFoundException,InstantiationException,IllegalAccessException,ClassCastException

    public static void main(String[] args) throws  Exception{
         /*
        Injection des dependances par instanciation dynamique => Fichier de configuration
         */
        Scanner scanner = new Scanner(new File("config.txt"));
        String daoClasseName= scanner.nextLine();
        Class cDao = Class.forName(daoClasseName);
        cDao.newInstance(); // creation instance , apple constructeur par desfaut.
        IDao iDao=(IDao) cDao.newInstance();
        System.out.println("Résultat :" + iDao.getdate());

        String metierClasseName= scanner.nextLine();
        Class cMetier = Class.forName(metierClasseName);
        cMetier.newInstance(); // creation instance , apple constructeur par desfaut.
        Imetier iMetier=(Imetier) cMetier.newInstance();

        Method method = cMetier.getMethod("setDao", IDao.class);
        method.invoke(iMetier,iDao); // la meme chose que metier.setDao(dao);

        System.out.println("Résultat :" + iMetier.calcul());

    }
}
```

## Le dossier ext 
Ce dossier contient la partie d'extension de l'application.
à la maintenace on a respecté la régle d'or ```l’application doit être fermée à la modification et ouverte à l’extension``` et on a ajouté un autre dossier pour déclarer des nouvelles implémentations de l'inteface IDao.
``` java
public class DaoImplVWeb implements IDao {
    @Override
    public double getdate() {
        System.out.println("Version web Service");
        return 90;
    }
}
```
une autre implémentation de l'interface IDao dans la partie d'extension :
``` java
public class MetierImplVWeb implements Imetier {
    IDao dao =null;
    @Override
    public double calcul() {
        System.out.println("Version web Service");
        return 203;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}
```
## Fichier config.txt
Ce fichier contient la configuration pour l'injection des dépendances dynamiquement.
il contient les noms des différentes implémentatios déja dclarés qu'on va l'utiliser pour l'injection des dépandances d'une façon dynamique sans passer par l'instanciation des objets en utilisant mot cle ```new```.

``` txt 
ma.enset.ext.DaoImplVWeb
ma.enset.ext.MetierImplVWeb
```

## FrameWork Spring 
### Les dépandances ```pom.xml```
Dans «External librairies» on a téléchargé 3 jars :
1. Spring core
2. Spring context
3. Spring beans
Ces jars vont être utilisés par Spring.
On utilisant Spring pour injecter les dépendances automatiquement dans le fichier ```pom.xml```: 
``` xml
<dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>5.3.20</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.3.16</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-beans</artifactId>
            <version>5.3.18</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
```
 
 ### XML
 ``` java
 public class PresentationSpringXML {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Imetier imetier =(Imetier) context.getBean("metier");
        System.out.println(imetier.calcul());
        IDao iDao = (IDao) context.getBean("dao");
        System.out.println(iDao.getdate());
    }
}
```
La structure de fichier ```applicationContext.xml```
Le fichier xml de configuration de spring, dans la balise « beans » on déclare les instances qu’on a besoin avec ‘id’ est le nom de chaque instance, et ‘class’ le nom de la classe, et pour injecter la dépendance il y a la balise « property » avec un ‘name’ nom de l’objet et ‘ref’ la référence vers quelle instance :
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="dao" class="ma.enset.ext.DaoImplVWeb"></bean>
    <bean id="metier" class="ma.enset.metier.MetierImpl">
        <property name="dao" ref="dao"></property>
        <!--constructor-arg ref="dao"></constructor-arg-->
    </bean>
</beans>
```
### Annotation
``` java
public class PresSpringAnnotation {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("ma.enset.dao","ma.enset.metier","ma.enset.ext");
        Imetier imetier = (Imetier) context.getBean("metier");
        System.out.println(imetier.calcul());
        IDao iDao = (IDao) context.getBean("dao");
        System.out.println(iDao.getdate());
    }
}
```
## Maven 
Maven : est un outil n’est pas d’un framework, qui permit l’automatisation des processus de développement d’un projet java, il utilise un paradigme connu sous le nom de POM (Project Object Model).
Principe : à chaque fois on ajoute une dépendance au fichier xml ‘pom.xml’ il va  chercher dans le ‘repository local’ s’il en trouve il va les utiliser, sinon il va se connecter à l’internet et il va télécharger les dépendances déclarées.
### Les commandes de Maven
- ```mvn compile``` ->  compile le code source du projet.
- ```mvn test``` ->  parcourir le projet et à chaque fois il trouve un test unitaire il va l’exécuter, puis il montre qui sont les tests réussit et qui ne sont pas.
- ```mvn package``` ->  exécute la commande ```mvn compile``` et ```mvn test``` puis archive le projet maven dans archive (.jar / .war).
- ```mvn install``` ->  exécute la commande ```mvn compile``` et ```mvn test``` puis elle installe le projet dans le repository local pour l’utiliser au cas de besoin.
- ```mvn deploy``` ->  déployer un projet vers un serveur.
- ```mvn site``` ->  générer un site de documentation.

Dans cette partie, nous avons étudier l'injection des dépendance et l'inversion de controle avec instation dynamique et statique, Sprig XML et annotation et finalement avec le framework Spring (XML et Annotation).
