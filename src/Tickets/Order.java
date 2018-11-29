package Tickets;

import java.util.ArrayList;

public class Order
{
	private int num;
	ArrayList<Payload> seats;
	
	
	public int getNum()
	{
		return num;
	}
	public ArrayList<Payload> getSeats()
	{
		return seats;
	}
	public void setNum(int num)
	{
		this.num = num;
	}
	public void setSeats(ArrayList<Payload> seats)
	{
		this.seats = seats;
	}

	public int getAdults()
	{
		int sum = 0;
		for (Payload i : seats)
			if (i.getType() == 'A')
				sum++;
		return sum;
	}
	
	public int getChildren()
	{
		int sum = 0;
		for (Payload i : seats)
			if (i.getType() == 'C')
				sum++;
		return sum;
	}
	public int getSeniors()
	{
		int sum = 0;
		for (Payload i : seats)
			if (i.getType() == 'S')
				sum++;
		return sum;
	}
	public String getSeatCoords()
	{
		String sum = "";

		for (Payload i : seats)
				sum+= i + " ";
		return sum;
	}
	@Override
	public String toString()
	{
		return "Order " + num + "\n"
				+ "Adults: " + getAdults() + "\n"
				+ "Children: " + getChildren() + "\n"
				+ "Seniors: " + getSeniors() + "\n"
				+ "Seats: " + getSeatCoords();
				
	}
}
