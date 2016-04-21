package media;

import java.io.File;

import saint.media.SimplePlayer;
/**
 * 音乐播放器
 * @author 小Y
 *
 */
public class Player {
	
	private static SimplePlayer musicPlayer = new SimplePlayer();
	private static SimplePlayer soundPlayer = null;
	
	
	/**
	 * 播放MP3音乐
	 * @param .mp3文件的文件名
	 */
	public static void playMusic(String name) {
		
	         
		try{
			musicPlayer.open(new File("media/music/" + name + ".mp3"));
			
			musicPlayer.setLoop(true);
			musicPlayer.setLoopCount(1000);
		}catch (Exception e) {
			System.err.println("Error！");
			return;
		}
		try {
			musicPlayer.play();	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 播放MP3音效
	 * @param .mp3文件的文件名
	 */
	public static void playSound(String name) {
		soundPlayer = new SimplePlayer();
		try {
			soundPlayer.open(new File("media/sound/" + name + ".mp3"));
			soundPlayer.setLoop(false);
		} catch (Exception e) {
			System.err.println("Error!");
			return;
		}
		try {
			soundPlayer.play();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 停止mp3音乐
	 */
	public static void stopMusic() {
//		musicPlayer.setVolume(0);
//	musicPlayer.setVolume(musicPlayer.getVolume()/2);
			
		musicPlayer.stop();
	}


	
}