package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Student database querying program. Query formats are available in the task asignment.
 * 
 * @author fabjanvucina
 */
public class StudentDB {

	public static void main(String[] args) throws IOException {
		
		//create student database
		List<String> studentStrings = Files.readAllLines(Paths.get("./src/main/resources/database.txt"), StandardCharsets.UTF_8);
		StudentDatabase db = new StudentDatabase(studentStrings);
		
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			
			//prompt for user to write query
			System.out.print("> ");
			
			//read command type
			String command = sc.next();
			
			//end program if user inputs "exit"
			if(command.equals("exit")) {
				System.out.println("Goodbye!");
				sc.close();
				return;
			}
			
			//process query if user inputs a query command
			else if(command.equals("query")) {
				
				//parse query and add queried students into a list
				QueryParser parser = new QueryParser(sc.nextLine().trim());
				List<StudentRecord> outputRecords = new ArrayList<>();
				
				//use index for direct queries
				if(parser.isDirectQuery()) {
					outputRecords.add(db.forJMBAG(parser.getQueriedJMBAG()));
					System.out.println("Using index for record retrieval");
				}
				
				//use filter for composite queries
				else {
					for(StudentRecord record : db.filter(new QueryFilter(parser.getQuery()))) {
						outputRecords.add(record);
					}
				}
				
				//format filtered student records
				List<String> output = RecordFormatter.format(outputRecords);
				
				//size == 2 would mean that we only have the borders
				if(output != null && output.size() > 2) {
					output.forEach(System.out::println);
				}
				
				System.out.println("Records selected: " + (output == null ? "0" : outputRecords.size()));
				System.out.println();
				
			}
			
			//invalid command
			else {
				System.out.println(command + " is an invalid command. Valid commands are: query, exit");
			}
			
		}
		
	}

}
