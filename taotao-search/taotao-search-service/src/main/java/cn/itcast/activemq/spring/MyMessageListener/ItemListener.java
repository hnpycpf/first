package cn.itcast.activemq.spring.MyMessageListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.search.service.SearchService;

public class ItemListener implements MessageListener {
	@Autowired
	private SearchService service;
	private ObjectMapper object=new ObjectMapper();
	@Override
	public void onMessage(Message arg0) {
		if(arg0 instanceof TextMessage){
			TextMessage text=(TextMessage) arg0;
			try {
				String text2 = text.getText();
				if(StringUtils.isNotBlank(text2)){
				JsonNode tree = object.readTree(text2);
				String type = tree.get("type").asText();
				Long itemId = tree.get("itemId").asLong();
				service.updateItem(type,itemId);}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
