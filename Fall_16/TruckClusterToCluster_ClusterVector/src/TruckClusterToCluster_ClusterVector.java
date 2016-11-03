import java.io.*;
import java.util.*;

public class TruckClusterToCluster_ClusterVector {
	
	public static final int OUTGOING = 0;
	public static final int INCOMING = 1;

	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader("N:/WPI/DR/Playing with data/IMPORTANT/NewDataset/Fall 16/Modified for calculations visualizations/Record_ClusterNO - Copy.csv");
		
		BufferedReader br = new BufferedReader(fr);
		
		try {
			
			String line = br.readLine(); // header
			
			//System.out.println(line);
			
			
			
			String []lineArr = line.split(",");
			
			//System.out.println(lineArr[7]);
			
			String prevTruckId = lineArr[0];
			int prevCluster;
			
			ArrayList<Integer> ar = new ArrayList<Integer>();
			
			int [][]m = new int[42][2];
			
			do{
				line = br.readLine().trim();
				lineArr = line.split(",");
				prevTruckId = lineArr[0];
				prevCluster = Integer.parseInt(lineArr[7].trim());
				
				if(prevCluster != -1){
					ar.add(prevCluster);
					
					//m[prevCluster][OUTGOING] = m[prevCluster][OUTGOING] + 1;
					
					break;
				} 
			} while(true); // if all cluster IDs are -1 then infinite loop
			
			
			
			String currTruckId = prevTruckId;
			int currCluster = prevCluster;
			
			PrintWriter pw = new PrintWriter("N:/WPI/DR/Playing with data/IMPORTANT/NewDataset/Fall 16/JavaClusterToCluster_ClusterVector_Outputs/JavaClusterToCluster.csv");
			PrintWriter mPw = new PrintWriter("N:/WPI/DR/Playing with data/IMPORTANT/NewDataset/Fall 16/JavaClusterToCluster_ClusterVector_Outputs/JavaClusterVector.csv");
			
			System.out.println(line);
			
			while((line = br.readLine()) != null){
				line = line.trim();
				lineArr = line.split(",");
				System.out.println(line);
				currTruckId = lineArr[0];
				System.out.println(currTruckId);
				currCluster = Integer.parseInt(lineArr[7].trim());
				
				if(prevTruckId.equals(currTruckId)){
					if(prevCluster != currCluster && currCluster != -1){
						ar.add(currCluster);
						
						if(prevCluster != -1)
							m[prevCluster][OUTGOING] = m[prevCluster][OUTGOING] + 1;
						
						prevCluster = currCluster;
						
						// this is correct even if the 1st cluster for a new truck is -1;
						// as the when we get a proper cluster, the truck is still incoming 
						// to that new cluster from some unknown (non-cluster) location
						m[currCluster][INCOMING] = m[currCluster][INCOMING] + 1; 
					}
				
				}
				else{
					pw.write(prevTruckId+","+ar+"\n");
					prevTruckId = currTruckId;
					prevCluster = currCluster;
					ar.clear();
					
					if(currCluster != -1){
						ar.add(currCluster);
						//m[currCluster][OUTGOING] = m[currCluster][OUTGOING] +1;
					}
				}
			}
			
			if(!ar.isEmpty()){
				pw.write(prevTruckId+","+ar+"\n");
			}
			
			// writing the cluster vector
			mPw.write("Cluster ID,OUTGOING Edges, INCOMING Edges\n");
			for(int i = 0; i<42; i++){
				mPw.write(i+","+m[i][OUTGOING]+","+m[i][INCOMING]+"\n");
			}
			
			pw.close();
			mPw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			br.close();
		}
		
		
	}

}
