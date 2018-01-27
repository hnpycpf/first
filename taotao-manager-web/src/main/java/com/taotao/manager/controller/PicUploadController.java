package com.taotao.manager.controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import taotao.common.pojo.PicUploadResult;



@Controller
@RequestMapping("pic/upload")
public class PicUploadController {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Value("${TAOTAO_PIC_PATH}")
	private String TAOTAO_PIC_PATH;

	// 声明能够上传的图片类型
	private String[] TYPE = { ".jpg", ".png", ".gif", ".jpeg", ".bmp" };

	// filePostName : "uploadFile", //上传的文件名
	// uploadJson : '/rest/pic/upload', //上传的请求路径

	// Request URL:http://manager.taotao.com/rest/pic/upload?dir=image
	// Request Method:POST

	@RequestMapping(method = RequestMethod.POST,produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public String upload(MultipartFile uploadFile) throws Exception {
		// 初始化PicUploadResult返回对象，默认上传失败
		PicUploadResult picUploadResult = new PicUploadResult();
		// error：0上传成功，1代表上传失败
		picUploadResult.setError(1);

		// 声明标识位，校验准备
		boolean flag = false;

		// 校验上传文件的后缀
		for (String type : TYPE) {
			// 判断上传文件是否是指定的后缀
			if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
				// 如果是指定的后缀的图片，设置标志位为true
				flag = true;

				// 跳出循环
				break;
			}
		}

		// 判断校验是否失败，
		// if(flag==false){
		if (!flag) {
			// 如果失败直接返回
			String json = MAPPER.writeValueAsString(picUploadResult);
			return json;
		}

		// 如果校验成功，重置标识位
		flag = false;

		// 校验文件内容
		try {
			BufferedImage image = ImageIO.read(uploadFile.getInputStream());
			if (image != null) {
				// 设置图片的宽和高到返回对象中
				picUploadResult.setHeight(image.getHeight() + "");
				picUploadResult.setWidth(image.getWidth() + "");

				// 如果执行到这里，表示没有问题，校验成功，设置标识位
				flag = true;
			}

		} catch (Exception e) {
			// 不用处理异常，因为这里就是校验，如果有异常就是校验失败，没有必要处理异常
		}

		// 判断校验是否成功
		// if(flag==true){
		if (flag) {
			// 如果成功，则使用FastDFS的java客户端上传图片
			// 初始化图片上传，加载配置文件
			ClientGlobal.init(System.getProperty("user.dir") + "/src/main/resources/tracker/tracker.conf");

			// 创建TrackerClient
			TrackerClient trackerClient = new TrackerClient();

			// 获取TrackerServer
			TrackerServer trackerServer = trackerClient.getConnection();

			// 声明StorageServer为null
			StorageServer storageServer = null;

			// 创建StorageClient
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);

			// 使用StorageClinet上传图片，返回字符串数组
			// 获取上传文件的后缀,有可能图片名是1.2.3.4.jpg
			String extName = StringUtils.substringAfterLast(uploadFile.getOriginalFilename(), ".");
			String[] str = storageClient.upload_file(uploadFile.getBytes(), extName, null);

			// 使用返回的字符串数组拼接图片的url
			String picUrl = this.TAOTAO_PIC_PATH + str[0] + "/" + str[1];

			// 封装PicUploadResult，设置图片的数据
			// 设置图片url
			picUploadResult.setUrl(picUrl);
			// 设置上传为成功状态error：0上传成功，1代表上传失败
			picUploadResult.setError(0);

		}

		// 返回PicUploadResult结果
		String json = MAPPER.writeValueAsString(picUploadResult);
		return json;
	}

}
