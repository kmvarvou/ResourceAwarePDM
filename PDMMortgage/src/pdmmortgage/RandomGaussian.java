/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdmmortgage;

import static java.lang.Math.abs;
import java.util.Random;

/** 
 Generate pseudo-random floating point values, with an 
 approximately Gaussian (normal) distribution.

 Many physical measurements have an approximately Gaussian 
 distribution; this provides a way of simulating such values. 
*/
public final class RandomGaussian {

  

  private  Random fRandom = new Random();

  public  double getRandProb(double MEAN){
      //double MEAN = 0.08; 
      double VARIANCE = 0.0216;//0.0216
      double result = 1 - getGaussian(MEAN, VARIANCE);
      if(result<0)
      {
          result = 1 - getGaussian(MEAN, VARIANCE);
         
      }
      return result;
  }
  
  public int getRandInt(double MEAN){ //original 2.5
      
      double VARIANCE=1.5625; //original 8.25 //1.5625
      int result = (int)getGaussian(MEAN, VARIANCE);
      return result;
  }
  
  public int getRandInt2(double MEAN){ //original 2.3
      
      double VARIANCE=1.3225; //8.21 //1.3225
      int result = (int)getGaussian(MEAN, VARIANCE);
      return result;
  }
  
  private double getGaussian(double aMean, double aVariance){
    return abs(aMean + fRandom.nextGaussian() * aVariance);
  }

  private static void log(Object aMsg){
    System.out.println(String.valueOf(aMsg));
  }
} 