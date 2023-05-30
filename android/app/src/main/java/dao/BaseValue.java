package dao;

public interface BaseValue {

    public static final String TAG = "kollus_renewal";
    public static String TitleString = "title";

    public static final String KOLLUS_PLAYER_USER_AGENT = "kollusplayer/";//"StarPlayer2/1.0.0.34";// ;
    public static final String KollusScheme = "kollusapp";
    public static final String PAYNOW_PACKAGE_NAME = "com.lguplus.paynow";

    //특정 단말 이슈로 인한 단말(모델) 예외 처리 리스트
    public static final String MODEL_NOT_SUPPORT_ITQ701 = "ITQ701";
    public static final String MODEL_NOT_SUPPORT_SM_T860 = "SM-T860";
    public static final String MODEL_NOT_SUPPORT_SHW_M250L = "SHW-M250L";
    public static final String MODEL_NOT_SUPPORT_SHV_E270S = "SHV-E270S";

    //    public String WEB_GENERAL_QUESTION_URL = "http://info.kollus.com/faq/";
    public String WEB_GENERAL_QUESTION_URL = "https://support.catenoid.net/pages/viewpage.action?pageId=1081689";

    public static final int DEVELOP = 10;

    public static final String DAY_31_MILLI_SECOND_SUM =  "2678400000";
    public static final String DAY_WEEKEND_SUM         =  "604800000";
    public static final int RECENTLY_MAX_COUNT         =   30;
    public static final int SECOND_10_MILLI                  = 10000;

    //player control animation time
    public static final int PLAYER_CONTROL_ANIMATION_TIME_VOLUME         =   5000; //millisecond
    public static final int PLAYER_CONTROL_ANIMATION_TIME_BRIGHT         =   5000; //millisecond
    public static final int SHOW_CONTROL_DURATION = 5000;
    public static final int SHOW_FIRST_CONTROL_DURATION = 5000;
    public static final int SHOW_CONTROL_HIDE_DURATION = 3000;

    //Mega byte
    public static final int MB_1 = 1048576;
    public static final int MB_2 = 2097152;

    // DEVICE TYPE
    public static final int DEVICE_MOBILE = 0;
    public static final int DEVICE_7_INCH_TABLET = 1;
    public static final int DEVICE_10_INCH_TABLET = 2;
    public static final String KEY_IS_DEVICE_TABLET = "is_device_tablet";

    //Kollus renewal data
    public static final String DIR_ROOT_PATH        = "/";

    public static final String TYPE_DIRECTORY        = "dir";
    public static final String TYPE_FILE             = "file";

    //lottie animation file name define
    public static final String LOTTIE_ANIMATION_MUTE             = "mute.json";
    public static final String LOTTIE_ANIMATION_UNMUTE             = "unmute.json";

    //플레이어 설정 KEY
    public static final String KEY_PLAYER_SKIP_TIME = "player_skip_time";
    public static final String KEY_PLAYER_SCREEN_RATIO = "player_screen_ratio";
    public static final String KEY_PLAYER_AUDIO_SINK = "player_audio_sink";
    public static final String KEY_PLAYER_VOLUME = "player_volume";
    public static final String KEY_PLAYER_MAX_VOLUME = "player_max_volume";
    public static final String KEY_PLAYER_BRIGHTNESS = "player_brightness";
    public static final String KEY_PLAYER_PLAYING_RATE = "player_playing_rate";
    public static final String KEY_PLAYER_BANDWIDTH_INDEX = "player_bandwidth_index";
    public static final String KEY_IS_BLUETOOTH_AUDIO_SINK_CHECK = "dlg_bt_audio_sink_check";
    public static final String KEY_SUBTITLE_MAIN_UI_ENABLE = "player_subtitle_main_ui_enable";
    public static final String KEY_SUBTITLE_SUB_UI_ENABLE = "player_subtitle_sub_ui_enable";
    public static final String KEY_SUBTITLE_LANGUAGE_INDEX = "player_subtitle_language_index";
    public static final String KEY_PLAYER_SUBTITLE_BACKGROUND = "player_is_subtitle_background";
    public static final String KEY_PLAYER_SUBTITLE_SIZE_INDEX = "player_subtitle_size_index";
    public static final String KEY_PLAYER_SUBTITLE_COLOR_INDEX = "player_subtitle_color_index";
    public static final String KEY_SUBTITLE_LANGUAGE_SUB_INDEX = "player_subtitle_language_sub_index";
    public static final String KEY_PLAYER_SUBTITLE_SUB_BACKGROUND = "player_is_subtitle_sub_background";
    public static final String KEY_PLAYER_SUBTITLE_SUB_SIZE_INDEX = "player_subtitle_sub_size_index";
    public static final String KEY_PLAYER_SUBTITLE_SUB_COLOR_INDEX = "player_subtitle_sub_color_index";
    public static final String KEY_PLAYER_COACH_MARK_CHECK = "player_coach_mark_check";
    public static final String KEY_NOTIFY_NO_WIFI = "notify_no_wifi";
    public static final String KEY_SEEK_TYPE = "seek_type";
    public static final String KEY_PLAYER_BACKGROUND_PLAY = "player_background_play";
    public static final String KEY_DOWNLOAD_UPDATE_TOTAL_TIME = "k_d_u_t_time";

