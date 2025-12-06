package project.task.manager.user_service.data.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum AlgorithmType {

		SHA256("HmacSHA256"),
		SHA512("HmacSHA512");

		private final String value;
}