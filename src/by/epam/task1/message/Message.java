package by.epam.task1.message;

import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

public class Message {
	
	
	public static void showMsg (String mandatoryMsg, Object... otherMsg) {
		StringJoiner joiner = new StringJoiner(", ");
		for(Object msg:otherMsg) {
			//check whether msg is Collection and show it
			if (msg instanceof Collection) {
				
				for (Object subMsg:(Collection<?>)msg) {
					joiner.add("\n"+ subMsg);
				}
			}
			//check whether msg is Map and show it
			else if(msg instanceof Map<?, ?>) {
				joiner.add("\n");
				for (Map.Entry<?, ?>entry:((Map<?, ?>)msg).entrySet()) {
					Object key = entry.getKey();
					Object value = entry.getValue();
					joiner.add("\n"+ key+": "+value);
				}
				
			}else {
				joiner.add(""+msg);
			}
			
			
		}
		System.out.println("\n"+mandatoryMsg + joiner);
	}

}
