package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FileDown")
public class FileDownServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)  {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			
			
			String file_id = request.getParameter("file_id");
//			file_id = new String(file_id.getBytes("UTF-8"), "ISO-8859-1");
			file_id = encodeFileNameForDownload(file_id);
			
			// 폴더의 경로를 가져옴.
			String path = this.getServletContext().getRealPath("uploadedFile");
			
			File file = new File(file_id,path);
			
			 // 헤더에 파일이름 용량을 설정
			response.setHeader("Content-Description", "file download");
			response.setHeader("Content-Disposition","attachment;filename="+file_id+";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Length",""+file.length());
			
			FileInputStream fis = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			
			byte[] buf = new byte[1024];
			int readCount = 0;
			
			while((readCount = fis.read(buf)) !=-1){
			     os.write(buf,0,readCount);
			}
			    fis.close();
			    os.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String encodeFileNameForDownload(String str) throws UnsupportedEncodingException{ 
		return new String(str.getBytes("UTF-8"),"ISO-8859-1");
	}
}
