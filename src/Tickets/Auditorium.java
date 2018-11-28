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
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Auditorium
{
	private Node head;
	private int rows, cols;
	
	public Auditorium() {head = null; rows = cols = 0;}	
	public int getRows() {return rows;}
	public int getCols() {return cols;}
	// read in the auditorium from a scanner
	public Auditorium(Scanner in)
	{
		// set our head to null
		head = null; 
		// track the head of our 
		Node lineHead = null;
		// track our previous line
		Node prevLine = null;
		// count the amount of rows
		// read in a line at a time
		while(in.hasNextLine()) 
		{
			// read in a line and create a null current value
			String nextLine = in.nextLine();
			Node current = null;
			// iterate through the string
			for (cols = 0;cols < nextLine.length(); cols++) // count the amount of rows and columns while reading in the lines
			{
				//create a new Node from the rows, cols, and type
				Node n = new Node(rows, cols, nextLine.charAt(cols));
				
				// if the head does not yet exist, initialize it
				if (head == null)
				{
					head = n;
					current = lineHead = head;
				}
				else
				{
					// if this is the first element of our line, create the lineHead
					if (lineHead == null)
					{
						current = lineHead = n;
					}
					// if there was a previous line(AKA this is not our first line)
					// then link each value in the previous line to the parallel value 
					// in the current line
					if (prevLine != null)
					{
						current.linkN(prevLine);
						prevLine = prevLine.getE();
					}
					// link the current value with the new Node and move the current value
					current.linkE(n);
					current = n;
				}
			}
			// after each line, set our previous line to the current line and nullify the current line
			prevLine = lineHead;
			lineHead = null;
			rows++;
		}
		// close our input stream
		in.close();
	}
	public Node getNode(int row, int col)
	{
		if (head == null)
			return null;
		Node n = head;
		for(int i = 0; i < row; i++)
			n = n.getS();
		for(int i = 0; i < col; i++)
			n = n.getE();
		return n;
	}
	public boolean checkAvailable(Node n, int numSeats)
	{
		if (((numSeats + n.getCol() - 1) < cols))
		{
			for(int i = 0; i < numSeats; i++)
				if (this.getNode(n.getRow(), i + n.getCol()).getType() != '.')
					return false;
		}
		else
		{
			return false;
		}
		return true;
	}
	public boolean seatsAvailable(Node n, int numSeats)
	{
		for(int i = 0; i < numSeats; i++)
		{
			if (n == null)
				return false;
			if (n.getType() != '.')
				return false;
			
			n = n.getE();
		}
		return true;
	}
	public Node bestAvailable(int numSeats)
	{
		if (head == null)
			return null;
		// use current to go through a line
		Node current = head;
		// use newHead to go over each line
		Node newHead = head;
		Node best = null;
		do
		{
			while(current != null)
			{
				//System.out.print(current.getType());
				if (seatsAvailable(current, numSeats))
				{
					if (best == null)
						best = current;
					else if (current.getRow() - (rows / 2.0) < current.getRow() - (rows / 2.0) && distanceToMiddle(current.getRow(), (2*current.getCol() + numSeats - 1)/2.0) <= distanceToMiddle(best.getRow(), (2*best.getCol() + numSeats - 1)/2.0))
					{
						
						//System.out.println("C: " + (current.getRow() + 1) + ", " + (char)(current.getCol() + 'A') + ": " + String.format("%.20f", distanceToMiddle(current.getRow(), (2*current.getCol() + numSeats - 1)/2.0)));
						//System.out.println("B: " + (best.getRow() + 1) + ", " + (char)(best.getCol() + 'A') + ": " + String.format("%.20f", distanceToMiddle(best.getRow(), (2*best.getCol() + numSeats - 1)/2.0)));	
						//System.out.println(distanceToMiddle(current.getRow(), (2*current.getCol() + numSeats - 1)/2.0) - distanceToMiddle(best.getRow(), (2*best.getCol() + numSeats - 1)/2.0));
						best = current;
					}
				}
				current = current.getE();
			}
			//System.out.println();
		}
		// iterate until the south side of newHead is null
		while ((newHead = current = newHead.getS()) != null);
		return best;
	}
	private double distanceToMiddle(double a, double b)
	{
		return Math.sqrt(Math.pow(a - (rows - 1)/2.0, 2) + Math.pow(b - (cols - 1)/2.0, 2));
	}
	// output this linked list to a file
	public void output(PrintStream Out)
	{
		// if we don't have a list, return
		if (head == null)
			return;
		// use current to go through a line
		Node current = head;
		// use newHead to go over each line
		Node newHead = head;
		do
		{
			while(current != null)
			{
				Out.print(current.getType());
				current = current.getE();
			}
			Out.println();
		}
		// iterate until the south side of newHead is null
		while ((newHead = current = newHead.getS()) != null);
	}
	public void display()
	{
		char alphabet = 'A'; // output a row of letters
		System.out.print("   ");
		for (int c = 0; c < cols; c++)
		{
			System.out.print(alphabet);
			alphabet++;
		}
		System.out.println();
		// if we don't have a list, return
		if (head == null)
			return;
		// use current to go through a line
		Node current = head;
		// use newHead to go over each line
		Node newHead = head;
		int r = 0;
		do
		{
			System.out.print(r + 1 + ((r != 9) ? ("  ") : (" "))); // output a column of numbers
			r++;
			while(current != null)
			{
				System.out.print((current.getType()=='.') ? ('.') : ('#'));
				current = current.getE();
			}
			System.out.println();
		}
		// iterate until the south side of newHead is null
		while ((newHead = current = newHead.getS()) != null);
		
	}
	public void printReport(String filename)
	{

		int adultSales = 0, childSales = 0, seniorSales = 0;
		// if we don't have a list, return
		if (head == null)
			return;
		// use current to go through a line
		Node current = head;
		// use newHead to go over each line
		Node newHead = head;
		try
		{
			PrintWriter fileOut = new PrintWriter(new File(filename));
			do
			{
				while(current != null)
				{
					switch (current.getType())
					{
						case 'A':
							adultSales++;
							break;
						case 'C':
							childSales++;
							break;
						case 'S':
							seniorSales++;
							break;
						default:
					}
					fileOut.print(current.getType());
					current = current.getE();
				}
				fileOut.println();
			}
			// iterate until the south side of newHead is null
			while ((newHead = current = newHead.getS()) != null);
			
			fileOut.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File Not Found");
		}
		
		System.out.println("Total seats:  " + (rows * cols));
		System.out.println("Total sold:   " + (adultSales + childSales + seniorSales));	
		System.out.println("Adult Sold:   " + (adultSales));		
		System.out.println("Child sold:   " + (childSales));	
		System.out.println("Senior sold:  " + (seniorSales));
		System.out.println("Total sales: $" + String.format("%.2f", 10 * adultSales + 5 * childSales + 7.5 * seniorSales));

	}
}
