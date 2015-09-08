package com.fajarainul.coconut_dev.titikota;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Fajar Ainul on 04/09/2015.
 */
public class processClassification {
    public processClassification(){

    }

    public void main(){

    }
    public List<String> token(String msg){
        List<String> kata_token = new ArrayList<String>();
        HashMap<String, Integer> TF = new HashMap<String, Integer>();

        StringTokenizer st = new StringTokenizer(msg.toLowerCase(),":;,(). ");

        String term;
        int freq;

        while(st.hasMoreTokens()){
            term = st.nextToken();

            if (!(TF.containsKey(term))){
                TF.put(term, 0);
            }
            freq = TF.get(term);
            TF.put(term, freq+1);
        }
        for(String key : TF.keySet()) {
            //Log.v(key, " : " + TF.get(key));
            kata_token.add(key);
        }
        /*
        * in iuntuk mengecek array saca
        *
        *
        for(int i=0; i<kata_token.size();i++){
            Log.v("Test",Integer.toString(i) + ":" + kata_token.get(i));
        }*/

        return kata_token;
    }

    /*
    * inputan adalah dokumen yang telah dilakukan tokenisasi, kata dalam topik, nilai topik
    * proses mengecek apakah kata terdapat dalam topik, jika ada maka result di isi nilai topik, jika tidak diberi nilai terkecil
    * output hasil cek
    * */

    public double[] checkWord(String[] words, String[] topik, double[] nilaiTopik){
        double[] result={0,0,0,0,0,0,0,0,0,0};

        for(int i=0;i<topik.length;i++){
            boolean testing = Arrays.asList(words).contains(topik[i]);
            Log.d("words"+i,words[i]);
            Log.d("testing" + i, Boolean.toString(testing));
            if(!testing){
                result[i] = 0.00005;
            }else{
                result[i] = nilaiTopik[i];
            }
            Log.d("result"+i,Double.toString(result[i]));
        }

        return result;

    }

    /*
    * inputan adalah p(z) dan PWZ
    * proses p(z) * SIGMA (i=1 sampai jumlah dokumen) PWZ
    * PWZ adalah hasil dari pengecekan nilai
    * output PZDnew
    * */

    public double getPZDnew(double p_z, double[] PWZ){
        double PZDnew = 0;
        double sigma = 0;
        for(int i=0; i<=PWZ.length;i++){
            sigma = sigma + PWZ[i];
        }

        PZDnew = p_z * sigma;

        return PZDnew;
    }


    public double KLD(double[] PZD, double[] PZC ){
        double KLD;
        double DPQ=0;
        double DQP=0;

        for(int i=0;i<4;i++){
            DPQ = DPQ + PZD[i]*Math.log(PZD[i]/PZC[i]);
        }

        for(int j=0;j<4;j++){
            DQP = DQP + PZC[j]*Math.log(PZC[j]/PZD[j]);
        }

        KLD = (DQP + DPQ)/2;

        return KLD;
    }

    /*
    *inputan array dari hasil perhitungan KLD antara PZDnew dengan PZC masing2 topik
    * proses mencari nilai minimum
    * nilai kembalian adalah topik mana yang paling minimum (berarti dokumen termasuk kelas itu)
    * */
    public int getClass(double[] KLD_Result){
        int index_min=0;

        for(int i=0;i<3;i++){
            if(KLD_Result[i] <= KLD_Result[i+1]){
                index_min = i;
            }else{
                index_min = i+1;
            }
        }

        return index_min;
    }



}
