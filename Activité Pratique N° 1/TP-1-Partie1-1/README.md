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
    - La performance : 
    - La maintenance :
    - La sécurité :
    - Les versions :

## Inversion de controle
## Injection des dépandances
