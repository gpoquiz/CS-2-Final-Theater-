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
}
