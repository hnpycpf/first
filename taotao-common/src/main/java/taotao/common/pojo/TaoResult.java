package taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

public class TaoResult<T> implements Serializable{

	private Long total;
	
	private List<T> rows;

	public TaoResult(Long total, List<T> rows) {
		
		this.total = total;
		this.rows = rows;
	}

	public TaoResult() {
		
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	
	
	
}
