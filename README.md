PAVLETIC Thomas  -  INFO 3 FA

Avant de commencer : 

Ce rapport a été rédigé à l'aide de ChatGPT. Il a été relu et certains points ont été reformulés dans le but d'expliquer certains concepts de la façon dont je les comprends.

# Rapport sur les Concepts de Sémaphore et Moniteur en Programmation Répartie en Java

## Introduction

Dans le cadre des TPs de programmation répartie, nous avons mis en œuvre les principes de synchronisation et de gestion de ressources partagées en Java, principalement en utilisant les concepts de **sémaphore** et de **moniteur**. Ces notions permettent de gérer l'accès concurrentiel de plusieurs threads à des ressources partagées, tout en assurant la cohérence des données et la fluidité des traitements. Ce rapport détaille ces concepts en suivant la progression des TP, en expliquant comment les notions théoriques du cours sont appliquées dans le code des exercices.

---

## 1. Notions Importantes : Définitions et Concepts du Cours

1. **Thread et Multi-Threading** : Un **thread** est un processus léger qui permet l'exécution de plusieurs tâches de manière concurrente au sein d'une application. En Java, les threads peuvent être créés en implémentant l'interface `Runnable` ou en héritant de la classe `Thread`. Le **multi-threading** est utilisé pour exécuter plusieurs threads en parallèle, ce qui permet d'optimiser l’utilisation des ressources.

2. **Sections Critiques et Exclusion Mutuelle** : Une **section critique** est un bloc de code auquel un seul thread peut accéder à la fois, afin d'éviter les conflits dus à l'accès concurrent à une ressource partagée. L'**exclusion mutuelle** est le principe qui assure qu'un seul thread à la fois peut entrer dans une section critique.

3. **Moniteur** : Un **moniteur** est un mécanisme de synchronisation qui utilise un verrou pour contrôler l'accès à une ressource. En Java, les moniteurs sont intégrés et peuvent être utilisés avec le mot-clé `synchronized`.

4. **Sémaphore** : Un **sémaphore** est une structure de contrôle de flux qui limite le nombre de threads pouvant accéder à une ressource critique. Contrairement à un moniteur, un sémaphore peut autoriser plusieurs threads à accéder à la section critique. En Java, il est implémenté par la classe `Semaphore` dans `java.util.concurrent`.

---

## 2. Application dans le TP 1 : Mouvement des Mobiles avec Sémaphore

Le TP 1 consistait à simuler le déplacement de plusieurs « mobiles » dans une fenêtre graphique, en utilisant des threads pour gérer leur mouvement simultané. Un sémaphore est utilisé pour limiter le nombre de mobiles qui peuvent accéder simultanément à la zone centrale de la fenêtre.

### Exemples du Code et Analyse

- **Création et Gestion de Threads** :
  Chaque mobile est géré par un thread indépendant, créé dans la classe `UneFenetre` avec :
  ```java
  laTache = new Thread(sonMobile);
  laTache.start();
  ```
  Ce code permet de démarrer plusieurs threads, chacun contrôlant un mobile.

- **Sémaphore pour Limiter l’Accès** :
  Le sémaphore, déclaré comme `Semaphore leurSemaphore = new Semaphore(2);` dans la classe `UnMobile`, limite à deux le nombre de threads pouvant entrer dans la zone centrale :
  ```java
  while(!leurSemaphore.tryAcquire()){
      try{ Thread.sleep(sonTemps); }
      catch (InterruptedException telleExcp) { telleExcp.printStackTrace(); }
  }
  ```
  **Explication** : Cette portion de code utilise `tryAcquire()` pour tenter d’acquérir le sémaphore. Si la capacité est atteinte, le thread attend jusqu'à ce qu'une place se libère, garantissant que seuls deux mobiles peuvent être dans la section centrale en même temps.

- **Libération du Sémaphore** :
  Lorsque le mobile quitte la zone centrale, le sémaphore est libéré via `leurSemaphore.release();`, permettant à d’autres mobiles d’accéder à la zone.

### Résultat et Justification

Le sémaphore assure une **synchronisation efficace** en limitant le nombre de mobiles dans la zone critique.

---

## 3. Application dans le TP 2 : Modèle Producteur-Consommateur avec Moniteur

