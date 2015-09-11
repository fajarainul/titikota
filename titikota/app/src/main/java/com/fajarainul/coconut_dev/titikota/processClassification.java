package com.fajarainul.coconut_dev.titikota;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Fajar Ainul on 04/09/2015.
 */
public class processClassification {
    Context context;
    public processClassification(Context context){
        this.context = context;

    }

    public int main(String documentTweet){

        int resultClass = 0;//kembalian nilai kelass
        /*
        Cursor cursor = context.getContentResolver().query(TitiKotaContract.TweetEntry.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Log.e("Test",  );
            cursor.moveToNext();
        }*/
        //topik 1 ==> padat merayap
        //topik 2 ==> macet total
        //topik 3 ==> ramai lancar
        //topik 4 ==> lancar

        double[][] PZC = {
                    {0.00013738,0.00017593, 0.00014463, 0.0001744},//topik1
                    {0.00013488, 0.00017593, 0.00014463, 0.0001744},//topik2
                    {0.0028, 0.00040411, 0.00065847, 0.000794},//topik3
                    {0.0145, 0.0148, 0.0156, 0.0147},//topik4
                    };
        String[][] topik ={
                        {"merayap","padat","arah","ke","dari","lintas","20","lalu","tmcpoldametro","tol"}, //Topik1
                        {"macet","total","arah","tol","di","lalin","menuju","rt","barat","fatmawati"},      //Topik2
                        {"lancar","ramai","lalin","arah","jl","di","terpantau","tmcpoldametro","wib","radioelshinta"}, //Topik3
                        {"tmcpoldametro","jalan","lancar","tol","arah","jakarta","di","cc","lalin","menuju"}};      //Topik4

        double[][] nilaiTopik = {
                        {0.06014, 0.06014, 0.05647, 0.03442, 0.03197, 0.02952, 0.02829, 0.02707, 0.02340, 0.02217}, //nilaiTopik1
                        {0.08253, 0.07787, 0.02347, 0.02347, 0.01725, 0.01570, 0.01414, 0.01414, 0.01259, 0.01104}, //nilaiTopik2
                        {0.07450, 0.05624, 0.05493, 0.03145, 0.03014, 0.02623, 0.02492, 0.02101, 0.01970, 0.01840}, //nilaiTopik3
                         {0.07727, 0.07418, 0.07110, 0.03871, 0.03717, 0.01712, 0.01558, 0.01403, 0.01403, 0.01249} }; //nilaiTopik4

        double[] p_z_topik = {
                        0.28400, //p_z_topik1
                        0.22382, //p_z_topik2
                        0.26661, //p_z_topik3
                        0.22556}; //p_z_topik4


        List<String> resultToken = token(documentTweet); //melakukan tokenisasi
        /*for(int i=0;i<resultToken.size();i++){
            Log.w("hasil token "+Integer.toString(i),resultToken.get(i));
        }*/
        //Log.d("DOKUMEN YANG DIUJI",documentTweet);

        //menghitung PZDnew
        double[][] resultPWZ = new double[4][10];
        double[] resultPZDnew = new double[4];
        for(int i=0;i<4;i++){
            resultPWZ[i] = checkWord(resultToken,topik[i],nilaiTopik[i]);//mencari PWZ untuk setiap topik
            //Log.w("PZD"+i,Double.toString(resultPZDnew[i]));
        }
        //Log.d("CEKSAMA","TRUE");
        boolean ketemu=false;
        int x = 0;
        while((!ketemu)&&(x<4)){
           // Log.d("CEKSAMA ITERASI TOPIK KE "+x,Boolean.toString(ketemu));
            ketemu = cekSama(resultPWZ[x]);
            x=x+1;
        }
        if(!ketemu){
            resultClass = 3;
            return resultClass;
        }

        for(int i=0;i<4;i++){
            resultPZDnew[i] = getPZDnew(p_z_topik[i],resultPWZ[i]); //mencari PZD new
        }

        //menghitung KLD antara PZDnew dengan masing2 PZC
        double[] resultKLD = new double[4];

        for(int i=0; i<4;i++){
            resultKLD[i] = KLD(resultPZDnew,PZC[i]);
            //Log.d("RESULT KLD",Double.toString(resultKLD[i]));
        }


        //mencari nilai minimum dari hasil KLD

        resultClass = getClass(resultKLD);

        //Log.d("HASIL KELAS",Integer.toString(resultClass));
        return resultClass;

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

    public double[] checkWord(List<String> words, String[] topik, double[] nilaiTopik){
        double[] result={0,0,0,0,0,0,0,0,0,0};

        for(int i=0;i<topik.length;i++){
           // boolean testing = Arrays.asList(words).contains("tol");
            boolean testing = words.contains(topik[i]);
            //Log.d("testing" + i, Boolean.toString(testing)+"==>"+topik[i]);
            if(!testing){
                result[i] = 0.0000000001;
            }else{
                result[i] = nilaiTopik[i];
            }
            //Log.d("result"+i,Double.toString(result[i]));
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
        for(int i=0; i<PWZ.length;i++){
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
            DPQ = DPQ + (PZD[i]*Math.log(PZD[i]/PZC[i]));
        }

        for(int j=0;j<4;j++){
            DQP = DQP + (PZC[j]*Math.log(PZC[j]/PZD[j]));
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
       double nilai_min = KLD_Result[0];

        for(int i=0;i<3;i++){
            if(nilai_min <= KLD_Result[i]){
                nilai_min = nilai_min;
            }else{
                nilai_min = KLD_Result[i];
                index_min = i;
            }
        }
        //Log.d("RESULT CLASS",Integer.toString(index_min));
        return index_min;
    }

    public boolean cekSama(double[] PWZ){
        boolean ketemu=false;
        int i = 0;
        double var = 0.0000000001;
        //Log.d("VAR",Double.toString(var));
        while((!ketemu)&&(i<10)){
            if(PWZ[i]==var){
                //Log.e("PWZ "+i,Double.toString(PWZ[i]));
                i=i+1;

            }else{
                ketemu=true;
            }
        }
        return ketemu;
    }





}
