/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdmsocialinsurance;

/**
 *
 * @author kostis
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;



/**
 *
 * @author Κωστής
 */
public class PDMSocialInsurance{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        int y=0;
        
        int b=10;
        while(b !=0 && b != 1)
        {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please choose type of PDM implementation.");
        System.out.println("Enter 0 for baseline (original) implementation or 1 for parallelized (extended). ");
        try{
        b = scan.nextInt();
        } catch(InputMismatchException e){
            System.out.println("Unsupported input, please enter either 0 or 1.");
        }
        }
        
        if(b==1)
        {
        
        int s=10;
        int execution_mode=10;
        while(s !=0 && s != 1)
        {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please choose distribution for generation of the operations' attributes.");
        System.out.println("Enter 0 for uniform distribution or 1 for gaussian distribution. ");
        try{
        s = scan.nextInt();
        } catch(InputMismatchException e){
            System.out.println("Unsupported input, please enter either 0 or 1.");
        }
        }
         while(execution_mode !=0 && execution_mode != 1 && execution_mode !=2)
        {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please choose execution mode.");
        System.out.println("Enter 0 for execution mode \"executable\" or 1 for \"resource-depletion\"");
        try{
        
        execution_mode = scan.nextInt();
        } catch(InputMismatchException e){
            System.out.println("Unsupported input, please enter either 0 or 1.");
        }
        }
        
        int choice_number = 13;
        int instances =1;
        double[][] sum = new double [instances][choice_number];
        double[][] sum2= new double [instances][choice_number];
        int[][] success = new int [instances][choice_number];
        int[][] failure = new int [instances][choice_number];
        int[][] early = new int[instances][choice_number];
        int[][] cost_failure = new int[instances][choice_number]; 
        
        
       
        int debug=0;
        PrintWriter[][] pw = new PrintWriter[instances][choice_number];
        String[] heuristics = {"Random","Lowest-Cost","Shortest-Time","Lowest-Fail-Probability","Root-Distance","Cost-Distance","Time-Distance","Rank-based","Rank-based-Time","Rank-based-Combination","Rank-based-extended","Rank-Based-extended-Time","Rank-Based-extended-Combo"};
        BufferedWriter[] bw= new BufferedWriter[13];
        HashMap<String,Integer> resources = new HashMap<String,Integer>();
        resources.put("resourceA",2);
        resources.put("resourceB",1);
        resources.put("resourceC",1);
        int p=0;
        while(p<instances)
        {
        while(y<choice_number)
        {
           String filename = heuristics[y] + (p+1)+  ".txt";
           pw[p][y] = new PrintWriter(new FileWriter(filename));
          
           y++;
        }
        p++;
        y=0;
        }
        y=0;
        while(y<10000)
        {
        Integer[] instances_matrix = new Integer[instances];
        for(int insta=0;insta<instances;insta++)
        {
            instances_matrix[insta] = (insta);
        }
        
        Double[] current_time = new Double[instances];
        Double[] starting_time = new Double[instances];
        HashMap<Integer,Integer[]> min_index = new HashMap<>();
        for(int i=0;i<instances;i++)
        {
            starting_time[i] = 0.0;
            current_time[i] = 0.0;
        }
        Double[] cost = new Double[instances];
        Double[] time = new Double[instances];
        
        double[][] indexes = new double[instances][3];
        double min =0.001, max =1.0;
        int minInt=1, maxInt=10;
        Random r = new Random();
        RandomGaussian gaussian = new RandomGaussian();
        double result, random2;
        HashMap<Integer, String[]> operations = new HashMap();
        if(s==0)
        {
         String[] temp = {"resourceA","i25","i27","i01","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp[temp.length-1] = String.valueOf(random2);
        temp[temp.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp[temp.length-3] = String.valueOf(result);
        operations.put(1,temp);
        String[] temp2 = {"resourceA","i25","i37","i02","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp2[temp2.length-1] = String.valueOf(random2);
        temp2[temp2.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp2[temp2.length-3] = String.valueOf(result);
        operations.put(2,temp2);
        String[] temp3 = {"resourceA","i33","i37","i03","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp3[temp3.length-1] = String.valueOf(random2);
        temp3[temp3.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp3[temp3.length-3] = String.valueOf(result);
        operations.put(3,temp3);
        String[] temp4 = {"resourceA","i33","i37","i04","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        //random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp4[temp4.length-1] = String.valueOf(random2);
        temp4[temp4.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp4[temp4.length-3] = String.valueOf(result);
        operations.put(4,temp4);
        String[] temp5 = {"resourceA","i37","i45","i05","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp5[temp5.length-1] = String.valueOf(random2);
        temp5[temp5.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp5[temp5.length-3] = String.valueOf(result);
        operations.put(5,temp5);
        String[] temp6 = {"resourceA","i21","i37","i06","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp6[temp6.length-1] = String.valueOf(random2);
        temp6[temp6.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp6[temp6.length-3] = String.valueOf(result);
        operations.put(6,temp6);
        String[] temp7 = {"resourceA","i24","i37","i07","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp7[temp7.length-1] = String.valueOf(random2);
        temp7[temp7.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp7[temp7.length-3] = String.valueOf(result);
        operations.put(7,temp7);
        String[] temp8 = {"resourceA","i23","i37","i08","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp8[temp8.length-1] = String.valueOf(random2);
        temp8[temp8.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp8[temp8.length-3] = String.valueOf(result);
        operations.put(8,temp8);
        String[] temp9 = {"resourceA","i24","i39","i09","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp9[temp9.length-1] = String.valueOf(random2);
        temp9[temp9.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp9[temp9.length-3] = String.valueOf(result);
        operations.put(9,temp9);
        String[] temp10 = {"resourceA","i13","i14","i34","i37","i42","i10","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp10[temp10.length-1] = String.valueOf(random2);
        temp10[temp10.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp10[temp10.length-3] = String.valueOf(result);
        operations.put(10,temp10);
        String[] temp111 = {"resourceA","i31","i11","0","60","0.85"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp111[temp111.length-1] = String.valueOf(random2);
        temp111[temp111.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp111[temp111.length-3] = String.valueOf(result);
        operations.put(11,temp111);
        String[] temp112 = {"resourceC","i16","i15","0","0","0.997"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp112[temp112.length-1] = String.valueOf(random2);
        temp112[temp112.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp112[temp112.length-3] = String.valueOf(result);
        operations.put(12,temp112);
        String[] temp113 = {"resourceC","i17","i15","0","0","0.003"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp113[temp113.length-1] = String.valueOf(random2);
        temp113[temp113.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp113[temp113.length-3] = String.valueOf(result);
        operations.put(13,temp113);
        String[] temp114 = {"resourceA","i16","i17","i51","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp114[temp114.length-1] = String.valueOf(random2);
        temp114[temp114.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp114[temp114.length-3] = String.valueOf(result);
        operations.put(14,temp114);
        String[] temp115 = {"resourceC","i25","i30","i35","i36","i44","i16","0","561","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp115[temp115.length-1] = String.valueOf(random2);
        temp115[temp115.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp115[temp115.length-3] = String.valueOf(result);
        operations.put(15,temp115);
        String[] temp116 = {"resourceC","i25","i30","i17","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp116[temp116.length-1] = String.valueOf(random2);
        temp116[temp116.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp116[temp116.length-3] = String.valueOf(result);
        operations.put(16,temp116);
        String[] temp117 = {"resourceC","i01","i18","0","0","0.009"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp117[temp117.length-1] = String.valueOf(random2);
        temp117[temp117.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp117[temp117.length-3] = String.valueOf(result);
        operations.put(17,temp117);
        String[] temp118 = {"resourceC","i02","i18","0","0","0.013"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp118[temp118.length-1] = String.valueOf(random2);
        temp118[temp118.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp118[temp118.length-3] = String.valueOf(result);
        operations.put(18,temp118);
        String[] temp119 = {"resourceC","i08","i18","0","0","0.016"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp119[temp119.length-1] = String.valueOf(random2);
        temp119[temp119.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp119[temp119.length-3] = String.valueOf(result);
        operations.put(19,temp119);
        String[] temp120 = {"resourceC","i09","i18","0","0","0.002"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp120[temp120.length-1] = String.valueOf(random2);
        temp120[temp120.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp120[temp120.length-3] = String.valueOf(result);
        operations.put(20,temp120);
        String[] temp121 = {"resourceC","i10","i18","0","0","0.068"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp121[temp121.length-1] = String.valueOf(random2);
        temp121[temp121.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp121[temp121.length-3] = String.valueOf(result);
        operations.put(21,temp121);
        String[] temp122 = {"resourceA","i11","i18","0","0","0.079"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp122[temp122.length-1] = String.valueOf(random2);
        temp122[temp122.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp122[temp122.length-3] = String.valueOf(result);
        operations.put(22,temp122);
        String[] temp123 = {"resourceA","i15","i18","0","0","0.21"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp123[temp123.length-1] = String.valueOf(random2);
        temp123[temp123.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp123[temp123.length-3] = String.valueOf(result);
        operations.put(23,temp123);
        String[] temp124 = {"resourceA","i09","i11","i15","i50","0","0","1"};
        operations.put(24,temp124);
        String[] temp125 = {"resourceA","i25","i37","i28","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp125[temp125.length-1] = String.valueOf(random2);
        temp125[temp125.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp125[temp125.length-3] = String.valueOf(result);
        operations.put(25,temp125);
        String[] temp126 = {"resourceA","i25","i30","i35","i36","i29","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp126[temp126.length-1] = String.valueOf(random2);
        temp126[temp126.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp126[temp126.length-3] = String.valueOf(result);
        operations.put(26,temp126);
        String[] temp127 = {"resourceA","i32","i37","i43","i30","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp127[temp127.length-1] = String.valueOf(random2);
        temp127[temp127.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp127[temp127.length-3] = String.valueOf(result);
        operations.put(27,temp127);
        String[] temp128 = {"resourceA","i29","i40","i48","i31","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp128[temp128.length-1] = String.valueOf(random2);
        temp128[temp128.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp128[temp128.length-3] = String.valueOf(result);
        operations.put(28,temp128);
        String[] temp129 = {"resourceA","i01","i02","i03","i04","i05","i06","i07","i08","i10","i27","i28","i32","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp129[temp129.length-1] = String.valueOf(random2);
        temp129[temp129.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp129[temp129.length-3] = String.valueOf(result);
        operations.put(29,temp129);
        String[] temp130 = {"resourceA","i36","i37","i41","i34","0","420","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp130[temp130.length-1] = String.valueOf(random2);
        temp130[temp130.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp130[temp130.length-3] = String.valueOf(result);
        operations.put(30,temp130);
        String[] temp131 = {"resourceA","i39","i41","i40","0","30","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp131[temp131.length-1] = String.valueOf(random2);
        temp131[temp131.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp131[temp131.length-3] = String.valueOf(result);
        operations.put(31,temp131);
        String[] temp132 = {"resourceA","i47","i42","0","30","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp132[temp132.length-1] = String.valueOf(random2);
        temp132[temp132.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp132[temp132.length-3] = String.valueOf(result);
        operations.put(32,temp132);
        String[] temp133 = {"resourceA","i39","i49","i43","0","60","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp133[temp133.length-1] = String.valueOf(random2);
        temp133[temp133.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp133[temp133.length-3] = String.valueOf(result);
        operations.put(33,temp133);
        String[] temp134 = {"resourceA","i13","0","8","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp134[temp134.length-1] = String.valueOf(random2);
        temp134[temp134.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp134[temp134.length-3] = String.valueOf(result);
        operations.put(34,temp134);
        String[] temp135 = {"resourceA","i14","0","8","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp135[temp135.length-1] = String.valueOf(random2);
        temp135[temp135.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp135[temp135.length-3] = String.valueOf(result);
        operations.put(35,temp135);
        String[] temp136 = {"resourceA","i21","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp136[temp136.length-1] = String.valueOf(random2);
        temp136[temp136.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp136[temp136.length-3] = String.valueOf(result);
        operations.put(36,temp136);
        String[] temp137 = {"resourceA","i23","0","67","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp137[temp137.length-1] = String.valueOf(random2);
        temp137[temp137.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp137[temp137.length-3] = String.valueOf(result);
        operations.put(37,temp137);
        String[] temp138 = {"resourceA","i24","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp138[temp138.length-1] = String.valueOf(random2);
        temp138[temp138.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp138[temp138.length-3] = String.valueOf(result);
        operations.put(38,temp138);
        String[] temp139 = {"resourceA","i25","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp139[temp139.length-1] = String.valueOf(random2);
        temp139[temp139.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp139[temp139.length-3] = String.valueOf(result);
        operations.put(39,temp139);
        String[] temp140 = {"resourceA","i27","0","8","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp140[temp140.length-1] = String.valueOf(random2);
        temp140[temp140.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp140[temp140.length-3] = String.valueOf(result);
        operations.put(40,temp140);
        String[] temp141 = {"resourceA","i33","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp141[temp141.length-1] = String.valueOf(random2);
        temp141[temp141.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp141[temp141.length-3] = String.valueOf(result);
        operations.put(41,temp141);
        String[] temp142 = {"resourceA","i35","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp142[temp142.length-1] = String.valueOf(random2);
        temp142[temp142.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp142[temp142.length-3] = String.valueOf(result);
        operations.put(42,temp142);
        String[] temp143 = {"resourceA","i36","0","1","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp143[temp143.length-1] = String.valueOf(random2);
        temp143[temp143.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp143[temp143.length-3] = String.valueOf(result);
        operations.put(43,temp143);
        String[] temp144 = {"resourceA","i37","0","167","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp144[temp144.length-1] = String.valueOf(random2);
        temp144[temp144.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp144[temp144.length-3] = String.valueOf(result);
        operations.put(44,temp144);
        String[] temp145 = {"resourceA","i39","0","17","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp145[temp145.length-1] = String.valueOf(random2);
        temp145[temp145.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp145[temp145.length-3] = String.valueOf(result);
        operations.put(45,temp145);
        String[] temp146 = {"resourceA","i41","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp146[temp146.length-1] = String.valueOf(random2);
        temp146[temp146.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp146[temp146.length-3] = String.valueOf(result);
        operations.put(46,temp146);
        String[] temp147 = {"resourceA","i44","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp147[temp147.length-1] = String.valueOf(random2);
        temp147[temp147.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp147[temp147.length-3] = String.valueOf(result);
        operations.put(47,temp147);
        String[] temp148 = {"resourceB","i45","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp148[temp148.length-1] = String.valueOf(random2);
        temp148[temp148.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp148[temp148.length-3] = String.valueOf(result);
        operations.put(48,temp148);
        String[] temp149 = {"resourceB","i47","0","33","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp149[temp149.length-1] = String.valueOf(random2);
        temp149[temp149.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp149[temp149.length-3] = String.valueOf(result);
        operations.put(49,temp149);
        String[] temp150 = {"resourceB","i48","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp150[temp150.length-1] = String.valueOf(random2);
        temp150[temp150.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp150[temp150.length-3] = String.valueOf(result);
        operations.put(50,temp150);
        String[] temp151 = {"resourceB","i49","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp151[temp151.length-1] = String.valueOf(random2);
        temp151[temp151.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp151[temp151.length-3] = String.valueOf(result);
        operations.put(51,temp151);
        String[] temp152 = {"resourceB","i50","i18","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp152[temp152.length-1] = String.valueOf(random2);
        temp152[temp152.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp152[temp152.length-3] = String.valueOf(result);
        operations.put(52,temp152);
        String[] temp153 = {"resourceB","i51","i15","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp153[temp153.length-1] = String.valueOf(random2);
        temp153[temp153.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp153[temp153.length-3] = String.valueOf(result);
        operations.put(53,temp153);
        }
        else
        {
            
        
        String[] temp = {"resourceA","i25","i27","i01","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp[temp.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp[temp.length-1])));
        temp[temp.length-1] = String.valueOf(random2);
        temp[temp.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp[temp.length-3]));
        temp[temp.length-3] = String.valueOf(result);
        operations.put(1,temp);
        String[] temp2 = {"resourceA","i25","i37","i02","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp2[temp2.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp2[temp2.length-1])));
        temp2[temp2.length-1] = String.valueOf(random2);
        temp2[temp2.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp2[temp2.length-3]));
        temp2[temp2.length-3] = String.valueOf(result);
        operations.put(2,temp2);
        String[] temp3 = {"resourceA","i33","i37","i03","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp3[temp3.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp3[temp3.length-1])));
        temp3[temp3.length-1] = String.valueOf(random2);
        temp3[temp3.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp3[temp3.length-3]));
        temp3[temp3.length-3] = String.valueOf(result);
        operations.put(3,temp3);
        String[] temp4 = {"resourceA","i33","i37","i04","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp4[temp4.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp4[temp4.length-1])));
        temp4[temp4.length-1] = String.valueOf(random2);
        temp4[temp4.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp4[temp4.length-3]));
        temp4[temp4.length-3] = String.valueOf(result);
        operations.put(4,temp4);
        String[] temp5 = {"resourceA","i37","i45","i05","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp5[temp5.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp5[temp5.length-1])));
        temp5[temp5.length-1] = String.valueOf(random2);
        temp5[temp5.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp5[temp5.length-3]));
        temp5[temp5.length-3] = String.valueOf(result);
        operations.put(5,temp5);
        String[] temp6 = {"resourceA","i21","i37","i06","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp6[temp6.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp6[temp6.length-1])));
        temp6[temp6.length-1] = String.valueOf(random2);
        temp6[temp6.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp6[temp6.length-3]));
        temp6[temp6.length-3] = String.valueOf(result);
        operations.put(6,temp6);
        String[] temp7 = {"resourceA","i24","i37","i07","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp7[temp7.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp7[temp7.length-1])));
        temp7[temp7.length-1] = String.valueOf(random2);
        temp7[temp7.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp7[temp7.length-3]));
        temp7[temp7.length-3] = String.valueOf(result);
        operations.put(7,temp7);
        String[] temp8 = {"resourceA","i23","i37","i08","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp8[temp8.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp8[temp8.length-1])));
        temp8[temp8.length-1] = String.valueOf(random2);
        temp8[temp8.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp8[temp8.length-3]));
        temp8[temp8.length-3] = String.valueOf(result);
        operations.put(8,temp8);
        String[] temp9 = {"resourceA","i24","i39","i09","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp9[temp9.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp9[temp9.length-1])));
        temp9[temp9.length-1] = String.valueOf(random2);
        temp9[temp9.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp9[temp9.length-3]));
        temp9[temp9.length-3] = String.valueOf(result);
        operations.put(9,temp9);
        String[] temp10 = {"resourceA","i13","i14","i34","i37","i42","i10","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp10[temp10.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp10[temp10.length-1])));
        temp10[temp10.length-1] = String.valueOf(random2);
        temp10[temp10.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp10[temp10.length-3]));
        temp10[temp10.length-3] = String.valueOf(result);
        operations.put(10,temp10);
        String[] temp111 = {"resourceA","i31","i11","0.6","0.6","0.85"};
        result = gaussian.getRandInt(Double.parseDouble(temp111[temp111.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp111[temp111.length-1])));
        temp111[temp111.length-1] = String.valueOf(random2);
        temp111[temp111.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp111[temp111.length-3]));
        temp111[temp111.length-3] = String.valueOf(result);
        operations.put(11,temp111);
        String[] temp112 = {"resourceC","i16","i15","0","0","0.997"};
        result = gaussian.getRandInt(Double.parseDouble(temp112[temp112.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp112[temp112.length-1])));
        temp112[temp112.length-1] = String.valueOf(random2);
        temp112[temp112.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp112[temp112.length-3]));
        temp112[temp112.length-3] = String.valueOf(result);
        operations.put(12,temp112);
        String[] temp113 = {"resourceC","i17","i15","0","0","0.003"};
        result = gaussian.getRandInt(Double.parseDouble(temp113[temp113.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp113[temp113.length-1])));
        temp113[temp113.length-1] = String.valueOf(random2);
        temp113[temp113.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp113[temp113.length-3]));
        temp113[temp113.length-3] = String.valueOf(result);
        operations.put(13,temp113);
        String[] temp114 = {"resourceA","i16","i17","i51","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp114[temp114.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp114[temp114.length-1])));
        temp114[temp114.length-1] = String.valueOf(random2);
        temp114[temp114.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp114[temp114.length-3]));
        temp114[temp114.length-3] = String.valueOf(result);
        operations.put(14,temp114);
        String[] temp115 = {"resourceC","i25","i30","i35","i36","i44","i16","5.61","5.61","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp115[temp115.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp115[temp115.length-1])));
        temp115[temp115.length-1] = String.valueOf(random2);
        temp115[temp115.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp115[temp115.length-3]));
        temp115[temp115.length-3] = String.valueOf(result);
        operations.put(15,temp115);
        String[] temp116 = {"resourceC","i25","i30","i17","6.1","6.1","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp116[temp116.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp116[temp116.length-1])));
        temp116[temp116.length-1] = String.valueOf(random2);
        temp116[temp116.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp116[temp116.length-3]));
        temp116[temp116.length-3] = String.valueOf(result);
        operations.put(16,temp116);
        String[] temp117 = {"resourceC","i01","i18","0","0","0.009"};
        result = gaussian.getRandInt(Double.parseDouble(temp117[temp117.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp117[temp117.length-1])));
        temp117[temp117.length-1] = String.valueOf(random2);
        temp117[temp117.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp117[temp117.length-3]));
        temp117[temp117.length-3] = String.valueOf(result);
        operations.put(17,temp117);
        String[] temp118 = {"resourceC","i02","i18","0","0","0.013"};
        result = gaussian.getRandInt(Double.parseDouble(temp118[temp118.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp118[temp118.length-1])));
        temp118[temp118.length-1] = String.valueOf(random2);
        temp118[temp118.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp118[temp118.length-3]));
        temp118[temp118.length-3] = String.valueOf(result);
        operations.put(18,temp118);
        String[] temp119 = {"resourceC","i08","i18","0","0","0.016"};
        result = gaussian.getRandInt(Double.parseDouble(temp119[temp119.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp119[temp119.length-1])));
        temp119[temp119.length-1] = String.valueOf(random2);
        temp119[temp119.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp119[temp119.length-3]));
        temp119[temp119.length-3] = String.valueOf(result);
        operations.put(19,temp119);
        String[] temp120 = {"resourceC","i09","i18","0","0","0.002"};
        result = gaussian.getRandInt(Double.parseDouble(temp120[temp120.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp120[temp120.length-1])));
        temp120[temp120.length-1] = String.valueOf(random2);
        temp120[temp120.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp120[temp120.length-3]));
        temp120[temp120.length-3] = String.valueOf(result);
        operations.put(20,temp120);
        String[] temp121 = {"resourceC","i10","i18","0","0","0.068"};
        result = gaussian.getRandInt(Double.parseDouble(temp121[temp121.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp121[temp121.length-1])));
        temp121[temp121.length-1] = String.valueOf(random2);
        temp121[temp121.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp121[temp121.length-3]));
        temp121[temp121.length-3] = String.valueOf(result);
        operations.put(21,temp121);
        String[] temp122 = {"resourceA","i11","i18","0","0","0.079"};
        result = gaussian.getRandInt(Double.parseDouble(temp122[temp122.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp122[temp122.length-1])));
        temp122[temp122.length-1] = String.valueOf(random2);
        temp122[temp122.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp122[temp122.length-3]));
        temp122[temp122.length-3] = String.valueOf(result);
        operations.put(22,temp122);
        String[] temp123 = {"resourceA","i15","i18","0","0","0.21"};
        result = gaussian.getRandInt(Double.parseDouble(temp123[temp123.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp123[temp123.length-1])));
        temp123[temp123.length-1] = String.valueOf(random2);
        temp123[temp123.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp123[temp123.length-3]));
        temp123[temp123.length-3] = String.valueOf(result);
        operations.put(23,temp123);
        String[] temp124 = {"resourceA","i09","i11","i15","i50","0","0","1"};
        operations.put(24,temp124);
        String[] temp125 = {"resourceA","i25","i37","i28","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp125[temp125.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp125[temp125.length-1])));
        temp125[temp125.length-1] = String.valueOf(random2);
        temp125[temp125.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp125[temp125.length-3]));
        temp125[temp125.length-3] = String.valueOf(result);
        operations.put(25,temp125);
        String[] temp126 = {"resourceA","i25","i30","i35","i36","i29","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp126[temp126.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp126[temp126.length-1])));
        temp126[temp126.length-1] = String.valueOf(random2);
        temp126[temp126.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp[temp126.length-3]));
        temp126[temp126.length-3] = String.valueOf(result);
        operations.put(26,temp126);
        String[] temp127 = {"resourceA","i32","i37","i43","i30","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp127[temp127.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp127[temp127.length-1])));
        temp127[temp127.length-1] = String.valueOf(random2);
        temp127[temp127.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp127[temp127.length-3]));
        temp127[temp127.length-3] = String.valueOf(result);
        operations.put(27,temp127);
        String[] temp128 = {"resourceA","i29","i40","i48","i31","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp128[temp128.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp128[temp128.length-1])));
        temp128[temp128.length-1] = String.valueOf(random2);
        temp128[temp128.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp128[temp128.length-3]));
        temp128[temp128.length-3] = String.valueOf(result);
        operations.put(28,temp128);
        String[] temp129 = {"resourceA","i01","i02","i03","i04","i05","i06","i07","i08","i10","i27","i28","i32","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp129[temp129.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp129[temp129.length-1])));
        temp129[temp129.length-1] = String.valueOf(random2);
        temp129[temp129.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp129[temp129.length-3]));
        temp129[temp129.length-3] = String.valueOf(result);
        operations.put(29,temp129);
        String[] temp130 = {"resourceA","i36","i37","i41","i34","4.2","4.2","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp130[temp130.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp130[temp130.length-1])));
        temp130[temp130.length-1] = String.valueOf(random2);
        temp130[temp130.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp130[temp130.length-3]));
        temp130[temp130.length-3] = String.valueOf(result);
        operations.put(30,temp130);
        String[] temp131 = {"resourceA","i39","i41","i40","0.30","0.30","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp131[temp131.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp131[temp131.length-1])));
        temp131[temp131.length-1] = String.valueOf(random2);
        temp131[temp131.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp131[temp131.length-3]));
        temp131[temp131.length-3] = String.valueOf(result);
        operations.put(31,temp131);
        String[] temp132 = {"resourceA","i47","i42","0.30","0.30","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp132[temp132.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp132[temp132.length-1])));
        temp132[temp132.length-1] = String.valueOf(random2);
        temp132[temp132.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp132[temp132.length-3]));
        temp132[temp132.length-3] = String.valueOf(result);
        operations.put(32,temp132);
        String[] temp133 = {"resourceA","i39","i49","i43","0.60","0.60","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp133[temp133.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp133[temp133.length-1])));
        temp133[temp133.length-1] = String.valueOf(random2);
        temp133[temp133.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp133[temp133.length-3]));
        temp133[temp133.length-3] = String.valueOf(result);
        operations.put(33,temp133);
        String[] temp134 = {"resourceA","i13","0.08","0.08","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp134[temp134.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp134[temp134.length-1])));
        temp134[temp134.length-1] = String.valueOf(random2);
        temp134[temp134.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp134[temp134.length-3]));
        temp134[temp134.length-3] = String.valueOf(result);
        operations.put(34,temp134);
        String[] temp135 = {"resourceA","i14","0.08","0.08","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp135[temp135.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp135[temp135.length-1])));
        temp135[temp135.length-1] = String.valueOf(random2);
        temp135[temp135.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp135[temp135.length-3]));
        temp135[temp135.length-3] = String.valueOf(result);
        operations.put(35,temp135);
        String[] temp136 = {"resourceA","i21","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp136[temp136.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp136[temp136.length-1])));
        temp136[temp136.length-1] = String.valueOf(random2);
        temp136[temp136.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp136[temp136.length-3]));
        temp136[temp136.length-3] = String.valueOf(result);
        operations.put(36,temp136);
        String[] temp137 = {"resourceA","i23","0.67","0.67","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp137[temp137.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp137[temp137.length-1])));
        temp137[temp137.length-1] = String.valueOf(random2);
        temp137[temp137.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp137[temp137.length-3]));
        temp137[temp137.length-3] = String.valueOf(result);
        operations.put(37,temp137);
        String[] temp138 = {"resourceA","i24","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp138[temp138.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp138[temp138.length-1])));
        temp138[temp138.length-1] = String.valueOf(random2);
        temp138[temp138.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp138[temp138.length-3]));
        temp138[temp138.length-3] = String.valueOf(result);
        operations.put(38,temp138);
        String[] temp139 = {"resourceA","i25","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp139[temp139.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp139[temp139.length-1])));
        temp139[temp139.length-1] = String.valueOf(random2);
        temp139[temp139.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp139[temp139.length-3]));
        temp139[temp139.length-3] = String.valueOf(result);
        operations.put(39,temp139);
        String[] temp140 = {"resourceA","i27","0.08","0.08","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp140[temp140.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp140[temp140.length-1])));
        temp140[temp140.length-1] = String.valueOf(random2);
        temp140[temp140.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp140[temp140.length-3]));
        temp140[temp140.length-3] = String.valueOf(result);
        operations.put(40,temp140);
        String[] temp141 = {"resourceA","i33","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp141[temp141.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp141[temp141.length-1])));
        temp141[temp141.length-1] = String.valueOf(random2);
        temp141[temp141.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp141[temp141.length-3]));
        temp141[temp141.length-3] = String.valueOf(result);
        operations.put(41,temp141);
        String[] temp142 = {"resourceA","i35","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp142[temp142.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp142[temp142.length-1])));
        temp142[temp142.length-1] = String.valueOf(random2);
        temp142[temp142.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp142[temp142.length-3]));
        temp142[temp142.length-3] = String.valueOf(result);
        operations.put(42,temp142);
        String[] temp143 = {"resourceA","i36","1","1","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp143[temp143.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp143[temp143.length-1])));
        temp143[temp143.length-1] = String.valueOf(random2);
        temp143[temp143.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp143[temp143.length-3]));
        temp143[temp143.length-3] = String.valueOf(result);
        operations.put(43,temp143);
        String[] temp144 = {"resourceA","i37","1.67","1.67","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp144[temp144.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp144[temp144.length-1])));
        temp144[temp144.length-1] = String.valueOf(random2);
        temp144[temp144.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp144[temp144.length-3]));
        temp144[temp144.length-3] = String.valueOf(result);
        operations.put(44,temp144);
        String[] temp145 = {"resourceA","i39","0.17","0.17","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp145[temp145.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp145[temp145.length-1])));
        temp145[temp145.length-1] = String.valueOf(random2);
        temp145[temp145.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp145[temp145.length-3]));
        temp145[temp145.length-3] = String.valueOf(result);
        operations.put(45,temp145);
        String[] temp146 = {"resourceA","i41","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp146[temp146.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp146[temp146.length-1])));
        temp146[temp146.length-1] = String.valueOf(random2);
        temp146[temp146.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp146[temp146.length-3]));
        temp146[temp146.length-3] = String.valueOf(result);
        operations.put(46,temp146);
        String[] temp147 = {"resourceA","i44","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp147[temp147.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp147[temp147.length-1])));
        temp147[temp147.length-1] = String.valueOf(random2);
        temp147[temp147.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp147[temp147.length-3]));
        temp147[temp147.length-3] = String.valueOf(result);
        operations.put(47,temp147);
        String[] temp148 = {"resourceB","i45","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp148[temp148.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp148[temp148.length-1])));
        temp148[temp148.length-1] = String.valueOf(random2);
        temp148[temp148.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp148[temp148.length-3]));
        temp148[temp148.length-3] = String.valueOf(result);
        operations.put(48,temp148);
        String[] temp149 = {"resourceB","i47","0.33","0.33","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp149[temp149.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp149[temp149.length-1])));
        temp149[temp149.length-1] = String.valueOf(random2);
        temp149[temp149.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp149[temp149.length-3]));
        temp149[temp149.length-3] = String.valueOf(result);
        operations.put(49,temp149);
        String[] temp150 = {"resourceB","i48","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp150[temp150.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp150[temp150.length-1])));
        temp150[temp150.length-1] = String.valueOf(random2);
        temp150[temp150.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp150[temp150.length-3]));
        temp150[temp150.length-3] = String.valueOf(result);
        operations.put(50,temp150);
        String[] temp151 = {"resourceB","i49","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp151[temp151.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp151[temp151.length-1])));
        temp151[temp151.length-1] = String.valueOf(random2);
        temp151[temp151.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp151[temp151.length-3]));
        temp151[temp151.length-3] = String.valueOf(result);
        operations.put(51,temp151);
        String[] temp152 = {"resourceB","i50","i18","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp152[temp152.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp152[temp152.length-1])));
        temp152[temp152.length-1] = String.valueOf(random2);
        temp152[temp152.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp152[temp152.length-3]));
        temp152[temp152.length-3] = String.valueOf(result);
        operations.put(52,temp152);
        String[] temp153 = {"resourceB","i51","i15","0","0","1"};
        result = gaussian.getRandInt(Double.parseDouble(temp153[temp153.length-2]));
        random2 = gaussian.getRandProb(1-(Double.parseDouble(temp153[temp153.length-1])));
        temp153[temp153.length-1] = String.valueOf(random2);
        temp153[temp153.length-2] = String.valueOf(result);
        result = gaussian.getRandInt(Double.parseDouble(temp153[temp153.length-3]));
        temp153[temp153.length-3] = String.valueOf(result);
        operations.put(53,temp153);
        }
        HashMap<Integer,String[]> operations_graph = (HashMap) operations.clone();
        Iterator print = operations.entrySet().iterator();
        
        while(print.hasNext())
        {
            HashMap.Entry entryprint = (HashMap.Entry) print.next();
            String[] printtemp = (String[]) entryprint.getValue();
            
        }
        
        
       int count = 10;
        int choice =0;
        HashMap<Integer,Map<Integer, String[]>> listOfMaps = new HashMap<>();
        int u=0;
        
         
        
        while(u<(choice_number*instances))
        {
           listOfMaps.put(u,(HashMap) operations.clone());
           u++;
          
           
        }
        HashMap<Integer,Integer> productions = new HashMap<Integer,Integer>();
        determineExecutionInstance(operations,productions);
        double[][] graph_time = new double[52][52];
        createArrayTime(operations,graph_time);
        double[][] graph_cost = new double [52][52];
        createArrayCost(operations,graph_cost);
        int[][] graph_root = new int[52][52];
        createArrayRoot(operations,graph_root);
        while(choice<choice_number)
        {
        current_time[0]=0.0;
        
        HashMap<Integer,ArrayList<Double>> listOfEnd = new HashMap<>();
        HashMap<Integer,ArrayList<String>> listOfAllocations = new HashMap<>();
        HashMap<Integer,HashMap<Integer, String[]>> listOfExecutables = new HashMap<>();
        HashMap<Integer,HashMap<Integer, String[]>> listOfExecutables2 = new HashMap<>();
        HashMap<Integer,HashSet<String>> listOfAvailables = new HashMap<>();
        HashMap<Integer,HashMap<String,Double>> listOfTimes = new HashMap<>();
        HashMap<Integer,Map<Integer, Integer>> listOfPaths = new HashMap<>();
        HashMap<Integer,HashSet<Integer>> listOfPrunes = new HashMap<>();
        HashMap<Integer,ArrayList<Double>> listOfStarts = new HashMap<>();
        int inst=0;
        boolean next = false;
         while(inst<instances)
        {
         
         starting_time[inst] =0.0;
         current_time[inst] =0.0;
         Integer[] temp_inst =  new Integer[2];
         temp_inst[0] = temp_inst[1] = 0;
    
         
         min_index.put(inst,temp_inst);
         ArrayList<Double> temp_end = new ArrayList();
         ArrayList<String> temp_allocation = new ArrayList();
         ArrayList<Double> temp_start = new ArrayList();
         HashMap<Integer,String[]> temp_executable = new HashMap();
         HashMap<Integer, String[]> temp_executable2 = new HashMap();
         HashSet<String> temp_available = new HashSet();
         HashMap<Integer,Integer> temp_path2 = new HashMap<Integer,Integer>();
         HashSet<Integer> temp_prune = new HashSet<Integer>();
         HashMap<String,Double> times = new HashMap<>();
         
         listOfStarts.put(inst,temp_start);
         listOfTimes.put(inst,times);
         listOfEnd.put(inst,temp_end);
         listOfAllocations.put(inst,temp_allocation);
         listOfExecutables.put(inst,temp_executable);
         listOfAvailables.put(inst,temp_available);
         listOfPaths.put(inst,temp_path2);
         listOfPrunes.put(inst,temp_prune);
         listOfExecutables2.put(inst, temp_executable2);
         cost[inst]=0.0;
         time[inst]=0.0;
         inst++;
        }    
        
         inst=0;
        count =10;
        
        boolean[] complete_instance = new boolean[instances];
        boolean resourceAvailability=false;
        HashMap<Integer,String[]> removed_operations = new HashMap<>(); 
        while(count !=0)
        {
             inst=0;
            boolean resource_flag2 = false;
            while(inst<instances)
            {
                
              int complete_count=0;
              for(int insta=0;insta<complete_instance.length;insta++)
              {
                  if(complete_instance[insta]==true)
                  {
                      complete_count++;
                  }
              }
              if(complete_count==complete_instance.length)
              {
                  count=0;
                  break;
              }
              
              if(complete_instance[inst]==true)
              {
               inst++;
              
               continue;
              }   
              operations= (HashMap)listOfMaps.get(choice + (inst * choice_number));
              
              
              HashMap<Integer,String[]> executable = new HashMap<Integer,String[]>();//new HashMap();
              HashMap<Integer,String[]> executable_without = new HashMap<Integer,String[]>();
              HashSet<String> available = listOfAvailables.get(inst);
              HashMap<Integer,Integer> path = (HashMap) listOfPaths.get(inst);
              HashSet<Integer> prune = listOfPrunes.get(inst);
              ArrayList<Double> end = listOfEnd.get(inst);
              Integer[] temp_inst = min_index.get(inst);
              ArrayList<String> allocation = listOfAllocations.get(inst);
              HashMap<String,Double> times = (HashMap) listOfTimes.get(inst);
              //HashMap<Integer,Double> starting = (HashMap) listOfStarting.get(inst); to be implemented
              Iterator it = operations.entrySet().iterator();
              ArrayList<Double> start = listOfStarts.get(inst);
              boolean available_input = false;
              boolean available_resources = false;
              HashMap<String,Integer> failures = new HashMap();
           //Iterator it = operations.entrySet().iterator();
           while (it.hasNext()) //calculate executable operations
           {
            
            HashMap.Entry pair = (HashMap.Entry)it.next();
            String[] temp11 = (String[])pair.getValue();
            if(temp11.length==5) //cases where operation has no input elements
            {
                executable.put((Integer)pair.getKey(),(String[]) pair.getValue());
            }
            else //cases where operation has input elements. so each input element is checked for availability
            {
              int temp12 = 0;
              int temp13 =0; 
              for(int i=0;i<temp11.length-4;i++)
              {
                if(available.contains(temp11[i]))//each input is checked
                {
                    temp12++;
                    
                }
              }
              for(int i=1;i<temp11.length-4;i++)
              {
                if(!times.containsKey(temp11[i]))
                {
                    
                }
                else
                {
                    Double next_time = times.get(temp11[i]);
                    if(next_time<= current_time[inst])
                    {
                        temp13++;
                    }
                }
                  
              }
             if((temp12 == temp11.length -5) &&(temp13 == temp11.length -5))//if every input is available then the operation is available for execution
              {
                available_input = true;
               
               executable.put((Integer)pair.getKey(),(String[]) pair.getValue());
               
               
              }
              else if((temp12 == temp11.length -5) &&(temp13 != temp11.length -5))
              {
                  executable_without.put((Integer)pair.getKey(),(String[]) pair.getValue());
              }
            }
            //it.remove(); // avoids a ConcurrentModificationException
            
            }
           if(choice==10 || choice==11 || choice == 12)
           {
               if(prune.size()>0)
               {
                   for(Integer op : prune)
                   {
                       executable.remove(op);
                       executable_without.remove(op);
                   }
               }
           }
           if(execution_mode==1)
           {
           HashMap<String,Integer> resource_availability = findNumberAvailableResources(executable,resources,listOfAllocations,listOfEnd,starting_time[inst]);
           int entire_availability =0 ;
           Iterator resource_availability_it = resource_availability.entrySet().iterator();
           while(resource_availability_it.hasNext())
           {
               Map.Entry<String,Integer> resource_entry = (Map.Entry<String,Integer>) resource_availability_it.next();
               entire_availability += resource_entry.getValue();
              // System.out.println("current_time" + current_time[inst] + " resource: " + resource_entry.getKey() + " , " + "availability: " + resource_entry.getValue());
               if(resource_entry.getValue()==0)
               {
                   Iterator executable_it = executable.entrySet().iterator();
                   while(executable_it.hasNext())
                   {
                        Map.Entry<Integer,String[]> operation_entry = (Map.Entry<Integer,String[]>) executable_it.next();
                        String[] operation_cand = operation_entry.getValue();
                        if(operation_cand[0].equals(resource_entry.getKey()))
                        {
                            executable_it.remove();
                        }
                   }
               }
           }
           
           if(executable.isEmpty() && entire_availability==0)
           {
               starting_time[inst] = findEarliestStartingTime(resources,listOfAllocations,listOfEnd,starting_time[inst]);
                current_time[inst] = starting_time[inst];
                if(current_time[inst]==Double.MAX_VALUE)
                {
                 time[inst] = getDuration(end);
                 System.out.println("Heuristic: " + heuristics[choice] + " instance: " + inst + "cost: " + cost[inst] + "     time: " + time[inst]);
                 System.out.println("");
                 String filename = heuristics[choice] + ".txt";
               
                 //pw[choice].println((y+1)+","+cost+","+time);
                 cost_failure[inst][choice] +=cost[inst];
                 //failure[inst][choice]+=1;
                 //System.out.println("Heuristic: " + heuristics[choice] + " instance: " + inst + "cost: " + cost[inst] + "     time: " + time[inst]);
                 //System.out.println("");
                 complete_instance[inst] = true;              
                 break;
                }
               
               
                
                
                
                continue;
           }
           
           if(executable.isEmpty() && entire_availability!=0)
           {
               starting_time[inst] = findEarliestStartingTime(resources,listOfAllocations,listOfEnd,starting_time[inst]);
                current_time[inst] = starting_time[inst];
               continue;
           }
            }
           
           if(executable.isEmpty()  && executable_without.isEmpty())//execution has completed since the production of A is no longer possible
           {
               time[inst] = getDuration(end);
               //System.out.println(cost);
               //System.out.println();
               String filename = heuristics[choice] + ".txt";
               failure[inst][choice]+=1;
               //bw[choice].write((y+1)+","+cost+","+time);
               //bw[choice].newLine();
               //sum[choice] += cost;
               //sum2[choice] +=time;
               if(time[inst]==0||cost[inst]==0)
               {
                   debug++;
               }
               System.out.println("Heuristic: " + heuristics[choice] + " instance: " + inst + "cost: " + cost[inst] + "     time: " + time[inst]);
               System.out.println("");
               complete_instance[inst] = true;    
               break;
           }
           
           
           
           if(executable.isEmpty() && !executable_without.isEmpty())//execution has completed since the production of A is no longer possible
           {
            
            System.out.println(executable_without.keySet());
            int key;
            if(choice==0)
           {
               key=executeRandom(executable_without,available,operations,productions);
               
           }
           else if(choice==1)
           {
               key=executeLowestCost(executable_without,available,operations_graph,productions);
           }
           else if(choice==2)
           {
               key=executeShortestTime(executable_without,available,operations_graph,productions);
           }
           else if(choice==3)
           {
               key=executeLowestFail(executable_without,available,operations_graph,productions);
               
           }
           else if(choice==4)
           {
               key=rootDistance(executable_without,available,operations_graph,graph_root,productions);
           }
           else if(choice==5)
           {
               key=costDistance(executable_without,available,operations_graph,graph_cost,productions);
           }
           else if(choice==6)
           {
               key=timeDistance(executable_without,available,operations_graph,graph_time,productions);
           }
           else if(choice==7)
           {
               key=knockoutPathOld(executable_without,available,operations_graph,productions);
               
           }
           else if(choice==8)
           {
               key=knockoutPathTime(executable_without,available,operations_graph,productions);
               
           }
           else if(choice==9)
           {
              key=knockoutPathCombo(executable_without,available,operations_graph,productions);
           }
           else if(choice==10)
           {
               key=knockoutPathPruneBoth(executable_without,available,operations_graph,productions,prune);
           }
           else if(choice==11)
           {
               key=knockoutPathPruneBothTime(executable_without,available,operations_graph,productions,prune);
           }
           else
           {
               key=knockoutPathPruneBothCombo(executable_without,available,operations_graph,productions,prune);
           }
           
            
            
            
            
            current_time[inst] = findStartingTime(operations,key,listOfEnd,listOfAllocations,instances_matrix,current_time[inst],resources,times,starting_time[inst],indexes,inst);
           
            continue;           
           }
           
           
           
           
           int key;
           if(choice==0)
           {
               key=executeRandom(executable,available,operations,productions);
               
           }
           else if(choice==1)
           {
               key=executeLowestCost(executable,available,operations_graph,productions);
           }
           else if(choice==2)
           {
               key=executeShortestTime(executable,available,operations_graph,productions);
           }
           else if(choice==3)
           {
               key=executeLowestFail(executable,available,operations_graph,productions);
               
           }
           else if(choice==4)
           {
               key=rootDistance(executable,available,operations_graph,graph_root,productions);
           }
           else if(choice==5)
           {
               key=costDistance(executable,available,operations_graph,graph_cost,productions);
           }
           else if(choice==6)
           {
               key=timeDistance(executable,available,operations_graph,graph_time,productions);
           }
           else if(choice==7)
           {
               key=knockoutPathOld(executable,available,operations_graph,productions);
               
           }
           else if(choice==8)
           {
               key=knockoutPathTime(executable,available,operations_graph,productions);
               
           }
           else if(choice==9)
           {
              key=knockoutPathCombo(executable,available,operations_graph,productions);
           }
           else if(choice==10)
           {
               key=knockoutPathPruneBoth(executable,available,operations_graph,productions,prune);
           }
           else if(choice==11)
           {
               key=knockoutPathPruneBothTime(executable,available,operations_graph,productions,prune);
           }
           else
           {
               key=knockoutPathPruneBothCombo(executable,available,operations_graph,productions,prune);
           }
           
           String[] temp16 = executable.get(key);
           int result_execution = productions.get(key);
          
           AtomicBoolean resource_flag = new AtomicBoolean(false); 


           
           StringBuilder resource_choice= new StringBuilder("");
           if(execution_mode==0)
           {
           current_time[inst] = findStartingTime(operations,key,listOfEnd,listOfAllocations,instances_matrix,current_time[inst],resources,times,starting_time[inst], indexes, inst);
           }
           
           
           if(result_execution ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
           {
            
            available.add(temp16[temp16.length-4]);
            //starting.put(key, current_time[inst]);
            starting_time[inst] = current_time[inst];
            
            Double end_time = current_time[inst] + Double.parseDouble(temp16[temp16.length-3]);
            System.out.println("Heuristic: " + heuristics[choice] + " Op"  + key+ "," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time[inst] + " , end time:" + end_time + "duration: " + Double.parseDouble(temp16[temp16.length-3]) + "resource: " + temp16[0] + "cost: " + Double.parseDouble(temp16[temp16.length-2]));
            if(execution_mode==0)
            {
               current_time[inst] = end_time; 
            }
            path.put(key,0);
            allocation.add(temp16[0]);
            end.add(end_time);
            times.put((temp16[temp16.length-4]),end_time);
            start.add(starting_time[inst]);
           
            
            
           }
           else //execution was unsuccessful and therefore the element was not produced
           {
            
            Double end_time = current_time[inst] + Double.parseDouble(temp16[temp16.length-3]);
            System.out.println("Heuristic: " + heuristics[choice] + " Op"  + key+ " fail ," + temp16[temp16.length-4] + " , instance: " + inst + " start_time: " + current_time[inst] + " , end time:" + end_time + " duration: " + Double.parseDouble(temp16[temp16.length-3]) + "resource: " + temp16[0] + " cost: " + Double.parseDouble(temp16[temp16.length-2]));
             if(execution_mode==0)
            {
               current_time[inst] = end_time; 
            }
            path.put(key,1);
            allocation.add(temp16[0]);
            end.add(end_time);
            times.put((temp16[temp16.length-4]),end_time);
            start.add(starting_time[inst]);
            
            
            
           
           }
               
           
           
           
          String[] temp14 = operations.get(key);
           
           if(resource_flag.get()==false)
           {
            Double cost2 = Double.valueOf(temp14[temp14.length-2]);
           
           Double time2 = Double.valueOf(temp14[temp14.length-3]);
           cost[inst] = cost[inst] + cost2; //cost of the entire path is calculated in a step by step manner
           }
           //time[inst] = time[inst] + time2;
           //time[inst] = current_time[inst];
           time[inst] = getDuration(end);
           operations.remove(key);//operation is removed from the list of operations as it is no longer available
           if(available.contains("i18"))//if A has been produced then the execution is completed
           {
             
              
              System.out.println(choice + " choice");
               String filename = heuristics[choice] + ".txt";
               success[inst][choice] +=1;
               double roundOff = Math.round(cost[inst] * 100.0) / 100.0;
               double roundOff2 = Math.round(time[inst] * 100.0) / 100.0;
               //bw[choice].write(roundOff+","+roundOff2);
              // bw[choice].newLine();
               sum[inst][choice] += cost[inst];
               sum2[inst][choice] += time[inst];
               if(time[inst]==0 || cost[inst]==0)
               {
                   debug++;
               }
               System.out.println("Heuristic: " + heuristics[choice] + " instance: " + inst + "cost: " + cost[inst] + "     time: " + time[inst]);
               System.out.println("");
               
               
               complete_instance[inst] = true; 
               break;
           }
           
            
           
           listOfEnd.put(inst,end);
         listOfStarts.put(inst,start);
         listOfAllocations.put(inst,allocation);
         
         listOfAvailables.put(inst,available);
         listOfPaths.put(inst,path);
         listOfPrunes.put(inst,prune);
         listOfTimes.put(inst,times);
         min_index.put(inst,temp_inst);
         listOfMaps.put(choice+(inst*choice_number),operations);
         if(execution_mode==0)
         {
           if(executable.isEmpty())
           {
             next = true;
           }  
         
           if(next)
           {
            inst++;
           }
         }  
           
           
            
           
            
        }  
        }
        
        choice++;
        System.out.println();
        
        }//choice
        System.out.println();
        System.out.println();
        y++;
    
    }
      
        double result = 0.0;
        double result2 =0.0;
        double result3= 0.0;
        System.out.println("Sum of average cost and time for each heuristic for each instance:");
      
        for(int i=0;i<choice_number;i++)
        {
         for(int t=0;t<instances;t++)
         {
          result += (double) sum[t][i]/ (double)success[t][i]; //average execution cost
          result2 += (double) sum2[t][i]/ (double)success[t][i];
         }
          System.out.println(heuristics[i] +" cost: " + result +  "  time: " +result2);
          result = result2 = result3 = 0.0;
         }
     
      System.out.println();
      System.out.println();
      
    
      
     
     }
     else
     {
         y=0;
        int s=10;
        while(s !=0 && s != 1)
        {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please choose distribution for generation of the operations' attributes.");
        System.out.println("Enter 0 for uniform distribution or 1 for gaussian distribution. ");
        try{
        s = scan.nextInt();
        } catch(InputMismatchException e){
            System.out.println("Unsupported input, please enter either 0 or 1.");
        }
        }
        double[] sum = new double[13];
        double[] sum2 = new double[13];
        int[] success = new int [13];
        int[] failure = new int [13];
        int[] early = new int[13];
       
        int choice_number = 13;
       
        int debug=0;
        PrintWriter[] pw = new PrintWriter[choice_number];
        
        String[] heuristics = {"Random","Lowest-Cost","Shortest-Time","Lowest-Fail-Probability","Root-Distance","Cost-Distance","Time-Distance","Rank-based","Rank-based-Time","Rank-based-Combination","Rank-based extended","Rank-Based-extended-Time","Rank-Based-extended-Combo"};
        BufferedWriter[] bw= new BufferedWriter[13];
        while(y<choice_number)
        {
           String filename = heuristics[y] + ".txt";
           pw[y] = new PrintWriter(new FileWriter(filename));
           
           y++;
        }
        y=0;
        while(y<1000)
        {
        double cost =0;
        double time=0;
        double min =0.001, max =1.0;
        int minInt=1, maxInt=10;
        Random r = new Random();
        RandomGaussian gaussian = new RandomGaussian();
        double result, random2;
        HashMap<Integer, String[]> operations = new HashMap();
        if(s==0)
        {
         String[] temp = {"i25","i27","i01","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp[temp.length-1] = String.valueOf(random2);
        temp[temp.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp[temp.length-3] = String.valueOf(result);
        operations.put(1,temp);
        String[] temp2 = {"i25","i37","i02","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp2[temp2.length-1] = String.valueOf(random2);
        temp2[temp2.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp2[temp2.length-3] = String.valueOf(result);
        operations.put(2,temp2);
        String[] temp3 = {"i33","i37","i03","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp3[temp3.length-1] = String.valueOf(random2);
        temp3[temp3.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp3[temp3.length-3] = String.valueOf(result);
        operations.put(3,temp3);
        String[] temp4 = {"i33","i37","i04","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        //random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp4[temp4.length-1] = String.valueOf(random2);
        temp4[temp4.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp4[temp4.length-3] = String.valueOf(result);
        operations.put(4,temp4);
        String[] temp5 = {"i37","i45","i05","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp5[temp5.length-1] = String.valueOf(random2);
        temp5[temp5.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp5[temp5.length-3] = String.valueOf(result);
        operations.put(5,temp5);
        String[] temp6 = {"i21","i37","i06","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp6[temp6.length-1] = String.valueOf(random2);
        temp6[temp6.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp6[temp6.length-3] = String.valueOf(result);
        operations.put(6,temp6);
        String[] temp7 = {"i24","i37","i07","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp7[temp7.length-1] = String.valueOf(random2);
        temp7[temp7.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp7[temp7.length-3] = String.valueOf(result);
        operations.put(7,temp7);
        String[] temp8 = {"i23","i37","i08","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp8[temp8.length-1] = String.valueOf(random2);
        temp8[temp8.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp8[temp8.length-3] = String.valueOf(result);
        operations.put(8,temp8);
        String[] temp9 = {"i24","i39","i09","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp9[temp9.length-1] = String.valueOf(random2);
        temp9[temp9.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp9[temp9.length-3] = String.valueOf(result);
        operations.put(9,temp9);
        String[] temp10 = {"i13","i14","i34","i37","i42","i10","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp10[temp10.length-1] = String.valueOf(random2);
        temp10[temp10.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp10[temp10.length-3] = String.valueOf(result);
        operations.put(10,temp10);
        String[] temp111 = {"i31","i11","0","60","0.85"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp111[temp111.length-1] = String.valueOf(random2);
        temp111[temp111.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp111[temp111.length-3] = String.valueOf(result);
        operations.put(11,temp111);
        String[] temp112 = {"i16","i15","0","0","0.997"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp112[temp112.length-1] = String.valueOf(random2);
        temp112[temp112.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp112[temp112.length-3] = String.valueOf(result);
        operations.put(12,temp112);
        String[] temp113 = {"i17","i15","0","0","0.003"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp113[temp113.length-1] = String.valueOf(random2);
        temp113[temp113.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp113[temp113.length-3] = String.valueOf(result);
        operations.put(13,temp113);
        String[] temp114 = {"i16","i17","i51","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp114[temp114.length-1] = String.valueOf(random2);
        temp114[temp114.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp114[temp114.length-3] = String.valueOf(result);
        operations.put(14,temp114);
        String[] temp115 = {"i25","i30","i35","i36","i44","i16","0","561","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp115[temp115.length-1] = String.valueOf(random2);
        temp115[temp115.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp115[temp115.length-3] = String.valueOf(result);
        operations.put(15,temp115);
        String[] temp116 = {"i25","i30","i17","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp116[temp116.length-1] = String.valueOf(random2);
        temp116[temp116.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp116[temp116.length-3] = String.valueOf(result);
        operations.put(16,temp116);
        String[] temp117 = {"i01","i18","0","0","0.009"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp117[temp117.length-1] = String.valueOf(random2);
        temp117[temp117.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp117[temp117.length-3] = String.valueOf(result);
        operations.put(17,temp117);
        String[] temp118 = {"i02","i18","0","0","0.013"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp118[temp118.length-1] = String.valueOf(random2);
        temp118[temp118.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp118[temp118.length-3] = String.valueOf(result);
        operations.put(18,temp118);
        String[] temp119 = {"i08","i18","0","0","0.016"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp119[temp119.length-1] = String.valueOf(random2);
        temp119[temp119.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp119[temp119.length-3] = String.valueOf(result);
        operations.put(19,temp119);
        String[] temp120 = {"i09","i18","0","0","0.002"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp120[temp120.length-1] = String.valueOf(random2);
        temp120[temp120.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp120[temp120.length-3] = String.valueOf(result);
        operations.put(20,temp120);
        String[] temp121 = {"i10","i18","0","0","0.068"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp121[temp121.length-1] = String.valueOf(random2);
        temp121[temp121.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp121[temp121.length-3] = String.valueOf(result);
        operations.put(21,temp121);
        String[] temp122 = {"i11","i18","0","0","0.079"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp122[temp122.length-1] = String.valueOf(random2);
        temp122[temp122.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp122[temp122.length-3] = String.valueOf(result);
        operations.put(22,temp122);
        String[] temp123 = {"i15","i18","0","0","0.21"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp123[temp123.length-1] = String.valueOf(random2);
        temp123[temp123.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp123[temp123.length-3] = String.valueOf(result);
        operations.put(23,temp123);
        String[] temp124 = {"i09","i11","i15","i50","0","0","1"};
        operations.put(24,temp124);
        String[] temp125 = {"i25","i37","i28","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp125[temp125.length-1] = String.valueOf(random2);
        temp125[temp125.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp125[temp125.length-3] = String.valueOf(result);
        operations.put(25,temp125);
        String[] temp126 = {"i25","i30","i35","i36","i29","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp126[temp126.length-1] = String.valueOf(random2);
        temp126[temp126.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp126[temp126.length-3] = String.valueOf(result);
        operations.put(26,temp126);
        String[] temp127 = {"i32","i37","i43","i30","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp127[temp127.length-1] = String.valueOf(random2);
        temp127[temp127.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp127[temp127.length-3] = String.valueOf(result);
        operations.put(27,temp127);
        String[] temp128 = {"i29","i40","i48","i31","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp128[temp128.length-1] = String.valueOf(random2);
        temp128[temp128.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp128[temp128.length-3] = String.valueOf(result);
        operations.put(28,temp128);
        String[] temp129 = {"i01","i02","i03","i04","i05","i06","i07","i08","i10","i27","i28","i32","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp129[temp129.length-1] = String.valueOf(random2);
        temp129[temp129.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp129[temp129.length-3] = String.valueOf(result);
        operations.put(29,temp129);
        String[] temp130 = {"i36","i37","i41","i34","0","420","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp130[temp130.length-1] = String.valueOf(random2);
        temp130[temp130.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp130[temp130.length-3] = String.valueOf(result);
        operations.put(30,temp130);
        String[] temp131 = {"i39","i41","i40","0","30","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp131[temp131.length-1] = String.valueOf(random2);
        temp131[temp131.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp131[temp131.length-3] = String.valueOf(result);
        operations.put(31,temp131);
        String[] temp132 = {"i47","i42","0","30","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp132[temp132.length-1] = String.valueOf(random2);
        temp132[temp132.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp132[temp132.length-3] = String.valueOf(result);
        operations.put(32,temp132);
        String[] temp133 = {"i39","i49","i43","0","60","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp133[temp133.length-1] = String.valueOf(random2);
        temp133[temp133.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp133[temp133.length-3] = String.valueOf(result);
        operations.put(33,temp133);
        String[] temp134 = {"i13","0","8","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp134[temp134.length-1] = String.valueOf(random2);
        temp134[temp134.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp134[temp134.length-3] = String.valueOf(result);
        operations.put(34,temp134);
        String[] temp135 = {"i14","0","8","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp135[temp135.length-1] = String.valueOf(random2);
        temp135[temp135.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp135[temp135.length-3] = String.valueOf(result);
        operations.put(35,temp135);
        String[] temp136 = {"i21","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp136[temp136.length-1] = String.valueOf(random2);
        temp136[temp136.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp136[temp136.length-3] = String.valueOf(result);
        operations.put(36,temp136);
        String[] temp137 = {"i23","0","67","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp137[temp137.length-1] = String.valueOf(random2);
        temp137[temp137.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp137[temp137.length-3] = String.valueOf(result);
        operations.put(37,temp137);
        String[] temp138 = {"i24","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp138[temp138.length-1] = String.valueOf(random2);
        temp138[temp138.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp138[temp138.length-3] = String.valueOf(result);
        operations.put(38,temp138);
        String[] temp139 = {"i25","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp139[temp139.length-1] = String.valueOf(random2);
        temp139[temp139.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp139[temp139.length-3] = String.valueOf(result);
        operations.put(39,temp139);
        String[] temp140 = {"i27","0","8","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp140[temp140.length-1] = String.valueOf(random2);
        temp140[temp140.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp140[temp140.length-3] = String.valueOf(result);
        operations.put(40,temp140);
        String[] temp141 = {"i33","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp141[temp141.length-1] = String.valueOf(random2);
        temp141[temp141.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp141[temp141.length-3] = String.valueOf(result);
        operations.put(41,temp141);
        String[] temp142 = {"i35","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp142[temp142.length-1] = String.valueOf(random2);
        temp142[temp142.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp142[temp142.length-3] = String.valueOf(result);
        operations.put(42,temp142);
        String[] temp143 = {"i36","0","1","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp143[temp143.length-1] = String.valueOf(random2);
        temp143[temp143.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp143[temp143.length-3] = String.valueOf(result);
        operations.put(43,temp143);
        String[] temp144 = {"i37","0","167","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp144[temp144.length-1] = String.valueOf(random2);
        temp144[temp144.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp144[temp144.length-3] = String.valueOf(result);
        operations.put(44,temp144);
        String[] temp145 = {"i39","0","17","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp145[temp145.length-1] = String.valueOf(random2);
        temp145[temp145.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp145[temp145.length-3] = String.valueOf(result);
        operations.put(45,temp145);
        String[] temp146 = {"i41","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp146[temp146.length-1] = String.valueOf(random2);
        temp146[temp146.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp146[temp146.length-3] = String.valueOf(result);
        operations.put(46,temp146);
        String[] temp147 = {"i44","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp147[temp147.length-1] = String.valueOf(random2);
        temp147[temp147.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp147[temp147.length-3] = String.valueOf(result);
        operations.put(47,temp147);
        String[] temp148 = {"i45","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp148[temp148.length-1] = String.valueOf(random2);
        temp148[temp148.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp148[temp148.length-3] = String.valueOf(result);
        operations.put(48,temp148);
        String[] temp149 = {"i47","0","33","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp149[temp149.length-1] = String.valueOf(random2);
        temp149[temp149.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp149[temp149.length-3] = String.valueOf(result);
        operations.put(49,temp149);
        String[] temp150 = {"i48","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp150[temp150.length-1] = String.valueOf(random2);
        temp150[temp150.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp150[temp150.length-3] = String.valueOf(result);
        operations.put(50,temp150);
        String[] temp151 = {"i49","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp151[temp151.length-1] = String.valueOf(random2);
        temp151[temp151.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp151[temp151.length-3] = String.valueOf(result);
        operations.put(51,temp151);
        String[] temp152 = {"i50","i18","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp152[temp152.length-1] = String.valueOf(random2);
        temp152[temp152.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp152[temp152.length-3] = String.valueOf(result);
        operations.put(52,temp152);
        String[] temp153 = {"i51","i15","0","0","1"};
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        random2 = ThreadLocalRandom.current().nextDouble(min, max);
        temp153[temp153.length-1] = String.valueOf(random2);
        temp153[temp153.length-2] = String.valueOf(result);
        result = r.nextInt((maxInt - minInt) + 1) + minInt;
        temp153[temp153.length-3] = String.valueOf(result);
        operations.put(53,temp153);
        }
        else
        {
          String[] temp = {"i25","i27","i01","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp[temp.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp[temp.length-1])));
          temp[temp.length-1] = String.valueOf(random2);
          temp[temp.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp[temp.length-3]));
          temp[temp.length-3] = String.valueOf(result);
          operations.put(1,temp);
          String[] temp2 = {"i25","i37","i02","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp2[temp2.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp2[temp2.length-1])));
          temp2[temp2.length-1] = String.valueOf(random2);
          temp2[temp2.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp2[temp2.length-3]));
          temp2[temp2.length-3] = String.valueOf(result);
          operations.put(2,temp2);
          String[] temp3 = {"i33","i37","i03","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp3[temp3.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp3[temp3.length-1])));
          temp3[temp3.length-1] = String.valueOf(random2);
          temp3[temp3.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp3[temp3.length-3]));
          temp3[temp3.length-3] = String.valueOf(result);
          operations.put(3,temp3);
          String[] temp4 = {"i33","i37","i04","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp4[temp4.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp4[temp4.length-1])));
          temp4[temp4.length-1] = String.valueOf(random2);
          temp4[temp4.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp4[temp4.length-3]));
          temp4[temp4.length-3] = String.valueOf(result);
          operations.put(4,temp4);
          String[] temp5 = {"i37","i45","i05","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp5[temp5.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp5[temp5.length-1])));
          temp5[temp5.length-1] = String.valueOf(random2);
          temp5[temp5.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp5[temp5.length-3]));
          temp5[temp5.length-3] = String.valueOf(result);
          operations.put(5,temp5);
          String[] temp6 = {"i21","i37","i06","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp6[temp6.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp6[temp6.length-1])));
          temp6[temp6.length-1] = String.valueOf(random2);
          temp6[temp6.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp6[temp6.length-3]));
          temp6[temp6.length-3] = String.valueOf(result);
          operations.put(6,temp6);
          String[] temp7 = {"i24","i37","i07","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp7[temp7.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp7[temp7.length-1])));
          temp7[temp7.length-1] = String.valueOf(random2);
          temp7[temp7.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp7[temp7.length-3]));
          temp7[temp7.length-3] = String.valueOf(result);
          operations.put(7,temp7);
          String[] temp8 = {"i23","i37","i08","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp8[temp8.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp8[temp8.length-1])));
          temp8[temp8.length-1] = String.valueOf(random2);
          temp8[temp8.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp8[temp8.length-3]));
          temp8[temp8.length-3] = String.valueOf(result);
          operations.put(8,temp8);
          String[] temp9 = {"i24","i39","i09","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp9[temp9.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp9[temp9.length-1])));
          temp9[temp9.length-1] = String.valueOf(random2);
          temp9[temp9.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp9[temp9.length-3]));
          temp9[temp9.length-3] = String.valueOf(result);
          operations.put(9,temp9);
          String[] temp10 = {"i13","i14","i34","i37","i42","i10","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp10[temp10.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp10[temp10.length-1])));
          temp10[temp10.length-1] = String.valueOf(random2);
          temp10[temp10.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp10[temp10.length-3]));
          temp10[temp10.length-3] = String.valueOf(result);
          operations.put(10,temp10);
          String[] temp111 = {"i31","i11","0.6","0.6","0.85"};
          result = gaussian.getRandInt(Double.parseDouble(temp111[temp111.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp111[temp111.length-1])));
          temp111[temp111.length-1] = String.valueOf(random2);
          temp111[temp111.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp111[temp111.length-3]));
          temp111[temp111.length-3] = String.valueOf(result);
          operations.put(11,temp111);
          String[] temp112 = {"i16","i15","0","0","0.997"};
          result = gaussian.getRandInt(Double.parseDouble(temp112[temp112.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp112[temp112.length-1])));
          temp112[temp112.length-1] = String.valueOf(random2);
          temp112[temp112.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp112[temp112.length-3]));
          temp112[temp112.length-3] = String.valueOf(result);
          operations.put(12,temp112);
          String[] temp113 = {"i17","i15","0","0","0.003"};
          result = gaussian.getRandInt(Double.parseDouble(temp113[temp113.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp113[temp113.length-1])));
          temp113[temp113.length-1] = String.valueOf(random2);
          temp113[temp113.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp113[temp113.length-3]));
          temp113[temp113.length-3] = String.valueOf(result);
          operations.put(13,temp113);
          String[] temp114 = {"i16","i17","i51","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp114[temp114.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp114[temp114.length-1])));
          temp114[temp114.length-1] = String.valueOf(random2);
          temp114[temp114.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp114[temp114.length-3]));
          temp114[temp114.length-3] = String.valueOf(result);
          operations.put(14,temp114);
          String[] temp115 = {"i25","i30","i35","i36","i44","i16","5.61","5.61","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp115[temp115.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp115[temp115.length-1])));
          temp115[temp115.length-1] = String.valueOf(random2);
          temp115[temp115.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp115[temp115.length-3]));
          temp115[temp115.length-3] = String.valueOf(result);
          operations.put(15,temp115);
          String[] temp116 = {"i25","i30","i17","6.1","6.1","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp116[temp116.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp116[temp116.length-1])));
          temp116[temp116.length-1] = String.valueOf(random2);
          temp116[temp116.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp116[temp116.length-3]));
          temp116[temp116.length-3] = String.valueOf(result);
          operations.put(16,temp116);
          String[] temp117 = {"i01","i18","0","0","0.009"};
          result = gaussian.getRandInt(Double.parseDouble(temp117[temp117.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp117[temp117.length-1])));
          temp117[temp117.length-1] = String.valueOf(random2);
          temp117[temp117.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp117[temp117.length-3]));
          temp117[temp117.length-3] = String.valueOf(result);
          operations.put(17,temp117);
          String[] temp118 = {"i02","i18","0","0","0.013"};
          result = gaussian.getRandInt(Double.parseDouble(temp118[temp118.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp118[temp118.length-1])));
          temp118[temp118.length-1] = String.valueOf(random2);
          temp118[temp118.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp118[temp118.length-3]));
          temp118[temp118.length-3] = String.valueOf(result);
          operations.put(18,temp118);
          String[] temp119 = {"i08","i18","0","0","0.016"};
          result = gaussian.getRandInt(Double.parseDouble(temp119[temp119.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp119[temp119.length-1])));
          temp119[temp119.length-1] = String.valueOf(random2);
          temp119[temp119.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp119[temp119.length-3]));
          temp119[temp119.length-3] = String.valueOf(result);
          operations.put(19,temp119);
          String[] temp120 = {"i09","i18","0","0","0.002"};
          result = gaussian.getRandInt(Double.parseDouble(temp120[temp120.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp120[temp120.length-1])));
          temp120[temp120.length-1] = String.valueOf(random2);
          temp120[temp120.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp120[temp120.length-3]));
          temp120[temp120.length-3] = String.valueOf(result);
          operations.put(20,temp120);
          String[] temp121 = {"i10","i18","0","0","0.068"};
          result = gaussian.getRandInt(Double.parseDouble(temp121[temp121.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp121[temp121.length-1])));
          temp121[temp121.length-1] = String.valueOf(random2);
          temp121[temp121.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp121[temp121.length-3]));
          temp121[temp121.length-3] = String.valueOf(result);
          operations.put(21,temp121);
          String[] temp122 = {"i11","i18","0","0","0.079"};
          result = gaussian.getRandInt(Double.parseDouble(temp122[temp122.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp122[temp122.length-1])));
          temp122[temp122.length-1] = String.valueOf(random2);
          temp122[temp122.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp122[temp122.length-3]));
          temp122[temp122.length-3] = String.valueOf(result);
          operations.put(22,temp122);
          String[] temp123 = {"i15","i18","0","0","0.21"};
          result = gaussian.getRandInt(Double.parseDouble(temp123[temp123.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp123[temp123.length-1])));
          temp123[temp123.length-1] = String.valueOf(random2);
          temp123[temp123.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp123[temp123.length-3]));
          temp123[temp123.length-3] = String.valueOf(result);
          operations.put(23,temp123);
          String[] temp124 = {"i09","i11","i15","i50","0","0","1"};
          operations.put(24,temp124);
          String[] temp125 = {"i25","i37","i28","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp125[temp125.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp125[temp125.length-1])));
          temp125[temp125.length-1] = String.valueOf(random2);
          temp125[temp125.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp125[temp125.length-3]));
          temp125[temp125.length-3] = String.valueOf(result);
          operations.put(25,temp125);
          String[] temp126 = {"i25","i30","i35","i36","i29","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp126[temp126.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp126[temp126.length-1])));
          temp126[temp126.length-1] = String.valueOf(random2);
          temp126[temp126.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp[temp126.length-3]));
          temp126[temp126.length-3] = String.valueOf(result);
          operations.put(26,temp126);
          String[] temp127 = {"i32","i37","i43","i30","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp127[temp127.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp127[temp127.length-1])));
          temp127[temp127.length-1] = String.valueOf(random2);
          temp127[temp127.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp127[temp127.length-3]));
          temp127[temp127.length-3] = String.valueOf(result);
          operations.put(27,temp127);
          String[] temp128 = {"i29","i40","i48","i31","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp128[temp128.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp128[temp128.length-1])));
          temp128[temp128.length-1] = String.valueOf(random2);
          temp128[temp128.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp128[temp128.length-3]));
          temp128[temp128.length-3] = String.valueOf(result);
          operations.put(28,temp128);
          String[] temp129 = {"i01","i02","i03","i04","i05","i06","i07","i08","i10","i27","i28","i32","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp129[temp129.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp129[temp129.length-1])));
          temp129[temp129.length-1] = String.valueOf(random2);
          temp129[temp129.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp129[temp129.length-3]));
          temp129[temp129.length-3] = String.valueOf(result);
          operations.put(29,temp129);
          String[] temp130 = {"i36","i37","i41","i34","4.2","4.2","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp130[temp130.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp130[temp130.length-1])));
          temp130[temp130.length-1] = String.valueOf(random2);
          temp130[temp130.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp130[temp130.length-3]));
          temp130[temp130.length-3] = String.valueOf(result);
          operations.put(30,temp130);
          String[] temp131 = {"i39","i41","i40","0.30","0.30","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp131[temp131.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp131[temp131.length-1])));
          temp131[temp131.length-1] = String.valueOf(random2);
          temp131[temp131.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp131[temp131.length-3]));
          temp131[temp131.length-3] = String.valueOf(result);
          operations.put(31,temp131);
          String[] temp132 = {"i47","i42","0.30","0.30","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp132[temp132.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp132[temp132.length-1])));
          temp132[temp132.length-1] = String.valueOf(random2);
          temp132[temp132.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp132[temp132.length-3]));
          temp132[temp132.length-3] = String.valueOf(result);
          operations.put(32,temp132);
          String[] temp133 = {"i39","i49","i43","0.60","0.60","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp133[temp133.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp133[temp133.length-1])));
          temp133[temp133.length-1] = String.valueOf(random2);
          temp133[temp133.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp133[temp133.length-3]));
          temp133[temp133.length-3] = String.valueOf(result);
          operations.put(33,temp133);
          String[] temp134 = {"i13","0.08","0.08","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp134[temp134.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp134[temp134.length-1])));
          temp134[temp134.length-1] = String.valueOf(random2);
          temp134[temp134.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp134[temp134.length-3]));
          temp134[temp134.length-3] = String.valueOf(result);
          operations.put(34,temp134);
          String[] temp135 = {"i14","0.08","0.08","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp135[temp135.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp135[temp135.length-1])));
          temp135[temp135.length-1] = String.valueOf(random2);
          temp135[temp135.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp135[temp135.length-3]));
          temp135[temp135.length-3] = String.valueOf(result);
          operations.put(35,temp135);
          String[] temp136 = {"i21","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp136[temp136.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp136[temp136.length-1])));
          temp136[temp136.length-1] = String.valueOf(random2);
          temp136[temp136.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp136[temp136.length-3]));
          temp136[temp136.length-3] = String.valueOf(result);
          operations.put(36,temp136);
          String[] temp137 = {"i23","0.67","0.67","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp137[temp137.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp137[temp137.length-1])));
          temp137[temp137.length-1] = String.valueOf(random2);
          temp137[temp137.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp137[temp137.length-3]));
          temp137[temp137.length-3] = String.valueOf(result);
          operations.put(37,temp137);
          String[] temp138 = {"i24","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp138[temp138.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp138[temp138.length-1])));
          temp138[temp138.length-1] = String.valueOf(random2);
          temp138[temp138.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp138[temp138.length-3]));
          temp138[temp138.length-3] = String.valueOf(result);
          operations.put(38,temp138);
          String[] temp139 = {"i25","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp139[temp139.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp139[temp139.length-1])));
          temp139[temp139.length-1] = String.valueOf(random2);
          temp139[temp139.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp139[temp139.length-3]));
          temp139[temp139.length-3] = String.valueOf(result);
          operations.put(39,temp139);
          String[] temp140 = {"i27","0.08","0.08","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp140[temp140.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp140[temp140.length-1])));
          temp140[temp140.length-1] = String.valueOf(random2);
          temp140[temp140.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp140[temp140.length-3]));
          temp140[temp140.length-3] = String.valueOf(result);
          operations.put(40,temp140);
          String[] temp141 = {"i33","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp141[temp141.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp141[temp141.length-1])));
          temp141[temp141.length-1] = String.valueOf(random2);
          temp141[temp141.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp141[temp141.length-3]));
          temp141[temp141.length-3] = String.valueOf(result);
          operations.put(41,temp141);
          String[] temp142 = {"i35","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp142[temp142.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp142[temp142.length-1])));
          temp142[temp142.length-1] = String.valueOf(random2);
          temp142[temp142.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp142[temp142.length-3]));
          temp142[temp142.length-3] = String.valueOf(result);
          operations.put(42,temp142);
          String[] temp143 = {"i36","1","1","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp143[temp143.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp143[temp143.length-1])));
          temp143[temp143.length-1] = String.valueOf(random2);
          temp143[temp143.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp143[temp143.length-3]));
          temp143[temp143.length-3] = String.valueOf(result);
          operations.put(43,temp143);
          String[] temp144 = {"i37","1.67","1.67","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp144[temp144.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp144[temp144.length-1])));
          temp144[temp144.length-1] = String.valueOf(random2);
          temp144[temp144.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp144[temp144.length-3]));
          temp144[temp144.length-3] = String.valueOf(result);
          operations.put(44,temp144);
          String[] temp145 = {"i39","0.17","0.17","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp145[temp145.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp145[temp145.length-1])));
          temp145[temp145.length-1] = String.valueOf(random2);
          temp145[temp145.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp145[temp145.length-3]));
          temp145[temp145.length-3] = String.valueOf(result);
          operations.put(45,temp145);
          String[] temp146 = {"i41","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp146[temp146.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp146[temp146.length-1])));
          temp146[temp146.length-1] = String.valueOf(random2);
          temp146[temp146.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp146[temp146.length-3]));
          temp146[temp146.length-3] = String.valueOf(result);
          operations.put(46,temp146);
          String[] temp147 = {"i44","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp147[temp147.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp147[temp147.length-1])));
          temp147[temp147.length-1] = String.valueOf(random2);
          temp147[temp147.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp147[temp147.length-3]));
          temp147[temp147.length-3] = String.valueOf(result);
          operations.put(47,temp147);
          String[] temp148 = {"i45","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp148[temp148.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp148[temp148.length-1])));
          temp148[temp148.length-1] = String.valueOf(random2);
          temp148[temp148.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp148[temp148.length-3]));
          temp148[temp148.length-3] = String.valueOf(result);
          operations.put(48,temp148);
          String[] temp149 = {"i47","0.33","0.33","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp149[temp149.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp149[temp149.length-1])));
          temp149[temp149.length-1] = String.valueOf(random2);
          temp149[temp149.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp149[temp149.length-3]));
          temp149[temp149.length-3] = String.valueOf(result);
          operations.put(49,temp149);
          String[] temp150 = {"i48","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp150[temp150.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp150[temp150.length-1])));
          temp150[temp150.length-1] = String.valueOf(random2);
          temp150[temp150.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp150[temp150.length-3]));
          temp150[temp150.length-3] = String.valueOf(result);
          operations.put(50,temp150);
          String[] temp151 = {"i49","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp151[temp151.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp151[temp151.length-1])));
          temp151[temp151.length-1] = String.valueOf(random2);
          temp151[temp151.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp151[temp151.length-3]));
          temp151[temp151.length-3] = String.valueOf(result);
          operations.put(51,temp151);
          String[] temp152 = {"i50","i18","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp152[temp152.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp152[temp152.length-1])));
          temp152[temp152.length-1] = String.valueOf(random2);
          temp152[temp152.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp152[temp152.length-3]));
          temp152[temp152.length-3] = String.valueOf(result);
          operations.put(52,temp152);
          String[] temp153 = {"i51","i15","0","0","1"};
          result = gaussian.getRandInt(Double.parseDouble(temp153[temp153.length-2]));
          random2 = gaussian.getRandProb(1-(Double.parseDouble(temp153[temp153.length-1])));
          temp153[temp153.length-1] = String.valueOf(random2);
          temp153[temp153.length-2] = String.valueOf(result);
          result = gaussian.getRandInt(Double.parseDouble(temp153[temp153.length-3]));
          temp153[temp153.length-3] = String.valueOf(result);
          operations.put(53,temp153);
        }
        HashMap<Integer,String[]> operations_graph = (HashMap) operations.clone();
        Iterator print = operations.entrySet().iterator();
        
        while(print.hasNext())
        {
            HashMap.Entry entryprint = (HashMap.Entry) print.next();
            String[] printtemp = (String[]) entryprint.getValue();
            //System.out.println(printtemp[printtemp.length-3] + "     cost " + printtemp[printtemp.length-2] + "      prob" + printtemp[printtemp.length-1]);
        }
        
        
        int count = 10;
         List<Map<Integer, String[]>> listOfMaps = new ArrayList<Map<Integer, String[]>>();
        int u=0;
        int choice =0;
        while(u<choice_number)
        {
           listOfMaps.add((HashMap) operations.clone());
           u++;
           
        }
        HashMap<Integer,Integer> productions = new HashMap<Integer,Integer>();
        determineExecutionInstance(operations,productions);
        double[][] graph_time = new double[52][52];
        createArrayTimeBaseline(operations,graph_time);
        double[][] graph_cost = new double [52][52];
        createArrayCostBaseline(operations,graph_cost);
        int[][] graph_root = new int[52][52];
        createArrayRootBaseline(operations,graph_root);
        while(choice<choice_number)
        {
        cost =0;
        time=0;
        HashMap<Integer,String[]> executable = new HashMap();
        
        HashSet<String> available = new HashSet();
        HashSet<Integer> path = new HashSet();
        operations = (HashMap)listOfMaps.get(choice);
        HashSet<Integer> prune = new HashSet<Integer>();
        HashMap<String,Integer> failures = new HashMap();
        while(count !=0)
        {
           Iterator it = operations.entrySet().iterator();
           while (it.hasNext()) //calculate executable operations
           {
            
            HashMap.Entry pair = (HashMap.Entry)it.next();
            String[] temp11 = (String[])pair.getValue();
            if(temp11.length==4) //cases where operation has no input elements
            {
                executable.put((Integer)pair.getKey(),(String[]) pair.getValue());
            }
            else //cases where operation has input elements. so each input element is checked for availability
            {
              int temp12 = 0;
              for(int i=0;i<temp11.length-4;i++)
              {
                if(available.contains(temp11[i]))//each input is checked
                {
                    temp12++;
                    
                }
              }
              if(temp12 == temp11.length -4)//if every input is available then the operation is available for execution
              {
                  executable.put((Integer)pair.getKey(),(String[]) pair.getValue());
              }
            }
            //it.remove(); // avoids a ConcurrentModificationException
            
            }
           if(choice==10 || choice == 11 || choice ==12)
           {
               if(prune.size()>0)
               {
                   
                   for(Integer op : prune)
                   {
                       executable.remove(op);
                   }
               }
           }
           
           if(executable.size()==0)//execution has completed since the production of A is no longer possible
           {
               System.out.println("Heuristic: " + heuristics[choice] + "  cost: " + cost + "     time: " + time);
               System.out.println("");
               String filename = heuristics[choice] + ".txt";
               failure[choice]+=1;
               //bw[choice].write((y+1)+","+cost+","+time);
               //bw[choice].newLine();
               //sum[choice] += cost;
               //sum2[choice] +=time;
               if(time==0||cost==0)
               {
                   debug++;
               }
               break;
           }
           int key;
           if(choice==0)
           {
               key=executeRandom(executable,available,operations,productions,path);
               
           }
           else if(choice==1)
           {
               key=executeLowestCost(executable,available,operations_graph,productions,path);
           }
           else if(choice==2)
           {
               key=executeShortestTime(executable,available,operations_graph,productions,path);
           }
           else if(choice==3)
           {
               key=executeLowestFail(executable,available,operations_graph,productions,path);
               
           }
           else if(choice==4)
           {
               key=rootDistance(executable,available,operations_graph,graph_root,productions,path);
           }
           else if(choice==5)
           {
               key=costDistance(executable,available,operations_graph,graph_cost,productions,path);
           }
           else if(choice==6)
           {
               key=timeDistance(executable,available,operations_graph,graph_time,productions,path);
           }
           else if(choice==7)
           {
               key=knockoutPathOld(executable,available,operations_graph,productions,path);
               
           }
           else if(choice==8)
           {
               key=knockoutPathTime(executable,available,operations_graph,productions,path);
               
           }
           else if(choice==9)
           {
              key=knockoutPathCombo(executable,available,operations_graph,productions,path);
           }
           else if(choice==10)
           {
               key=knockoutPathPruneBoth(executable,available,operations_graph,productions,prune,failures,path);
           }
           else if(choice==11)
           {
               key=knockoutPathPruneBothTime(executable,available,operations_graph,productions,prune,failures,path);
           }
           else
           {
               key=knockoutPathPruneBothCombo(executable,available,operations_graph,productions,prune,failures,path);
           }
               
           String[] temp16 = executable.get(key);
           int result_execution = productions.get(key);
           
           if(result_execution ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
           {
            
            available.add(temp16[temp16.length-4]);
            System.out.println("Heuristic: " + heuristics[choice] + " Op"  + key+ "," + temp16[temp16.length-4]  + "time: " + Double.parseDouble(temp16[temp16.length-3])  + "cost: " + Double.parseDouble(temp16[temp16.length-2]));
            path.add(key);
           }
           else //execution was unsuccessful and therefore the element was not produced
           {
            System.out.println("Heuristic: " + heuristics[choice] + " Op"  + key+ "  fail," + temp16[temp16.length-4]  + "time: " + Double.parseDouble(temp16[temp16.length-3])  + "cost: " + Double.parseDouble(temp16[temp16.length-2]));
            path.add(key);
           }
           
           
           String[] temp14 = operations.get(key);
           double cost2 = Double.valueOf(temp14[temp14.length-2]);
           cost = cost + cost2; //cost of the entire path is calculated in a step by step manner
           double time2 = Double.valueOf(temp14[temp14.length-3]);
           time = time + time2;
           operations.remove(key);//operation is removed from the list of operations as it is no longer available
           executable.remove(key);
           if(available.contains("i18"))//if A has been produced then the execution is completed
           {
               System.out.println("Heuristic: " + heuristics[choice] + "  cost: " + cost + "     time: " + time);
               System.out.println("");
               String filename = heuristics[choice] + ".txt";
               success[choice] +=1;
               double roundOff = Math.round(cost * 100.0) / 100.0;
               double roundOff2 = Math.round(time * 100.0) / 100.0;
               //bw[choice].write(roundOff+","+roundOff2);
              // bw[choice].newLine();
               sum[choice] += cost;
               sum2[choice] += time;
               if(time==0 || cost==0)
               {
                   debug++;
               }
               break;
           }
           
           
           
           
            
           
            
            
        }
        choice++;
        System.out.println();
        
        }//choice
        System.out.println();
        System.out.println();
        y++;
    
    }
      for(int u=0;u<choice_number;u++)
      {
      double result = (double) sum[u]/ (double)success[u]; //average execution cost
      double result2 = (double) sum2[u] /(double)success[u];
      double result3 = (result) /(double) 100;
      double result4 = (result2) / (double) 100;
      System.out.println(heuristics[u]+"    cost: " +result +"      time: " + result2 + " number of successful executions: " + success[u]);
      //bw[u].close();
      }
      
      for(int i=0;i<choice_number;i++)
      {
          //System.out.println(success[i]);
      }
     
     }
}

    
    private static int getRandomNumberInRange(int min, int max) {

		if (min > max)
                {
		   throw new IllegalArgumentException("max must be greater than min");
		}
                if(min==max)
                {
                    return 1;
                }

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
    
    private static int executeRandom(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions)//Random selection strategy
    {
        int next = getRandomNumberInRange(1,input.size()) -1;
        int count =0;
        int key=0;
        Iterator it2 = input.entrySet().iterator();
        
        while(it2.hasNext())
        {
           HashMap.Entry pair2 = (HashMap.Entry)it2.next();
           if(count==next)
           {
            key =  (Integer) pair2.getKey();
            String[] temp13 = (String []) pair2.getValue();
            /*
            //operations.remove(key);
            input.remove(key);
            path.add(key);
            double prob = Double.parseDouble(temp13[temp13.length-1]);
            int result = productions.get(key);
            if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
            {
                available.add(temp13[temp13.length-4]);
                //System.out.println("Op" + key+ "," + temp13[temp13.length-4]);
            }
            else //execution was unsuccessful and therefore the element was not produced
            {
                //System.out.println("Op" + key+ "," + temp13[temp13.length-4] + " fail");
            }
            
            */
            
            break;
           }
           
           count++;
                   
        }
        
        return key;
    }
    
     private static int executeRandom(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions, HashSet<Integer> path)//Random selection strategy
    {
        int next = getRandomNumberInRange(1,input.size()) -1;
        int count =0;
        int key=0;
        Iterator it2 = input.entrySet().iterator();
        
        while(it2.hasNext())
        {
           HashMap.Entry pair2 = (HashMap.Entry)it2.next();
           if(count==next)
           {
            key =  (Integer) pair2.getKey();
            /*String[] temp13 = (String []) pair2.getValue();
            
            operations.remove(key);
            input.remove(key);
            path.add(key);
            double prob = Double.parseDouble(temp13[temp13.length-1]);
            int result = productions.get(key);
            if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
            {
                available.add(temp13[temp13.length-4]);
                //System.out.println("Op" + key+ "," + temp13[temp13.length-4]);
            }
            else //execution was unsuccessful and therefore the element was not produced
            {
                //System.out.println("Op" + key+ "," + temp13[temp13.length-4] + " fail");
            }
            */
            
            
            break;
           }
           
           count++;
                   
        }
        
        return key;
    }
    
    private static int executeLowestCost(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions)//Lowest Cost selection strategy
    {
        int n = input.size();
        double[][] cost = new double [n][3];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();
          cost[i][0]= Double.valueOf((Integer)pair.getKey());
          String[] temp15= (String[]) pair.getValue();
          cost[i][1]= Double.parseDouble(temp15[temp15.length-2]);
          
          i++;
        }
        int key = (int) getMinValue(cost);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
    }
    
    private static int executeShortestTime(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations,HashMap<Integer,Integer> productions)
    {
        int n = input.size();
        double[][] cost = new double [n][3];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();
          cost[i][0]= (Integer) pair.getKey();
          String[] temp15= (String[]) pair.getValue();
          cost[i][1]= Double.parseDouble(temp15[temp15.length-3]);
          
          i++;
        }
        int key = (int)getMinValue(cost);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
    }
    
    private static int executeLowestFail(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions)
    {
        int n = input.size();
        double[][] cost = new double [n][3];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();
          cost[i][0]= (Integer) pair.getKey();
          String[] temp15= (String[]) pair.getValue();
          cost[i][1]= Double.parseDouble(temp15[temp15.length-1]);
          
          i++;
        }
        int key = (int)getMaxValue(cost);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
    }
    
    public static double getMinValue(double[][] numbers)
    {
                
        double minValue = numbers[0][1];
        int index=0;
        for(int i=1;i<numbers.length;i++)
        {
         if(numbers[i][1] < minValue)
           {
                minValue = numbers[i][1];
                index =i;
           }
		}
                
		return numbers[index][0];
	}
    
    public static int getMinIntValue(int[][] numbers)
    {
                
        int minValue = numbers[0][1];
        int index=0;
        for(int i=1;i<numbers.length;i++)
        {
         if(numbers[i][1] < minValue)
           {
                minValue = numbers[i][1];
                index =i;
           }
		}
                
		return numbers[index][0];
	}
    
    public static double getMaxValue(double[][] numbers)
    {
                
        double maxValue = numbers[0][1];
        int index=0;
        for(int i=1;i<numbers.length;i++)
        {
         if(numbers[i][1] > maxValue)
           {
                maxValue = numbers[i][1];
                index =i;
           }
		}
                
		return numbers[index][0];
	}
    
    public static int getMinValue2(int[][] numbers)
    {
                
        int minValue = numbers[0][1];
        int index=0;
        for(int i=1;i<numbers.length;i++)
        {
         if(numbers[i][1] < minValue)
           {
                minValue = numbers[i][1];
                index =i;
           }
		}
                
		return minValue;
	}
    
    private static int execution(double prob)// function that takes the input the success probability of a rule and executes it
    {
        Random r = new Random();
        double result = r.nextDouble();
        if(result>prob)
        {
          return 0; //failure
        }
        else
        {
          return 1;//success
        }
    }
    
     private static int executionOld(double prob)// function that takes the input the success probability of a rule and executes it
    {
        Random r = new Random();
        double result = r.nextDouble();
        if(result>prob)
        {
          return 0; //failure
        }
        else
        {
          return 1;//success
        }
    }
    
    private static int rootDistance(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations,int [][] graph, HashMap<Integer,Integer> productions)
    {
        
        
        
        
        ShortestPath s = new ShortestPath(); 
        int[] dist = s.dijkstra(graph, 18);
        
        
        int n = input.size();
        double[][] cost = new double [n][2];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          String tempstring = temp15[temp15.length-4].replace("i",""); //for formatting reasons the "i" prefix is removed so that the element key can be extracted
          int tempint = Integer.parseInt(tempstring);
          cost[i][1] = dist[tempint];  //in the case wherethere are >=1 input elements the one that has the biggest distance represents the distance
          
          
          i++;
        }
        int key = (int) getMinValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*
        //System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
    }
    
    private static int costDistance(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations,double [][] graph, HashMap<Integer,Integer> productions)
    {
        
        
        ShortestPathDouble s = new ShortestPathDouble(); 
        double[] dist = s.dijkstra(graph, 18);
        
        
        int n = input.size();
        double[][] cost = new double [n][2];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          String tempstring = temp15[temp15.length-4].replace("i",""); //for formatting reasons the "i" prefix is removed so that the element key can be extracted
          int tempint = Integer.parseInt(tempstring);
          cost[i][1] = dist[tempint];  //in the case wherethere are >=1 input elements the one that has the biggest distance represents the distance 
           
          
          
          
          
          i++;
        }
        int key = (int) getMinValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        /*
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
    }
    
    private static int costDistanceNew(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions)
    {
        double graph[][] = new double[52][52];
        double graph2[][] = new double [52][52];
        for(int l=0;l<52;l++)
        {
            for(int p=0;p<52;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[1].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                graph[two][one]=Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        double dist[] = new double[52];
        double distProb[] = new double[52];
        ShortestPathDouble tp = new ShortestPathDouble();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 18);
        distProb = tpp.dijkstra(graph2, 18);
        
        double[] dist2 = new double[dist.length];
        for(int f=1;f<dist.length;f++)
        {
            prune2(operations,f,graph,dist2);
        }
        for(int f=1;f<dist.length;f++)
        {
           dist[f] += dist2[f];
        }     
        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           String index = temp15[0];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator,numerator;
            if(graph[0][ind]!=-1)
           {
             denominator = dist[ind] + graph[0][ind];
           }
           else
           {
             denominator = dist[ind];  
           }
           if(graph2[0][ind]!=-1)
           {
             numerator = distProb[ind] * (1-graph2[0][ind]);
           }
           else
           {
             numerator = distProb[ind];  
           }
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           String index = temp15[limit-1];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*
        //System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
        
          
    }
    
     
    
    private static int timeDistance(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations,double[][] graph, HashMap<Integer,Integer> productions)
    {
        
        
        ShortestPathDouble s = new ShortestPathDouble(); 
        double[] dist = s.dijkstra(graph, 18);
        
        
        int n = input.size();
        double[][] cost = new double [n][2];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          String tempstring = temp15[temp15.length-4].replace("i",""); //for formatting reasons the "i" prefix is removed so that the element key can be extracted
          int tempint = Integer.parseInt(tempstring);
          cost[i][1] = dist[tempint];  //in the case wherethere are >=1 input elements the one that has the biggest distance represents the distance 
           
          
          
          
          
          
          
          
          i++;
        }
        int key = (int) getMinValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*
        //System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
    }
    
     
      
      
   private static int knockoutPath(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions,HashSet<Integer> prune, HashMap<String,Integer> failures)
   {
        double graph[][] = new double[52][52];
        double graph2[][] = new double [52][52];
        for(int l=0;l<52;l++)
        {
            for(int p=0;p<52;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[1].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                graph[two][one]=Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        double dist[] = new double[52];
        double distProb[] = new double[52];
        ShortestPathDouble tp = new ShortestPathDouble();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 18);
        distProb = tpp.dijkstra(graph2, 18);
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           String index = temp15[0];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator,numerator;
            if(graph[0][ind]!=-1)
           {
             denominator = dist[ind] + graph[0][ind];
           }
           else
           {
             denominator = dist[ind];  
           }
           if(graph2[0][ind]!=-1)
           {
             numerator = distProb[ind] * (1-graph2[0][ind]);
           }
           else
           {
             numerator = distProb[ind];  
           }
           if(denominator==0 && numerator !=0)
           {
               
               denominator=1;
           }
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           String index = temp15[limit-1];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator = dist[ind];
           double numerator = distProb[ind];
           if(denominator==0 && numerator !=0)
           {
               
               denominator=1;
           }
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*
        //System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            String[] output =(String[]) operations.get(key);
            String output2 = output[output.length-4];
            if(failures.containsKey(output2))
            {
                
                Integer count =  failures.get(output2);
                count +=1;
                failures.put(output2, count);
            }
            else
            {
                failures.put(output2,1);
            }
            prune(operations,key,prune,failures);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
        
       
   }
   
   private static int knockoutPathPruneBoth(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions,HashSet<Integer> prune)
   {
        double graph[][] = new double[52][52];
        double graph2[][] = new double [52][52];
        for(int l=0;l<52;l++)
        {
            for(int p=0;p<52;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[1].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                graph[two][one]=Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        double dist[] = new double[52];
        double distProb[] = new double[52];
        ShortestPathDouble tp = new ShortestPathDouble();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 18);
        distProb = tpp.dijkstra(graph2, 18);
        
        double[] dist2 = new double[dist.length];
        for(int f=1;f<dist.length;f++)
        {
            prune2(operations,f,graph,dist2);
        }
        for(int f=1;f<dist.length;f++)
        {
           dist[f] += dist2[f];
        }
        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           String index = temp15[0];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator,numerator;
            if(graph[0][ind]!=-1)
           {
             denominator = dist[ind] + graph[0][ind];
           }
           else
           {
             denominator = dist[ind];  
           }
           if(graph2[0][ind]!=-1)
           {
             numerator = distProb[ind] * (1-graph2[0][ind]);
           }
           else
           {
             numerator = distProb[ind];  
           }
           if(denominator==0 && numerator !=0)
           {
               
               denominator=1;
           }
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           String index = temp15[limit-1];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator = dist[ind];
           double numerator = distProb[ind];
           if(denominator==0 && numerator !=0)
           {
               
               denominator=1;
           }
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*
        //System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        //input.remove(key);
        //path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
          //  available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            String[] output =(String[]) operations.get(key);
            String output2 = output[output.length-4];
            if(failures.containsKey(output2))
            {
                
                Integer count =  failures.get(output2);
                count +=1;
                failures.put(output2, count);
            }
            else
            {
                failures.put(output2,1);
            }
            prune(operations,key,prune,failures);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
        
       
   }
   
   private static int knockoutPathPruneBothTime(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions,HashSet<Integer> prune)
   {
        double graph[][] = new double[52][52];
        double graph2[][] = new double [52][52];
        for(int l=0;l<52;l++)
        {
            for(int p=0;p<52;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[1].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                graph[two][one]=Double.parseDouble(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        double dist[] = new double[52];
        double distProb[] = new double[52];
        ShortestPathDouble tp = new ShortestPathDouble();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 18);
        distProb = tpp.dijkstra(graph2, 18);
        
        double[] dist2 = new double[dist.length];
        for(int f=1;f<dist.length;f++)
        {
            prune2(operations,f,graph,dist2);
        }
        for(int f=1;f<dist.length;f++)
        {
           dist[f] += dist2[f];
        }
        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           String index = temp15[0];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator,numerator;
            if(graph[0][ind]!=-1)
           {
             denominator = dist[ind] + graph[0][ind];
           }
           else
           {
             denominator = dist[ind];  
           }
           if(graph2[0][ind]!=-1)
           {
             numerator = distProb[ind] * (1-graph2[0][ind]);
           }
           else
           {
             numerator = distProb[ind];  
           }
           if(denominator==0 && numerator !=0)
           {
               
               denominator=1;
           }
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           String index = temp15[limit-1];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator = dist[ind];
           double numerator = distProb[ind];
           if(denominator==0 && numerator !=0)
           {
               
               denominator=1;
           }
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*
        //System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            String[] output =(String[]) operations.get(key);
            String output2 = output[output.length-4];
            if(failures.containsKey(output2))
            {
                
                Integer count =  failures.get(output2);
                count +=1;
                failures.put(output2, count);
            }
            else
            {
                failures.put(output2,1);
            }
            prune(operations,key,prune,failures);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
        
       
   }
   
   private static int knockoutPathPruneBothCombo(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions,HashSet<Integer> prune)
   {
        double graph[][] = new double[52][52];
        double graph2[][] = new double [52][52];
        for(int l=0;l<52;l++)
        {
            for(int p=0;p<52;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[1].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit+1]) + Double.parseDouble(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                graph[two][one]=Double.parseDouble(dokimi[limit+1]) + Double.parseDouble(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        double dist[] = new double[52];
        double distProb[] = new double[52];
        ShortestPathDouble tp = new ShortestPathDouble();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 18);
        distProb = tpp.dijkstra(graph2, 18);
        
        double[] dist2 = new double[dist.length];
        for(int f=1;f<dist.length;f++)
        {
            prune2(operations,f,graph,dist2);
        }
        for(int f=1;f<dist.length;f++)
        {
           dist[f] += dist2[f];
        }
        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           String index = temp15[0];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator,numerator;
            if(graph[0][ind]!=-1)
           {
             denominator = dist[ind] + graph[0][ind];
           }
           else
           {
             denominator = dist[ind];  
           }
           if(graph2[0][ind]!=-1)
           {
             numerator = distProb[ind] * (1-graph2[0][ind]);
           }
           else
           {
             numerator = distProb[ind];  
           }
           if(denominator==0 && numerator !=0)
           {
               
               denominator=1;
           }
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           String index = temp15[limit-1];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator = dist[ind];
           double numerator = distProb[ind];
           if(denominator==0 && numerator !=0)
           {
               
               denominator=1;
           }
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*
        //System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            String[] output =(String[]) operations.get(key);
            String output2 = output[output.length-4];
            if(failures.containsKey(output2))
            {
                
                Integer count =  failures.get(output2);
                count +=1;
                failures.put(output2, count);
            }
            else
            {
                failures.put(output2,1);
            }
            prune(operations,key,prune,failures);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
        
       
   }
   
   
   
   private static int knockoutPathOld(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions)
   {
    double graph[][] = new double[52][52];
        double graph2[][] = new double [52][52];
        for(int l=0;l<52;l++)
        {
            for(int p=0;p<52;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[1].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                graph[two][one]=Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        double dist[] = new double[52];
        double distProb[] = new double[52];
        ShortestPathDouble tp = new ShortestPathDouble();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 18);
        distProb = tpp.dijkstra(graph2, 18);
        //w
        
        //w
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           String index = temp15[0];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator,numerator;
            if(graph[0][ind]!=-1)
           {
             denominator = dist[ind] + graph[0][ind];
           }
           else
           {
             denominator = dist[ind];  
           }
           if(graph2[0][ind]!=-1)
           {
             numerator = distProb[ind] * (1-graph2[0][ind]);
           }
           else
           {
             numerator = distProb[ind];  
           }
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           String index = temp15[limit-1];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*
        //System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
        
          
   }
    
    
   private static int knockoutPathTime(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions)
   {
        double graph[][] = new double[52][52];
        double graph2[][] = new double [52][52];
        for(int l=0;l<52;l++)
        {
            for(int p=0;p<52;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[1].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                graph[two][one]=Double.parseDouble(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        double dist[] = new double[52];
        double distProb[] = new double[52];
        ShortestPathDouble tp = new ShortestPathDouble();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 18);
        distProb = tpp.dijkstra(graph2, 18);
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           String index = temp15[0];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator,numerator;
            if(graph[0][ind]!=-1)
           {
             denominator = dist[ind] + graph[0][ind];
           }
           else
           {
             denominator = dist[ind];  
           }
           if(graph2[0][ind]!=-1)
           {
             numerator = distProb[ind] * (1-graph2[0][ind]);
           }
           else
           {
             numerator = distProb[ind];  
           }
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           String index = temp15[limit-1];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*
        //System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
        
       
   }
   
   
   private static int knockoutPathCombo(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions)
   {
        double graph[][] = new double[52][52];
        double graph2[][] = new double [52][52];
        for(int l=0;l<52;l++)
        {
            for(int p=0;p<52;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[1].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit])+Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                
                graph[two][one]=Double.parseDouble(dokimi[limit])+Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        double dist[] = new double[52];
        double distProb[] = new double[52];
        ShortestPathDouble tp = new ShortestPathDouble();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 18);
        distProb = tpp.dijkstra(graph2, 18);
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           String index = temp15[0];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator,numerator;
            if(graph[0][ind]!=-1)
           {
             denominator = dist[ind] + graph[0][ind];
           }
           else
           {
             denominator = dist[ind];  
           }
           if(graph2[0][ind]!=-1)
           {
             numerator = distProb[ind] * (1-graph2[0][ind]);
           }
           else
           {
             numerator = distProb[ind];  
           }
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           String index = temp15[limit-1];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*
        //System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
        
       
   }
   
   private static int createArrayRoot(HashMap<Integer,String[]> operations,int[][] graph)
   {
       for(int i=0;i<graph.length;i++)
        {
            for(int j=0;j<graph.length;j++)
            {
                graph[i][j]=-1;
            }
        }
        
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[1].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = 1;
                
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                
                if(Double.parseDouble(dokimi[dokimi.length-2])==0)
                {
                 graph[two][one]=0;
                }
                else
                {
                 graph[two][one]=1;
                }
                
               
                
             }
            }
            
        }
        return 1;
   }
   
    private static int createArrayCost(HashMap<Integer,String[]> operations,double[][] graph)
   {
       for(int i=0;i<graph.length;i++)
        {
            for(int j=0;j<graph.length;j++)
            {
                graph[i][j]=-1;
            }
        }
        
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[1].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit+1]);
                
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                graph[two][one]=Double.parseDouble(dokimi[limit+1]);
               
                
             }
            }
            
        }
       
        return 1;
   }
    
   private static int createArrayTime(HashMap<Integer,String[]> operations,double[][] graph)
   {
        
        for(int i=0;i<graph.length;i++)
        {
            for(int j=0;j<graph.length;j++)
            {
                graph[i][j]=-1;
            }
        }
        
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[1].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit]);
                
            }
            else
            {
             for(int y=1;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                
                graph[two][one]=Double.parseDouble(dokimi[limit]);
               
                
             }
            }
            
        }
       
       
        return 1;
   }
   
   private static int determineExecutionInstance(HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions)
   {
       Iterator it = operations.entrySet().iterator();
       while(it.hasNext())
       {
           HashMap.Entry temp = (HashMap.Entry) it.next();
           int key = (Integer) temp.getKey();
           String[] value = (String[]) temp.getValue();
           double prob = Double.parseDouble(value[value.length-1]);
           int result = executionOld(prob);
           productions.put(key, result);
       }
             
       return 1;
   }
   
    private static void pruneOperations(HashMap<Integer,String[]> operations, int key, HashSet<Integer> prune)
   {
        String[] entry = (String []) operations.get(key);
        String output = entry[entry.length-4];
        
        
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry entry2 = (HashMap.Entry) it.next();
            String[] compare = (String[]) entry2.getValue();
            for(int i=0;i<compare.length-4;i++)
            {
                if(compare[i].equals(output))
                {
                    prune.add((Integer)entry2.getKey());
                }
            }
        }
        
        
       
   }
    
   private static void prune(HashMap<Integer,String[]> operations, int key, HashSet<Integer> prune, HashMap<String,Integer> failures)
    {
        Iterator it = operations.entrySet().iterator();
        String[] operation = (String[])operations.get(key);
        String output = operation[operation.length-4];
        int count=0;
        while(it.hasNext())
        {
            HashMap.Entry entry = (HashMap.Entry) it.next();
            String compare[] = (String[]) entry.getValue();
            if(compare[compare.length-4].equals(output))
            {
                count+=1;
            }
        }
        int check = failures.get(output);
        if(check!=count)
        {
            
            return;
        }
        else
        {
            
               // System.out.println(count + ",   " + output);
            
        }
        HashSet<String> candidates = new HashSet();
        HashSet<String> prune2 = new HashSet();
        it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry entry = (HashMap.Entry) it.next();
            String[] compare = (String[]) entry.getValue();
            for(int i=0;i<compare.length-4;i++)
            {
                if(compare[i].equals(output))
                {
                 for(int j=0;j<compare.length-4;j++)
                 {
                     
                     if(j!=i)
                     {
                         
                         candidates.add(compare[j]);
                     }
                 }
                }
            }
        }
        
        int sum_cand=0;
        int sum_out=0;
        for(String cand : candidates)
        {
          
          sum_cand=0;
          sum_out=0;
          it = operations.entrySet().iterator();
          while(it.hasNext())
          {
           HashMap.Entry entry = (HashMap.Entry) it.next();
           
           String[] compare = (String[]) entry.getValue();
           for(int i=0;i<compare.length-4;i++)
           {
            if(compare[i].equals(cand))
            {
                sum_cand+=1;
                
             for(int j=0;j<compare.length-4;j++)
             {
                 
              if(compare[j].equals(output))
              {
                  
                  sum_out+=1;
              }   
             }   
            }   
           }
          }
          
          if(sum_cand==sum_out)
          {
              
              prune2.add(cand);
          }
        }
        
        for(String c : prune2)
        {
             it=operations.entrySet().iterator();
             while(it.hasNext())
             {
               HashMap.Entry entry = (HashMap.Entry) it.next();
               String compare[] = (String[]) entry.getValue();
               if(compare[compare.length-4].equals(c))
               {
                   
                   prune.add((Integer)entry.getKey());
               }
             }
            
        }
        
       
    }
   
  private static void prune2(HashMap<Integer,String[]> operations, int key,double[][] dist, double[] dist2)
    {
        Iterator it = operations.entrySet().iterator();
        String[] operation = (String[])operations.get(key);
        HashSet<Integer> prune = new HashSet();
        String output;
        if(key<10)
        {
           output = "i0".concat(String.valueOf(key));
        }
        else
        {
            output = "i".concat(String.valueOf(key));
        }
        
        
        HashSet<String> candidates = new HashSet();
        HashSet<String> prune2 = new HashSet();
        while(it.hasNext())
        {
            HashMap.Entry entry = (HashMap.Entry) it.next();
            String[] compare = (String[]) entry.getValue();
            for(int i=0;i<compare.length-4;i++)
            {
                if(compare[i].equals(output))
                {
                   
                 for(int j=0;j<compare.length-4;j++)
                 {
                     
                     if(j!=i)
                     {
                         
                         candidates.add(compare[j]);
                     }
                 }
                }
            }
        }
        
        int sum_cand=0;
        int sum_out=0;
        for(String cand : candidates)
        {
          
          sum_cand=0;
          sum_out=0;
          it = operations.entrySet().iterator();
          while(it.hasNext())
          {
           HashMap.Entry entry = (HashMap.Entry) it.next();
           
           String[] compare = (String[]) entry.getValue();
           for(int i=0;i<compare.length-4;i++)
           {
            if(compare[i].equals(cand))
            {
                sum_cand+=1;
                
             for(int j=0;j<compare.length-4;j++)
             {
                 
              if(compare[j].equals(output))
              {
                  
                  sum_out+=1;
              }   
             }   
            }   
           }
          }
          
          if(sum_cand==sum_out)
          {
              prune2.add(cand);
              String output2 = cand.replace("i","");
              int two = Integer.parseInt(output2);
              
              if(dist[0][key]!=-1)
              {
              dist2[two]+=dist[0][key];
              }
              else
              {
              dist2[two]+=findOperationCost(operations,key);
              }
              prune.add(two);
          }
        }
        if(prune2.isEmpty())
        {
            
        }
        
        
       
    }
  
  private static double findOperationCost(HashMap<Integer,String[]> operations, int key)
   {
       double result=11;
       String output;
        if(key<10)
        {
           output = "i0".concat(String.valueOf(key));
        }
        else
        {
            output = "i".concat(String.valueOf(key));
        }
       Iterator it = operations.entrySet().iterator();
       while(it.hasNext())
       {
           HashMap.Entry entry = (HashMap.Entry) it.next();
           String[] operation = (String[]) entry.getValue();
           if(operation[operation.length-4].equals(output))
           {
               if(Double.parseDouble(operation[operation.length-2])<result)
               {
                   result = Double.parseDouble(operation[operation.length-2]);
               }
           }
           
       }
       if(result==11)
       {
           System.out.println(key);
       }
       return result;
   }
  
  private static double getDuration(ArrayList<Double> end)
{
    double max_end =0;
    
    Iterator it_end = end.iterator();
    
    
    while(it_end.hasNext())
    {
        double cand = (double) it_end.next();
        if(cand>max_end)
        {
            max_end = cand;
        }
    }
    
    return max_end;
    
}
  
  private static HashMap<String,Integer> findNumberAvailableResources(HashMap<Integer,String[]> executable ,HashMap<String,Integer> resources, HashMap<Integer, ArrayList<String>> listOfAllocations, HashMap<Integer,ArrayList<Double>> listOfStarts, Double starting_time)
{
   HashMap<String,Integer> resources_local = (HashMap<String,Integer>) resources.clone();
   HashMap<String,Integer> resources_mention = (HashMap<String,Integer>) resources.clone();
   
   Iterator mention_it = resources_mention.entrySet().iterator();
   while(mention_it.hasNext())
   {
       Map.Entry<String,Integer> resource_mention = (Map.Entry<String,Integer>) mention_it.next();
       resources_mention.replace(resource_mention.getKey(), 0);
       
   }
   boolean start = true;
   for(int i=0;i<listOfStarts.size();i++)
   {
       ArrayList<String> temp_allocation = listOfAllocations.get(i);
       if(!temp_allocation.isEmpty())
       {
        start = false;   
       }
   }
   if(start)
   {
       
       Iterator executable_it = executable.entrySet().iterator();
       while(executable_it.hasNext())
       {
         Map.Entry<Integer,String[]> operation_it = (Map.Entry<Integer,String[]>) executable_it.next();
         int resource_mention = resources_mention.get(operation_it.getValue()[0]);
         resource_mention+=1;
         resources_mention.put(operation_it.getValue()[0], resource_mention);
       }
       
       Iterator resources_local_it = resources_local.entrySet().iterator();
       while(resources_local_it.hasNext())
       {
         Map.Entry<String,Integer> next_entry = (Map.Entry<String,Integer>) resources_local_it.next();
        
         
           if(resources_mention.get(next_entry.getKey())==0)
           {
               resources_local.put(next_entry.getKey(),0);
               //count++;
           }
         
       
     }
         return resources_local;
     }
    
   for(int i=0;i<listOfStarts.size();i++)
   {
       ArrayList<Double> temp_start = listOfStarts.get(i);
       ArrayList<String> temp_allocation = listOfAllocations.get(i);
       
       for(int j=0;j<temp_allocation.size();j++)
       {
           
           double start_candidate = temp_start.get(j);
           if(start_candidate>starting_time)
           {
               String resource_candidate = temp_allocation.get(j);
               //System.out.println(resource_candidate);
               int candidate_availability = resources_local.get(resource_candidate);
               candidate_availability -= 1;
               resources_local.put(resource_candidate,candidate_availability);
               int candidate_mention = resources_mention.get(resource_candidate);
               candidate_mention += 1;
               resources_mention.put(resource_candidate,candidate_mention);
           }
       }
   }
   
   Iterator executable_it = executable.entrySet().iterator();
   while(executable_it.hasNext())
   {
       Map.Entry<Integer,String[]> operation_it = (Map.Entry<Integer,String[]>) executable_it.next();
       int resource_mention = resources_mention.get(operation_it.getValue()[0]);
       resource_mention+=1;
       resources_mention.put(operation_it.getValue()[0], resource_mention);
   }
   
   int count=0; // nubmer of resources with availability == zero
   int count2=0; // number of operations that feature this resource as their required resource
   Iterator resources_local_it = resources_local.entrySet().iterator();
   while(resources_local_it.hasNext())
   {
       Map.Entry<String,Integer> next_entry = (Map.Entry<String,Integer>) resources_local_it.next();
       int availability = next_entry.getValue();
       if(availability == 0)
       {
           count++;
       }
       else
       {
           if(resources_mention.get(next_entry.getKey())==0)
           {
               resources_local.put(next_entry.getKey(),0);
               count++;
           }
       }
       //System.out.println(next_entry.getKey()  +  "  ,  " +  next_entry.getValue());
   }
   
   return resources_local;
}
  
private static double findEarliestStartingTime(HashMap<String,Integer> resources, HashMap<Integer, ArrayList<String>> listOfAllocations, HashMap<Integer,ArrayList<Double>> listOfEnd, Double starting_time)
{
    HashMap<String,Integer> resources_local = (HashMap<String,Integer>) resources.clone();
    double min = Double.MAX_VALUE;
   for(int i=0;i<listOfEnd.size();i++)
   {
       ArrayList<Double> temp_end = listOfEnd.get(i);
       ArrayList<String> temp_allocation = listOfAllocations.get(i);
       
       for(int j=0;j<temp_end.size();j++)
       {
           double start_candidate = temp_end.get(j);
           if(start_candidate!=starting_time  && start_candidate>starting_time && start_candidate<min)
           {
               min = start_candidate;
               
           }
       }
   }
   
   return min;
}

private static double findEarliestStartingTimeOperation(Integer key, HashMap<Integer,String[]> operations, HashMap<String,Integer> resources, HashMap<Integer, ArrayList<String>> listOfAllocations, HashMap<Integer,ArrayList<Double>> listOfEnd, Double starting_time, Integer[] index, AtomicBoolean resource_flag, StringBuilder resource_choice)
{
    ;
    String[] operation = operations.get(key);
    String resource = operation[0];
    int index_instance = index[0];
    int index_b = index[1];
    HashMap<String,Integer> resources_local = (HashMap<String,Integer>) resources.clone();
    
    int availability = resources_local.get(resource);
    int entire_availability =0;
    Iterator resources_iterator = resources_local.entrySet().iterator();
    while(resources_iterator.hasNext())
    {
        Map.Entry<String,Integer> resource_next = (Map.Entry<String,Integer>) resources_iterator.next();
        entire_availability += resource_next.getValue();
    }
    for(int i=0;i<listOfEnd.size();i++)
   {
       ArrayList<Double> temp_end = listOfEnd.get(i);
       ArrayList<String> temp_allocation = listOfAllocations.get(i);
       
       for(int j=0;j<temp_end.size();j++)
       {
           double start_candidate = temp_end.get(j);
           String resource_candidate = temp_allocation.get(j);
           int this_resource_availability = resources_local.get(resource_candidate);
           if(start_candidate>starting_time  && resource_candidate.equals(resource))
           {
               this_resource_availability -=1;
               resources_local.put(resource_candidate,this_resource_availability);
               
               availability -=1;
               entire_availability -=1;
               continue;
           }
           if(start_candidate>starting_time)
           {
               
               
               entire_availability -=1;
               this_resource_availability -=1;
               resources_local.put(resource_candidate,this_resource_availability);
           }
       }
   }
    
    
    
   String candidate_resource="";
   
    Double no_wait_starting_time = Double.MAX_VALUE;
    String other_resource = "";
   if(availability >0)
   {
       
       return starting_time;
   }
   
   if(availability ==0 & entire_availability>0) 
   {
       resource_flag.set(true);
       
       if(candidate_resource.equals("") || candidate_resource.equals(resource))
       {
         Iterator resources_local_iterator = resources_local.entrySet().iterator();
         ArrayList<String> resources_available = new ArrayList<>();
         while(resources_local_iterator.hasNext())
         {
            
             Map.Entry<String,Integer> resource_temp = (Map.Entry<String,Integer>)resources_local_iterator.next();
             
             if(resource_temp.getValue()!=0)
             {
                 
                 resources_available.add(resource_temp.getKey());
             }
         }
        
        String[] resources_available_array = resources_available.toArray(new String[0]);
        System.out.println(resources_available_array.length);
        int index2;
        int minimum=0;
        int max = resources_available_array.length-1;
        index2 = (int) ((Math.random() * (max - minimum)) + minimum);
        resource_choice.append(resources_available_array[index2]);
        //other_resource = resources_available_array[index2];
       }
       else
       {
           
           other_resource = candidate_resource;
       }
       
        //no_wait_starting_time =  starting_time;
        return starting_time;
   }
   
   
   double min = Integer.MAX_VALUE;
   for(int i=0;i<listOfEnd.size();i++)
   {
       ArrayList<Double> temp_end = listOfEnd.get(i);
       ArrayList<String> temp_allocation = listOfAllocations.get(i);
       
       for(int j=0;j<temp_end.size();j++)
       {
           double start_candidate = temp_end.get(j);
           String resource_candidate = temp_allocation.get(j);
           if( start_candidate>starting_time && start_candidate<min)// && resource_candidate.equals(resource))
           {
               min = start_candidate;
               
               candidate_resource = resource_candidate;
              /*if(starting_time==0)
              {
                  min = start_candidate;
               index[0] = i;
               index[1] =j;
              }
              else
              {
                  if(j>=index[1] && i>=index[0])
                  {
                  min = start_candidate;
               index[0] = i;
               index[1] =j;
                  }
              }*/
               
           }
       }
   }
      /* if(min<no_wait_starting_time) //(gia no wait conditional)
       {
           System.out.println("heatran " + min);
       }
       else
       {
           min = no_wait_starting_time;
           resource_choice.append(other_resource);
           return min;
           
       }*/
       
       System.out.println(candidate_resource);
       //resource_flag.set(true);
       resource_choice.append(candidate_resource);
   
   if(min==Integer.MAX_VALUE)
   {
       
       min=starting_time;
       
   }
   
   
   starting_time = min;
   return min;
}







private static double findStartingTime(HashMap<Integer,String[]> operations, int key, HashMap<Integer,ArrayList<Double>> listOfEnd, HashMap<Integer, ArrayList<String>> listOfAllocations, Integer[] instances, Double current_time, HashMap<String,Integer> resources, HashMap<String,Double> times, Double starting_time,double[][] indexes, int instance)
{
        String[] operation = operations.get(key);
        String resource = operation[0];
        Integer resource_mentions =0;
        int resource_index = 0;
        
        HashMap<String,Integer> resources_clone = (HashMap<String,Integer>) resources.clone();
        int resource_capacity = resources.get(resource);
        Iterator it = resources.entrySet().iterator();
        int q=0;
        while(it.hasNext())
        {
           Map.Entry<String,Integer> resource_pair = (Map.Entry<String,Integer>) it.next();
           if(resource_pair.getKey().equals(resource))
           {
               resource_index = q;
           }
           q++;
        }
        
        double max_end;
        int save_index =0;
        int available=resource_capacity;
        if(resource_capacity==1)
        {
            max_end=0;
        }
        else
        {
            max_end=Double.MAX_VALUE;
        }
        for(Integer j=0;j<instances.length;j++)
        {
            int inst = instances[j];
            
        ArrayList<Double> end = listOfEnd.get(inst);
        ArrayList<String> allocation = listOfAllocations.get(inst);
        
        //int index =0;
        if(resource_capacity==1)
        {
        if(!allocation.isEmpty())
        {
        for(int i=0;i<allocation.size();i++)
        {
           String current_resource = allocation.get(i);
           if(current_resource.equals(resource))
           {
               resource_mentions+=1;
               Double max_cand = end.get(i);
               available = resources_clone.get(resource);
               
               if(max_cand > indexes[instance][resource_index])
               {
                  
                   available = available -1;
                   resources_clone.put(resource,available); //edo
                   
                   if(max_cand > max_end)
                   {
                       max_end = max_cand;
                   }
               }
               
           }
        }
        }
        }
        else
        {
          if(!allocation.isEmpty())
          {
          for(int i=0;i<allocation.size();i++)
           {
            
            String current_resource = allocation.get(i);
            if(current_resource.equals(resource))
            {
               resource_mentions+=1;
               Double max_cand = end.get(i);
               available = resources_clone.get(resource);
               //available = available -1;
               
               resources_clone.put(resource,available);
               if(max_cand > indexes[instance][resource_index])
               {
                   
                available = available -1;
                
                resources_clone.put(resource,available);
                 if(max_cand < max_end)
                   {
                       max_end = max_cand;
                       save_index = i;
                   }
                     
                   
               }
               //max_end = end.get(i);
              
            }
           }  
          }
        }
        
        
        
   }
        if(available>0)
        {
            max_end = starting_time;
        }
        
        if(available>0 && resource_mentions<resource_capacity)
        {
            max_end = 0;
        }
        

        
        if(available <=0)
        {
          
        }
        
        
        ArrayList<String> input_elements = new ArrayList<>();
        for(int j=1;j<(operation.length-4);j++)
        {
            input_elements.add(operation[j]);
        }
        
        if(input_elements.size()!=0)
        {
        for(int j=0;j<input_elements.size();j++)
        {
            Double time_cand = times.get(input_elements.get(j));
            if(time_cand>max_end)
            {
                max_end = time_cand;
            }
        }
        }
        double second_max = Double.MAX_VALUE;
        
        if( available <0)
        {
             
          for(int j=0;j<instances.length;j++)
          {
               int inst = instances[j];
            
               ArrayList<Double> end = listOfEnd.get(inst);
               ArrayList<String> allocation = listOfAllocations.get(inst);
               if(!allocation.isEmpty())
              {
               for(int i=0;i<allocation.size();i++)
               {
                String current_resource = allocation.get(i);
                if(current_resource.equals(resource))
                {
                  Double max_cand = end.get(i);
                  //available = resources_clone.get(resource);
                  //available = available -1;
                  //resources_clone.put(resource,available);
                  if(max_cand > max_end && max_cand < second_max)
                  {
                    max_end = max_cand;
                    second_max = max_end;
                  }
                  //max_end = end.get(i);
                  
                }
              }
             }
          }
        }else
        {
           
        }
        
       // System.out.println("available: " + available + " max_end: " + max_end);
        indexes[instance][resource_index] = max_end;
        
        
        return max_end;
}


private static int executeLowestCost(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions, HashSet<Integer> path)//Lowest Cost selection strategy
    {
        int n = input.size();
        double[][] cost = new double [n][3];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();
          cost[i][0]= Double.valueOf((Integer)pair.getKey());
          String[] temp15= (String[]) pair.getValue();
          cost[i][1]= Double.parseDouble(temp15[temp15.length-2]);
          
          i++;
        }
        int key = (int) getMinValue(cost);
        
        /*String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
    }
    
    private static int executeShortestTime(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations,HashMap<Integer,Integer> productions, HashSet<Integer> path)
    {
        int n = input.size();
        double[][] cost = new double [n][3];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();
          cost[i][0]= (Integer) pair.getKey();
          String[] temp15= (String[]) pair.getValue();
          cost[i][1]= Double.parseDouble(temp15[temp15.length-3]);
          
          i++;
        }
        int key = (int)getMinValue(cost);
        /*
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
    }
    
    private static int executeLowestFail(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions, HashSet<Integer> path)
    {
        int n = input.size();
        double[][] cost = new double [n][3];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();
          cost[i][0]= (Integer) pair.getKey();
          String[] temp15= (String[]) pair.getValue();
          cost[i][1]= Double.parseDouble(temp15[temp15.length-1]);
          
          i++;
        }
        int key = (int)getMaxValue(cost);
        
        /*String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
    }
    
    private static int rootDistance(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations,int [][] graph, HashMap<Integer,Integer> productions, HashSet<Integer> path)
    {
        
        
        
        
        ShortestPath s = new ShortestPath(); 
        int[] dist = s.dijkstra(graph, 18);
        
        
        int n = input.size();
        double[][] cost = new double [n][2];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          String tempstring = temp15[temp15.length-4].replace("i",""); //for formatting reasons the "i" prefix is removed so that the element key can be extracted
          int tempint = Integer.parseInt(tempstring);
          cost[i][1] = dist[tempint];  //in the case wherethere are >=1 input elements the one that has the biggest distance represents the distance
          
          
          i++;
        }
        int key = (int) getMinValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        */
        return key;
    }
    
    private static int costDistance(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations,double [][] graph, HashMap<Integer,Integer> productions, HashSet<Integer> path)
    {
        
        
        ShortestPathDouble s = new ShortestPathDouble(); 
        double[] dist = s.dijkstra(graph, 18);
        
        
        int n = input.size();
        double[][] cost = new double [n][2];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          String tempstring = temp15[temp15.length-4].replace("i",""); //for formatting reasons the "i" prefix is removed so that the element key can be extracted
          int tempint = Integer.parseInt(tempstring);
          cost[i][1] = dist[tempint];  //in the case wherethere are >=1 input elements the one that has the biggest distance represents the distance 
           
          
          
          
          
          i++;
        }
        int key = (int) getMinValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            .out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }*/
        return key;
    }
    
    
     private static int timeDistance(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations,double[][] graph, HashMap<Integer,Integer> productions, HashSet<Integer> path)
    {
        
        
        ShortestPathDouble s = new ShortestPathDouble(); 
        double[] dist = s.dijkstra(graph, 18);
        
        
        int n = input.size();
        double[][] cost = new double [n][2];
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          String tempstring = temp15[temp15.length-4].replace("i",""); //for formatting reasons the "i" prefix is removed so that the element key can be extracted
          int tempint = Integer.parseInt(tempstring);
          cost[i][1] = dist[tempint];  //in the case wherethere are >=1 input elements the one that has the biggest distance represents the distance 
           
          
          
          
          
          
          
          
          i++;
        }
        int key = (int) getMinValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }*/
        return key;
    }
    
     
      
      
   private static int knockoutPath(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions,HashSet<Integer> prune, HashMap<String,Integer> failures, HashSet<Integer> path)
   {
        double graph[][] = new double[52][52];
        double graph2[][] = new double [52][52];
        for(int l=0;l<52;l++)
        {
            for(int p=0;p<52;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[0].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                graph[two][one]=Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        double dist[] = new double[52];
        double distProb[] = new double[52];
        ShortestPathDouble tp = new ShortestPathDouble();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 18);
        distProb = tpp.dijkstra(graph2, 18);
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           String index = temp15[0];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator,numerator;
            if(graph[0][ind]!=-1)
           {
             denominator = dist[ind] + graph[0][ind];
           }
           else
           {
             denominator = dist[ind];  
           }
           if(graph2[0][ind]!=-1)
           {
             numerator = distProb[ind] * (1-graph2[0][ind]);
           }
           else
           {
             numerator = distProb[ind];  
           }
           if(denominator==0 && numerator !=0)
           {
               
               denominator=1;
           }
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           String index = temp15[limit-1];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator = dist[ind];
           double numerator = distProb[ind];
           if(denominator==0 && numerator !=0)
           {
               
               denominator=1;
           }
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            String[] output =(String[]) operations.get(key);
            String output2 = output[output.length-4];
            if(failures.containsKey(output2))
            {
                
                Integer count =  failures.get(output2);
                count +=1;
                failures.put(output2, count);
            }
            else
            {
                failures.put(output2,1);
            }
            prune(operations,key,prune,failures);
            System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }*/
        return key;
        
       
   }
   
   private static int knockoutPathPruneBoth(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions,HashSet<Integer> prune, HashMap<String,Integer> failures, HashSet<Integer> path)
   {
        double graph[][] = new double[52][52];
        double graph2[][] = new double [52][52];
        for(int l=0;l<52;l++)
        {
            for(int p=0;p<52;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[0].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                graph[two][one]=Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        double dist[] = new double[52];
        double distProb[] = new double[52];
        ShortestPathDouble tp = new ShortestPathDouble();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 18);
        distProb = tpp.dijkstra(graph2, 18);
        
        double[] dist2 = new double[dist.length];
        for(int f=1;f<dist.length;f++)
        {
            prune2(operations,f,graph,dist2);
        }
        for(int f=1;f<dist.length;f++)
        {
           dist[f] += dist2[f];
        }
        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           String index = temp15[0];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator,numerator;
            if(graph[0][ind]!=-1)
           {
             denominator = dist[ind] + graph[0][ind];
           }
           else
           {
             denominator = dist[ind];  
           }
           if(graph2[0][ind]!=-1)
           {
             numerator = distProb[ind] * (1-graph2[0][ind]);
           }
           else
           {
             numerator = distProb[ind];  
           }
           if(denominator==0 && numerator !=0)
           {
               
               denominator=1;
           }
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           String index = temp15[limit-1];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator = dist[ind];
           double numerator = distProb[ind];
           if(denominator==0 && numerator !=0)
           {
               
               denominator=1;
           }
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        //input.remove(key);
        //path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            String[] output =(String[]) operations.get(key);
            String output2 = output[output.length-4];
            if(failures.containsKey(output2))
            {
                
                Integer count =  failures.get(output2);
                count +=1;
                failures.put(output2, count);
            }
            else
            {
                failures.put(output2,1);
            }
            prune(operations,key,prune,failures);
         //   System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        return key;
        
       
   }
   
   private static int knockoutPathPruneBothTime(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions,HashSet<Integer> prune, HashMap<String,Integer> failures, HashSet<Integer> path)
   {
        double graph[][] = new double[52][52];
        double graph2[][] = new double [52][52];
        for(int l=0;l<52;l++)
        {
            for(int p=0;p<52;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[0].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                graph[two][one]=Double.parseDouble(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        double dist[] = new double[52];
        double distProb[] = new double[52];
        ShortestPathDouble tp = new ShortestPathDouble();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 18);
        distProb = tpp.dijkstra(graph2, 18);
        
        double[] dist2 = new double[dist.length];
        for(int f=1;f<dist.length;f++)
        {
            prune2(operations,f,graph,dist2);
        }
        for(int f=1;f<dist.length;f++)
        {
           dist[f] += dist2[f];
        }
        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           String index = temp15[0];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator,numerator;
            if(graph[0][ind]!=-1)
           {
             denominator = dist[ind] + graph[0][ind];
           }
           else
           {
             denominator = dist[ind];  
           }
           if(graph2[0][ind]!=-1)
           {
             numerator = distProb[ind] * (1-graph2[0][ind]);
           }
           else
           {
             numerator = distProb[ind];  
           }
           if(denominator==0 && numerator !=0)
           {
               
               denominator=1;
           }
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           String index = temp15[limit-1];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator = dist[ind];
           double numerator = distProb[ind];
           if(denominator==0 && numerator !=0)
           {
               
               denominator=1;
           }
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        //input.remove(key);
        //path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
          //  available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            String[] output =(String[]) operations.get(key);
            String output2 = output[output.length-4];
            if(failures.containsKey(output2))
            {
                
                Integer count =  failures.get(output2);
                count +=1;
                failures.put(output2, count);
            }
            else
            {
                failures.put(output2,1);
            }
            prune(operations,key,prune,failures);
         //   System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        return key;
        
       
   }
   
   private static int knockoutPathPruneBothCombo(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions,HashSet<Integer> prune, HashMap<String,Integer> failures, HashSet<Integer> path)
   {
        double graph[][] = new double[52][52];
        double graph2[][] = new double [52][52];
        for(int l=0;l<52;l++)
        {
            for(int p=0;p<52;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[0].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit+1]) + Double.parseDouble(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                graph[two][one]=Double.parseDouble(dokimi[limit+1]) + Double.parseDouble(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        double dist[] = new double[52];
        double distProb[] = new double[52];
        ShortestPathDouble tp = new ShortestPathDouble();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 18);
        distProb = tpp.dijkstra(graph2, 18);
        
        double[] dist2 = new double[dist.length];
        for(int f=1;f<dist.length;f++)
        {
            prune2(operations,f,graph,dist2);
        }
        for(int f=1;f<dist.length;f++)
        {
           dist[f] += dist2[f];
        }
        
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           String index = temp15[0];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator,numerator;
            if(graph[0][ind]!=-1)
           {
             denominator = dist[ind] + graph[0][ind];
           }
           else
           {
             denominator = dist[ind];  
           }
           if(graph2[0][ind]!=-1)
           {
             numerator = distProb[ind] * (1-graph2[0][ind]);
           }
           else
           {
             numerator = distProb[ind];  
           }
           if(denominator==0 && numerator !=0)
           {
               
               denominator=1;
           }
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           String index = temp15[limit-1];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator = dist[ind];
           double numerator = distProb[ind];
           if(denominator==0 && numerator !=0)
           {
               
               denominator=1;
           }
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        //System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        //input.remove(key);
        //path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            //available.add(temp16[temp16.length-4]);
          //  System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            String[] output =(String[]) operations.get(key);
            String output2 = output[output.length-4];
            if(failures.containsKey(output2))
            {
                
                Integer count =  failures.get(output2);
                count +=1;
                failures.put(output2, count);
            }
            else
            {
                failures.put(output2,1);
            }
            prune(operations,key,prune,failures);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }
        return key;
        
       
   }
   
   
   
   private static int knockoutPathOld(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions, HashSet<Integer> path)
   {
    double graph[][] = new double[52][52];
        double graph2[][] = new double [52][52];
        for(int l=0;l<52;l++)
        {
            for(int p=0;p<52;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[0].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                graph[two][one]=Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        double dist[] = new double[52];
        double distProb[] = new double[52];
        ShortestPathDouble tp = new ShortestPathDouble();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 18);
        distProb = tpp.dijkstra(graph2, 18);
        //w
        
        //w
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           String index = temp15[0];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator,numerator;
            if(graph[0][ind]!=-1)
           {
             denominator = dist[ind] + graph[0][ind];
           }
           else
           {
             denominator = dist[ind];  
           }
           if(graph2[0][ind]!=-1)
           {
             numerator = distProb[ind] * (1-graph2[0][ind]);
           }
           else
           {
             numerator = distProb[ind];  
           }
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           String index = temp15[limit-1];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }*/
        return key;
        
          
   }
    
    
   private static int knockoutPathTime(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions, HashSet<Integer> path)
   {
        double graph[][] = new double[52][52];
        double graph2[][] = new double [52][52];
        for(int l=0;l<52;l++)
        {
            for(int p=0;p<52;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[0].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                graph[two][one]=Double.parseDouble(dokimi[limit]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        double dist[] = new double[52];
        double distProb[] = new double[52];
        ShortestPathDouble tp = new ShortestPathDouble();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 18);
        distProb = tpp.dijkstra(graph2, 18);
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           String index = temp15[0];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator,numerator;
            if(graph[0][ind]!=-1)
           {
             denominator = dist[ind] + graph[0][ind];
           }
           else
           {
             denominator = dist[ind];  
           }
           if(graph2[0][ind]!=-1)
           {
             numerator = distProb[ind] * (1-graph2[0][ind]);
           }
           else
           {
             numerator = distProb[ind];  
           }
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           String index = temp15[limit-1];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }*/
        return key;
        
       
   }
   
   
   private static int knockoutPathCombo(HashMap<Integer,String[]> input, HashSet<String> available, HashMap<Integer,String[]> operations, HashMap<Integer,Integer> productions, HashSet<Integer> path)
   {
        double graph[][] = new double[52][52];
        double graph2[][] = new double [52][52];
        for(int l=0;l<52;l++)
        {
            for(int p=0;p<52;p++)
            {
                graph2[l][p]=-1;
                graph[l][p]=-1;
            }
        }
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[0].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit])+Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                graph2[one][two] = 1 - prob;
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                
                graph[two][one]=Double.parseDouble(dokimi[limit])+Double.parseDouble(dokimi[limit+1]);
                double prob = Double.parseDouble(dokimi[limit+2]);
                //graph2[one][two] = 1 - prob;
                graph2[two][one] = 1 - prob;
                
             }
            }
            
        }
        
        double dist[] = new double[52];
        double distProb[] = new double[52];
        ShortestPathDouble tp = new ShortestPathDouble();
        ShortestPathProb tpp = new ShortestPathProb();
        dist = tp.dijkstra(graph, 18);
        distProb = tpp.dijkstra(graph2, 18);
        int n = input.size();
        double[][] cost = new double[n][2];
        
        Iterator t = input.entrySet().iterator();
        int i=0;
        while(t.hasNext())
        {
          HashMap.Entry pair = (HashMap.Entry) t.next();  // takes from the dist array, which contains the distance from root for all operations, the distances
          String[] temp15= (String[]) pair.getValue(); // of the available for execution operations and saves them in the cost array
          int tempkey = (Integer) pair.getKey();
          cost[i][0] = tempkey;
          int limit = temp15.length-3;
          if(limit==1)
          {
           String index = temp15[0];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator,numerator;
            if(graph[0][ind]!=-1)
           {
             denominator = dist[ind] + graph[0][ind];
           }
           else
           {
             denominator = dist[ind];  
           }
           if(graph2[0][ind]!=-1)
           {
             numerator = distProb[ind] * (1-graph2[0][ind]);
           }
           else
           {
             numerator = distProb[ind];  
           }
           cost[i][1] = numerator/denominator;
              
          }
          else
          {
           String index = temp15[limit-1];
           int ind = Integer.parseInt(index.replace("i",""));
           double denominator = dist[ind];
           double numerator = distProb[ind];
           cost[i][1] = numerator/denominator;   
          }
          
          
          i++;
        }
        int key = (int) getMaxValue(cost); // the rule with the lowest value(distance from root) is selected for execution
        /*System.out.println(key);
        String[] temp16 = input.get(key);
        double prob = Double.parseDouble(temp16[temp16.length-1]);
        input.remove(key);
        path.add(key);
        int result = productions.get(key);
        if(result ==1) // the execution was successful and therefore the element is produced and added to the set of available elements
        {
            available.add(temp16[temp16.length-4]);
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4]);
            
        }
        else //execution was unsuccessful and therefore the element was not produced
        {
            //System.out.println("Op" + key+ "," + temp16[temp16.length-4] + " fail");
        }*/
        return key;
        
       
}
   
private static int createArrayRootBaseline(HashMap<Integer,String[]> operations,int[][] graph)
   {
       for(int i=0;i<graph.length;i++)
        {
            for(int j=0;j<graph.length;j++)
            {
                graph[i][j]=-1;
            }
        }
        
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[0].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = 1;
                
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                
                if(Double.parseDouble(dokimi[dokimi.length-2])==0)
                {
                 graph[two][one]=0;
                }
                else
                {
                 graph[two][one]=1;
                }
                
               
                
             }
            }
            
        }
        return 1;
   }
   
    private static int createArrayCostBaseline(HashMap<Integer,String[]> operations,double[][] graph)
   {
       for(int i=0;i<graph.length;i++)
        {
            for(int j=0;j<graph.length;j++)
            {
                graph[i][j]=-1;
            }
        }
        
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[0].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit+1]);
                
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                graph[two][one]=Double.parseDouble(dokimi[limit+1]);
               
                
             }
            }
            
        }
       
        return 1;
   }
    
   private static int createArrayTimeBaseline(HashMap<Integer,String[]> operations,double[][] graph)
   {
        
        for(int i=0;i<graph.length;i++)
        {
            for(int j=0;j<graph.length;j++)
            {
                graph[i][j]=-1;
            }
        }
        
        Iterator it = operations.entrySet().iterator();
        while(it.hasNext())
        {
            HashMap.Entry pair = (HashMap.Entry) it.next(); 
            String[] dokimi = (String[]) pair.getValue();
            int limit = dokimi.length-3;
            String[] te = new String[limit];
            
            if(limit==1)
            {
                int one = 0;
                String index = dokimi[0].replace("i", "");
                int two = Integer.parseInt(index);
                graph[one][two] = Double.parseDouble(dokimi[limit]);
                
            }
            else
            {
             for(int y=0;y<limit-1;y++)
             {
                String index = dokimi[y].replace("i","");
                int one = Integer.parseInt(index);
                String index2 = dokimi[limit-1].replace("i","");
                int two = Integer.parseInt(index2);
                //graph[one][two]=Double.parseDouble(dokimi[limit]);
                graph[two][one]=Double.parseDouble(dokimi[limit]);
               
                
             }
            }
            
        }
       
       
        return 1;
   }
    


    
    
}