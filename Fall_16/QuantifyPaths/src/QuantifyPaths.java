import java.io.*;

public class QuantifyPaths {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int[][] clusterAdjMat = new int[42][42];
		
		try{
			FileReader f = new FileReader("N:/WPI/DR/Playing with data/IMPORTANT/NewDataset/Fall 16/JavaClusterToCluster_ClusterVector_Outputs/JavaClusterToCluster.csv");
			
			//System.out.println(f);
			
			BufferedReader bf = new BufferedReader(f);
			
			bf.readLine();
			
			String strLine;
			
			while((strLine = bf.readLine()) != null){
				String[] arrLine = strLine.split(",");
				
				System.out.println(strLine);
				
				for(int i = 1; i<arrLine.length - 1; i++){
					String startElem = arrLine[i].replace("[", "").replace("]", "").trim();
					
					int startCluster = -1;
					
					if(startElem.equals("") == false)
						startCluster = Integer.parseInt(startElem);
					
					int endCluster = -1;
					
					if(i+1 < arrLine.length){
						String endElem = arrLine[i+1].replace("[", "").replace("]", "").trim();
						
						if(endElem.equals("") == false)
							endCluster = Integer.parseInt(endElem);
					}
						 
					
					if(endCluster != -1)
						clusterAdjMat[startCluster][endCluster] += 1;
				}
				
			}
			
			FileWriter fw = new FileWriter("N:/WPI/DR/Playing with data/IMPORTANT/NewDataset/Fall 16/JavaQuantifyPathsOutput/JavaPathsQuantified.csv");
			
			BufferedWriter bw = new BufferedWriter(fw);
			
			
			
			for(int i = 0; i<clusterAdjMat.length; i++){
				System.out.println(clusterAdjMat[i][0]);
				StringBuilder out = new StringBuilder(String.valueOf(clusterAdjMat[i][0]));
				//System.gc();
				for(int j = 1; j<clusterAdjMat[i].length; j++){
					out.append("," + clusterAdjMat[i][j]); 
				}
				bw.write(out.toString()+"\n");
				
			}
			
			bw.close();
			bf.close();
					
		}
		catch(IOException e){
			
			System.out.println(e.getMessage());
			
		}
	}

}
