package com.careydevelopment.fun.streams;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InstatiationTechniques {

	private static final String OUTPUT_FILE = "./files/outputfile.txt";
	private static final String INPUT_FILE = "./files/inputfile.txt";
	
	private FakeRepository repository = new FakeRepository();
	
	public static void main (String... args) {
		InstatiationTechniques it = new InstatiationTechniques();
		it.go();
	}

	private void go() {
		playWithArray();
		playWithList();
		playWithBuilder();
		playWithMap();
		playWithFilter();
		playWithFlatMap();
		playWithPeek();
		playWithCount();
		playWithSkipAndLimit();
		playWithSorting();
		playWithMatching();
		playWithJoining();
		playWithPartitioning();
		playWithGrouping();
		playWithFiles();
	}
	
	private void playWithFiles() {
		System.err.println("\n\n\n--- STARTING FILES ---");
		
		File file = new File (OUTPUT_FILE);
		if (file.exists()) file.delete();
		
		List<Employee> empList = Arrays.asList(repository.getAllEmployees());

		try (PrintWriter pw = new PrintWriter(
				Files.newBufferedWriter(Paths.get(OUTPUT_FILE)))) {
			empList.stream().forEach(pw::println);
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		
		try {
			Path path = Paths.get(INPUT_FILE);
			Stream<String> names = Files.lines(path);
			names.forEach(System.err::println);
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		
		System.err.println("--- FINISHED FILES ---");		
	}

	private void playWithGrouping() {
		System.err.println("\n\n\n--- STARTING GROUP ---");
		
		List<Employee> empList = Arrays.asList(repository.getAllEmployees());
	    
		Map<Character, List<Employee>> groupByAlphabet = empList.stream().collect(
			      Collectors.groupingBy(e -> new Character(e.getFirstName().charAt(0))));
		
		for (Character letter : groupByAlphabet.keySet()) {
			System.err.println("Starts with " + letter);
			groupByAlphabet.get(letter).stream().forEach(System.err::println);
		}
		
		System.err.println("--- FINISHED GROUP ---");
	}

	
	private void playWithPartitioning() {
		System.err.println("\n\n\n--- STARTING PARTITION ---");
		
		List<Employee> empList = Arrays.asList(repository.getAllEmployees());
	    
		Map<Boolean, List<Employee>> startsWithJ = empList.stream().collect(
			      Collectors.partitioningBy(e -> e.getFirstName().startsWith("j")));
			    
		System.err.println("Number who start with j " + startsWithJ.get(true).size());
		System.err.println("Number who don't start with j " + startsWithJ.get(false).size());
		
		System.err.println("--- FINISHED PARTITION ---");
	}

	
	private void playWithJoining() {
		System.err.println("\n\n\n--- STARTING JOIN ---");
		
		List<Employee> empList = Arrays.asList(repository.getAllEmployees());
	    
		String empNames = empList.stream()
			      .map(Employee::getLastName)
			      .collect(Collectors.joining(", "))
			      .toString();
		
		System.err.println(empNames);
		
		System.err.println("--- FINISHED JOIN ---");
	}

	
	private void playWithMatching() {
		System.err.println("\n\n\n--- STARTING MATCH ---");
		
		List<Employee> empList = Arrays.asList(repository.getAllEmployees());
	    
		boolean alljs = empList.stream()
				.allMatch(e -> e.getFirstName().startsWith("j"));
		
		boolean anyjs = empList.stream()
				.anyMatch(e -> e.getFirstName().startsWith("j"));
		
		System.err.println("All match is " + alljs);
		System.err.println("Any match is " + anyjs);
		
		System.err.println("--- FINISHED MATCH ---");
	}

	
	private void playWithSorting() {
		System.err.println("\n\n\n--- STARTING SORT ---");
		
		List<Employee> empList = Arrays.asList(repository.getAllEmployees());
	    
		List<Employee> sortedList = empList.stream()
			      .sorted((e1, e2) -> e1.getLastName().compareTo(e2.getLastName()))
			      .collect(Collectors.toList());
		
		sortedList.stream().forEach(System.err::println);
		
		System.err.println("--- FINISHED SORT ---");
	}

	
	private void playWithSkipAndLimit() {
		System.err.println("\n\n\n--- STARTING SKIP ---");
		
		List<Employee> empList = Arrays.asList(repository.getAllEmployees());
	    
		List<Employee> newList = empList.stream()
				.skip(1)
				.limit(1)
				.collect(Collectors.toList());
		
		newList.stream().forEach(System.err::println);
		
		System.err.println("--- FINISHED SKIP ---");
	}

	
	private void playWithCount() {
		System.err.println("\n\n\n--- STARTING COUNT ---");
		
		List<Employee> empList = Arrays.asList(repository.getAllEmployees());
	    
	    long count = empList.stream()
	    		.filter(e -> e.getId() > 1l)
	    		.count();
	    
	    System.err.println("Count is " + count);
		
		System.err.println("--- FINISHED COUNT ---");
	}

	
	private void playWithPeek() {
		System.err.println("\n\n\n--- STARTING PEEK ---");
		
		List<Employee> empList = Arrays.asList(repository.getAllEmployees());
	    
	    List<Employee> newList = empList.stream()
	      .peek(e -> e.setDepartment("OPS"))
	      .collect(Collectors.toList());

	    newList.stream().forEach(System.err::println);
		
		System.err.println("--- FINISHED PEEK ---");
	}

	
	private void playWithFlatMap() {
		System.err.println("\n\n\n--- STARTING FLATMAP ---");
		
		List<List<String>> namesNested = Arrays.asList( 
			      Arrays.asList("Jeff", "Bezos"), 
			      Arrays.asList("Bill", "Gates"), 
			      Arrays.asList("Mark", "Zuckerberg"));

		List<String> namesFlatStream = namesNested.stream()
			      .flatMap(Collection::stream)
			      .collect(Collectors.toList());
		
		namesFlatStream.stream().forEach(System.err::println);
		
		System.err.println("--- FINISHED FLATMAP ---");
	}
	
	private void playWithFilter() {
		System.err.println("\n\n\n--- STARTING FILTER ---");
		Long[] empIds = {1l, 2l, 3l, 4l};
		
		System.err.println("Filter out null results");
		List<Employee> employees = Stream.of(empIds)
				.map(repository::findById)
				.filter(e -> e != null)
				.collect(Collectors.toList());
		employees.stream().forEach(System.err::println);
		
		System.err.println("\n\n\nReturn just the first result");
		Employee emp = Stream.of(empIds)
			      .map(repository::findById)
			      .filter(e -> e != null)
			      .findFirst()
			      .orElse(null);
		System.err.println("Employee is " + emp);
		
		System.err.println("--- FINISHED FILTER ---");
	}
	
	private void playWithMap() {
		System.err.println("\n\n\n--- STARTING MAP ---");
		Long[] empIds = {1l, 2l};
		List<Employee> employees = Stream.of(empIds)
				.map(repository::findById)
				.collect(Collectors.toList());

		employees.stream().forEach(System.err::println);
		System.err.println("--- FINISHED MAP ---");
	}

	
	private void playWithArray() {
		System.err.println("\n\n\n--- STARTING ARRAY ---");
		Stream<Employee> stream = Stream.of(repository.getAllEmployees());
		System.err.println("--- FINISHED ARRAY ---");
	}
	
	private void playWithList() {
		System.err.println("\n\n\n--- STARTING LIST ---");
		List<Employee> list = Arrays.asList(repository.getAllEmployees());
		System.err.println("doing a for each with method reference");
		list.stream().forEach(System.err::println);
		
		System.err.println("\n\n\nconvert back to array");
		Employee[] arr = list.stream().toArray(Employee[]::new);
		for (Employee emp : arr) {
			System.err.println(emp);
		}
		System.err.println("--- FINISHED LIST ---");
	}
	
	private void playWithBuilder() {
		System.err.println("\n\n\n--- STARTING BUILDER ---");
		Employee[] emps = repository.getAllEmployees();
		
		Stream.Builder<Employee> builder = Stream.builder();
		builder.accept(emps[0]);
		builder.accept(emps[1]);
		builder.accept(emps[2]);
		
		Stream<Employee> stream = builder.build();
		System.err.println("--- FINISHED BUILDER ---");
	}
	
	
	private static class FakeRepository {
		Employee emp3 = new Employee(3l,"joe","blow","accounting");
		Employee emp2 = new Employee(2l,"john","james","it");
		Employee emp1 = new Employee(1l,"maria","radical","marketing");
		
		Employee[] getAllEmployees() {
			Employee[] emps = {emp1, emp2, emp3};
			return emps;			
		}
		
		Employee findById(Long id) {
			if (id.equals(1l)) {
				return emp1;
			} else if (id.equals(2l)) {
				return emp2;
			} else if (id.equals(3l)) {
				return emp3;
			} else {
				return null;
			}
		}
	}
}
