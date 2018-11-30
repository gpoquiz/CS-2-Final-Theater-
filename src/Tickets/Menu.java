package Tickets;

import java.util.HashMap;
import java.util.Scanner;

public class Menu
{
	public void adminMenu(Scanner userIn, Auditorium ...Theaters)
	{
		int choice = 0;
		do 
		{
			// prompt for menu choice
			choice = promptInput("1. Print Report\n" + 
					"2. Logout\n" + 
					"3. Exit\n", "Please enter a valid choice", 1, 5, userIn);
			
			switch (choice)
			{
			case 1: // print report
				System.out.print(String.format("%-13s", "Auditoriums"));
				System.out.print(String.format("%-11s", "Open seats"));
				System.out.print(String.format("%-21s", "Total reserved seats"));
				System.out.print(String.format("%-12s", "Adult seats"));
				System.out.print(String.format("%-12s", "Child seats"));
				System.out.print(String.format("%-13s", "Senior seats"));
				System.out.println(String.format("%-13s", "Ticket sales"));
				for (Auditorium Theater : Theaters)
				{
					Theater.printReport();
				}
				break;
			case 3: // exit
				userIn.close();
				Theaters[0].printReport();
				Theaters[1].printReport();
				Theaters[2].printReport();
			}
				
		} while (choice != 2);
	}

	public HashMap<String, Customer> readLogins(Scanner in)
	{
		String user, pass;
		HashMap<String, Customer> hash = new HashMap<String, Customer>();
		while (in.hasNext())
		{
			user = in.next();
			pass = in.next();
			hash.put(pass, new Customer(user));
		}
		return hash;
	}
	public Customer loginMenu(Scanner in, HashMap<String, Customer> hash)
	{

		Customer user;
		String userName;
		do
		{
			System.out.print("Enter username: ");
			userName = in.next();

			int attempts = 0;
			String pass = "";
			while (attempts < 3);
			{
				attempts++;
				System.out.print("Enter password: ");
				pass = in.next();
				user = hash.get(pass);
				if (user.getUser() == userName)
					break;
			}
			
		} while (user != null && user.getUser() != userName);
		return user;
	}
	public void mainMenu(Scanner userIn, Customer user, Auditorium... Theaters)
	{
		// our main menu loop
		int choice = 0;
		do 
		{
			// prompt for menu choice
			choice = promptInput("1. Reserve Seats\n"
					+ "2. Exit\n"
					+ "3. Update Order\n"
					+ "4. Display Receipt\n"
					+ "5. Logout\n", "Please enter a valid choice", 1, 5, userIn);
			
			switch (choice)
			{
			case 1:
				// prompt for seat information
				promptOrder(null, userIn, Theaters);
				break;
			case 2: //view
				System.out.println(user);
			case 3: // update
				System.out.println(user);
				int orderChoice = promptInput("Choose an order number", "Please enter a order number", 1, user.getOrders().size(), userIn);
				Order userOrder = user.getOrders().get(orderChoice - 1);
				int updateChoice = promptInput("1. Add tickets to order\r\n" + 
						"2. Delete tickets from order\r\n" + 
						"3. Cancel Order\r\n" + 
						"", "Please enter a valid choice", 1, 3, userIn);
				switch(updateChoice)
				{
				case 1:
					// add
					promptOrder(userOrder, userIn, Theaters);
					break;
				case 2:
					// delete
					int row;
					int col;
						row = promptInput("Choose a row", "Please enter a valid row number", 1, userOrder.getAud().getRows(), userIn) - 1;
						col = promptInput("Choose a column", "Please enter a valid column letter", 'A', 'A' + userOrder.getAud().getCols() - 1, userIn) - 'A';
						if (userOrder.hasSeat(row, col) == null)
							break;
						user.removeSeat(userOrder, row, col);
					break;
				case 3:
					// cancel
					userOrder.cancelOrder();
					break;
				}
				
			case 4: // display
				user.displayReceipt();
				break;
			case 5: // log out
				break;
			}
		} while (choice != 5);
	
	}
	public void promptOrder(Order userOrder, Scanner userIn, Auditorium... Theaters)
	{
		boolean newOrder = false;
		int adults = 0, children = 0, seniors = 0;
		
		int audNum;
		if (userOrder == null)
		{
			newOrder = true;
			userOrder = new Order();
			audNum = promptInput("1. Auditorium 1\r\n" + 
					"2. Auditorium 2\r\n" + 
					"3. Auditorium 3\r\n" + 
					"", "Please enter a valid Auditorium number", 1, Theaters.length ,userIn);
		}
		else
		{
			audNum = userOrder.getAud().getNum();
		}
		
		Auditorium Theater = Theaters[audNum - 1];
		int row = promptInput("Choose a row", "Please enter a valid row number", 1, Theater.getRows(), userIn) - 1;
		int col = promptInput("Choose a column", "Please enter a valid column letter", 'A', 'A' + Theater.getCols() - 1, userIn) - 'A';
		adults = (promptInput("How many adults?", "Please enter a number greater than 0 and less than " + Theater.getCols() + 1, 0, Theater.getCols(), userIn));
		children = (promptInput("How many children?", "Please enter a number greater than 0 and less than " + Theater.getCols() + 1, 0, Theater.getCols(), userIn));
		seniors = (promptInput("How many seniors?", "Please enter a number greater than 0 and less than " + Theater.getCols() + 1, 0, Theater.getCols(), userIn));
		// if the seats requested are available, reserve them
		Node seat = Theater.getNode(row, col);

		if (Theater.checkAvailable(seat, adults + children + seniors))
		{
			reserveSeats(seat, adults, children, seniors, userOrder);
			System.out.println("Seats reserved!");
		}
		else // if they are not, attempt to find the seat cluster closest to the middle
			if (newOrder && (seat = Theater.bestAvailable(adults + children + seniors)) != null)
			{
				// if that cluster is found, then ask if they would like to reserve those seats
				System.out.println("Would you like to reserve the next best seats? (Your new seats would start with column "
						+ (char)('A' + seat.getCol()) +  " and end with " + (char)('A' + seat.getCol() + adults + children + seniors - 1)
						+ " on row " + (seat.getRow() + 1) +") (Y/N)");
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
					reserveSeats(seat, adults, children, seniors, userOrder);
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

	private static void reserveSeats(Node n, int adults, int children, int seniors, Order o)
	{ 
		for(int i = 0; i < adults; i++)
		{
			o.addSeat(n);
			n.setType('A');
			n = n.getE();
		}
		for(int i = 0; i < children; i++)
		{
			o.addSeat(n);
			n.setType('C');
			n = n.getE();
		}
		for(int i = 0; i < seniors; i++)
		{
			o.addSeat(n);
			n.setType('S');
			n = n.getE();
		}
		
	}

}
