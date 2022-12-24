package src;

public class gravityfield
{
	object[] object;
	double[] ax,ay,z,a,b;
	double gc=3000;
	double dx,abx,aby,k,Dp;

	int progress=0;
	String id;
	
	public gravityfield(object[] object,double gc,double ec,double gec){
		this.object = object;
		this.gc = gc;

		ax = new double[object.length];
		ay = new double[object.length];
		z = new double[2];
	}

	public void setbound(int x,int y){
		for(object o:object)o.setbound(x,y);
	}

	public void setprogress(int progress){
		this.progress = progress;
	}

	public int getprogress(){
		return progress;
	}

	public String getid(){
		return id;
	}

	public void setid(String id){
		this.id = id;
	}

	public double Dpow(object x,object y){
		return sizepow((y.Lx()-x.Lx()),(y.Ly()-x.Ly()));
	}

	public double sizepow(double x,double y){
		return x*x+y*y;
	}

	public double[] crash(double dx,double abx,double aby,object x,object y){
		if(Dpow(x,y)>dx*dx)return z;

		double abvx=(y.Vx()-x.Vx()), abvy=(y.Vy()-x.Vy());
		if(abvx*abx+abvy*aby>0)return z;

		k=(abvx*abx+abvy*aby)/sizepow(abx,aby);
		return new double[]{k*abx,k*aby};
	}

	public double[] gravity(double dx,double abx,double aby,object x,object y){
		Dp = Dpow(x,y);
		if(Dp<0.01)return z;

		double ab=Math.abs(abx)+Math.abs(aby);

		if(Dp>dx*dx)k = gc*x.M()*y.M()/Dpow(x,y);
		else k = gc*x.M()*y.M()/(dx*dx);

		return new double[]{abx/ab*k,aby/ab*k};
	}

	public void moving(){
		progress++;
		for(int x=0;x<object.length;x++){
			for(int y=x+1;y<object.length;y++){
				dx = (object[y].S()+ object[x].S())/2;
				abx=object[y].Lx()-object[x].Lx();
				aby=object[y].Ly()-object[x].Ly();

				a=gravity(dx,abx,aby,object[x],object[y]);
				b=crash(dx,abx,aby,object[x],object[y]);
				ax[x]+=a[0]+b[0];
				ay[x]+=a[1]+b[1];
				ax[y]-=a[0]+b[0];
				ay[y]-=a[1]+b[1];
			}
		}
		for(int x=0;x<object.length;x++){
			object[x].nextL(ax[x],ay[x]);
			ax[x]=0;
			ay[x]=0;
		}
	}

	public object[] state(){
		return object;
	}

	public int length(){
		return object.length;
	}

	public double gc(){
		return gc;
	}

	public String backup(){
		StringBuilder sb = new StringBuilder();
		for(object o:object){
			sb.append(o.Lx()+"/"+o.Ly()+"/"+o.Vx()+"/"+o.Vy()+"/"+o.M()+"/"+o.S()+"\n");
		}
		return sb.toString().trim();
	}
}