    //default value
    public static final String BANDWIDTH_DEFAULT_AUTO = "Auto";
    public static final int PLAYER_BANDWIDTH_DEFAULT_INDEX    = 0;
    public static final int PLAYER_AUDIO_SINK_DEFAULT_VALUE   = 0;
    public static final int PLAYER_SUBTITLE_LANGUAGE_DEFAULT_VALUE   = 0;
    public static final int PLAYER_REPLAY_DEFAULT_SEEK_TIME   = 0;
    public static final int PLAYER_PLAY_LIST_INDEX_DEFAULT   = 0;
    public static final boolean PLAYER_BACKGROUND_PLAY_DEFAULT   = false;
    public static final boolean NOTIFY_NO_WIFI_CHECK   = true;
    public static final int DOWNLOADING_TIMER_PERIOD   = 500; //millisecond
    public static final int PLAY_CONTENT_LIST_INDEX_DEFAULT   = 0;

    public static final int DOWNLOAD_SLIDE_BAR_HEIGHT   = 40;
    public static final int DOWNLOAD_SLIDE_BAR_HEIGHT_TABLET   = 48;

    //Caption layout margin
    public static final int CAPTION_LAYOUT_MARGIN_DEFAULT = 0;
    public static final int CAPTION_LAYOUT_MARGIN_BOTTOM  = 5;
    public static final int CAPTION_LAYOUT_MARGIN_MOBILE = 80;
    public static final int CAPTION_LAYOUT_MARGIN_TABLET = 90;

    //setting screen rendering type
    public static final int SCREEN_RENDERING_TYPE_MODEL       = 0;
    public static final int SCREEN_RENDERING_TYPE_DOUBLE_UP   = 1;
    public static final int SCREEN_RENDERING_TYPE_X2          = 2;
    public static final int SCREEN_RENDERING_TYPE_X4          = 3;
    public static final int SCREEN_RENDERING_TYPE_X16         = 4;
    public static final int SCREEN_RENDERING_TYPE_X32         = 5;


    //플레이어 재생 리스트 타입 정의
    public static final int PLAYER_LIST_FILE_BROWSER     = 0;
    public static final int PLAYER_LIST_STREAMING        = 1;
    public static final int PLAYER_LIST_DOWNLOAD_PLAY    = 2;
    public static final int PLAYER_LIST_FILE_RECENTLY    = 3;
    public static final int PLAYER_LIST_STREAMING_SERIES = 4;

    //플레이어 설정화면 인덱스 정의
    public static final int PLAYER_SKIP_INDEX_5_SEC     = 0;
    public static final int PLAYER_SKIP_INDEX_10_SEC    = 1;
    public static final int PLAYER_SKIP_INDEX_20_SEC    = 2;
    public static final int PLAYER_SKIP_INDEX_30_SEC    = 3;
    public static final int PLAYER_SKIP_INDEX_60_SEC    = 4;
    public static final int PLAYER_SKIP_INDEX_300_SEC   = 5;

