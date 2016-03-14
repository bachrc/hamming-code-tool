# Le code de Hamming

## Rappel du sujet

Ce programme a été réalisé dans le cadre d'un TP de Réseau pour les première année de Master SIRES, encadré par M. Duvallet, à l'université du Havre.

Il s'agit d'implanter un programme JAVA permettant de calculer les codes de Hamming.

1. Ecrire une méthode qui, à partir d'un mot donné sous forme d'une chaine de caractères, donnera lorsque c'est possible le code à lui adjoindre et le mot de Hamming résultant de cette adjonction.
2. Ecrire une méthode qui, à partir d'un mot de Hamming toujours donné sous forme d'une chaine de caractères, vérifiera si ce mot contient ou ne contient pas d'erreurs. Cette méthode devra permettre de présenter les étapes ayant abouti au résultat.
3. Ecrire un programme principal qui permettra à un utilisateur de saisir des suites de bits puis d'effectuer des vérifications sur des codes de Hamming ou de calculer les mots de Hamming correspondant à la chaîne saisie.

## Téléchargement du programme

Vous pouvez compiler le programme à partir des sources en téléchargeant les sources, et en utilisant votre compilateur.

Cependant, si vous voulez tester le programme immédiatement, vous pouvez télécharger le fichier .jar et double cliquer dessus.

La liste des releases est disponible en cliquant sur le lien suivant : 

