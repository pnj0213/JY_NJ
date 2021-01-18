package board;

public class Paging {
	 //�솕硫댁뿉 蹂댁뿬吏� 寃뚯떆湲��쓽 媛쒖닔瑜� 吏��젙
   private int pageSize=10;
   private int count =0; //�쟾泥� 湲��쓽 媛��닔瑜� ���옣�븯�뒗 蹂��닔
   private int number =0; //�럹�씠吏� �꽆踰꾨쭅 蹂��닔
   private String pageNum;

   private int startRow;
   private int endRow;
   
   private int currentPage;
   private int pageCount;
   private int startPage;
   private int pageBlock;
   private int endPage;
   
   private int prev; //�씠�쟾
   private int next; //�떎�쓬
   
   
   
	public Paging(String pageNum) {
	 //留뚯빟 泥섏쓬 boardList.jsp瑜� �겢由��븯嫄곕굹 �닔�젙 �궘�젣 �벑 �떎瑜� 寃뚯떆湲��뿉�꽌 �씠 �럹�씠吏�濡� �꽆�뼱�삤硫� pageNum媛믪씠 �뾾湲곗뿉 null 泥섎━瑜� �빐以�
	  if(pageNum==null){
			 pageNum="1";
	  }
	  this.pageNum=pageNum;
	  
	  //�쟾泥� 蹂닿퀬�옄 �븯�뒗 �럹�씠吏��닽�옄瑜� ���옣
	  currentPage =Integer.parseInt(pageNum);
	}
	 

	public void setTotalCount(int count){
		
		this.count=count;
		
		//�쁽�옱 �럹�씠吏��뿉 蹂댁뿬以� �떆�옉 踰덊샇瑜� �꽕�젙 = �뜲�씠�꽣 踰좎씠�뒪�뿉�꽌 遺덈윭�삱 �떆�옉踰덊샇
	    startRow =(currentPage-1) *pageSize+1;
		endRow =currentPage * pageSize;
		
		//�뀒�씠釉붿뿉 �몴�떆�븷 踰덊샇瑜� 吏��젙
		this.number =count - (currentPage -1 ) * pageSize;
		
		//�럹�씠吏� 怨꾩궛
		pageCaculator();
	}
	 
	   
   public void pageCaculator(){
   	if(count  >0){
   		
   		pageCount =count /pageSize + (count%pageSize == 0 ?  0 :1) ; //移댁슦�꽣留� �닽�옄瑜� �뼹留덇퉴吏� 蹂댁뿬以꾧굔吏� 寃곗젙
   		
   		//�떆�옉 �럹�씠吏� �닽�옄瑜� �꽕�젙
   		startPage =1;
   		
   		if(currentPage %10 !=0){
   			startPage =(int)(currentPage/10)*10+1;
   		}else{
   			startPage =((int)(currentPage/10)-1)*10+1;
   		}
   		
   		pageBlock=10;//移댁슫�꽣留� 泥섎━ �닽�옄
   		endPage =startPage+pageBlock-1;//�솕硫댁뿉 蹂댁뿬吏� �럹�씠吏��쓽 留덉�留� �닽�옄
   		if(endPage > pageCount) endPage =pageCount;
   		
   		
   		//�씠�쟾 �떎�쓬
   		if(startPage >pageSize)  prev =startPage-10;
   		//�떎�쓬
   		if(endPage < pageCount) next=startPage+10;
   	}   		
   }


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public String getPageNum() {
		return pageNum;
	}


	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}


	public int getStartRow() {
		return startRow;
	}


	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}


	public int getEndRow() {
		return endRow;
	}


	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}


	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public int getPageCount() {
		return pageCount;
	}


	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}


	public int getStartPage() {
		return startPage;
	}


	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}


	public int getPageBlock() {
		return pageBlock;
	}


	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
	}


	public int getEndPage() {
		return endPage;
	}


	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}


	public int getPrev() {
		return prev;
	}


	public void setPrev(int prev) {
		this.prev = prev;
	}


	public int getNext() {
		return next;
	}


	public void setNext(int next) {
		this.next = next;
	}

   
   
   
	 	
	
}
