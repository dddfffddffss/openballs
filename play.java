import java.util.*;
import java.io.*;
import bin.*;

class play
{
	public static void main(String[] args) 
	{
		/*
		 - mainpicdir 사진 저장되는 폴더 명
		   - 버전 X,프로그램마다 다르고 실행 할 때마다 새로운 하위 폴더를 만들어 저장됨
		 - backup 백업파일을 저장할 폴더 명
		   - [backup]폴더에 [id(subdir)]파일이 자동으로 지정되어 저장됨

		  ㄱ. 백업 하는 법
		    1.[backupdir]폴더 내에 현재 시각을 기준으로 한 백업파일이 자동으로 만들어진다.
			  - [id(백업파일의 이름)] 와 [subdir([mainpicdir]에서 사진을 저장한 폴더)] 은 같다.

		  ㄴ. 복원하는 법
		    1.[backupdir]에 복원파일을 넣는다.
			2.[mainpicdir]에 [subdir]폴더를 넣는다.
			3.[getrestore]에 복원파일의 이름을 적는다.
			4.[startnew]를 false로 설정한다.

		  ㄷ.시작할 때 자동으로 복원파일이 안되면 처음부터 시작된다.
		*/

		//공용
		String mainpicdir = "C:\\Users\\dddfffddffss\\Pictures\\progoutput\\pr";
		String backupdir = "bak";

		//복원파일
		boolean startnew = true;
		String getrestore = "1629614555756",subdir = null;
		
		
		//복원파일 로드 실패시 활용할 초기 설정
		int fwindowx = 3840 , fwindowy = 2160 , ballsize = 100;
		double scale = 1000 , gap = 200 , m = 1 , fgc = 100000;
		/*
		int fwindowx = 1600 , fwindowy = 900 , ballsize = 7;
		double scale = 700 , gap = 300 , m = 2 , fgc = 100000;
		*/

		object[] obao = null;
		gravityfield gf = null;
		imagefactory ifac = null;
		int windowx = 0,windowy = 0,progress = 0;
		double gc = 0;

		// 백업 파일 로드
		try{
			getrestore = startnew?"":backupdir+"\\"+getrestore+".mdpp";
			BufferedReader bw = new BufferedReader(new FileReader(new File(getrestore)));

			String s;
			StringTokenizer stn;
			ArrayList<object> ao = new ArrayList<>();

			for(int i=0;(s=bw.readLine())!=null;i++){
				//둘째 줄 부터는 object 정보
				if(i>0){
					stn = new StringTokenizer(s,"/");
					ao.add(new object(
						Double.valueOf(stn.nextToken()),
						Double.valueOf(stn.nextToken()),
						Double.valueOf(stn.nextToken()),
						Double.valueOf(stn.nextToken()),
						Double.valueOf(stn.nextToken()),
						Integer.valueOf(stn.nextToken())
					));
				}

				//첫 줄은 창 크기, gc , 진행 정도(progress) , id(subdir) 이 들어가있음
				else{
					stn = new StringTokenizer(s,",");
					windowx = Integer.valueOf(stn.nextToken());
					windowy = Integer.valueOf(stn.nextToken());
					gc = Double.valueOf(stn.nextToken());
					progress = Integer.valueOf(stn.nextToken());
					subdir = stn.nextToken();
				}
			}

			obao = new object[ao.size()];
			for(int j=0;j<ao.size();j++)obao[j]=ao.get(j);
		// 백업파일 로드 실패, 새로 시작
		} catch(Exception e){
			System.out.println("로딩 실패");

			windowx = fwindowx;
			windowy = fwindowy;
			gc = fgc;
			progress=0;

			makeob mo = new makeob(windowx,windowy);

			subdir = System.currentTimeMillis()+"";
	
			//mo.obao( scale , gap , m ,  ballsize )
			//gravityfield( (makeob -> ao ob2 ob900 ob1600) , gc, ec , gec )
			obao = mo.ao(scale,gap,m,ballsize);

		} finally {
			System.out.println("object 사이즈 : "+obao.length);
			System.out.println("\n종료하려면 m을 입력하세요");
			gf = new gravityfield(obao,gc,0,0);
			gf.setprogress(progress);
			gf.setid(subdir);

			ifac = new imagefactory(gf,windowx,windowy);

			//startfactory( mainpicdir , count )
			ifac.startfactory(mainpicdir,-1);
		}
		
	//정상적으로 종료 되면 현재 상태 자동 저장//

		try{
			File f1 = new File(backupdir);
			if(!f1.exists())f1.mkdirs();
			f1 = new File(backupdir+"\\"+gf.getid()+".mdpp");
			if(!f1.exists())f1.createNewFile();

			BufferedWriter bw = new BufferedWriter(new FileWriter(f1,false));
			bw.write(windowx+","+windowy+","+gf.gc()+","+gf.getprogress()+","+gf.getid()+"\n"+gf.backup());
			bw.flush();
			bw.close();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
