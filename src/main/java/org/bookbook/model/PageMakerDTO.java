package org.bookbook.model;

import org.bookbook.model.Criteria;

public class PageMakerDTO {

	/* �떆�옉 �럹�씠吏� */
    private int startPage;
    
    /* �걹 �럹�씠吏� */
    private int endPage;
    
    /* �씠�쟾 �럹�씠吏�, �떎�쓬 �럹�씠吏� 議댁옱�쑀臾� */
    private boolean prev, next;
    
    /*�쟾泥� 寃뚯떆臾� �닔*/
    private int total;
    
    /* �쁽�옱 �럹�씠吏�, �럹�씠吏��떦 寃뚯떆臾� �몴�떆�닔 �젙蹂� */
    private Criteria cri;
	
    /* �깮�꽦�옄 */
    public PageMakerDTO(Criteria cri, int total) {
        
        this.cri = cri;
        this.total = total;
        
        /* 留덉�留� �럹�씠吏� */
        this.endPage = (int)(Math.ceil(cri.getPageNum()/10.0))*10;
        /* �떆�옉 �럹�씠吏� */
        this.startPage = this.endPage - 9;
        
        /* �쟾泥� 留덉�留� �럹�씠吏� */
        int realEnd = (int)(Math.ceil(total * 1.0/cri.getAmount()));
        
        /* �쟾泥� 留덉�留� �럹�씠吏�(realend)媛� �솕硫댁뿉 蹂댁씠�뒗 留덉�留됲럹�씠吏�(endPage)蹂대떎 �옉�� 寃쎌슦, 蹂댁씠�뒗 �럹�씠吏�(endPage) 媛� 議곗젙 */
        if(realEnd < this.endPage) {
            this.endPage = realEnd;
        }
        
        /* �떆�옉 �럹�씠吏�(startPage)媛믪씠 1蹂대떎 �겙 寃쎌슦 true */
        this.prev = this.startPage > 1;
        
        /* 留덉�留� �럹�씠吏�(endPage)媛믪씠 1蹂대떎 �겙 寃쎌슦 true */
        this.next = this.endPage < realEnd;
        
        
    }

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	@Override
	public String toString() {
		return "PageMakerDTO [startPage=" + startPage + ", endPage=" + endPage + ", prev=" + prev + ", next=" + next
				+ ", total=" + total + ", cri=" + cri + "]";
	}
}
