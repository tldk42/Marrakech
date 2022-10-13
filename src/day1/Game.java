package day1;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

	public static mapInfo map = new mapInfo();
	public static String whosCarpet;

	public static Assam assam = new Assam();

	public static GameLog log = new GameLog();
	public static int thisState = 0;
	public static boolean availvableCarpet;
	public static boolean isDead;

	public static PlayerInfo[] p = new PlayerInfo[4];

	public static int playerNum = 0;
	public static int count = 0;
	public static int winner;
	private int col;
	private int deadPlayer;
	private ArrayList<Point> dimension = new ArrayList<>();

	TimerTask task3;

	public Game(int n) {
		switch (n) {
		case 1:
			p[0] = new PlayerInfo(1, 1, true);
			p[1] = new PlayerInfo(2, 2, false);
			p[2] = new PlayerInfo(3, 3, false);
			p[3] = new PlayerInfo(4, 4, false);
			break;
		case 2:
			p[0] = new PlayerInfo(1, 1, true);
			p[1] = new PlayerInfo(2, 2, true);
			p[2] = new PlayerInfo(3, 3, false);
			p[3] = new PlayerInfo(4, 4, false);
			break;
		case 3:
			p[0] = new PlayerInfo(1, 1, true);
			p[1] = new PlayerInfo(2, 2, true);
			p[2] = new PlayerInfo(3, 3, true);
			p[3] = new PlayerInfo(4, 4, false);
			break;
		case 4:
			p[0] = new PlayerInfo(1, 1, true);
			p[1] = new PlayerInfo(2, 2, true);
			p[2] = new PlayerInfo(3, 3, true);
			p[3] = new PlayerInfo(4, 4, true);
			break;
		}

	}

	public void screenDraw(Graphics2D g) {
		drawCarpet(g);
		drawAssam(g);
		g.setColor(Color.black);
		log.screenDraw(g, thisState, playerNum);
		setPreview(g);
		drawLine(g);

	}

	// 아쌈의 위치 변할 때마다 x,y 좌표 받아서 그리기
	public void drawAssam(Graphics2D g) {
		Image assamImage = assam.getAssamImage();
		g.drawImage(assamImage, 38 + assam.getX() * 97, 38 + assam.getY() * 97, 56, 56, null);
	}

	// map의 카펫 번호를 받아서 그리기
	public void drawCarpet(Graphics2D g) {
		if (p[playerNum].getCarpet() > 0) {
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 7; j++) {
					if (map.getCarpetColor(i, j) == 1) {
						g.setColor(new Color(102, 102, 204));
						g.fillRoundRect(22 + i * 97, 24 + j * 97, 90, 90, 25, 25);
					} else if (map.getCarpetColor(i, j) == 2) {
						g.setColor(new Color(204, 204, 000));
						g.fillRoundRect(22 + i * 97, 24 + j * 97, 90, 90, 25, 25);
					} else if (map.getCarpetColor(i, j) == 3) {
						g.setColor(new Color(255, 102, 102));
						g.fillRoundRect(22 + i * 97, 24 + j * 97, 90, 90, 25, 25);
					} else if (map.getCarpetColor(i, j) == 4) {
						g.setColor(new Color(051, 153, 000));
						g.fillRoundRect(22 + i * 97, 24 + j * 97, 90, 90, 25, 25);
					}

				}

			}
		}

	}

	// 동서남북으로 카펫을 설치할 수 있는 경로 보여주기
	public void drawLine(Graphics2D g) {
		if (availvableCarpet) {
			g.setStroke(new BasicStroke(2));
			g.setColor(new Color(255, 051, 000));
			if (count == 0)
				map.firstClick(assam.getX(), assam.getY());
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 7; j++) {
					if (map.getPreCarpet(i, j))
						g.drawRoundRect(22 + 97 * i, 23 + 97 * j, 90, 90, 25, 25);
				}
			}

		}
	}

	public void setPreview(Graphics2D g) {
		g.setStroke(new BasicStroke(4));
		g.setColor(new Color(000, 204, 051));
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if (map.getPreview(i, j))
					g.drawRoundRect(22 + 97 * i, 23 + 97 * j, 90, 90, 25, 25);
			}
		}
	}

	// 아쌈의 위치에 있는 카펫의 면적 구하기
	public int getDimension(int n, int x, int y) {
		if (x >= 0 && x <= 6 && y >= 0 && y <= 6) {
			if (y != 6)
				if (map.getCarpetColor(x, y + 1) == n) {
					Point p = new Point(x, y + 1);
					if (!dimension.contains(p)) {
						dimension.add(p);
						getDimension(n, x, y + 1);
					}
				}
			if (x != 6)
				if (map.getCarpetColor(x + 1, y) == n) {
					Point p = new Point(x + 1, y);
					if (!dimension.contains(p)) {
						dimension.add(p);
						getDimension(n, x + 1, y);
					}
				}
			if (y != 0)
				if (map.getCarpetColor(x, y - 1) == n) {
					Point p = new Point(x, y - 1);
					if (!dimension.contains(p)) {
						dimension.add(p);
						getDimension(n, x, y - 1);
					}
				}
			if (x != 0)
				if (map.getCarpetColor(x - 1, y) == n) {
					Point p = new Point(x - 1, y);
					if (!dimension.contains(p)) {
						dimension.add(p);
						getDimension(n, x - 1, y);
					}
				}
			if (dimension.size() == 0) {
				dimension.add(new Point(x, y));
			}
		}

		return dimension.size();
	}

	// 게임 종료 조건
	public boolean isGameOver() {
		deadPlayer = 0;
		for (int i = 0; i < 4; i++) {
			if (p[i].isOver())
				deadPlayer++;
			if (deadPlayer >= 3)
				return true;
		}

		if (p[0].getCarpet() == 0 && p[1].getCarpet() == 0 && p[2].getCarpet() == 0 && p[3].getCarpet() == 0)
			return true;
		return false;
	}

	// 위치를 기준으로 디르함을 거래하기
	public void transDirham(int num) {
		if (!p[num - 1].isOver()) {		// 지불할 플레이어가 죽었으면 실행하지 않음
			int dir = getDimension(num, assam.getX(), assam.getY());
			if (dir < 5) { // 지불해야하는 디르함이 5디르함 보다 적을 때.
				if (p[playerNum].getDirham5() < 1) // 지불해야 하는 플레이어의 5디르함이 없을 떄.
					for (int i = 0; i < dir; i++) {
						if (!p[playerNum].isOver()) // 디르함을 지불해야하는 플레이어의 돈이 1이라도 있을 떄.
						{
							p[playerNum].setDirham1();
							p[num - 1].addDirham1();
						}
					}
				else { // 5디르함이 있을 때 (거스름돈을 돌려 받아야함.) -> 예외 설정

					if (p[num - 1].getDirham1() < 5 - dir) // 거스름돈이 없다면
						resetDirham(dir, num - 1);
					else {
						p[playerNum].setDirham5();
						p[num - 1].addDirham5();
						for (int i = 0; i < 5 - dir; i++) {
							p[playerNum].addDirham1();
							p[num - 1].setDirham1();
						}
					}
				}
			} else { // 지불해야하는 디르함이 5디르함 이상일 때
				if (p[playerNum].getTotalDirham() >= dir) {

					if (dir % 5 == 0) {
						if (p[playerNum].getDirham5() >= dir / 5) {
							for (int i = 0; i < dir / 5; i++) {
								p[playerNum].setDirham5();
								p[num - 1].addDirham5();
							}
						} else {
							for (int i = 0; i < (dir / 5) - p[playerNum].getDirham5(); i++) {
								p[playerNum].setDirham1();
								p[num - 1].addDirham1();
							}
							for (int i = 0; i < p[playerNum].getDirham5(); i++) {
								p[playerNum].setDirham5();
								p[num - 1].addDirham5();
							}
						}
					} else {
						if (p[num - 1].getDirham1() < 5 - (dir % 5)) // 거스름돈을 줄 수 없을 떄
							resetDirham(dir, num - 1);
						else {
							for (int i = 0; i < (dir / 5) + 1; i++) {
								p[playerNum].setDirham5();
								p[num - 1].addDirham5();
							}
							for (int i = 0; i < (5 - dir % 5); i++) {
								p[num - 1].setDirham1();
								p[playerNum].addDirham1();
							}
						}

					}
				} else {
					for (int i = 0; i < p[playerNum].getDirham1(); i++) {
						p[playerNum].setDirham1();
						p[num - 1].addDirham1();
					}
					for (int i = 0; i < p[playerNum].getDirham5(); i++) {
						p[playerNum].setDirham5();
						p[num - 1].addDirham5();
					}
					System.out.println(playerNum + " 게임에서 탈락했습니다.");
					p[playerNum].setOver(true);
					for(int i=0;i<p[playerNum].getCarpet();i++)
						p[playerNum].setCarpet();
				}

			}
		}
		else
			isDead = true;
	}

	// 거스름돈이 부족할 때
	public void resetDirham(int dir, int num) {
		System.out.println("재정렬");
		int newDir = 0;

		int count = 0;

		for (int i = 0; i < 4; i++) {
			if (i == num) {
				newDir = p[i].getTotalDirham() + dir;
			} else if (i == playerNum) {
				newDir = p[i].getTotalDirham() - dir;
			} else {
				newDir = p[i].getTotalDirham();
			}
			p[i].reset(); // dirham1,5 == 0 으로 초기화
			for (int j = 0; j < newDir / 5; j++) {
				p[i].addDirham5();
			}
			for (int j = 0; j < newDir % 5; j++) {
				p[i].addDirham1();
			}
			if (p[i].getDirham5() > 2 && count != 2) {
				p[i].setDirham5();
				for (int j = 0; j < 5; j++)
					p[i].addDirham1();
				count++;
			}
		}
	}

	public void start() {
		Timer timer = new Timer();
		task3 = new TimerTask() {

			@Override
			public void run() {
				if (!isGameOver()) {
					if (p[playerNum].isUser()) {
						userLogic();
					} else {
						ComputerLogic();
					}
				} else {
					int winScore = p[0].getTotalScore();
					winner = 0;
					for (int i = 0; i < 4; i++) {
						if (winScore <= p[i].getTotalScore())
							winner = i + 1;
					}
					thisState = 99;
					return;
				}
			}

		};
		timer.schedule(task3, 5000, 1000);
	}

	// 유저의 실행
	public void userLogic() {
		if (thisState == 0) {
			Music notify = new Music("notify.mp3", false);
			notify.start();
		}
		if (thisState == 3) {
			System.out.println("moving Assam");
			for (int i = 0; i < log.getDiceNum(); i++) {
				try {
					Thread.sleep(1000);
					Music btnEnter = new Music("movementSound.mp3", false);
					btnEnter.start();
					assam.moveAssam();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			sleep(1);
			thisState++;
		}

		if (thisState == 4) {
			// 아쌈의 위치에 카페트가 깔려있는지 확인
			col = map.getCarpetColor(assam.getX(), assam.getY());
			// 깔려있으면 카페트의 색이 어떤 플레이어의 것인지 확인
			switch (col) {
			case 0:
				whosCarpet = "0";
				break;
			case 1:
				whosCarpet = "player1";
				break;
			case 2:
				whosCarpet = "player2";
				break;
			case 3:
				whosCarpet = "player3";
				break;
			case 4:
				whosCarpet = "player4";
				break;
			}
			// 출력해준다
		}
		if (thisState == 5) {
			// 우선 카페트의 면적이 얼마인지 확인한다.
			// 카페트 주인에게 디르함을 건네준다
			switch (col) {
			case 0:
				break;
			case 1:
				if (playerNum == 0) {
					break;
				} else {
					transDirham(1);
				}
				break;
			case 2:
				if (playerNum == 1) {
					break;
				} else {
					transDirham(2);
				}
				break;
			case 3:
				if (playerNum == 2) {
					break;
				} else {
					transDirham(3);
				}
				break;
			case 4:
				if (playerNum == 3) {
					break;
				} else {
					transDirham(4);
				}
				break;
			}
			dimension.clear();
			thisState++;
			// 만약 디르함이 부족하다면 유저 탈락
			// 본인의 디르함을 차감한다.
		}

		if (thisState == 8) {

			// 초기화
			availvableCarpet = false;
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 7; j++) {
					map.setPreCarpet(i, j, false);
					map.setPreview(i, j, false);
				}
			}
			//

			if (playerNum != 3) {
				if (p[playerNum + 1].isOver()) {
					p[playerNum].setCarpet();
					playerNum++;
					thisState = 8;
				} else {
					if(p[playerNum].getCarpet()!=0)
					p[playerNum].setCarpet();
					playerNum++;
					thisState = 0;
					count = 0;

				}
			} else {
				if (p[0].isOver()) {
					if(p[playerNum].getCarpet()!=0)
					p[playerNum].setCarpet();
					playerNum = 0;
					thisState = 8;
				} else {
					p[playerNum].setCarpet();
					playerNum = 0;
					thisState = 0;
					count = 0;

				}
			}
		}
	}

	// 컴퓨터가 카펫을 설치하는 메소드
	public void state7Com() {
		int x = assam.getX();
		int y = assam.getY();
		ArrayList<Point> points = new ArrayList<Point>(); // 설치 가능한 구역들

		int random = (int) (Math.random() * 4); // 0,1,2,3

		switch (random) { // 현재 아쌈의 위치에서 동 서 남 북 으로 랜덤하게 선택한다.
		case 0:
			if (y != 0)
				y--;
			else
				y++;
			break;
		case 1:
			if (y != 6)
				y++;
			else
				y--;
			break;
		case 2:
			if (x != 0)
				x--;
			else
				x++;
			break;
		case 3:
			if (x != 6)
				x++;
			else
				x--;
			break;
		}

		map.secondView(x, y); // 랜덤하게 선택된 곳을 기준으로 주위에 카페트가 설치될 수 있는곳을 true 로 변환해준다.

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				if (map.getPreCarpet(i, j)) {
					points.add(new Point(i, j)); // 리스트에 가능한 곳들을 넣어준다
					break;
				}
			}
		}
		int random2 = (int) (Math.random() * points.size()); // 리스트중에 한 요소를 뽑는다.

		if (points.size() == 0) { // 리스트에 가능한 곳이 없다면 다시 호출한다.
			state7Com();
		} else {
			map.setCarpetColor((int) points.get(random2).getX(), (int) points.get(random2).getY(),
					p[playerNum].getColor());
			map.setCarpetColor(x, y, p[playerNum].getColor());

			sleep(2);
			thisState++;
		}
	}

	// 컴퓨터의 실행
	public void ComputerLogic() {
		if (thisState == 0) { // 컴퓨터 방향 설정
			int[] random = new int[3];
			int direction = assam.getDirection();
			switch (direction) {// 0: up 1:down 2:left 3:right
			case 0:
				random[0] = 0;
				random[1] = 2;
				random[2] = 3;
				break;
			case 1:
				random[0] = 1;
				random[1] = 3;
				random[2] = 2;
				break;
			case 2:
				random[0] = 2;
				random[1] = 0;
				random[2] = 1;
				break;
			case 3:
				random[0] = 3;
				random[1] = 1;
				random[2] = 0;
			}
			sleep(2);
			assam.setDirection(random[(int) (Math.random() * 3)]);
			thisState++;
		}
		if (thisState == 1) { // 주사위 굴리기
			log.RollDice();
			sleep(2);
			thisState++;
			System.out.println("주사위 숫자 : " + log.getDiceNum());
		}
		if (thisState == 2) {
			sleep(2);
			thisState++;
		}
		if (thisState == 3) { // 아쌈 이동
			System.out.println("moving Assam");
			for (int i = 0; i < log.getDiceNum(); i++) {
				try {
					Thread.sleep(800);
					assam.moveAssam();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			sleep(2);
			thisState++;
		}
		if (thisState == 4) {
			col = map.getCarpetColor(assam.getX(), assam.getY());
			// 깔려있으면 카페트의 색이 어떤 플레이어의 것인지 확인
			switch (col) {
			case 0:
				whosCarpet = "0";
				break;
			case 1:
				whosCarpet = "player1";
				break;
			case 2:
				whosCarpet = "player2";
				break;
			case 3:
				whosCarpet = "player3";
				break;
			case 4:
				whosCarpet = "player4";
				break;
			}
			sleep(2);
			thisState++;
		}
		if (thisState == 5) {
			switch (col) {
			case 0:
				break;
			case 1:
				if (playerNum == 0) {
					break;
				} else {
					transDirham(1);
				}
				break;
			case 2:
				if (playerNum == 1) {
					break;
				} else {
					transDirham(2);
				}
				break;
			case 3:
				if (playerNum == 2) {
					break;
				} else {
					transDirham(3);
				}
				break;
			case 4:
				if (playerNum == 3) {
					break;
				} else {
					transDirham(4);
				}
				break;
			}
			dimension.clear();
			sleep(2);
			thisState++;
		}
		if (thisState == 6) {
			sleep(1);
			thisState++;
		}
		if (thisState == 7) {
			state7Com();
		}
		if (thisState == 8) {
			availvableCarpet = false;
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 7; j++) {
					map.setPreCarpet(i, j, false);
					map.setPreview(i, j, false);
				}
			}
			//

			if (playerNum != 3) {
				if (p[playerNum + 1].isOver()) {
					p[playerNum].setCarpet();
					playerNum++;
					thisState = 8;
				} else {
					if(p[playerNum].getCarpet()!=0)
					p[playerNum].setCarpet();
					playerNum++;
					thisState = 0;
					count = 0;

				}
			} else {
				if (p[0].isOver()) {
					p[playerNum].setCarpet();
					playerNum = 0;
					thisState = 8;
				} else {
					if(p[playerNum].getCarpet()!=0)
					p[playerNum].setCarpet();
					playerNum = 0;
					thisState = 0;
					count = 0;

				}
			}
		}
	}

	// time초 만큼 delay 시켜주는 메소드
	public void sleep(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
