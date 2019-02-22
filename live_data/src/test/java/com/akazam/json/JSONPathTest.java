package com.akazam.json;

import com.akazam.json.utils.JSONUtils;
import com.jayway.jsonpath.Filter;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.jayway.jsonpath.Criteria.where;

/**
 * @ClassName JSONPathTest
 * @Description TODO
 * @Author Alex
 * @Date 2019/2/19 9:32
 * @Version 1.0
 **/
public class JSONPathTest {

    public static final String jsonData = "{\n" +
            "\t\"deviceInfo\": {\n" +
            "\t\t\"appVersion\": \"3.5.2\",\n" +
            "\t\t\"phoneBrand\": \"OPPO\",\n" +
            "\t\t\"apppkg\": \"com.cmcc.migutvtwo\",\n" +
            "\t\t\"appchannel\": \"0117_43000504-00002-201800000010058\",\n" +
            "\t\t\"sdkversion\": \"3.1.3\",\n" +
            "\t\t\"phoneMode\": \"OPPO R11 Plusk\",\n" +
            "\t\t\"osversion\": \"25\",\n" +
            "\t\t\"os\": \"AD\",\n" +
            "\t\t\"udid\": \"6bc76d5369ca101254948eeed6a3783b\",\n" +
            "\t\t\"installationID\": \"ffffffff-de72-acad-bded-1580000000001524454275330\",\n" +
            "\t\t\"imei\": \"A0000070E4822A\",\n" +
            "\t\t\"userId\": \"\",\n" +
            "\t\t\"uploadTs\": \"1539310100758\"\n" +
            "\t},\n" +
            "\t\"customInfo\": [{\n" +
            "\t\t\"result\": \"0\",\n" +
            "\t\t\"clientID\": \"6b30d76462fa905d58574e085c853fab\",\n" +
            "\t\t\"type\": \"4\",\n" +
            "\t\t\"networkType\": \"WIFI\",\n" +
            "\t\t\"account\": \"17835335258\",\n" +
            "\t\t\"channelID\": \"25000403-99000-200300330000001\",\n" +
            "\t\t\"timestamp\": \"2018-10-12 10:08:30:655\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"playerManif\": \"咪咕自研\",\n" +
            "\t\t\"ContentID\": \"220105782120181012014\",\n" +
            "\t\t\"networkType\": \"4G\",\n" +
            "\t\t\"playSessionID\": \"3C4CD7C2016CC6A1EC915E83B4F3B3A71539310082173\",\n" +
            "\t\t\"playerVersion\": \"v8.4.0.25\",\n" +
            "\t\t\"programeUrl\": \"未获取到播放地址\",\n" +
            "\t\t\"account\": \"3C4CD7C2016CC6A1EC915E83B4F3B3A7\",\n" +
            "\t\t\"timestamp\": \"2018-10-12 10:08:13:521\",\n" +
            "\t\t\"programeType\": \"2\",\n" +
            "\t\t\"channelID\": \"null\",\n" +
            "\t\t\"result\": \"1\",\n" +
            "\t\t\"type\": \"9\",\n" +
            "\t\t\"rateType\": \"0\",\n" +
            "\t\t\"clientID\": \"3C4CD7C2016CC6A1EC915E83B4F3B3A7\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"networkType\": \"4G\",\n" +
            "\t\t\"loadTime\": \"1474\",\n" +
            "\t\t\"account\": \"3C4CD7C2016CC6A1EC915E83B4F3B3A7\",\n" +
            "\t\t\"timestamp\": \"2018-10-12 10:07:49:034\",\n" +
            "\t\t\"totalLoadTime\": \"5216\",\n" +
            "\t\t\"channelID\": \"null\",\n" +
            "\t\t\"type\": \"10\",\n" +
            "\t\t\"result\": \"0\",\n" +
            "\t\t\"clientID\": \"3C4CD7C2016CC6A1EC915E83B4F3B3A7\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"ContentID\": \"220105782120181012014\",\n" +
            "\t\t\"networkType\": \"4G\",\n" +
            "\t\t\"loadTime\": \"43\",\n" +
            "\t\t\"timestamp\": \"2018-10-12 10:08:01:297\",\n" +
            "\t\t\"account\": \"3C4CD7C2016CC6A1EC915E83B4F3B3A7\",\n" +
            "\t\t\"channelID\": \"null\",\n" +
            "\t\t\"result\": \"0\",\n" +
            "\t\t\"type\": \"11\",\n" +
            "\t\t\"clientID\": \"3C4CD7C2016CC6A1EC915E83B4F3B3A7\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"ContentID\": \"647376691\",\n" +
            "\t\t\"downloadTime\": \"37215\",\n" +
            "\t\t\"networkType\": \"WIFI\",\n" +
            "\t\t\"size\": \"222639343\",\n" +
            "\t\t\"account\": \"18438168356\",\n" +
            "\t\t\"timestamp\": \"2018-10-12 10:07:51:708\",\n" +
            "\t\t\"channelID\": \"25000403-99000-200300330000001\",\n" +
            "\t\t\"type\": \"14\",\n" +
            "\t\t\"clientID\": \"3a39ae31ad2448199a4b9f1a5ac10a14\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"networkType\": \"4G\",\n" +
            "\t\t\"nodeID\": \"ContextThemeWrapper\",\n" +
            "\t\t\"picID\": \"1012351539310110472\",\n" +
            "\t\t\"size\": \"11664\",\n" +
            "\t\t\"programeUrl\": \"http://img.cmvideo.cn:8080/publish/noms/v1/ic_tv_xhdpi.png\",\n" +
            "\t\t\"loadTime\": \"3445\",\n" +
            "\t\t\"timestamp\": \"2018-10-12 10:08:30\",\n" +
            "\t\t\"account\": \"1126042818\",\n" +
            "\t\t\"picIP\": \"112.25.62.155\",\n" +
            "\t\t\"result\": \"0\",\n" +
            "\t\t\"type\": \"19\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"result\": \"0\",\n" +
            "\t\t\"number\": \"22\",\n" +
            "\t\t\"loadTime\": \"10542.0\",\n" +
            "\t\t\"picIP\": \"119.36.142.20\",\n" +
            "\t\t\"type\": \"19\",\n" +
            "\t\t\"networkType\": \"WIFI\",\n" +
            "\t\t\"timestamp\": \"2018-10-12 10:08:19\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"ContentID\": \"636434607\",\n" +
            "\t\t\"networkType\": \"4G\",\n" +
            "\t\t\"playSessionID\": \"3A84760319447371E09C4677A389400E1539310105146V\",\n" +
            "\t\t\"loadTime\": \"623\",\n" +
            "\t\t\"videoType\": \"TV_PLAY\",\n" +
            "\t\t\"timestamp\": \"2018-10-12 10:08:25:754\",\n" +
            "\t\t\"account\": \"18765268883\",\n" +
            "\t\t\"channelID\": \"25000403-99000-200300280000000\",\n" +
            "\t\t\"result\": \"0\",\n" +
            "\t\t\"type\": \"22\",\n" +
            "\t\t\"clientID\": \"fce89bad4efce91c7b325b232d86e6a6\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"contentId\": \"636434607\",\n" +
            "\t\t\"jid\": \"3A84760319447371E09C4677A389400E1539310105146V\",\n" +
            "\t\t\"MG_MSG_FFRAME_TIME\": \"881\",\n" +
            "\t\t\"userId\": \"108972825\",\n" +
            "\t\t\"MG_MSG_MEDIA_INFO_VIDEO_RESOLUTION\": \"1280 x 720\",\n" +
            "\t\t\"MG_MSG_MEDIA_INFO_VIDEO_FRAMERATE\": \"25.0\",\n" +
            "\t\t\"Status\": \"0\",\n" +
            "\t\t\"MG_MSG_GETURL_TIME\": \"1539310105781\",\n" +
            "\t\t\"SourceID\": \"aHR0cDovL2dzbGJtZ3Nwdm9kLm1pZ3V2aWRlby5jb20vZGVwb3NpdG9yeV9zcC9hc3NldC96aGVu\\nZ3NoaS81MTAxLzA5My81NzcvNTEwMTA5MzU3Ny9tZWRpYS81MTAxMDkzNTc3XzUwMDM1OTg2NzNf\\nNTYubXA0Lm0zdTg/bXNpc2RuPTE4NzY1MjY4ODgzJm1kc3BpZD0mc3BpZD04MDAwMzMmbmV0VHlw\\nZT01JnNpZD01NTAwMzgyMzQ3JnBpZD0yMDI4NjAwNzM4JnRpbWVzdGFtcD0yMDE4MTAxMjEwMDgy\\nNSZDaGFubmVsX0lEPTAxMTZfMjUwMDA0MDMtOTkwMDAtMjAwMzAwMjgwMDAwMDAwJlByb2dyYW1J\\nRD02MzY0MzQ2MDcmUGFyZW50Tm9kZUlEPS05OSZwcmV2aWV3PTEmcGxheXNlZWs9MDAwMDAwLTAw\\nMDYwMCZhc3NlcnRJRD01NTAwMzgyMzQ3JmNsaWVudF9pcD0yMjMuMTA0LjE4Ny45MCZTZWN1cml0\\neUtleT0yMDE4MTAxMjEwMDgyNSZpbWVpPTg2ODA2NDAzNjYwODc3MyZwcm9tb3Rpb25JZD0mbXZp\\nZD01MTAxMDkzNTc3Jm1jaWQ9MTAwMiZtcGlkPTEzMDAwMDA1MDQ2MyZlbmNyeXB0PWFjMGViNWU5\\nMWRlNGE1YjVkY2RlNzdjMDZlODNiMWQ3JmppZD0zQTg0NzYwMzE5NDQ3MzcxRTA5QzQ2NzdBMzg5\\nNDAwRTE1MzkzMTAxMDUxNDZW\\n_1539310105781\",\n" +
            "\t\t\"Session\": \"3A84760319447371E09C4677A389400E1539310105146V\",\n" +
            "\t\t\"MG_MSG_START_TIME\": \"1539310106851\",\n" +
            "\t\t\"MG_MSG_TIME\": \"1539310106851\",\n" +
            "\t\t\"PlayerID\": \"f3d39e4_1539310105780\",\n" +
            "\t\t\"type\": \"56000004\",\n" +
            "\t\t\"networkType\": \"4G\",\n" +
            "\t\t\"MG_MSG_MEDIA_INFO_AUDIO_CODEC\": \"aac\",\n" +
            "\t\t\"MG_TOTAL_LOAD_TIME\": \"1504\",\n" +
            "\t\t\"MG_MSG_PROGRAM_URL\": \"http://gslbmgspvod.miguvideo.com/depository_sp/asset/zhengshi/5101/093/577/5101093577/media/5101093577_5003598673_56.mp4.m3u8?msisdn=18765268883&mdspid=&spid=800033&netType=5&sid=5500382347&pid=2028600738&timestamp=20181012100825&Channel_ID=0116_25000403-99000-200300280000000&ProgramID=636434607&ParentNodeID=-99&preview=1&playseek=000000-000600&assertID=5500382347&client_ip=223.104.187.90&SecurityKey=20181012100825&imei=868064036608773&promotionId=&mvid=5101093577&mcid=1002&mpid=130000050463&encrypt=ac0eb5e91de4a5b5dcde77c06e83b1d7&jid=3A84760319447371E09C4677A389400E1539310105146V\",\n" +
            "\t\t\"MG_MSG_MEDIA_INFO_VIDEO_BITRATE\": \"456\",\n" +
            "\t\t\"MG_MSG_MEDIA_INFO_VIDEO_CODEC\": \"h264\",\n" +
            "\t\t\"contentType\": \"TV_PLAY\",\n" +
            "\t\t\"MG_MSG_MEDIA_INFO_AUDIO_SAMPLERATE\": \"44100\",\n" +
            "\t\t\"MG_MSG_MEDIA_INFO_TYPE\": \"AV\",\n" +
            "\t\t\"PlaybackRate\": \"1.0\",\n" +
            "\t\t\"title\": \"《与贝尔同行之突破极限》05\",\n" +
            "\t\t\"MG_MSG_MEDIA_INFO_AUDIO_CHANNELS\": \"2\",\n" +
            "\t\t\"MG_URL_LOAD_TIME\": \"623\",\n" +
            "\t\t\"account\": \"18765268883\",\n" +
            "\t\t\"LastSession\": \"3A84760319447371E09C4677A389400E1539310090675V\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"PlayerID\": \"1164e2cf0_1539309066358\",\n" +
            "\t\t\"PlaybackRate\": \"1.000000\",\n" +
            "\t\t\"timestamp\": \"2018-10-12 10:07:49\",\n" +
            "\t\t\"Action\": \"2\",\n" +
            "\t\t\"Session\": \"20dedd52979f1f432cb392fc7587a8a5\",\n" +
            "\t\t\"SubsessionServiceURL\": \"http://mgzb.live.miguvideo.com:8088/envivo_x/2018/SD/jiangsuTV/1000/20180928152040-01-115351.ts?msisdn=5f555e7bf42e48b4b6224ce6ad89b133&mdspid=&spid=699067&netType=4&sid=5500199481&pid=2028597139&timestamp=20181012095034&Channel_ID=0116_25040400-99000-200300020100001&ProgramID=623899540&ParentNodeID=-99&playbackbegin=20181011215500&playbackend=20181011235600&assertID=5500199481&client_ip=111.40.179.231&SecurityKey=20181012095034&imei=FEE3C29A-EE5B-4F8B-BF83-5BFEE0E86EFE&promotionId=&mvid=&mcid=&mpid=&mtv_session=7fe1c6b40e6f29cb0bfb39eeec737bbb&jid=20dedd52979f1f432cb392fc7587a8a5&sjid=subsession_1539309066378&encrypt=548f934ef0d1e921a2b2f4c7c14ed93a&HlsSubType=2&HlsProfileId=1\",\n" +
            "\t\t\"Reason\": \"30000\",\n" +
            "\t\t\"type\": \"56000015\",\n" +
            "\t\t\"channelID\": \"25040400-99000-200300020100001\",\n" +
            "\t\t\"MG_MSG_TIME\": \"1539310069643\",\n" +
            "\t\t\"clientID\": \"5f555e7bf42e48b4b6224ce6ad89b133\",\n" +
            "\t\t\"SubsessionServiceIP\": \"10.247.225.118\",\n" +
            "\t\t\"MG_MSG_STUCK_END\": \"1539310069642\",\n" +
            "\t\t\"MG_MSG_STUCK_DURATION\": \"38559.40\",\n" +
            "\t\t\"Subsession\": \"subsession_1539309066378\",\n" +
            "\t\t\"SourceID\": \"aHR0cDovL2dzbGJtZ3NwbGl2ZS5taWd1dmlkZW8uY29tL2Vudml2b194LzIwMTgvU0QvamlhbmdzdVRWLzEwMDAvaW5kZXgubTN1OD9tc2lzZG49NWY1NTVlN2JmNDJlNDhiNGI2MjI0Y2U2YWQ4OWIxMzMmbWRzcGlkPSZzcGlkPTY5OTA2NyZuZXRUeXBlPTQmc2lkPTU1MDAxOTk0ODEmcGlkPTIwMjg1OTcxMzkmdGltZXN0YW1wPTIwMTgxMDEyMDk1MDM0JkNoYW5uZWxfSUQ9MDExNl8yNTA0MDQwMC05OTAwMC0yMDAzMDAwMjAxMDAwMDEmUHJvZ3JhbUlEPTYyMzg5OTU0MCZQYXJlbnROb2RlSUQ9LTk5JnBsYXliYWNrYmVnaW49MjAxODEwMTEyMTU1MDAmcGxheWJhY2tlbmQ9MjAxODEwMTEyMzU2MDAmYXNzZXJ0SUQ9NTUwMDE5OTQ4MSZjbGllbnRfaXA9MTExLjQwLjE3OS4yMzEmU2VjdXJpdHlLZXk9MjAxODEwMTIwOTUwMzQmaW1laT1GRUUzQzI5QS1FRTVCLTRGOEItQkY4My01QkZFRTBFODZFRkUmcHJvbW90aW9uSWQ9Jm12aWQ9Jm1jaWQ9Jm1waWQ9JmVuY3J5cHQ9N2ZlMWM2YjQwZTZmMjljYjBiZmIzOWVlZWM3MzdiYmImamlkPTIwZGVkZDUyOTc5ZjFmNDMyY2IzOTJmYzc1ODdhOGE1_1539309066358\",\n" +
            "\t\t\"MG_MSG_STUCK_START\": \"1539310031082\",\n" +
            "\t\t\"account\": \"\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"BeginTime\": \"1539310091000\",\n" +
            "\t\t\"DataUsage\": \"16274499\",\n" +
            "\t\t\"networkType\": \"4G\",\n" +
            "\t\t\"MG_MSG_PLAYER_VERSION\": \"v8.5.1.25\",\n" +
            "\t\t\"contentId\": \"636434607\",\n" +
            "\t\t\"jid\": \"3A84760319447371E09C4677A389400E1539310090675V\",\n" +
            "\t\t\"Subsession\": \"subsession_1539310091346\",\n" +
            "\t\t\"userId\": \"108972825\",\n" +
            "\t\t\"PlayDuration\": \"13410\",\n" +
            "\t\t\"contentType\": \"TV_PLAY\",\n" +
            "\t\t\"Action\": \"0\",\n" +
            "\t\t\"SubsessionServiceURL\": \"http://hlsmgspvod.miguvideo.com:8080/depository_sp/asset/zhengshi/5101/093/576/5101093576/media/5101093576_5003598633_56.mp4_0-0.ts?msisdn=18765268883&mdspid=&spid=800033&netType=5&sid=5500382346&pid=2028600738&timestamp=20181012100811&Channel_ID=0116_25000403-99000-200300280000000&ProgramID=636433618&ParentNodeID=-99&preview=1&playseek=000000-000600&assertID=5500382346&client_ip=223.104.187.90&SecurityKey=20181012100811&promotionId=&mvid=5101093576&mcid=1002&mpid=130000050463&FreePlay=1&encrypt=e9ddf1c99f134d5582553f1d14c547e6&jid=3A84760319447371E09C4677A389400E1539310090675V&sjid=subsession_1539310091346&mtv_session=6496cc888fe4044f89e74a5cc5b394f5&hls_type=2&HlsSubType=2&HlsProfileId=0\",\n" +
            "\t\t\"PlaybackRate\": \"1.0\",\n" +
            "\t\t\"SubsessionServiceIP\": \"39.134.146.46\",\n" +
            "\t\t\"SourceID\": \"aHR0cDovL2dzbGJtZ3Nwdm9kLm1pZ3V2aWRlby5jb20vZGVwb3NpdG9yeV9zcC9hc3NldC96aGVu\\nZ3NoaS81MTAxLzA5My81NzYvNTEwMTA5MzU3Ni9tZWRpYS81MTAxMDkzNTc2XzUwMDM1OTg2MzNf\\nNTYubXA0Lm0zdTg/bXNpc2RuPTE4NzY1MjY4ODgzJm1kc3BpZD0mc3BpZD04MDAwMzMmbmV0VHlw\\nZT01JnNpZD01NTAwMzgyMzQ2JnBpZD0yMDI4NjAwNzM4JnRpbWVzdGFtcD0yMDE4MTAxMjEwMDgx\\nMSZDaGFubmVsX0lEPTAxMTZfMjUwMDA0MDMtOTkwMDAtMjAwMzAwMjgwMDAwMDAwJlByb2dyYW1J\\nRD02MzY0MzM2MTgmUGFyZW50Tm9kZUlEPS05OSZwcmV2aWV3PTEmcGxheXNlZWs9MDAwMDAwLTAw\\nMDYwMCZhc3NlcnRJRD01NTAwMzgyMzQ2JmNsaWVudF9pcD0yMjMuMTA0LjE4Ny45MCZTZWN1cml0\\neUtleT0yMDE4MTAxMjEwMDgxMSZpbWVpPTg2ODA2NDAzNjYwODc3MyZwcm9tb3Rpb25JZD0mbXZp\\nZD01MTAxMDkzNTc2Jm1jaWQ9MTAwMiZtcGlkPTEzMDAwMDA1MDQ2MyZlbmNyeXB0PTY0OTZjYzg4\\nOGZlNDA0NGY4OWU3NGE1Y2M1YjM5NGY1JmppZD0zQTg0NzYwMzE5NDQ3MzcxRTA5QzQ2NzdBMzg5\\nNDAwRTE1MzkzMTAwOTA2NzVW\\n_1539310092556\",\n" +
            "\t\t\"title\": \"《与贝尔同行之突破极限》05\",\n" +
            "\t\t\"NetType\": \"1\",\n" +
            "\t\t\"Session\": \"3A84760319447371E09C4677A389400E1539310090675V\",\n" +
            "\t\t\"PlayerID\": \"310045_1539310091344\",\n" +
            "\t\t\"MG_MSG_TIME\": \"1539310105767\",\n" +
            "\t\t\"EndTime\": \"1539310105000\",\n" +
            "\t\t\"account\": \"18765268883\",\n" +
            "\t\t\"type\": \"57000000\",\n" +
            "\t\t\"HostIP\": \"117.136.195.132\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"Error_Code\": \"10000102\",\n" +
            "\t\t\"contentId\": \"220105782120181012014\",\n" +
            "\t\t\"userId\": \"3C4CD7C2016CC6A1EC915E83B4F3B3A7\",\n" +
            "\t\t\"MG_MSG_MEDIA_INFO_VIDEO_RESOLUTION\": \"\",\n" +
            "\t\t\"MG_MSG_MEDIA_INFO_VIDEO_FRAMERATE\": \"\",\n" +
            "\t\t\"Failed_Detail_Code\": \"-110\",\n" +
            "\t\t\"Common_Info\": \"null\",\n" +
            "\t\t\"SourceID\": \"aHR0cDovL2dzbGJtZ3pibGl2ZS5taWd1dmlkZW8uY29tL3dkX3IyL2NjdHYvY2N0djFoZC8zNTAv\\naW5kZXgubTN1OD9tc2lzZG49MDVjaTEzMDIzMTRBMDAwMDA3MEU0ODIyQSZzaWQ9MjIwMTA1Nzgy\\nMSZ0aW1lc3RhbXA9MjAxODEwMTIxMDA4MDImQ2hhbm5lbF9JRD0wMTE3XzQzMDAwNTA0LTAwMDAy\\nLTIwMTgwMDAwMDAxMDA1OCZwaWQ9MjAyODU5NzEzOSZzcGlkPTY5OTAxOSZpbWVpPUEwMDAwMDcw\\nRTQ4MjJBJm5ldFR5cGU9NSZQcm9ncmFtSUQ9MjIwMTA1NzgyMTIwMTgxMDEyMDE0JmNsaWVudF9p\\ncD0xMDYuMTI1LjUxLjE3MSZtdmlkPW51bGwmbXBpZD1udWxsJm1jaWQ9bnVsbCZlbmNyeXB0PWMx\\nMDFjNDJlZTcwMGU5NmNlNjk1MTFlOTM3ODNmYzBkJmppZD0zQzRDRDdDMjAxNkNDNkExRUM5MTVF\\nODNCNEYzQjNBNzE1MzkzMTAwODIxNzM=\\n_1539310082189\",\n" +
            "\t\t\"Session\": \"3C4CD7C2016CC6A1EC915E83B4F3B3A71539310082173\",\n" +
            "\t\t\"PlayerID\": \"c598d8_1539310082185\",\n" +
            "\t\t\"MG_MSG_TIME\": \"1539310093486\",\n" +
            "\t\t\"Error_Action\": \"1\",\n" +
            "\t\t\"type\": \"58000000\",\n" +
            "\t\t\"networkType\": \"4G\",\n" +
            "\t\t\"MG_MSG_MEDIA_INFO_AUDIO_CODEC\": \"\",\n" +
            "\t\t\"Subsession\": \"subsession_1539310082190\",\n" +
            "\t\t\"MG_MSG_PROGRAM_URL\": \"http://gslbmgzblive.miguvideo.com/wd_r2/cctv/cctv1hd/350/index.m3u8?msisdn=05ci1302314A0000070E4822A&sid=2201057821&timestamp=20181012100802&Channel_ID=0117_43000504-00002-201800000010058&pid=2028597139&spid=699019&imei=A0000070E4822A&netType=5&ProgramID=220105782120181012014&client_ip=106.125.51.171&mvid=null&mpid=null&mcid=null&encrypt=c101c42ee700e96ce69511e93783fc0d&jid=3C4CD7C2016CC6A1EC915E83B4F3B3A71539310082173\",\n" +
            "\t\t\"MG_MSG_MEDIA_INFO_VIDEO_BITRATE\": \"\",\n" +
            "\t\t\"MG_MSG_MEDIA_INFO_VIDEO_CODEC\": \"\",\n" +
            "\t\t\"contentType\": \"3\",\n" +
            "\t\t\"SubsessionServiceURL\": \"http://mgzb.live.miguvideo.com:8088/wd_r2/cctv/cctv1hd/350/index.m3u8?msisdn=05ci1302314A0000070E4822A&sid=2201057821&timestamp=20181012100802&Channel_ID=0117_43000504-00002-201800000010058&pid=2028597139&spid=699019&imei=A0000070E4822A&netType=5&ProgramID=220105782120181012014&client_ip=106.125.51.171&mvid=null&mpid=null&mcid=null&encrypt=c101c42ee700e96ce69511e93783fc0d&jid=3C4CD7C2016CC6A1EC915E83B4F3B3A71539310082173&sjid=subsession_1539310082190\",\n" +
            "\t\t\"MG_MSG_MEDIA_INFO_AUDIO_SAMPLERATE\": \"0\",\n" +
            "\t\t\"MG_MSG_MEDIA_INFO_TYPE\": \"\",\n" +
            "\t\t\"PlaybackRate\": \"1.0\",\n" +
            "\t\t\"SubsessionServiceIP\": \"36.102.222.16\",\n" +
            "\t\t\"title\": \"大秧歌\",\n" +
            "\t\t\"MG_MSG_MEDIA_INFO_AUDIO_CHANNELS\": \"0\",\n" +
            "\t\t\"account\": \"3C4CD7C2016CC6A1EC915E83B4F3B3A7\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"networkType\": \"4G\",\n" +
            "\t\t\"jid\": \"3A84760319447371E09C4677A389400E1539310090675V\",\n" +
            "\t\t\"contentId\": \"636434607\",\n" +
            "\t\t\"MG_MSG_PLAYER_VERSION\": \"v8.5.1.25\",\n" +
            "\t\t\"Subsession\": \"subsession_1539310091346\",\n" +
            "\t\t\"MG_MSG_PROGRAM_URL\": \"http://gslbmgspvod.miguvideo.com/depository_sp/asset/zhengshi/5101/093/576/5101093576/media/5101093576_5003598633_56.mp4.m3u8?msisdn=18765268883&mdspid=&spid=800033&netType=5&sid=5500382346&pid=2028600738&timestamp=20181012100811&Channel_ID=0116_25000403-99000-200300280000000&ProgramID=636433618&ParentNodeID=-99&preview=1&playseek=000000-000600&assertID=5500382346&client_ip=223.104.187.90&SecurityKey=20181012100811&imei=868064036608773&promotionId=&mvid=5101093576&mcid=1002&mpid=130000050463&encrypt=6496cc888fe4044f89e74a5cc5b394f5&jid=3A84760319447371E09C4677A389400E1539310090675V\",\n" +
            "\t\t\"userId\": \"108972825\",\n" +
            "\t\t\"PlayDuration\": \"13410\",\n" +
            "\t\t\"contentType\": \"TV_PLAY\",\n" +
            "\t\t\"SubsessionServiceURL\": \"http://hlsmgspvod.miguvideo.com:8080/depository_sp/asset/zhengshi/5101/093/576/5101093576/media/5101093576_5003598633_56.mp4_0-0.ts?msisdn=18765268883&mdspid=&spid=800033&netType=5&sid=5500382346&pid=2028600738&timestamp=20181012100811&Channel_ID=0116_25000403-99000-200300280000000&ProgramID=636433618&ParentNodeID=-99&preview=1&playseek=000000-000600&assertID=5500382346&client_ip=223.104.187.90&SecurityKey=20181012100811&promotionId=&mvid=5101093576&mcid=1002&mpid=130000050463&FreePlay=1&encrypt=e9ddf1c99f134d5582553f1d14c547e6&jid=3A84760319447371E09C4677A389400E1539310090675V&sjid=subsession_1539310091346&mtv_session=6496cc888fe4044f89e74a5cc5b394f5&hls_type=2&HlsSubType=2&HlsProfileId=0\",\n" +
            "\t\t\"PlaybackRate\": \"1.0\",\n" +
            "\t\t\"title\": \"《与贝尔同行之突破极限》05\",\n" +
            "\t\t\"SourceID\": \"aHR0cDovL2dzbGJtZ3Nwdm9kLm1pZ3V2aWRlby5jb20vZGVwb3NpdG9yeV9zcC9hc3NldC96aGVu\\nZ3NoaS81MTAxLzA5My81NzYvNTEwMTA5MzU3Ni9tZWRpYS81MTAxMDkzNTc2XzUwMDM1OTg2MzNf\\nNTYubXA0Lm0zdTg/bXNpc2RuPTE4NzY1MjY4ODgzJm1kc3BpZD0mc3BpZD04MDAwMzMmbmV0VHlw\\nZT01JnNpZD01NTAwMzgyMzQ2JnBpZD0yMDI4NjAwNzM4JnRpbWVzdGFtcD0yMDE4MTAxMjEwMDgx\\nMSZDaGFubmVsX0lEPTAxMTZfMjUwMDA0MDMtOTkwMDAtMjAwMzAwMjgwMDAwMDAwJlByb2dyYW1J\\nRD02MzY0MzM2MTgmUGFyZW50Tm9kZUlEPS05OSZwcmV2aWV3PTEmcGxheXNlZWs9MDAwMDAwLTAw\\nMDYwMCZhc3NlcnRJRD01NTAwMzgyMzQ2JmNsaWVudF9pcD0yMjMuMTA0LjE4Ny45MCZTZWN1cml0\\neUtleT0yMDE4MTAxMjEwMDgxMSZpbWVpPTg2ODA2NDAzNjYwODc3MyZwcm9tb3Rpb25JZD0mbXZp\\nZD01MTAxMDkzNTc2Jm1jaWQ9MTAwMiZtcGlkPTEzMDAwMDA1MDQ2MyZlbmNyeXB0PTY0OTZjYzg4\\nOGZlNDA0NGY4OWU3NGE1Y2M1YjM5NGY1JmppZD0zQTg0NzYwMzE5NDQ3MzcxRTA5QzQ2NzdBMzg5\\nNDAwRTE1MzkzMTAwOTA2NzVW\\n_1539310092556\",\n" +
            "\t\t\"Session\": \"3A84760319447371E09C4677A389400E1539310090675V\",\n" +
            "\t\t\"NetType\": \"1\",\n" +
            "\t\t\"PlayerID\": \"310045_1539310091344\",\n" +
            "\t\t\"MG_MSG_TIME\": \"1539310105766\",\n" +
            "\t\t\"account\": \"18765268883\",\n" +
            "\t\t\"MG_EVENT_PERIOD\": \"15\",\n" +
            "\t\t\"type\": \"60000000\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"MG_EVENT_PERIOD\": \"15\",\n" +
            "\t\t\"PlaybackRate\": \"1.0\",\n" +
            "\t\t\"networkType\": \"4G\",\n" +
            "\t\t\"Subsession\": \"subsession_1539309129834\",\n" +
            "\t\t\"contentType\": \"VOD\",\n" +
            "\t\t\"type\": \"60000000\",\n" +
            "\t\t\"contentId\": \"647771975\",\n" +
            "\t\t\"SourceID\": \"aHR0cDovL2dzbGJtZ3Nwdm9kLm1pZ3V2aWRlby5jb20vZGVwb3NpdG9yeV9zanEvYXNzZXQvemhl\\nbmdzaGkvNTEwMi8xMjkvNzc1LzUxMDIxMjk3NzUvbWVkaWEvNTEwMjEyOTc3NV81MDAzNzgyNjcy\\nXzU2Lm1wNC5tM3U4P21zaXNkbj0xNTkxNzc1OTA0OSZtZHNwaWQ9JnNwaWQ9NjAwNDYzJm5ldFR5\\ncGU9NSZzaWQ9NTUwMDYyODY2MiZwaWQ9MjAyODU5NzEzOSZ0aW1lc3RhbXA9MjAxODEwMTIwOTUy\\nMDQmQ2hhbm5lbF9JRD0wMTE2XzI1MDAwNDAzLTk5MDAwLTIwMDMwMDI3MDAwMDAwMSZQcm9ncmFt\\nSUQ9NjQ3NzcxOTc1JlBhcmVudE5vZGVJRD0tOTkmYXNzZXJ0SUQ9NTUwMDYyODY2MiZjbGllbnRf\\naXA9MjIzLjEwNC42My40OSZTZWN1cml0eUtleT0yMDE4MTAxMjA5NTIwNCZpbWVpPTg2NzcxODAy\\nNTI2MTQyMCZwcm9tb3Rpb25JZD0mbXZpZD01MTAyMTI5Nzc1Jm1jaWQ9MTAwMSZtcGlkPTEzMDAw\\nMDEwMTY4OCZlbmNyeXB0PTMxNzJjZDE0NTJkNDAwNGQyNzc4NmVmMTdmYzdhMmY4JmppZD0zM0U1\\nQTU4NzgxNTExRUNCODNFQzgxMDJGQjQ5NUUzRTE1MzkzMDkxMjMzMjVW\\n_1539309132709\",\n" +
            "\t\t\"PlayDuration\": \"15013\",\n" +
            "\t\t\"title\": \"《娘道》第67集\",\n" +
            "\t\t\"Session\": \"33E5A58781511ECB83EC8102FB495E3E1539309123325V\",\n" +
            "\t\t\"userId\": \"1024999762\",\n" +
            "\t\t\"SubsessionServiceURL\": \"http://hlsmgspvod.miguvideo.com:8080/depository_sjq/asset/zhengshi/5102/129/775/5102129775/media/5102129775_5003782672_56.mp4_0-189.ts?msisdn=15917759049&mdspid=&spid=600463&netType=5&sid=5500628662&pid=2028597139&timestamp=20181012095204&Channel_ID=0116_25000403-99000-200300270000001&ProgramID=647771975&ParentNodeID=-99&assertID=5500628662&client_ip=223.104.63.49&SecurityKey=20181012095204&imei=867718025261420&promotionId=&mvid=5102129775&mcid=1001&mpid=130000101688&encrypt=a89d50197d20972435c73485b58b02f5&jid=33E5A58781511ECB83EC8102FB495E3E1539309123325V&sjid=subsession_1539309129834&mtv_session=3172cd1452d4004d27786ef17fc7a2f8&hls_type=2&HlsSubType=2&HlsProfileId=0\",\n" +
            "\t\t\"MG_MSG_PLAYER_VERSION\": \"v8.5.1.25\",\n" +
            "\t\t\"account\": \"15917759049\",\n" +
            "\t\t\"jid\": \"33E5A58781511ECB83EC8102FB495E3E1539309123325V\",\n" +
            "\t\t\"NetType\": \"1\",\n" +
            "\t\t\"MG_MSG_TIME\": \"1539310082568\",\n" +
            "\t\t\"PlayerID\": \"44db8c58_1539309129829\",\n" +
            "\t\t\"MG_MSG_PROGRAM_URL\": \"http://gslbmgspvod.miguvideo.com/depository_sjq/asset/zhengshi/5102/129/775/5102129775/media/5102129775_5003782672_56.mp4.m3u8?msisdn=15917759049&mdspid=&spid=600463&netType=5&sid=5500628662&pid=2028597139&timestamp=20181012095204&Channel_ID=0116_25000403-99000-200300270000001&ProgramID=647771975&ParentNodeID=-99&assertID=5500628662&client_ip=223.104.63.49&SecurityKey=20181012095204&imei=867718025261420&promotionId=&mvid=5102129775&mcid=1001&mpid=130000101688&encrypt=3172cd1452d4004d27786ef17fc7a2f8&jid=33E5A58781511ECB83EC8102FB495E3E1539309123325V\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"DataUsage\": \"16274499\",\n" +
            "\t\t\"networkType\": \"4G\",\n" +
            "\t\t\"MG_MSG_PLAYER_VERSION\": \"v8.5.1.25\",\n" +
            "\t\t\"jid\": \"3A84760319447371E09C4677A389400E1539310105146V\",\n" +
            "\t\t\"contentId\": \"636434607\",\n" +
            "\t\t\"Subsession\": \"subsession_1539310091346\",\n" +
            "\t\t\"userId\": \"108972825\",\n" +
            "\t\t\"MG_MSG_PROGRAM_URL\": \"http://gslbmgspvod.miguvideo.com/depository_sp/asset/zhengshi/5101/093/576/5101093576/media/5101093576_5003598633_56.mp4.m3u8?msisdn=18765268883&mdspid=&spid=800033&netType=5&sid=5500382346&pid=2028600738&timestamp=20181012100811&Channel_ID=0116_25000403-99000-200300280000000&ProgramID=636433618&ParentNodeID=-99&preview=1&playseek=000000-000600&assertID=5500382346&client_ip=223.104.187.90&SecurityKey=20181012100811&imei=868064036608773&promotionId=&mvid=5101093576&mcid=1002&mpid=130000050463&encrypt=6496cc888fe4044f89e74a5cc5b394f5&jid=3A84760319447371E09C4677A389400E1539310090675V\",\n" +
            "\t\t\"PlayDuration\": \"13410\",\n" +
            "\t\t\"contentType\": \"TV_PLAY\",\n" +
            "\t\t\"PlaybackRate\": \"1.0\",\n" +
            "\t\t\"SourceID\": \"aHR0cDovL2dzbGJtZ3Nwdm9kLm1pZ3V2aWRlby5jb20vZGVwb3NpdG9yeV9zcC9hc3NldC96aGVu\\nZ3NoaS81MTAxLzA5My81NzYvNTEwMTA5MzU3Ni9tZWRpYS81MTAxMDkzNTc2XzUwMDM1OTg2MzNf\\nNTYubXA0Lm0zdTg/bXNpc2RuPTE4NzY1MjY4ODgzJm1kc3BpZD0mc3BpZD04MDAwMzMmbmV0VHlw\\nZT01JnNpZD01NTAwMzgyMzQ2JnBpZD0yMDI4NjAwNzM4JnRpbWVzdGFtcD0yMDE4MTAxMjEwMDgx\\nMSZDaGFubmVsX0lEPTAxMTZfMjUwMDA0MDMtOTkwMDAtMjAwMzAwMjgwMDAwMDAwJlByb2dyYW1J\\nRD02MzY0MzM2MTgmUGFyZW50Tm9kZUlEPS05OSZwcmV2aWV3PTEmcGxheXNlZWs9MDAwMDAwLTAw\\nMDYwMCZhc3NlcnRJRD01NTAwMzgyMzQ2JmNsaWVudF9pcD0yMjMuMTA0LjE4Ny45MCZTZWN1cml0\\neUtleT0yMDE4MTAxMjEwMDgxMSZpbWVpPTg2ODA2NDAzNjYwODc3MyZwcm9tb3Rpb25JZD0mbXZp\\nZD01MTAxMDkzNTc2Jm1jaWQ9MTAwMiZtcGlkPTEzMDAwMDA1MDQ2MyZlbmNyeXB0PTY0OTZjYzg4\\nOGZlNDA0NGY4OWU3NGE1Y2M1YjM5NGY1JmppZD0zQTg0NzYwMzE5NDQ3MzcxRTA5QzQ2NzdBMzg5\\nNDAwRTE1MzkzMTAwOTA2NzVW\\n_1539310092556\",\n" +
            "\t\t\"title\": \"《与贝尔同行之突破极限》05\",\n" +
            "\t\t\"video_length\": \"2613000\",\n" +
            "\t\t\"Session\": \"3A84760319447371E09C4677A389400E1539310090675V\",\n" +
            "\t\t\"PlayerID\": \"310045_1539310091344\",\n" +
            "\t\t\"MG_MSG_TIME\": \"1539310105768\",\n" +
            "\t\t\"account\": \"18765268883\",\n" +
            "\t\t\"type\": \"70000000\",\n" +
            "\t\t\"quit_point\": \"12959\"\n" +
            "\t}],\n" +
            "\t\"exception\": [{\n" +
            "\t\t\"name\": \"Attempt to invoke virtual method 'java.lang.String[] java.lang.String.split(java.lang.String)' on a null object reference\",\n" +
            "\t\t\"reason\": \"java.lang.NullPointerException%3A+Attempt+to+invoke+virtual+method+%27java.lang.String%5B%5D+java.lang.String.split%28java.lang.String%29%27+on+a+null+object+reference%0A%09at+com.akazam.android.migutest.Migu220Activity.onClick%28Migu220Activity.java%3A547%29%0A%09at+android.view.View.performClick%28View.java%3A5264%29%0A%09at+android.view.View%24PerformClick.run%28View.java%3A21297%29%0A%09at+android.os.Handler.handleCallback%28Handler.java%3A743%29%0A%09at+android.os.Handler.dispatchMessage%28Handler.java%3A95%29%0A%09at+android.os.Looper.loop%28Looper.java%3A150%29%0A%09at+android.app.ActivityThread.main%28ActivityThread.java%3A5546%29%0A%09at+java.lang.reflect.Method.invoke%28Native+Method%29%0A%09at+com.android.internal.os.ZygoteInit%24MethodAndArgsCaller.run%28ZygoteInit.java%3A794%29%0A%09at+com.android.internal.os.ZygoteInit.main%28ZygoteInit.java%3A684%29%0A\",\n" +
            "\t\t\"ts\": 1523885617779\n" +
            "\t}],\n" +
            "\t\"sdkSessionInfo\": {\n" +
            "\t\t\"clientId\": \"2b67f5044df4eeb3a630c0b5db27eff2\",\n" +
            "\t\t\"appVersion\": \"5.4.1\",\n" +
            "\t\t\"appchannel\": \"25000401-99000-100200070000041\",\n" +
            "\t\t\"os\": \"AD\",\n" +
            "\t\t\"phoneMode\": \"JMM-AL00\",\n" +
            "\t\t\"phoneBrand\": \"HONOR\",\n" +
            "\t\t\"sdkversion\": \"3.1.4\",\n" +
            "\t\t\"osversion\": \"24\",\n" +
            "\t\t\"apppkg\": \"com.cmcc.cmvideo\",\n" +
            "\t\t\"installationID\": \"00000000-2bcd-9c4c-0000-0000000000001536899493541\",\n" +
            "\t\t\"udid\": \"2b67f5044df4eeb3a630c0b5db27eff2\",\n" +
            "\t\t\"networkType\": \"4G\",\n" +
            "\t\t\"account\": \"14752883173\",\n" +
            "\t\t\"accountType\": \"0\",\n" +
            "\t\t\"imei\": \"865551031646496\",\n" +
            "\t\t\"userId\": \"1126042818\",\n" +
            "\t\t\"sessionId\": \"2b67f5044df4eeb3a630c0b5db27eff21539310098120\",\n" +
            "\t\t\"uploadTs\": \"1539310110817\",\n" +
            "\t\t\"promotion\": \"\"\n" +
            "\t},\n" +
            "\t\"sessionStart\": {\n" +
            "\t\t\"startTs\": \"1539310063552\",\n" +
            "\t\t\"sessionId\": \"6bc76d5369ca101254948eeed6a3783b1539310063562\",\n" +
            "\t\t\"udid\": \"6bc76d5369ca101254948eeed6a3783b\",\n" +
            "\t\t\"clientId\": \"3C4CD7C2016CC6A1EC915E83B4F3B3A7\",\n" +
            "\t\t\"os\": \"AD\",\n" +
            "\t\t\"userId\": \"\",\n" +
            "\t\t\"account\": \"\"\n" +
            "\t},\n" +
            "\t\"sessionEnd\": {\n" +
            "\t\t\"endTs\": \"1539253407968\",\n" +
            "\t\t\"duration\": \"93256\",\n" +
            "\t\t\"sessionId\": \"6bc76d5369ca101254948eeed6a3783b1539253314717\",\n" +
            "\t\t\"udid\": \"6bc76d5369ca101254948eeed6a3783b\",\n" +
            "\t\t\"clientId\": \"3C4CD7C2016CC6A1EC915E83B4F3B3A7\",\n" +
            "\t\t\"os\": \"AD\",\n" +
            "\t\t\"userId\": \"\",\n" +
            "\t\t\"account\": \"\"\n" +
            "\t}\n" +
            "}";

    @Test
    public void filterTest() {
        ReadContext context = JsonPath.parse(jsonData);
        Filter filter = Filter.filter(where("type").is("60000000"));
        List<Map<String, String>> list = context.read("$.customInfo[?]", filter);
        System.out.println(list);
    }
}
