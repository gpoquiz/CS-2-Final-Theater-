package Tickets;

import java.util.ArrayList;

public class Order
{
	private Auditorium aud;
	ArrayList<Node> seats;
	
	
	public ArrayList<Node> getSeats()
	{
		return seats;
	}
	public Auditorium getAud()
	{
		return aud;
	}
	public void setAud(Auditorium aud)
	{
		this.aud = aud;
	}
	public void setSeats(ArrayList<Node> seats)
	{
		this.seats = seats;
	}
	public void addSeat(Node p)
	{
		seats.add(p);
	}

	public Node hasSeat(int row, int col)
	{
		for (Node i : seats)
			if (i.getRow() == row && i.getCol() == col)
				return i;
		return null;
	}

	public int getAdults()
	{
		int sum = 0;
		for (Node i : seats)
			if (i.getType() == 'A')
				sum++;
		return sum;
	}
	
	public int getChildren()
	{
		int sum = 0;
		for (Node i : seats)
			if (i.getType() == 'C')
				sum++;
		return sum;
	}
	public int getSeniors()
	{
		int sum = 0;
		for (Node i : seats)
			if (i.getType() == 'S')
				sum++;
		return sum;
	}
	public String getSeatCoords()
	{
		String sum = "";

		for (Node i : seats)
				sum+= i + " ";
		return sum;
	}
	public void cancelOrder()
	{
		for (Node i : seats)
		{
			i.setType('#');
			seats.remove(i);
		}
	}
	@Override
	public String toString()
	{
		return    "Auditorium: " + aud.getNum() + "\n"
				+ "Adults: " + getAdults() + "\n"
				+ "Children: " + getChildren() + "\n"
				+ "Seniors: " + getSeniors() + "\n"
				+ "Seats: " + getSeatCoords() + "\n";
	}
}
