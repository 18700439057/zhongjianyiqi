package com.sibo.business.utils;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;

import com.github.wxiaoqi.security.common.exception.businessException.BusinessRuntimeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import net.sf.jxls.transformer.XLSTransformer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ExportUtils {
	
	public static final String TEMPLATE_PATH = "";
	public static final String TEMP_PATH = "";


	/**
	 * <strong>创建信息:2015年7月10日 下午6:35:57 </strong> <br>
	 * <strong>概要 : 通用导出方法</strong> <br>
	 * <strong>修改记录 : (修改人： ;时间： ;目的: ;)</strong> <br>
	 *@param source 模板文件路径
	 *@param target 目标文件路径
	 *@param beansMaps 数据对象
	 *@return
	 *@throws GlobalException
	 */
	public static String export(String source,String target,Map<String, Object> beansMaps) throws GlobalException {
		String[] sourceArrays =  StringUtils.split(source, '.');
//		source = TEMPLATE_PATH + File.separator+source;
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		String month = String.valueOf(date.get(Calendar.MONTH)+1);
		String day = String.valueOf(date.get(Calendar.DATE));
		String currentTimeMillis = System.currentTimeMillis()+"";
		String currentTime = currentTimeMillis.substring(currentTimeMillis.length()-3);
		target = target +sourceArrays[0].substring(sourceArrays[0].lastIndexOf("\\")+1)+"-"+year+month+day+currentTime +"."+sourceArrays[1];
		XLSTransformer transformer = new XLSTransformer();
		try {
			transformer.transformXLS(source, beansMaps, target);
		} catch (Exception e) {
			Logger.getLogger(ExportUtils.class).error("导出excel文件出错 ", e);
			throw new BusinessRuntimeException("导出excel文件出错");
		}
		return target;
	}
	public static String export2(String source,String target,Map<String, Object> beansMaps) throws GlobalException {
		XLSTransformer transformer = new XLSTransformer();
		try {
			transformer.transformXLS(source, beansMaps, target);
		} catch (Exception e) {
			Logger.getLogger(ExportUtils.class).error("导出excel文件出错 ", e);
		}
		return target;
	}

	
	/**
	 * <strong>创建信息:2015年7月10日 下午2:24:51 </strong> <br>
	 * <strong>概要 : 模板文件对象</strong> <br>
	 * <strong>修改记录 : (修改人： ;时间： ;目的: ;)</strong> <br>
	 *@param templateName 模板名称
	 *@return File 对象
	 */
	public static File template(String templateName) {
		return new File(TEMPLATE_PATH, templateName);
	}
	
	/**
	 * <strong>创建信息:2015年7月10日 下午2:23:01 </strong> <br>
	 * <strong>概要 : 根据模板名称获取模板路径</strong> <br>
	 * <strong>修改记录 : (修改人： ;时间： ;目的: ;)</strong> <br>
	 *@param templateName
	 *@return
	 */
	public static String getTemplatePath(String templateName) {
		return template(templateName).getAbsolutePath();
	}

	/**
	 * <strong>创建信息:2015年7月10日 上午9:36:46 </strong> <br>
	 * <strong>概要 : 文件存储</strong> <br>
	 * <strong>修改记录 : (修改人： ;时间： ;目的: ;)</strong> <br>
	 *@param file 临时文件对象
	 *@return
	 */
	public static String uploadTemp(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		File targetFile = new File(TEMP_PATH, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		// 保存
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			//Logger.getLogger(ImportUtils.class).error("文件导出，临时文件存储出错"+fileName, e);
		}
		
		return targetFile.getAbsolutePath();
	}


	public static void down(String downLoadPath,String trueName,HttpServletRequest request,
						HttpServletResponse response) throws GlobalException {
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;
		try {
			trueName = URLDecoder.decode(trueName,"utf-8");
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			long fileLength = new File(downLoadPath).length();
			response.setContentType("application/x-msdownload;");
			if(request.getHeader("User-Agent").contains("Firefox")){
				response.setHeader("Content-disposition", "attachment; filename="
						+ new String(trueName.getBytes("utf-8"),"ISO-8859-1"));
			}else{
				response.setHeader("Content-disposition", "attachment; filename="
						+ URLEncoder.encode(trueName,"UTF-8"));
			}
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(downLoadPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			//log.info("文件下载出错"+downLoadPath, e);
		} finally {
			if (bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					throw new GlobalException(e.getMessage());
				}
			}

			if (bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					throw new GlobalException(e.getMessage());
				}
			}

		}
	}


}