[Téléchargement sources et jars](https://github.com/totorolepacha/hamming-code-tool/releases)

## Sommaire

<!-- MarkdownTOC -->

- Le code de Hamming
    - Conception d'un mot de Hamming
    - Vérification d'un mot de Hamming
- Le programme
    - L'interface
    - Génération d'un mot
    - Vérification d'un mot

<!-- /MarkdownTOC -->

## Le code de Hamming

Lors de la transmission de signaux, des erreurs peuvent survenir. Divers facteurs peuvent intervenir, tel de mauvais câbles, et une probabilité bien présente sur des lignes téléphoniques d'une erreur de transmission. C'est afin de prévoir ces erreurs de transmission de données que des moyens ont été mis en place, tel le code de Hamming. Ce principe permet de détecter si un bit dans le signal est faux, et si c'est le cas, de pouvoir localiser ce bit.

### Conception d'un mot de Hamming

Nous partons du principe qu'un mot de Hamming doit être de longueur `(2^n) - 1`. Car en effet, un mot de Hamming comporte `n` bits de contrôle. Le mot à encoder devra donc être de longueur `(2^n) - n - 1`.

Les bits de contrôle sont donc placés en position 2^i pour i=0,1,...n

### Vérification d'un mot de Hamming

Afin de vérifier un mot de Hamming, le mot doit déjà être de la bonne longueur. Puis, nous effectuons un contrôle de parité avec les bits de contrôle, et les bits qu'ils contrôlent respectivement.

Pour un mot de longueur `(2^n) - 1`, le bit de contrôle Ci contrôle tous les bits pour lesquels le bit i vaut 1 dans un mot de longueur n. 

Si les bits de contrôle de réception Ci valent tous 0, il n'y a pas d'erreurs, sinon la valeur des bits de contrôle indique la position sur le mot de l'erreur.

## Le programme

### L'interface

L'interface est très simple : elle propose une zone de texte afin que l'on puisse y renseigner le mot à traiter. Ainsi, une fois le mot renseigné, vous disposez de deux boutons, afin d'y faire ce que vous souhaitez. Soit vérifier le mot renseigné, soit générer un mot de Hamming à partir du mot renseigné.

![Interface](https://i.imgur.com/HQoGTg7.png)

### Génération d'un mot

Plusieurs étapes interviennent lors de la création d'un mot de Hamming.

```java
    public static String generateWord(String word) throws Exception {
        // Vérifie la longueur du mot
        int n = 1;
        while ((Math.pow(2, n) - 1 - n) < word.length()) n++;

        // S'il n'est pas à la bonne longueur, on l'étend de zéros afin qu'il fasse la bonne taille.
        if (Math.pow(2, n) - 1 - n != word.length()) 
            word = String.format("%" + (int)(Math.pow(2, n) - 1 - n) + "s", word).replace(' ', '0');
```

Si le mot n'est pas de la taille requise, soit `(2^n) - n - 1`, on l'allonge avec des 0 sur la gauche.

```java
        StringBuilder mot = new StringBuilder(word);
        
        // On ajoute des bits de contrôle nuls aux bons endroits
        for (int c = 0; c < n; c++) {
            int endroit = mot.length() - (int) Math.pow(2, c) + 1;
            mot.insert(endroit, '0');
        }
```
Nous ajoutons dans le mot les bits de contrôle prêts à être modifiés.

```java
        for (int c = 0; c < n; c++) {
            int valeur = 0;
            for (Integer i : bitsControled(c, n)) {
                int endroit = (int) Math.pow(2, n) - i - 1;
                if(mot.charAt(endroit) != '0' && mot.charAt(endroit) != '1')
                    throw new Exception("Caractère invalide à l'indice " + endroit + " : " + mot.charAt(endroit));

                valeur = (valeur + Character.getNumericValue(mot.charAt(endroit))) % 2;
            }

            int endroit = mot.length() - (int) Math.pow(2, c);
            System.out.println(endroit);
            mot.setCharAt(endroit, Character.forDigit(valeur, 10));
        }
```
Puis, conformément aux règles d'un mot de Hamming, nous adaptons la valeur de tous les bits de contrôle insérés.

![Génération du mot](https://i.imgur.com/esnGdaQ.png)

Et bien entendu, ce mot est valide, et réutilisable pour vérification dans le logiciel.

![Vérification du mot généré](https://i.imgur.com/dtaySQD.png)

### Vérification d'un mot

Derrière la vérification du mot, plusieurs étapes entrent en jeu.

```java
public static boolean verifyWord(String word) throws Exception {
        // Vérifie la longueur du mot        
        int n = 1;
        while (Math.pow(2, n) - 1 < word.length()) {
            n++;
        }

        if (Math.pow(2, n) - 1 != word.length()) {
            throw new Exception("Mot de longueur invalide.");
        }
```

Tout d'abord, il faut vérifier si le mot est de longueur `2^n - 1` , et nous récupérons donc la valeur n.

```java
        int[] control = new int[n];

        for (int c = 0; c < n; c++) {
            for (Integer i : bitsControled(c, n)) {
```

Nous définissons un tableau d'entiers de taille n pour y stocker la somme des bits contrôlés par les bits de contrôle. Puis, nous bouclons sur tous les bits de contrôle.

La méthode `bitsControled(int c, int n)` renvoie tous les bits contrôlés par le bit de contrôle c dans un mot possédant n bits de contrôle. 

```java
                int endroit = (int) Math.pow(2, n) - i - 1;
                if (word.charAt(endroit) != '0' && word.charAt(endroit) != '1'){
                    throw new Exception("Caractère invalide à l'indice " + endroit);
                }

                control[c] = (control[c] + Character.getNumericValue(word.charAt(endroit))) % 2;
```
Nous parcourons tous ces bits contrôlés, et nous consignons leur somme dans le tableau précédemment créé à cet effet. Bien sûr, si un caractère ne correspond pas à la base binaire, une exception est renvoyée.

![Erreur](https://i.imgur.com/Siclaew.png)

```java
        // Conversion de l'adresse du bit incriminé.
        int indiceRetour = 0;
        for (int i = 0; i < n; i++) {
            indiceRetour += control[i] * Math.pow(2, i);
        }

        System.out.println(indiceRetour);
        if (indiceRetour != 0) {
            throw new InvalidWordException(word, word.length() - indiceRetour);
        }

        return true;
```

Puis nous convertissons en entier les bits de contrôle. Si la valeur de ces bits de contrôle renvoie 0, c'est qu'aucune erreur n'est à déplorer. Sinon, elle renvoie l'endroit incriminé dans le mot via une exception.

![Vérification](https://i.imgur.com/77dkqLf.png)

![Vérification](https://i.imgur.com/guVN7JE.png)

Si aucune exception n'a été renvoyée durant la méthode, c'est que le mot a été vérifié sans encombre.
