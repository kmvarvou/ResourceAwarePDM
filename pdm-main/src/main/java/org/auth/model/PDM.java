package org.auth.model;

import java.util.ArrayList;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class PDM {
    private String _name;
    private LinkedHashSet<DataElement> _dataElements;
    private LinkedHashSet<Operation> _operations;
    private LinkedHashSet<Operation> _graphOperations;
    private HashMap<DataElement, LinkedHashSet<Operation>> _inputIndex;
    private HashMap<DataElement, LinkedHashSet<Operation>> _outputIndex;
    private DataElement _objective;
    private DataElement _source;
    
    private List<Resource> _resources;

    public PDM(String name, List<DataElement> dataElements, List<Operation> operations, DataElement objective, List<Resource> resources) {
        _name = name;
        _dataElements = new LinkedHashSet<>(dataElements);
        _operations = new LinkedHashSet<>(operations);
        _graphOperations = new LinkedHashSet<>(operations);
        _source = new DataElement(0, "source");
        _objective = objective;
        
        _resources = new ArrayList<>();
        for(Resource r : resources )
        {
            _resources.add(r);
        }
        buildInputIndex();
        buildOutputIndex();
    }

    public PDM(PDM other, boolean randomize) {
        _name = other._name;
        _dataElements = new LinkedHashSet<>();
        for (DataElement el : other._dataElements) {
            _dataElements.add(new DataElement(el));
        }
        _operations = new LinkedHashSet<>();
        for (Operation op : other._operations) {
            Operation newOp = new Operation(op);
            if (randomize) {
                newOp.randomize();
            }
            _operations.add(newOp);
        }
        _graphOperations = new LinkedHashSet<>();
        for (Operation op : _operations) {
            _graphOperations.add(new Operation(op));
        }
        _source = new DataElement(other._source);
        _objective = new DataElement(other._objective);
        _resources = other._resources;
        
        buildInputIndex();
        buildOutputIndex();
    }

    private void buildInputIndex() {
        _inputIndex = new HashMap<>();
        for (Operation op : _operations) {
            for (DataElement el : op.input()) {
                if (!_inputIndex.containsKey(el)) {
                    _inputIndex.put(el, new LinkedHashSet<>());
                }
                _inputIndex.get(el).add(op);
            }
        }
    }

    private void buildOutputIndex() {
        _outputIndex = new HashMap<>();
        for (Operation op : _operations) {
            if (!_outputIndex.containsKey(op.output())) {
                _outputIndex.put(op.output(), new LinkedHashSet<>());
            }
            _outputIndex.get(op.output()).add(op);
        }
    }

    public String name() {
        return _name;
    }

    public LinkedHashSet<Operation> operations() {
        return _operations;
    }

    public LinkedHashSet<DataElement> dataElements() {
        return _dataElements;
    }

    public DataElement target() {
        return _objective;
    }

    public DataElement source() {
        return _source;
    }

    public HashMap<DataElement, LinkedHashSet<Operation>> inputIndex() {
        return _inputIndex;
    }

    public HashMap<DataElement, LinkedHashSet<Operation>> outputIndex() {
        return _outputIndex;
    }
    
    public Operation getLeafOperation(DataElement output)
    {
        for(Operation op : _operations)
        {
            if(op.output().equals(output))
            {
                if(!op.hasInput())
                {
                    return op;
                }
            }
        }
        
        return null;
    }

    public Graph<DataElement, DefaultWeightedEdge> graph(EdgeWeight edgeWeight) {
        Graph<DataElement, DefaultWeightedEdge> graph = new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);

        graph.addVertex(_source);

        for (DataElement element : dataElements()) {
            graph.addVertex(element);
        }

        // direction is from target element to source
        for (Operation op : _graphOperations) {
            if (op.hasInput()) {
                for (DataElement element : op.input()) {
                    DefaultWeightedEdge e = graph.addEdge(op.output(), element);
                    graph.setEdgeWeight(e, edgeWeight.get(op));
                }
            } else {
                DefaultWeightedEdge e = graph.addEdge(op.output(), _source);
                graph.setEdgeWeight(e, edgeWeight.get(op));
            }
        }

        return graph;
    }
    
    
    public Graph<DataElement, DefaultWeightedEdge> graph_prune(EdgeWeight edgeWeight) {
        Graph<DataElement, DefaultWeightedEdge> graph = new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);

        graph.addVertex(_source);

        for (DataElement element : dataElements()) {
            graph.addVertex(element);
        }

        // direction is from target element to source
        for (Operation op : _graphOperations) {
            if (op.hasInput()) {
                for (DataElement element : op.input()) {
                    DefaultWeightedEdge e = graph.addEdge(op.output(), element);
                    graph.setEdgeWeight(e, edgeWeight.get(op));
                }
            } else {
                DefaultWeightedEdge e = graph.addEdge(op.output(), _source);
                graph.setEdgeWeight(e, edgeWeight.get(op));
            }
        }

        return graph;
    }

    public String summary() {
        return "{\n" +
                "name: " + _name + "\n" +
                "data elements: " + String.join(", ", _dataElements.toString()) + "\n" +
                "objective: " + _objective.toString() + "\n" +
                "number of operations: " + _operations.size() + "\n" +
                "}";
    }

    public String toString() {
        return "{\n" +
                "name: " + _name + "\n" +
                "data elements: " + String.join(", ", _dataElements.toString()) + "\n" +
                "objective: " + _objective.toString() + "\n" +
                "operations: " + String.join("", _operations.toString()) + "\n" +
                "}";
    }
    
    private static void prune2(LinkedHashSet<Operation> operations, Operation operation,double[][] dist, double[] dist2)
    {
        
        
        
        
        LinkedHashSet<DataElement> candidates = new LinkedHashSet<>();
        LinkedHashSet<DataElement> prune2 = new LinkedHashSet<>();
        
        for(Operation op : operations)
        {
            if(op.input().contains(operation.output()))
            {
                for(DataElement cand : op.input())
                {
                    if(!cand.equals(operation.output()))
                    {
                        candidates.add(cand);
                    }
                }
            }
        }
        
        int sum_cand=0;
        int sum_out=0;
        for(DataElement cand : candidates)
        {
          
          sum_cand=0;
          sum_out=0;
          for(Operation op : operations)
          {
              if(op.input().contains(cand) && op.input().contains(operation.output()))
              {
                  sum_out+=1;
              }
              if(op.input().contains(cand) && !op.input().contains(operation.output()))
              {
                  sum_cand+=1;
              }
          }
          
          if(sum_out==sum_cand)
          {
              prune2.add(cand);
              
              int two = cand.getId();
              
              if(dist[0][operation.getId()]!=0) // na to do poio value antiprosoeuei tin ellipsi edge
              {
              dist2[two]+=dist[0][operation.getId()];
              }
              else
              {
              dist2[two]+=operation.getCost();
              }
              
              
          }
        }
        if(prune2.isEmpty())
        {
            
        }
        
        
       
    }
    
    
    public boolean getResourceCapacity(Double time)
    {
        int count = _resources.size();
        for(Resource resource : _resources)
        {
            HashMap<Integer,ArrayList<String>> assignments = resource.getAssignments();
            Integer capacity = resource.getCapacity();
            Iterator it = assignments.entrySet().iterator();
            while(it.hasNext())
            {
                Map.Entry<Integer,ArrayList<String>> assignments_entry = (Map.Entry<Integer,ArrayList<String>>) it.next();
                ArrayList<String> timeframes = assignments_entry.getValue();
                if(timeframes.size()>1)
                {
                    
                }
                for(String timeframe : timeframes)
                {
                
                String[] timesplit = timeframe.split("-");
                Double starting_time = Double.parseDouble(timesplit[0]);
                Double ending_time = Double.parseDouble(timesplit[1]);
                
                if(starting_time<=time && ending_time>time)
                {
                    capacity -= 1;
                }
                else if(starting_time>time && ending_time>time)
                {
                    capacity -= 1;
                }
                else if(ending_time>time)
                {
                    capacity -=1;
                }
            }
            }
            if(capacity<=0)
            {
                count -= 1;
                 
            }
            
            else
            {
               
            }
            
            
        }
        
        
        if(count>0)
        {
            
            return true;
        }
        else
        {
            
            return false;
        }
            
    }
    
    
    public boolean getResourceCapacity(Double time1, Double time2)
    {
        int count = _resources.size();
        for(Resource resource : _resources)
        {
            HashMap<Integer,ArrayList<String>> assignments = resource.getAssignments();
            Integer capacity = resource.getCapacity();
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
                if(starting_time<=time1 && ending_time>time1)
                {
                    capacity -= 1;
                }
                else if(starting_time>time1 && ending_time>time1)
                {
                    capacity -= 1;
                }
                else if(starting_time<time2)
                {
                    capacity -=1;
                }
                }
            }
            if(capacity==0)
            {
                count -= 1;
            }
            
            else
            {
               
            }
            
            
        }
        
        
        if(count>0)
        {
            
            return true;
        }
        else
        {
            
            return false;
        }
            
    }
    
    
    public HashSet<Resource> getAvailableResources(Double time)
    {
        HashSet<Resource> output = new HashSet<>();
        int count = _resources.size();
        for(Resource resource : _resources)
        {
            HashMap<Integer,ArrayList<String>> assignments = resource.getAssignments();
            Integer capacity = resource.getCapacity();
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
                if(time==0)
                {
                
                if(starting_time<=time && ending_time>=time)
                {
                    capacity -= 1;
                }
                }
                else  
                {  
               if( ending_time>time)
                {
                 
                   
                    capacity -= 1;
                
                }
                }
                }
                
               
                
            }
            if(capacity<=0)
            {
                count -= 1;
            }
            else
            {
                output.add(resource);
            }
            
            
        }
        
        
        return output;
            
    }
    
    public Double getNextTimeOld(Double time, Boolean pass)
    {
        
        ArrayList<Double> candidate = new ArrayList<>();
        int i=0;
        
        
        for(Resource resource : _resources)
        {
            HashMap<Integer,ArrayList<String>> assignments = resource.getAssignments();
            Integer capacity = resource.getCapacity();
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
                
                
               if(pass)
               {
                if (ending_time >= time)
                {
                   
                    candidate.add(ending_time);
                    
                }
               }
               else
               {
                 if (ending_time > time)
                {
                    
                    candidate.add(ending_time);
                    
                }  
               }
                i++;
               }
            }
            
            
            
        }
        
        if(candidate.isEmpty())
        {
            return time;
        }
        
        Double result = getMinValue(candidate);
        
        return result;
            
    }
    
    public Double getNextTime(Double time, Boolean pass)
    {
        
        ArrayList<Double> candidate = new ArrayList<>();
        int i=0;
        if(this.getResourceCapacity(time))
        {
        
        for(Resource resource : _resources)
        {
            HashMap<Integer,ArrayList<String>> assignments = resource.getAssignments();
            Integer capacity = resource.getCapacity();
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
                
                
               if(pass)
               {
                if (ending_time >= time)
                {
                    
                    candidate.add(ending_time);
                    
                }
               }
               else
               {
                 if (ending_time > time)
                {
                    
                    candidate.add(ending_time);
                    
                }  
               }
                i++;
               }
            }
            
            
            
        }
        
        if(candidate.isEmpty())
        {
            return time;
        }
        
        Double result = getMinValue(candidate);
        
        return result;
        }
        else
        {
            for(Resource resource : _resources)
            {
            HashMap<Integer,ArrayList<String>> assignments = resource.getAssignments();
            Integer capacity = resource.getCapacity();
            Iterator it = assignments.entrySet().iterator();
            Double max = 0.0;
            while(it.hasNext())
            {
                Map.Entry<Integer,ArrayList<String>> assignments_entry = (Map.Entry<Integer,ArrayList<String>>) it.next();
                ArrayList<String> timeframes = assignments_entry.getValue();
                
                for(String timeframe : timeframes)
                {
                String[] timesplit = timeframe.split("-");
                Double starting_time = Double.parseDouble(timesplit[0]);
                Double ending_time = Double.parseDouble(timesplit[1]);
                
                
                if(ending_time>max)
                {
                    max = ending_time;
                }
                //i++;
               }
            }
            candidate.add(max);
            
            
        }
           return getMinValue(candidate); 
        }
            
    }
    
    public Operation getOperation(DataElement d)
    {
        Iterator it = _operations.iterator();
        for(Operation op : _operations)
        {
            if(op.output().getName().equals(d.getName()))
            {
                return op;
            }
        }
        return null;
    }
    
    
    public Double getMaxTime()
    {
        
        Double max = 0.0;
        
        int i=0;
        
        
        for(Resource resource : _resources)
        {
            HashMap<Integer,ArrayList<String>> assignments = resource.getAssignments();
            Integer capacity = resource.getCapacity();
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
                if (ending_time > max)
                {
                    max = ending_time;
                    
                }
                i++;
                }
            }
            
            
            
        }
        
        
        return max;
            
    }
    
    public Double getTime()
    {
        
        Double sum = 0.0;
        
        int i=0;
        
        
        for(Resource resource : _resources)
        {
            HashMap<Integer,ArrayList<String>> assignments = resource.getAssignments();
            Integer capacity = resource.getCapacity();
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
                sum += ending_time - starting_time;
                }
            }
            
            
            
        }
        
        
        return sum;
            
    }
            
    
    
    
    public static Double getMinValue(ArrayList<Double> array) {
    Double minValue = array.get(0);
    for (int i = 1; i < array.size(); i++) {
        if (array.get(i) < minValue) {
            minValue = array.get(i);
        }
    }
    return minValue;
}
    
    public static Double getMaxValue(ArrayList<Double> array) {
    Double maxValue = array.get(0);
    for (int i = 1; i < array.size(); i++) {
        if (array.get(i) > maxValue) {
            maxValue = array.get(i);
        }
    }
    return maxValue;
}
    
    
  public LinkedHashSet<Operation> executableFilter(LinkedHashSet<Operation> executable, Double time)
  {
      
      HashSet<Resource> available_resources = this.getAvailableResources(time);
      for(Resource r : available_resources)
      {
          
      }
      LinkedHashSet<Operation> executable_filtered = new LinkedHashSet<>();
      for(Operation op : executable)
      {
          
         
          for(Resource r : op.getResources())
          {
              
              
              if(available_resources.contains(r))
              {
              
               executable_filtered.add(op);
              }
          }
          
          
      }
      
      return executable_filtered;
  }
  
  public void resetResources()
  {
      for(Resource r : _resources)
      {
          r.clear();
          
      }
  }
  
  public Resource defaultResource()
  {
      return _resources.get(0);
  }
  
  
  public LinkedHashSet<Operation> getProductionOperations(DataElement output)
  {
      LinkedHashSet<Operation> result = new LinkedHashSet<Operation>();
      for(Operation op : _operations)
      {
          if(op.output().equals(output))
          {
             result.add(op);
          }
      }
      
      return result;
  }
    
}
