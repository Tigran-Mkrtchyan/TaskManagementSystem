package am.tigran.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Setter
@Getter
@Entity
@Table(name="tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "creation_data")
    private Date creationDate;


    @Column(name = "update_data")
    private Date updateDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "task_status")
    private TaskStatus taskStatus;

    private String description;
    @ManyToOne
    @JoinColumn(name = "user_Id", referencedColumnName = "id")
    private User user;

    public void add(User user) {
        this.user = user;
        user.getTasks().add(this);
    }
}
