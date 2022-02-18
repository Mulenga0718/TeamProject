package service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import dao.MusicDao;
import util.MusicPlayMethod;
import util.ScanUtil;
import util.View;

public class MusicService {
	// 싱글톤 패턴
	private MusicService() {
	}

	private static MusicService instance;

	public static MusicService getInstance() {
		if (instance == null) {
			instance = new MusicService();
		}
		return instance;
	}

	private MyListService myListService = MyListService.getInstance();
	
	
	private MusicDao musicDao = MusicDao.getInstance(); // 객체 생성
	private MusicPlayMethod musicPlayMethod = MusicPlayMethod.getInstance();// 객체 생성
	public static List<Map<String, Object>> mlist;
	public static Map<String, Object> currentmus; // 음악정보
	public static Map<String, Object> subCheck;
	public static Map<String, Object> musicCheck;
//	[1.노래검색 2.인기차트 3.최신 음악 4.추천노래목록 
//5.내 음악 6.마이페이지 7.공지사항 8.문의사항 9.로그아웃 0.프로그램종료]

	public int musicsearch() { // 1.노래검색
		System.out.print("검색>");
		String serching = ScanUtil.nextLine();
		mlist = musicDao.search(serching);
		System.out.println("=============================================");
		System.out.println("번호\t곡명\t\t\t가수");
		System.out.println("---------------------------------------------");
		for (int i = 0; i < mlist.size(); i++) {
	 
			String str = mlist.get(i).get("M_NAME").toString();
			 if(str.length()>11) {
				str = str.substring(0,12)+"..";
			    } 
			String str2 = mlist.get(i).get("M_SINGER").toString();
			if(str2.length()>11) {
				str2 = str2.substring(0,12)+"..";
			}
			System.out.printf("%d\t%-17s\t%-10s",i+1,str,str2);
			System.out.println();
		
		}
		System.out.println("=============================================");
		System.out.print(" (1) 노래 선택 (0) 뒤로가기 >");
		int num = ScanUtil.nextInt();
		switch (num) {
		case 1:
			System.out.print("몇 번을 선택하시겠습니까?");
			int choice = ScanUtil.nextInt() - 1;
			currentmus = mlist.get(choice);
			if (Login.loginMember != null) {
				MusicService.subCheck = musicDao.subcheck();
				MusicService.musicCheck = musicDao.musiccheck();
				if (MusicService.subCheck.get("SUBCHECK") == null) {MusicService.subCheck.put("SUBCHECK", 0);}
				if (MusicService.musicCheck.get("MUSICCHECK") == null) {MusicService.musicCheck.put("MUSICCHECK","");};
			}
			
			if (Login.loginMember == null) {
				return View.MUSIC_NOMEMPLAY;
			} else if (Integer.parseInt(String.valueOf(MusicService.subCheck.get("SUBCHECK"))) > 0) {
				return View.MUSIC_PLAY;
			} else if (String.valueOf(MusicService.musicCheck.get("MUSICCHECK")).equals(MusicService.currentmus.get("M_ADDR")) ) {
				// 회원이지만																					// 노래구매했다
				return View.MUSIC_PLAY;
			} else {
				return View.MUSIC_NOSUBPLAY;
			}
		
	
		case 0:
			if (Login.loginMember != null) {
			return View.MAIN;
		} else {
			return View.HOME;
			}	
		}
		return View.MUSIC_SEARCH;
	}

