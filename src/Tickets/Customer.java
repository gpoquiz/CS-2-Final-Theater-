package Tickets;

import java.util.ArrayList;

public class Customer
{
	private String user;
	private ArrayList<Order> orders;
	
	
	public Customer(String user)
	{
		super();
		this.user = user;
	}
	public String getUser()
	{
		return user;
	}
	public ArrayList<Order> getOrders()
	{
		return orders;
	}
	public void setUser(String user)
	{
		this.user = user;
	}
	public void setOrders(ArrayList<Order> orders)
	{
		this.orders = orders;
	}
	public void removeSeat(Order o, int row, int col)
	{
		for (Node i : o.getSeats())
			if (i.getRow() == row && i.getCol() == col)
			{
				i.setType('#');
				if (o.getSeats().isEmpty())
					o.getSeats().remove(i);
			}
	}
	public void displayReceipt()
	{

		int totalAdultSales = 0, totalChildSales = 0, totalSeniorSales = 0;
		
		int i = 1;
		for (Order o : orders)
		{
			int adultSales = 0, childSales = 0, seniorSales = 0;
			System.out.println(o);
			for (Node n : o.getSeats())
			{
				switch (n.getType())
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
			}
			System.out.println("Order: " + i + "\n" + o);
			System.out.println("Sales: $" + String.format("%.2f", 10 * adultSales + 5 * childSales + 7.5 * seniorSales));
			totalAdultSales += adultSales;
			totalChildSales += childSales;
			totalSeniorSales += seniorSales;
			i++;
		}
		
		System.out.println("Total sold:   " + (totalAdultSales + totalChildSales + totalSeniorSales));	
		System.out.println("Adult Sold:   " + (totalAdultSales));		
		System.out.println("Child sold:   " + (totalChildSales));	
		System.out.println("Senior sold:  " + (totalSeniorSales));
		System.out.println("Sales: $" + String.format("%.2f", 10 * totalAdultSales + 5 * totalChildSales + 7.5 * totalSeniorSales));
		
	}
	public void cancelOrder(Order o)
	{
		o.cancelOrder();
		orders.remove(o);
	}
	public void cancelOrders()
	{
		for (Order i : orders)
			i.cancelOrder();
		
		orders.clear();
	}
	
	@Override
	public String toString()
	{
		String s = "";
		int i = 1;
		for (Order o : orders)
		{
			s += "Order: " + i + "\n" + o;
			i++;
		}
		return s;
	}
}
