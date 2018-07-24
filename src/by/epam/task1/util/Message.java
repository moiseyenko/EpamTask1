package by.epam.task1.util;

import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Message {
	private static Logger LOG = LogManager.getLogger(Message.class);

	public static void showMsg(String mandatoryMsg, Object... otherMsg) {
		StringJoiner joiner = new StringJoiner(", ");
		for (Object msg : otherMsg) {
			// check whether msg is Collection and show it
			if (msg instanceof Collection) {

				for (Object subMsg : (Collection<?>) msg) {
					joiner.add("\n" + subMsg);
				}
			}
			// check whether msg is Map and show it
			else if (msg instanceof Map<?, ?>) {
				joiner.add("\n");
				for (Map.Entry<?, ?> entry : ((Map<?, ?>) msg).entrySet()) {
					Object key = entry.getKey();
					Object value = entry.getValue();
					joiner.add("\n" + key + ": " + value);
				}

			} else {
				joiner.add(msg.toString());
			}

		}
		if (LOG.isInfoEnabled()) {
			LOG.info(mandatoryMsg + joiner + "\n");
		}
	}

}