	// 회원노래 재생
	Timer m = new Timer();
	boolean playing = true;
        public int musicPlay() throws UnsupportedAudioFileException, IOException, LineUnavailableException { // 노래차트에서 뮤직 //
		// 플레이 할 때
		musicDao.addcount(); // 노래 재싱 시 카운트 올라가기
		String str = currentmus.get("M_ADDR").toString();
		File file = new File("D:" + File.separator + "project" + File.separator + str + ".wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		musicinfo();
		clip.start();
		// 음악이 시작되고
		
		int response = 2;
		while (response != 0) {
			System.out.print("(1) ▶⏸ (2)🔁 (3) Lyrics (4) PlayList  \n(5)이전곡  (6)다음곡  (7)♥좋아요 (8)댓글 (9)음원 구매 (0)종료 >");
			response = ScanUtil.nextInt();
			switch (response) {
			case 1:
				if (playing == true) { // 재생 중이면 멈추기
					clip.stop();
					playing = false;
				} else if (playing == false) {// 멈춰있으면 재생하기
					clip.start();
					playing = true;
				}
				break;
			case 2: // 다시듣기
				clip.setMicrosecondPosition(0);
				break;
			case 3: // 가사 출력하기 clob 형식 출력하기
				Map<String, Object> read = musicDao.readLyrics();
				System.out.println("=============================================");
				System.out.println(read.get("M_LYRICS"));
				System.out.println("=============================================");
				
				break; // 가사보기
			case 4:
				myListService.addMyList(); // 플레이리스트에 추가하기
				break;
			case 5:
				try {
					for (int i = 0; i < mlist.size(); i++) {
						if (mlist.get(i).get("M_ADDR").toString().equals(currentmus.get("M_ADDR").toString())) {
							currentmus = mlist.get(i - 1);
							clip.close();
							return View.MUSIC_PLAY;
						}
					}
				} catch (Exception e) {
					System.out.println("이전 곡이 없습니다.");
					break;
				}
			case 6:
				try {
					for (int i = 0; i < mlist.size(); i++) {
						if (mlist.get(i).get("M_ADDR").toString().equals(currentmus.get("M_ADDR").toString())) {
							currentmus = mlist.get(i + 1);
							clip.close();
							return View.MUSIC_PLAY;
						}
					}
				} catch (Exception e) {
					System.out.println("다음 곡이 없습니다.");
					break;
				}
			case 7: // 좋아요 누르기
				musicDao.addLike();
				break;
			case 8:
				if(("admin").equals((String)Login.loginMember.get("ID"))) {
					adminMcomment();
				}else {
				musiccomment();}
				break;
			case 9: clip.stop();
				if(String.valueOf(MusicService.musicCheck.get("MUSICCHECK")).equals(MusicService.currentmus.get("M_ADDR"))){
					System.out.println("이미 구매한 음원입니다");
				}else {
				return View.BUYMUSIC;}
			case 0: // 종료하기
				clip.stop();
				if (Login.loginMember == null) {
					return View.HOME;
				} else {
					return View.MAIN;
				}
			}
		}
		return View.MUSIC_PLAY;
	}
	public int musicChart() {// 인기차트
		List<Map<String, Object>> musicChart = musicDao.chart();
		mlist = musicChart;
		int result = musicPlayMethod.playMethod(musicChart);
		return result;
	}

	public int recentChart() { // 최신 차트
		List<Map<String, Object>> recentChart = musicDao.recentSelect();
		mlist = recentChart;
		int result = musicPlayMethod.playMethod(recentChart);
		return result;
	}

	public int recomChart() { // 추천차트 //비회원은 (1)번 개발자추천 이용불가
		System.out.println();
		System.out.print("(1)개발자 추천 (2)선호 장르 추천 (3)기분에 맞는 노래 추천 (0)뒤로가기 >");
		int input = ScanUtil.nextInt();
		switch (input) {
		case 1: // 개인정보 //가장많이 들은 장르, 분위기 ,나이 (랜덤으로 뽑아주기)
			if (Login.loginMember == null) {
				System.out.println("로그인이 필요한 서비스입니다");
				return View.HOME;
			}
			mlist = musicDao.recomGF();
		List<Map<String, Object>> mlist1 =musicDao.recomAge(); //List<Map<String,Object> 타입
			for(int i =0; i<mlist1.size(); i++) {
			 	mlist.add(mlist1.get(i));
			}
			Collections.shuffle(mlist); //무작위로 섞어주기
			int result = musicPlayMethod.playMethod(mlist);
			return result;
		case 2:
			System.out.print("(1)발라드 (2)댄스 (3)락 (4)힙합 >");
			int gen = ScanUtil.nextInt();
			List<Map<String, Object>> genList = musicDao.selectgen(gen);
			mlist = genList;
			int result2 = musicPlayMethod.playMethod(genList);
			return result2;
		case 3:
			System.out.print("(1)행복 (2)우울 (3)편안함 (4)분노 >");
			int feel = ScanUtil.nextInt();
			List<Map<String, Object>> feelList = musicDao.selectfeel(feel);
			mlist = feelList;
			result = musicPlayMethod.playMethod(feelList);
			return result;
		case 4:
			return View.HOME;
		}
		return View.HOME;
	}

	public void musiccomment() { //음악댓글
		List<Map<String, Object>> list = musicDao.musiccomment();
	
		try {
			System.out.println("=================댓글=======================");
			for (int i = 0; i < list.size(); i++) {
				System.out.print(i + 1);
				System.out.print("\t" + list.get(i).get("ID"));
				System.out.println("\t" + list.get(i).get("M_COMMENT"));
			}
			System.out.println("===========================================");
		} catch (Exception e) {
			System.out.println("[등록된 댓글이 없습니다]");
		}

		System.out.print("(1) 등록 (2)뒤로가기");
		int result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			System.out.println("--------------------------------------------");
			System.out.print("댓글: ");
			String comment = ScanUtil.nextLine();
			result = musicDao.commentadd(comment);
			if (result > 0) {
				System.out.println("[등록이 완료되었습니다]");
			} else {
				System.out.println("[등록에 실패하였습니다]");
			}
			break;
		case 2:
			break;
		}
	}
	public void adminMcomment() { 
		List<Map<String, Object>> list = musicDao.musiccomment();
		try {
			for (int i = 0; i < list.size(); i++) {
				System.out.print(i + 1);
				System.out.print("\t" + list.get(i).get("ID"));
				System.out.println("\t" + list.get(i).get("M_COMMENT"));
			}
		} catch (Exception e) {
			System.out.println("[등록된 댓글이 없습니다]");
		}
		System.out.print("(1)등록 (2)삭제 (0)뒤로가기");
		int result = ScanUtil.nextInt();
		switch (result) {
		case 1:
			System.out.print("댓글: ");
			String comment = ScanUtil.nextLine();
			result = musicDao.commentadd(comment);
			if (result > 0) {
				System.out.println("등록이 완료되었습니다.");
			} else {
				System.out.println("등록에 실패하였습니다.");
			}
			break;
		case 2: System.out.println("몇번 글을 삭제하시겠습니까? >");
			int num = ScanUtil.nextInt();
			result =  musicDao.commentdel(Integer.parseInt(String.valueOf(list.get(num-1).get("M_CNO"))));
			if (result > 0) {
				System.out.println("삭제가 완료되었습니다.");
			} else {
				System.out.println("삭제를 실패하였습니다.");
			} break;
		case 0: break;	
		}
	}


