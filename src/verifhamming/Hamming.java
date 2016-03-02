/**
 *
 * @author Yohann Bacha <y.bacha@live.fr>
 */
package verifhamming;

import java.util.ArrayList;

public class Hamming {
	public static boolean verifyWord(String word) throws Exception {
		// Vérifie la longueur du mot        
        int n = 1;
		while(Math.pow(2, n) - 1 < word.length()) n++;
		
		if(Math.pow(2, n) - 1 != word.length())
			throw new Exception("Mot de longueur invalide.");
		
        // Définition de la portée des bits de controle
        
        for(int c = 0; c < n; c++) {
            System.out.println("Pour le bit de controle c" + c + " : \n" + bitsControled(c, n));
        }
        
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
