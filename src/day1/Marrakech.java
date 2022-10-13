package day1;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Marrakech extends JFrame {

	private Image screenImage;
	private Graphics screenGraphics;

	// 이미지
	// 첫번 쨰 화면
	private ImageIcon exitButtonEnteredImage = new ImageIcon("images/exitButtonEntered.png");
	private ImageIcon exitButtonBasicImage = new ImageIcon("images/exitButtonBasic.png");
	private ImageIcon startButtonBasicImage = new ImageIcon("images/startButtonBasic.png");
	private ImageIcon settingButtonBasicImage = new ImageIcon("images/settingButtonBasic.png");
	// 시작하기 버튼 누를 때 화면
	private ImageIcon offlineButtonBasic = new ImageIcon("images/offlineButtonBasic.png");
	private ImageIcon onlineButtonBasic = new ImageIcon("images/onlineButtonBasic.png");
	private ImageIcon goBackButtonBasic = new ImageIcon("images/goBackButtonBasic.png");

	private Image gameImage;
	private Image background = new ImageIcon("images/intoImage.png").getImage();

	// 상단 바
	private JLabel menuBar = new JLabel(new ImageIcon("images/menuBar.png"));

	// 음악 바
	private JButton preMusic = new JButton(new ImageIcon("images/preMusic.png"));
	private JButton nextMusic = new JButton(new ImageIcon("images/nextMusic.png"));
	private JButton playMusic = new JButton(new ImageIcon("images/playMusic.png"));

	// 메뉴 버튼1
	private JButton exitButton = new JButton(exitButtonBasicImage);
	private JButton startButton = new JButton(startButtonBasicImage);
	private JButton settingButton = new JButton(settingButtonBasicImage);

	// 메뉴 버튼2
	private JButton offlineButton = new JButton(offlineButtonBasic);
	private JButton onlineButton = new JButton(onlineButtonBasic);
	private JButton goBackButton = new JButton(goBackButtonBasic);

	// 오프라인 버튼 클릭시
	private JButton play1 = new JButton(new ImageIcon("images/play1.png"));
	private JButton play2 = new JButton(new ImageIcon("images/play2.png"));
	private JButton play3 = new JButton(new ImageIcon("images/play3.png"));
	private JButton play4 = new JButton(new ImageIcon("images/play4.png"));
	private JButton home = new JButton(new ImageIcon("images/home.png"));

	// 설정 버튼 클릭시
	private JButton next = new JButton(new ImageIcon("images/next.png"));
	private JButton back = new JButton(new ImageIcon("images/backButton.png"));
	private JButton complete = new JButton(new ImageIcon("images/complete.png"));

	// 아쌈 방향 지정 버튼
	private JButton upButton = new JButton(new ImageIcon("images/up.png"));
	private JButton downButton = new JButton(new ImageIcon("images/downPressed.png"));
	private JButton leftButton = new JButton(new ImageIcon("images/left.png"));
	private JButton rightButton = new JButton(new ImageIcon("images/right.png"));

	// 나머지 클릭 버튼
	// 주사위
	private JButton diceRollButton = new JButton(new ImageIcon("images/diceRollImage.png"));
	// 다음 차례
	private JButton nextButton = new JButton(new ImageIcon("images/nextButton.png"));
	// undo
	private JButton undoButton = new JButton(new ImageIcon("images/undoButton.png"));

	private JPanel[][] board = new JPanel[7][7];
	Music introMusic;

	private int x1, y1, x2, y2;
	private int preCol1, preCol2;

	private int mouseX, mouseY;

	private boolean isGameScreen = false;
	private boolean isSettingScreen = false;
	private boolean isMusicOn = false;
	public static Game game;

	private Image selectedImage;
	private int nowSelected = 0;
	private int nowSelectedMusic = 0;

	public Marrakech() {
		super("Marrakech");
		this.setUndecorated(true);
		this.setBounds(0, 0, 1280, 720);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setBackground(new Color(0, 0, 0, 0));
		this.setLayout(null);

		selectMusic();
		introMusic.start();
		isMusicOn = true;
		// 종료 버튼 (JButton)
		exitButton.setBounds(1245, 0, 30, 30);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitButtonEnteredImage);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnter = new Music("buttonEnteredMusic.mp3", false);
				btnEnter.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(exitButtonBasicImage);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music btnPressed = new Music("buttonPressedMusic.mp3", false);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		add(exitButton);

		home.setVisible(false);
		home.setBounds(0, 0, 24, 24);
		home.setBorderPainted(false);
		home.setContentAreaFilled(false);
		home.setFocusPainted(false);
		home.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				home.setIcon(new ImageIcon("images/homeEntered.png"));
				home.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				home.setIcon(new ImageIcon("images/home.png"));
				home.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				backMain();
			}
		});
		add(home);

		//	음악 컨트롤 버튼
		preMusic.setBounds(900, 0, 20, 20);
		nextMusic.setBounds(960, 0, 20, 20);
		playMusic.setBounds(930, 0, 20, 20);
		preMusic.setBorderPainted(false);
		nextMusic.setBorderPainted(false);
		playMusic.setBorderPainted(false);
		preMusic.setContentAreaFilled(false);
		nextMusic.setContentAreaFilled(false);
		playMusic.setContentAreaFilled(false);
		preMusic.setFocusPainted(false);
		nextMusic.setFocusPainted(false);
		playMusic.setFocusPainted(false);
		preMusic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				preMusic.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				preMusic.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (nowSelectedMusic != 0) {
					if (isMusicOn)
						introMusic.close();
					else
						isMusicOn = true;
					nowSelectedMusic--;
				} else {
					if (isMusicOn)
						introMusic.close();
					else
						isMusicOn = true;
					nowSelectedMusic = 2;
				}
				selectMusic();
				introMusic.start();
			}
		});
		nextMusic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				nextMusic.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				nextMusic.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (nowSelectedMusic != 2) {
					if (isMusicOn)
						introMusic.close();
					else
						isMusicOn = true;
					nowSelectedMusic++;
				} else {
					if (isMusicOn)
						introMusic.close();
					else
						isMusicOn = true;
					nowSelectedMusic = 0;
				}
				selectMusic();
				introMusic.start();
			}
			
		});
		playMusic.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				playMusic.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				playMusic.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (isMusicOn) {
					introMusic.close();
					isMusicOn = false;
				} else {
					selectMusic();
					introMusic.start();
					isMusicOn = true;
				}
			}
		});
		add(preMusic);
		add(nextMusic);
		add(playMusic);

		// 시작 버튼 (JButton)
		startButton.setBounds(120, 300, 400, 100);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				startButton.setIcon(startButtonBasicImage);
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnter = new Music("buttonEnteredMusic.mp3", false);
				btnEnter.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startButtonBasicImage);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music btnPressed = new Music("buttonPressedMusic.mp3", false);
				btnPressed.start();
				settingButton.setVisible(false);
				startButton.setVisible(false);
				offlineButton.setVisible(true);
				onlineButton.setVisible(true);
				goBackButton.setVisible(true);
				background = new ImageIcon("images/gameStartImage.jpg").getImage();
			}
		});
		add(startButton);

		// 환경설정 버튼 (JButton)
		settingButton.setBounds(760, 300, 400, 100);
		settingButton.setBorderPainted(false);
		settingButton.setContentAreaFilled(false);
		settingButton.setFocusPainted(false);
		settingButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				settingButton.setIcon(settingButtonBasicImage);
				settingButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnter = new Music("buttonEnteredMusic.mp3", false);
				btnEnter.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				settingButton.setIcon(settingButtonBasicImage);
				settingButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music btnPressed = new Music("buttonPressedMusic.mp3", false);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				isSettingScreen = true;
				setting();
			}
		});
		add(settingButton);

		next.setVisible(false);
		back.setVisible(false);
		complete.setVisible(false);
		next.setBounds(1070, 215, 35, 35);
		back.setBounds(180, 215, 35, 35);
		complete.setBounds(1190, 660, 80, 40);
		next.setBorderPainted(false);
		back.setBorderPainted(false);
		complete.setBorderPainted(false);
		next.setContentAreaFilled(false);
		back.setContentAreaFilled(false);
		complete.setContentAreaFilled(false);
		next.setFocusPainted(false);
		back.setFocusPainted(false);
		complete.setFocusPainted(false);
		next.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				next.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnter = new Music("buttonEnteredMusic.mp3", false);
				btnEnter.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				next.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				selectRight();
			}
		});
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				back.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnter = new Music("buttonEnteredMusic.mp3", false);
				btnEnter.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				back.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				selectLeft();
			}
		});
		complete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				complete.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnter = new Music("buttonEnteredMusic.mp3", false);
				btnEnter.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				complete.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				backMain();
			}
		});
		add(next);
		add(back);
		add(complete);

		// 메뉴 바 (JLabel)
		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		add(menuBar);

		// 오프라인 게임 버튼
		offlineButton.setVisible(false);
		offlineButton.setBounds(100, 300, 350, 130);
		offlineButton.setBorderPainted(false);
		offlineButton.setContentAreaFilled(false);
		offlineButton.setFocusPainted(false);
		offlineButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				offlineButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnter = new Music("buttonEnteredMusic.mp3", false);
				btnEnter.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				offlineButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				offlineButton.setVisible(false);
				onlineButton.setVisible(false);
				goBackButton.setVisible(false);
				play1.setVisible(true);
				play2.setVisible(true);
				play3.setVisible(true);
				play4.setVisible(true);
			}
		});

		// 온라인 게임 버튼
		onlineButton.setVisible(false);
		onlineButton.setBounds(900, 300, 350, 130);
		onlineButton.setBorderPainted(false);
		onlineButton.setContentAreaFilled(false);
		onlineButton.setFocusPainted(false);
		onlineButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				onlineButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnter = new Music("buttonEnteredMusic.mp3", false);
				btnEnter.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				onlineButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Music btnPressed = new Music("buttonPressedMusic.mp3", false);
				// gameStart();
			}
		});

		// 뒤로가기 버튼
		goBackButton.setVisible(false);
		goBackButton.setBounds(475, 500, 350, 130);
		goBackButton.setBorderPainted(false);
		goBackButton.setContentAreaFilled(false);
		goBackButton.setFocusPainted(false);
		goBackButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				goBackButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnter = new Music("buttonEnteredMusic.mp3", false);
				btnEnter.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				goBackButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				backMain();
			}
		});
		add(offlineButton);
		add(onlineButton);
		add(goBackButton);

		// 1~4인 게임 선택 버튼 (오프라인)
		play1.setVisible(false);
		play2.setVisible(false);
		play3.setVisible(false);
		play4.setVisible(false);
		play1.setBounds(465, 100, 350, 130);
		play2.setBounds(465, 250, 350, 130);
		play3.setBounds(465, 400, 350, 130);
		play4.setBounds(465, 550, 350, 130);
		play1.setBorderPainted(false);
		play2.setBorderPainted(false);
		play3.setBorderPainted(false);
		play4.setBorderPainted(false);
		play1.setContentAreaFilled(false);
		play2.setContentAreaFilled(false);
		play3.setContentAreaFilled(false);
		play4.setContentAreaFilled(false);
		play1.setFocusPainted(false);
		play2.setFocusPainted(false);
		play3.setFocusPainted(false);
		play4.setFocusPainted(false);
		play1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				play1.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnter = new Music("buttonEnteredMusic.mp3", false);
				btnEnter.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				play1.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				gameStart(1);
			}
		});
		play2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				play2.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnter = new Music("buttonEnteredMusic.mp3", false);
				btnEnter.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				play2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				gameStart(2);
			}
		});
		play3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				play3.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnter = new Music("buttonEnteredMusic.mp3", false);
				btnEnter.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				play3.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				gameStart(3);
			}
		});
		play4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				play4.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music btnEnter = new Music("buttonEnteredMusic.mp3", false);
				btnEnter.start();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				play4.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				gameStart(4);
			}
		});
		add(play1);
		add(play2);
		add(play3);
		add(play4);

		// 주사위 버튼
		diceRollButton.setVisible(false);
		diceRollButton.setBounds(734, 591, 74, 74);
		diceRollButton.setBorderPainted(false);
		diceRollButton.setContentAreaFilled(false);
		diceRollButton.setFocusPainted(false);
		diceRollButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (game.thisState == 1) {
					diceRollButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
					diceRollButton.setIcon(new ImageIcon("images/diceRollEnterImage.png"));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {

				diceRollButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				diceRollButton.setIcon(new ImageIcon("images/diceRollImage.png"));

			}

			@Override
			public void mousePressed(MouseEvent e) {

				if (game.thisState == 1) {
					Music diceRollMusic = new Music("Shake_And_Roll_Dice.mp3", false);
					diceRollMusic.start();
					game.log.RollDice();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
					game.thisState++;
					System.out.println("주사위 숫자 : " + game.log.getDiceNum());
				}
			}
		});
		add(diceRollButton);

		// 아쌈 방향버튼 (상 하 좌 우)
		upButton.setVisible(false);
		upButton.setBounds(1165, 605, 30, 30);
		upButton.setBorderPainted(false);
		upButton.setContentAreaFilled(false);
		upButton.setFocusPainted(false);
		upButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (game.thisState == 0 && game.assam.getDirection() != 1) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
					upButton.setIcon(new ImageIcon("images/upEnter.png"));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (game.thisState == 0 && game.assam.getDirection() != 1) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					upButton.setIcon(new ImageIcon("images/up.png"));
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (game.thisState == 0 && game.assam.getDirection() != 1) {// 0: up 1:down 2:left 3:right
					upButton.setIcon(new ImageIcon("images/upPressed.png"));
					downButton.setIcon(new ImageIcon("images/down.png"));
					leftButton.setIcon(new ImageIcon("images/left.png"));
					rightButton.setIcon(new ImageIcon("images/right.png"));
					game.assam.setDirection(0);
					game.thisState++;

				}
			}
		});
		add(upButton);

		downButton.setVisible(false);
		downButton.setBounds(1165, 635, 30, 30);
		downButton.setBorderPainted(false);
		downButton.setContentAreaFilled(false);
		downButton.setFocusPainted(false);
		downButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (game.thisState == 0 && game.assam.getDirection() != 1) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
					downButton.setIcon(new ImageIcon("images/downEnter.png"));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (game.thisState == 0 && game.assam.getDirection() != 1) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					downButton.setIcon(new ImageIcon("images/down.png"));
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (game.thisState == 0 && game.assam.getDirection() != 0) {
					downButton.setIcon(new ImageIcon("images/downPressed.png"));
					upButton.setIcon(new ImageIcon("images/up.png"));
					leftButton.setIcon(new ImageIcon("images/left.png"));
					rightButton.setIcon(new ImageIcon("images/right.png"));
					game.assam.setDirection(1);
					game.thisState++;

				}
			}
		});
		add(downButton);

		leftButton.setVisible(false);
		leftButton.setBounds(1150, 620, 30, 30);
		leftButton.setBorderPainted(false);
		leftButton.setContentAreaFilled(false);
		leftButton.setFocusPainted(false);
		leftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (game.thisState == 0 && game.assam.getDirection() != 3) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
					leftButton.setIcon(new ImageIcon("images/leftEnter.png"));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (game.thisState == 0 && game.assam.getDirection() != 3) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					leftButton.setIcon(new ImageIcon("images/left.png"));
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (game.thisState == 0 && game.assam.getDirection() != 3) {
					leftButton.setIcon(new ImageIcon("images/leftPressed.png"));
					downButton.setIcon(new ImageIcon("images/down.png"));
					upButton.setIcon(new ImageIcon("images/up.png"));
					rightButton.setIcon(new ImageIcon("images/right.png"));
					game.assam.setDirection(2);
					game.thisState++;

				}
			}
		});
		add(leftButton);

		rightButton.setVisible(false);
		rightButton.setBounds(1180, 620, 30, 30);
		rightButton.setBorderPainted(false);
		rightButton.setContentAreaFilled(false);
		rightButton.setFocusPainted(false);
		rightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (game.thisState == 0 && game.assam.getDirection() != 3) {
					setCursor(new Cursor(Cursor.HAND_CURSOR));
					rightButton.setIcon(new ImageIcon("images/rightEnter.png"));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (game.thisState == 0 && game.assam.getDirection() != 3) {
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					rightButton.setIcon(new ImageIcon("images/right.png"));
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (game.thisState == 0 && game.assam.getDirection() != 2) {
					rightButton.setIcon(new ImageIcon("images/rightPressed.png"));
					downButton.setIcon(new ImageIcon("images/down.png"));
					leftButton.setIcon(new ImageIcon("images/left.png"));
					upButton.setIcon(new ImageIcon("images/up.png"));
					game.assam.setDirection(3);
					game.thisState++;
				}
			}
		});
		add(rightButton);

		// 게임의 다음 진행으로 넘어가는 버튼
		nextButton.setVisible(false);
		nextButton.setBounds(1221, 691, 30, 30);
		nextButton.setBorderPainted(false);
		nextButton.setContentAreaFilled(false);
		nextButton.setFocusPainted(false);
		nextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (game.thisState != 3 && game.thisState != 1 && game.p[game.playerNum].isUser()) {
					nextButton.setIcon(new ImageIcon("images/nextButtonPressed.png"));
					setCursor(new Cursor(Cursor.HAND_CURSOR));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				nextButton.setIcon(new ImageIcon("images/nextButton.png"));
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (game.thisState != 3 && game.thisState != 1 && game.p[game.playerNum].isUser()) {
					if (game.thisState < 7)
						game.thisState++;
					else if (game.thisState == 7)
						if (game.count == 2)
							game.thisState++;
				}
			}
		});
		add(nextButton);

		// 카펫 설치를 취소하는 버튼
		undoButton.setVisible(false);
		undoButton.setBounds(1100, 650, 88, 30);
		undoButton.setBorderPainted(false);
		undoButton.setContentAreaFilled(false);
		undoButton.setFocusPainted(false);
		undoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				undoButton.setIcon(new ImageIcon("images/undoButtonEntered.png"));
				setCursor(new Cursor(Cursor.HAND_CURSOR));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				undoButton.setIcon(new ImageIcon("images/undoButton.png"));
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (game.thisState == 7 && game.count > 0) {
					for (int i = 0; i < 7; i++) {
						for (int j = 0; j < 7; j++) {
							game.map.setPreCarpet(i, j, false);
							game.map.setPreview(i, j, false);
						}
					}
					game.map.setCarpetColor(x2, y2, preCol2);
					if (game.count == 2)
						game.map.setCarpetColor(x1, y1, preCol1);

					game.count = 0;
				}
			}
		});
		add(undoButton);

		// 카펫 패널 (설치, 미리 보여주기)
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				int x = i;
				int y = j;
				board[i][j] = new JPanel();
				board[i][j].setBounds(22 + i * 97, 23 + j * 97, 90, 90);
				board[i][j].setBackground(new Color(255, 0, 0, 0));
				board[i][j].setVisible(false);
				board[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						if (game.thisState == 7) {
							setCursor(new Cursor(Cursor.HAND_CURSOR));
							// if (game.count == 0)
							game.availvableCarpet = true;
							if (game.count == 1)
								game.map.secondView(x2, y2);
						}
					}

					@Override
					public void mouseExited(MouseEvent e) {
						if (game.thisState == 7) {
							setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
							game.availvableCarpet = false;
						}

					}

					@Override
					public void mousePressed(MouseEvent e) {
						if (game.thisState == 7 && game.count != 2 && game.p[game.playerNum].isUser()) { // 설정을 완료하기 전
																											// 상태
							Music click = new Music("buttonClick.mp3", false);
							click.start();
							if (game.count == 0) {
								if (game.map.getPreCarpet(x, y)) {
									preCol2 = game.map.getCarpetColor(x, y);
									x2 = x;
									y2 = y;
									game.map.setPreview(x, y, true);
									game.map.InitPreCarpet();
									game.map.setPreCarpet(x, y, true);
									game.count++;
								}
							} else if (game.count == 1) {
								if (game.map.secondClick(x, y, x2, y2)) {
									preCol1 = game.map.getCarpetColor(x, y);
									x1 = x;
									y1 = y;
									game.map.setPreview(x, y, true);
									game.count++;
								}
							}
						}
						if (game.count == 2) {
							for (int i = 0; i < 7; i++)
								for (int j = 0; j < 7; j++)
									if (game.map.getPreview(i, j))
										game.map.setCarpetColor(i, j, game.p[game.playerNum].getColor());
						}
					}
				});
				add(board[i][j]);
			}
		}

	}

	@Override
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphics = screenImage.getGraphics();
		screenDraw((Graphics2D) screenGraphics);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics2D g) {
		g.drawImage(background, 0, 0, null);
		paintComponents(g);
		if (isGameScreen) {
			switch (game.assam.getDirection()) {
			case 0:
				upButton.setIcon(new ImageIcon("images/upPressed.png"));
				downButton.setIcon(new ImageIcon("images/down.png"));
				leftButton.setIcon(new ImageIcon("images/left.png"));
				rightButton.setIcon(new ImageIcon("images/right.png"));
				break;
			case 1:
				upButton.setIcon(new ImageIcon("images/up.png"));
				downButton.setIcon(new ImageIcon("images/downPressed.png"));
				leftButton.setIcon(new ImageIcon("images/left.png"));
				rightButton.setIcon(new ImageIcon("images/right.png"));
				break;
			case 2:
				upButton.setIcon(new ImageIcon("images/up.png"));
				downButton.setIcon(new ImageIcon("images/down.png"));
				leftButton.setIcon(new ImageIcon("images/leftPressed.png"));
				rightButton.setIcon(new ImageIcon("images/right.png"));
				break;
			case 3:
				upButton.setIcon(new ImageIcon("images/up.png"));
				downButton.setIcon(new ImageIcon("images/down.png"));
				leftButton.setIcon(new ImageIcon("images/left.png"));
				rightButton.setIcon(new ImageIcon("images/rightPressed.png"));
				break;
			}
			if (game.thisState == 7)
				undoButton.setVisible(true);
			else
				undoButton.setVisible(false);
			game.screenDraw(g);

		}
		if (isSettingScreen) {
			selectImage();
			g.drawImage(selectedImage, 240, 40, null);
		}
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		this.repaint();
	}

	public void selectMusic() {
		switch (nowSelectedMusic) {
		case 0:
			introMusic = new Music("AcrylicOnCanvas.mp3", true);
			break;
		case 1:
			introMusic = new Music("jazzMusic.mp3", true);
			break;
		case 2:
			introMusic = new Music("rockMusic.mp3", true);
			break;
		}
	}

	public void selectImage() {
		switch (nowSelected) {
		case 0:
			selectedImage = new ImageIcon("images/setting1.png").getImage();
			break;
		case 1:
			selectedImage = new ImageIcon("images/setting2.png").getImage();
			break;
		case 2:
			selectedImage = new ImageIcon("images/setting3.png").getImage();
			break;
		}
	}

	public void selectLeft() {
		if (nowSelected == 0)
			nowSelected = 2;
		else
			nowSelected--;
	}

	public void selectRight() {
		if (nowSelected == 2)
			nowSelected = 0;
		else
			nowSelected++;
	}

	public void setting() {
		settingButton.setVisible(false);
		startButton.setVisible(false);
		background = new ImageIcon("images/settingImage.jpg").getImage();
		next.setVisible(true);
		back.setVisible(true);
		complete.setVisible(true);
	}

	public void gameStart(int n) {
		switch (nowSelected) {
		case 0:
			gameImage = new ImageIcon("images/green.png").getImage();
			break;
		case 1:
			gameImage = new ImageIcon("images/gameImage.png").getImage();
			break;
		case 2:
			gameImage = new ImageIcon("images/yoda.png").getImage();
			break;
		}
		background = gameImage;
		isGameScreen = true;
		play1.setVisible(false);
		play2.setVisible(false);
		play3.setVisible(false);
		play4.setVisible(false);
		menuBar.setBackground(new Color(255, 0, 0, 0));
		diceRollButton.setVisible(true);
		upButton.setVisible(true);
		downButton.setVisible(true);
		leftButton.setVisible(true);
		rightButton.setVisible(true);
		nextButton.setVisible(true);
		home.setVisible(true);
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				board[i][j].setVisible(true);
			}
		}
		game = new Game(n);
		game.start();
	}

	public void backMain() {
		System.out.println("뒤로가기");
		offlineButton.setVisible(false);
		onlineButton.setVisible(false);
		goBackButton.setVisible(false);
		next.setVisible(false);
		back.setVisible(false);
		complete.setVisible(false);
		diceRollButton.setVisible(false);
		upButton.setVisible(false);
		downButton.setVisible(false);
		leftButton.setVisible(false);
		rightButton.setVisible(false);
		nextButton.setVisible(false);
		undoButton.setVisible(false);
		home.setVisible(false);
		if (isGameScreen) {
			game.task3.cancel();
			isGameScreen = false;
		}
		background = new ImageIcon("images/intoImage.png").getImage();
		startButton.setVisible(true);
		settingButton.setVisible(true);

		isSettingScreen = false;
	}
}
