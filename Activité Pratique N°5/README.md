# Web services SOAP WSDL UDDI avec JaxWS

## Objectifs
 Création d'un service web qui permet de :
- Convertir un montant de l’auro en DH
- Consulter un Compte
- Consulter une Liste de comptes
## Partie serveur : Déployer le Web service avec un simple Serveur JaxWS
### 1. Ajouter une dépendance au fichier pom.xml
``` xml
        <dependency>
            <groupId>com.sun.xml.ws</groupId>
            <artifactId>jaxws-ri</artifactId>
            <version>4.0.1</version>
            <type>pom</type>
        </dependency>
```
### 2. La classe Compte 

Cette classe présente le compte bancaire de l'utilisateur, et qui contient les attribus suivants :

- ``` int code ``` : Il présente la clé primaire qui numéro de compte.
- ``` double solode ```: il présente le solde de client.
- ``` Date dateCreation ``` : il présente la date de creation de compte.

Il contient aussi le constructeur et les getters et  setters de paramètres.

### 3. la classe BanqueService

Cette classe présente un service SOAP, et contient les méthodes suivantes :
 
 - ``` conversion(double mt)  ``` : qui permet de convertire les eruo a DH.
 - ``` getCompte(int code) ```: qui permet des créé un compte à partir de son code.
 - ``` listCompte() ``` : Permet de récupérer les informations de tous les comptes.
 
 ``` 
 SOAP est une spécification XML pour l'envoi de messages sur un réseau. 
 
 Les messages SOAP sont indépendants de tout système d'exploitation et peuvent utiliser divers protocoles de communication, notamment HTTP et SMTP.

 SOAP est lourd en XML, il est donc préférable de l'utiliser avec des outils/frameworks. 
 
 JAX-WS est une infrastructure qui simplifie l'utilisation de SOAP. Il fait partie du standard Java.
```

### 4. JAX-WS 

L'API Java pour les services Web XML (JAX-WS) est une API standardisée pour la création et l'utilisation de services Web SOAP (Simple Object Access Protocol).

Dans notre application, nous avons utiliser approche ascendante.

Nous devons créer à la fois le classe du point de terminaison. Le WSDL est généré à partir des classes lors de la publication du service Web.

Créons un service Web qui effectuera des opérations simples sur les données des comptes.

La classe BanqueService définit un contrat abstrait pour le service Web.  Les annotations suivante :
- ``` @WebService ``` indique qu'il s'agit d'une interface de service Web.
- ``` @WebMethod ``` est utilisé pour personnaliser une opération de service Web.
- ``` @WebParam ``` est utilisé pour indiquer que le paramètre est un paramètre de service web.

- ``` La classe banqueService ```
``` java 
  @WebService(serviceName = "BanqueWS")
  public class BanqueService {
      @WebMethod(operationName = "Convert")
      public double conversion(@WebParam(name="montant") double mt){
          return mt+11.3;
      }

      @WebMethod
      public Compte getCompte(@WebParam(name="code") int code){
          return new Compte(code,Math.random()*65000,new Date());
      }

      @WebMethod
      public List<Compte> listCompte(){
         return List.of(
                 new Compte(1,Math.random()*65000,new Date()),
                 new Compte(2,Math.random()*65000,new Date()),
                 new Compte(3,Math.random()*65000,new Date())
         ) ;
      }
  }
```
### 5, Classe  ServerJWS

Pour publier les services Web , nous devons transmettre une adresse et une instance de l'implémentation du service Web à la méthode publish() de la classe javax.xml.ws.Endpoint:

``` java
  public class ServerJWS {
      public static void main(String[] args) {
          Endpoint.publish("http://localhost:8080/",new BanqueService());
      }
}
```
À ce stade on peut tester notre service web.
## Partie test de serveur web 
### 1. Consultation et analyse du fichier WSDL avec un Browser HTTP

