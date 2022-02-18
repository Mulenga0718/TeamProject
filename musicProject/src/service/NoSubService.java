package service;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import dao.MusicDao;
import util.ScanUtil;
import util.View;

public class NoSubService {

	private NoSubService() {

	}

	private static NoSubService instance;

	public static NoSubService getInstance() {
		if (instance == null) {
			instance = new NoSubService();

		}
		return instance;
	}

	private MusicDao musicDao = MusicDao.getInstance();
	private MyListService myListService = MyListService.getInstance();
	private MusicService musicService = MusicService.getInstance();
	// 회원이지만 구독권 없는 경우
	public int noSubPlay() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		musicDao.addcount(); // 조회수 올라가기
		String str = MusicService.currentmus.get("M_ADDR").toString();
		File file = new File("D:" + File.separator + "project" + File.separator + str + ".wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		musicService.musicinfo();
		clip.start();
		Timer m = new Timer();

		TimerTask task = new TimerTask() {

			@Override
			public void run() {
			System.out.println("\n[회원님은 현재 30초 듣기만 가능합니다"
					+ "\n스트리밍 가능한 이용권 구매 후 사용해주세요]");
				clip.stop();
				//구매유도
			}
		};
		m.schedule(task, 30000);
		int response = 22;
		outer: while (response != 0) {
			System.out.print("(1)⏸ (2) 🔁 (3)Lyrics (4) PlayList  (5)♥좋아요 (6)댓글 (7)음원 구매 (0)종료>");
			response = ScanUtil.nextInt();
			switch (response) {
			case 1:
				clip.stop();
				task.cancel();
				break;
			case 2:
				clip.setMicrosecondPosition(0);
				task.cancel();
				task = new TimerTask() {
					@Override
					public void run() {
						System.out.println();
						System.out.println("\n[회원님은 현재 30초 듣기만 가능합니다. \n스트리밍 가능한 이용권 구매 후 사용해주세요]");
						System.out.println("(0)종료");
						clip.stop();
						
					}
				};
				m.schedule(task, 30000); // 30초시작
				break;
			case 3:
				
				Map<String, Object> read = musicDao.readLyrics();
				System.out.println(read.get("M_LYRICS"));
				continue outer;
			case 4:
				myListService.addMyList(); // 플레이리스트에 추가하기
				continue outer;
			case 5:
				musicDao.addLike();
				continue outer;
			case 6:
				musicService.musiccomment();
				continue outer;
			case 7:
				clip.stop();
				task.cancel();
				return View.BUYMUSIC;
			case 0:
				m.cancel();
				clip.stop();
				return View.MAIN;
			}
		}
		return View.MAIN;
	}

}
