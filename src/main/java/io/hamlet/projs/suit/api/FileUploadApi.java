package io.hamlet.projs.suit.api;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import io.hamlet.projs.suit.bean.Result;
import io.hamlet.projs.suit.utils.ResultUtils;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.stereotype.Component;


@Component
@Produces("application/json;charset=utf-8")
@Path("/upload/")
public class FileUploadApi {

	static final String TEMP_PATH = "uploadBuffer";
	static final String UPLOAD_PATH = "images/data/suits/#filename";

	@Context
	HttpServletRequest request;

	@Path("/garmentImg")
	@POST
	public Result uploadGarmentImg() {
		DiskFileUpload fu = new DiskFileUpload();
		// 设置最大文件尺寸，这里是4MB
		fu.setSizeMax(4194304);
		// 设置缓冲区大小，这里是4kb
		fu.setSizeThreshold(4096 * 64);
		// 设置临时目录：
		fu.setRepositoryPath(getTmpPath());
		try {
			List<FileItem> fileItemList = fu.parseRequest(request);
			
			if(fileItemList.size() > 1) {
				return ResultUtils.error("一次只允许上传一张图片");
			}else if(fileItemList.size() < 1){
				return ResultUtils.error("文件数据为空");
			}else {
				FileItem item = fileItemList.get(0);
				String filename = item.getName();
				File newFile = new File(getUploadFilePath(filename));
				item.write(newFile);
				return ResultUtils.success((Object)item.getName());
				
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResultUtils.error("上传数据时发生错误");
		
		
	}

	private String getTmpPath() {
		String curDir = request.getServletContext().getRealPath(TEMP_PATH);
		System.out.println("TmpPath Parent dir: " + curDir);
		return curDir;
	}
	
	private String getUploadFilePath(String filename) {
		return request.getServletContext().getRealPath(UPLOAD_PATH.replace("#filename", filename));
	}

}
