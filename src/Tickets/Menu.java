package Tickets;

import java.util.Scanner;

public class Menu
{

	public void loginMenu()
	{
		
	}
	public void mainMenu(Scanner userIn, Auditorium Theater)
	{
		// our main menu loop
		int choice = 0;
		do 
		{
			// prompt for menu choice
			choice = promptInput("1. Reserve Seats\n2. Exit", "Please enter either 1 or 2", 1, 2, userIn);
			
			int adults = 0, children = 0, seniors = 0;
			if (choice != 2)
			{
				// prompt for seat information
				Theater.display();
				int row = promptInput("Choose a row", "Please enter a valid row number", 1, Theater.getRows(), userIn) - 1;
				int col = promptInput("Choose a column", "Please enter a valid column letter", 'A', 'A' + Theater.getCols() - 1, userIn) - 'A';
				adults = (promptInput("How many adults?", "Please enter a number greater than 0 and less than " + Theater.getCols() + 1, 0, Theater.getCols(), userIn));
				children = (promptInput("How many children?", "Please enter a number greater than 0 and less than " + Theater.getCols() + 1, 0, Theater.getCols(), userIn));
				seniors = (promptInput("How many seniors?", "Please enter a number greater than 0 and less than " + Theater.getCols() + 1, 0, Theater.getCols(), userIn));
				// if the seats requested are available, reserve them
				Node n = Theater.getNode(row, col);
				if (Theater.checkAvailable(n, adults + children + seniors))
				{
					reserveSeats(n, adults, children, seniors);
					System.out.println("Seats reserved!");
				}
				else // if they are not, attempt to find the seat cluster closest to the middle
					if ((n = Theater.bestAvailable(adults + children + seniors)) != null)
						{
							// if that cluster is found, then ask if they would like to reserve those seats
							System.out.println("Would you like to reserve the next best seats? (Your new seats would start with column "
									+ (char)('A' + n.getCol()) +  " and end with " + (char)('A' + n.getCol() + adults + children + seniors - 1)
									+ " on row " + (n.getRow() + 1) +") (Y/N)");
							String prompt;
							do
							{
								prompt = userIn.next().toUpperCase();
								if (!(prompt.equals("Y") || prompt.equals("N")))
									System.out.println("Please output a single 'Y' or an 'N'");
							} while (!(prompt.equals("Y") || prompt.equals("N")));
							// if the user says yes, then reserve those seats
							// otherwise, restart the loop
							if (prompt.equals("Y"))
							{
								reserveSeats(n, adults, children, seniors);
								System.out.println("Seats reserved!");
							}
							else
							{
								System.out.println("Please count your seats this time then.");	
							}
							
						}
						else
						{
							System.out.println("No seats available for that many people to sit together.");
						}
				
			}
		} while (choice != 2);
	
	}

	// template for asking for input with a minimum and maximum value
	private static int promptInput(String prompt,String error, int min, int max, Scanner userIn)
	{
		// output the prompt given
		System.out.println(prompt);
	
		String choice = "";
		int choiceInt = 0;
		do
		{
			try
			{
				// if the user inputs a single character and we are looking for a letter, read the input as a character
				if(((choice = userIn.next()).length()) == 1 && min == 'A')
					choiceInt = choice.toUpperCase().charAt(0);
				else
					choiceInt = Integer.parseInt(choice);
				// if the choice is out of range, output the error
				if((choiceInt < min) || (choiceInt > max))
					throw new NumberFormatException(error);
				
			}
			catch (NumberFormatException e)
			{
				// if there is an error in input(such as inputting a series of letters when a number is expected)
				// output the error and continue
				System.out.println(error);
				continue;
			}
			
			
			// continue until choice is within range
		} while((choiceInt < min) || (choiceInt > max));
		
		return choiceInt;
	}

	private static void reserveSeats(Node n, int adults, int children, int seniors)
	{
		for(int i = 0; i < adults; i++)
		{
			n.setType('A');
			n = n.getE();
		}
		for(int i = 0; i < children; i++)
		{
			n.setType('C');
			n = n.getE();
		}
		for(int i = 0; i < seniors; i++)
		{
			n.setType('S');
			n = n.getE();
		}
		
	}

}
