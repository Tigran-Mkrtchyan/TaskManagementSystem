package am.tigran.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangeStatusInTaskDto {
    private Integer taskId;
    private String status;
}
