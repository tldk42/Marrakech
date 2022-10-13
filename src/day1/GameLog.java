package day1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class GameLog {

	private int[] dice = { 1, 2, 2, 3, 3, 4 };
	private int diceNum = 0;

	public GameLog() {
		RollDice();
	}

	// 주사위를 굴린다.
	public void RollDice() {
		diceNum = dice[(int) (java.lang.Math.random() * 6)];
	}

	// 주사위를 반환한다.
	public int getDiceNum() {
		return diceNum;
	}

	// 진행 상황에 따른 게임로그 설정
	public void screenDraw(Graphics2D g, int status, int num) {

		// Player Statement
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));

		g.drawString(Integer.toString(Game.p[0].getDirham1()), 870, 110);
		g.drawString(Integer.toString(Game.p[0].getDirham5()), 965, 110);
		g.drawString(Integer.toString(Game.p[0].getCarpet()), 1190, 110);

		g.drawString(Integer.toString(Game.p[1].getDirham1()), 870, 240);
		g.drawString(Integer.toString(Game.p[1].getDirham5()), 965, 240);
		g.drawString(Integer.toString(Game.p[1].getCarpet()), 1190, 240);

		g.drawString(Integer.toString(Game.p[2].getDirham1()), 870, 370);
		g.drawString(Integer.toString(Game.p[2].getDirham5()), 965, 370);
		g.drawString(Integer.toString(Game.p[2].getCarpet()), 1190, 370);

		g.drawString(Integer.toString(Game.p[3].getDirham1()), 870, 500);
		g.drawString(Integer.toString(Game.p[3].getDirham5()), 965, 500);
		g.drawString(Integer.toString(Game.p[3].getCarpet()), 1190, 500);

		// Game Log
		g.setColor(Color.black);
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		if (status == 0) {
			switch (num) {
			case 0:
				g.drawString("플레이어1의 차례입니다", 850, 600);
				break;
			case 1:
				g.drawString("플레이어2의 차례입니다", 850, 600);
				break;
			case 2:
				g.drawString("플레이어3의 차례입니다", 850, 600);
				break;
			case 3:
				g.drawString("플레이어4의 차례입니다", 850, 600);
				break;
			}
			g.drawString("방향을 설정해주세요", 850, 625);
		} else if (status == 1) {
			switch (num) {
			case 0:
				g.drawString("플레이어1의 차례입니다", 850, 600);
				break;
			case 1:
				g.drawString("플레이어2의 차례입니다", 850, 600);
				break;
			case 2:
				g.drawString("플레이어3의 차례입니다", 850, 600);
				break;
			case 3:
				g.drawString("플레이어4의 차례입니다", 850, 600);
				break;
			}
			g.drawString("주사위를 굴려주세요", 850, 625);
		} else if (status == 2) {

			switch (num) {
			case 0:
				g.drawString("플레이어1의 주사위는", 850, 600);
				break;
			case 1:
				g.drawString("플레이어2의 주사위는", 850, 600);
				break;
			case 2:
				g.drawString("플레이어3의 주사위는", 850, 600);
				break;
			case 3:
				g.drawString("플레이어4의 주사위는", 850, 600);
				break;
			}
			g.drawString(diceNum + "이 나왔습니다.", 850, 630);
		} else if (status == 3) {
			g.drawString("아쌈 이동중입니다.", 850, 620);
		} else if (status == 4) {
			g.drawString("아쌈의 위치에는.", 850, 600);
			if (!Game.whosCarpet.equals("0")) {
				g.drawString(Game.whosCarpet + "의 카페트가 존재합니다.", 850, 620);
				g.drawString("돈을 지불합니다.", 850, 640);
			} else
				g.drawString("아직 카페트가 존재하지 않습니다.", 850, 620);

		} else if (status == 5) {
			g.drawString("계산중.... ", 850, 610);
		} else if (status == 6) {
			if (Game.isDead) {
				g.drawString("플레이어가 탈락하여 생략합니다. ", 850, 610);
				Game.isDead = false;
			} else
				g.drawString("돈을 지불했습니다. ", 850, 610);
		} else if (status == 7) {
			g.drawString("카펫을 설치할 장소를 클릭해주세요.", 850, 610);
			g.drawString("선택 취소는  ", 850, 630);
			g.drawString("아래 undo 버튼을 클릭하세요. ", 850, 650);
		} else if (status == 99) {
			g.drawString("게임이 종료되었습니다..", 850, 610);
			g.drawString("우승자는  ", 850, 630);
			g.drawString(Integer.toString(Game.winner), 850, 650);
		}
	}

}
