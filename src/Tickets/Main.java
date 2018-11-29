/*
     * Grant Poquiz
     * gep160230
     * 2336.003
     * Jason Smith
     * 10/16/2018
     * This program is intended to read in a movie theater, prompt for sales, and then output
     * Updated to use Quadruply linked lists 
*/
package Tickets;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main
{

	public static void main(String[] args)
	{
		Menu Interface = new Menu();
		// scanner for input of file and user commands
		String file1Name = "A1base.txt";
		String file2Name = "A2base.txt";
		String file3Name = "A3base.txt";
	
		// class for our auditorium
		// if we cannot find the file, exit the program
		Auditorium TheaterOne = null;
		Auditorium TheaterTwo = null;
		Auditorium TheaterThree = null;
		try
		{
			TheaterOne = new Auditorium(new Scanner(new File(file1Name)));
			TheaterTwo = new Auditorium(new Scanner(new File(file2Name)));
			TheaterThree = new Auditorium(new Scanner(new File(file3Name)));
			TheaterOne.display();
			TheaterTwo.display();
			TheaterThree.display();
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e);
			System.exit(1);	
		}
		
		// change the scanner to use System.in
		Scanner userIn = new Scanner(System.in);
		Interface.mainMenu(userIn, TheaterOne);
		
		
		userIn.close();
		TheaterOne.printReport(file1Name);
		TheaterTwo.printReport(file2Name);
		TheaterThree.printReport(file3Name);
		System.exit(0);
	}
}
