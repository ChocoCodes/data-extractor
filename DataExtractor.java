import java.io.*;
import java.util.Scanner;

public class DataExtractor {
    private static final String DIR = "Datasets/";
    public static void main(String[] args) {
        String btc = DIR + "BTC.csv", eth = DIR + "ETH.csv", sol = DIR + "SOL.csv";
        String[] dataBTC = getData(btc), dataETH = getData(eth), dataSOL = getData(sol);
        if (dataBTC.length == 0 || dataETH.length == 0 || dataSOL.length == 0) {
            System.err.println("Error retrieving data.");
        }
        String[] domainOpenBTC = getMinAndMax(dataBTC,'o'), rangeVolBTC = getMinAndMax(dataBTC,'v');

        System.out.println("================================================================================");
        System.out.printf("[BTC Open] Min: %s, Max: %s\n", domainOpenBTC[0], domainOpenBTC[1]);
        System.out.printf("[BTC Volume] Min: %s, Max: %s\n", rangeVolBTC[0], rangeVolBTC[1]);
        System.out.println("================================================================================");
        String[] domainOpenETH = getMinAndMax(dataETH,'o'), rangeVolETH = getMinAndMax(dataETH,'v');
        System.out.printf("[ETH Open] Min: %s, Max: %s\n", domainOpenETH[0], domainOpenETH[1]);
        System.out.printf("[ETH Volume] Min: %s, Max: %s\n", rangeVolETH[0], rangeVolETH[1]);
        System.out.println("================================================================================");
        String[] domainOpenSOL = getMinAndMax(dataSOL,'o'), rangeVolSOL = getMinAndMax(dataSOL,'v');
        System.out.printf("[SOL Open] Min: %s, Max: %s\n", domainOpenSOL[0], domainOpenSOL[1]);
        System.out.printf("[SOL Volume] Min: %s, Max: %s\n", rangeVolSOL[0], rangeVolSOL[1]);
        System.out.println("================================================================================");

        double[] resBTC = getVolumeInMillions(dataBTC);
        for(double i : resBTC) {
            System.out.printf("%.4f, ", i);
        }
        System.out.println("\n================================================================================");
        String[] openData = getSubData(dataBTC);
        for(String x : openData) {
            if (x == null) continue;
            System.out.printf("%s, ", x);
        }
        System.out.println("\n================================================================================");        
        
    }

    private static String[] getSubData(String[] data) {
        String[] subData = new String[data.length];
        for(int i = 0; i < data.length; i++) {
            if (i == 0) continue;
            subData[i] = data[i].split(",")[0];
        }
        return subData;
    }

    private static double[] getVolumeInMillions(String[] data) {
        double[] res = new double[data.length];
        int n = 1000000;
        for(int i = 0; i < data.length; i++) {  
            if (i == 0) continue;
            res[i] = Double.parseDouble(data[i].split(",")[1]) / n;
        }
        return res;
    }

    private static String[] getMinAndMax(String[] data, char flag) {
        int[] volume = new int[data.length];
        double[] open = new double[data.length];
        String[] minMaxPair = new String[2];

        double min2, max2;
        int min1, max1;
        min1 = max1 = Integer.parseInt(data[1].split(",")[1]);
        min2 = max2 = Double.parseDouble(data[1].split(",")[0]);

        for(int i = 0; i < data.length; i++) {
            if (i == 0) continue; 
            String[] tmp = data[i].split(",");
            open[i] = Double.parseDouble(tmp[0]);
            volume[i] = Integer.parseInt(tmp[1]);

            switch (flag) {
                case 'o':
                    if (open[i] < min2) {
                        min2 = open[i];
                    }
                    if (open[i] > max2) {
                        max2 = open[i];
                    }
                    minMaxPair[0] = Double.toString(min2);
                    minMaxPair[1] = Double.toString(max2);
                    break;
                case 'v':
                    if (volume[i] < min1) {
                        min1 = volume[i];
                    }
                    if (volume[i] > max1) {
                        max1 = volume[i];
                    }
                    minMaxPair[0] = Integer.toString(min1);
                    minMaxPair[1] = Integer.toString(max1);
                    break;
            }
        }
        return minMaxPair;
    }

    private static String[] getData(String filename) {
        File fin = new File(filename);
        if (!fin.exists()) {
            return new String[0];
        }
        int n = 0;
        String[] dataArr = new String[n];

        try (Scanner frdr = new Scanner(fin)) {
            while(frdr.hasNextLine()) {
                n++;
                String tmpdata = frdr.nextLine();
                String[] tmpArr = new String[n];
                for(int i = 0; i < dataArr.length; i++) {
                    tmpArr[i] = dataArr[i];
                }
                tmpArr[tmpArr.length - 1] = tmpdata;
                dataArr = tmpArr;
            }
            frdr.close();
        } catch (FileNotFoundException e) {
            return new String[0];
        }
        return dataArr;
    }
}