Le TP 2 met en œuvre un modèle producteur-consommateur pour gérer une boîte aux lettres (BAL). Deux types de threads — producteurs et consommateurs — interagissent de manière asynchrone avec une ressource partagée (la BAL).

### Exemples du Code et Analyse

- **Synchronisation avec Moniteur** :
  La classe `BAL` utilise les mots-clés `synchronized`, `wait()`, et `notifyAll()` pour gérer l'accès concurrent :
  ```java
  public synchronized void deposer(String nouvelleLettre) throws InterruptedException {
      while (available) { wait(); }
      lettre = nouvelleLettre;
      available = true;
      notifyAll();
  }

  public synchronized String retirer() throws InterruptedException {
      while (!available) { wait(); }
      String lettreRecuperee = lettre;
      available = false;
      notifyAll();
      return lettreRecuperee;
  }
  ```
  **Explication** : Ces méthodes bloquent un thread si la ressource n'est pas prête (par exemple, le consommateur attend jusqu'à ce qu'une lettre soit déposée). `notifyAll()` permet de réveiller les threads en attente lorsque la ressource devient disponible.

- **Exclusion Mutuelle Assurée par le Moniteur** :
  Le mot-clé `synchronized` garantit qu'un seul thread à la fois peut exécuter les méthodes `deposer` ou `retirer`, assurant l'exclusion mutuelle. Le moniteur intégré à l’objet `BAL` contrôle l’accès pour éviter les conflits.

### Résultat et Justification

En utilisant un moniteur, le code assure la **sécurité de l'accès concurrent** : les producteurs et les consommateurs peuvent interagir avec la BAL sans conflits ou incohérence. Par exemple, la même lettre ne peut pas être récupérée par deux consomateurs différents.

---

### 4. Application dans le TP3 : Modèle Producteur-Consommateur avec `ArrayBlockingQueue`

Dans le TP3, le modèle producteur-consommateur est implémenté en utilisant une **`ArrayBlockingQueue`**, une file d’attente bloquante de capacité fixe. Cela simplifie la coordination entre threads en gérant automatiquement les blocages lorsque la file est pleine ou vide.

#### Concepts Appliqués

1. **`ArrayBlockingQueue`** :
  - La méthode `put()` est utilisée par les producteurs pour insérer des lettres dans la queue. Si la file est pleine, le producteur attend automatiquement :
    ```java
    lettres.put(nouvelleLettre); // Bloque si la file est pleine
    System.out.println("Lettre déposée : " + nouvelleLettre);
    ```
  - La méthode `take()` est utilisée par les consommateurs pour retirer des lettres. Si la file est vide, le consommateur attend :
    ```java
    String lettre = lettres.take(); // Bloque si la file est vide
    System.out.println("Lettre retirée : " + lettre);
    ```

2. **Synchronisation Intégrée** :
  - Contrairement à un tampon classique, l’`ArrayBlockingQueue` gère automatiquement les verrous nécessaires pour garantir la sécurité des accès concurrents. Cela élimine la nécessité d’écrire des mécanismes explicites avec `synchronized` ou `wait()`.

3. **Signal de Fin** :
  - Les producteurs insèrent un caractère spécial (`*`) pour indiquer la fin de la production, permettant aux consommateurs de s’arrêter proprement.

### Résultat et Justification

L’utilisation de l’`ArrayBlockingQueue` simplifie la mise en œuvre du modèle producteur-consommateur. Elle garantit une synchronisation efficace tout en évitant les erreurs courantes liées à la gestion manuelle des tampons et des verrous. Ce choix rend le code plus lisible, fiable et adapté aux environnements multi-threadés.

---

## Conclusion

Ces cours ont permis de comprendre l'importance des concepts de **sémaphore** et de **moniteur** en gestion concurrente :
- Dans le TP 1, le **sémaphore** est utilisé pour limiter le nombre de threads accédant à une section spécifique de la ressource.
- Dans le TP 2, le **moniteur** assure une **synchronisation stricte** et l'exclusion mutuelle pour les opérations de dépôt et de retrait.
- Dans le TP 3, le tampon étend ces concepts à un contexte plus avancé avec un modèle producteur-consommateur multi-ressources.

Ces exercices ont démontré comment la synchronisation et la gestion des ressources partagées peuvent être efficacement appliquées en Java, garantissant la fluidité et la sécurité dans des environnements multi-threadés complexes.