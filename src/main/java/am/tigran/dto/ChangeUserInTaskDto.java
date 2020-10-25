package am.tigran.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangeUserInTaskDto {
    private Integer userId;
    private Integer taskId;
}
