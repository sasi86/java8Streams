package streams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamsByExample {
	
	
	public void findTheMaxAgeofPerson() {
		List<Person> persons = new ArrayList<>();
		 try (
		            BufferedReader reader = 
		                new BufferedReader(
		                    new InputStreamReader(
		                    		StreamsByExample.class.getResourceAsStream("person.txt")));

		            Stream<String> stream = reader.lines();
		        ) {
			 
			 stream.map(line -> {
	                String[] s = line.split(" ");
	                Person p = new Person(s[0].trim(), Integer.parseInt(s[1]));
	                persons.add(p);
	                return p;
	                    })
	                    .forEach(System.out::println);
			 
		 } catch (IOException ioe) {
	            System.out.println(ioe);
	        }
		 Optional<Person> opt = 
			        persons.stream().filter(p -> p.getAge() >= 20)
			                .min(Comparator.comparing(Person::getAge));
			        System.out.println(opt);
			        
			        Optional<Person> opt2 = 
			        persons.stream().max(Comparator.comparing(Person::getAge));
			        System.out.println(opt2);
			        
			        Map<Integer, String> map = 
			        persons.stream()
			                .collect(
			                        Collectors.groupingBy(
			                                Person::getAge, 
			                                Collectors.mapping(
			                                        Person::getName, 
			                                        Collectors.joining(", ")
			                                )
			                        )
			                );
			        System.out.println(map);
		
	}
	
	public void customStreamForPerson() {
		
		
		 try (Stream<String> stream = Files.lines(Paths.get("src/main/resources/person_3.txt"))) {
			 
			 Spliterator<String>  fileSpliterator = stream.spliterator();
			 Spliterator<Person> personSpliterator = new PersonSpliterator(fileSpliterator);
			 Stream<Person> personStream = StreamSupport.stream(personSpliterator, false);
			 personStream.forEach(System.out::println);
			 
		 } catch (IOException ioe) {
	            System.out.println(ioe);
	        }
	}
	
	public static void main(String[] args) {
		StreamsByExample example = new StreamsByExample();
		//example.findTheMaxAgeofPerson();
		//example.customStreamForPerson();
		
		List<Integer> result = new ArrayList<>();
		List<Integer> persons = Arrays.asList(1,2,30,40,50,4,6);
		
		persons.stream()
		.peek(System.out::println)
		.filter(ii -> ii >=20)
		.forEach(result::add);
		System.out.println(result.size());
	}

}

class Person {

    private String name;
    
    private int age;
    
    private String city;
    
    public Person(){}
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public Person(String name, int age, String city) {
        this.name = name;
        this.age = age;
        this.city = city;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getAge() {
        return this.age;
    }
    
    public String getCity() {
		return city;
	}

	public String toString() {
        return "Person [" + this.name + ", " + this.age + ", " + this.city+" ]";
    }
}

