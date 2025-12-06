package project.task.manager.user_service.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Component
public class Utils {
		
		public String mapRandomPassword() {
				String random = UUID.randomUUID().toString();

				char[] randomCharArray = random.toCharArray();
				randomCharArray[1] = '!';
				randomCharArray[3] = '%';
				randomCharArray[5] = 'F';
				randomCharArray[7] = 'H';

				StringBuilder sb = new StringBuilder();

				for(char c : randomCharArray) {
						sb.append(c);
				}
				return sb.toString().substring(0, 9);
		}
}
