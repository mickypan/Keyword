import java.util.*;
import java.io.*;

public class DocumentFrequency {
  
  public static void main(String[] args) {
    String dir = args[0]; // name of directory with input files
    HashMap<String, Integer> dfs;
    dfs = extractDocumentFrequencies(dir, 40);
    writeDocumentFrequencies(dfs, "freqs.txt");
  }
  
  public static HashMap<String, Integer> extractDocumentFrequencies(String directory, int nDocs) {
    HashMap<String, Integer> map = new HashMap<String, Integer>();
    
    for(int i = 1; i <= nDocs; i++){
      String filename = directory + "/" + i +".txt";
      HashSet<String> set = extractWordsFromDocument(filename);
      
      for(String k : set){
        
        if(map.containsKey(k)){
         int x = map.get(k);
         x = x + 1;
         map.put(k,x);
        }else{
          map.put(k,1); 
        }//end if else
      }//end inner for
    }//end outer for
    
    
    return map;
    
  }//end method0
  
  
  public static HashSet<String> extractWordsFromDocument(String filename) {
    
    HashSet<String> set = new HashSet<String>();
    
    try{
       FileReader fr = new FileReader(filename);
       BufferedReader br = new BufferedReader(fr);
       
       for(String line = br.readLine(); line != null; line = br.readLine()){
         String[] words = line.split(" "); 
         
         for (int i = 0; i<words.length; i++) {
           set.add(normalize(words[i]));
         }
       }
    
       br.close();
       fr.close();
       
    }catch(IOException e){
       System.out.println("Could not open file");     
    }
    
    return set;
    
  }
  
  
  public static void writeDocumentFrequencies(HashMap<String, Integer> dfs, String filename) {
    try{
      FileWriter fw = new FileWriter(filename);
      BufferedWriter bw = new BufferedWriter(fw);
      
      ArrayList<String> list = new ArrayList<String>();
      
      for(String k : dfs.keySet()){
        list.add(k);
      }
      
      Collections.sort(list);
      
      for(int i = 0; i < list.size(); i++){
       String str = list.get(i); 
       int num = dfs.get(str);
       
       bw.write(str + " " + num);
       bw.newLine();
       
      }
      
      
      
      bw.close();
      fw.close();
      
    }catch(IOException e){}
  }
  
  /*
   * This method "normalizes" a word, stripping extra whitespace and punctuation.
   * Do not modify.
   */
  public static String normalize(String word) {
    return word.replaceAll("[^a-zA-Z ']", "").toLowerCase();
  }
  
}