	// 비회원 음악 재생
	public int noMemPlay() throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		musicDao.addcount(); // 노래 재싱 시 카운트 올라가기
		// 비회원 노래재생 (30초 재생 후 홈 화면)
		playing = true;
		String str = currentmus.get("M_ADDR").toString();
		File file = new File("D:" + File.separator + "project" + File.separator + str + ".wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		musicinfo();
		clip.start();// 음악재생
		Timer m = new Timer();

		TimerTask task = new TimerTask() {

			@Override
			public void run() {
				System.out.println("[로그인 전에는 30초 듣기만 가능하며,\n" + "이용권 권한에 따른 전곡듣기는 로그인 후 이용하실 수 있습니다]");
				// 홈화면으로 이동
				clip.stop();
				System.out.println("(0) 홈으로 이동");
			}
		};
		m.schedule(task, 30000);
		int response = 0;
		outer: while (response != 4) {
			System.out.print("(1) ⏸ (2) 🔁 (3) Lyrics (0) 종료 >");	
			response = ScanUtil.nextInt();
			switch (response) {
			case 1:
				clip.stop();
				task.cancel();// 테스크 캔슬
				break;
			case 2: // 재생 중 처음부터 다시듣기
				clip.setMicrosecondPosition(0);
				task.cancel();
				task = new TimerTask() {
					@Override
					public void run() {
						System.out.println("[비회원은 30초만 재생가능]");
						//비회원 : home화면가야함 
						clip.stop();
					}
				};
				m.schedule(task, 30000); // 30초시작
				break;
			case 3: // 가사 출력하기 clob 형식 출력하기
				Map<String, Object> read = musicDao.readLyrics();
				System.out.println(read.get("M_LYRICS"));
				continue outer; // 가사보기
			case 0: // 종료하기
				m.cancel();
				clip.stop();
				return View.HOME;
			}
		}
		return View.HOME;
	}
	public void musicinfo() { //음악정보 불러오기
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
		System.out.println("==================================");
		System.out.println("곡명: " + currentmus.get("M_NAME"));
		System.out.println("가수: " + currentmus.get("M_SINGER"));
		System.out.println("발매일: " + format.format(currentmus.get("M_DATE")));
		
		Map<String, Object> likenum =  musicDao.countlike(Integer.parseInt(String.valueOf(currentmus.get("M_NO"))));
		try{System.out.println("좋아요 수: " + likenum.get("LIKECOUNT"));}catch(Exception e) {
			System.out.println("좋아요 수: 0");
		}
		System.out.println("==================================");
	}
}