![image](https://user-images.githubusercontent.com/48890714/229384252-40baf1ef-e117-4b63-9aa9-65be016d21e3.png)

![image](https://user-images.githubusercontent.com/48890714/229384319-0a44d340-aa9b-4592-87f9-ffc4a0cc6f42.png)

### 2. Test des opérations du web service avec un l'outil SoapUI

![image](https://user-images.githubusercontent.com/48890714/229384428-b625279e-9950-4e3d-8cd5-1eec23476b37.png)

![image](https://user-images.githubusercontent.com/48890714/229384518-f73f0d26-25a6-4abb-bcd5-66f3f6b02e20.png)

![image](https://user-images.githubusercontent.com/48890714/229384546-416b7fea-15e6-4135-b9f5-9cff07f9138d.png)

![image](https://user-images.githubusercontent.com/48890714/229384573-a047eec7-6e77-4a70-a72e-d17ac14fe0ad.png)

## Partie Client JWS

Nous avons initialiser un projet maven sous le nom  ``` ClientWebService ``` pour se connecter à distance au service Web.

### 1. La class ClientWS

Le client de service Web utilise le BanqueService_service généré pour se connecter au serveur et effectuer des appels de service Web à distance.
On génère le package proxy pour tester le service SOAP. Pour cela, on utilise l'extension IntelliJ JaxWS.

``` java 
 public class ClientWS {
    public static void main(String[] args) {
        BanqueService stub = new BanqueWS().getBanqueServicePort();
        System.out.println(stub.convert(69999));
        Compte compte= stub.getCompte(3);
        System.out.println(compte.getCode());
        System.out.println(compte.getSolode());
    }
  }
```

### test 
![image](https://user-images.githubusercontent.com/48890714/229384872-e33eaa4b-d301-4c05-b7f4-9cb18c8ef72f.png)

## Partie Client PHP 

On va tester notre service web avec un notre client céer avec Php.

 - ``` banque.php ```
 
 ``` php 
   <?php
     $mt=0; $res=0;
    if(isset($_POST['action'])){
     $action=$_POST['action'];
     if($action=="OK") {
         if (isset($_POST['montant'])) {
             $mt = $_POST['montant'];
             $clientSAOP = new SoapClient("http://localhost:8080/?wsdl");
             $param = new stdClass();
             $param->montant = $mt;
             $res = $clientSAOP->__soapCall("Convert", array($param));
             $res=$res->return;
         }
     }
     elseif ($action=="Comptes") {
         $clientSAOP = new SoapClient("http://localhost:8080/?wsdl");
         $Compte=$clientSAOP->__soapCall("listCompte", array());
      }
    }
    ?>


    <html>
        <body>
            <form action="banque.php" method="post">
               Montant : <input type="text" name="montant" value="<?php echo($mt) ?>">
                <input name="action" type="submit" value="OK">
                <input name="action" type="submit" value="Comptes">
            </form>
            Résultat <br>
            <?php if(isset($res)) {?>
             <?php echo($mt) ?> en Euro = <?php echo $res ?> en DH
            <?php }?>
            <br>
            Liste des comptes
            <br>
            <?php if(isset($Compte)) {?>
                <table border="1">
                    <tr>
                        <th>Code</th>
                        <th>Solde</th>
                    </tr>
                    <?php foreach ($Compte->return as $cp) {?>
                        <tr>
                            <td><?php echo ($cp->code) ?></td>
                            <td> <?php echo ($cp->solode) ?></td>
                        </tr>
                    <?php }?>
                </table>

            <?php }?>
        </body>
    </html>
 ```
 
### 2. test 

![image](https://user-images.githubusercontent.com/48890714/229385121-08c73cc6-d8da-465c-b944-0534e8d2f111.png)

![image](https://user-images.githubusercontent.com/48890714/229385147-70ff3053-faad-4f31-8d59-0aeb892e7919.png)


## les technologies utilisees 
 ### 1. Java
 
 Java est le langage de choix pour créer des applications à l'aide de code managé qui peut s'exécuter sur des appareils mobiles.
 
 Plus d'info  [Java](https://inside.java/2023/03/21/the-arrival-of-java-20/)
 
 ### 2. JaxWS

JAX-WS (Java API for XML Web Services) Est un ensemble d'interfaces (API) De langage de programmation Java dédié au développement de services web. L'ensemble fait partie de la plate-forme Java EE.
 
 Plus d'info [JaxWS](https://boowiki.info/art/plate-forme-java/jax-ws.html)
 
 ### 3. Architecture du web service
 ![image](https://user-images.githubusercontent.com/48890714/229385403-c4a4abcf-89e8-4bd3-9f9e-d6d9fd90fffc.png)

 ### 4. Comment il travaille ?
 
![image](https://user-images.githubusercontent.com/48890714/229385416-096992ed-3940-4875-8b07-7d64b1220922.png)
![image](https://user-images.githubusercontent.com/48890714/229385421-c891a194-d7a9-4adc-91d3-95998c542de7.png)


1. Le client demande au stub de faire appel à la méthode conversion(12)
2. Le Stub se connecte au Skeleton et lui envoie une requête SOAP
3. Le Skeleton fait appel à la méthode du web service
4. Le web service retourne le résultat au Skeleton
5. Le Skeleton envoie le résultat dans une la réponse SOAP au Stub
6. Le Stub fournie lé résultat au client


## Conclusion 

  Dans cette activite nous avons créer un service Web SOAP à l'aide de l'API JAX-WS. Nous avons également écrit un client JAX-WS qui peut se connecter à distance au serveur et effectuer des appels de service Web. nous avons aussi l'occasion de tester ce service web avec d'autre client créer avec le langage php.