    public static final int PLAYER_SKIP_TIME_5_SEC     =   5;
    public static final int PLAYER_SKIP_TIME_10_SEC    =  10;
    public static final int PLAYER_SKIP_TIME_20_SEC    =  20;
    public static final int PLAYER_SKIP_TIME_30_SEC    =  30;
    public static final int PLAYER_SKIP_TIME_60_SEC    =  60;
    public static final int PLAYER_SKIP_TIME_300_SEC   = 300;

    //subtitle progress setting define value. dp
    public static final int SUBTITLE_SEEK_BAR_MIN                        =   0;
    public static final int SUBTITLE_SEEK_BAR_MAX                        =   4;
    public static final int SUBTITLE_SEEK_BAR_DEFAULT_PROGRESS           =   0;
    public static final int SUBTITLE_SEEK_BAR_SECTION_COUNT              =   4;
    public static final int SUBTITLE_SEEK_BAR_SECTION_IMAGE_WIDTH        =   2;
    public static final int SUBTITLE_SEEK_BAR_SECTION_IMAGE_HEIGHT       =   10;
    public static final int SUBTITLE_SEEK_BAR_THUMB_RADIUS               =   10;
    public static final int SUBTITLE_SEEK_BAR_THUMB_RADIUS_ON_DRAGGING   =   12;
    public static final int SUBTITLE_SEEK_BAR_TRACK_HEIGHT               =    2;

    //subtitle sub progress setting define value. dp
    public static final int SUBTITLE_SEEK_BAR_SUB_MIN                        =   0;
    public static final int SUBTITLE_SEEK_BAR_SUB_MAX                        =   2;
    public static final int SUBTITLE_SEEK_BAR_SUB_DEFAULT_PROGRESS           =   0;
    public static final int SUBTITLE_SEEK_BAR_SUB_SECTION_COUNT              =   2;
    public static final int SUBTITLE_SEEK_BAR_SUB_SECTION_IMAGE_WIDTH        =   2;
    public static final int SUBTITLE_SEEK_BAR_SUB_SECTION_IMAGE_HEIGHT       =   10;
    public static final int SUBTITLE_SEEK_BAR_SUB_THUMB_RADIUS               =   10;
    public static final int SUBTITLE_SEEK_BAR_SUB_THUMB_RADIUS_ON_DRAGGING   =   12;
    public static final int SUBTITLE_SEEK_BAR_SUB_TRACK_HEIGHT               =    2;

    //player options menu screen mode define
    public static final int PLAYER_SCREEN_RATIO_FIX_SCREEN    = 0;
    public static final int PLAYER_SCREEN_RATIO_FULL_SCREEN   = 1;
    public static final int PLAYER_SCREEN_RATIO_CENTER_CROP   = 2;

    //player options menu play speed define
//    public static final int PLAYER_PLAY_SPEED_0_5_X    = 0;
//    public static final int PLAYER_PLAY_SPEED_0_75_X   = 1;
//    public static final int PLAYER_PLAY_SPEED_1_0_X    = 2;
//    public static final int PLAYER_PLAY_SPEED_1_25_X   = 3;
//    public static final int PLAYER_PLAY_SPEED_1_5_X    = 4;
//    public static final int PLAYER_PLAY_SPEED_1_75_X   = 5;
//    public static final int PLAYER_PLAY_SPEED_2_0_X    = 6;
//    public static final int PLAYER_PLAY_SPEED_AUDIO_1_0_X    = 0;
//    public static final int PLAYER_PLAY_SPEED_AUDIO_1_25_X   = 1;
//    public static final int PLAYER_PLAY_SPEED_AUDIO_1_5_X    = 2;
//    public static final float PLAYER_PLAYING_RATE_0_5_F    = 0.5f;
//    public static final float PLAYER_PLAYING_RATE_0_75_F   = 0.75f;
//    public static final float PLAYER_PLAYING_RATE_1_0_F    = 1.0f;
//    public static final float PLAYER_PLAYING_RATE_1_25_F   = 1.25f;
//    public static final float PLAYER_PLAYING_RATE_1_5_F    = 1.5f;
//    public static final float PLAYER_PLAYING_RATE_1_75_F   = 1.75f;
//    public static final float PLAYER_PLAYING_RATE_2_0_F    = 2.0f;

