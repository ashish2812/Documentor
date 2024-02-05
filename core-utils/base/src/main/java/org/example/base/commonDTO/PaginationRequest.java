/**
 * 
 */
package org.example.base.commonDTO;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 
 * @author Ashish
 *
 * @date 04-Feb-2024
 */
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest {

	@Builder.Default
	protected int pageNo = 1;

	@Builder.Default
	protected int limit = 100;

}