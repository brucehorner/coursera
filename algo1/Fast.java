import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class Fast
{	
	private static class Line
	{
		double slope;
		int size;
		Point[] pts;
	}
	
	public static void main(String[] args)
	{
		// read data from given input file
		// and put data into an array of Points
		In input = new In(args[0]);
		int size = input.readInt();
		Point[] pts = new Point[size];
		int[] arr = input.readAllInts();
		input.close();
		for (int i = 0; i < arr.length; i += 2)
			pts[i/2] = new Point(arr[i], arr[i+1]);
		
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for (Point p: pts)
			p.draw();

		ArrayList<Line> lines = new ArrayList<Line>();
		
		for (int i = 0; i < pts.length - 3; i++)	// only interested in segments with > 3 points
		{
			Point reference = pts[i];
			if (i < (pts.length - 1))
				Arrays.sort(pts, i+1, pts.length, reference.SLOPE_ORDER);
			
			// look at all other points and count how many have the same slope
			//for (; j < pts.length - 1;)
			int j = i + 1;
			while (j < pts.length - 1)
			{
				Point subref = pts[j];
				int count = 0;
				double s = reference.slopeTo(subref); 
				while ((j + count + 1) < pts.length && s == reference.slopeTo(pts[j+count+1]))
					count++;
				
				// if more than 3 (including the reference and subrefence points) then output something
				if (count > 1)
				{			
					Point[] ps = new Point[count+2];
					ps[0] = reference;
					ps[1] = subref;
					for (int c = 0; c < count; c++)
						ps[c+2] = pts[j + 1 + c];

					Arrays.sort(ps);	// put this line segment's points in natural order
					Line l = new Line();
					l.pts = ps;
					l.slope = s;
					l.size = ps.length;
					lines.add(l);
				}
				
				j += (count == 0 ? 1 : count);
			}
		}
		
		Line[] linesArr = lines.toArray(new Line[lines.size()]);
		
		// now we've found all the lines, print out the longest segments
		int pos = 0;
		while (pos < linesArr.length)
		{
			Line l = linesArr[pos];
			
			for (int pi = 0; pi < l.pts.length; pi++)
			{
				System.out.format("%s", l.pts[pi]);
				if (pi < (l.pts.length - 1))
					System.out.format(" -> ");							
			}			
			System.out.format("%n");
			l.pts[0].drawTo(l.pts[l.pts.length-1]);	// draw line only between two extremes
			
			pos++;
		}		
	}
}