    public static final int PLAYER_PLAY_SPEED_0_5_X    =  0;
    public static final int PLAYER_PLAY_SPEED_0_6_X    =  1;
    public static final int PLAYER_PLAY_SPEED_0_7_X    =  2;
    public static final int PLAYER_PLAY_SPEED_0_8_X    =  3;
    public static final int PLAYER_PLAY_SPEED_0_9_X    =  4;
    public static final int PLAYER_PLAY_SPEED_1_0_X    =  5;
    public static final int PLAYER_PLAY_SPEED_1_1_X    =  6;
    public static final int PLAYER_PLAY_SPEED_1_2_X    =  7;
    public static final int PLAYER_PLAY_SPEED_1_3_X    =  8;
    public static final int PLAYER_PLAY_SPEED_1_4_X    =  9;
    public static final int PLAYER_PLAY_SPEED_1_5_X    = 10;
    public static final int PLAYER_PLAY_SPEED_1_6_X    = 11;
    public static final int PLAYER_PLAY_SPEED_1_7_X    = 12;
    public static final int PLAYER_PLAY_SPEED_1_8_X    = 13;
    public static final int PLAYER_PLAY_SPEED_1_9_X    = 14;
    public static final int PLAYER_PLAY_SPEED_2_0_X    = 15;

    public static final int PLAYER_PLAY_SPEED_AUDIO_1_0_X    = 0;
    public static final int PLAYER_PLAY_SPEED_AUDIO_1_1_X    = 1;
    public static final int PLAYER_PLAY_SPEED_AUDIO_1_2_X    = 2;
    public static final int PLAYER_PLAY_SPEED_AUDIO_1_3_X    = 3;
    public static final int PLAYER_PLAY_SPEED_AUDIO_1_4_X    = 4;
    public static final int PLAYER_PLAY_SPEED_AUDIO_1_5_X    = 5;

    public static final float PLAY_SPEED_ENABLE_ALPHA      = 1.0f;
    public static final float PLAY_SPEED_DISABLE_ALPHA     = 0.7f;

    public static final float PLAYER_PLAYING_RATE_0_5_F    = 0.5f;
    public static final float PLAYER_PLAYING_RATE_0_6_F    = 0.6f;
    public static final float PLAYER_PLAYING_RATE_0_7_F    = 0.7f;
    public static final float PLAYER_PLAYING_RATE_0_8_F    = 0.8f;
    public static final float PLAYER_PLAYING_RATE_0_9_F    = 0.9f;
    public static final float PLAYER_PLAYING_RATE_1_0_F    = 1.0f;
    public static final float PLAYER_PLAYING_RATE_1_1_F    = 1.1f;
    public static final float PLAYER_PLAYING_RATE_1_2_F    = 1.2f;
    public static final float PLAYER_PLAYING_RATE_1_3_F    = 1.3f;
    public static final float PLAYER_PLAYING_RATE_1_4_F    = 1.4f;
    public static final float PLAYER_PLAYING_RATE_1_5_F    = 1.5f;
    public static final float PLAYER_PLAYING_RATE_1_6_F    = 1.6f;
    public static final float PLAYER_PLAYING_RATE_1_7_F    = 1.7f;
    public static final float PLAYER_PLAYING_RATE_1_8_F    = 1.8f;
    public static final float PLAYER_PLAYING_RATE_1_9_F    = 1.9f;
    public static final float PLAYER_PLAYING_RATE_2_0_F    = 2.0f;

