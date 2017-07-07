import java.util.*;
import java.io.*;

public class KeywordExtractor {
  public static void main(String[] args) {
    String dir = args[0]; // name of directory with input files
    HashMap<String, Integer> dfs;
    dfs = readDocumentFrequencies("freqs.txt");
    
    for(int i = 1; i <= 40; i++){
     String filename = dir + "/" + i + ".txt";

     
     HashMap<String,Integer> tfs = computeTermFrequencies(filename);
     HashMap<String, Double>tfidfs = computeTFIDF(tfs,dfs,40);
     
     System.out.println(i + ".txt");
     printTopKeywords(tfidfs,5);
     System.out.println();
     
     
    }
    
    
  }
  
  public static HashMap<String, Integer> computeTermFrequencies(String filename) {
    HashMap<String, Integer> map = new HashMap<String,Integer>();
    
    try{
      FileReader fr = new FileReader(filename);
      BufferedReader br = new BufferedReader(fr);
      
      for(String line = br.readLine(); line != null; line = br.readLine()){
        line = DocumentFrequency.normalize(line);
        String[] words = line.split(" ");
        
        for(int i = 0; i<words.length; i++){
          String q = words[i];
          if(map.containsKey(q)){
           int x = map.get(q);
           x+=1;
           map.put(q,x);
          }else{
           map.put(q,1); 
          }
        }//end inner for
      }//end outer for
    }catch(IOException e){}
    
    return map;
  }
  
  public static HashMap<String, Integer> readDocumentFrequencies(String filename) {
    HashMap<String, Integer> map = new HashMap<String, Integer>();
    
    try{
      FileReader fr = new FileReader(filename);
      BufferedReader br = new BufferedReader(fr);
      for(String line = br.readLine(); line != null ; line = br.readLine() ) {
        String[] a = line.split(" ");
        
        String word = a[0];
        String number = a[1];
        int num = Integer.parseInt(number);
        
        map.put(word,num);
        
      }
      
      
    }catch(IOException e){}
    
    return map;
  }
  
  public static HashMap<String, Double> computeTFIDF(HashMap<String, Integer> tfs, HashMap<String, Integer> dfs, 
                                                     double nDocs) {
    HashMap<String,Double> map = new HashMap<String,Double>();
    for(String w : tfs.keySet()){
      double idf = Math.log(nDocs / dfs.get(w));
      double tf = tfs.get(w);
      double tfidf = tf * idf;
      
      map.put(w,tfidf);
    }
    
    return map;
  }
  
  /**
   * This method prints the top K keywords by TF-IDF in descending order.
   */
  public static void printTopKeywords(HashMap<String, Double> tfidfs, int k) {
    ValueComparator vc =  new ValueComparator(tfidfs);
    TreeMap<String, Double> sortedMap = new TreeMap<String, Double>(vc);
    sortedMap.putAll(tfidfs);
    
    int i = 0;
    for(Map.Entry<String, Double> entry: sortedMap.entrySet()) {
      String key = entry.getKey();
      Double value = entry.getValue();
      
      System.out.println(key + " " + value);
      i++;
      if (i >= k) {
        break;
      }
    }
  }  
}

/*
 * This class makes printTopKeywords work. Do not modify.
 */
class ValueComparator implements Comparator<String> {
    
    Map<String, Double> map;
    
    public ValueComparator(Map<String, Double> base) {
      this.map = base;
    }
    
    public int compare(String a, String b) {
      if (map.get(a) >= map.get(b)) {
        return -1;
      } else {
        return 1;
      } // returning 0 would merge keys 
    }
  }