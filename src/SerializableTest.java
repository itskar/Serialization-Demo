import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Author: Karthik Umashankar
 * Object Oriented Programming
 * Homework 11
 */

// Person class
class Person implements Serializable {
	String name;
	String phoneNumber;
	String dob;
	String emailAddress;
	
	// Constructor
	public Person(String name, String phoneNumber, String dob, String emailAddress) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.dob = dob;
		this.emailAddress = emailAddress;
	}
	
	// toString method
	@Override
	public String toString() {
		return "Person [name=" + name + ", phoneNumber=" + phoneNumber + ", dob=" + dob + ", emailAddress="
				+ emailAddress + "]";
	}
	
}

public class SerializableTest {

	public static void main(String[] args){
		int menuInput = 0;
		Scanner scan = new Scanner(System.in);
		ArrayList<Person> list;
		
		// Start the menu
		while(menuInput!=5) {
			System.out.println("1. Add information to a file");
			System.out.println("2. Retrieve information from a file and display them.");
			System.out.println("3. Delete information");
			System.out.println("4. Update information");
			System.out.println("5. Exit");
			
			// Read menu choice
			menuInput = scan.nextInt();
			
			switch(menuInput){
			// Add a list of new persons to the file. Note: this deletes the previous contents of the file
			case 1:
				list = new ArrayList<>();
				String response ="y";
				while(response.equals("y")) 
				{
					System.out.print("Enter the person's name: ");
					String name = scan.next();
					System.out.print("Enter the phone number: ");
					String phoneNumber = scan.next();
					System.out.print("Enter the DOB: ");
					String dob = scan.next();
					System.out.print("Enter the email address: ");
					String email = scan.next();
					System.out.println("");
					Person p = new Person (name, phoneNumber, dob, email);
					list.add(p);
					scan.nextLine();
					System.out.print("Add another person? (y/n)");
					response = scan.next().toLowerCase();
					System.out.println("");
				}
				
				// Write the end of file object after the user finished data entry
				try {
					writeToFile(list);
					System.out.println("Information added to the file! ");
					System.out.println("");
					list.clear();
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
				break;
			// Read the file and display the information from it.
			case 2:
				try {
					list = readFile();
					if(list!=null) {
						for (Person p: list) {
							System.out.println(p.toString());
							System.out.println("");
						}
					}else {
						System.out.println("List is empty, nothing to read!");
						System.out.println("");
					}
					
				} catch (ClassNotFoundException e) {
					System.out.println(e.getMessage());
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3: // Deletes all the contents of the file 
				try {
					deleteFile();
					System.out.println("Information deleted from file!");
					System.out.println("");
				} catch (IOException e1) {
					System.out.println("List is already empty");
				}
				break;
			// Adds a new person to file 
			case 4:
				System.out.print("Enter the person's name: ");
				String name = scan.next();
				System.out.print("Enter the phone number: ");
				String phoneNumber = scan.next();
				System.out.print("Enter the DOB: ");
				String dob = scan.next();
				System.out.print("Enter the email address: ");
				String email = scan.next();
				System.out.println("");
				Person p = new Person (name, phoneNumber, dob, email);
				
				try {
					updateFile(p);
					System.out.println("Information updated to the file! ");
					System.out.println("");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
	
	//Write the list of persons to the binary file
	public static void writeToFile(ArrayList<Person> p) throws IOException {
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Person.bin"));
		objectOutputStream.writeObject(p);
		objectOutputStream.close();
	}
	
	// Read and return the list of persons from the binary file
	public static ArrayList<Person> readFile() throws ClassNotFoundException, IOException {
		ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Person.bin"));
		ArrayList<Person> persons = (ArrayList<Person>)objectInputStream.readObject();
		objectInputStream.close();
		return persons;
	}
	
	//Delete all the contents of the file
	public static void deleteFile() throws FileNotFoundException, IOException {
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Person.bin"));
		objectOutputStream.writeObject(null);
		objectOutputStream.close();
	}
	
	// Add a new person to the binary file
	public static void updateFile(Person p) throws IOException, ClassNotFoundException {
		ArrayList<Person> list = readFile();
		if(list!=null) {
			list.add(p);
		}
		else {
			list = new ArrayList<>();
			list.add(p);
		}
		writeToFile(list);
	}

}