    //player speed define value. dp
    public static final int PLAY_SPEED_BAR_MIN                        =   0;
    public static final int PLAY_SPEED_BAR_MAX                        =   15;
    public static final int PLAY_SPEED_BAR_DEFAULT_PROGRESS           =   0;
    public static final int PLAY_SPEED_BAR_SECTION_COUNT              =   15;
    public static final int PLAY_SPEED_BAR_SECTION_IMAGE_WIDTH        =   0;//4;
    public static final int PLAY_SPEED_BAR_SECTION_IMAGE_HEIGHT       =   0;//4;
    public static final int SPLAY_SPEED_BAR_THUMB_RADIUS              =   10;
    public static final int PLAY_SPEED_BAR_THUMB_RADIUS_ON_DRAGGING   =   12;
    public static final int PLAY_SPEED_BAR_TRACK_HEIGHT               =    4;
    public static final int PLAY_SPEED_TEXT_MOBILE_WIDTH_DP           =   30;
    public static final int PLAY_SPEED_TEXT_TABLET_WIDTH_DP           =   60;
    public static final int PLAY_SPEED_AUDIO_BAR_MIN                  =    0;
    public static final int PLAY_SPEED_AUDIO_BAR_MAX                  =    5;
    public static final int PLAY_SPEED_AUDIO_BAR_DEFAULT_PROGRESS     =    0;
    public static final int PLAY_SPEED_AUDIO_BAR_SECTION_COUNT        =    5;
    public static final int PLAY_SPEED_HORIZONTAL_LAYOUT_MARGIN                  =   100;
    public static final int PLAY_SPEED_TABLET_HORIZONTAL_LAYOUT_MARGIN           =   290;
    public static final int PLAY_SPEED_HORIZONTAL_CURRENT_PLAY_RATE_LAYOUT_HALF  =    30;
    public static final int PLAY_SPEED_VERTICAL_LAYOUT_MARGIN                     =   16;
    public static final int PLAY_SPEED_VERTICAL_CURRENT_PLAY_RATE_LAYOUT_HALF     =   15;
    public static final int PLAY_SPEED_TABLET_VERTICAL_LAYOUT_MARGIN              =   50;
    public static final int PLAY_SPEED_TABLET_CURRENT_PLAY_RATE_LAYOUT_HALF       =   40;

    //player options menu subtitle size level define
    public static final int PLAYER_SUBTITLE_SIZE_1_LEVEL     = 0;
    public static final int PLAYER_SUBTITLE_SIZE_2_LEVEL     = 1;
    public static final int PLAYER_SUBTITLE_SIZE_3_LEVEL     = 2;
    public static final int PLAYER_SUBTITLE_SIZE_4_LEVEL     = 3;
    public static final int PLAYER_SUBTITLE_SIZE_5_LEVEL     = 4;

    //player options menu subtitle font size define. dp
    public static final int PLAYER_SUBTITLE_FONT_SIZE_12_DP    = 12;
    public static final int PLAYER_SUBTITLE_FONT_SIZE_14_DP    = 14;
    public static final int PLAYER_SUBTITLE_FONT_SIZE_18_DP    = 18;
    public static final int PLAYER_SUBTITLE_FONT_SIZE_20_DP    = 20;
    public static final int PLAYER_SUBTITLE_FONT_SIZE_24_DP    = 24;

    //player options menu subtitle second sub size level define
    public static final int PLAYER_SUBTITLE_SUB_SIZE_1_LEVEL     = 0;
    public static final int PLAYER_SUBTITLE_SUB_SIZE_2_LEVEL     = 1;
    public static final int PLAYER_SUBTITLE_SUB_SIZE_3_LEVEL     = 2;

    //player options menu subtitle second sub font size define. dp
    public static final int PLAYER_SUBTITLE_SUB_FONT_SIZE_12_DP    = 12;
    public static final int PLAYER_SUBTITLE_SUB_FONT_SIZE_15_DP    = 15;
    public static final int PLAYER_SUBTITLE_SUB_FONT_SIZE_18_DP    = 18;

    //player options menu subtitle color index define.
    public static final int PLAYER_SUBTITLE_COLOR_WHITE_0_INDEX         = 0;
    public static final int PLAYER_SUBTITLE_COLOR_BLACK_1_INDEX         = 1;
    public static final int PLAYER_SUBTITLE_COLOR_GRAY_2_INDEX          = 2;
    public static final int PLAYER_SUBTITLE_COLOR_DARK_GRAY_3_INDEX     = 3;
    public static final int PLAYER_SUBTITLE_COLOR_RED_4_INDEX           = 4;
    public static final int PLAYER_SUBTITLE_COLOR_PINK_5_INDEX          = 5;
    public static final int PLAYER_SUBTITLE_COLOR_ORANGE_6_INDEX        = 6;
    public static final int PLAYER_SUBTITLE_COLOR_YELLOW_7_INDEX        = 7;
    public static final int PLAYER_SUBTITLE_COLOR_GREEN_8_INDEX         = 8;
    public static final int PLAYER_SUBTITLE_COLOR_BLUE_SKY_9_INDEX      = 9;

