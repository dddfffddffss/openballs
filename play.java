import java.util.*;
import java.io.*;
import bin.*;

class play
{
	public static void main(String[] args) 
	{
		/*
		 - mainpicdir ���� ����Ǵ� ���� ��
		   - ���� X,���α׷����� �ٸ��� ���� �� ������ ���ο� ���� ������ ����� �����
		 - backup ��������� ������ ���� ��
		   - [backup]������ [id(subdir)]������ �ڵ����� �����Ǿ� �����

		  ��. ��� �ϴ� ��
		    1.[backupdir]���� ���� ���� �ð��� �������� �� ��������� �ڵ����� ���������.
			  - [id(��������� �̸�)] �� [subdir([mainpicdir]���� ������ ������ ����)] �� ����.

		  ��. �����ϴ� ��
		    1.[backupdir]�� ���������� �ִ´�.
			2.[mainpicdir]�� [subdir]������ �ִ´�.
			3.[getrestore]�� ���������� �̸��� ���´�.
			4.[startnew]�� false�� �����Ѵ�.

		  ��.������ �� �ڵ����� ���������� �ȵǸ� ó������ ���۵ȴ�.
		*/

		//����
		String mainpicdir = "C:\\Users\\dddfffddffss\\Pictures\\progoutput\\pr";
		String backupdir = "bak";

		//��������
		boolean startnew = true;
		String getrestore = "1629614555756",subdir = null;
		
		
		//�������� �ε� ���н� Ȱ���� �ʱ� ����
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

		// ��� ���� �ε�
		try{
			getrestore = startnew?"":backupdir+"\\"+getrestore+".mdpp";
			BufferedReader bw = new BufferedReader(new FileReader(new File(getrestore)));

			String s;
			StringTokenizer stn;
			ArrayList<object> ao = new ArrayList<>();

			for(int i=0;(s=bw.readLine())!=null;i++){
				//��° �� ���ʹ� object ����
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

				//ù ���� â ũ��, gc , ���� ����(progress) , id(subdir) �� ������
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
		// ������� �ε� ����, ���� ����
		} catch(Exception e){
			System.out.println("�ε� ����");

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
			System.out.println("object ������ : "+obao.length);
			System.out.println("\n�����Ϸ��� m�� �Է��ϼ���");
			gf = new gravityfield(obao,gc,0,0);
			gf.setprogress(progress);
			gf.setid(subdir);

			ifac = new imagefactory(gf,windowx,windowy);

			//startfactory( mainpicdir , count )
			ifac.startfactory(mainpicdir,-1);
		}
		
	//���������� ���� �Ǹ� ���� ���� �ڵ� ����//

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
