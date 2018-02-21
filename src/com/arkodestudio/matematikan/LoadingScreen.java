package com.arkodestudio.matematikan;

import java.io.File;

import com.arkodestudio.framework.Audio;
import com.arkodestudio.framework.Game;
import com.arkodestudio.framework.Graphics;
import com.arkodestudio.framework.Pixmap;
import com.arkodestudio.framework.Screen;
import com.arkodestudio.framework.Graphics.PixmapFormat;


public class LoadingScreen extends Screen{
	
	private float currentTick = 0f;
	private int alphaChannel = 0; // continuation from LoadSplashScreen
	private boolean isLoadCompleted = false;
	
	public LoadingScreen(Game game) {
		super(game);	
		Graphics g = game.getGraphics();
		Audio audio = game.getAudio();
		String sparator = File.separator;		
		
		Pixmap temp_bg_gamescreen_lumayan = g.newPixmap("bg_gamescreen_lumayan.png", PixmapFormat.ARGB8888);
		Pixmap temp_bg_gamescreen_susah = g.newPixmap("bg_gamescreen_susah.png", PixmapFormat.ARGB8888);
		Pixmap temp_ikan_sprite = g.newPixmap("ikan_sprite.png", PixmapFormat.ARGB8888);	
		Pixmap temp_penyelam_sprite = g.newPixmap("penyelam_sprite.png", PixmapFormat.ARGB8888);
		Pixmap temp_set_gamescreen_finish = g.newPixmap("set_gamescreen_finish.png", PixmapFormat.ARGB8888);
		Pixmap temp_set_gamescreen = g.newPixmap("set_gamescreen.png", PixmapFormat.ARGB8888);
		Pixmap temp_set_container = g.newPixmap("set_container.png", PixmapFormat.ARGB8888);
		Pixmap temp_set_level_selection = g.newPixmap("set_level_selection.png", PixmapFormat.ARGB8888);		
		Pixmap temp_set_main_menu = g.newPixmap("set_main_menu.png", PixmapFormat.ARGB8888);
		Pixmap temp_set_main_menu_peringatan =  g.newPixmap("set_main_menu_peringatan.png", PixmapFormat.ARGB8888);
		Pixmap temp_set_stage_selection = g.newPixmap("set_stage_selection.png", PixmapFormat.ARGB8888);		
		Pixmap temp_set_tahukah_anda = g.newPixmap("set_tahukah_kamu.png", PixmapFormat.ARGB8888);	
		Pixmap temp_set_confirmation_help = g.newPixmap("set_confirmation_help.png", PixmapFormat.ARGB8888);
		Pixmap temp_set_confirmation_play = g.newPixmap("set_confirmation_play.png", PixmapFormat.ARGB8888);
		Pixmap temp_set_prestasi = g.newPixmap("set_prestasi.png", PixmapFormat.ARGB8888);
		Assets.gameLogo = g.newPixmap("matematikan_logo.png", PixmapFormat.ARGB8888);
		Pixmap temp_bg_gamescreen_general = g.newPixmap("bg_gamescreen_general.png", PixmapFormat.ARGB8888);
		Assets.Bg.menu1 =  g.slicePixmap(temp_bg_gamescreen_general, new int[]{0,0,800,480});	 
		Assets.Bg.menu2 = g.slicePixmap(temp_bg_gamescreen_general, new int[]{0,480,800,600});
		Assets.Bg.menu3 = g.slicePixmap(temp_bg_gamescreen_general, new int[]{0,600,800,670});	
		temp_bg_gamescreen_general.dispose();
		Assets.Bg.levelMudah1 = Assets.Bg.menu1;	
		Assets.Bg.levelMudah2 = Assets.Bg.menu2;		
		Assets.Bg.levelMudah3 = Assets.Bg.menu3;		
		Assets.Bg.levelLumayan1 = g.slicePixmap(temp_bg_gamescreen_lumayan, new int[]{0,0,800,480});	
		Assets.Bg.levelLumayan2 = g.slicePixmap(temp_bg_gamescreen_lumayan, new int[]{0,480,800,600});		
		Assets.Bg.levelLumayan3 = g.slicePixmap(temp_bg_gamescreen_lumayan, new int[]{0,600,800,670});
		temp_bg_gamescreen_lumayan.dispose();
		Assets.Bg.levelSusah1 = g.slicePixmap(temp_bg_gamescreen_susah, new int[]{0,0,800,480});	
		Assets.Bg.levelSusah2 = g.slicePixmap(temp_bg_gamescreen_susah, new int[]{0,480,800,600});		
		Assets.Bg.levelSusah3 = g.slicePixmap(temp_bg_gamescreen_susah, new int[]{0,600,800,670});
		temp_bg_gamescreen_susah.dispose();
		Assets.Bg.Tutorial.inGame1 = g.newPixmap("bg_tutorialscreen_play_1.png", PixmapFormat.ARGB8888);
		Assets.Bg.Tutorial.inGame2 = g.newPixmap("bg_tutorialscreen_play_2.png", PixmapFormat.ARGB8888);
		Assets.Bg.Tutorial.inHelp1 = g.newPixmap("bg_tutorialscreen_help_1.png", PixmapFormat.ARGB8888);
		Assets.Bg.Tutorial.inHelp2 = g.newPixmap("bg_tutorialscreen_help_2.png", PixmapFormat.ARGB8888);		
		Assets.Button.Game.tembak = g.slicePixmap(temp_set_gamescreen, new int[]{0,0,80,80}); 
		Assets.Button.Game.tangkap = g.slicePixmap(temp_set_gamescreen, new int[]{0,80,80,160});
		Assets.Button.Game.pause= g.slicePixmap(temp_set_gamescreen, new int[]{80,80,120,120});	
		Assets.Button.back = g.slicePixmap(temp_set_main_menu, new int[]{240,0,320,80}); 
		Assets.Button.Main.about = g.slicePixmap(temp_set_main_menu, new int[]{180,60,240,120}); 
		Assets.Button.Main.help = g.slicePixmap(temp_set_main_menu, new int[]{180,0,240,60}); 
		Assets.Button.Main.play =  g.slicePixmap(temp_set_main_menu, new int[]{0,0,120,120}); 
		Assets.Button.Main.soundOff = g.slicePixmap(temp_set_main_menu, new int[]{120,60,180,120});
		Assets.Button.Main.settings = g.slicePixmap(temp_set_main_menu, new int[]{120,0,180,60});
		Assets.Button.Main.achievement = g.slicePixmap(temp_set_main_menu, new int[]{320,0,380,60});
		Assets.Button.Main.exit = g.slicePixmap(temp_set_main_menu, new int[]{320,60,380,120});	
		temp_set_main_menu.dispose();		
		Assets.Button.Confirmation.Game.lanjutkan = g.slicePixmap(temp_set_confirmation_play, new int[]{0,0,300,60});
		Assets.Button.Confirmation.Game.berikutnya = g.slicePixmap(temp_set_confirmation_play, new int[]{0,60,300,120});
		Assets.Button.Confirmation.Game.ulangi = g.slicePixmap(temp_set_confirmation_play, new int[]{0,120,240,180});
		Assets.Button.Confirmation.Game.keMenu = g.slicePixmap(temp_set_confirmation_play, new int[]{0,180,240,240});
		Assets.Button.Confirmation.Game.yesExit = g.slicePixmap(temp_set_confirmation_play, new int[]{0,240,200,300});
		Assets.Button.Confirmation.Game.noExit = g.slicePixmap(temp_set_confirmation_play, new int[]{ 0,300,200,360});
		Assets.Button.Confirmation.Game.yesReset = g.slicePixmap(temp_set_confirmation_play, new int[]{0,360,140,420});
		Assets.Button.Confirmation.Game.noReset = g.slicePixmap(temp_set_confirmation_play, new int[]{140,360,280,420});
		temp_set_confirmation_play.dispose();
		Assets.Button.Confirmation.Help.lanjutkan = g.slicePixmap(temp_set_confirmation_help, new int[]{0,0,250,50});
		Assets.Button.Confirmation.Help.berikutnya = g.slicePixmap(temp_set_confirmation_help, new int[]{0,50,190,100});
		Assets.Button.Confirmation.Help.keMenu = g.slicePixmap(temp_set_confirmation_help, new int[]{0,100,190,150});
		temp_set_confirmation_help.dispose();
		Assets.Button.Level.lumayan = g.slicePixmap(temp_set_level_selection, new int[]{0,80,240,160}); 
		Assets.Button.Level.mudah = g.slicePixmap(temp_set_level_selection, new int[]{0,0,240,80});	
		Assets.Button.Level.susah = g.slicePixmap(temp_set_level_selection, new int[]{0,160,240,240});
		Assets.Button.Level.locked = g.slicePixmap(temp_set_level_selection, new int[]{0,240,240,320});
		Assets.Button.Setttings.reset =  g.slicePixmap(temp_set_level_selection, new int[]{0,320,240,400});
		Assets.Button.Setttings.soundOff =  g.slicePixmap(temp_set_level_selection, new int[]{0,400,240,480});
		Assets.Button.Setttings.soundOn =  g.slicePixmap(temp_set_level_selection, new int[]{0,480,240,560});
		temp_set_level_selection.dispose();	
		Assets.Button.Stage.ke1 = g.slicePixmap(temp_set_stage_selection, new int[]{0,0,130,108});
		Assets.Button.Stage.ke2 = g.slicePixmap(temp_set_stage_selection, new int[]{130,0,260,108});
		Assets.Button.Stage.ke3 = g.slicePixmap(temp_set_stage_selection, new int[]{260,0,390,108});
		Assets.Button.Stage.ke4 = g.slicePixmap(temp_set_stage_selection, new int[]{390,0,520,108});
		Assets.Button.Stage.ke5 = g.slicePixmap(temp_set_stage_selection, new int[]{520,0,650,108});
		Assets.Button.Stage.ke6 = g.slicePixmap(temp_set_stage_selection, new int[]{0,108,130,216});
		Assets.Button.Stage.ke7 = g.slicePixmap(temp_set_stage_selection, new int[]{130,108,260,216});
		Assets.Button.Stage.ke8 = g.slicePixmap(temp_set_stage_selection, new int[]{260,108,390,216});
		Assets.Button.Stage.ke9 = g.slicePixmap(temp_set_stage_selection, new int[]{390,108,520,216});
		Assets.Button.Stage.ke10 = g.slicePixmap(temp_set_stage_selection, new int[]{520,108,650,216}); 
		Assets.Button.Stage.locked = g.slicePixmap(temp_set_stage_selection, new int[]{0,216,130,324});
		Assets.Button.Stage.starLocked = g.slicePixmap(temp_set_stage_selection, new int[]{130,216,260,248}); 
		Assets.Button.Stage.star0 = g.slicePixmap(temp_set_stage_selection, new int[]{260,216,390,248});
		Assets.Button.Stage.star1 = g.slicePixmap(temp_set_stage_selection, new int[]{390,216,520,248});
		Assets.Button.Stage.star2 = g.slicePixmap(temp_set_stage_selection, new int[]{520,216,650,248});
		Assets.Button.Stage.star3 = g.slicePixmap(temp_set_stage_selection, new int[]{130,248,260,280});	
		temp_set_stage_selection.dispose();
		Assets.Font.ikan = g.newPixmap("ikan_font.png", PixmapFormat.ARGB8888); 
		Assets.Font.container = g.newPixmap("container_font.png", PixmapFormat.ARGB8888);
		Assets.Font.general = Assets.Font.container; 
		Assets.Ikan.hijauAction1 = g.slicePixmap(temp_ikan_sprite, new int[]{110,0,220,60});
		Assets.Ikan.hijauAction2 = g.slicePixmap(temp_ikan_sprite, new int[]{110,60,220,120}); 
		Assets.Ikan.hijauAction3 = g.slicePixmap(temp_ikan_sprite, new int[]{110,120,220,180}); 
		Assets.Ikan.kuningAction1 = g.slicePixmap(temp_ikan_sprite, new int[]{220,0,330,60});
		Assets.Ikan.kuningAction2 = g.slicePixmap(temp_ikan_sprite, new int[]{220,60,330,120});
		Assets.Ikan.kuningAction3 = g.slicePixmap(temp_ikan_sprite, new int[]{220,120,330,180});
		Assets.Ikan.merahAction1 = g.slicePixmap(temp_ikan_sprite, new int[]{0,0,110,60}); 
		Assets.Ikan.merahAction2 = g.slicePixmap(temp_ikan_sprite, new int[]{0,60,110,120}); 
		Assets.Ikan.merahAction3 = g.slicePixmap(temp_ikan_sprite, new int[]{0,120,110,180}); 
		Assets.Ikan.unguAction1 = g.slicePixmap(temp_ikan_sprite, new int[]{330,0,440,60}); 
		Assets.Ikan.unguAction2 = g.slicePixmap(temp_ikan_sprite, new int[]{330,60,440,120});
		Assets.Ikan.unguAction3 = g.slicePixmap(temp_ikan_sprite, new int[]{330,120,440,180});
		temp_ikan_sprite.dispose();	
		Assets.Ikan.Effect.min10 = g.newPixmap("ikan_effect_min10.png", PixmapFormat.ARGB4444);
		Assets.Penyelam.act1 = g.slicePixmap(temp_penyelam_sprite, new int[]{0,0,232,75});
		Assets.Penyelam.act2 = g.slicePixmap(temp_penyelam_sprite, new int[]{0,75,232,150});
		Assets.Penyelam.act3 = g.slicePixmap(temp_penyelam_sprite, new int[]{0,150,232,225});
		temp_penyelam_sprite.dispose();
		Assets.Penyelam.jaring = g.slicePixmap(temp_set_gamescreen, new int[]{80,40,160,80});
		Assets.Penyelam.tombak = g.slicePixmap(temp_set_gamescreen, new int[]{80,20,160,30});
		Assets.InGame.Operator.bagi = g.slicePixmap(temp_set_gamescreen, new int[]{140,0,160,20});
		Assets.InGame.Operator.kali = g.slicePixmap(temp_set_gamescreen, new int[]{100,0,120,20});
		Assets.InGame.Operator.kurang = g.slicePixmap(temp_set_gamescreen, new int[]{120,0,140,10});
		Assets.InGame.Operator.tambah = g.slicePixmap(temp_set_gamescreen, new int[]{80,0,100,20});
		Assets.InGame.Operator.samaDengan = g.slicePixmap(temp_set_gamescreen, new int[]{120,0,140,20});
		temp_set_gamescreen.dispose();
		Assets.InGame.Finished.clear1 = g.slicePixmap(temp_set_gamescreen_finish, new int[]{0,240,210,360});
		Assets.InGame.Finished.clear2 = g.slicePixmap(temp_set_gamescreen_finish, new int[]{0,120,210,240});
		Assets.InGame.Finished.clear3 = g.slicePixmap(temp_set_gamescreen_finish, new int[]{0,0,210,120});
		Assets.InGame.Finished.failed = g.slicePixmap(temp_set_gamescreen_finish, new int[]{0,360,210,480});
		Assets.InGame.Finished.scoreBest = g.slicePixmap(temp_set_gamescreen_finish, new int[]{0,560,180,590});
		Assets.InGame.Finished.scoreInfo = g.slicePixmap(temp_set_gamescreen_finish, new int[]{0, 495,100,550});
		temp_set_gamescreen_finish.dispose();
		Assets.Container.kapalKecil = g.slicePixmap(temp_set_container,new int[]{0,0,60,70}); 
		Assets.Container.kapalBesar = g.slicePixmap(temp_set_container,new int[]{60,0,180,70});
		Assets.Container.tong = g.slicePixmap(temp_set_container,new int[]{180,0,300,70});
		temp_set_container.dispose();
		Assets.Knowledge.frame = g.slicePixmap(temp_set_tahukah_anda, new int[]{0,550,630,630});
		Assets.Knowledge.Content.info1 = g.slicePixmap(temp_set_tahukah_anda, new int[]{0,0,630,55});
		Assets.Knowledge.Content.info2 = g.slicePixmap(temp_set_tahukah_anda, new int[]{0,55,630,110});
		Assets.Knowledge.Content.info3 = g.slicePixmap(temp_set_tahukah_anda, new int[]{0,110,630,165});
		Assets.Knowledge.Content.info4 = g.slicePixmap(temp_set_tahukah_anda, new int[]{0,165,630,220});
		Assets.Knowledge.Content.info5 = g.slicePixmap(temp_set_tahukah_anda, new int[]{0,220,630,275});
		Assets.Knowledge.Content.info6 = g.slicePixmap(temp_set_tahukah_anda, new int[]{0,275,630,330});
		Assets.Knowledge.Content.info7 = g.slicePixmap(temp_set_tahukah_anda, new int[]{0,330,630,385});
		Assets.Knowledge.Content.info8 = g.slicePixmap(temp_set_tahukah_anda, new int[]{0,385,630,440});
		Assets.Knowledge.Content.info9 = g.slicePixmap(temp_set_tahukah_anda, new int[]{0,440,630,495});
		Assets.Knowledge.Content.info10 = g.slicePixmap(temp_set_tahukah_anda, new int[]{0,495,630,550});
		temp_set_tahukah_anda.dispose();
		Assets.Achievement.achievedSign = g.slicePixmap(temp_set_prestasi, new int[]{0,520,65,585});
		Assets.Achievement.unAchievedSign = g.slicePixmap(temp_set_prestasi, new int[]{65,520,130,585});
		Assets.Achievement.clearingStage1= g.slicePixmap(temp_set_prestasi, new int[]{0,0,235,65});
		Assets.Achievement.clearingMudah = g.slicePixmap(temp_set_prestasi, new int[]{0,65,235,130});
		Assets.Achievement.completingMudah = g.slicePixmap(temp_set_prestasi, new int[]{0,130,235,195});
		Assets.Achievement.clearingLumayan = g.slicePixmap(temp_set_prestasi, new int[]{0,195,235,260});
		Assets.Achievement.completingLumayan = g.slicePixmap(temp_set_prestasi, new int[]{0,260,235,325});
		Assets.Achievement.clearingSusah = g.slicePixmap(temp_set_prestasi, new int[]{0,325,235,390});
		Assets.Achievement.completingSusah = g.slicePixmap(temp_set_prestasi, new int[]{0,390,235,455});
		Assets.Achievement.completingAll = g.slicePixmap(temp_set_prestasi, new int[]{0,455,235,520});
		temp_set_prestasi.dispose();
		Assets.Bg.Credit.screen1 = g.newPixmap("bg_credit_1.png", PixmapFormat.ARGB4444);
		Assets.Notification.exitGame = g.slicePixmap(temp_set_main_menu_peringatan, new int[]{0,180,700,230});
		Assets.Notification.resetGame = g.slicePixmap(temp_set_main_menu_peringatan, new int[]{0,0,700,180});		
		temp_set_main_menu_peringatan.dispose();
		Assets.Bg.Credit.screen2 = g.newPixmap("bg_credit_2.png", PixmapFormat.ARGB4444);
		Assets.Bg.Credit.screen3 = g.newPixmap("bg_credit_3.png", PixmapFormat.ARGB4444);
		Assets.Audio.BGM.menuGeneral = audio.createMusic("audio" + sparator +"bgm_general.ogg");
		Assets.Audio.BGM.menuMudah = audio.createMusic("audio" + sparator +"bgm_mudah.ogg");
		Assets.Audio.BGM.menuLumayan = audio.createMusic("audio" + sparator +"bgm_lumayan.ogg");
		Assets.Audio.BGM.menuSusah = audio.createMusic("audio" + sparator +"bgm_susah.ogg");
		Assets.Audio.Effect.click = audio.createSound("audio" + sparator + "effect_button_click.ogg");
		Assets.Audio.Effect.collideFish = audio.createSound("audio" + sparator + "effect_collide_fish_penyelam.ogg");
		Assets.Audio.Effect.hitFishJaring = audio.createSound("audio" + sparator + "effect_collide_fish_jaring.ogg");
		Assets.Audio.Effect.hitFishTombak = audio.createSound("audio" + sparator + "effect_collide_fish_tombak.ogg");
		Assets.Audio.Effect.shootJaring = audio.createSound("audio" + sparator + "effect_press_jaring.ogg");
		Assets.Audio.Effect.shootTombak = audio.createSound("audio" + sparator + "effect_press_tombak.ogg");
		
		Settings.load(game.getFileIO());			
		/*
		temp_bg_gamescreen_general.dispose();
		temp_bg_gamescreen_mudah.dispose();
		temp_bg_gamescreen_lumayan.dispose();
		temp_bg_gamescreen_susah.dispose();		
		temp_ikan_sprite.dispose();	
		temp_penyelam_sprite.dispose();
		temp_set_gamescreen_finish.dispose();
		temp_set_gamescreen.dispose();
		temp_set_container.dispose();
		temp_set_level_selection.dispose();		
		temp_set_main_menu.dispose();
		temp_set_main_menu_peringatan.dispose();
		temp_set_stage_selection.dispose();		
		temp_set_tahukah_anda.dispose();	
		temp_set_confirmation_help.dispose();
		temp_set_confirmation_play.dispose();		
		temp_set_prestasi.dispose();
		*/
		
		temp_bg_gamescreen_general = null;		
		temp_bg_gamescreen_lumayan = null;
		temp_bg_gamescreen_susah= null; 	
		temp_ikan_sprite = null;	
		temp_penyelam_sprite = null;
		temp_set_gamescreen_finish = null;
		temp_set_gamescreen = null;
		temp_set_container = null;
		temp_set_level_selection = null;		
		temp_set_main_menu = null;
		temp_set_main_menu_peringatan = null;
		temp_set_stage_selection = null;		
		temp_set_tahukah_anda = null;	
		temp_set_confirmation_help = null;
		temp_set_confirmation_play = null;
		temp_set_prestasi = null;
		isLoadCompleted = true;
	}

	@Override
	public void update(float deltaTime) {
		if (isLoadCompleted) {				
			if (currentTick > 1.0f){		
				game.setScreen(new MainMenuScreen(game));
			}
		}
	}

	@Override
	public void present(float deltaTime) {	
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.loadingScreen, 0, 0);
		currentTick += deltaTime;
		if (currentTick <=1.0f) {
			alphaChannel += (int)((90) * deltaTime);
			if (alphaChannel >= 255) {
				alphaChannel = 255;
			}
		}  
		g.drawARGB(alphaChannel, 0, 0, 0);
	}

	@Override
	public void pause() {		
		
	}

	@Override
	public void resume() {
				
	}

	@Override
	public void dispose() {
		Assets.loadingScreen.dispose();
		Assets.splashScreen.dispose();
		Assets.loadingScreen = null;
		Assets.splashScreen = null;
	}
	@Override
	public void backButton() {
		// TODO Auto-generated method stub
		
	}
}