    public static final int CHECK_FAIL = -1;
    public static final int NO_DATA = -1;
    public static final String NO_FIELD_DATA = "";

    public static final int SEC_1_MILLISECOND = 1000;

    public static final int DISK_TYPE_LOCAL           = 0;
    public static final int DISK_TYPE_EXTERNAL        = 1;
    public static final int DISK_TYPE_EXTERNAL_ONE    = 2;
    public static final int DISK_TYPE_EXTERNAL_TWO    = 3;
    public static final int DISK_TYPE_EXTERNAL_THREE  = 4;
    public static final int DISK_TYPE_EXTERNAL_FOUR   = 5;
    public static final int DISK_TYPE_EXTERNAL_FIVE   = 6;
    public static final int DISK_TYPE_EXTERNAL_SIX    = 7;

    public static final int SEEK_SEARCH_TYPE_FAST = 0;
    public static final int SEEK_SEARCH_TYPE_EXACTLY = 1;

    //sound volume mute true. silent mode
    public static final int VOLUME_LEVEL_SILENT = 0;
    public static final int VOLUME_LEVEL_MIN_MUTE = 1;

    //thumbnail min width. dip
    public static final int THUMBNAIL_VERTICAL_MIN_WIDTH           =   84;
    public static final int THUMBNAIL_HORIZONTAL_MIN_WIDTH         =   96;

    //Caption bottom margin. dip
    public static final int CAPTION_BOTTOM_MARGIN_UNLOCK         =   12;
    public static final int CAPTION_BOTTOM_MARGIN_LOCKED         =   60;

    public static final int CAPTION_BOTTOM_MARGIN_TABLET_UNLOCK         =   16;
    public static final int CAPTION_BOTTOM_MARGIN_TABLET_LOCKED         =   80;

    //device type inch
    public static final int DEVICE_TYPE_MOBILE = 0;
    public static final int DEVICE_TYPE_7_INCH_TABLET = 6;
    public static final int DEVICE_TYPE_10_INCH_TABLET = 10;
    public static final int DEVICE_TYPE_LARGE_RESOLUTION_DEVICE = 11;

    //Storage Index
    public static final int LOCAL_STORAGE_INDEX = 0;
    public static final int EXTERNAL_STORAGE_INDEX = 1;

    //더보기 상세메뉴 인덱스 정리
    public static final int DIRECTORY_MENU_RENAME = 0;
    public static final int DIRECTORY_MENU_DELETE = 1;

    public static final int FILE_MENU_DELETE = 0;
    public static final int FILE_MENU_DETAIL = 1;

    //메인메뉴 인덱스 정리
    public static final int MAIN_MENU_DELETE = 0;
    public static final int MAIN_MENU_MOVE = 1;
    public static final int MAIN_MENU_ADD_FOLDER = 2;

    //최근 추가된 파일 아이템 인덱스 정리
    public static final int RECENTLY_ADD_MOVE_SAVE_FOLDER = 0;
    public static final int RECENTLY_ADD_DELETE = 1;
    public static final int RECENTLY_ADD_DETAIL = 2;

    //파일 이동 메뉴
    public static final int EDIT_MENU_MOVE_CHECK   = 0;
    public static final int EDIT_MENU_MOVE_CONFIRM = 1;
    public static final int EDIT_MENU_MOVE_CANCEL  = 2;

    //다운로드 프로그래스 상태 정의
    public static final int DOWNLOAD_PROGRESS_STATUS_STOP  = 0;
    public static final int DOWNLOAD_PROGRESS_STATUS_START = 1;
    public static final int DOWNLOAD_PROGRESS_STATUS_READY = 2;

    //파일브라우저 정렬
    public static final int SORT_NAME = 0;
    public static final int SORT_DATE = 1;
    public static final int SORT_SIZE = 2;

    //player bright setting value
    public static final int BRIGHTNESS_MIN = 15;
    public static final int BRIGHTNESS_MAX = 255;
    public static final int BRIGHTNESS_UNIT = 24;

