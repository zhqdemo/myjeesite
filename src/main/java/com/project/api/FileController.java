package com.project.api;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.project.util.Encrypt;
import com.project.util.ResultBean;
import com.project.util.StringUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.SystemPath;
import com.thinkgem.jeesite.common.web.BaseController;
@Controller
@RequestMapping(value = "${adminPath}/file")
public class FileController extends BaseController {	
	
	@RequestMapping(value = "imageFileUpload")
	public @ResponseBody ResultBean fileUpload(@RequestParam("file") CommonsMultipartFile[] files,HttpServletRequest request){
		String fileP = SystemPath.getSysPath()+"filetemp"+SystemPath.getSeparator()+DateUtils.getYear()+SystemPath.getSeparator();//文件物理路径
		String fileUrl = "filetemp/"+DateUtils.getYear()+"/";//文件url访问路径
		System.out.println(fileP);
		//文件夹是否存在
		File folder	 = new File(fileP);
		if(!folder.exists()){
			folder.mkdirs();
		}
		//获取限制参数
		String format = request.getParameter("format");//格式限制
		String sizeStr = request.getParameter("size");//大小限制
		String widthStr = request.getParameter("width");//宽度限制
		String heightStr = request.getParameter("height");//高度限制

		ResultBean result = new ResultBean();
		if(files!=null&&files.length>=1){
			for(CommonsMultipartFile item:files){
				try {
					FileItem file = item.getFileItem();					
					String fileName = item.getFileItem().getName();
					String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
					if(StringUtil.isWebFile(extension)){
						result.setCode(1);
						result.setMsg("文件为非法文件");
						return result;
					}
					//检验文件名现在
					if(StringUtils.isNotBlank(format)){
						String formats[] = format.split(",");
						boolean flag = false;
						for(String f:formats){
							if(!f.startsWith(".")){
								f = "."+f;
							}
							if(f.toLowerCase().equals(extension)){
								flag = true;
							}
						}
						if(!flag){
							result.setCode(1);
							result.setMsg("文件格式不正确,必须："+format);
							return result;
						}
					}
					//文件大小限制
					if(StringUtils.isNotBlank(sizeStr)){
						long fileSize = file.getSize()/1024;
						int fileMini = 0;
						try{
							fileMini = Integer.parseInt(sizeStr);
						}catch(Exception e){
							result.setCode(1);
							result.setMsg("文件大小异常");
							return result;
						}
						if(fileMini>0){
							if(fileSize>fileMini){
								result.setCode(1);
								result.setMsg("文件大小超过"+fileMini+"KB限制");
								return result;
							}
						}
					}

					String filename = null;
					filename = new String(Encrypt.getMD5(file.getInputStream()))+ extension;
					String nameMd5 = DateUtils.getYear()+"/"+filename;
					fileUrl = fileUrl + filename;
					
					fileP = fileP + filename;
					File uploadedFile = new File(fileP);
					item.getFileItem().write(uploadedFile);
					
					
					result.setCode(0);
					result.setMsg("文件上传成功");
					Map<String,Object> data = new HashMap<String, Object>();
					data.put("nameMd5", nameMd5);
					data.put("size", file.getSize());
					data.put("name", fileName);
					data.put("img", fileUrl);
					if(StringUtil.getFiletype(extension).equals("image")){
						BufferedImage img = ImageIO.read(uploadedFile);
						data.put("width", img.getWidth());
						data.put("height", img.getHeight());
						img.flush();
						//检验文件分辨率
						if(StringUtils.isNotBlank(widthStr)){
							int width = 0;
							try{
								width = Integer.parseInt(widthStr);
								if(width>0&&width!=img.getWidth()){
									result.setCode(1);
									result.setMsg("图片宽度不正确,必须："+width+"px");
									return result;
								}
							}catch(Exception e){
								result.setCode(1);
								result.setMsg("宽度设置异常");
								return result;
							}
						}
						if(StringUtils.isNotBlank(heightStr)){
							int height = 0;
							try{
								height = Integer.parseInt(heightStr);
								if(height>0&&height!=img.getHeight()){
									result.setCode(1);
									result.setMsg("图片高度不正确,必须："+height+"px");
									return result;
								}
							}catch(Exception e){
								result.setCode(1);
								result.setMsg("宽度设置异常");
								return result;
							}
						}
					}
					result.setData(data);

				} catch (Exception e) {
					e.printStackTrace();
				};
			}
		}else{
			result.setCode(1);
			result.setMsg("未上传文件");
		}
		return result;
	}
}
