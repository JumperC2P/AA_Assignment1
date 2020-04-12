package aa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import aa.runquene.BinarySearchTreeRQ;
import aa.runquene.OrderedArrayRQ;
import aa.runquene.OrderedLinkedListRQ;
import aa.runquene.impl.Runqueue;


/**
 * @author Chih-Hsuan Lee <s3714761>
 *
 */
public class DataGenerator {
	
	public static void main(String[] args) {
		
		// number, algorithm, output path
		if (args.length != 4) {
			System.out.println("The number of parameters must be 3. The order is number(max.5000), algorithm, output file path");
		}
		
		String[] numberString = null;
		List<Integer> numbers = new ArrayList<>();
		try {
			numberString = args[0].split(",");
			for (String number : numberString) {
				numbers.add(Integer.valueOf(number));
			}
		}catch (NumberFormatException e) {
			System.out.println("The first parameter must be number or a list of numbers.");
			System.exit(1);
		}
		
		Integer turns = null;
		try {
			turns = Integer.valueOf(args[1]);
		}catch (NumberFormatException e) {
			System.out.println("The second parameter must be a number.");
			System.exit(1);
		}
		
		String algorithm = args[2].toLowerCase();
		Map<String, Boolean> runAlgorithm = new HashMap<>();
		runAlgorithm.put("array", false);
		runAlgorithm.put("list", false);
		runAlgorithm.put("tree", false);
		
		
		switch (algorithm) {
			case "array":
				runAlgorithm.put("array", true);
				break;
			case "list":
				runAlgorithm.put("list", true);
				break;
			case "tree":
				runAlgorithm.put("tree", true);
				break;
			case "all":
				runAlgorithm.put("array", true);
				runAlgorithm.put("list", true);
				runAlgorithm.put("tree", true);
				break;
			default:
				System.out.println("Unknown implmementation type.");
				System.exit(1);
				break;
		}
		
		PrintWriter outWriter = null;
		
		try {
			outWriter = new PrintWriter(new FileWriter(args[3]), true);
		}catch (IOException ioe) {
			System.out.println("Failed to load the output file.");
			System.exit(1);
		}
		
		outWriter.println("Number, Algorithm, EN Time, PT Time, PT Sum, ST Time, ST Sum, DE Time");
		
		for (Integer number : numbers) {
			runByNumbers(turns, number, runAlgorithm, outWriter);
		}
		
		outWriter.close();
		System.out.println("Please check the file:" + args[3]);

	}

	private static void runByNumbers(Integer turns, Integer number, Map<String, Boolean> runAlgorithm, PrintWriter outWriter) {
		
		int i = 1;
		while (i <= turns) {
			List<String> dataList = dataPreparation(number);
			
//			String targetForPT = dataList.get(dataList.size()-1);
			String targetForPT = dataList.get(new Random().nextInt(dataList.size()));
			
			for (String action : runAlgorithm.keySet()) {
				if (runAlgorithm.get(action)) {
					System.out.println(number + ": "+ action + ": " + i);
					run(action, dataList, targetForPT, outWriter);
				}
			}
			i++;
		}
		
	}

	private static void run(String action, List<String> dataList, String targetForPT, PrintWriter outWriter) {
		
		
		Runqueue queue = null;
		switch (action) {
			case "array":
	            queue = new OrderedArrayRQ();
	            break;
	        case "list":
	            queue  = new OrderedLinkedListRQ();
	            break;
	        case "tree":
	            queue = new BinarySearchTreeRQ();
	            break;
	        default:
				System.out.println("Unknown implmementation type.");
				System.exit(1);
				break;
		}
		
		Long startTime = System.nanoTime();
		for(String data : dataList) {
			String[] info = data.split(",");
			queue.enqueue(info[0], Integer.valueOf(info[1]));
		}
		Long endTime = System.nanoTime();
		
		long enTime = (endTime-startTime)/1000;
		startTime = System.nanoTime();
		int ptSum = queue.precedingProcessTime(targetForPT.split(",")[0]);
		endTime = System.nanoTime();
		
		long ptTime = (endTime-startTime)/1000;
		startTime = System.nanoTime();
		int stSum = queue.succeedingProcessTime(targetForPT.split(",")[0]);
		endTime = System.nanoTime();
		
		long stTime = (endTime-startTime)/1000;
		startTime = System.nanoTime();
		String deLabel ="";
		String pre = "";
		do {
			deLabel = queue.dequeue();
			if (pre.equals(deLabel)) {
				System.out.println(deLabel);
			}else {
				pre = deLabel;
			}
		} while (deLabel.trim().length() > 0);
		endTime = System.nanoTime();

		long deTime = (endTime-startTime)/1000;
		
		outWriter.println(dataList.size()+", "+action+", "+ enTime +", "+ ptTime + ", " + ptSum + ", " + stTime + ", " + stSum + ", " + deTime);
	}

	private static List<String> dataPreparation(Integer number) {
		
		String filePath = System.getProperty("user.dir")+"/src/processes.txt";
		List<String> data = new ArrayList<>();
		
		BufferedReader inReader = null;
		
		try {
			inReader = new BufferedReader(new FileReader(filePath));
			String line;
			while ((line = inReader.readLine()) != null) {
				if (line.split(",").length == 2) {
					data.add(line);
				}
			}
		}catch(IOException e) {
			System.err.println("Cannot load the process.txt file.");
			System.exit(1);
		}finally {
			if (inReader != null)
				try {
					inReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		if (data.size() == 0) {
			System.err.println("Cannot load the process.txt file to a list.");
			System.exit(1);
		}
		
		if (number != data.size()) {
			List<String> partOfdata = new ArrayList<>();
			Random random = new Random();
			int count = 1;
			while (count <= number) {
				int randomNumber = random.nextInt(data.size());
				String target = data.get(randomNumber);
				partOfdata.add(target);
				data.remove(target);
				count++;
			}
			
			return partOfdata;
		}
		
		return data;
	}

}
