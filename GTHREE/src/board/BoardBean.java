package board;

public class BoardBean {
	private int num;
	private String file_id;
	private String emp_no;
	private String file_name;
	private String file_size;
	private String upload_date;
	private String author;
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public String getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	public String getUpload_date() {
		return upload_date;
	}
	public void setUpload_date(String upload_date) {
		this.upload_date = upload_date;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@Override
	public String toString() {
		return "BoardBean [num=" + num + ", file_id=" + file_id + ", emp_no=" + emp_no + ", file_name=" + file_name
				+ ", file_size=" + file_size + ", upload_date=" + upload_date + ", author=" + author + "]";
	}
	
	
}