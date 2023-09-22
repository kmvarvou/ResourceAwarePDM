/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.auth.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static org.auth.model.PDM.getMinValue;

/**
 *
 * @author kostis
 */
public class Resource {
    
    private int _id;
    private String _name;
    private int _capacity;
    private HashMap<Integer,ArrayList<String>> _assignments; 
    private HashSet<Operation> _responsibility;
    
    
    
    public Resource(int id, String name, int capacity) {
        _id = id;
        _name = name;
        _capacity = capacity;
        _assignments = new HashMap<>();
        _responsibility = new HashSet<>();
    }
    
    public Resource(int id, String name) {
      _id = id; 
      _name = name;
      _assignments = new HashMap<>();
      _responsibility = new HashSet<>();
    }
    
    public String getName() {
        return _name;
    }
    
    public int getCapacity() {
        return _capacity;
    }
    
    public HashMap<Integer,ArrayList<String>> getAssignments(){
        return _assignments;
    }
    
    
    public void assignResource(Integer instance, Double start, Double ending) {
        
        String time = start.toString();
        time = time.concat("-");
        time = time.concat(ending.toString());
        if(_assignments.containsKey(_id))
        {
            ArrayList<String> temp = _assignments.get(_id);
            temp.add(time);
            _assignments.put(_id,temp);  
        }
        else
        {
          ArrayList<String> temp = new ArrayList<>();
            temp.add(time);
            _assignments.put(_id,temp);  
        }
       
    }
    
    public void setAssignments( HashMap<Integer,ArrayList<String>> assignments)
    {
        _assignments = assignments;
    }
    
    public void addResponsibility(Operation op)
    {
        _responsibility.add(op);
    }
    
    public HashSet<Operation> getResponsibility()
    {
        return _responsibility;
    }
    
    public void clear()
    {
        Iterator it = _assignments.entrySet().iterator();
        while(it.hasNext())
        {
            Map.Entry<Integer,String> whatever = (Map.Entry<Integer,String>) it.next();
            _assignments.remove(whatever.getKey()); 
        }
    }
    
    public int getLiveCapacity(Double current_time)
    {
            HashMap<Integer,ArrayList<String>> assignments = _assignments;
            Integer capacity = _capacity;
            Iterator it = assignments.entrySet().iterator();
            if(_assignments.isEmpty())
            {
                return capacity;
            }
            while(it.hasNext())
            {
                Map.Entry<Integer,ArrayList<String>> assignments_entry = (Map.Entry<Integer,ArrayList<String>>) it.next();
                ArrayList<String> timeframes = assignments_entry.getValue();
                for(String timeframe : timeframes)
                {
                String[] timesplit = timeframe.split("-");
                Double starting_time = Double.parseDouble(timesplit[0]);
                Double ending_time = Double.parseDouble(timesplit[1]);
                if(current_time==0)
                {
                
                if(starting_time<=current_time && ending_time>=current_time)
                {
                    capacity -= 1;
                }
                }
                else if(starting_time<=current_time && ending_time>current_time)
                {
                  
                
                    capacity -= 1;
                
                }  
                else if(starting_time> current_time)
                {
                    capacity -= 1;
                }
                
            }
            }
            
            return capacity;
        
        
    }
    
    public int getLiveCapacity(Double current_time, Double time2)
    {
            HashMap<Integer,ArrayList<String>> assignments = _assignments;
            Integer capacity = _capacity;
            Iterator it = assignments.entrySet().iterator();
            while(it.hasNext())
            {
                Map.Entry<Integer,ArrayList<String>> assignments_entry = (Map.Entry<Integer,ArrayList<String>>) it.next();
                ArrayList<String> timeframes = assignments_entry.getValue();
                for(String timeframe: timeframes)
                {
                
                String[] timesplit = timeframe.split("-");
                Double starting_time = Double.parseDouble(timesplit[0]);
                Double ending_time = Double.parseDouble(timesplit[1]);
                if(current_time==0)
                {
                
                if(starting_time<=current_time && ending_time>=current_time)
                {
                    capacity -= 1;
                }
                }
                else  if(starting_time<=current_time && ending_time>current_time)
                {
                 
                
                    capacity -= 1;
                
                }  
                else if(starting_time < time2)
                {
                    capacity -=1;
                }
            }
                
            }
            
            return capacity;
        
        
    }
    
    public Double getNextTime(Double time)
    {
        
        ArrayList<Double> candidate = new ArrayList<>();
        int i=0;
        
        
        
            HashMap<Integer,ArrayList<String>> assignments = this.getAssignments();
            Integer capacity = this.getCapacity();
            Iterator it = assignments.entrySet().iterator();
            while(it.hasNext())
            {
                Map.Entry<Integer,ArrayList<String>> assignments_entry = (Map.Entry<Integer,ArrayList<String>>) it.next();
                ArrayList<String> timeframes = assignments_entry.getValue();
                for(String timeframe : timeframes)
                {
                
                String[] timesplit = timeframe.split("-");
                Double starting_time = Double.parseDouble(timesplit[0]);
                Double ending_time = Double.parseDouble(timesplit[1]);
                //System.out.println("dasasa" + ending_time + " , " + time);
                
               
               
                 if (ending_time > time)
                {
                    
                    if(this.getLiveCapacity(ending_time)>0)
                    {
                    candidate.add(ending_time);
                    }
                    
                }  
               
                i++;
               }
            }
            
            
            
        
        
        if(candidate.isEmpty())
        {
            return time;
        }
        
        Double result = getMinValue(candidate);
        
        return result;
            
    }
}