    //bookmark mode. status.
    public static final int BOOKMARK_MODE_DEFAULT = 0;
    public static final int BOOKMARK_MODE_ADD     = 1;
    public static final int BOOKMARK_MODE_DELETE  = 2;
    public static final int BOOKMARK_MODE_EDIT    = 3;

    //다운로드 서비스 처리를 위한 브로드 캐스트 메시지
    public static final String BROADCAST_DOWNLOAD_COMPLETE = "br_d_complete";
    public static final String BROADCAST_DOWNLOAD_DELETE_ITEM = "br_d_remove_item";
    public static final String BROADCAST_PLAYBACK_UPDATE = "br_playback_update";
    public static final String BROADCAST_SETTING_REMOVE_DOWNLOAD_CONTENT = "br_s_remove_d_content";

    //KEY 설정값 관리
    public static final String KEY_FILE_BROWSER_SORT = "k_b_sort";

    //KEY 근처기기 UI 체크
    public static final String KEY_NEARBY_DEVICE_CHECK = "k_n_d_c";

    // lottie animation 예외처리
    public static final int SM_T_860_TABLET_VERTICAL_RELATIVE_LAYOUT_WIDTH = 200;//dp

    public static final int ITQ701_TABLET_RELATIVE_LAYOUT_WIDTH = 150;//dp
    public static final int ITQ701_TABLET_RELATIVE_LAYOUT_MARGIN = 70;//dp

    public static final int SHW_M250L_SHV_E270S_RELATIVE_LAYOUT_WIDTH = 100;//dp
    public static final int SHW_M250L_SHV_E270S_RELATIVE_LAYOUT_MARGIN = 24;//dp

    //프로그래스 다이얼로그 타이틀 갱신
//    public static final int HANDLE_PROGRESS_TITLE_SET = 1210;

    //device type key value.
    public static final String KEY_DEVICE_TYPE = "k_device_type";

    //디바이스 해상도를 구분하고 가로 세로 로드 되는 ui를 확인하기 위해 정의
    public static final String DEVICE_UI_MOBILE_PORT   = "mobile_port";
    public static final String DEVICE_UI_MOBILE_LAND   = "mobile_land";
    public static final String DEVICE_UI_6_TABLET_PORT = "6inch_port";
    public static final String DEVICE_UI_6_TABLET_LAND = "6inch_land";
    public static final String DEVICE_UI_7_TABLET_PORT = "7inch_port";
    public static final String DEVICE_UI_7_TABLET_LAND = "7inch_land";

    public static final int DEVICE_TYPE_MOBILE_PORT          =    0;
    public static final int DEVICE_TYPE_MOBILE_LAND          =    1;
    public static final int DEVICE_TYPE_TABLET_6_PORT        =    2;
    public static final int DEVICE_TYPE_TABLET_6_LAND        =    3;
    public static final int DEVICE_TYPE_TABLET_7_PORT        =    4;
    public static final int DEVICE_TYPE_TABLET_7_LAND        =    5;

