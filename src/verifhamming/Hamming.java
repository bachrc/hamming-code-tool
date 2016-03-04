/**
 *
 * @author Yohann Bacha <y.bacha@live.fr>
 */
package verifhamming;

import java.util.ArrayList;
import java.util.Arrays;

public class Hamming {
    
    /**
     * Méthode vérifiant si un mot de Hamming est valide ou non
     * @param word Le mot de Hamming à vérifier
     * @return Retourne true si aucune exception n'a été levée, donc que le mot est bon.
     * @throws InvalidWordException Retournée si une erreur est détectée dans le mot. 
     * Y est consigné le mot, ainsi que l'indice de l'erreur
     * @throws Exception Renvoyée en cas de caractère non binaire.
     */
	public static boolean verifyWord(String word) throws Exception {
		// Vérifie la longueur du mot        
        int n = 1;
		while(Math.pow(2, n) - 1 < word.length()) n++;
		
		if(Math.pow(2, n) - 1 != word.length())
			throw new Exception("Mot de longueur invalide.");
		
        // Définition de la portée des bits de controle
        int[] control = new int[n];
        
        for(int c = 0; c < n; c++) 
            for (Integer i : bitsControled(c, n)) {
                int endroit = (int)Math.pow(2, n) - i - 1;
                if(word.charAt(endroit) != '0' && word.charAt(endroit) != '1')
                    throw new Exception("Caractère invalide à l'indice " + endroit);
                
                control[c] = (control[c] + Character.getNumericValue(word.charAt(endroit))) % 2;  
            }
        // Debug
        System.out.println(Arrays.toString(control));
        
        // Conversion de l'adresse du bit incriminé.
        int indiceRetour = 0;
        for(int i = 0; i < n; i++) 
            indiceRetour += control[i] * Math.pow(2, i);
        
        System.out.println(indiceRetour);
        if(indiceRetour != 0)
            throw new InvalidWordException(word, word.length() - indiceRetour);
        
		return true;
	}
    
    /**
     * Méthode renvoyant les bits controlés par un bit de contrôle c dans un mot de longueur 2^n - 1
     * @param c Le numéro du bit de controle
     * @param n Indique la longueur du mot 2^n - 1
     * @return Retourne tous les bits contrôlés par le bit de contrôle c
     */
    private static ArrayList<Integer> bitsControled(int c, int n) {
        ArrayList<Integer> retour = new ArrayList<>();
        
        // On parcourt tous les nombres à n bits.
        for(int i = 0; i < Math.pow(2, n); i++) {
            // On change l'entier actuelle en chaîne binaire
            String temp = String.format("%" + n +"s", Integer.toBinaryString(i)).replace(' ', '0');
            int endroit = n - c - 1;
            // Si le bit c est de 1, on ajoute le nombre parcouru dans les bits controlés à retourner
            if(temp.charAt(endroit ) == '1')
                retour.add(i);
        }
        
        return retour;
    }
}
