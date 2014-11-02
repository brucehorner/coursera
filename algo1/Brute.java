import java.util.Arrays;
public class Brute
{
	public static void main(String[] args)
	{
		// read data from given input file
		// and put data into an array of Points
		In input = new In(args[0]);
		int size = input.readInt();
		Point[] pts = new Point[size];
		int[] arr = input.readAllInts();
		input.close();

		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);

		for (int i = 0; i < arr.length; i += 2)
		{
			Point p = new Point(arr[i], arr[i+1]);
			pts[i/2] = p;
			p.draw();
		}
		
		Arrays.sort(pts);
				
		// iterate over all points
		// and calculate slope to all other points in turn
		// no need to visit pairs already considered		
		for (int i = 0; i < pts.length; i++)
			for (int j = i+1; j < pts.length; j++)
				for (int k = j+1; k < pts.length; k++)
					for (int l = k+1; l < pts.length; l++)
					{
						double s0 = pts[i].slopeTo(pts[j]);
						double s1 = pts[j].slopeTo(pts[k]);
						double s2 = pts[k].slopeTo(pts[l]);
						if ((s0 == s1) && (s1 == s2))
						{							
							System.out.format("%s -> %s -> %s -> %s%n",	pts[i], pts[j], pts[k], pts[l]);
							pts[i].drawTo(pts[l]);  // only need the end to end segment
						}
					}
	}
}