    //caption layout height & text size define . value is dp
    public static final int CAPTION_MOBILE_PORT_MAIN_LAYOUT_HEIGHT           =   320;//96;
    public static final int CAPTION_MOBILE_PORT_SUB_LAYOUT_1_LEVEL_HEIGHT    =   54;
    public static final int CAPTION_MOBILE_PORT_SUB_LAYOUT_2_LEVEL_HEIGHT    =   64;
    public static final int CAPTION_MOBILE_PORT_SUB_LAYOUT_3_LEVEL_HEIGHT    =   78;
    public static final int CAPTION_MOBILE_LAND_MAIN_LAYOUT_HEIGHT           =   192;//78;
    public static final int CAPTION_MOBILE_LAND_SUB_LAYOUT_1_LEVEL_HEIGHT    =   36;
    public static final int CAPTION_MOBILE_LAND_SUB_LAYOUT_2_LEVEL_HEIGHT    =   42;
    public static final int CAPTION_MOBILE_LAND_SUB_LAYOUT_3_LEVEL_HEIGHT    =   52;
    public static final int CAPTION_TABLET_6_PORT_MAIN_LAYOUT_HEIGHT         =   320;//78;
    public static final int CAPTION_TABLET_6_LAND_MAIN_LAYOUT_HEIGHT         =   192;//64;
    public static final int CAPTION_TABLET_6_PORT_SUB_LAYOUT_1_LEVEL_HEIGHT  =   36;
    public static final int CAPTION_TABLET_6_PORT_SUB_LAYOUT_2_LEVEL_HEIGHT  =   42;
    public static final int CAPTION_TABLET_6_PORT_SUB_LAYOUT_3_LEVEL_HEIGHT  =   52;
    public static final int CAPTION_TABLET_6_LAND_SUB_LAYOUT_1_LEVEL_HEIGHT  =   36;
    public static final int CAPTION_TABLET_6_LAND_SUB_LAYOUT_2_LEVEL_HEIGHT  =   42;
    public static final int CAPTION_TABLET_6_LAND_SUB_LAYOUT_3_LEVEL_HEIGHT  =   52;
    public static final int CAPTION_TABLET_7_PORT_MAIN_LAYOUT_HEIGHT         =   320;//64;
    public static final int CAPTION_TABLET_7_LAND_MAIN_LAYOUT_HEIGHT         =   192;//64;
    public static final int CAPTION_TABLET_7_PORT_SUB_LAYOUT_1_LEVEL_HEIGHT  =   36;
    public static final int CAPTION_TABLET_7_PORT_SUB_LAYOUT_2_LEVEL_HEIGHT  =   42;
    public static final int CAPTION_TABLET_7_PORT_SUB_LAYOUT_3_LEVEL_HEIGHT  =   52;
    public static final int CAPTION_TABLET_7_LAND_SUB_LAYOUT_1_LEVEL_HEIGHT  =   36;
    public static final int CAPTION_TABLET_7_LAND_SUB_LAYOUT_2_LEVEL_HEIGHT  =   42;
    public static final int CAPTION_TABLET_7_LAND_SUB_LAYOUT_3_LEVEL_HEIGHT  =   52;

    //caption sub layout margin. value is dp.
    public static final int CAPTION_MOBILE_BOTTOM_MARGIN             =   16;
    public static final int CAPTION_TABLET_6_BOTTOM_MARGIN           =   24;
    public static final int CAPTION_TABLET_7_BOTTOM_MARGIN           =   24;

    //caption max line.
    public static final int CAPTION_MAIN_VIEW_PORT_MAX_LINE         =  10;
    public static final int CAPTION_MAIN_VIEW_LAND_MAX_LINE         =   6;
    public static final int CAPTION_SUB_VIEW_PORT_MAX_LINE          =   3;
    public static final int CAPTION_SUB_VIEW_LAND_MAX_LINE          =   2;
    public static final int CAPTION_TABLET_MAIN_VIEW_MAX_LINE       =  10;
    public static final int CAPTION_TABLET_SUB_VIEW_MAX_LINE        =   2;

    //caption line height. value is dp.
    public static final int CAPTION_MAIN_VIEW_PORT_LINE_HEIGHT         =   26;
    public static final int CAPTION_MAIN_VIEW_LAND_LINE_HEIGHT         =   26;
    public static final int CAPTION_MAIN_VIEW_TABLET_LINE_HEIGHT       =   32;

    public static final int CAPTION_SUB_VIEW_PORT_LINE_HEIGHT         =   26;
    public static final int CAPTION_SUB_VIEW_LAND_LINE_HEIGHT         =   18;

    public static final int CAPTION_SUB_VIEW_PORT_LEVEL_1_LINE_HEIGHT =   18;
    public static final int CAPTION_SUB_VIEW_PORT_LEVEL_2_LINE_HEIGHT =   21;
    public static final int CAPTION_SUB_VIEW_PORT_LEVEL_3_LINE_HEIGHT =   26;

    public static final int CAPTION_SUB_VIEW_LAND_LEVEL_1_LINE_HEIGHT =   18;
    public static final int CAPTION_SUB_VIEW_LAND_LEVEL_2_LINE_HEIGHT =   21;
    public static final int CAPTION_SUB_VIEW_LAND_LEVEL_3_LINE_HEIGHT =   26;

    public static final int CAPTION_SUB_VIEW_PORT_LEVEL_3_PIXEL_DEVICE_MARGIN =   5;
    public static final String CAPTION_SUB_LAYOUT_ISSUE_PIXEL_2 = "Pixel 2";

}
