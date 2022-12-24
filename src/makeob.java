package src;

import java.util.*;

public class makeob
{
	double windowx, windowy,my,mx;
	public makeob(double windowx,double windowy){
		this.windowx = windowx;
		this.windowy = windowy;
		mx = windowx/2;
		my = windowy/2;
	}

	public object[] ao(double scale,double gap,double m,int ballsize) 
	{
		//int i=-1 , j=-1;

		ArrayList<object> ao = new ArrayList<object>();
		for(int i=-(int)(scale/gap/2);i<scale/gap/2;i++){
			for(int j=-(int)(scale/gap/2);j<scale/gap/2;j++){
				if(i*i+j*j<scale*scale/(gap*gap*4)){
					//		   object( x        , y        , vx  , vy, m , ballsize )
					ao.add(new object( mx+i*gap , my+j*gap , 0   , 0 , m , ballsize));
				}
			}
		}

		//ao.add(new object(1200,800,-600,-600,100,ballsize));
		object[] obao = new object[ao.size()];
		for(int j=0;j<ao.size();j++)obao[j]=ao.get(j);

		return obao;
	}

	public object[] ob2() 
	{
		object[] ob2 = new object[2];
		ob2[0] = new object( mx+600 , my , 200 , 500 , 1 , 100 );
		ob2[1] = new object( mx-800 , my , -20 , 0 , 1 , 150 );

		return ob2;
	}

	public object[] ob900() 
	{
		object[] ob900 = new object[901];
		for(int i=-1;++i<900;ob900[i] = new object( 700+15*(i%30) , 250+15*(i/30) , 0 , 0 , 1 , 7 ));
		ob900[900] = new object(1500,500,-00,-00,50,400);

		return ob900;
	}

	public object[] ob1600() 
	{
		object[] ob1600 = new object[1600];
		for(int i=-1;++i<1600;ob1600[i] = new object(500+15*(i%40),120+15*(i/40),0,0,1,7));

		return ob1600;
	}
}
