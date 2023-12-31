package Courses;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Weights {
    Map<String, Double> evalStrategy;
    Iterator<Entry<String, Double>> entrySet;
    Entry<String, Double> currentEntry;

    public void addToEvalStrategy(String examOrAssignment, Double weight){
        if(evalStrategy == null)
            evalStrategy = new HashMap<String, Double>();
        evalStrategy.put(examOrAssignment, weight);
    }

    public void initializeIterator(){
        entrySet = evalStrategy.entrySet().iterator();
    }

    public boolean hasNext(){
        return entrySet.hasNext();
    }

    public Entry<String, Double> getNextEntry(){
        if(entrySet.hasNext()){
            return entrySet.next();
        }
        else
            return null;
    }

    public Double getValueWithKey(String key){
        return evalStrategy.get(key);
    }

    public void next(){
        if(entrySet.hasNext()){
            currentEntry = entrySet.next();
        }
    }

    public String getCurrentKey(){
        return currentEntry.getKey();
    }

    public Double getCurrentValue(){
        return currentEntry.getValue();
    }

}
