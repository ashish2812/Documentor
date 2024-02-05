package org.example.base.commonDTO;

//import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Ashish
 *
 * @date 04-Feb-2024
 *
 **/

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractSearchIndexDto implements Serializable {

	private static final long serialVersionUID = 1635296868115644218L;

	protected Long id;

	protected String uuid;

	protected Boolean status;

//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date createdAt;

//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	protected Date updatedAt;

	protected String createdBy;

	protected String updatedBy;

}
