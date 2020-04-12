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
		if (args.length != 3) {
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
			System.out.println("The first parameter is a number.");
			System.exit(1);
		}
		
		String algorithm = args[1].toLowerCase();
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
			outWriter = new PrintWriter(new FileWriter(args[2]), true);
		}catch (IOException ioe) {
			System.out.println("Failed to load the output file.");
			System.exit(1);
		}
		
		outWriter.println("Number, Algorithm, EN Time, PT Time, ST Time, DE Time");
		
		for (Integer number : numbers) {
			runByNumbers(number, runAlgorithm, outWriter);
		}
		
		outWriter.close();

	}

	private static void runByNumbers(Integer number, Map<String, Boolean> runAlgorithm, PrintWriter outWriter) {
		
		List<String> dataList = dataPreparation(number);
		
		String targetForPT = dataList.get(dataList.size()-1);
//		String targetForPT = dataList.get(new Random().nextInt(dataList.size()));
		
		System.out.println("The number of data: " + number);
		
		
		for (String action : runAlgorithm.keySet()) {
			if (runAlgorithm.get(action)) {
				run(action, dataList, targetForPT, outWriter);
				System.out.println("-------------------------------");
			}
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
		
		System.out.println("Algorithm: " + action.toUpperCase());
		System.out.println("Scenario 1: Enqueue");
//		outWriter.write("Algorithm: " + action.toUpperCase());
//		outWriter.write("Scenario 1: Enqueue");
		Long startTime = System.currentTimeMillis();
		for(String data : dataList) {
			String[] info = data.split(",");
			queue.enqueue(info[0], Integer.valueOf(info[1]));
		}
		Long endTime = System.currentTimeMillis();
		
		long enTime = endTime-startTime;
		System.out.println(queue.toString());
		System.out.println("The time used of enqueuing: " + enTime + " ms.");
		
		System.out.println("Scenario 3: PT");
		startTime = System.currentTimeMillis();
		int sum = queue.precedingProcessTime(targetForPT.split(",")[0]);
		endTime = System.currentTimeMillis();
		
		long ptTime = endTime-startTime;
		System.out.println("The target is: " + targetForPT.split(",")[0] + ", and the sum is: " + sum);
		System.out.println("The time used of PT: " + ptTime + " ms.");
		
		System.out.println("Scenario 4: ST");
		startTime = System.currentTimeMillis();
		int sum1 = queue.succeedingProcessTime(targetForPT.split(",")[0]);
		endTime = System.currentTimeMillis();
		
		long stTime = endTime-startTime;
		System.out.println("The target is: " + targetForPT.split(",")[0] + ", and the sum is: " + sum1);
		System.out.println("The time used of ST: " + stTime + " ms.");
		
		System.out.println("Scenario 2: Dequeue");
		startTime = System.currentTimeMillis();
		for (int i = 1 ; i <= dataList.size() ; i++) {
			queue.dequeue();
		}
		endTime = System.currentTimeMillis();

		long deTime = endTime-startTime;
		System.out.println("The time used of dequeuing: " + deTime + " ms.");
		
		outWriter.println(dataList.size()+", "+action+", "+ enTime +", "+ ptTime + ", " + stTime + ", " + deTime);
	}

	private static List<String> dataPreparation(Integer number) {
		
		String filePath = System.getProperty("user.dir")+"/src/processes.txt";
		List<String> data = new ArrayList<>();
		
		BufferedReader inReader = null;
		
//		String sample = "P4974 P1623 P4466 P2964 P450 P3573 P4207 P2333 P4078 P3034 P4076 P120 P1058 P2226 P1665 P3930 P2338 P3633 P2946 P2640 P720 P957 P2499 P4591 P4839";
//		String[] array = sample.split(" ");
		
		try {
			inReader = new BufferedReader(new FileReader(filePath));
			String line;
			while ((line = inReader.readLine()) != null) {
				if (line.split(",").length == 2) {
//					boolean isFound = false;
//					for (String s : array) {
//						if (s.equals(line.split(",")[0])) {
//							isFound = true;
//							break;
//						}
//					}
//					if (isFound)
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
