import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.container.page.PageHandler;
import com.github.pagehelper.PageHelper;
import com.taotao.manager.mapper.ItemMapper;
import com.taotao.manager.pojo.Item;

public class Class {
	
	private CloudSolrServer server;
	
	private ItemMapper item;
	@Before
	public void me(){
	
		ApplicationContext context=	new ClassPathXmlApplicationContext("spring/applicationContext-dao.xml");
		
		server = context.getBean(CloudSolrServer.class);
		item=context.getBean(ItemMapper.class);
		System.out.println(server);
		System.out.println(item);
	}
	@Test
	public void me1() throws SolrServerException, IOException, InterruptedException{
		
		int size;
		int i=1;
		do {
			Thread.sleep(50);
			PageHelper.startPage(i++, 500);
			List<Item> select = item.select(null);
			ArrayList<SolrInputDocument> arrayList = new ArrayList<>();
			for (Item item : select) {
				SolrInputDocument solr=new SolrInputDocument();
				solr.addField("id", item.getId());
				solr.addField("item_title", item.getTitle());
				solr.addField("item_price", item.getPrice());
				solr.addField("item_image", item.getImage());
				solr.addField("item_cid", item.getCid());
				solr.addField("item_status", item.getStatus());
				arrayList.add(solr);		
			}
			server.add(arrayList);
			server.commit();
			 size = select.size();
		} while (size==500);
		System.out.println(1);
	}
}
