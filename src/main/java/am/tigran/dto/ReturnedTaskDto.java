package am.tigran.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReturnedTaskDto {
    private Integer taskId;
    private String taskName;
    private String description;
    private String creationDate;
    private String updateDate;
    private String userName;
}
