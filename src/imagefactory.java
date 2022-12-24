package bin;

import java.io.*;
import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;

public class imagefactory
{
	int x,y;
	gravityfield gf;
	breaker b;

	public imagefactory(gravityfield gf,int x,int y){
		this.x = x;
		this.y = y;
		this.gf = gf;

		gf.setbound(x,y);

		b =  new breaker();
		b.start();
	}

	/*x	
	 - calnextpicture으로 계산하고 makeframeimage로 계산 결과를 출력한다.
	   - 모든 연산은 calnextpicture( gf ) 에서 이뤄지므로, makeframeimage( imagefactory )은 단순한 이미지 제작 과정이다.
	*/

	private void calnextpicture(){
		gf.moving();
	}

	//새로운 프레임의 이미지 가져오기
	private BufferedImage makeframeimage(){
		BufferedImage bi = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
		Graphics g1 = bi.getGraphics();
		g1.setColor(Color.black);

		try{g1.drawImage(ImageIO.read(new File("res\\white1a1.jpg")),0,0,x,y,0,0,1,1,null);}
		catch (IOException e) {e.printStackTrace();}
		
		for(object ob:gf.state()){
			g1.fillOval((int)(ob.Lx()),(int)(ob.Ly()),ob.S(),ob.S());
		}

		return bi;
	}

	//현재 상태의 gf를 이미지로 출력한다.
	private void makecurrentstateoutput(String dir,String subdir,String name){
        try {ImageIO.write(makeframeimage(),"JPG", new File(dir+"\\"+subdir+"\\"+name+".jpg")); }
		catch (IOException e) {e.printStackTrace();}
	}

	/*
	 - dir는 디랙토리+이름 까지만, 구분자를 쓸 필요 없다.
	 - subdir은 각 폴더마다 id가 지정되어 백업파일과 쉽게 만날 수 있게 한다.
	   - 백업파일의 이름과 subdir의 이름은 id로 동일하다.
	   - id의 이름은 처음으로 만들어진 시간으로 한다.
	*/

	public void startfactory(String dir,int count){
		String subdir = gf.getid();
		File f  = new File(dir+"\\"+subdir);
		f.mkdirs();

		for(int i=gf.getprogress();(count<0||i<count)&&!b.isend();i++){
			System.out.println(i);
			makecurrentstateoutput(dir,subdir,i+"");
			calnextpicture();
		}
		System.out.println("종료합니다.");
	}
}