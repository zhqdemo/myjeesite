package com.project.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * 获取文件类型
	 * @param extension
	 * @return
	 */
	public static String getFiletype(String extension){
		String image = ".png,.jpg,.jpeg,.gif,.bmp";
		String audio = ".mp3";
		String video = ".flv,.swf,.mkv,.avi,.rm,.rmvb,.mpeg,.mpg,.ogg,.ogv,.mov,.wmv,.mp4,.webm,.wav,.mid";
		if(image.contains(extension.toLowerCase())){
			return "image";
		}else if(audio.contains(extension.toLowerCase())){
			return "audio";
		}else if(video.contains(extension.toLowerCase())){
			return "video";
		}else{
			return "file";
		}
	}
	/**
	 * 是否为动态网页文件
	 * @param extension
	 * @return
	 */
	public static boolean isWebFile(String extension){
		String fileExtension = ".jsp,.php,.asp,.aspx,.class,.java,.bat,.sh";
		if((","+fileExtension+",").contains(","+extension.toLowerCase()+",")){
			return true;
		}
		return false;
	}
	/**
	 * 获取html标签中的img的src
	 * @param content
	 * @return
	 */
	public static List<String> getImgSrcList(String content){
        List<String> list = new ArrayList<String>();
        //目前img标签标示有3种表达式
        //<img alt="" src="1.jpg"/>   <img alt="" src="1.jpg"></img>     <img alt="" src="1.jpg">
        //开始匹配content中的<img />标签
        Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
        Matcher m_img = p_img.matcher(content);
        boolean result_img = m_img.find();
        if (result_img) {
            while (result_img) {
                //获取到匹配的<img />标签中的内容
                String str_img = m_img.group(2);
                //开始匹配<img />标签中的src
                Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
                Matcher m_src = p_src.matcher(str_img);
                if (m_src.find()) {
                    String str_src = m_src.group(3);
                    list.add(str_src);
                }
                //匹配content中是否存在下一个<img />标签，有则继续以上步骤匹配<img />标签中的src
                result_img = m_img.find();
            }
        }
        return list;
    }
}
