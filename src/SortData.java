import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SortData {
	
	void work(int bufferSize) {
		System.out.println(bufferSize);
		File file = new File("input.txt");
		 
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		  String st;
		  int fileCount = 0;
		  try {
			while ((st = br.readLine()) != null) {
				ArrayList<String> data = new ArrayList<String>();
				data.add(st);
				for(int i = 1; i < bufferSize && (st = br.readLine()) != null; i++) {
				    data.add(st);
					System.out.println(st);				    				
				}
			
				data.sort((p1, p2) -> p1.compareTo(p2));
				writeToFile((String.valueOf(fileCount)+ ".txt"), data);
				
				fileCount++;

			}	
			
			mergeFiles(fileCount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	}
	
	void writeToFile(String fileName, ArrayList<String> data ){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		for(int i =0 ; i< data.size(); i++) {
			writer.println(data.get(i));
		}
		writer.close();
	}
	
	
	void mergeFiles(int fileCount){
		
		
		ArrayList<File> files = new ArrayList<>(fileCount);
		ArrayList<BufferedReader> bfs = new ArrayList<>(fileCount);
		ArrayList<String> input = new ArrayList<>(fileCount);
		ArrayList<String> inputLocation = new ArrayList<>(fileCount);
		ArrayList<Boolean> working = new ArrayList<>(fileCount);
		 
		//initial step
		for(int i = 0; i < fileCount; i++) {
			try {
				File file = new File(String.valueOf(i) + ".txt");
				BufferedReader br = new BufferedReader(new FileReader(file));
				
				bfs.add(br);
				String st = br.readLine();
				if(st != null) {
					input.add(st);
					inputLocation.add(st);
					working.add(true);
				}
				else {
					working.add(false);
				}

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		PrintWriter writer = null;
		try {
			writer = new PrintWriter("result.txt", "UTF-8");
		}
		catch (Exception e) {
			// TODO: handle exception
		}	

		while(working.stream().anyMatch(Boolean.TRUE::equals)) {
			//flow
			try{
				input.sort((p1, p2) -> p1.compareTo(p2));
			}
			catch (Exception e){
				System.err.println("gfgfg");
			}
			String first = input.get(0);

			writer.println(first);
			int index = 0;
			for(int i = 0; i < fileCount; i++) {
				if(inputLocation.get(i).equals(first)) {
					index = i;
					break;
				}
			}

			input.remove(0);
			
			BufferedReader localBr = bfs.get(index);
			try {
				String localSt = localBr.readLine();
				if(localSt != null) {
					input.add(localSt);
					inputLocation.set(index, localSt);
				}
				else {
					working.set(index, false);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		
		writer.close();
	}
}
