import java.util.*;
import src.*;

class p0044
{
	public static void main(String[] args) 
	{
		double mag = 100;

		int windowx = 1900 , windowy = 950 , t = 10;
		double gc = 0.1;
		double scale = 7  , gap = 0.1 ;
		double mx = windowx*mag/2 , my = windowy*mag/2 , m = 2 ;
		double d=1;
		int ballsize = 7;

		ArrayList<object> ao = new ArrayList<object>();
		for(double i=-scale/2;i<+scale/2 ;i+=gap ){
			for(double j=-scale/2;j<+scale/2 ;j+=gap ){
				if( i*i + j*j < scale*scale/4 ){
					ao.add(new object( i+mx , j+my , 0 , 0 , m*d , ballsize));
				}
			}
		}
		object[] obao = new object[ao.size()];
		int count = 0;
		for(object ob:ao)obao[count++]=ob;

		System.out.println(ao.size());

		//object( x, y, vx, vy, m, ballsize)
		//gravityfield( object , gc, ec , gec )
		gravityfield gf = new gravityfield(obao,gc,0,0);
		new frame(gf,t,mag,windowx,windowy);
	}
}
