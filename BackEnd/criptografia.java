/**
 *
 * @author arthux
 */
public class criptografia {

  

    /**
     * @param args the command line arguments
     */
    
  
    
    
  public static void criptografar(String texto, int num){ // vai receber a mesagem
         int chave = num;
         StringBuilder resultado = new StringBuilder();
         
         for(char c: texto.toCharArray()){
             if(Character.isLetter(c)){
                 char old = Character.isLowerCase(c) ? 'a' : 'A';
                 char novo = (char) ((c- old - chave + 26) % 26 + old);
                 resultado.append(novo);
             } else{
                 resultado.append(c);
             }
         }
  
  resultado.toString();  
  }
}

public static void descriptografar(String texto, int num){
    int chave = num;
    
}  
