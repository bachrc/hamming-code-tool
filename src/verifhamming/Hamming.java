/**
 *
 * @author Yohann Bacha <y.bacha@live.fr>
 */
package verifhamming;

import java.util.ArrayList;
import java.util.Arrays;

public class Hamming {
	public static boolean verifyWord(String word) throws Exception {
		// Vérifie la longueur du mot        
        int n = 1;
		while(Math.pow(2, n) - 1 < word.length()) n++;
		
		if(Math.pow(2, n) - 1 != word.length())
			throw new Exception("Mot de longueur invalide.");
		
        // Définition de la portée des bits de controle
        int[] control = new int[n];
        
        for(int c = 0; c < n; c++) 
            for (Integer i : bitsControled(c, n)) 
                try {
                    int endroit = (int)Math.pow(2, n) - i - 1;
                    control[c] = (control[c] + Character.getNumericValue(word.charAt(endroit))) % 2;  
                } catch(Exception e) {
                    e.printStackTrace();
                    throw new Exception("Caractère invalide à l'indice " + i);
                }
        
        System.out.println(Arrays.toString(control));
        int indiceRetour = 0;
        for(int i = 0; i < n; i++) 
            indiceRetour += control[i] * Math.pow(2, i);
        
        if(indiceRetour != 0)
            throw new InvalidWordException(word, indiceRetour);
        
		return true;
	}
    
    private static ArrayList<Integer> bitsControled(int c, int n) {
        ArrayList<Integer> retour = new ArrayList<>();
        for(int i = 0; i < Math.pow(2, n); i++) {
            String temp = String.format("%" + n +"s", Integer.toBinaryString(i)).replace(' ', '0');
            int endroit = n - c - 1;
            if(temp.charAt(endroit ) == '1')
                retour.add(i);
        }
        
        
        return retour;
    }
}
